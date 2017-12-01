package top.tosim.actrainer.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DedicatedHttpClient {
    private PoolingHttpClientConnectionManager cm = null;
    private Header[] defaultHeaders = null;
    private String encode;

    public void init() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(200);
    }
    public DedicatedHttpClient(Header[] headers,String encode){
        this.defaultHeaders = headers;
        this.encode = encode;
        init();
    }


    private CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }
    public String postForm(String url, List<NameValuePair> formParams,String cookie,String encoding){
        CloseableHttpResponse response = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encoding));
            httpPost.setHeaders(defaultHeaders);
            httpPost.setHeader("Cookie",cookie);
            response = getHttpClient().execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, encoding);

            EntityUtils.consume(entity);//
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(null != response) response.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    public String postForm(String url, List<NameValuePair> formParams,String cookie){
        return postForm(url,formParams,cookie,encode);
    }

    public String postAndGetCookie(String url, List<NameValuePair> formParams,String encoding){
        CloseableHttpResponse response = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encoding));
            httpPost.setHeaders(defaultHeaders);
            //httpPost.setHeader("Cookie",cookie);
            response = getHttpClient().execute(httpPost);
            String loginCookie = response.getHeaders("set-cookie")[0].toString();

            EntityUtils.consume(response.getEntity());//
            return loginCookie.split(": ")[1];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(null != response) response.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    public String postAndGetCookie(String url, List<NameValuePair> formParams){
        return postAndGetCookie(url,formParams,encode);
    }

    public String get(String url,String encoding){
        CloseableHttpResponse response = null;
        try{
            HttpGet httpGet = new HttpGet(url);
            response = getHttpClient().execute(httpGet);
            HttpEntity entity = response.getEntity();
            String content =  EntityUtils.toString(entity,encoding);
            EntityUtils.consume(entity);
            return content;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(null != response) response.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    public String get(String url){
        return get(url,encode);
    }
}
