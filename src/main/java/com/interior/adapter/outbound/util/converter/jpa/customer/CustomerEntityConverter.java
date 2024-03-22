package com.interior.adapter.outbound.util.converter.jpa.customer;

import com.interior.adapter.outbound.jpa.entity.customer.CustomerEntity;
import com.interior.domain.customer.Customer;

public class CustomerEntityConverter {

    public static CustomerEntity customerToEntity(final Customer customer) {
        return CustomerEntity.of(customer.getName(), customer.getEmail(), customer.getPassword(), customer.getTel());
    }
}
