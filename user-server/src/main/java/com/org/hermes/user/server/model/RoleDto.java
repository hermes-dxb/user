package com.org.hermes.user.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends AuditDto {
    private int roleId;
    private String roleName;
    private int isActive;
}
