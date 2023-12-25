package top.archer.instagdm.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DirectMessageDTO {
    private String username;
    private String password;
    private String message;
    private List<String> receivers;
    private List<String> threadIds;
}
