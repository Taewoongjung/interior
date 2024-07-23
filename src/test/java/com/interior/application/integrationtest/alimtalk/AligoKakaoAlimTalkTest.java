package com.interior.application.integrationtest.alimtalk;

import static business.BusinessFixture.B_1;
import static businessschedule.BusinessScheduleAlarmFixture.BSA_1;
import static businessschedule.BusinessScheduleFixture.BS_2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.interior.adapter.config.gson.CustomTypeAdapter;
import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;
import com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgtemplate.KakaoMsgTemplateRepositoryAdapter;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkButtonLinkType;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkThirdPartyType;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import com.interior.domain.util.BoolType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Disabled
@SpringBootTest
@ActiveProfiles(value = "dev")
public class AligoKakaoAlimTalkTest {

    @Autowired
    private WebClient webClient;

    @Autowired
    private KakaoMsgTemplateRepositoryAdapter kakaoMsgTemplateRepositoryAdapter;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Test
    @Transactional
    @DisplayName("[알리고] 알림톡 템플릿 조회")
    void callRegisteredTemplateOnAligo() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "gtmpgewaf3b1ofzzxzb3qc5zpxzr3xw7");
        formData.add("userid", "interiorjung");
        formData.add("senderkey", "c600ad999cf3840328560d429e3f43fef8012719");

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/template/list/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {
            List<KakaoMsgTemplate> res = new ArrayList<>();
            List<Map<String, Object>> parsedRes = parseSuccessString(response);

            System.out.println("result = " + parsedRes);

            for (Map<String, Object> template : parsedRes) {
                res.add(KakaoMsgTemplate.of(
                        null,
                        template.get("templtName").toString(),
                        template.get("templtCode").toString(),
                        AlimTalkThirdPartyType.ALIGO,
                        template.get("templateExtra").toString(),
                        template.get("templtTitle").toString(),
                        template.get("templtContent").toString(),
                        template.get("templtTitle").toString(),
                        template.get("templtContent").toString(),
                        parseButtonInfo(template.get("buttons").toString()),
                        AlimTalkButtonLinkType.AL,
                        null, null
                ));
            }

            kakaoMsgTemplateRepositoryAdapter.syncToTemplateRegistered(res);

        }, error -> {
            System.out.println("에러 발생" + error);
        });
    }

    private List<Map<String, Object>> parseSuccessString(final String target) {

        // Gson 인스턴스 생성
        Gson gson = new Gson();

        // JSON 문자열을 Map으로 변환
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> jsonMap = gson.fromJson(target, mapType);

        // 각 필드 출력
        System.out.println("Code: " + jsonMap.get("code"));
        System.out.println("Message: " + jsonMap.get("message"));
        System.out.println("@@ = " + jsonMap);

        return (List<Map<String, Object>>) jsonMap.get("list");
    }

    private String parseButtonInfo(final String target) throws JSONException {

        String jsonString = target;

        jsonString = jsonString.replaceAll("=(?!(//|\\?))", ":");

        // Wrap URL values in quotes explicitly
        jsonString = jsonString.replaceAll(":kakaotalk://web/openExternal\\?#\\{url}",
                ":\"kakaotalk://web/openExternal?#{url}\"");

        // Fix empty values and remove unnecessary spaces
        jsonString = jsonString.replaceAll(":,", ":\"empty\",");
        jsonString = jsonString.replaceAll(":}", ":\"empty\"}");

        // Print the intermediate JSON string for debugging
        System.out.println("Intermediate JSON String: " + jsonString);

        // Step 1: Parse the valid JSON string
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONArray buttonsArray = new JSONArray();

        // Step 2: Process each item in the JSON array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            JSONObject button = new JSONObject();

            button.put("name", item.getString("name"));
            button.put("linkType", item.getString("linkType"));
            button.put("linkTypeName", item.getString("linkTypeName"));

            if (item.has("linkIos") && !item.getString("linkIos").isEmpty() && !item.getString(
                    "linkIos").equals("empty")) {
                button.put("linkIos", item.getString("linkIos"));
            }
            if (item.has("linkAnd") && !item.getString("linkAnd").isEmpty() && !item.getString(
                    "linkAnd").equals("empty")) {
                button.put("linkAnd", item.getString("linkAnd"));
            }

            buttonsArray.put(button);
        }

        JSONObject result = new JSONObject();
        result.put("button", buttonsArray);

        // Step 3: Output the result
        System.out.println(result.toString(4)); // Pretty print with an indent of 4

        return result.toString(4);
    }

    @Test
    @Transactional
    @DisplayName("[알리고] 회원가입 완료 알림톡 발송 테스트")
    void sendAlimTalkTest1() {

        KakaoMsgTemplate template = kakaoMsgTemplateRepositoryAdapter.findByTemplateCode("TT_6051");

        template.replaceArgumentOfTemplate("정태웅", null, null, null, null, null);

        System.out.println("template = " + template);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "gtmpgewaf3b1ofzzxzb3qc5zpxzr3xw7");
        formData.add("userid", "interiorjung");
        formData.add("senderkey", "c600ad999cf3840328560d429e3f43fef8012719");
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", "01029143611");
        formData.add("receiver_1", "01088257754");
        formData.add("subject_1", template.getMessageSubject());
        formData.add("message_1", template.getMessage());
        formData.add("button_1", template.getButtonInfo());
//        formData.add("testMode", "Y");

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/alimtalk/send/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {

            System.out.println("success = " + response);

//            aaa(response);

        }, error -> {
            System.out.println("에러 발생");
        });
    }

    @Test
    @Transactional
    @DisplayName("[알리고] 견적서 초안 발송 알림톡 발송 테스트")
    void sendAlimTalkTest2() {

        KakaoMsgTemplate template = kakaoMsgTemplateRepositoryAdapter.findByTemplateCode("TT_6052");

        template.replaceArgumentOfTemplate(null,
                Company.of("서울인테리어", "01000", 519L, "address", "subAddress", "buildingNumber",
                        "tel", BoolType.F, LocalDateTime.now(), LocalDateTime.now()),
                User.of("정태웅", "a@a.com", "password", "tel", UserRole.ADMIN,
                        LocalDateTime.now(), LocalDateTime.now()),
                null,
                null,
                null
        );

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "gtmpgewaf3b1ofzzxzb3qc5zpxzr3xw7");
        formData.add("userid", "interiorjung");
        formData.add("senderkey", "c600ad999cf3840328560d429e3f43fef8012719");
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", "01029143611");
        formData.add("receiver_1", "01088257754");
        formData.add("emtitle_1", "[" + "무지개 아파트 인테리어 견적서" + "]");
        formData.add("subject_1", template.getTemplateName());
        formData.add("message_1", template.getMessage());
        formData.add("button_1", template.getButtonInfo());
//        formData.add("testMode", "Y");

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/alimtalk/send/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {

            System.out.println("success = " + response);

        }, error -> {
            System.out.println("에러 발생");
        });
    }

    @Test
    @Transactional
    @DisplayName("[알리고] 발주 스케줄 알림 알림톡 발송 테스트")
    void sendAlimTalkTest3() {

        KakaoMsgTemplate template = kakaoMsgTemplateRepositoryAdapter.findByTemplateCode("TT_9862");

        template.replaceArgumentOfTemplate(null,
                Company.of("서울인테리어", "01000", 519L, "address", "subAddress", "buildingNumber",
                        "tel", BoolType.F, LocalDateTime.now(), LocalDateTime.now()),
                User.of("정태웅", "a@a.com", "password", "tel", UserRole.ADMIN,
                        LocalDateTime.now(), LocalDateTime.now()),
                B_1, // Business
                BS_2, // BusinessSchedule
                BSA_1 // BusinessScheduleAlarm
        );

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "gtmpgewaf3b1ofzzxzb3qc5zpxzr3xw7");
        formData.add("userid", "interiorjung");
        formData.add("senderkey", "c600ad999cf3840328560d429e3f43fef8012719");
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", "01029143611");
        formData.add("receiver_1", "01088257754");
        formData.add("subject_1", template.getTemplateName());
        formData.add("message_1", template.getMessage());
        formData.add("senddate", "20240723180800");
//        formData.add("testMode", "Y");

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/alimtalk/send/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {

            System.out.println("success = " + response);

        }, error -> {
            System.out.println("에러 발생");
        });
    }

    @Test
    @DisplayName("[알리고] 발송 후 결과값 파싱 테스트")
    void parsingTest() {
//        String target = "{\"code\":0,\"message\":\"\\uc131\\uacf5\\uc801\\uc73c\\ub85c \\uc804\\uc1a1\\uc694\\uccad \\ud558\\uc600\\uc2b5\\ub2c8\\ub2e4.\",\"info\":{\"type\":\"AT\",\"mid\":830636753,\"current\":\"49705.6\",\"unit\":6.5,\"total\":6.5,\"scnt\":1,\"fcnt\":0}}\n";
        String target = "{\"code\":0,\"message\":\"\\uc131\\uacf5\\uc801\\uc73c\\ub85c \\uc804\\uc1a1\\uc694\\uccad \\ud558\\uc600\\uc2b5\\ub2c8\\ub2e4.\",\"info\":{\"type\":\"AT\",\"mid\":830649269,\"current\":\"49699.1\",\"unit\":6.5,\"total\":6.5,\"scnt\":1,\"fcnt\":0}}";
        Map<String, String> result = parser(target);
        System.out.println("@@ = " + result);
    }

    private Map<String, String> parser(final String target) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, String>>() {
        }.getType(), new CustomTypeAdapter());
        Gson gson = gsonBuilder.create();

        // JSON 문자열을 Map으로 변환
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();

        Map<String, String> json1 = gson.fromJson(target, mapType);
        Map<String, String> json2 = gson.fromJson(json1.get("info"), mapType);

        Map<String, String> resultMap = new HashMap<>(json1);
        resultMap.putAll(json2);

        return resultMap;
    }

    @Test
    @DisplayName("회원가입 완료 알림톡 전송 테스트")
    void test1() {
        eventPublisher.publishEvent(
                new SendAlimTalk(null, null, KakaoMsgTemplateType.COMPLETE_SIGNUP, "01088257754",
                        "태웅정", null, null, null));
    }

    @Test
    void test2() throws JSONException {

//        String jsonString = "[{ordering=1, name=채널 추가, linkType=AC, linkTypeName=채널 추가, linkMo=, linkPc=, linkIos=, linkAnd=}]";
        String jsonString = "[{ordering=1, name=채널 추가, linkType=AC, linkTypeName=채널 추가, linkMo=, linkPc=, linkIos=, linkAnd=}, {ordering=2, name=서비스 바로가기, linkType=AL, linkTypeName=앱링크, linkMo=, linkPc=, linkIos=kakaotalk://web/openExternal?#{url}, linkAnd=kakaotalk://web/openExternal?#{url}}]";

        jsonString = jsonString.replaceAll("=(?!(//|\\?))", ":");

        // Wrap URL values in quotes explicitly
        jsonString = jsonString.replaceAll(":kakaotalk://web/openExternal\\?#\\{url}",
                ":\"kakaotalk://web/openExternal?#{url}\"");

        // Fix empty values and remove unnecessary spaces
        jsonString = jsonString.replaceAll(":,", ":\"empty\",");
        jsonString = jsonString.replaceAll(":}", ":\"empty\"}");

        // Print the intermediate JSON string for debugging
        System.out.println("Intermediate JSON String: " + jsonString);

        // Step 1: Parse the valid JSON string
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONArray buttonsArray = new JSONArray();

        // Step 2: Process each item in the JSON array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            JSONObject button = new JSONObject();

            button.put("name", item.getString("name"));
            button.put("linkType", item.getString("linkType"));
            button.put("linkTypeName", item.getString("linkTypeName"));

            if (item.has("linkIos") && !item.getString("linkIos").isEmpty() && !item.getString(
                    "linkIos").equals("empty")) {
                button.put("linkIos", item.getString("linkIos"));
            }
            if (item.has("linkAnd") && !item.getString("linkAnd").isEmpty() && !item.getString(
                    "linkAnd").equals("empty")) {
                button.put("linkAnd", item.getString("linkAnd"));
            }

            buttonsArray.put(button);
        }

        JSONObject result = new JSONObject();
        result.put("button", buttonsArray);

        // Step 3: Output the result
        System.out.println(result.toString(4)); // Pretty print with an indent of 4
    }
}