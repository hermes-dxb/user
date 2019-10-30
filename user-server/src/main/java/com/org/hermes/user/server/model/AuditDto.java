package com.org.hermes.user.server.model;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.Data;

@Data
public class AuditDto {

	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	@Column(name = "updated_by")
	private String updatedBy;

}
