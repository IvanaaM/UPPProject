package com.ftn.configuration;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.ftn.enums.RoleName;
import com.ftn.model.Role;
import com.ftn.model.UserCustom;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.ScientificAreaRepository;
import com.ftn.repository.UserRepository;

@Component
public class Data implements ApplicationRunner {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ScientificAreaRepository saRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		// da li je admin tu -> admin-marko
		List<User> users = identityService.createUserQuery().userIdIn("marko").list();
		if(users.isEmpty() ) {
			
			User admin = identityService.newUser("marko");
			admin.setEmail("marko@mail.com");
			admin.setFirstName("Marko");
			admin.setLastName("Maric");
			admin.setPassword("marko");
			identityService.saveUser(admin);
		}
			UserCustom uc = new UserCustom();
			
			uc.setFirstName("Marko");
			uc.setLastName("Maric");
			uc.addRole(roleRepository.findByName(RoleName.ROLE_ADMIN));
			uc.setActive(true);
			uc.setState("Srbija");
			uc.setCity("Novi Sad");
			uc.setTitle("T1");
			
			String salt = BCrypt.gensalt();

			String passwordHashed = BCrypt.hashpw("marko", salt);
			uc.setPassword(passwordHashed);
			uc.setEmail("marko@mail.com");
			uc.setUsername("marko");
			
			userRepository.save(uc);
			

			List<User> users2 = identityService.createUserQuery().userIdIn("anja").list();
			
			if (users2.isEmpty()) {
				
				User ured = identityService.newUser("anja");
				ured.setEmail("anja@mail.com");
				ured.setFirstName("Anja");
				ured.setLastName("Anjic");
				ured.setPassword("anja");
				identityService.saveUser(ured);
				
			}
			
			UserCustom uc2 = new UserCustom();
			
			uc2.setFirstName("Anja");
			uc2.setLastName("Anjic");
			uc2.addRole(roleRepository.findByName(RoleName.ROLE_EDITOR));
			uc2.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc2.setActive(true);
			uc2.setState("Srbija");
			uc2.setCity("Novi Sad");
			uc2.setTitle("T4");
			

			
			uc2.addArea(saRepository.findByName("Biologija"));
			
			String salt2 = BCrypt.gensalt();

			String passwordHashed2 = BCrypt.hashpw("anja", salt2);
			uc2.setPassword(passwordHashed2);
			uc2.setEmail("anja@mail.com");
			uc2.setUsername("anja");
			
			userRepository.save(uc2);
			
			UserCustom uc3 = new UserCustom();
			
			uc3.setFirstName("Maja");
			uc3.setLastName("Majic");
			uc3.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc3.setActive(true);
			uc3.setState("Srbija");
			uc3.setCity("Novi Sad");
			uc3.setTitle("T6");

			uc3.addArea(saRepository.findByName("Matematika"));
			uc3.addArea(saRepository.findByName("Astronomija"));
			
			String salt3 = BCrypt.gensalt();

			String passwordHashed3 = BCrypt.hashpw("ivana", salt3);
			uc3.setPassword(passwordHashed3);
			uc3.setEmail("maja@mail.com");
			uc3.setUsername("maja");
			
			userRepository.save(uc3);
			
			UserCustom uc4 = new UserCustom();
			
			uc4.setFirstName("Ivana");
			uc4.setLastName("Ivanovic");
			uc4.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc4.setActive(true);
			uc4.setState("Srbija");
			uc4.setCity("Novi Sad");
			uc4.setTitle("T6");
			
			uc4.addArea(saRepository.findByName("Matematika"));
			uc4.addArea(saRepository.findByName("Astronomija"));
			uc4.addArea(saRepository.findByName("Biologija"));
			
			String salt4 = BCrypt.gensalt();

			String passwordHashed4 = BCrypt.hashpw("ivana", salt4);
			uc4.setPassword(passwordHashed4);
			uc4.setEmail("ivana@mail.com");
			uc4.setUsername("ivana");
			
			userRepository.save(uc4);
			
			UserCustom uc5 = new UserCustom();
			
			uc5.setFirstName("Sara");
			uc5.setLastName("Saric");
			uc5.addRole(roleRepository.findByName(RoleName.ROLE_EDITOR));
			uc5.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc5.setActive(true);
			uc5.setState("Srbija");
			uc5.setCity("Novi Sad");
			uc5.setTitle("T3");
		
			uc5.addArea(saRepository.findByName("Matematika"));
			uc5.addArea(saRepository.findByName("Astronomija"));
			uc5.addArea(saRepository.findByName("Biologija"));
			
			String salt5 = BCrypt.gensalt();

			String passwordHashed5 = BCrypt.hashpw("sara", salt5);
			uc5.setPassword(passwordHashed5);
			uc5.setEmail("sara@mail.com");
			uc5.setUsername("sara");
			
			userRepository.save(uc5);
			
			UserCustom uc6 = new UserCustom();
			
			uc6.setFirstName("Dunja");
			uc6.setLastName("Dunjic");
			uc6.addRole(roleRepository.findByName(RoleName.ROLE_EDITOR));
			uc6.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc6.setActive(true);
			uc6.setState("Srbija");
			uc6.setCity("Novi Sad");
			uc6.setTitle("T9");
		
			uc6.addArea(saRepository.findByName("Matematika"));
			uc6.addArea(saRepository.findByName("Astronomija"));
			uc6.addArea(saRepository.findByName("Biologija"));
			
			String salt6 = BCrypt.gensalt();

			String passwordHashed6 = BCrypt.hashpw("dunja", salt6);
			uc6.setPassword(passwordHashed6);
			uc6.setEmail("dunja@mail.com");
			uc6.setUsername("dunja");
			
			userRepository.save(uc6);
			
			
			


	}
	


}
