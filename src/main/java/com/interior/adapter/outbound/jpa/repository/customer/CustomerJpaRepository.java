package com.interior.adapter.outbound.jpa.repository.customer;

import com.interior.adapter.outbound.jpa.entity.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

}
