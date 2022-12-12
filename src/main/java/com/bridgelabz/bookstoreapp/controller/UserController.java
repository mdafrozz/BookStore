package com.bridgelabz.bookstoreapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.model.UserModel;
import com.bridgelabz.bookstoreapp.service.IUserService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService iuserService;

	@Autowired
	TokenUtil tokenUtil;

	// Welcome message
	@GetMapping(value = { "/", "" })
	public String welcome() {
		return "Welcome to Book Store App..!!";
	}

	// insert data and generate Token
	@PostMapping(value = { "/register" })
	public ResponseEntity<ResponseDTO> AddUser(@Valid @RequestBody UserDTO userdto) {
		String token = iuserService.addUser(userdto);
		ResponseDTO responseDTO = new ResponseDTO("User Registered Successfully..!!!", token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}

	// Get all data
	@GetMapping("/getall")
	public List<UserModel> getAllUserDetails() {
		List<UserModel> userList = iuserService.getAllUsersData();
		return userList;
	}

	// Get User Data by ID
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<ResponseDTO> getUserById(@PathVariable int id) {
		UserModel userModel = iuserService.getUserDataById(id);
		ResponseDTO responseDTO = new ResponseDTO("User Details with ID: " + id, userModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get User Data by token
	@GetMapping("/getby/{token}")
	public ResponseEntity<ResponseDTO> getUserDetails(@PathVariable String token) {
		UserModel userModel = iuserService.getUserDataByToken(token);
		ResponseDTO respDTO = new ResponseDTO("Data with Token retrieved successfully", userModel);
		return new ResponseEntity<>(respDTO, HttpStatus.OK);
	}

	// Get User Data by Email Address
	@GetMapping("/getbyemail/{email}")
	public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String email) {
		UserModel userModel = iuserService.getUserDataByEmail(email);
		ResponseDTO responseDTO = new ResponseDTO("User Details with the Email Address: " + email, userModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Forgot Password
	@PostMapping("/forgotpassword/{email}/{newPassword}")
	public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email, @PathVariable String newPassword) {
		String response = iuserService.forgotPassword(email, newPassword);
		ResponseDTO responseDTO = new ResponseDTO("Password Reset Successfull", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Change password
	@PostMapping("/changepassword/{newPassword}")
	public ResponseEntity<ResponseDTO> changePassword(@RequestBody LoginDTO loginDTO,
			@PathVariable String newPassword) {
		String response = iuserService.changePassword(loginDTO, newPassword);
		ResponseDTO responseDTO = new ResponseDTO("Password Status:", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Updating the User Data using email Address
	@PutMapping("/update/{email}")
	public ResponseEntity<ResponseDTO> updateUserByEmailAddress(@PathVariable String email,
			@Valid @RequestBody UserDTO userDTO) {
		UserModel userModel = iuserService.updateUserDataByEmail(userDTO, email);
		ResponseDTO respDTO = new ResponseDTO("Data Updated Successfully", userModel);
		return new ResponseEntity<>(respDTO, HttpStatus.OK);
	}

	// Login check
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
		String response = iuserService.loginUser(loginDTO);
		ResponseDTO responseDTO = new ResponseDTO("Login Status:", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Delete User by User ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseDTO> deleteUserDataByID(@PathVariable int id) {
		int response = iuserService.deleteUser(id);
		ResponseDTO respDTO = new ResponseDTO("Deleted Successfully Id:", response);
		return new ResponseEntity<>(respDTO, HttpStatus.OK);
	}

	// Generate user token from id
	@PostMapping("/gen/{id}")
	public ResponseEntity<ResponseDTO> GenerateToken(@PathVariable int id) {
		String token = iuserService.generateToken(id);
		ResponseDTO responseDTO = new ResponseDTO("Token Generated Successfully!!!", token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}
}
