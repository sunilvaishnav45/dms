package com.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dms.login.filter.CustomAuthenticator;

@Controller
public class HomeController {

	@Autowired
	private CustomAuthenticator customAuthenticator;
	
	@RequestMapping(value={"","/"}, method = RequestMethod.GET)
	public String homePage(Model model){
		if(customAuthenticator.hasRole("ROLE_admin")){
			return "redirect:/admin";
		}else if(customAuthenticator.hasRole("ROLE_user")){
			return "redirect:/user";
		}else{
			return "unautharised";
		}
	}
}
