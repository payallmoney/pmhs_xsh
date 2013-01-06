package cn.net.tongfang.web.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;

public class TimeoutFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
			IOException {
//		// TODO Auto-generated method stub
//		HttpServletRequest req = (HttpServletRequest)request;
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println( "auth is " + auth );
//		System.out.println("========timeout==========="+req.getRequestURI());
//		System.out.println("==========自动session========="+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date()));
//		HttpServletResponse rep = (HttpServletResponse) response;
//		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
//		System.out.println("===========output========"+rep.getOutputStream());
//		ServletOutputStream s = (ServletOutputStream)rep.getOutputStream();
//		System.out.println("===========output========"+s);
//		System.out.println("==================="+req.getRequestURI());
//		if(req.getRequestURI() !="/dwr/login.jsp" && SecurityManager.currentOperator() == null){
//			rep.sendRedirect("login.jsp");
//		}
//		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
    
	
}