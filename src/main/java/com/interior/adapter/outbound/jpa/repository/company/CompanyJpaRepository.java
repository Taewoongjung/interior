package com.interior.adapter.outbound.jpa.repository.company;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, Long> {

}
