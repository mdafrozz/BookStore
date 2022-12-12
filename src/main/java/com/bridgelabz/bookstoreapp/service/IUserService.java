package com.bridgelabz.bookstoreapp.service;

import java.util.List;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.model.UserModel;

public interface IUserService {
	
    public String addUser(UserDTO userDTO);
    public UserModel getUserDataByToken(String token);
    public String loginUser(LoginDTO loginDTO);
    public String changePassword(LoginDTO loginDTO, String newPassword);
    public String forgotPassword(String email, String password);
    public List<UserModel> getAllUsersData();
    public UserModel getUserDataById(int id);
    public UserModel getUserDataByEmail(String email);
    public UserModel updateUserDataByEmail(UserDTO userDTO, String email);
    public int deleteUser(int id);
    public String generateToken(int id);
    public boolean validateUser(String token);
}
