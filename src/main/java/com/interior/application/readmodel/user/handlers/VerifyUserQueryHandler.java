package com.interior.application.readmodel.user.handlers;

import static com.interior.adapter.common.exception.ErrorType.NOT_MATCHED_EMAIL_WITH_PHONE_NUMBER;
import static com.interior.util.CheckUtil.check;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.user.queries.VerifyUserQuery;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyUserQueryHandler implements IQueryHandler<VerifyUserQuery, Boolean> {

    private final UserRepository userRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean handle(final VerifyUserQuery query) {
        log.info("VerifyUserQuery {}", query);

        User user = userRepository.findByEmail(query.email());

        check(!query.phoneNumber().equals(user.getTel()), NOT_MATCHED_EMAIL_WITH_PHONE_NUMBER);

        return true;
    }
}
