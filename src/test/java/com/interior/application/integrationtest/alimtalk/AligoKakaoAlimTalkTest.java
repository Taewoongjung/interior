package com.interior.application.integrationtest.alimtalk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.interior.adapter.config.gson.CustomTypeAdapter;
import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;
import com.interior.adapter.outbound.jpa.repository.alimtalk.kakaomsgtemplate.KakaoMsgTemplateRepositoryAdapter;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkButtonLinkType;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                        template.get("templateExtra").toString(),
                        template.get("templtTitle").toString(),
                        template.get("templtContent").toString(),
                        template.get("templtTitle").toString(),
                        template.get("templtContent").toString(),
                        "",
                        AlimTalkButtonLinkType.AL,
                        null, null
                ));
            }

            kakaoMsgTemplateRepositoryAdapter.syncToTemplateRegistered(res);

        }, error -> {
            System.out.println("에러 발생");
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

        return (List<Map<String, Object>>) jsonMap.get("list");
    }

    @Test
    @Transactional
    @DisplayName("[알리고] 회원가입 완료 알림톡 발송 테스트")
    void sendAlimTalkTest1() {

        KakaoMsgTemplate template = kakaoMsgTemplateRepositoryAdapter.findByTemplateCode("TT_5460");

        System.out.println("template = " + template);
        System.out.println();

        String buttonJson = """
                {
                    "button": [
                      {
                        "name": "서비스 바로가기",
                        "linkType": "AL",
                        "linkTypeName": "앱링크",
                        "linkIos": "kakaotalk://web/openExternal?url=http://interiorjung.shop/auth",
                        "linkAnd": "kakaotalk://web/openExternal?url=http://interiorjung.shop/auth"
                      }
                    ]
                }
                """;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "gtmpgewaf3b1ofzzxzb3qc5zpxzr3xw7");
        formData.add("userid", "interiorjung");
        formData.add("senderkey", "c600ad999cf3840328560d429e3f43fef8012719");
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", "01029143611");
        formData.add("receiver_1", "01095599617");
        formData.add("subject_1", template.getMessageSubject());
        formData.add("message_1", "(축하) 정호윤님! 환영합니다 (축하)\n"
                + "\n"
                + "인테리어정가 에 회원가입 해주셔서\n"
                + "진심으로 감사드립니다.");
        formData.add("button_1", buttonJson);
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

        KakaoMsgTemplate template = kakaoMsgTemplateRepositoryAdapter.findByTemplateCode("TT_5461");

        String buttonJson = """
                {
                    "button": [
                      {
                        "name": "견적서 확인하기",
                        "linkType": "AL",
                        "linkTypeName": "앱링크",
                        "linkIos": "kakaotalk://web/openExternal?url=http://interiorjung.shop/auth",
                        "linkAnd": "kakaotalk://web/openExternal?url=http://interiorjung.shop/auth"
                      }
                    ]
                }
                """;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", "gtmpgewaf3b1ofzzxzb3qc5zpxzr3xw7");
        formData.add("userid", "interiorjung");
        formData.add("senderkey", "c600ad999cf3840328560d429e3f43fef8012719");
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", "01029143611");
        formData.add("receiver_1", "01088257754");
        formData.add("emtitle_1", "[#{견적서명}]");
        formData.add("subject_1", "견적서 초안 도착");
        formData.add("message_1",
                "▶ 요청 한 사람 : 정태웅\n"
                        + "▶ 요청 회사 : TW인테리어\n\n"
        );
        formData.add("button_1", buttonJson);
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
    public void test12() {
        String target = "success = {\"code\":0,\"message\":\"\\uc131\\uacf5\\uc801\\uc73c\\ub85c \\uc804\\uc1a1\\uc694\\uccad \\ud558\\uc600\\uc2b5\\ub2c8\\ub2e4.\",\"info\":{\"type\":\"AT\",\"mid\":830636753,\"current\":\"49705.6\",\"unit\":6.5,\"total\":6.5,\"scnt\":1,\"fcnt\":0}}\n";
        // Gson 인스턴스 생성
        Gson gson = new Gson();

        // JSON 문자열을 Map으로 변환
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();

        Map<String, Object> jsonMap1 = gson.fromJson(target, mapType);
        Map<String, Object> jsonMap2 = gson.fromJson(jsonMap1.get("info").toString(), mapType);

        // 각 필드 출력
        System.out.println("Code: " + jsonMap1.get("code"));
        System.out.println("Message: " + jsonMap1.get("message"));
        System.out.println("Info: " + jsonMap1.get("info"));

        Map<String, Object> resultMap = new HashMap<>(jsonMap1);
        resultMap.putAll(jsonMap2);

        System.out.println("info222: " + resultMap.get("mid"));
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
        eventPublisher.publishEvent(new SendAlimTalk("01088257754", "태웅정"));
    }
}