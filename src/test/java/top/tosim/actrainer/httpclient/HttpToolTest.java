package top.tosim.actrainer.httpclient;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class HttpToolTest {
    @Test
    public void testGet(){
        //String content = HttpTool.get("http://acm.hdu.edu.cn/status.php");
        //System.out.println(content);
    }
    @Test
    public void testPostForm(){
        Map<String,String> formMap = new HashMap<String, String>();
        formMap.put("check","0");
        formMap.put("problemid","1000");
        formMap.put("language","0");
        formMap.put("usercode","#include<stdio.h>\n" + "int main()\n" + "{\n" + "    int a,b;\n" + "    while(scanf(\"%d %d\",&a,&b) != EOF)\n" + "        printf(\"%d\\n\",a + b);\n" + "    return 0;\n" + "}");
        Map<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Host","acm.hdu.edu.cn");
        headerMap.put("Referer","http://acm.hdu.edu.cn/");
        headerMap.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        headerMap.put("Cookie","PHPSESSID=761s8m3oadoa79ucaaqdse30b4; path=/");
        //HttpTool.postForm("http://acm.hdu.edu.cn/submit.php?action=submit",formMap,headerMap,"gb2312");
    }

    @Test
    public void testLoginForm(){
        Map<String,String> formMap = new HashMap<String, String>();
        formMap.put("username","Firemann");
        formMap.put("userpass","056210");
        formMap.put("login","Sign In");
        //formMap.put("usercode","#include<stdio.h>\n" + "int main()\n" + "{\n" + "    int a,b;\n" + "    while(scanf(\"%d %d\",&a,&b) != EOF)\n" + "        printf(\"%d\\n\",a + b);\n" + "    return 0;\n" + "}");
        Map<String,String> headerMap = new HashMap<String, String>();
        headerMap.put("Host","acm.hdu.edu.cn");
        headerMap.put("Referer","http://acm.hdu.edu.cn/");
        headerMap.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        //headerMap.put("Cookie","cbc8a_c_stamp=1511076564; cbc8a_lastpos=index; cbc8a_lastvisit=0%091511076564%09%2Fforum%2Findex.php; cbc8a_ol_offset=1358; cbc8a_ipstate=1511076564; PHPSESSID=jh7gsd0o86itfhgubldpnck7o5; td_cookie=2582350712; exesubmitlang=0");
       // HttpTool.postForm("http://acm.hdu.edu.cn/userloginex.php?action=login",formMap,headerMap,"gb2312");
    }
}
