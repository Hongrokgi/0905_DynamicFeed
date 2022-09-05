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
public class MessageService extends HttpCallService{
    private static final String MSG_SEND_SERVICE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
    private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";
    private static final String SUCCESS_CODE = "0"; // Kakao api 에서 return 하는 success_code 값

    public boolean sendMessage(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type",APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer "+accessToken);

        //여기는 template_object{"object_type", "content"} 이 두개만 포함..
//        JSONObject typeObject = new JSONObject();
//        typeObject.put("object_type","feed");

        JSONObject linkObject = new JSONObject();
        linkObject.put("web_url","http://localhost:88");

        JSONObject contentObject = new JSONObject();
        contentObject.put("title","오늘의 디저트");
        contentObject.put("description","아메리카노, 빵, 텐동");
        contentObject.put("image_url","https://previews.123rf.com/images/orla/orla1307/orla130700405/21138722-3d-%EC%82%AC%EB%9E%8C%EB%93%A4-%ED%81%B0-%EB%AC%BC%EC%9D%8C%ED%91%9C%EB%A5%BC-%EA%B0%80%EC%A7%84-%EC%82%AC%EB%9E%8C-%EC%82%AC%EB%9E%8C.jpg?fj=1");
        contentObject.put("image_width","640");
        contentObject.put("image_height","640");
        contentObject.put("link",linkObject);

        JSONObject templateObj = new JSONObject();
        templateObj.put("object_type", "feed");
        templateObj.put("content",contentObject);


        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_object",templateObj.toString());

        HttpEntity<?> messageRequestEntity = httpClientEntity(header, parameters);
        String resultCode = "";
        ResponseEntity<String> response = httpRequest(MSG_SEND_SERVICE_URL, HttpMethod.POST, messageRequestEntity);
        JSONObject jsonData = new JSONObject(response.getBody());
        resultCode = jsonData.get("result_code").toString();
        return successCheck(resultCode);
    }

    public boolean successCheck(String resultCode) {
        if (resultCode.equals(SUCCESS_CODE)) {
            log.info(SEND_SUCCESS_MSG);
            return true;
        }else {
            log.info(SEND_FAIL_MSG);
            return false;
        }
    }
}
