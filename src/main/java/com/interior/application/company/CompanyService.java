package com.interior.application.company;

import com.interior.adapter.outbound.jpa.repository.company.CompanyJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyJpaRepository companyJpaRepository;

//    @Transactional
//    public boolean createCompany() {
//
//    }
}
