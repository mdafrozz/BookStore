package com.bridgelabz.bookstoreapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.UserModel;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.util.EmailSenderService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	EmailSenderService emailSender;

	// Generated token after saving data and sent email
	public String addUser(UserDTO userDTO) throws BookStoreException {
		Optional<UserModel> model = Optional.ofNullable(userRepo.findByEmailAddress(userDTO.getEmail()));
		if (model.isPresent()) {
			return "Email already exists";
		} else {
			UserModel userModel = new UserModel(userDTO);
			userRepo.save(userModel);
			String token = tokenUtil.createToken(userModel.getUserId());
			// email body
			String data = "Hi " + userModel.getFirstName()
					+ " \uD83D\uDE00,\n\nThank you for choosing BookStore App \uD83E\uDD1D\n\n"
					+ "Your account is successfully created. Please find the details below\n\n" + "USER DETAILS: \n"
					+ "\uD83D\uDC71 First Name: " + userModel.getFirstName() + ",\t" + "Last Name: "
					+ userModel.getLastName() + "\n\uD83C\uDFE0 Address: " + userModel.getAddress() + "\n"
					+ "\uD83D\uDCE7 Email Address: " + userModel.getEmail() + "\n\uD83D\uDCC6 DOB: "
					+ "\n\uD83D\uDD11 Password: " + userModel.getPassword()
					+ "\n\uD83E\uDE99 Token :" + token + "\n\nRegards \uD83D\uDE4F,\nBookStore Team";
			// sending email
			//emailSender.sendEmail(userModel.getEmail(), "Registered Successfully", data);
			return token;
		}
	}

	// Get all User Details list
	public List<UserModel> getAllUsersData() {
		List<UserModel> userList = userRepo.findAll();
		if (userList.isEmpty()) {
			throw new BookStoreException("No User Registered yet!!!!");
		} else
			return userList;
	}

	// Get the user data by id
	public UserModel getUserDataById(int id) {
		UserModel userModel = userRepo.findById(id).orElse(null);
		if (userModel != null) {
			return userModel;
		} else
			throw new BookStoreException("UserID: " + id + ", does not exist");
	}

	// Get User Details by Token
	public UserModel getUserDataByToken(String token) {
		try {
			int id = tokenUtil.decodeToken(token);
			return userRepo.findById(id).stream().filter(data -> data.getUserId() == id).findFirst()
					.orElseThrow(() -> new BookStoreException("Invalid User/Token"));
		} catch (Exception e) {
			throw new BookStoreException("Invalid User/Token");
		}
	}

	// Get the User Details by Email Address
	public UserModel getUserDataByEmail(String email) {
		UserModel userModel = userRepo.findByEmailAddress(email);
		if (userModel != null) {
			return userModel;
		} else
			throw new BookStoreException("Email Address: " + email + ", does not exist");
	}

	// forgot password
	public String forgotPassword(String email, String newPassword) {
		UserModel userModel = userRepo.findByEmailAddress(email);
		if (userModel != null) {
			userModel.setPassword(newPassword);// changing password
			userRepo.save(userModel);
			return "Hi " + userModel.getFirstName() + ", your password has been successfully reset.";
		} else
			throw new BookStoreException("Invalid Email Address");
	}

	// reset password
	public String changePassword(LoginDTO loginDTO, String newPassword) {
		UserModel userModel = userRepo.findByEmailAddress(loginDTO.getEmail());
		if (userModel != null) {
			if (userModel.getPassword().equals(loginDTO.getPassword())) {
				userModel.setPassword(newPassword); // changing password
			} else {
				return "Incorrect old password!!";
			}
			// Sending Email
			//emailSender.sendEmail(userModel.getEmail(), "Password Change!", "Password Changed Successfully!");
			userRepo.save(userModel);
			return "Password Changed Successfully!";
		} else
			return "Invalid Email Address";
	}

	// Update data by email address
	public UserModel updateUserDataByEmail(UserDTO userDTO, String email) {
		UserModel userModel = userRepo.findByEmailAddress(email);
		if (userModel != null) {
			userModel.setFirstName(userDTO.getFirstName());
			userModel.setLastName(userDTO.getLastName());
			userModel.setAddress(userDTO.getAddress());
			userModel.setEmail(userDTO.getEmail());
			userModel.setPassword(userDTO.getPassword());
			// email body
			String data = "Hi " + userModel.getFirstName() + " \uD83D\uDE07"
					+ ",\n\nYour details are successfully updated \uD83D\uDC4D\n\n" + "UPDATED DETAILS: \n"
					+ "\uD83D\uDC71 First Name: " + userModel.getFirstName() + ",\t" + "Last Name: "
					+ userModel.getLastName() + "\n\uD83C\uDFE0 Address: " + userModel.getAddress() + "\n"
					+ "\uD83D\uDCE7 Email Address: " + userModel.getEmail() + "\n\uD83D\uDCC6 DOB: "
					+ "\n\uD83D\uDD11 Password: " + userModel.getPassword()
					+ "\n\nRegards \uD83D\uDE4F,\nBookStore Team";
			// sending email
			//emailSender.sendEmail(userModel.getEmail(), "Data Updated!!!", data);
			return userRepo.save(userModel);
		} else
			throw new BookStoreException("Invalid Email Address: " + email);
	}

	// Login check
	public String loginUser(LoginDTO loginDTO) {
		UserModel userModel = userRepo.findByEmailAddress(loginDTO.getEmail());
		if (userModel != null) {
			if (userModel.getPassword().equals(loginDTO.getPassword())) {
				return userModel.getEmail();
			} else
				throw new BookStoreException("Login Failed, Wrong Password!!!");
		} else
			throw new BookStoreException("Login Failed, Invalid emailId or password!!!");
	}

	// Delete data by userId
	public int deleteUser(int id) {
		UserModel userModel = userRepo.findById(id).orElse(null);
		if (userModel != null) {
			String msg = "Hey " + userModel.getFirstName()
					+ " \uD83D\uDE07,\n\n Your account has been successfully removed" + " from BookStore App..!!\n"
					+ "Hope to see you again soon \uD83D\uDE1E!\n\n" + "Thank you,\n" + "BookStore Team";
			// sending email
			//emailSender.sendEmail(userModel.getEmail(), "User Removed!!!", msg);
			userRepo.deleteById(id);
		} else
			throw new BookStoreException("Error: Cannot find User ID " + id);
		return id;
	}

	// Generate token using userID
	public String generateToken(int id) {
		UserModel model = userRepo.findById(id).get();
		if (model != null) {
			String token = tokenUtil.createToken(model.getUserId());
			return token;
		} else
			throw new BookStoreException("Invalid ID");
	}

	// Validate User using user token
	public boolean validateUser(String token) {
		try {
			int id = tokenUtil.decodeToken(token);
			do {
				return true;
			} while (userRepo.findById(id).isPresent());
		} catch (Exception e) {
			throw new BookStoreException("Invalid User/ Token");
		}

	}
}
