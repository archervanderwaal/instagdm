package top.archer.instagdm.service;

import com.github.instagram4j.instagram4j.IGClient;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class InsApiService {

    public CompletableFuture<Set<Long>> queryUserIds(IGClient client, List<String> userNames) {
        List<CompletableFuture<Long>> allFutures = userNames.stream()
                .map(client.actions().users()::findByUsername)
                .map(f -> f.thenApply(u -> u.getUser().getPk())).toList()
                .stream().toList();
        return CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[]{})).thenApply(v -> {
            Set<Long> result = new HashSet<>();
            allFutures.forEach(f -> result.add(f.join()));
            return result;
        });
    }
}
