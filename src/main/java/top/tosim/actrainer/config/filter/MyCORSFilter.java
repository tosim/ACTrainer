package top.tosim.actrainer.config.filter;


import com.thetransactioncompany.cors.CORSFilter;

import javax.servlet.annotation.WebFilter;


@WebFilter(filterName="myCORSFilter", urlPatterns="/*")
public class MyCORSFilter extends CORSFilter{
}
