package com.simple.poll.database.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;

import com.simple.poll.config.database.postgres.PostgreSQLEnumType;

@Entity
@Table(name = "users")
@TypeDef(name = "role", typeClass = PostgreSQLEnumType.class)
@DynamicInsert(value = false)
@DynamicUpdate(value = true)
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "userName", unique = true)
    @Length(min=1, max=50)
    private String name;
    
    @Column(nullable = false)
    @Length(min=50, max=72)
    private String password;
    
    @Column(nullable = false, unique = true)
    @Length(min=1, max=50)
    @Pattern(regexp="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
    
    @Column(nullable = false, unique = true)
    @Length(min=7, max=7)
    @Pattern(regexp="^[A-Za-z]\\d{6}$")
    private String hkIdentify;
    
    @Column(nullable = false)
    private Boolean userStatus;

    @Type(type = "role")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "UserRole")
    private UserRole role;
    
    private LocalDateTime lastLogin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHkIdentify() {
        return hkIdentify;
    }

    public void setHkIdentify(String hkIdentify) {
        this.hkIdentify = hkIdentify;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

}
