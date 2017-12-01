package top.tosim.actrainer.config.init;

import top.tosim.actrainer.remote.RemoteOJ;
import top.tosim.actrainer.remote.provider.hdu.HDUHelper;

import java.util.HashMap;
import java.util.Map;

public class LanguageMapManager {
    private static Map<RemoteOJ,Map<String,String>> OJLanguageMaps = new HashMap<RemoteOJ, Map<String, String>>();
    static {
        OJLanguageMaps.put(RemoteOJ.HDU, HDUHelper.getlanguageMap());
    }
    public static Map<String,String> getLanguageMap(RemoteOJ remoteOJ){
        return OJLanguageMaps.get(remoteOJ);
    }
}
