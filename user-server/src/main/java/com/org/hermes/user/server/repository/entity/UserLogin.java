package com.org.hermes.user.server.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="UserLogin")
@Table(name="user_login",schema="hermes")
public class UserLogin {

	@Id
    @Column(name = "user_id")
    private int userId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;
    @Column(name = "status")
    private int status;
    @Column(name = "is_tp_login")
    private int isTpLogin;
    @Column(name = "tp_login_type")
    private String tpLoginType;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
}
