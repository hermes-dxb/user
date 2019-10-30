package com.org.hermes.user.server.model;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
public class UserRegistrationDto extends AuditDto {
    private int userId;
    private String userName;
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    @NotEmpty(message = "*Please provide your name")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    @NotEmpty(message = "*Please provide mobile number")
    private String mobile;
    private int status;
    private Set<RoleDto> roles;
}
