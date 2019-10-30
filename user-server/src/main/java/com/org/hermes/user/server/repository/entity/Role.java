package com.org.hermes.user.server.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="role")
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="role_seq_gen")
    @SequenceGenerator(name="role_seq_gen", sequenceName = "role_seq", allocationSize=999999)
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "is_active")
    private int isActive;
    @Column(name = "created_date")
   	private LocalDateTime createdDate;
   	@Column(name = "created_by")
   	private String createdBy;
   	@Column(name = "updated_date")
   	private LocalDateTime updatedDate;
   	@Column(name = "updated_by")
   	private String updatedBy;
}
