package com.ec.pichincha.vos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents plain object of an User
 * 
 * @author kleverhidalgo
 */
@Getter
@Setter
public class UserVo {
	private String username;
	private String password;
	private List<?> authorities;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean enabled;
}
