package com.cg.iba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.dto.EMailDTO;
import com.cg.iba.dto.JWTResponseDTO;
import com.cg.iba.dto.UserLoginDTO;
import com.cg.iba.dto.UserRequestSubmitDTO;
import com.cg.iba.dto.UserResponseDTO;
import com.cg.iba.entity.EMail;
import com.cg.iba.entity.BankUser;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.security.JWTUtil;
import com.cg.iba.serviceimpl.EMailServiceImpl;
import com.cg.iba.serviceimpl.UserServiceImpl;
import com.cg.iba.util.UserDTOMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5005", "http://localhost:4200"},allowedHeaders = "*")
public class UserRestController {

	static int otp;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDTOMapper userDTO;

	@Autowired
	private EMailServiceImpl mailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@ApiOperation(value = "Login User", response = Contact.class)
	@PostMapping("/login")
	public ResponseEntity<JWTResponseDTO> doLogin(@RequestBody UserLoginDTO userEntry) throws Exception {
		System.out.println("----->> inside public/login " + userEntry);
		try {
			System.out.println(userEntry.getUserName()+ userEntry.getPassword());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userEntry.getUserName(), userEntry.getPassword()));
			System.out.println();
		} catch (Exception e) {
			throw new Exception("Bad credentials ");
		}
		UserDetails userDetails = userServiceImpl.loadUserByUsername(userEntry.getUserName());
		String token = jwtUtil.generateToken(userDetails);
		boolean isValid = token != null ? true : false;
		long userId = userServiceImpl.loadUserByUsernameforID(userEntry.getUserName());
		JWTResponseDTO jwtResponseDTO = new JWTResponseDTO(token, userEntry.getUserName(), isValid, userId);
		return new ResponseEntity<JWTResponseDTO>(jwtResponseDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Create new User", response = Contact.class)
	@PostMapping("/addnewuser")
	public ResponseEntity<UserResponseDTO> addNewUser(@RequestBody UserRequestSubmitDTO userdto)
			throws InvalidDetailsException {
		BankUser u = userDTO.setUserUsingDTO(userdto);
		String password = passwordEncoder.encode(u.getPassword());
		u.setPassword(password);
		BankUser user = userServiceImpl.addNewUser(u);
		UserResponseDTO dto = userDTO.getUserUsingDTO(user);
		return new ResponseEntity<UserResponseDTO>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "update information of user", response = Contact.class)
	@PutMapping("/updateuser")
	public ResponseEntity<UserResponseDTO> updateUserInfo(@RequestParam Long id,
			@RequestBody UserRequestSubmitDTO userdto) throws InvalidDetailsException {
		BankUser user = userDTO.setUserUsingDTO(userdto);
		BankUser u = userServiceImpl.updateUserInfo(id, user);
		UserResponseDTO dto1 = userDTO.getUserUsingDTO(u);
		return new ResponseEntity<UserResponseDTO>(dto1, HttpStatus.FOUND);
	}

	@ApiOperation(value = "Delete the User", response = Contact.class)
	@DeleteMapping("/removeuser")
	public ResponseEntity<Boolean> removeUserInfo(@RequestParam long userId) throws DetailsNotFoundException {
		boolean status = userServiceImpl.deleteUserInfo(userId);
		if (status) {
			return new ResponseEntity<Boolean>(status, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/verifyOtp") //
	public boolean verifyOtp(@RequestBody UserRequestSubmitDTO login) {
		otp = userServiceImpl.sendOtp();
		return false;
	}

	@PostMapping("/getByEmail") // done
	public boolean getApplicant(@RequestBody UserLoginDTO dto) {
		BankUser user = userServiceImpl.getUserByEmail(dto.getUserName());
		if (user != null) {
			return true;
		}
		return false;

	}
	
	@PostMapping("/send") //done
	public boolean  sendMail(@RequestBody EMail mailstructure) {
		System.out.println("--------------------"+mailstructure);
		otp = userServiceImpl.sendOtp();
		mailstructure.setBody(mailstructure.getBody()+" Here is Your otp for resetting Password - "+otp);
		mailService.sendMail( mailstructure);
		return true;
	}

	@PutMapping("/resetpassword") // done
	public boolean updatePassword(@RequestBody EMailDTO mail) {
		System.out.println(otp+"---------------------------"+mail.getOtp());
		System.out.println(mail.getEmail()+"---------"+mail.getPassword());
		if (otp == mail.getOtp()) {
			System.out.println("otp verified successfully" + mail.getEmail());
			BankUser applicant = userServiceImpl.updateApplicantPassword(mail.getEmail(), mail.getPassword());

			if (applicant != null) {
				return true;
			}
		}
		return false;
	}

}
