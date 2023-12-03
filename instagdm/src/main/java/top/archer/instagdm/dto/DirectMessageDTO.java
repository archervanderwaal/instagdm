package top.archer.instagdm.dto;

import lombok.Data;

import java.util.List;

@Data
public class DirectMessageDTO {
    private String username;
    private String password;
    private String message;
    private List<String> receivers;
    private List<String> threadIds;
}
