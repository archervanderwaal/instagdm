package top.archer.instagdm.request;

import com.github.instagram4j.instagram4j.requests.IGGetRequest;
import com.github.instagram4j.instagram4j.responses.IGResponse;

public class WeiboInfoQueryRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return null;
    }

    @Override
    public Class getResponseType() {
        return null;
    }
}
