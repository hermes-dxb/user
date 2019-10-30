package com.org.hermes.user.server.model;

import java.util.Set;

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
public class UserDto extends AuditDto {
    private int userId;
    private String userName;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobile;
    private int status;
    private String officeNum;
    private int officeExt;
    private Set<RoleDto> roles;
}
