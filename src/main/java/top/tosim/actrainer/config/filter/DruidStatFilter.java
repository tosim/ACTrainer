package top.tosim.actrainer.config.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName="druidFilter", urlPatterns="/*", initParams={
        @WebInitParam(name="exclusions", value="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
})
public class DruidStatFilter extends WebStatFilter {
}