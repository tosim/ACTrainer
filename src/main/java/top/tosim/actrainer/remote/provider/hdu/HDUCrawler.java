package top.tosim.actrainer.remote.provider.hdu;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.tosim.actrainer.dao.ProblemDao;
import top.tosim.actrainer.entity.Problem;
import top.tosim.actrainer.httpclient.HttpTool;
import top.tosim.actrainer.remote.RemoteOJ;
import top.tosim.actrainer.tool.HtmlHandleUtil;
import top.tosim.actrainer.tool.Tools;

import java.util.ArrayList;
import java.util.List;


public class HDUCrawler{
    private static Integer getMaxProblemId(){
        String html = HttpTool.get("http://acm.hdu.edu.cn/listproblem.php?vol=1");
        try{
            Integer vol = Integer.parseInt(Tools.regFind(html, "style=\"margin:5px\">(\\d+)</a></font>").trim());
            //System.out.println("vol = " + vol);
            html = HttpTool.get("http://acm.hdu.edu.cn/listproblem.php?vol="+vol);
            //System.out.println(html);
            String tmp = Tools.regFind(html,",(\\d+),-?\\d+,\"[\\w ]+?\",-?\\d+,-?\\d+\\);</script></table>").trim();
            //System.out.println("string id = " + s);
            Integer maxProblemId = Integer.parseInt(tmp);
            return maxProblemId;
        }catch (Exception e){
            return 6240;
            //e.printStackTrace();
        }
    }
    private static Logger log = LoggerFactory.getLogger(HDUCrawler.class);

    public static void crawlAll(ProblemDao problemDao,boolean isUpdate){
        Integer maxProblemId = getMaxProblemId();
        //log.info("HDUmaxProblem = " + maxProblemId);
        //System.out.println("HDUmaxProblem = " + maxProblemId);
        //List<Problem> problemList = new ArrayList<Problem>();
        if(isUpdate){
            for(int i = 1000;i <= maxProblemId;i++){
                log.info("crawling " + i);
                problemDao.updateByRemoteOjAndProblemId(crawl(i+""));
                log.info("update " + i + "\n");
            }
        }else{
            for(int i = 1000;i <= maxProblemId;i++){
                log.info("crawling " + i);
                problemDao.insertSelective(crawl(i+""));
                log.info("save " + i + "\n");
            }
        }

    }

    public static Problem crawl(String remoteProblemId) {
        Problem problem = new Problem();
        String html = HDUHelper.getClient().get(HDUHelper.getProblemUrl()+"?pid="+remoteProblemId);
        log.debug(html);
        //需要再document转换前抓，不然换行会被舍去
        problem.setHint(Tools.regFind(html,"<i>Hint</i></div>([\\s\\S]*?)</div><i[^<>]*?>"));
        if (!StringUtils.isEmpty(problem.getHint())){
            problem.setHint("<pre>" + problem.getHint() + "</pre>");
        }

        String problemUrl = HDUHelper.getProblemUrl()+"?pid="+remoteProblemId;
        html = HtmlHandleUtil.transformUrlToAbs(html, problemUrl);//转换图片等的url的相对地址到绝对地址
        log.debug(html);

        problem.setRemoteOj(RemoteOJ.HDU.name());
        problem.setRemoteProblemId(remoteProblemId);
        problem.setUrl(problemUrl);

        problem.setTitle(Tools.regFind(html, "color:#1A5CC8\">([\\s\\S]*?)</h1>").trim());
        problem.setTimeLimit(Tools.regFind(html, "Time Limit: ([\\s\\S]*?)&nbsp;"));
        problem.setMemoryLimit(Tools.regFind(html, "Memory Limit: ([\\s\\S]*?)<br />"));
        problem.setAuthor(Tools.regFind(html,"Author</div><div class=\"panel_content\">([\\s\\S]*?)</div>"));
        problem.setDescription(Tools.regFind(html, ">Problem Description</div><div class=\"panel_content\">([\\s\\S]*?)(<br />)?</div><div class=\"panel_bottom\">"));
        problem.setInput(Tools.regFind(html, ">Input</div><div class=\"panel_content\">([\\s\\S]*?)<br /></div><div class=\"panel_bottom\">"));
        problem.setOutput(Tools.regFind(html, ">Output</div><div class=\"panel_content\">([\\s\\S]*?)<br /></div><div class=\"panel_bottom\">"));
        problem.setSource(Tools.regFind(html, "Source</div><div class=\"panel_content\">([\\s\\S]*?)<[^<>]*?panel_[^<>]*?>").replaceAll("<[\\s\\S]*?>", ""));
        problem.setSampleInput("<pre>"+Tools.regFind(html, ">Sample Input</div><div class=\"panel_content\"><pre><div[^<>]*?>([\\s\\S]*?)</div></pre>") + "</pre>");
        //System.out.println(html);
        problem.setSampleOutput("<pre>" + Tools.regFind(html, ">Sample Output</div><div class=\"panel_content\"><pre><div[^<>]*?>([\\s\\S]*?)(<div[^<>]*?><div[^<>]*?><i>Hint</i></div>[\\s\\S]*?</div><i[^<>]*?></i>)?</div></pre>") + "</pre>");

        return problem;
    }

    public static void main(String[] args){
        System.out.println(JSON.toJSONString(crawl(1083+"")));
    }
}
