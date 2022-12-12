package com.bridgelabz.bookstoreapp.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {
	
	@NotNull(message = "UserID cannot be NULL")
	private int user_id;
	@NotNull(message = "BookID cannot be NULL")
	private int book_id;
	@NotNull(message = "Please specify valid book quantity")
	private int quantity;
}
