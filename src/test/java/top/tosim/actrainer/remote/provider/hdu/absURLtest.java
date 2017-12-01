package top.tosim.actrainer.remote.provider.hdu;

import org.jsoup.helper.Validate;
import org.junit.Test;
import top.tosim.actrainer.httpclient.HttpTool;
import top.tosim.actrainer.tool.Tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class absURLtest {
    @Test
    public void testAbs(){
        String html = "<a href=\"abc\"/>\n<a href=\"/\"/>\n<a href=\"/\"/>\n<a href=\"/\">\n<a href=\"/\" targt=\"_blank\">\n<a href=\"/\" targt=\"_blank\"/>";
        //String html = HDUHelper.getClient().get( HDUHelper.getProblemUrl()+"?pid="+2600 );
        String baseUrl = "http://acm.hdu.edu.cn/showproblem.php?pid=2600";
        Matcher m = Pattern.compile("<[^>]+?((href)|(src))=\"((?!http)[^\">]*?)\"[^>]*?/?>").matcher(html);
        while(m.find()){
            String origin = m.group(4);
            System.out.println(m.start() + "\n");
            if(!origin.equals("")){
                html = html.replace(origin,absUrl(origin,baseUrl));
            }else{
            }
//            System.out.println(html+"\n");
        }
//        System.out.println(html);
    }


    public String absUrl(String relUrl,String baseUri) {
        try {
            URL base;
            try {
                base = new URL(baseUri);
            } catch (MalformedURLException var6) {
                URL abs = new URL(relUrl);
                return abs.toExternalForm();
            }
            if (relUrl.startsWith("?")) {
                relUrl = base.getPath() + relUrl;
            }
            URL abs = new URL(base, relUrl);
            return abs.toExternalForm();
        } catch (MalformedURLException var7) {
            return "";
        }
    }
}
