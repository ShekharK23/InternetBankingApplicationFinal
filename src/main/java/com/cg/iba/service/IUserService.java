package com.cg.iba.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

import com.cg.iba.entity.BankUser;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;

@Service
public interface IUserService extends UserDetailsService {

	public  BankUser addNewUser(BankUser user) throws InvalidDetailsException;
	public BankUser signIn(BankUser user)throws InvalidDetailsException;
	public BankUser signOut(BankUser user) throws InvalidDetailsException;
	public BankUser updateUserInfo(Long id,BankUser user) throws InvalidDetailsException;
	public boolean deleteUserInfo(long userId)throws DetailsNotFoundException;

	public BankUser getUserByEmail(String email);
	public int sendOtp();
	public BankUser updateApplicantPassword(String email, String password) ;
}
