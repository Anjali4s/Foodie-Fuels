package com.curiouscoders.service;

import java.util.List;

import com.curiouscoders.dto.UserDTO;
import com.curiouscoders.model.User;

public interface IUserService {
	
	UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deactivateUser(Long id);

    void activateUser(Long id);

    void changeUserPassword(Long id, String newPassword);

}
