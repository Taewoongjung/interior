package com.interior.application.readmodel.user;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.application.readmodel.user.queries.GetUserEmailQuery;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserEmailQueryHandler implements IQueryHandler<GetUserEmailQuery, User> {

    private final UserRepository userRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public User handle(final GetUserEmailQuery query) {

        return userRepository.findByPhoneNumber(query.phoneNumber());
    }
}
