package com.interior.application.command.log.business.material;

import com.interior.application.command.log.business.material.dto.event.BusinessMaterialCreateLogEvent;
import com.interior.application.command.log.business.material.dto.event.BusinessMaterialDeleteLogEvent;
import com.interior.application.command.log.business.material.dto.event.BusinessReviseMaterialLogEvent;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.material.log.BusinessMaterialLog;
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


/**
 * 로그 생성을 담당 하는 logService 를 도메인별로 따로 빼는 이유 : 해당 클래스에서는 CQRS 가 적용 되지 않으며, 비동기로 실행 된다. 각각 Command,
 * Query 로 나뉘어진 이유는 각각의 생성, 삭제, 수정 / 조회 를 정확히 나뉘어 졌기에 Command 와 Query 가 같이 존재하는 로그 생성은 Command,
 * Query 어느쪽에도 해당 되지 않다고 판단해서 logService 로 따로 뺐다.
 */
@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessMaterialLogService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;


    private boolean createLogOfChangeMaterials(
            final Long businessId,
            final Long businessMaterialId,
            final BusinessMaterialChangeFieldType changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId
    ) {

        User user = userRepository.findById(updaterId);

        return businessRepository.createBusinessMaterialUpdateLog(
                BusinessMaterialLog.of(
                        businessId,
                        businessMaterialId,
                        changeField,
                        beforeData,
                        afterData,
                        updaterId,
                        user.getName(),
                        LocalDateTime.now()
                )
        );
    }


    // 재료 삭제에 대한 로그
    @EventListener
    @Transactional
    public void createLogForDeletingBusinessMaterial(final BusinessMaterialDeleteLogEvent event) {

        BusinessMaterial originalBusinessMaterial =
                businessRepository.findBusinessMaterialByMaterialId(event.materialId());

        createLogOfChangeMaterials(
                event.businessId(),
                event.materialId(),
                BusinessMaterialChangeFieldType.DELETE_MATERIAL,
                originalBusinessMaterial.getName(),
                null,
                event.updaterId()
        );
    }

    // 재료 생성에 대한 로그
    @EventListener
    @Transactional
    public void createLogForCreatingBusinessMaterial(final BusinessMaterialCreateLogEvent event) {

        createLogOfChangeMaterials(
                event.businessId(),
                event.businessMaterialId(),
                BusinessMaterialChangeFieldType.CREATE_NEW_MATERIAL,
                null,
                event.afterData(),
                event.updaterId()
        );
    }

    // 재료 생성에 대한 로그
    @EventListener
    @Transactional
    public void createLogForRevisingBusinessMaterial(final BusinessReviseMaterialLogEvent event) {

        createLogOfChangeMaterials(
                event.businessId(),
                event.businessMaterialId(),
                event.type(),
                event.beforeData(),
                event.afterData(),
                event.updaterId()
        );
    }
}
