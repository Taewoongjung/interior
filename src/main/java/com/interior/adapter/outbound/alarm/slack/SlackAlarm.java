package com.interior.adapter.outbound.alarm.slack;

import com.interior.adapter.outbound.alarm.AlarmService;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Slf4j
@Getter
@Primary
@Component
public class SlackAlarm implements AlarmService {


    @Value(value = "${slack.token}")
    private String token;

    @Value(value = "${slack.channel.monitor.error}")
    private String errorAlarmChanel;

    @Value(value = "${slack.channel.monitor.new-user}")
    private String newUserAlarmChanel;

    @Value(value = "${slack.channel.monitor.new-company}")
    private String newCompanyAlarmChanel;

    @Value(value = "${slack.channel.monitor.new-business}")
    private String newBusinessAlarmChanel;

    @Async
    public void sendErrorAlarm(final String methodName, final String errorMsg) {

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText(errorMsg));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(errorAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText("⚠️ " + methodName))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", errorAlarmChanel);
        }
    }

    @Async
    public void sendNewUserAlarm(final String newUserName, final String email, final String tel) {
        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*이메일:*\n" + email));
            textObjects.add(markdownText("*전화번호:*\n" + tel));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(newUserAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText(newUserName + "님이 회원가입 했습니다."))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", newUserAlarmChanel);
        }
    }

    @Async
    public void sendNewCompanyAlarm(
            final String companyName,
            final String ownerName,
            final String email,
            final String tel,
            final String address
    ) {

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*사업체 명:*\n" + companyName));
            textObjects.add(markdownText("*사업체 주소:*\n" + address));
            textObjects.add(markdownText("*오너:*\n" + ownerName));
            textObjects.add(markdownText("*이메일:*\n" + email));
            textObjects.add(markdownText("*전화번호:*\n" + tel));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(newCompanyAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText("새로운 사업체가 생겼습니다."))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", newCompanyAlarmChanel);
        }
    }

    @Async
    public void sendNewBusinessAlarm(
            final String businessName,
            final String companyName,
            final String ownerName,
            final String email,
            final String tel
    ) {

        System.out.println("??? = " + newBusinessAlarmChanel);

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*사업체 명* : " + companyName));
            textObjects.add(markdownText("*오너* : " + ownerName));
            textObjects.add(markdownText("*이메일* : " + email));
            textObjects.add(markdownText("*전화번호* : " + tel));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(newBusinessAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText("[ " + businessName + " ] 새로운 사업이 등록 되었습니다."))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", newBusinessAlarmChanel);
        }
    }
}
