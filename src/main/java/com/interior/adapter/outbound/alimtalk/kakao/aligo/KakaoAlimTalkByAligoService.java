package com.interior.adapter.outbound.alimtalk.kakao.aligo;

import static com.interior.adapter.common.exception.ErrorType.ALIMTALK_SEND_ERROR;
import static com.interior.domain.alimtalk.kakaomsgtemplate.KakaoMsgTemplateType.REQUEST_QUOTATION_DRAFT;
import static com.interior.util.CheckUtil.check;

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
import com.interior.domain.business.thirdpartymessage.BusinessThirdPartyMessage;
import com.interior.domain.util.BoolType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
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
            log.error("[Err_msg] 알림톡 템플릿 싱크 맞추는 중 에러 : {}", error.toString());
        });
    }

    private List<Map<String, Object>> parseSuccessString(final String target) {

        // Gson 인스턴스 생성
        Gson gson = new Gson();

        // JSON 문자열을 Map으로 변환
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> jsonMap = gson.fromJson(target, mapType);

        return (List<Map<String, Object>>) jsonMap.get("list");
    }

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(final SendAlimTalk sendReq) {

        KakaoMsgTemplate template = kakaoMsgTemplateRepository.findByTemplateCode(
                sendReq.template().getTemplateCode());

        template.replaceArgumentOfTemplate(
                sendReq.customerName(),
                sendReq.company(),
                sendReq.user(),
                sendReq.business(),
                sendReq.businessSchedule(),
                sendReq.businessScheduleAlarm()
        );

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", KEY);
        formData.add("userid", USER_ID);
        formData.add("senderkey", SENDER_KEY);
        formData.add("tpl_code", template.getTemplateCode());
        formData.add("sender", senderPhoneNumber);
        formData.add("receiver_1", sendReq.receiverPhoneNumber());
        formData.add("subject_1", template.getMessageSubject());

        if (sendReq.businessScheduleAlarm() != null) { // 예약 발송
            LocalDateTime alarmStartTime = sendReq.businessScheduleAlarm().getAlarmStartDate();
            formData.add("senddate", getReservingAlimTalkTimeAsString(alarmStartTime));
        }

        if (REQUEST_QUOTATION_DRAFT.getTemplateCode()
                .equals(sendReq.template().getTemplateCode())) {
            formData.add("emtitle_1", "[" + sendReq.business().getName() + "]");
        }

        formData.add("message_1", template.getMessage());

        if (!AlimTalkButtonLinkType.NO.equals(template.getButtonLinkType())) {
            formData.add("button_1", template.getButtonInfo());
        }

        Mono<String> responseMono = webClient.post()
                .uri("https://kakaoapi.aligo.in/akv10/alimtalk/send/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve().bodyToMono(String.class);

        responseMono.subscribe(response -> {

            Map<String, String> res = parseSendResult(response);

            Long createdResultId = kakaoMsgResultRepository.save(KakaoMsgResult.of(
                    template.getTemplateName(),
                    template.getTemplateCode(),
                    template.getMessageSubject(),
                    template.getMessage(),
                    AlimTalkMessageType.KKO,
                    sendReq.receiverPhoneNumber(),
                    "0".equals(res.get("code")) ? res.get("mid") : null,
                    "0".equals(res.get("code")) ? BoolType.T : BoolType.F
            ));

            if (sendReq.business() != null && sendReq.user() != null) {

                kakaoMsgResultRepository.createThirdPartyMessageSendLog(
                        BusinessThirdPartyMessage.of(
                                sendReq.business().getId(),
                                sendReq.user().getId(),
                                createdResultId
                        ));
            }

        }, error -> {
            log.error("[Err_msg] 알림톡 전송 에러 (템플릿코드: {}) : {}", template.getTemplateCode(),
                    error.toString());
            error.printStackTrace();
        });
    }

    private Map<String, String> parseSendResult(final String target) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, String>>() {
        }.getType(), new CustomTypeAdapter());
        Gson gson = gsonBuilder.create();

        // JSON 문자열을 Map으로 변환
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();

        Map<String, String> wholeJson = gson.fromJson(target, mapType);

        check("-99".equals(wholeJson.get("code")), ALIMTALK_SEND_ERROR);

        Map<String, String> infoFromWholeJson = gson.fromJson(wholeJson.get("info"), mapType);

        Map<String, String> resultMap = new HashMap<>(wholeJson);
        resultMap.putAll(infoFromWholeJson);

        return resultMap;
    }

    private String getReservingAlimTalkTimeAsString(final LocalDateTime target) {

        return
                target.getYear() +
                        String.format("%02d", target.getMonthValue()) +
                        String.format("%02d", target.getDayOfMonth()) +
                        String.format("%02d", target.getHour()) +
                        String.format("%02d", target.getMinute()) +
                        String.format("%02d", target.getSecond());
    }
}
