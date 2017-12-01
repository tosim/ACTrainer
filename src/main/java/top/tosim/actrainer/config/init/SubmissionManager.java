package top.tosim.actrainer.config.init;

import top.tosim.actrainer.entity.Submission;
import top.tosim.actrainer.remote.RemoteOJ;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SubmissionManager {
    private static Map<RemoteOJ,BlockingQueue<Submission>> submissionsQueueMap = new HashMap<RemoteOJ, BlockingQueue<Submission>>();
    private static Map<RemoteOJ,BlockingQueue<Submission>> runingSubmissionsQueueMap = new HashMap<RemoteOJ, BlockingQueue<Submission>>();

    //submissionsQueues
    private static BlockingQueue<Submission> submissionsQueue_HDU = new LinkedBlockingQueue<Submission>();

    //runingSubmissionsQueues
    private static BlockingQueue<Submission> runingSubmissionsQueue_HUD = new LinkedBlockingQueue<Submission>();

    static {
        submissionsQueueMap.put(RemoteOJ.HDU,submissionsQueue_HDU);


        runingSubmissionsQueueMap.put(RemoteOJ.HDU,runingSubmissionsQueue_HUD);
    }
    public static Submission takeSubmission(RemoteOJ remoteOJ){
        try{
            return submissionsQueueMap.get(remoteOJ).take();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Submission takeQuerySubmission(RemoteOJ remoteOJ){
        try{
            return runingSubmissionsQueueMap.get(remoteOJ).take();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void putSubmission(RemoteOJ remoteOJ,Submission submission){
        try{
            submissionsQueueMap.get(remoteOJ).put(submission);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void putQuerySubmission(RemoteOJ remoteOJ,Submission submission){
        try{
            runingSubmissionsQueueMap.get(remoteOJ).put(submission);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
