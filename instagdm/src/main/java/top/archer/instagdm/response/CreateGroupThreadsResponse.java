package top.archer.instagdm.response;

import com.github.instagram4j.instagram4j.responses.IGResponse;
import lombok.Data;

@Data
public class CreateGroupThreadsResponse extends IGResponse {
    private String thread_v2_id;
    private String thread_title;
}
