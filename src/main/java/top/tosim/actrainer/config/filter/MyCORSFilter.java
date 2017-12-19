package top.tosim.actrainer.config.filter;


import com.thetransactioncompany.cors.CORSFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName="myCORSFilter", urlPatterns="/*")
public class MyCORSFilter extends CORSFilter{
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) resp;
//        HttpServletRequest request = (HttpServletRequest) req;
//        String method = request.getMethod();
//        if(method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELETE") || method.equalsIgnoreCase("OPTIONS")){
//            System.out.println("get put request!!");
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//            chain.doFilter(req, resp);
//        }else {
//            super.doFilter(req, resp, chain);
//        }
        super.doFilter(req, resp, chain);
    }
}

