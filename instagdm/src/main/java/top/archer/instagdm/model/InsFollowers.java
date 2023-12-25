package top.archer.instagdm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InsFollowers {
    private String user;
    private String follower;
    private long followerId;
}
