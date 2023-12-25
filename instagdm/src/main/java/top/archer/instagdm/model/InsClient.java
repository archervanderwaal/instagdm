package top.archer.instagdm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsClient {
    private long id;
    private String username;
    private String password;
    private byte[] client;
    private byte[] cookieJar;
}
