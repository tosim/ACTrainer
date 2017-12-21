package top.tosim.actrainer.dto;

import java.util.List;

public class ExcelDto {
    List<String> title;

    List<List<String>> values;

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<List<String>> getValues() {
        return values;
    }

    public void setValues(List<List<String>> values) {
        this.values = values;
    }
}
