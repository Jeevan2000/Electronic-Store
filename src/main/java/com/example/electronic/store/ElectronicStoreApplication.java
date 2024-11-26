package com.example.electronic.store;

import com.example.electronic.store.entities.Role;
import com.example.electronic.store.entities.User;
import com.example.electronic.store.repositories.RoleRepository;
import com.example.electronic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElse(null);

		if(roleAdmin ==  null)
		{
			roleAdmin = new Role();
			roleAdmin.setRoleId(UUID.randomUUID().toString());
			roleAdmin.setName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);
		}

		Role roleNormal = roleRepository.findByName("ROLE_NORMAL").orElse(null);

		if(roleNormal == null)
		{
			roleNormal= new Role();
			roleNormal.setRoleId(UUID.randomUUID().toString());
			roleNormal.setName("ROLE_NORMAL");
			roleRepository.save(roleNormal);
		}

		User user = userRepository.findByEmail("jeevan08@gmail.com").orElse(null);
		if(user == null)
		{
			user = new User();
			user.setName("jeevan");
			user.setEmail("jeevan08@gmail.com");
			user.setPassword(passwordEncoder.encode("jeevan"));
			user.setRoles(List.of(roleAdmin));
			user.setUserId(UUID.randomUUID().toString());
			userRepository.save(user);

		}

	}
}
