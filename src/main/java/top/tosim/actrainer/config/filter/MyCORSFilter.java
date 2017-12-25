package top.tosim.actrainer.config.filter;


import com.thetransactioncompany.cors.CORSFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName="myCORSFilter", urlPatterns="/*")
public class MyCORSFilter extends CORSFilter{
}

