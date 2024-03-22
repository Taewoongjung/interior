package com.interior.domain.customer.repository;

import com.interior.domain.customer.Customer;

public interface CustomerRepository {

    Customer save(final Customer customer);
}
