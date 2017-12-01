package top.tosim.actrainer.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;


import javax.servlet.ServletContext;

@Component
@Lazy(false)
public class InitUserList implements ServletContextAware{
    //private static ServletContext servletContext;
    //@Autowired
    //private UserDao userDao;

//    @Autowired
//    SubmitterInitializer submitterInitializer;
    public void setServletContext(ServletContext servletContext) {
       // System.out.println("submitterInitializer okokd ");
        //submitterInitializer.init();
    }
}