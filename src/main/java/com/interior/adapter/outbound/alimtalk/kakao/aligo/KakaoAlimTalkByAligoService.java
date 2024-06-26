package com.interior.adapter.outbound.alimtalk.kakao.aligo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.interior.adapter.config.gson.CustomTypeAdapter;
import com.interior.adapter.outbound.alimtalk.AlimTalkService;
import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;
import com.interior.domain.alimtalk.AlimTalkMessageType;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.alimtalk.kakaomsgresult.repository.KakaoMsgResultRepository;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkButtonLinkType;
import com.interior.domain.alimtalk.kakaomsgtemplate.AlimTalkThirdPartyType;
import com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplate;
import com.interior.domain.alimtalk.kakaomsgtemplate.repository.KakaoMsgTemplateRepository;
import com.interior.domain.util.BoolType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoAlimTalkByAligoService implements AlimTalkService {

    private final WebClient webClient;
    private final KakaoMsgResultRepository kakaoMsgResultRepository;
    private final KakaoMsgTemplateRepository kakaoMsgTemplateRepository;

    private String senderPhoneNumber = "01029143611";


    @Value("${kakao.aligo.key}")
    private String KEY;

    @Value("${kakao.aligo.user-id}")
    private String USER_ID;

    @Value("${kakao.aligo.sender-key}")
    private String SENDER_KEY;

    @Override
    @Transactional
    public void syncTemplate() {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", KEY);
        formData.add("userid", USER_ID);
        formData.add("senderkey", SENDER_KEY);

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/template/list/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {
            List<KakaoMsgTemplate> result = new ArrayList<>();
            List<Map<String, Object>> parsedRes = parseSuccessString(response);

            for (Map<String, Object> template : parsedRes) {
                result.add(KakaoMsgTemplate.of(
                        null,
                        template.get("templtName").toString(),
                        template.get("templtCode").toString(),
                        AlimTalkThirdPartyType.ALIGO,
                        template.get("templateExtra").toString(),
                        template.get("templtTitle").toString(),
                        template.get("templtContent").toString(),
                        template.get("templtTitle").toString(),
                        template.get("templtContent").toString(),
                        template.get("buttons").toString(),
                        AlimTalkButtonLinkType.AL,
                        null, null
                ));
            }

            kakaoMsgTemplateRepository.syncToTemplateRegistered(result);

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

    @Override
    @EventListener
    @Transactional
    public void send(final SendAlimTalk sendReq) {
        KakaoMsgTemplate template = kakaoMsgTemplateRepository.findByTemplateCode("TT_5653");
        template.replaceArgumentOfTemplate(sendReq.customerName());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", KEY);
        formData.add("userid", USER_ID);
        formData.add("senderkey", SENDER_KEY);
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", senderPhoneNumber);
        formData.add("receiver_1", sendReq.receiverPhoneNumber());
        formData.add("subject_1", template.getMessageSubject());
        formData.add("message_1", template.getMessage());
        formData.add("button_1", getButtonJson());

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/alimtalk/send/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {

            Map<String, String> res = parseResult(response);

            kakaoMsgResultRepository.save(KakaoMsgResult.of(
                    template.getTemplateName(),
                    template.getTemplateCode(),
                    template.getMessageSubject(),
                    template.getMessage(),
                    AlimTalkMessageType.KKO,
                    sendReq.receiverPhoneNumber(),
                    "0".equals(res.get("code")) ? res.get("mid") : null,
                    "0".equals(res.get("code")) ? BoolType.T : BoolType.F
            ));

        }, error -> {
            log.error("알림톡 전송 에러 (템플릿코드: {})", template.getTemplateCode());
            log.error("Err_msg : {}", error.fillInStackTrace().toString());
        });
    }

    private String getButtonJson() {

        return """
                {
                    "button": [{
                         "name": "채널 추가",
                         "linkType": "AC",
                         "linkTypeName": "채널 추가"
                      },
                      {
                        "name": "서비스 바로가기",
                        "linkType": "AL",
                        "linkTypeName": "앱링크",
                        "linkIos": "kakaotalk://web/openExternal?url=http://interiorjung.shop",
                        "linkAnd": "kakaotalk://web/openExternal?url=http://interiorjung.shop"
                      }
                    ]
                }
                """;
    }

    private Map<String, String> parseResult(final String target) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, String>>() {
        }.getType(), new CustomTypeAdapter());
        Gson gson = gsonBuilder.create();

        // JSON 문자열을 Map으로 변환
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();

        Map<String, String> wholeJson = gson.fromJson(target, mapType);
        Map<String, String> infoFromWholeJson = gson.fromJson(wholeJson.get("info"), mapType);

        Map<String, String> resultMap = new HashMap<>(wholeJson);
        resultMap.putAll(infoFromWholeJson);

        return wholeJson;
    }
}