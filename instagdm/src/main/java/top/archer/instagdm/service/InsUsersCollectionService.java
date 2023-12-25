package top.archer.instagdm.service;


import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.friendships.FriendshipsFeedsRequest;
import org.springframework.stereotype.Service;
import top.archer.instagdm.model.InsUsers;

import java.util.List;

@Service
public class InsUsersCollectionService {

    public List<InsUsers> queryInsUsers(IGClient client, long sourcePk) {
        FriendshipsFeedsRequest request = new FriendshipsFeedsRequest(sourcePk, FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWERS);
//        request.execute(client).get();
        return null;
    }
}
