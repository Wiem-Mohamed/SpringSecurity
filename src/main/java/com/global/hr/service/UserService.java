package com.global.hr.service;

import com.global.hr.entity.User;
import com.global.hr.entity.UserRecord;
import com.global.hr.exception.UserAlreadyExistsException;
import com.global.hr.exception.UserNotFoundException;
import com.global.hr.repository.IUserService;
import com.global.hr.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User add(User user) throws UserAlreadyExistsException {
		Optional<User> theUser = userRepository.findByEmail(user.getEmail());
		if (theUser.isPresent()){
			throw new UserAlreadyExistsException ("A user with " +user.getEmail() +" already exists");
		}
		user.setPassword ( passwordEncoder.encode ( user.getPassword () ) );
		return userRepository.save(user);
	}
//The mapping operation in this code is used to
// transform each user object retrieved from the
// userRepository into a UserRecord object.
@Override
public List<UserRecord> getAllUsers() {
	return userRepository.findAll()
			.stream()
			.map(user -> new UserRecord(
					user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getEmail())).collect( Collectors.toList());
}

	@Override
	@Transactional
	public void delete(String email) {
		userRepository.deleteByEmail(email);
	}


	@Override
	public User getUser(String email) throws UserNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException ("User not found"));
	}

	@Override
	public User update(User user) {
		user.setRoles(user.getRoles());
		return userRepository.save(user);
	}
}

