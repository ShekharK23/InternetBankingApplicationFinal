package com.cg.iba.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestSubmitDTO {
	
	@NotBlank(message = "User Name Cannot be Null")
	@NotNull(message = "User Name Cannot be Blank")
	@Size(max=10,message = "Maximum 10 Characters are allowed")
	@Size(min=3,message = "Minimum 03 Characters are allowed")
	private String userName;
	@NotBlank(message = "User Name Cannot be Null")
	@NotNull(message = "User Name Cannot be Blank")
	@Size(max=10,message = "Maximum 10 Characters are allowed")
	@Size(min=3,message = "Minimum 03 Characters are allowed")
	private String password;
	@Email(message = "Provide Correct Email Id")
	private String userEmailID;
	//private String token;
	private Role role;
	
}
