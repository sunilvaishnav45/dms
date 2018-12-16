package com.dms.login.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dms.database.mapping.Role;
import com.dms.database.mapping.User;
import com.dms.service.ServiceRole;
import com.dms.service.ServiceUser;

public class CustomeUserDetailsService implements UserDetailsService {

	@Autowired
	private ServiceUser serviceUser;
	
	@Autowired
	private ServiceRole serviceRole;

	public static final Logger log = Logger.getLogger(CustomeUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = serviceUser.getUserFor(username);

		List<GrantedAuthority> authorities = buildUserAuthority(serviceRole.getRole(user));

		return buildUserForAuthentication(user, authorities);
	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isActive(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		for (Role role : roles) {
			setAuths.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
			log.info("ROLE_" + role.getRoleName());
		}
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
		result.addAll(setAuths);
		return result;
	}

}
