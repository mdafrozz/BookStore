package com.bridgelabz.bookstoreapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Invalid First Name(First Letter Should be in Upper Case and min 3 Characters.)")
	private String firstName;
    
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{1,}$", message="Invalid Last Name(First Letter Should be in Upper Case")
	private String lastName;
    
	@NotEmpty(message = "Address Cannot be Empty")
	private String address;

	@Pattern(regexp = "^[a-z0-9]{1,20}([_.+-][a-z0-9]+)?@[a-z0-9]+.[a-z]{2,3}(.[a-z]{2})?$", message = "Enter a valid Email-id")
	private String email;
	    
	@NotEmpty(message = "Password Cannot be Empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&*()-+=])([a-zA-Z0-9@._-]).{8,}$", message="Invalid Password (1.Must have atleast one upper case character" +
            " 2.Must contain atleast one numeric value 3.Must contain a special symbol 4. Must have a lower case character 5.Should have minimum 8 characters)")
	private String password;

}
