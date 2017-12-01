package top.tosim.actrainer.dto;

public class UserPageSelectDto extends PageSelectDto{
    private String accountName;

    @Override
    public void validateAndCalculateStart(Integer defaultSize) {
        super.validateAndCalculateStart(defaultSize);
        if(this.accountName == null || this.accountName.equals("")) this.accountName = null;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
