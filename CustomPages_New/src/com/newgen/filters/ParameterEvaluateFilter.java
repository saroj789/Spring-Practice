package com.newgen.filters;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newgen.bean.Constants;
import com.newgen.business.Validations;
import com.newgen.dmsapi.DMSXmlList;
import com.newgen.dmsapi.DMSXmlResponse;
import com.newgen.eworkstyle.supportbeans.EWContext;
import com.newgen.logger.CustomLogger;


public class ParameterEvaluateFilter implements Filter {
	String encoding = "";
	private Set<String> urlSet;
	private ArrayList<String> headerList;
	private Set<String> inputJsonKeySet = new LinkedHashSet<>();
	private Set<String> inputJsonValueSet = new LinkedHashSet<>();
	private ArrayList<String> urlList;
	private HashSet<String> wrongStringHashSet;
	private HashMap<String, ArrayList<String>> servletparamMapping;
	protected FilterConfig filterConfig;

	public ParameterEvaluateFilter() {
		this.wrongStringHashSet = new HashSet<>();
	}

	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletrequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletresponse;
		StringBuffer url = httpRequest.getRequestURL();
		String newUrl = url.toString();
		String sessionIdFilter=RandomStringUtils.randomAlphanumeric(20);
		

		CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"servlet - "+httpRequest.getRequestURI()+" ParameterEvaluateFilter Started");
		try {
			servletrequest.setCharacterEncoding(this.encoding);
			httpRequest.getHeader("referer");
			servletresponse.setContentType(new StringBuilder().append("text/html;charset=").append(this.encoding).toString());

			String keyTobeEvaluated = keyForUrlTobeEvaluated(newUrl);

			String reqString =httpRequest.getContextPath()+"/Error.jsp";

			if (!validateHeaders(httpRequest)) {
				httpResponse.sendRedirect(reqString);
				return;
			}

			String requestCall = newUrl;
			if (newUrl.indexOf("ControllerServlet") != -1) {
				requestCall = httpRequest.getParameter("requestCall");
			}

			if (isEvaluatedUrl(requestCall)) {
				for (Enumeration<?> parameterNames = servletrequest.getParameterNames(); parameterNames
						.hasMoreElements();) {
					String parameterName = (String) parameterNames.nextElement();
					if ((!"".equals(keyTobeEvaluated)) && (((this.servletparamMapping.get(keyTobeEvaluated)).isEmpty())
							|| ((this.servletparamMapping.get(keyTobeEvaluated)).contains(parameterName)))) {
						continue;
					}
					String[] parameterValue = servletrequest.getParameterValues(parameterName);
					if (this.wrongStringHashSet.isEmpty()) {
						httpResponse.sendRedirect(reqString);
						return;
					}

					boolean isValidParamName = true;
					for (int pcount = 0; pcount < parameterValue.length; pcount++) {
						if (!isCheckValid(parameterValue[pcount])) {
							isValidParamName = false;
							break;
						}
					}
					isValidParamName = (isCheckValid(parameterName)) && (isValidParamName);

					if (!isValidParamName) {
						httpResponse.sendRedirect(reqString);
						return;
					}

				}

				if (this.wrongStringHashSet.isEmpty()) {
					httpResponse.sendRedirect(reqString);
					return;
				}

				this.inputJsonValueSet.clear();
				this.inputJsonKeySet.clear();
				
			}

			if (filterchain != null)
				filterchain.doFilter(servletrequest, servletresponse);
			
		}catch (Exception e) {
			CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Exception in ParameterEvaluateFilter::",e);
		}finally {
			CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"servlet - "+httpRequest.getRequestURI()+" ParameterEvaluateFilter Ended");
		}
	}

	public boolean isEvaluatedUrl(String url) {
		String sIterator = "";

		for (Iterator<String> urlListIterator = this.urlList.iterator(); urlListIterator.hasNext();) {
			sIterator = urlListIterator.next();

			if (url.indexOf(sIterator) != -1) {
				return false;
			}
		}
		return true;
	}

	public boolean isCheckValid(String parameter) {
		String stemp = parameter.toUpperCase();
		String sIterator = "";

		for (Iterator<String> parameterIterator = this.wrongStringHashSet.iterator(); parameterIterator.hasNext();) {
			sIterator = parameterIterator.next();
			sIterator = sIterator.toUpperCase();
			if ((stemp.indexOf(sIterator) != -1) && (stemp.indexOf("<None>") == -1) && (stemp.indexOf("<NONE>") == -1)
					&& (stemp.indexOf("%3CNONE%3E") == -1) && (stemp.indexOf("%3CNone%3E") == -1)) {
				return false;
			}
		}
		return true;
	}

	private String keyForUrlTobeEvaluated(String url) {
		String sIterator = "";

		for (Iterator<String> urlListIterator = this.urlSet.iterator(); urlListIterator.hasNext();) {
			sIterator = urlListIterator.next();

			if (url.indexOf(sIterator) != -1) {
				return sIterator;
			}
			sIterator = "";
		}
		return sIterator;
	}

	private HashMap<String, ArrayList<String>> readRestrictedServletParameter(String filePath) {
		File servletParamMapping = new File(filePath);
		HashMap<String, ArrayList<String>> paramServletMap = new HashMap<>();
		if (servletParamMapping.exists()) {
			try {
				String strMapping = readFile(filePath);
				DMSXmlResponse mapResponse = new DMSXmlResponse(strMapping);
				for (DMSXmlList mapList = mapResponse.createList("Mappings", "Mapping"); mapList
						.hasMoreElements(); mapList.skip()) {
					String urlName = mapList.getVal("UrlName");
					String parmName = mapList.getVal("ParameterName");
					StringTokenizer paramTokenizer = new StringTokenizer(parmName, ",");
					ArrayList<String> paramList = new ArrayList<>();
					while (paramTokenizer.hasMoreElements()) {
						paramList.add((String) paramTokenizer.nextElement());
					}
					paramServletMap.put(urlName, paramList);
				}
			} catch (IOException e) {
				CustomLogger.printErr("IOException in ParameterEvaluateFilter - " + Validations.stackTraceToString(e));
			}

		}

		return paramServletMap;
	}

	private String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	private boolean validateHeaders(HttpServletRequest servletRequest) {
		boolean isValidHeaders = true;

		for (Enumeration<?> headerNames = servletRequest.getHeaderNames(); headerNames.hasMoreElements();) {
			String headerName = (String) headerNames.nextElement();
			if (!this.headerList.contains(headerName)) {
				String headerValue = servletRequest.getHeader(headerName);
				if (this.wrongStringHashSet.isEmpty()) {
					return true;
				}
				if ((!isCheckValid(headerValue)) || (!isCheckValid(headerName))) {
					isValidHeaders = false;
					break;
				}
			}
			
		}

		return isValidHeaders;
	}

	private HashSet<String> readRestrictedInputParameterIni(String txtPath) throws IOException {
		String strLine = null;
		HashSet<String> iniHashSet = new HashSet<>();
		try (FileInputStream restrictedParametersInSrteam = new FileInputStream(txtPath);
			DataInputStream in = new DataInputStream(restrictedParametersInSrteam);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));){
			

			while ((strLine = br.readLine()) != null) {
				strLine = strLine.toUpperCase();
				iniHashSet.add(strLine);
			}

		} catch (Exception e) {
			CustomLogger.printErr("Error in readRestrictedInputParameterIni : "+Validations.stackTraceToString(e));

		}

		return iniHashSet;
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		this.filterConfig = filterconfig;
		String context = filterconfig.getServletContext().getRealPath("/");
		EWContext.setContextPath(context);
		this.encoding = this.filterConfig.getInitParameter("encoding-value");

		populateRestrictedInputParameters(context);

		String servletParameterFilepath = new StringBuilder().append(EWContext.getContextPath()).append("ini")
				.append(File.separator).append("UnevaluatedUrlParameters.xml").toString();

		this.servletparamMapping = readRestrictedServletParameter(servletParameterFilepath);
		this.urlSet = this.servletparamMapping.keySet();

		String headers = this.filterConfig.getInitParameter("unevaluated-headers");
		StringTokenizer token = new StringTokenizer(headers, ",");
		this.headerList = new ArrayList<>();
		while (token.hasMoreTokens()) {
			this.headerList.add(token.nextToken());
		}

		String urls = this.filterConfig.getInitParameter("unevaluated-urls");
		StringTokenizer urltoken = new StringTokenizer(urls, ",");

		this.urlList = new ArrayList<>();
		while (urltoken.hasMoreTokens()) {
			this.urlList.add(urltoken.nextToken());
		}

		
	}

	public void populateRestrictedInputParameters(String context) {
		EWContext.setContextPath(context);
		String restrictedParametersFilepath = null;
		try {
			restrictedParametersFilepath = new StringBuilder().append(EWContext.getContextPath()).append("ini")
					.append(File.separator).append("RestrictedInputParameters.txt").toString();

			this.wrongStringHashSet = readRestrictedInputParameterIni(restrictedParametersFilepath);
		} catch (IOException ex) {
			CustomLogger.printErr("IOException in ParameterEvaluateFilter - " + Validations.stackTraceToString(ex));
		}

	}

	public Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<>();

		Iterator<?> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = (String) keysItr.next();
			Object value = object.get(key);

			if ((value instanceof JSONArray)) {
				this.inputJsonKeySet.add(key);
				value = toList((JSONArray) value);
			} else if ((value instanceof JSONObject)) {
				this.inputJsonKeySet.add(key);
				value = toMap((JSONObject) value);
			} else if ((value instanceof String)) {
				this.inputJsonKeySet.add(key);
				this.inputJsonValueSet.add((String) value);
			} else if ((value instanceof Integer) || (value instanceof Float) || (value instanceof Double)) {
				this.inputJsonKeySet.add(key);
				this.inputJsonValueSet.add(value.toString());
			}

			map.put(key, value);
		}
		return map;
	}

	public List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if ((value instanceof JSONArray)) {
				value = toList((JSONArray) value);
			} else if ((value instanceof JSONObject)) {
				value = toMap((JSONObject) value);
			} else if ((value instanceof String)) {
				this.inputJsonValueSet.add((String) value);
			} else if ((value instanceof Integer)) {
				this.inputJsonValueSet.add(value.toString());
			} else if ((value instanceof Float)) {
				this.inputJsonValueSet.add(value.toString());
			} else if ((value instanceof Double)) {
				this.inputJsonValueSet.add(value.toString());
			}
			list.add(value);
		}
		return list;
	}

	public boolean validateJSONObject(JSONObject objJSONObject, HttpServletRequest request) throws JSONException {
		this.inputJsonValueSet.clear();
		this.inputJsonKeySet.clear();
		boolean isValidParamName = true;

		if ((objJSONObject.toString() != null) && (objJSONObject.toString().length() != 0)) {
			populateRestrictedInputParameters(request.getServletContext().getRealPath("/"));
			isValidParamName = validateKeyAndValuesForJSON(objJSONObject);
		}

		return isValidParamName;
	}

	public boolean validateKeyAndValuesForJSON(JSONObject objJSONObject) throws JSONException {
		boolean isValidParamName = true;
		toMap(objJSONObject);

		if (!this.inputJsonKeySet.isEmpty()) {
			Object[] inputJsonKeyArray = this.inputJsonKeySet.toArray();
			for (int i = 0; i < inputJsonKeyArray.length; i++) {
				String str = (String) inputJsonKeyArray[i];
				if (!isCheckValid(str)) {
					isValidParamName = false;
					break;
				}
			}
		}

		if (isValidParamName && !this.inputJsonValueSet.isEmpty()) {
			Object[] inputJsonValueArray = this.inputJsonValueSet.toArray();
			for (int i = 0; i < inputJsonValueArray.length; i++) {
				String str = (String) inputJsonValueArray[i];
				if (!isCheckValid(str)) {
					isValidParamName = false;
					break;
				}
			}

		}
		return isValidParamName;
	}

	public void destroy() {
		CustomLogger.printOut("In destroy ParameterEvaluateFilter");
	}
}