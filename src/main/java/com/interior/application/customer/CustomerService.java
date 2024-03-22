package com.interior.application.customer;

import com.interior.application.customer.dto.SignUpDto.SignUpReqDto;
import com.interior.application.customer.dto.SignUpDto.SignUpResDto;
import com.interior.domain.customer.Customer;
import com.interior.domain.customer.repository.CustomerRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public SignUpResDto signUp(final SignUpReqDto req) {

        Customer customer = Customer.of(
            req.name(), req.email(), req.password(), req.tel(),
            LocalDateTime.now(), LocalDateTime.now());

        Customer result = customerRepository.save(customer);

        return new SignUpResDto(result != null, result.getName());
    }
}
