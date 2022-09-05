package com.kakao.feed.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class HttpCallService {
    public static final String APP_TYPE_URL_ENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String APP_TYPE_JSON = "application/json;charset=UTF-8";


    /*
    *   HTTP 요청 클라이언트 객체 생성 method
    *   @ param Map<String, String> header HttpHeader 정보
    *   @ param Object params HttpBody 정보
    *   @ return HttpEntity 생성된 HttpClient 객체 정보 변환
    *   @ exception 예외사항
    *
    * */
    public HttpEntity<?> httpClientEntity(HttpHeaders header, Object params) {
        HttpHeaders requestHeaders = header;
        if(params == null || "".equals(params)) {
            return new HttpEntity<>(requestHeaders);
        }else {
            return new HttpEntity<>(params,requestHeaders);
        }
    }

    /*
    *   HTTP 요청 method
    *   @ param String url 요청 url 정보
    *   @ param HttpMethod method 요청 method 정보
    *   @ param HttpEntity<?> entity 요청 EntityClient 객체 정보
    *   @ return HttpEntity 생성된 HttpClient 객체 정보 반환
    * */
    public ResponseEntity<String> httpRequest(String url, HttpMethod method, HttpEntity<?> entity) {
        RestTemplate restTemplate = new RestTemplate(); // Time out 이 필요없기 때문에 다음과 같이 생성
        return restTemplate.exchange(url, method, entity, String.class); // exchange() : api 호출
    }

    /*
    *   HTTP 요청 method
    *   @ param  URI 요청 url 정보
    *   @ param HttpMethod method 요청 method 정보
    *   @ param HttpEntity<?> entity 요청 EntityClient 객체 정보
    *   @ return HttpEntity 생성된 HttpClient 객체 정보
    * */
    public ResponseEntity<String> httpRequest(URI url, HttpMethod method, HttpEntity<?> entity) {
        RestTemplate restTemplate = new RestTemplate(); // Time out 이 필요없기 때문에 다음과 같이 생성
        return restTemplate.exchange(url, method, entity, String.class); // api 호출
    }
}
