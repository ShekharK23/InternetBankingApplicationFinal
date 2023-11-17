package com.cg.iba.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.cg.iba.dto.NomineeRequestSubmitDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Nominee;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.INomineeRepository;
import com.cg.iba.service.INomineeService;

@Service
public class NomineeServiceImpl implements INomineeService {

	@Autowired
	INomineeRepository nomineeRepository;

	@Autowired
	IAccountRepository accountRepository;

	@Override
	public Nominee addNominee(Nominee nominee) throws InvalidDetailsException {
		Nominee savedNominee = nomineeRepository.save(nominee);
		if (savedNominee != null) {
			return savedNominee;
		} else {
			throw new InvalidDetailsException("Details of nominee provided are invalid", NomineeServiceImpl.class + "");
		}

	}

	@Override
	@Transactional
	public Nominee updateNominee(long id, NomineeRequestSubmitDTO nominee)
			throws InvalidDetailsException, DetailsNotFoundException {

		Nominee existingNominee = nomineeRepository.findById(id)
				.orElseThrow(() -> new DetailsNotFoundException("Requested id " + id + " is not found",
						NomineeServiceImpl.class + ""));
		if (nominee != null) {
			if (nominee.getName() != null) {
				existingNominee.setName(nominee.getName());
			}
			if (nominee.getGovtId() != null) {
				existingNominee.setGovtId(nominee.getGovtId());
			}
			if (nominee.getGovtIdType() != null) {
				existingNominee.setGovtIdType(nominee.getGovtIdType());
			}
			if (nominee.getPhoneNo() != null) {
				existingNominee.setPhoneNo(nominee.getPhoneNo());
			}
			return nomineeRepository.save(existingNominee);
		}

		else {
			throw new InvalidDetailsException("Details are invalid please upload correct details ",
					NomineeServiceImpl.class + "");
		}

	}

	@Override
	public boolean deleteNominee(long nomineeId) throws DetailsNotFoundException {
		Nominee nominee = nomineeRepository.findById(nomineeId)
				.orElseThrow(() -> new DetailsNotFoundException("Requested id " + nomineeId + " is not found",
						NomineeServiceImpl.class + ""));
		nomineeRepository.deleteById(nomineeId);
		return true;

	}

	@Override
	public Nominee findNomineeById(long nomineeId) throws DetailsNotFoundException {

		Nominee nominee = nomineeRepository.findById(nomineeId)
				.orElseThrow(() -> new DetailsNotFoundException("You've Entered wrong Nominee Id " + nomineeId,
						NomineeServiceImpl.class + ""));
		return nominee;
	}

	@Override
	public List<Nominee> listAllNominees(long accountid) throws InvalidAccountException, EmptyListException {
		Account account = accountRepository.findById(accountid)
				.orElseThrow(() -> new InvalidAccountException("You've Entered wrong Account Id " + accountid,
						NomineeServiceImpl.class + ""));

		List<Nominee> allNominees = account.getNominees();
		if (!allNominees.isEmpty()) {
			return allNominees;
		} else {
			throw new EmptyListException("No nominee are linked with current account", NomineeServiceImpl.class + "");
		}

	}

}
