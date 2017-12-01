package top.tosim.actrainer.dto;

public class PicConfirmData {
    private String oldName;
    private String newName;
    private String path;

    public PicConfirmData(){}
    public PicConfirmData(String oldName,String newName,String path){
        this.oldName = oldName;
        this.newName = newName;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
