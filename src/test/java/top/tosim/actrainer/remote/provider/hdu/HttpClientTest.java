package top.tosim.actrainer.remote.provider.hdu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import top.tosim.actrainer.dto.SubmissionStatus;
import top.tosim.actrainer.httpclient.HttpTool;
import top.tosim.actrainer.remote.RemoteStatusNormalizer;
import top.tosim.actrainer.remote.RemoteStatusType;
import top.tosim.actrainer.tool.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientTest {
    @Test
    public void testHttpClient(){
        String loginCookie = HDUHelper.login("Firemann","056210");
        System.out.println(loginCookie);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("check","0"));
        formParams.add(new BasicNameValuePair("problemid","1000"));
        formParams.add(new BasicNameValuePair("language","0"));
        formParams.add(new BasicNameValuePair("usercode","#include<stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "    int a,b;\n" +
                "    while(scanf(\"%d %d\",&a,&b) != EOF)\n" +
                "        printf(\"%d\\n\",a + b);\n" +
                "    return 0;\n" +
                "}"));
        HDUHelper.getClient().postForm(HDUHelper.getSubmitUrl(),formParams,loginCookie);
    }

    private SubmissionStatus any(String html,String runId){
        Pattern pattern = Pattern.compile(">" + runId + "</td><td>[\\s\\S]*?</td><td>([\\s\\S]*?)</td><td>[\\s\\S]*?</td><td>(\\d*?)MS</td><td>(\\d*?)K</td>");
        Matcher matcher = pattern.matcher(html);
        //Validate.isTrue(matcher.find());
        if(matcher.find() == false) return null;
        SubmissionStatus status = new SubmissionStatus();
        //status.setStatusType(RemoteStatusType.PENDING);
        //System.out.println("RawStatus = " + matcher.group(1));
        status.setRawStatus(matcher.group(1).replaceAll("<[\\s\\S]*?>", "").trim());
        status.setStatusType(RemoteStatusNormalizer.DEFAULT.getStatusType(status.getRawStatus()));

        if (status.getStatusType() == RemoteStatusType.AC) {
            status.setExecutionTime(Integer.parseInt(matcher.group(2)));
            status.setExecutionMemory(Integer.parseInt(matcher.group(3)));
        } else if (status.getStatusType() == RemoteStatusType.CE) {
            html = HttpTool.get("/viewerror.php?rid=" + runId);
            status.setCompilationErrorInfo(Tools.regFind(html, "(<pre>[\\s\\S]*?</pre>)"));
        }
        return status;
    }
}
