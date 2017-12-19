package top.tosim.actrainer.dto;

import lombok.Data;

@Data
public class RespJson {
    private Integer success = 0;
    private String msg = "";
    private Object obj = null;
}
