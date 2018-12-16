package com.dms.login.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.resources.ApplicationUser;
import com.dms.service.ServiceRole;
import com.dms.service.ServiceUser;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private ServiceUser serviceUser;

	@Autowired
	private ServiceRole serviceRole;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		log.info("User name in -- " + username);

		User user = serviceUser.getUserFor(username);
		log.info("User name from db -" +user.getFullName());
		if (user == null
				|| !(user.getEmail().equalsIgnoreCase(username))) {
			throw new BadCredentialsException("Username not found.");
		}

		if (password ==null || !password.equals(user.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}
		Collection<? extends GrantedAuthority> authorities = getAuthorities(serviceRole.getRole(user));

		ApplicationUser appUser = new ApplicationUser(username, password, true, true, true, true, authorities,
				user.getEmail());

		return new UsernamePasswordAuthenticationToken(appUser, password, authorities);

	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public Collection<? extends GrantedAuthority> getAuthorities(List<Role> role) {
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for (Role rl : role) {
			String roleString = "ROLE_" + rl.getRoleName().toLowerCase();
			log.info("roleString" + roleString);
			roles.add(new SimpleGrantedAuthority(roleString));
		}
		return roles;
	}

}
