package com.interior.adapter.outbound.jpa.entity.customer;

import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_PASSWORD;
import static com.interior.adapter.common.exception.ErrorType.INVALID_CUSTOMER_TEL;
import static com.interior.adapter.outbound.util.CheckUtil.require;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String tel;

    private CustomerEntity(
        final Long id,
        final String name,
        final String email,
        final String password,
        final String tel
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public static CustomerEntity of(
            final Long id,
            final String name,
            final String email,
            final String password,
            final String tel
    ) {
        return new CustomerEntity(id, name, email, password, tel);
    }

    public static CustomerEntity of(
            final String name,
            final String email,
            final String password,
            final String tel
    ) {
        return new CustomerEntity(null, name, email, password, tel);
    }

    public Customer toPojo() {
        return Customer.of(
                getId(),
                getName(),
                getEmail(),
                getPassword(),
                getTel(),
                getLastModified(),
                getCreatedAt()
        );
    }
}
