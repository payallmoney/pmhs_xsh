package cn.net.tongfang.web.util;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

public class AuthFilter extends AuthenticationProcessingFilter {
	
	public static String FAIL_TARGET_PARAMETER = "authentication-failure-url";

	private String failUrlParameter = FAIL_TARGET_PARAMETER;

	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(
				response);
		Writer out = responseWrapper.getWriter();
		String targetUrl = determineTargetUrl(request);
		out.write("{success:true, targetUrl : \'" + targetUrl + "\'}");
		out.close();
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException {
		String failurl = request.getParameter(this.failUrlParameter);
		try {
			if (StringUtils.hasText(failurl)) {
				request.getRequestDispatcher("/"+failurl).forward(request, response); 
			} else {
				failurl = request.getRequestURL().toString();
			}
		} catch (ServletException ex) {
			ex.printStackTrace();
		}
	}
}