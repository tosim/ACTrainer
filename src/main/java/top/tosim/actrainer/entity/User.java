package top.tosim.actrainer.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String accountName;

    private String password;

    private String nickName;

    private String school;

    private String email;

    private Integer gender;
    private String icon;
    private String description;

    private Integer acCount;
    private Integer failCount;

}