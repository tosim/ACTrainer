package top.tosim.actrainer.remote.provider.hdu;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parttenTest {
    @Test
    public void testPartten(){
        String s = "aababaaabx<aaa";
        Matcher m = Pattern.compile("aa(a|b)bx").matcher(s);
        while(m.find()){
            System.out.println(m.group(0));
        }
    }
}
