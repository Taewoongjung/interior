package com.interior.adapter.outbound.jpa.entity.user;

import com.interior.adapter.outbound.jpa.entity.company.CompanyEntity;
import com.interior.adapter.outbound.jpa.entity.BaseEntity;
import com.interior.domain.user.User;
import com.interior.domain.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Length;

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
    
    @Length(min=10, max=11)
    @Column(name = "tel", nullable = false)
    private String tel;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "varchar")
    private UserRole role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyEntity> companyEntityList = new ArrayList<>();

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
        return new UserEntity(null, name, email, password, tel, userRole, companyEntityList);
    }

    public User toPojo() {
        return User.of(
                getId(),
                getName(),
                getEmail(),
                getPassword(),
                getTel(),
                getRole(),
                getLastModified(),
                getCreatedAt(),
                getCompanyEntityList().stream().map(CompanyEntity::toPojo).collect(Collectors.toList())
        );
    }
}
