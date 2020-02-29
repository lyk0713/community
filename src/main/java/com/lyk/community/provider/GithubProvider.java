package com.lyk.community.provider;

import com.alibaba.fastjson.JSON;
import com.lyk.community.dto.AccessTokenDTO;
import com.lyk.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        // 通过fastjson将accessTokenDTO传输对象转换成json格式
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);

            // 将Github返回的字符截取出access_token的值
            String[] split = string.split("&");
            String tokenstr = split[0];
            String token = tokenstr.split("=")[1];
            //System.out.println(token);
            return token;
        } catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);

            // 将github返回json格式的字符串通过fastjson转成构建好的GithubUser对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
