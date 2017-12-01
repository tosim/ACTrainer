package top.tosim.actrainer.config.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.tosim.actrainer.dao.SubmissionDao;
import top.tosim.actrainer.dao.UserDao;
import top.tosim.actrainer.remote.provider.hdu.HDUQuerier;
import top.tosim.actrainer.remote.provider.hdu.HDUSubmitter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/*
*用于初始化各个OJ的提交账号的线程
*
* */
@Component
@PropertySource({"classpath:accounts.properties"})
public class OJWorkerInitializer {
    @Autowired
    private SubmissionDao submissionDao;
    @Autowired
    private UserDao userDao;

    @Value("${HDU.userNames}")
    private List<String> HDU_userNames;

    @Value("${HDU.passwords}")
    private List<String> HDU_password;

    @PostConstruct
    public void init(){
        HDU_userNames = Arrays.asList(HDU_userNames.get(0).split(";"));
        HDU_password = Arrays.asList(HDU_password.get(0).split(";"));

//        initSubmitter();
//        initQuerier();
    }

    public void initSubmitter(){
        //System.out.println("SubmitterInitializer initMethod performed!");

        for(int i = 0;i < HDU_userNames.size();i++){
            System.out.println("userName = " + HDU_userNames.get(i) + "  password = " + HDU_password.get(i));
            new Thread(new HDUSubmitter(HDU_userNames.get(i),HDU_password.get(i),submissionDao,userDao)).start();
            System.out.println("submitter[" + HDU_userNames.get(i) + "] started!");
        }
    }
    public void initQuerier(){
        new Thread(new HDUQuerier(submissionDao,userDao)).start();
    }
}
