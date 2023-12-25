package top.archer.instagdm.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.archer.instagdm.proxy.ProxyService;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WeiboSpider {

    @Autowired
    private ProxyService proxyService;

    private static final String BASE_URL_FORMAT = "https://weibo.com/ajax/friendships/friends?relate=fans&page=%d&uid=%s&type=fans";

    public List<Long> queryUidByKeyword(String keyWord) {
        try {
            int page = 0;
            List<Long> uids = queryUidByKeyword(keyWord, ++page);
            TimeUnit.MILLISECONDS.sleep(10);
            List<Long> result = new ArrayList<>(uids);
            while (!uids.isEmpty()) {
                uids = queryUidByKeyword(keyWord, ++page);
                result.addAll(uids);
            }
            return result;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private List<Long> queryUidByKeyword(String keyWord, int page) {
        try {
            String url = "https://weibo.com/ajax/search/all?containerid=100103type%3D3%26q%3D" + URLEncoder.encode(keyWord) + "%26t%3D2&page=" + page;
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Cookie", "SINAGLOBAL=5370928478514.693.1693291872695; _s_tentry=-; Apache=5708136138220.918.1701679249145; ULV=1701679249155:2:1:1:5708136138220.918.1701679249145:1693291872698; UOR=,,www.google.com; XSRF-TOKEN=y03BYbZ-hD2CavIUbODzgmOs; SUB=_2A25IadErDeRhGeFG7VoS9CjFyD6IHXVrB2zjrDV8PUNbmtAGLVenkW9NeRryU5ZIFfQufy1JGyErwClxQCia5dUq; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWhTdOxJTidmuog4bWdBwBz5JpX5o275NHD95QN1hqRe0Bc1KeEWs4DqcjMi--NiK.Xi-2Ri--ciKnRi-zNS0nc1heXSo.0entt; ALF=1702288380; SSOLoginState=1701683579; WBPSESS=kH06LWg8LQ5SUc6adXGpxMp7w-OGSLIq7fiieWlKEZ-MzXdLZhC8QkjOPKbrzIEHgI_BC-zxqwJiH7-wmmWOFvZVKNk64ZK9mg4GNv6bxZS8vIOfU-7Hl0xfm4bO5bC-8AWw03oC5aJIos-KNg7oog==")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36")
                    .get()
                    .build();
            Response response = proxyService.createClient().newCall(request).execute();
            List<Long> result = new ArrayList<>();
            try (ResponseBody body = response.body()) {
                if (Objects.nonNull(body)) {
                    String str = body.string();
                    JSONArray cards = JSON.parseObject(str).getJSONObject("data").getJSONArray("cards");
                    if (cards != null) {
                        JSONArray cardGroup = ((JSONObject) cards.get(0)).getJSONArray("card_group");
                        if (cardGroup != null) {
                            cardGroup.forEach(group -> {
                                JSONObject user = ((JSONObject) group).getJSONObject("user");
                                long userId = user.getLong("id");
                                long followers_count = user.getLong("followers_count");
                                if (followers_count > 200000) {
                                    result.add(userId);
                                }
                            });
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Long> queryFansByUid(String uid) {
        int page = 0;
        List<Long> fans = queryFansByUid(uid, ++page);
        List<Long> result = new ArrayList<>(fans);
        try {
            while (!fans.isEmpty()) {
                fans = queryFansByUid(uid, ++page);
                result.addAll(fans);
                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (Exception ignored) {
        }
        return result;
    }

    private List<Long> queryFansByUid(String uid, int page) {
        try {
            String url = String.format(BASE_URL_FORMAT, page, uid);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Cookie", "SINAGLOBAL=5370928478514.693.1693291872695; _s_tentry=-; Apache=5708136138220.918.1701679249145; ULV=1701679249155:2:1:1:5708136138220.918.1701679249145:1693291872698; UOR=,,www.google.com; XSRF-TOKEN=y03BYbZ-hD2CavIUbODzgmOs; SUB=_2A25IadErDeRhGeFG7VoS9CjFyD6IHXVrB2zjrDV8PUNbmtAGLVenkW9NeRryU5ZIFfQufy1JGyErwClxQCia5dUq; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWhTdOxJTidmuog4bWdBwBz5JpX5o275NHD95QN1hqRe0Bc1KeEWs4DqcjMi--NiK.Xi-2Ri--ciKnRi-zNS0nc1heXSo.0entt; ALF=1702288380; SSOLoginState=1701683579; WBPSESS=kH06LWg8LQ5SUc6adXGpxMp7w-OGSLIq7fiieWlKEZ-MzXdLZhC8QkjOPKbrzIEHgI_BC-zxqwJiH7-wmmWOFvZVKNk64ZK9mg4GNv6bxZS8vIOfU-7Hl0xfm4bO5bC-8AWw03oC5aJIos-KNg7oog==")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36")
                    .get()
                    .build();
            Response response = proxyService.createClient().newCall(request).execute();
            List<Long> result = new ArrayList<>();
            try (ResponseBody body = response.body()) {
                if (Objects.nonNull(body)) {
                    String str = body.string();
                    JSONArray users = JSON.parseObject(str).getJSONArray("users");
                    if (users == null) {
                        return Collections.emptyList();
                    }
                    users.forEach(obj -> result.add(((JSONObject) obj).getLong("id")));
                }
            }
            return result;
        } catch (Throwable e) {
            return Collections.emptyList();
        }
    }

    public Set<String> queryFollowListByUid(String uid) {
        int page = 0;
        Set<String> follows = queryFollowListByUid(uid, ++page);
        Set<String> result = new HashSet<>(follows);
        try {
            while (!follows.isEmpty()) {
                follows = queryFollowListByUid(uid, ++page);
                result.addAll(follows);
            }
        } catch (Exception ignored) {
        }
        return result;
    }

    private Set<String> queryFollowListByUid(String uid, int page) {
        try {
            String url = String.format("https://weibo.com/ajax/friendships/friends?page=%d&uid=%s", page, uid);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Cookie", "SINAGLOBAL=5370928478514.693.1693291872695; _s_tentry=-; Apache=5708136138220.918.1701679249145; ULV=1701679249155:2:1:1:5708136138220.918.1701679249145:1693291872698; UOR=,,www.google.com; XSRF-TOKEN=y03BYbZ-hD2CavIUbODzgmOs; SUB=_2A25IadErDeRhGeFG7VoS9CjFyD6IHXVrB2zjrDV8PUNbmtAGLVenkW9NeRryU5ZIFfQufy1JGyErwClxQCia5dUq; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWhTdOxJTidmuog4bWdBwBz5JpX5o275NHD95QN1hqRe0Bc1KeEWs4DqcjMi--NiK.Xi-2Ri--ciKnRi-zNS0nc1heXSo.0entt; ALF=1702288380; SSOLoginState=1701683579; WBPSESS=kH06LWg8LQ5SUc6adXGpxMp7w-OGSLIq7fiieWlKEZ-MzXdLZhC8QkjOPKbrzIEHgI_BC-zxqwJiH7-wmmWOFvZVKNk64ZK9mg4GNv6bxZS8vIOfU-7Hl0xfm4bO5bC-8AWw03oC5aJIos-KNg7oog==")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36")
                    .get()
                    .build();
            Response response = proxyService.createClient().newCall(request).execute();
            Set<String> result = new HashSet<>();
            try (ResponseBody body = response.body()) {
                if (Objects.nonNull(body)) {
                    String str = body.string();
                    JSONArray users = JSON.parseObject(str).getJSONArray("users");
                    users.forEach(u -> {
                        String name = ((JSONObject) u).getString("name");
                        if (StringUtils.isNotBlank(name)) {
                            result.add(name);
                        }
                    });
                }
            }
            return result;
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }
}
