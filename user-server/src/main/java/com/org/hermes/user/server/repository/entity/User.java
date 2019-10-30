package com.org.hermes.user.server.repository.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
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
@Entity(name = "user")
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_seq_gen")
    @SequenceGenerator(name="user_id_seq_gen", sequenceName = "USER_USER_ID_seq", allocationSize=1)
	@Column(name = "user_id")
    private int userId;
	@Column(name = "email")
    private String email;
	@Column(name = "first_name")
    private String firstName;
	@Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "office_num")
    private String officeNum;
    @Column(name = "office_ext")
    private int officeExt;
    @Column(name = "status")
    private int status;
    @Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
