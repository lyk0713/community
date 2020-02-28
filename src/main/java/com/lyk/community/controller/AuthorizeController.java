package com.lyk.community.controller;

import com.lyk.community.dto.AccessTokenDTO;
import com.lyk.community.dto.GithubUser;
import com.lyk.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code, @RequestParam(name="state")String state) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id("2635142931332aac30d8");
        accessTokenDTO.setClient_secret("56bbaf3b216a8df77a889eebd9f43903f47fb2d8");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        GithubUser user = githubProvider.getUser(accessToken);


        return "index";
    }
}
