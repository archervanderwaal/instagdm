package top.archer.instagdm.request;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.IGPayload;
import com.github.instagram4j.instagram4j.requests.IGPostRequest;
import com.github.instagram4j.instagram4j.utils.IGUtils;
import lombok.Data;
import top.archer.instagdm.response.CreateGroupThreadsResponse;

import java.util.Arrays;

public class DirectCreateGroupThreadRequest extends IGPostRequest<CreateGroupThreadsResponse> {
    private final String title;
    private final String[] userIds;

    public DirectCreateGroupThreadRequest(String title, String... user_ids) {
        this.title = title;
        this.userIds = user_ids;
    }

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new DirectCreateGroupThreadPayload();
    }

    @Override
    public String path() {
        return "direct_v2/create_group_thread/";
    }

    @Override
    public Class<CreateGroupThreadsResponse> getResponseType() {
        return CreateGroupThreadsResponse.class;
    }

    @Data
    public class DirectCreateGroupThreadPayload extends IGPayload {
        private String recipient_users = IGUtils.objectToJson(Arrays.asList(userIds));
        private String thread_title = title;
    }
}
