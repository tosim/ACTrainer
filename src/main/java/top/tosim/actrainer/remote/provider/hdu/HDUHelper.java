package top.tosim.actrainer.remote.provider.hdu;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import top.tosim.actrainer.httpclient.DedicatedHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HDUHelper {
    private final static String problemUrl = "http://acm.hdu.edu.cn/showproblem.php";//?pid=        //题目详情url
    private static final String loginUrl = "http://acm.hdu.edu.cn/userloginex.php?action=login";    //登录url
    private static final String statusPageUrl = "http://acm.hdu.edu.cn/status.php"; //+ realRunId   //运行状态查询页url
    private static final String submitUrl = "http://acm.hdu.edu.cn/submit.php?action=submit";       //提交题目url

    private static  Map<String,String> languageMap = new HashMap<String, String>();                 //提交的语言代码对应的语言
    private final static String encoding = "gb2312";                                                //网站编码
    private static DedicatedHttpClient client;                                                      //网络请求对象

    //初始化语言映射
    //初始化网络请求对象（初始化请求头）
    static {
        languageMap.put("G++", "0");
        languageMap.put("GCC", "1");
        languageMap.put("C++", "2");
        languageMap.put("C", "3");
        languageMap.put("Pascal", "4");
        languageMap.put("Java", "5");
        languageMap.put("C#", "6");
        Header[] headers = {
                new BasicHeader("Host","acm.hdu.edu.cn"),
                new BasicHeader("Referer","http://acm.hdu.edu.cn/"),
                new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"),
        };
        client = new DedicatedHttpClient(headers,encoding);
    }
    private HDUHelper() {
    }

    public static DedicatedHttpClient getClient() {
        return client;
    }

    public static String login(String userName,String password){
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("username",userName));
        formParams.add(new BasicNameValuePair("userpass",password));
        formParams.add(new BasicNameValuePair("login","Sign In"));
        String cookie = client.postAndGetCookie(loginUrl,formParams);
        return cookie;
    }

    public static Map<String,String> getlanguageMap(){
        return languageMap;
    }

    public static String getEncoding(){
        return encoding;
    }

    public static String getStatusPageUrl() {
        return statusPageUrl;
    }

    public static String getSubmitUrl() {
        return submitUrl;
    }

    public static String getProblemUrl() {
        return problemUrl;
    }
}
