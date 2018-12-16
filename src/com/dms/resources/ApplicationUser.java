package com.dms.resources;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ApplicationUser extends User{
	
	private static final long serialVersionUID = 1L;
	private String email;

	public ApplicationUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,String email) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	
		this.email =email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
}
