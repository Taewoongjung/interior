package com.interior.adapter.outbound.jpa.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

}
