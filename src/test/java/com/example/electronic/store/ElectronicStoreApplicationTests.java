package com.example.electronic.store;

import com.example.electronic.store.entities.User;
import com.example.electronic.store.repositories.UserRepository;
import com.example.electronic.store.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElectronicStoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserRepository userRepository;

//	@Test
//	void testToken()
//	{
//		User user = userRepository.findByEmail("jeevan08@gmail.com").get();
//
//		String token = jwtHelper.generateToken(user);
//		System.out.println(token);
//
//		System.out.println("getting username from token");
//
//		System.out.println(jwtHelper.isTokenExpired(token));
//
//		System.out.println(jwtHelper.getUsernameFromToke(token));
//
//
//
//
//	}

}
