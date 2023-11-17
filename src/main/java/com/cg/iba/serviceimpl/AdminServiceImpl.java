package com.cg.iba.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.controller.AdminRestController;
import com.cg.iba.dto.AdminRequestSubmitDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Admin;
import com.cg.iba.entity.BankUser;
import com.cg.iba.entity.enums.Role;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAdminRepository;
import com.cg.iba.repository.IUserRepository;
import com.cg.iba.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {
	
	@Autowired
	IAdminRepository adminRepository;
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public Admin addAdmin(Admin admin) throws InvalidDetailsException {
		Admin a = adminRepository.save(admin);
		return a;
	}

	@Override
	public Admin findAdminById(long adminId) throws DetailsNotFoundException {
		Admin a = adminRepository.findById(adminId)
				.orElseThrow(()-> new DetailsNotFoundException("Details Not Found For AdminID.", AdminServiceImpl.class+""));
		return a;
	}

	@Override
	@Transactional
	public Admin updateAdmin(long adminId, AdminRequestSubmitDTO dto) throws InvalidDetailsException {
		Admin a = adminRepository.findById(adminId)
				.orElseThrow(()-> new InvalidDetailsException("Invalid Details Entered.", AdminServiceImpl.class+""));
		
		a.setAdminContact(dto.getAdminContact());
		a.setAdminEmailId(dto.getAdminEmailId());
		a.setAdminName(dto.getAdminName());

		Admin updated = adminRepository.save(a);
		return updated;
	}

	@Override
	public boolean removeAdmin(long adminId) throws DetailsNotFoundException {
		Admin a = adminRepository.findById(adminId)
				.orElseThrow(() -> new DetailsNotFoundException("Admin Not found for AdminId.", AdminServiceImpl.class+""));
		adminRepository.deleteById(a.getAdminId());
		return true;
	}

	@Override
	public List<Admin> listAllAdmins() throws EmptyListException {
		List<Admin> allAdmins = adminRepository.findAll();
		if(!allAdmins.isEmpty()) {
			return allAdmins;
		} else {
			throw new EmptyListException("List is Empty. Admin Not Present.", AdminRestController.class+"");
		}
	}
	
	@Override
	@Transactional
	public Admin allocateUserToAdmin(long adminId, long userId)
			throws InvalidAccountException, InvalidDetailsException {
		Admin admin = adminRepository.findById(adminId).orElseThrow(
				() -> new InvalidDetailsException("Enter Correct Admin Number.", AdminServiceImpl.class + ""));
		BankUser user = userRepository.findById(userId).orElseThrow(
				() -> new InvalidDetailsException("Enter Correct Details of User.", AdminServiceImpl.class + ""));
		if (admin != null && user != null && user.getRole() == Role.ADMIN) {
			admin.setUser(user);
			return admin;
		}
		return null;
	}

}
