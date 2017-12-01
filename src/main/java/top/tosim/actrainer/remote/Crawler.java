package top.tosim.actrainer.remote;

import top.tosim.actrainer.entity.Problem;

public interface Crawler {
    public Problem crawl(String remoteProblemId);
}
