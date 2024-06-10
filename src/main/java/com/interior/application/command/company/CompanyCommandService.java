package com.interior.application.command.company;

import static com.interior.adapter.common.exception.ErrorType.LIMIT_OF_COMPANY_COUNT_IS_FIVE;
import static com.interior.util.CheckUtil.check;

import com.interior.adapter.outbound.alarm.dto.event.NewCompanyAlarm;
import com.interior.application.command.company.dto.CreateCompanyServiceDto;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyCommandService {

    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public boolean createCompany(
            final User user,
            final CreateCompanyServiceDto.CreateCompanyDto reqDto
    ) {

        check(user.getCompanyList().size() >= 5, LIMIT_OF_COMPANY_COUNT_IS_FIVE);

        Company company = Company.of(
                reqDto.companyName(),
                reqDto.zipCode(),
                user.getId(),
                reqDto.mainAddress(),
                reqDto.subAddress(),
                reqDto.bdgNumber(),
                reqDto.tel(),
                BoolType.F,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        if (companyRepository.save(user.getEmail(), company)) {

            // 새로운 사업체 생성 시 알람 발송
            eventPublisher.publishEvent(
                    new NewCompanyAlarm(company.getName(),
                            user.getName(),
                            user.getEmail(),
                            company.getTel(),
                            company.getAddress() + " " + company.getSubAddress()
                    )
            );

            return true;
        }

        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCompany(final Long userId, final Long companyId) {

        companyRepository.delete(userId, companyId);

        return true;
    }
}
