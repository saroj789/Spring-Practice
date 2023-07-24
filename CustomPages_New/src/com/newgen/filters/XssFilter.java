
package com.newgen.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.newgen.bean.Constants;
import com.newgen.business.Validations;
import com.newgen.logger.CustomLogger;
import com.newgen.regex.MatchRegexD;


public class XssFilter implements Filter {

	private FilterConfig filterConfig = null;
	private List<String> mvectHtmlElements;
	String sContextPath;

	/** Creates a new instance of XssFilter */
	public XssFilter() {
		/* Creates a new instance of XssFilter */
	}

	public void init(FilterConfig filterConfig) {
		Enumeration<String> paramEnum = filterConfig.getInitParameterNames();
		this.filterConfig = filterConfig;

		String key = "";
		String value = "";
		while (paramEnum.hasMoreElements()) {
			key =  paramEnum.nextElement();
			value = filterConfig.getInitParameter(key);

			if (key.equals("HtmlElements")) {
				StringTokenizer stn = new StringTokenizer(value, ",");
				String temp = null;
				mvectHtmlElements = new ArrayList<>();
				while (stn.hasMoreTokens()) {
					temp = stn.nextToken();
					if (temp.length() > 0) {
						mvectHtmlElements.add(temp.trim().toLowerCase());
					}
				}
			}
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse resp=null;
        HttpServletRequest req=null;
        String sessionIdFilter=RandomStringUtils.randomAlphanumeric(20);
		try {
			boolean redirectFlag = false;
			boolean regexFlag = false;
			if (filterConfig == null) {
				return;
			}
			req = (HttpServletRequest) request;
			resp = (HttpServletResponse) response;
			
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"servlet - "+req.getRequestURI()+" XssFilter Started");
			
			String url = req.getRequestURI();
			
			resp.setHeader("Cache-Control", "no-store"); // HTTP 1.1
			resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
			resp.setDateHeader("Expires", -1); // prevents caching at the proxy server

			sContextPath = req.getContextPath();

			String strParmName = "";
			String strParmValue = "";
			
			Enumeration<String> enumKeys = req.getParameterNames();
			while (enumKeys.hasMoreElements()) {
				strParmName = enumKeys.nextElement();
				strParmValue = "";
				if (strParmName.equalsIgnoreCase("com.sun.faces.VIEW")) {
					continue;
				}

				if (strParmName != null && strParmName.length() > 0) {
					String strTempParmName = strParmName.toLowerCase();
					if (isInvalidValue(strTempParmName)) {
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Setting redirectFlag value to true for strParmName - "+strParmName);
						CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Setting redirectFlag value to true for strParmName - "+strParmName);
						redirectFlag = true;
						break;
					} 
				}

				if (req.getParameter(strParmName) != null && req.getParameter(strParmName).length() > 0) {
					strParmValue = req.getParameter(strParmName);
					strParmValue = strParmValue.toLowerCase();
					strParmValue=getEncodedString(strParmValue);

					if (isInvalidValue(strParmValue)){
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Setting redirectFlag value to true for strParmName - "+strParmName+" For value - "+strParmValue);
						CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Setting redirectFlag value to true for strParmName - "+strParmName+" For value - "+strParmValue);
						redirectFlag = true;
						break;
					} 
					else if (!url.contains("/LoginServlet") && !url.contains("/Javascript")
							&& !url.contains("/NewUserSavePwdServlet") && !url.contains("/StyleSheet")
							&& !url.contains("/css") && !url.contains("/Images") && !url.contains("/image")
							&& !url.contains("/js") && !url.contains("/fonts")&&(!MatchRegexD.Match(strParmValue))
					){
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Setting redirectFlag value to true for strParmName - "+strParmName+" For value - "+strParmValue);
						CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Setting redirectFlag value to true for strParmName - "+strParmName+" For value - "+strParmValue);
						redirectFlag = true;
						regexFlag = true;
						break;
					}
				}

			}
				
				
			

			if (!redirectFlag) {
				chain.doFilter(req, resp);
			} else {

				if (url.indexOf("/ajax") > -1 || url.indexOf("workitem/list/workitemtable.") > -1) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				} else {
					if (regexFlag) {
						CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"in XSS BAD REQUEST Regex:" + url);
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"in XSS BAD REQUEST Regex:" + url);
						resp.setHeader("InvalidRequestValue", strParmValue);
						String reqString =req.getContextPath()+"/Error.jsp";
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Redirecting to : "+reqString);
	            		resp.setHeader("InvalidRequest", "Y");
	            		resp.sendRedirect(reqString);
						
					} else {
						CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"in XSS BAD REQUEST scripting:" + url);
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"in XSS BAD REQUEST scripting:" + url);
						
						String reqString =req.getContextPath()+"/Error.jsp";
						CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Redirecting to : "+reqString);
	            		resp.setHeader("InvalidRequest", "Y");
	            		resp.sendRedirect(reqString);
						
					}

				}

			}
		} catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Exception in XssFilter: " + Validations.stackTraceToString(e));

		}finally {
			if(req!=null) {
				CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"servlet - "+req.getRequestURI()+" clickkjack Ended");
			}
			
		}
	}

	public boolean bCheckForBrace(String strParmValue, String strKeyWord) {
		int nIndex;
		while (strParmValue.indexOf(strKeyWord) > -1) {
			nIndex = strParmValue.indexOf(strKeyWord) + strKeyWord.length();
			if (nIndex < strParmValue.length()) {
				while (strParmValue.charAt(nIndex) == ' ') {
					nIndex++;
				}
				if (nIndex < strParmValue.length()) {
					if (strParmValue.charAt(nIndex) == '(' || strParmValue.charAt(nIndex) == '`') {
						return true;
					} else {
						strParmValue = strParmValue.substring(nIndex, strParmValue.length());
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean htmlElementCheck(String strParmValue) {
		boolean returnFlag = false;
		if ((strParmValue.length() > 0 && (strParmValue.indexOf('<') > -1 || strParmValue.indexOf("%3c") > -1))&&(mvectHtmlElements != null && !mvectHtmlElements.isEmpty())) {
			String strTemp = "";
			for (int icount = 0; icount < mvectHtmlElements.size(); icount++) {
				strTemp = mvectHtmlElements.get(icount);
				if (strParmValue.indexOf(strTemp) > -1 &&
						(strParmValue.indexOf("<" + strTemp + "+") > -1
						|| strParmValue.indexOf("<" + strTemp + " ") > -1
						|| strParmValue.indexOf("<" + strTemp + "%20") > -1
						|| strParmValue.indexOf("3c" + strTemp + "%20") > -1
						|| strParmValue.indexOf("3c" + strTemp + "+") > -1
						|| strParmValue.indexOf("3c" + strTemp + " ") > -1

						|| strParmValue.indexOf("<" + strTemp + ">") > -1
						|| strParmValue.indexOf("3c" + strTemp + ">") > -1
						|| strParmValue.indexOf("<" + strTemp + "%3e") > -1
						|| strParmValue.indexOf("3c" + strTemp + "%3e") > -1
						|| strParmValue.indexOf("<" + strTemp + "/>") > -1
						|| strParmValue.indexOf("3c" + strTemp + "/>") > -1
						|| strParmValue.indexOf("<" + strTemp + "/%3e") > -1
						|| strParmValue.indexOf("3c" + strTemp + "/%3e") > -1
						|| strParmValue.indexOf("<" + strTemp + "%2f%3e") > -1
						|| strParmValue.indexOf("3c" + strTemp + "%2f%3e") > -1

						|| strParmValue.indexOf("</" + strTemp + ">") > -1
						|| strParmValue.indexOf("</" + strTemp + "%3e") > -1
						|| strParmValue.indexOf("<%2f" + strTemp + ">") > -1
						|| strParmValue.indexOf("<%2f" + strTemp + "%3e") > -1
						|| strParmValue.indexOf("3c%2f" + strTemp + ">") > -1
						|| strParmValue.indexOf("3c%2f" + strTemp + "%3e") > -1
						|| strParmValue.indexOf("3c/" + strTemp + ">") > -1
						|| strParmValue.indexOf("3c/" + strTemp + "%3e") > -1)) {

						returnFlag = true;
						break;
					
				}
			}
			
		}
		return returnFlag;
	}
	
	private String getEncodedString(String str) {
		String strParmValue=str;
		try {
			strParmValue = java.net.URLDecoder.decode(strParmValue, Constants.UTF8ENCODING);
		} catch (Exception ex) {
			CustomLogger.printErr("Exception in XssFilter::",ex);	
		}
		return strParmValue;
	}
	
	private boolean isInvalidValue(String str) {
		boolean flag=false;
		if(str.toLowerCase().indexOf("javascript:") > -1 || htmlElementCheck(str)||
							(str.toLowerCase().indexOf("script:") > -1|| htmlElementCheck(str))||
							(str.toLowerCase().indexOf("eval") > -1 && bCheckForBrace(str, "eval"))||
							(str.toLowerCase().indexOf("alert") > -1 && bCheckForBrace(str, "alert"))||
							(str.toLowerCase().indexOf("prompt") > -1 && bCheckForBrace(str, "prompt"))||
							(str.toLowerCase().indexOf("confirm") > -1 && bCheckForBrace(str, "confirm"))) {
			flag=true;
		}
		return flag;
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {

		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 *
	 */
	public void destroy() {
		this.filterConfig = null;
	}

}
