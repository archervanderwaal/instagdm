package top.archer.instagdm.spider;

import com.alibaba.fastjson.JSON;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.archer.instagdm.data.Credit51Thread;
import top.archer.instagdm.proxy.ProxyService;

import java.util.Collections;
import java.util.List;

@Service
public class Credit51Spider {

    @Autowired
    private ProxyService proxyService;

    public List<Credit51Thread> queryThreads(int page) {
        Request request = new Request.Builder()
                .url(String.format("https://api.51credit.com/bbs/app/api/v1/thread/list?cityId=&fid=0&filter=0&location=&orderType=1&page=%d&pageSize=20&score=&typeId=0", page))
                .addHeader("token", "eyJhbGciOiJIUzUxMiJ9.eyJvcyI6MCwicGhvbmUiOiIxOTMqKioqMzk0MyIsImJic1VJZCI6Ijg5NzcwOTYiLCJleHAiOjE3MTc3NTQ0NDMsInVzZXJJZCI6MTIzNTA0MzgxMSwiaWF0IjoxNzAyMjAyNDQzLCJ1c2VybmFtZSI6Imthc2hlbjk3MjkzMjI0In0.yfvUPoj29KO-fLJ7wjuGUG5yt4jTXNiaHNSQs4kCJcapOUMt_dWjZ9nzA0BOgwh8R5_DLXh1Y-nDpf6kYz02NQ")
                .addHeader("x-wak-s", "7649f975de4c583e256f7bb62c43d0167e262548")
                .addHeader("User-Agent", "BBSAdminiOS/2.6.1 (iPhone; iOS 16.6.1; Scale/3.00) WakBrowser/2.5 device/iPhone14,7 wakbbs/2.6.1 (bbsappstore) wakos/0 waksite/wakbbs wakchannel/bbsappstore wakver/2.6.1 wakbuild/2.6.1 status/0")
                .addHeader("Host", "api.51credit.com")
                .addHeader("site", "wakbbs")
                .addHeader("channel", "bbsappstore")
                .addHeader("os", "0")
                .addHeader("device", "iPhone14,7")
                .addHeader("t", "1702209003525")
                .addHeader("Connection", "keep-alive")
                .addHeader("x-wak-c", "110100")
                .addHeader("version", "2.6.1")
                .addHeader("x-wak-u", "1235043811")
                .addHeader("x-wak-t", "1702209003526")
                .addHeader("x-wak-status", "0")
                .addHeader("Cookie", "https_waf_cookie=ebee05be-c6af-428147c9f3003cda27a5cf84868ae03111ac; smidV2=20231112010853b40b83f685c697c2228f3adbac0ca22f00acdd3d1eef1fae0; Hm_lvt_e06f837c19a278d81816c90560e0d82d=1699722532; clientkey=018328672F3FAC17921A4FD086E54BD3; ip_=123.113.109.176; sessionid=4B0AC8D52BF0AD8A16B48ECE754D055F")
                .addHeader("ticket", "")
                .addHeader("x-sm-id", "BpoTyhePqvvkU5UIMVGS2JYR8iJeqodpvjiq2rivCf8tMfG7YdO/p+ILFOwq8fH0cT/2NshysHh5R4c0sL22ipw==")
                .addHeader("udid", "3AE86D63-FF4E-1875-B914-E01F955924C0")
                .get()
                .build();
        try {
            Response response = proxyService.createClient().newCall(request).execute();
            try (ResponseBody body = response.body()) {
                String json = body.string();
            } catch (Exception e) {
                return Collections.emptyList();
            }
        } catch (Exception ignored) {
        }
        return Collections.emptyList();
    }
}
