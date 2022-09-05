package com.kakao.feed.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Slf4j
public class AuthService extends HttpCallService{
    private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";
    public static String authToken;

    public boolean getKakaoAuthToken(String code) {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type",APP_TYPE_URL_ENCODED);

        parameters.add("code",code);
        parameters.add("grant_type","authorization_code");
        parameters.add("client_id","9fe97b094255a1ba147d10cc045a5bf6");
        parameters.add("redirect_url","http://localhost:88");
        parameters.add("client_secret","rG7sHKbZclt2SYu0NxgtpUdn1AOGLI8j");

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        accessToken = jsonData.getString("access_token").toString();
        refreshToken = jsonData.getString("refresh_token").toString();
        if(accessToken.isEmpty() || refreshToken.isEmpty()) {
            log.debug("토큰 발급에 실패");
            return false;
        }else {
            authToken= accessToken;
            return true;
        }
    }
}
