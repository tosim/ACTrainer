package top.tosim.actrainer.config.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import top.tosim.actrainer.config.RootConfig;
import top.tosim.actrainer.dao.ProblemDao;
import top.tosim.actrainer.remote.provider.hdu.HDUCrawler;

public class ProblemCrawlerAndSaver {
    private static Logger log = LoggerFactory.getLogger(ProblemCrawlerAndSaver.class);

    public static void crawlAndSave(ProblemDao problemDao){
        HDUCrawler.crawlAll(problemDao,true);
    }

    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);//
        ProblemDao problemDao = context.getBean(ProblemDao.class);
        crawlAndSave(problemDao);
    }
}
