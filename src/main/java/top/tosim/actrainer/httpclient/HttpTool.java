package top.tosim.actrainer.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpTool {
    public static CloseableHttpResponse postForm(String url,Map<String,String> formMap,Map<String,String> headerMap,String ecoding){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            // 创建参数队列
            List<NameValuePair> formList = new ArrayList<NameValuePair>();
            if(formMap!=null){
                for (Map.Entry<String, String> entry : formMap.entrySet()) {
                    formList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            if(headerMap!=null){
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    httppost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            //参数转码
            httppost.setEntity(new UrlEncodedFormEntity(formList, ecoding));
            //CloseableHttpResponse response = httpclient.execute(httppost);
            //System.out.println(response.getHeaders("set-cookie")[0]);
            return httpclient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
        return null;
    }
    public static String get(String url){
        String content;
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            HttpEntity entity = httpclient.execute(httpget).getEntity();
            content =  EntityUtils.toString(entity);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
        return null;
    }
}
