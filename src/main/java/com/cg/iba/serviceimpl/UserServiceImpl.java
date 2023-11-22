package com.cg.iba.serviceimpl;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.iba.entity.MyUserDetails;
import com.cg.iba.entity.BankUser;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.IAdminRepository;
import com.cg.iba.repository.IUserRepository;
import com.cg.iba.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IAccountRepository accountRepository;
	
	@Autowired
	IAdminRepository adminRepository;


	@Override
	public BankUser addNewUser(BankUser user) throws InvalidDetailsException {
		if (user != null) {
			BankUser savedUser = userRepository.save(user);
			return savedUser;
		} else {
			throw new InvalidDetailsException("Invalid Details.", UserServiceImpl.class+"");
		}
	}

	@Override
	public BankUser signIn(BankUser user) throws InvalidDetailsException {
		BankUser u = userRepository.findById(user.getUserId())
				.orElseThrow(() -> new InvalidDetailsException("Invalid UserID Entered.", UserServiceImpl.class+""));
		if(u.getPassword().equals(user.getPassword()) && u.getRole().equals(user.getRole())) {
			return u;
		} else {
			throw new InvalidDetailsException("Enter Correct Password.", UserServiceImpl.class+"");
		}
	}

	@Override
	public BankUser signOut(BankUser user) throws InvalidDetailsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public BankUser updateUserInfo(Long id, BankUser user) throws InvalidDetailsException {
		BankUser userDetails = userRepository.findById(id)
				.orElseThrow(() -> new InvalidDetailsException("Invalid UserId Entered.", UserServiceImpl.class+""));
		userDetails.setPassword(user.getPassword());
		return userDetails;
	}

	@Override
	public boolean deleteUserInfo(long userId) throws DetailsNotFoundException {
		BankUser user = userRepository.findById(userId)
				.orElseThrow(() -> new DetailsNotFoundException("Details Not found for entered UserId.", UserServiceImpl.class+""));
		if(user != null) {
			userRepository.deleteById(user.getUserId());
			return true;
		} else {
			return false;
		}
	}

	@Override  // from UserDetailsService
	public UserDetails loadUserByUsername(String username) throws 
	      UsernameNotFoundException {
		BankUser user =  userRepository.getBankUserByUserName(username);
		return new MyUserDetails(user);
		
	}
	
	public long loadUserByUsernameforID(String username) {
		BankUser user = userRepository.getBankUserByUserName(username);
		long userId = user.getUserId();
		return userId;
	}
	
	@Override
	public BankUser getUserByEmail(String email) {
		BankUser u = userRepository.getBankUserByUserEmailID(email);
		return u;
	}

	@Override
	public int sendOtp() {
		Random rnd = new Random();
		int otp = rnd.nextInt(999999);
		return otp;
	}

	@Override
	@Transactional
	public BankUser updateApplicantPassword(String email, String password) {
		BankUser user = userRepository.getBankUserByUserEmailID(email);
		if (user != null) {
			user.setPassword(password);
			return user;
		}
		return null;
	}
}
