package com.interior.adapter.outbound.alarm.slack;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.header;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import com.interior.adapter.outbound.alarm.AlarmService;
import com.interior.adapter.outbound.alarm.dto.event.ErrorAlarm;
import com.interior.adapter.outbound.alarm.dto.event.NewBusinessAlarm;
import com.interior.adapter.outbound.alarm.dto.event.NewCompanyAlarm;
import com.interior.adapter.outbound.alarm.dto.event.NewUserAlarm;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Async
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

    @EventListener
    @Transactional
    public void sendErrorAlarm(final ErrorAlarm event) {

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText(event.errorMsg()));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(errorAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText("⚠️ " + event.methodName()))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", errorAlarmChanel);
        }
    }

    @EventListener
    @Transactional
    public void sendNewUserAlarm(final NewUserAlarm event) {

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*이메일:*\n" + event.email()));
            textObjects.add(markdownText("*전화번호:*\n" + event.tel()));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(newUserAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText(event.newUserName() + "님이 회원가입 했습니다."))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", newUserAlarmChanel);
        }
    }

    @Async
    public void sendNewCompanyAlarm(final NewCompanyAlarm event) {

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*사업체 명:*\n" + event.companyName()));
            textObjects.add(markdownText("*사업체 주소:*\n" + event.address()));
            textObjects.add(markdownText("*오너:*\n" + event.ownerName()));
            textObjects.add(markdownText("*이메일:*\n" + event.email()));
            textObjects.add(markdownText("*전화번호:*\n" + event.tel()));

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

    @EventListener
    @Transactional
    public void sendNewBusinessAlarm(final NewBusinessAlarm event) {

        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*사업체 명* : " + event.companyName()));
            textObjects.add(markdownText("*오너* : " + event.ownerName()));
            textObjects.add(markdownText("*이메일* : " + event.email()));
            textObjects.add(markdownText("*전화번호* : " + event.tel()));

            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(newBusinessAlarmChanel)
                    .blocks(asBlocks(
                            header(header -> header.text(
                                    plainText(
                                            "[ " + event.businessName() + " ] 새로운 사업이 등록 되었습니다."))),
                            divider(),
                            section(section -> section.fields(textObjects)
                            ))).build();

            methods.chatPostMessage(request);

        } catch (SlackApiException | IOException e) {
            log.info("{} 채널에 슬랙 메시지 전송 실패", newBusinessAlarmChanel);
        }
    }
}
