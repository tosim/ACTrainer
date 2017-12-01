package top.tosim.actrainer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("top.tosim.actrainer.controller")//扫描mvc组件（controller）
public class WebConfig extends WebMvcConfigurerAdapter{
    @Bean
    public MappingJackson2HttpMessageConverter jsonHttpMessageConverter(){
        return new MappingJackson2HttpMessageConverter();
    }
    @Bean
    CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20*1024*1024);//文件总大小 //20M
        multipartResolver.setMaxUploadSizePerFile(10*1024*1024);//单个文件大小    //10M
        multipartResolver.setDefaultEncoding("utf-8");//文件编码
        return multipartResolver;
    }
    //@Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(MappingJackson2HttpMessageConverter jsonHttpMessageConverter){
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        List list = new ArrayList();
        list.add(jsonHttpMessageConverter);
        adapter.setMessageConverters(list);
        return adapter;
    }
    @Bean
    //配置JSP视图解析器
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    //配置静态资源处理
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
