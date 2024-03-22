package com.interior.adapter.inbound.customer;

import com.interior.application.customer.CustomerService;
import com.interior.application.customer.dto.SignUpDto.SignUpReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(value = "/customers")
    public ResponseEntity<Boolean> signup(final SignUpReqDto req) {

        return ResponseEntity.status(HttpStatus.OK).body(customerService.signUp(req));
    }
}
