package com.interior.application.readmodel.company.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.company.queries.GetCompanyQuery;
import com.interior.domain.company.Company;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import com.interior.domain.util.BoolType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCompanyQueryHandler implements IQueryHandler<GetCompanyQuery, Company> {

    private final UserRepository userRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Company handle(final GetCompanyQuery query) {
        log.info("process GetCompanyQuery {}", query);
        
        User user = userRepository.findByEmail(query.userEmail());

        return user.getCompanyList().stream()
                .filter(f -> query.companyId().equals(f.getId()))
                .filter(f -> f.getIsDeleted() == BoolType.F)
                .findFirst().orElse(null);
    }
}
