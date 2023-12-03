package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import kotlin.collections.ArrayDeque;
import org.springframework.stereotype.Service;
import top.archer.instagdm.request.DirectCreateGroupThreadRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class LoginService {

    public Optional<IGClient> login(String username, String passwd) {
        try {
            return Optional.of(IGClient.builder().username(username).password(passwd).login());
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        Optional<IGClient> opClient = loginService.login("archervanderwaal", "ArcherVanderWaal0221@");
        assert opClient.isPresent();
        List<String> userNames = new ArrayDeque<>();
        userNames.add("1231.nico");
        userNames.add("candydiamondx");
        userNames.add("xaviiii49");
        List<String> userIds = new ArrayList<>();
        userNames.forEach(uName ->
                {
                    try {
                        userIds.add(String.valueOf(opClient.get().actions().users().findByUsername(uName).get().getUser().getPk()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        System.out.println(userIds);
        String[] res = userIds.toArray(new String[0]);
        new DirectCreateGroupThreadRequest("Happy", res).execute(opClient.get()).thenAccept(r -> {
            System.out.println(1);
            //thread_v2_id -> 17998780415465410
            //joinable_group_link -> https://ig.me/j/AbZKLrhFQmDOBxIM/
        }).join();
    }
}
