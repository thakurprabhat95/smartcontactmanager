package com.scm;

import java.util.Arrays;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repsitories.UserRepo;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setName("admin");
		user.setEmail("admin@gmail.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoleList(Arrays.asList(AppConstants.ROLE_USER));
		user.setEmailVerified(true);
		user.setEnabled(true);
		user.setAbout("This is dummy user created initially");
		user.setPhoneVerified(true);

		// userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {},() -> {
		// userRepo.save(user);
		// System.out.println("user created");
		// });
		User user1 = userRepo.findByEmail("admin@gmail.com")
				.orElseThrow(() -> new ResourceNotFoundException("user is not found"));
		userRepo.save(user1);

	}
}
