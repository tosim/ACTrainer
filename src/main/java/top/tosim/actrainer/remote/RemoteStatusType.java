package top.tosim.actrainer.remote;

public enum RemoteStatusType {
    PENDING(false),
    SUBMIT_FAILED(true),
    SUBMITTED(false),
    QUEUEING(false),
    COMPILING(false),
    JUDGING(false),
    AC(true),
    PE(true),
    WA(true),
    TLE(true),
    MLE(true),
    OLE(true),
    RE(true),
    CE(true),
    FAILED_OTHER(true),
    ;
    private boolean finalized;
    RemoteStatusType(boolean finalized) {
        this.finalized = finalized;
    }
    public boolean isFinal(){//判断这个状态是否完成
        return this.finalized;
    }
}