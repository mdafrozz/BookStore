package com.bridgelabz.bookstoreapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
	
	@Pattern(regexp = "^[a-z0-9]{1,20}([_.+-][a-z0-9]+)?@[a-z0-9]+.[a-z]{2,3}(.[a-z]{2})?$", message = "Enter a valid Email-id")
	 public String email;
	@NotEmpty(message = "Password Cannot be Empty")
	 public String password;
}
