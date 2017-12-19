package top.tosim.actrainer.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageSelectDto {
    Logger log = LoggerFactory.getLogger(PageSelectDto.class);
    private Integer page;
    private Integer size;
    private Integer start;
    public void validateAndCalculateStart(Integer defaultSize){
        if(null == this.page) this.page = new Integer(1);
        if(null == this.size) this.size = defaultSize;
        log.info("page = " + this.page + " and size = " + this.size);
        this.start = (this.page-1)*this.size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
