package com.org.hermes.user.server.util.converter;

import java.time.LocalDateTime;

import com.org.hermes.user.server.model.RoleDto;
import com.org.hermes.user.server.repository.entity.Role;
/**
 * 
 * 
 * @author v.nayanar
 *
 */
public class RoleConverter {
	/**
	 * Convert dto to entity
	 * 
	 * @param roleDto
	 * @return
	 */
	public static Role convertToRoleEntity(RoleDto roleDto) {
		Role role = new Role();
		role.setRoleId(roleDto.getRoleId());
		role.setRoleName(roleDto.getRoleName());
		role.setIsActive(roleDto.getIsActive());
		role.setCreatedBy(roleDto.getCreatedBy());
		role.setCreatedDate(roleDto.getCreatedDate() != null ? roleDto.getCreatedDate() : LocalDateTime.now());
		return role;
	}
	
	/**
	 * Convert entity to dto
	 * 
	 * @param role
	 * @return
	 */
	public static RoleDto convertToRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(role.getRoleId());
		roleDto.setRoleName(role.getRoleName());
		return roleDto;
	}

}
