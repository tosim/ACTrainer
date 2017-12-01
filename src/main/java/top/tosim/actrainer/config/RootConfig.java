package top.tosim.actrainer.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import top.tosim.actrainer.tool.SpringContextUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"top.tosim.actrainer"},excludeFilters = {@Filter(type = FilterType.ANNOTATION,value = EnableWebMvc.class)})//包扫描，排除mvc组件
//@Import(DataSourceConfig.class)//加载数据源配置
public class RootConfig {
    //配置aop自动代理
    /*
    @Bean
    public BeanNameAutoProxyCreator proxycreate(){
        BeanNameAutoProxyCreator proxycreate = new BeanNameAutoProxyCreator();
        proxycreate.setProxyTargetClass(true);
        proxycreate.setBeanNames("*ServiceImpl");
        proxycreate.setInterceptorNames("transactionInterceptor");
        return proxycreate;
    }*/

}
