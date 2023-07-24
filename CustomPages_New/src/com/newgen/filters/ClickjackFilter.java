

package com.newgen.filters;
import java.io.IOException;

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
import com.newgen.logger.CustomLogger;


public class ClickjackFilter implements Filter 
{

	private String mode = "DENY";
	/**
	 * Add X-FRAME-OPTIONS response header to tell IE8 (and any other browsers who
	 * decide to implement) not to display this content in a frame. For details, please
	 * refer to http://blogs.msdn.com/sdl/archive/2009/02/05/clickjacking-defense-in-ie8.aspx.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		          String sessionId="";
		          HttpServletResponse res=null;
		          HttpServletRequest req=null;
		          String sessionIdFilter=RandomStringUtils.randomAlphanumeric(20);
		          try {
	                  res = (HttpServletResponse)response;
	                  req = (HttpServletRequest)request;
	                  sessionId = req.getSession().getId();
	                  
	                  CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"servlet - "+req.getRequestURI()+" clickkjack filter Started");
	                  res.addHeader("X-FRAME-OPTIONS", mode );
	                  res.setHeader("SET-COOKIE", "JSESSIONID="+ sessionId+ ";HttpOnly");
	                  res.setHeader("SET-COOKIE", "JSESSIONID="+ sessionId+ "; secure");
	
	                  chain.doFilter(request, response);
                    
		          }
		          catch(Exception e){
		        	  CustomLogger.printErr(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"Exception in ClickjackFilter::",e);				    	
		          }finally {
		        	  CustomLogger.printOut(Constants.SESSIONIDFORLOGGER+sessionIdFilter+" - "+"servlet - "+req.getRequestURI()+" clickkjack filter Ended");
				}

      
	}
	@Override
	public void destroy() {
		CustomLogger.printOut("In destroy ClickjackFilter");
	}
	@Override
	public void init(FilterConfig filterConfig) {
		String configMode = filterConfig.getInitParameter("mode");
		if ( configMode != null ) {
			mode = configMode;
		}
	}
	
}
