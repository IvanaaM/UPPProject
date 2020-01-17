package com.ftn.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.RoleName;
import com.ftn.model.Role;
import com.ftn.model.UserCustom;

import com.ftn.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public List<UserCustom> getReviewers(){
		
		List<UserCustom> users = new ArrayList<UserCustom>();
		
		for (UserCustom user : userRepository.findAll()) {
			for (Role role : user.getRoles()) {
				if(role.getName().equals(RoleName.ROLE_REVIEWER)) {
					users.add(user);
				}
			}
		}
		
		return users;
		
	}
	
	public List<UserCustom> getEditors(String name){
		
		List<UserCustom> users = new ArrayList<UserCustom>();
		
		UserCustom u = userRepository.findByUsername(name);
		
		for (UserCustom user : userRepository.findAll()) {
			if(user.getUsername() != name) {
			for (Role role : user.getRoles()) {
				if(role.getName().equals(RoleName.ROLE_EDITOR)) {
					users.add(user);
				}
			}
			}
		}
		
		return users;
		
	}

}
