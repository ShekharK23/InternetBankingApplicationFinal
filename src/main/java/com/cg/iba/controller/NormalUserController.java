package com.cg.iba.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.dto.AccountResponseDTO;
import com.cg.iba.dto.BeneficiaryResponseDTO;
import com.cg.iba.dto.DebitCardResponseDTO;
import com.cg.iba.dto.NomineeResponseDTO;
import com.cg.iba.dto.TransactionResponseDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Beneficiary;
import com.cg.iba.entity.DebitCard;
import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.Policy;
import com.cg.iba.entity.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.repository.IDebitCardRepository;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.service.IAccountService;
import com.cg.iba.service.IBeneficiaryService;
import com.cg.iba.service.IDebitCardService;
import com.cg.iba.service.INomineeService;
import com.cg.iba.service.ITransactionService;
import com.cg.iba.serviceimpl.AccountServiceImpl;
import com.cg.iba.util.AccountDTOMapper;
import com.cg.iba.util.BeneficiaryDTOMapper;
import com.cg.iba.util.DebitCardResponseDTOConverter;
import com.cg.iba.util.NomineeDTOMapper;
import com.cg.iba.util.TransactionDTOMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;

@RestController
@RequestMapping("/normalUser")
@Validated
@CrossOrigin(origins = {"http://localhost:5005", "http://localhost:4200"},allowedHeaders = "*")
public class NormalUserController {

	@Autowired
	IAccountService accountService;

	@Autowired
	INomineeService nomineeService;

	@Autowired
	NomineeDTOMapper nomineeDTOMapper;

	@Autowired
	IBeneficiaryService beneficiaryService;

	@Autowired
	BeneficiaryDTOMapper beneficiaryDTO;

	@Autowired
	IDebitCardService debitCardService;

	@Autowired
	IDebitCardRepository debitCardRepository;

	@Autowired
	DebitCardResponseDTOConverter debitCardResponseConverter;

	@Autowired
	ITransactionService transactionService;

	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	TransactionDTOMapper transactionDTOMapper;
	
	@Autowired
	AccountDTOMapper accountDTOMapper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = " To Get Particular account based on Account Id", response = Contact.class)
	@GetMapping("/getAccountById") // to be checked
	public ResponseEntity<Account> getAccountById(@RequestParam long accountId) {
		try {
			Account account = accountService.findAccountById(accountId);
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		} catch (InvalidAccountException e) {
			System.out.println(e);
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Nominee Functionality
	 */
	@ApiOperation(value = "Finding the nominees based on nominee id", response = Contact.class)
	@GetMapping("/findNomineeById") // Working
	public ResponseEntity<NomineeResponseDTO> findNomineeById(@RequestParam long nomineeId)
			throws DetailsNotFoundException {

		logger.info("Attempting to find nominee by ID: {}", nomineeId);
		Nominee savedNominee = nomineeService.findNomineeById(nomineeId);

		logger.info("Nominee found successfully with ID: {}", nomineeId);
		NomineeResponseDTO dto = nomineeDTOMapper.getNomineeUsingDTO(savedNominee);

		logger.debug("Mapping Nominee to DTO: {}", dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "Getting all the nominees related to specific Account", response = Contact.class)
	@GetMapping("/listAllNominee") // working
	public ResponseEntity<List<NomineeResponseDTO>> listAllNominees(@RequestParam long accountId)
			throws InvalidAccountException, EmptyListException {
		logger.info("Received request to list all nominees for account ID: {}", accountId);
		List<Nominee> allNominee = nomineeService.listAllNominees(accountId);
		List<NomineeResponseDTO> mapped = new ArrayList<NomineeResponseDTO>();
		for (Nominee nominee : allNominee) {
			mapped.add(nomineeDTOMapper.getNomineeUsingDTO(nominee));
		}
		logger.info("Returning list of nominees for account ID: {}", accountId);
		return new ResponseEntity<List<NomineeResponseDTO>>(mapped, HttpStatus.OK);
	}

	@ApiOperation(value = "Getting the Debit Card Details through debit card number", response = Contact.class)
	@GetMapping("/getDebitCard/{debitCardNumber}")
	public ResponseEntity<DebitCardResponseDTO> getDebitCard(@PathVariable long debitCardNumber)
			throws DetailsNotFoundException {
		logger.info("Attempting to retrieve debit card with number: {}", debitCardNumber);
		DebitCard card = debitCardService.getDebitCardByDebitCardNumber(debitCardNumber);
		if (card != null) {
			DebitCardResponseDTO responseDTO = debitCardResponseConverter.convertToDTO(card);
			logger.info("Debit card found successfully. Card Number: {}", debitCardNumber);
			return new ResponseEntity<DebitCardResponseDTO>(responseDTO, HttpStatus.OK);
		} else {
			logger.warn("Debit card not found. Card Number: {}", debitCardNumber);
			return new ResponseEntity<DebitCardResponseDTO>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Beneficiery Functionality
	 */
	@ApiOperation(value = "Getting all the Beneficiries related to specific Account", response = Contact.class)
	@GetMapping("/listAllBeneficiariesbyAccount")
	public ResponseEntity<List<BeneficiaryResponseDTO>> listAllBeneficiariesbyAccount(@RequestParam long accountid)
			throws InvalidAccountException, EmptyListException {
		try {
			logger.info("Fetching beneficiaries for account with ID: {}", accountid);
			List<Beneficiary> allBeneficiary = beneficiaryService.listAllBeneficiariesbyAccount(accountid);
			List<BeneficiaryResponseDTO> mapped = new ArrayList<>();

			for (Beneficiary beneficiary : allBeneficiary) {
				mapped.add(beneficiaryDTO.getBeneficiaryUsingDTO(beneficiary));
			}

			logger.info("Successfully retrieved {} beneficiaries for account with ID: {}", mapped.size(), accountid);
			return new ResponseEntity<>(mapped, HttpStatus.OK);
		} catch (InvalidAccountException e) {
			logger.error("Invalid account ID: {}", accountid, e);
			throw e;
		} catch (EmptyListException e) {
			logger.warn("No beneficiaries found for account with ID: {}", accountid);
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Policy Functionality
	 */
	@ApiOperation(value = "Getting the policy related to specific Account", response = Contact.class)
	@GetMapping("getPolicyByAccountId") // working
	public List<Policy> getPolicyByAccountId(@RequestParam long accNum) {
		logger.info("Request received for getPolicyByAccountId with accNum: {}", accNum);
		List<Policy> temp = accountService.getPolicyByAccountId(accNum);
		if (temp != null && !temp.isEmpty()) {
			logger.info("Found {} policies for account ID {}", temp.size(), accNum);
		} else {
			logger.info("No policies found for account ID {}", accNum);
		}
		return temp;
	}

	/**
	 * Transactions Functionality
	 */
	@ApiOperation(value = "Getting all the transaction related to Entered account Number", response = Contact.class)
	@GetMapping("/getAllMyAccTransactions")
	public ResponseEntity<List<TransactionResponseDTO>> getAllMyAccTransactions(long account_id)
			throws InvalidAccountException, EmptyListException {
		logger.info("Fetching transactions for account_id: {}", account_id);
		List<Transaction> allMyTransactions = transactionService.getAllMyAccTransactions(account_id);
		List<TransactionResponseDTO> temp = new ArrayList<TransactionResponseDTO>();
		for (Transaction transaction : allMyTransactions) {
			temp.add(transactionDTOMapper.getTransactionUsingDTO(transaction));
		}
		logger.info("Returning {} transactions for account_id: {}", temp.size(), account_id);
		return new ResponseEntity<List<TransactionResponseDTO>>(temp, HttpStatus.FOUND);
	}

	@ApiOperation(value = "Getting all the transaction related to Entered account Number between Two Dates", response = Contact.class)
	@GetMapping("/listAllTransactions")
	public ResponseEntity<List<TransactionResponseDTO>> listAllTransactions(long accountId, String from, String to)
			throws InvalidAccountException, EmptyListException {
		try {
			logger.info("Entering listAllTransactions method with accountId={}, from={}, to={}", accountId, from, to);

			List<Transaction> betweenTransaction = transactionService.listAllTransactions(accountId, from, to);
			List<TransactionResponseDTO> temp = new ArrayList<>();

			for (Transaction transaction : betweenTransaction) {
				temp.add(transactionDTOMapper.getTransactionUsingDTO(transaction));
			}

			logger.info("Exiting listAllTransactions method. Found {} transactions.", temp.size());
			return new ResponseEntity<>(temp, HttpStatus.FOUND);
		} catch (InvalidAccountException | EmptyListException e) {
			logger.error("Error in listAllTransactions method: {}", e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error in listAllTransactions method", e);
			throw new RuntimeException("Internal Server Error", e);
		}
	}
	
	
	@ApiOperation(value = "Finding the Account based on User id", response = AccountServiceImpl.class)
	@GetMapping("/account/userId") // Working
	public ResponseEntity<AccountResponseDTO> findAccountByuserId(@RequestParam long userId){

		System.err.println("Inside service :- "+userId);
		Account account = accountService.getAccountByUserId(userId);
		AccountResponseDTO dto = accountDTOMapper.getAccountUsingDTO(account);
		System.err.println("Inside Service before return "+dto);
		return new ResponseEntity<AccountResponseDTO>(dto, HttpStatus.OK);
		
	}

}
