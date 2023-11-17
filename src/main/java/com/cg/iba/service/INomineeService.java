package com.cg.iba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.iba.dto.NomineeRequestSubmitDTO;
import com.cg.iba.entity.Nominee;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;

@Service
public interface INomineeService {

	public Nominee addNominee(Nominee nominee) throws InvalidDetailsException;
	public Nominee updateNominee(long id,NomineeRequestSubmitDTO nominee) throws InvalidDetailsException, DetailsNotFoundException;
	public boolean deleteNominee(long nomineeId) throws DetailsNotFoundException ;
	public Nominee findNomineeById(long nomineeId)throws DetailsNotFoundException ;
	public List<Nominee> listAllNominees(long accountid) throws InvalidAccountException, EmptyListException ;
}
