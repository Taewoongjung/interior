package com.interior.application.command.log.business;

import com.interior.application.command.log.business.dto.event.BusinessDeleteEvent;
import com.interior.application.command.log.business.dto.event.BusinessReviseEvent;
import com.interior.domain.business.log.BusinessChangeFieldType;
import com.interior.domain.business.log.BusinessLog;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessLogService {

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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createLogForChangingBusinessName(final BusinessReviseEvent event) {

        createLogOfChangeBusiness(
                event.businessId(),
                BusinessChangeFieldType.BUSINESS_NAME,
                event.originalBusinessName(),
                event.changeBusinessName(),
                event.updaterId()
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createLogForDeletingBusiness(final BusinessDeleteEvent event) {

        createLogOfChangeBusiness(
                event.businessId(),
                BusinessChangeFieldType.BUSINESS_NAME,
                event.originalBusinessName(),
                null,
                event.updaterId()
        );
    }
}
