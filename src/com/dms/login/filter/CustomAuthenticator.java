package com.dms.login.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dms.database.mapping.User;
import com.dms.service.ServiceUser;

@Component("customAuthenticator")
public class CustomAuthenticator {
	
	@Autowired
	private ServiceUser serviceUser;
	
	public boolean hasRole(String role){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean hasRole =false;
	    for (GrantedAuthority userRole : auth.getAuthorities()) {
	    	if(userRole.getAuthority().equals(role)){
	    		hasRole =true;
	    	}
		}
		return hasRole;
	}
	
	public User getUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    System.out.println(username);
		User user = null;
		if(username !=null)
			user = serviceUser.getUserFor(username);
		
		return user;
	}

}
