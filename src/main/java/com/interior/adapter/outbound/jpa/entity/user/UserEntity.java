package com.interior.adapter.outbound.jpa.entity.user;

import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.interior.adapter.common.exception.ErrorType.*;
import static com.interior.util.CheckUtil.require;

@Entity
@Getter
@ToString
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Length(min = 10, max = 11)
    @Column(name = "tel", nullable = false)
    private String tel;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "varchar")
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyEntity> companyEntityList;

    private UserEntity(
            final Long id,
            final String name,
            final String email,
            final String password,
            final String tel,
            final UserRole role,
            final List<CompanyEntity> companyEntityList
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.role = role;
        this.companyEntityList = companyEntityList;
    }

    public static UserEntity of(
            final Long id,
            final String name,
            final String email,
            final String password,
            final String tel,
            final UserRole userRole
    ) {

        require(o -> name == null, name, INVALID_CUSTOMER_NAME);
        require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
        require(o -> password == null, password, INVALID_CUSTOMER_PASSWORD);
        require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);
        require(o -> userRole == null, userRole, INVALID_CUSTOMER_USER_ROLE);

        return new UserEntity(id, name, email, password, tel, userRole, null);
    }

    public static UserEntity of(
            final String name,
            final String email,
            final String password,
            final String tel,
            final UserRole userRole,
            final List<CompanyEntity> companyEntityList
    ) {

        require(o -> name == null, name, INVALID_CUSTOMER_NAME);
        require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
        require(o -> password == null, password, INVALID_CUSTOMER_PASSWORD);
        require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);
        require(o -> userRole == null, userRole, INVALID_CUSTOMER_USER_ROLE);

        return new UserEntity(null, name, email, password, tel, userRole, companyEntityList);
    }

    // 모든 연관 관계 제외 한 POJO 객체 리턴
    public User toPojo() {
        return User.of(
                getId(),
                getName(),
                getEmail(),
                getPassword(),
                getTel(),
                getRole(),
                getLastModified(),
                getCreatedAt()
        );
    }

    // 지연로딩으로 불러 오는 연관관계와 매핑 된 POJO 객체 리턴
    public User toPojo(final List<CompanyEntity> companyEntityList) {
        return User.of(
                getId(),
                getName(),
                getEmail(),
                getPassword(),
                getTel(),
                getRole(),
                getLastModified(),
                getCreatedAt(),
                companyEntityList.stream().map(CompanyEntity::toPojo)
                        .collect(Collectors.toList())
        );
    }

    // 모든 연관 관계와 매핑 된 POJO 객체 리턴
    public User toPojoWithRelations() {
        return User.of(
                getId(),
                getName(),
                getEmail(),
                getPassword(),
                getTel(),
                getRole(),
                getLastModified(),
                getCreatedAt(),
                getCompanyEntityList().stream().map(CompanyEntity::toPojo)
                        .collect(Collectors.toList())
        );
    }

    public void resetPassword(final String targetPwd) {
        this.password = targetPwd;
    }
}
