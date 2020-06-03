package com.mega.print.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestFileFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String password = ((HttpServletRequest) request).getParameter("uploadfile");
        System.out.println(password);
//
//        if(password.equals("opensesame")) {
//            chain.doFilter(request,response);
//        } else {
//            response.setContentType("text/html");
//            PrintWriter out = response.getWriter();
//            out.println("<HTML>");
//            out.println("<HEAD>");
//            out.println("<TITLE>");
//            out.println("Incorrect Password");
//            out.println("</TITLE>");
//            out.println("</HEAD>");
//            out.println("<BODY>");
//            out.println("<H1>Incorrect Password</H1>");
//            out.println("Sorry, that password was incorrect.");
//            out.println("</BODY>");
//            out.println("</HTML>");
//        }
    }

    @Override
    public void destroy() {

    }
}
