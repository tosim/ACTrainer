package top.tosim.actrainer.dto;

import java.util.Date;

public class ContestPageSelectDto extends PageSelectDto{
    private String title;
    private String accountName;
    private String status;
    private Integer contestType;

    @Override
    public void validateAndCalculateStart(Integer defaultSize) {
        super.validateAndCalculateStart(defaultSize);
        if(this.title == null || this.title.equals("")) this.title = null;
        if(this.accountName == null || this.accountName.equals("")) this.accountName = null;
        if(this.status == null || this.status.equals("")) this.status = null;
        if(this.contestType == null || this.contestType.equals(-1)) this.contestType = null;
    }

    public Integer getContestType() {
        return contestType;
    }

    public void setContestType(Integer contestType) {
        this.contestType = contestType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
