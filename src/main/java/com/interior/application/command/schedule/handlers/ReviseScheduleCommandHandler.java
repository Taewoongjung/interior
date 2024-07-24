package com.interior.application.command.schedule.handlers;

import static com.interior.adapter.common.exception.ErrorType.ONLY_OWNER_CAN_REVISE_BUSINESS_SCHEDULE;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.ICommandHandler;
import com.interior.application.command.schedule.commands.ReviseScheduleCommand;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.schedule.BusinessSchedule;
import com.interior.domain.schedule.repository.BusinessScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviseScheduleCommandHandler implements
        ICommandHandler<ReviseScheduleCommand, Long> {

    private final BusinessRepository businessRepository;
    private final BusinessScheduleRepository businessScheduleRepository;

    @Override
    public boolean isCommandHandler() {
        return true;
    }

    @Override
    @Transactional
    public Long handle(final ReviseScheduleCommand command) {
        log.info("execute ReviseScheduleCommand = {}", command);

        BusinessSchedule originalBusinessSchedule = businessScheduleRepository.findById(
                command.scheduleId());

        // revisedUser 를 받는데, 최초 자신이 등록한 스케줄은 그 사람의 것이며, 자기 자신만 변경 가능.
        check(!command.revisedUser().getId().equals(originalBusinessSchedule.getUserId()),
                ONLY_OWNER_CAN_REVISE_BUSINESS_SCHEDULE);

        if (!command.relatedBusinessId().equals(originalBusinessSchedule.getBusinessId())) {
            // 존재 하는 사업인지 조회/검증
            businessRepository.findById(command.relatedBusinessId());
        }

        // 이전에 isAlarmOn 이 true 였다가 false 로 변경 되면 BusinessScheduleAlarm 에 isDeleted 가 true 로 변경 됨
        Long revisedBusinessScheduleId = businessScheduleRepository.reviseBusinessSchedule(
                command.convertToReviseBusinessSchedule());

        // 변경 로그를 쌓을지는 더 검토 필요
        
        log.info("ReviseScheduleCommand executed successfully");
        return revisedBusinessScheduleId; // 수정 된 스케줄의 id 리턴
    }
}
