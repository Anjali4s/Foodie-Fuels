package com.curiouscoders.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curiouscoders.dto.UserDTO;
import com.curiouscoders.exception.UserNotFoundException;
import com.curiouscoders.model.User;
import com.curiouscoders.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ConversionUtils conversion;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = conversion.convertToUserEntity(userDTO);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); 
        user.setActive(userDTO.getActive());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepo.save(user); // Save user to DB
        return conversion.convertToUserDTO(savedUser); // Convert saved entity to DTO
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)); // uses ID-specific message
        return conversion.convertToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("email", email)); // Uses field-specific message
        return conversion.convertToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> list = userRepo.findAll();
        list.sort((u1, u2) -> u1.getId().compareTo(u2.getId())); // Sort by ID
        return list.stream()
                   .map(conversion::convertToUserDTO)
                   .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)); // Uses ID-specific message
        user.setEmail(userDTO.getEmail());
        user.setActive(userDTO.getActive());
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
        return conversion.convertToUserDTO(user);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(false);
        userRepo.save(user);
    }

    @Override
    public void activateUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(true);
        userRepo.save(user);
    }

    @Override
    public void changeUserPassword(Long id, String newPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setPassword(newPassword);
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
    }
}

























//public class UserService implements IUserService {
//	@Autowired
//	private UserRepository userRepo;
//	@Autowired
//    private ConversionUtils conversion;
//
//	@Override
//	public User createUser(UserDTO userDTO) {
//		User user=conversion.convertToUserEntity(userDTO);
//		user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());  // Default password (user will change it on first login)
//        user.setActive(userDTO.isActive());
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
//        return userRepo.save(user);
//	}
//
//	@Override
//	public UserDTO getUserById(Long id) {
//		User user=userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found with id: "+id));
//		return conversion.convertToUserDTO(user);
//	}
//
//	@Override
//	public UserDTO getUserByEmail(String email) {
//		User user=userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found with email: "+email));
//		return conversion.convertToUserDTO(user);
//	}
//
//	@Override
//	public List<UserDTO> getAllUsers() {
//		List<User> list=userRepo.findAll();
//		list.sort((t1,t2)->t1.getId().compareTo(t2.getId()));
//		List<UserDTO> userDTO=list.stream()
//				                  .map(conversion::convertToUserDTO)
//				                  .collect(Collectors.toList());
//		return userDTO;
//	}
//
//	@Override
//	public UserDTO updateUser(Long id, UserDTO userDTO) {
//		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        user.setEmail(userDTO.getEmail());  // Update email
//        user.setActive(userDTO.isActive()); // Update active status
//        user.setUpdatedAt(LocalDateTime.now()); // Update last modified time
//        userRepo.save(user);
//        return conversion.convertToUserDTO(user);
//	}
//
//	@Override
//	public void deactivateUser(Long id) {
//		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//		user.setActive(false);
//		userRepo.save(user);
//		
//	}
//
//	@Override
//	public void activateUser(Long id) {
//		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//		user.setActive(true);
//		userRepo.save(user);	
//	}
//
//	@Override
//	public void changeUserPassword(Long id, String newPassword) {
//		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//		user.setPassword(newPassword);  // Set new password
//        user.setUpdatedAt(LocalDateTime.now()); // Update the last modified time
//        userRepo.save(user);
//		
//	}
//
//}
