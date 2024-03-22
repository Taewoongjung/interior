package com.interior.adapter.outbound.jpa.repository.customer;

import static com.interior.adapter.outbound.util.converter.jpa.customer.CustomerEntityConverter.customerToEntity;

import com.interior.adapter.outbound.jpa.entity.customer.CustomerEntity;
import com.interior.domain.customer.Customer;
import com.interior.domain.customer.repository.CustomerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public Customer save(final Customer customer) {
        CustomerEntity entity = customerJpaRepository.save(customerToEntity(customer));
        return entity.toPojo();
    }
}
