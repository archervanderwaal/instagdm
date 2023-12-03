package top.archer.instagdm.dto;

import lombok.Data;

import java.util.List;

@Data
public class ThreadsCreateGroupDTO {
    private String username;
    private String password;
    private String title;
    private List<String> inviters;
}
