package com.interior.application.command.log.business;

import com.interior.application.command.log.business.dto.event.BusinessDeleteLogEvent;
import com.interior.application.command.log.business.dto.event.BusinessReviseLogEvent;
import com.interior.domain.business.log.BusinessChangeFieldType;
import com.interior.domain.business.log.BusinessLog;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessLogEventListener {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    private boolean createLogOfChangeBusiness(
            final Long businessId,
            final BusinessChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId
    ) {

        User user = userRepository.findById(updaterId);

        return businessRepository.createBusinessUpdateLog(
                BusinessLog.of(
                        businessId,
                        changeField,
                        beforeData,
                        afterData,
                        updaterId,
                        user.getName(),
                        LocalDateTime.now()
                )
        );
    }

    @EventListener
    @Transactional
    public void createLogForChangingBusinessName(final BusinessReviseLogEvent event) {

        createLogOfChangeBusiness(
                event.businessId(),
                event.type(),
                event.originalBusinessName(),
                event.changeBusinessName(),
                event.updaterId()
        );
    }

    @EventListener
    @Transactional
    public void createLogForDeletingBusiness(final BusinessDeleteLogEvent event) {

        createLogOfChangeBusiness(
                event.businessId(),
                BusinessChangeFieldType.DELETE_BUSINESS,
                event.originalBusinessName(),
                null,
                event.updaterId()
        );
    }
}
