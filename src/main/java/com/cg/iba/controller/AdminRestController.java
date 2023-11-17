package com.cg.iba.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iba.dto.AccountResponseDTO;
import com.cg.iba.dto.AdminRequestSubmitDTO;
import com.cg.iba.dto.AdminResponseDTO;
import com.cg.iba.dto.CurrentAccountRequestSubmitDTO;
import com.cg.iba.dto.CurrentAccountResponseDTO;
import com.cg.iba.dto.DebitCardRequestDTO;
import com.cg.iba.dto.PolicyResponseDTO;
import com.cg.iba.dto.SavingAccountRequestSubmitDTO;
import com.cg.iba.dto.SavingAccountResponseDTO;
import com.cg.iba.dto.TransactionResponseDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Admin;
import com.cg.iba.entity.CurrentAccount;
import com.cg.iba.entity.DebitCard;
import com.cg.iba.entity.Policy;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.service.IAccountService;
import com.cg.iba.service.IAdminService;
import com.cg.iba.service.IBeneficiaryService;
import com.cg.iba.service.IDebitCardService;
import com.cg.iba.service.INomineeService;
import com.cg.iba.service.IPolicyService;
import com.cg.iba.service.ITransactionService;
import com.cg.iba.service.IUserService;
import com.cg.iba.util.AccountDTOMapper;
import com.cg.iba.util.AdminDTOMapper;
import com.cg.iba.util.BeneficiaryDTOMapper;
import com.cg.iba.util.CurrentAccountDTOMapper;
import com.cg.iba.util.DebitCardRequestDTOConverter;
import com.cg.iba.util.DebitCardResponseDTOConverter;
import com.cg.iba.util.NomineeDTOMapper;
import com.cg.iba.util.PolicyResponseDTOConverter;
import com.cg.iba.util.SavingsAccountDTOMapper;
import com.cg.iba.util.TransactionDTOMapper;
import com.cg.iba.util.UserDTOMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;

@RestController
@RequestMapping("/admin")
@Validated
@CrossOrigin(origins = {"http://localhost:5005", "http://localhost:4200"},allowedHeaders = "*")
public class AdminRestController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IAccountService accountService;

	@Autowired
	IUserService userService;

	@Autowired
	IAdminService adminService;

	@Autowired
	SavingsAccountDTOMapper savAccDTO;

	@Autowired
	CurrentAccountDTOMapper curAccDTO;

	@Autowired
	AccountDTOMapper accountDTO;

	@Autowired
	AdminDTOMapper adminDTO;

	@Autowired
	UserDTOMapper userDTO;

	@Autowired
	IDebitCardService debitCardService;

	@Autowired
	DebitCardResponseDTOConverter debitCardResponseConverter;

	@Autowired
	DebitCardRequestDTOConverter debitCardRequestCoverter;

	@Autowired
	INomineeService nomineeService;

	@Autowired
	NomineeDTOMapper nomineeDTOMapper;

	@Autowired
	IBeneficiaryService beneficiaryService;

	@Autowired
	BeneficiaryDTOMapper beneficiaryDTO;

	@Autowired
	IPolicyService policyService;

	@Autowired
	PolicyResponseDTOConverter policyResponseDTOConverter;

	@Autowired
	ITransactionService transactionService;

	@Autowired
	TransactionDTOMapper transactionDTOMapper;

	/**
	 * Admin Work
	 */
	@ApiOperation(value = "Create new Admin", notes = "Add all parameters for creating an Admin", response = Contact.class)
	@PostMapping("/register/newadmin") // to be checked
	public ResponseEntity<AdminResponseDTO> saveAdmin(@Valid @RequestBody AdminRequestSubmitDTO dto)
			throws InvalidDetailsException {
		Admin a = adminDTO.setAdminUsingDTO(dto);
		Admin a1 = adminService.addAdmin(a);
		AdminResponseDTO resDto = adminDTO.getAdminUsingDTO(a1);
		return new ResponseEntity<AdminResponseDTO>(resDto, HttpStatus.OK);
	}

	@ApiOperation(value = "Finding the admin by taking admin Id", notes = "Add admin id as request param.", response = Contact.class)
	@GetMapping("/findAdminById")
	public ResponseEntity<AdminResponseDTO> findAdminById(@RequestParam long adminId) throws DetailsNotFoundException {

		Admin savedAdmin = adminService.findAdminById(adminId);
		AdminResponseDTO dto = adminDTO.getAdminUsingDTO(savedAdmin);
		return new ResponseEntity<AdminResponseDTO>(dto, HttpStatus.FOUND);
	}

	@ApiOperation(value = "Update The Admin Details", notes = "Update certain Details of Admin", response = Contact.class)
	@PutMapping("/updateadmin")
	public ResponseEntity<AdminResponseDTO> updateAdmin(@RequestParam long adminId,
			@Valid @RequestBody AdminRequestSubmitDTO dto) throws InvalidDetailsException {
		Admin a = adminService.updateAdmin(adminId, dto);
		AdminResponseDTO dto1 = adminDTO.getAdminUsingDTO(a);
		return new ResponseEntity<AdminResponseDTO>(dto1, HttpStatus.FOUND);
	}

	@ApiOperation(value = "Removing the particular Admin from Database", notes = "Remove the admins from Databse", response = Contact.class)
	@DeleteMapping("/removeadmin")
	public ResponseEntity<Boolean> removeAdmin(@RequestParam long adminId) throws DetailsNotFoundException {
		boolean status = adminService.removeAdmin(adminId);
		if (status) {
			return new ResponseEntity<Boolean>(status, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Get All the Avaliable admins", response = Contact.class)
	@GetMapping("/getalladmins")
	public ResponseEntity<List<AdminResponseDTO>> listAllAdmins() throws EmptyListException {
		List<Admin> allAdmins = adminService.listAllAdmins();
		List<AdminResponseDTO> newlist = allAdmins.stream().map(admin -> adminDTO.getAdminUsingDTO(admin))
				.collect(Collectors.toList());
		return new ResponseEntity<List<AdminResponseDTO>>(newlist, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Allocate the user to Account", notes = "Add account number and UserId based on which we will perform the below method", response = Contact.class)
	@PutMapping("/usertoadmin")
	public ResponseEntity<AdminResponseDTO> allocateUserToAdmin(@RequestParam long admNum,
			@RequestParam long userId) throws InvalidAccountException, InvalidDetailsException {
		Admin admin = adminService.allocateUserToAdmin(admNum, userId);
		AdminResponseDTO dto = adminDTO.getAdminUsingDTO(admin);
		return new ResponseEntity<AdminResponseDTO>(dto, HttpStatus.OK);
	}

	/**
	 * Allocate User To Account
	 */
	@ApiOperation(value = "Allocate the user to Account", notes = "Add account number and UserId based on which we will perform the below method", response = Contact.class)
	@PutMapping("/usertoaccount")
	public ResponseEntity<AccountResponseDTO> allocateUserToAccount(@RequestParam long accNum,
			@RequestParam long userId) throws InvalidAccountException, InvalidDetailsException {
		Account acc = accountService.allocateUserToAccount(accNum, userId);
		AccountResponseDTO dto = accountDTO.getAccountUsingDTO(acc);
		return new ResponseEntity<AccountResponseDTO>(dto, HttpStatus.OK);
	}

	/**
	 * Accounts Functionality
	 */
	@ApiOperation(value = "Register New Savings Account", notes = "Add all parameters for creating an Savings Account.", response = Contact.class)
	@PostMapping("saveSavingsAccountDto") // working
	public ResponseEntity<SavingAccountResponseDTO> saveSavingsAccountDto(
			@Valid @RequestBody SavingAccountRequestSubmitDTO dto) throws InvalidDetailsException {
		logger.info("Received request to save Savings Account DTO: {}", dto);
		if (dto != null) {
			SavingsAccount a = savAccDTO.setSavingAccountUsingDTO(dto);
			SavingsAccount sav = accountService.addSavingsAccount(a);
			SavingAccountResponseDTO resDto = savAccDTO.getSavingAccountUsingDTO(sav);
			logger.info("Savings Account DTO saved successfully: {}", resDto);
			return new ResponseEntity<SavingAccountResponseDTO>(resDto, HttpStatus.OK);
		} else {
			logger.error("Error saving Savings Account DTO");
			return null;
		}
	}

	@ApiOperation(value = "Register New Current Account", notes = "Add all parameters for creating an Current Account.", response = Contact.class)
	@PostMapping("saveCurrentAcoountDto") // working
	public ResponseEntity<CurrentAccountResponseDTO> saveCurrentAcoountDto(
			@Valid @RequestBody CurrentAccountRequestSubmitDTO dto) throws InvalidDetailsException {

		logger.info("Received request to save Savings Account DTO: {}", dto);
		if (dto != null) {
			CurrentAccount a = curAccDTO.setCurrentAccountUsingDTO(dto);
			CurrentAccount cur = accountService.addCurrentAccount(a);
			CurrentAccountResponseDTO resDto = curAccDTO.getCurrentAccountUsingDTO(cur);
			logger.info("Current Account DTO saved successfully: {}", resDto);
			return new ResponseEntity<CurrentAccountResponseDTO>(resDto, HttpStatus.OK);
		} else {
			logger.error("Error saving Current Account DTO");
			return null;
		}
	}

	@ApiOperation(value = "Update the Saving account Details", response = Contact.class)
	@PutMapping("/updateSavingsAccount/{accountId}") // working
	public ResponseEntity<SavingAccountResponseDTO> updateSavingsAccount(@PathVariable long accountId,
			@Valid @RequestBody SavingAccountRequestSubmitDTO savingRequestDTO) {
		try {
			logger.info("Updating savings account with ID: {}", accountId);
			SavingsAccount updatedAccount = accountService.updateSavingsAccount(accountId, savingRequestDTO);
			SavingAccountResponseDTO dto = savAccDTO.getSavingAccountUsingDTO(updatedAccount);
			logger.info("Savings account updated successfully for ID: {}", accountId);
			return new ResponseEntity<SavingAccountResponseDTO>(dto, HttpStatus.OK);
		} catch (InvalidDetailsException e) {
			logger.error("Error updating savings account with ID: {}", accountId, e);
			return new ResponseEntity<SavingAccountResponseDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Update the Current account Details", response = Contact.class)
	@PutMapping("/updateCurrentAccount/{accountId}") // working
	public ResponseEntity<CurrentAccountResponseDTO> updateCurrentAccount(@PathVariable long accountId,
			@Valid @RequestBody CurrentAccountRequestSubmitDTO currentRequestDTO) {
		try {
			logger.info("Updating currents account with ID: {}", accountId);
			CurrentAccount updatedAccount = accountService.updateCurrentAccount(accountId, currentRequestDTO);
			CurrentAccountResponseDTO res = curAccDTO.getCurrentAccountUsingDTO(updatedAccount);
			logger.info("Currents account updated successfully for ID: {}", accountId);
			return new ResponseEntity<CurrentAccountResponseDTO>(res, HttpStatus.OK);
		} catch (InvalidDetailsException e) {
			logger.error("Error updating currents account with ID: {}", accountId, e);
			return new ResponseEntity<CurrentAccountResponseDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Delete the Saving account Details", response = Contact.class)
	@DeleteMapping("/deleteSavingAccount/delete")
	public ResponseEntity<String> deleteSavingAccount(@RequestBody SavingsAccount savingAccount) {
		try {
			boolean status = accountService.closeSavingsAccount(savingAccount);
			if (status) {
				String response = "Saving account - " + savingAccount.getAccountId() + " is Closed.";
				return new ResponseEntity<String>(response, HttpStatus.OK);
			}
		} catch (InvalidAccountException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Delete the Current account Details", response = Contact.class)
	@DeleteMapping("/deleteCurrentAccount/delete")
	public ResponseEntity<String> deleteCurrentAccount(@RequestBody CurrentAccount currentAccount) {
		try {
			boolean status = accountService.closeCurrentAccount(currentAccount);
			if (status) {
				String response = "Current account - " + currentAccount.getAccountId() + " is Closed.";
				return new ResponseEntity<String>(response, HttpStatus.OK);
			}
		} catch (InvalidAccountException e) {
			System.out.println(e);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get all the accounts of Bank", response = Contact.class)
	@GetMapping("/getallaccounts") // to be check
	public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
		logger.info("Attempting to retrieve all accounts");
		List<Account> accounts = accountService.viewAccounts();
		List<AccountResponseDTO> list = new ArrayList<AccountResponseDTO>();
		for (Account account : accounts) {
			list.add(accountDTO.getAccountUsingDTO(account));
		}
		logger.info("Successfully retrieved all accounts");
		return new ResponseEntity<List<AccountResponseDTO>>(list, HttpStatus.OK);
	}

	/**
	 * Debit Card Functionality By Admin
	 */
	@ApiOperation(value = "Request to Register New Debit Card", notes = "Add all parameters for creating a Debit Card.", response = Contact.class)
	@PostMapping("/createDebitCard")
	public ResponseEntity<Long> createDebitCard(@Valid @RequestBody DebitCardRequestDTO debitCardRequestDTO)
			throws InvalidDetailsException {
		logger.info("Received a request to create a debit card with details: {}", debitCardRequestDTO);
		DebitCard debitCard = debitCardRequestCoverter.convertToEntity(debitCardRequestDTO);
		long newDebitCardNumber = debitCardService.saveDebitCardDetails(debitCard);
		logger.info("Debit card created successfully. Debit card number: {}", newDebitCardNumber);
		return new ResponseEntity<>(newDebitCardNumber, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Allocate the Debit Card to Account", notes = "Add account number and Debit Card Number based on which we will perform the below method", response = Contact.class)
	@PutMapping("/allocateDebitCardToAccount")
	public ResponseEntity<Account> allocateDebitCardToAccount(@RequestParam long accNum,
			@RequestParam long debitCardNum) throws InvalidAccountException {
		Account updatedAcc = accountService.addDebitCardToAccount(accNum, debitCardNum);
		return new ResponseEntity<Account>(updatedAcc, HttpStatus.OK);
	}

	/**
	 * Policy Functionality
	 */
	@ApiOperation(value = "Method To Create the new Policy", response = Contact.class)
	@PostMapping("/policy/save") // working

	public Policy savePolicy(@Valid @RequestBody PolicyResponseDTO dto) throws InvalidDetailsException {
		logger.info("Received request to save policy with DTO: {}", dto);
		Policy savedPolicy = policyResponseDTOConverter.setPolicyUsingDTO(dto);
		Policy policy = policyService.savePolicy(savedPolicy);
		logger.info("Policy saved successfully: {}", policy);
		return policy;
	}

	@ApiOperation(value = "Method To Update the new Policy", response = Contact.class)
	@PutMapping("/policy/update") // working
	public ResponseEntity<PolicyResponseDTO> updatePremiumAmountOfPolicyByPolicyNumber(@RequestParam long policyNumber,
			@RequestParam int newPremiumAmount) throws DetailsNotFoundException {
		try {
			logger.info("Updating premium amount for policyNumber: {} to newPremiumAmount: {}", policyNumber,
					newPremiumAmount);

			Policy savedPolicy = policyService.updatePremiumAmountOfPolicyByPolicyNumber(policyNumber,
					newPremiumAmount);

			if (savedPolicy != null) {
				PolicyResponseDTO dto = policyResponseDTOConverter.getPolicyUsingDTO(savedPolicy);
				logger.info("Premium amount updated successfully for policyNumber: {}. Returning HttpStatus.OK",
						policyNumber);
				return new ResponseEntity<>(dto, HttpStatus.OK);
			} else {
				logger.warn("Policy with policyNumber: {} not found. Returning HttpStatus.NOT_FOUND", policyNumber);
				return new ResponseEntity<>(new PolicyResponseDTO(), HttpStatus.NOT_FOUND);
			}
		} catch (DetailsNotFoundException e) {
			logger.error("DetailsNotFoundException occurred: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An unexpected error occurred while updating premium amount for policyNumber: {}",
					policyNumber, e);
			throw e;
		}
	}

	@ApiOperation(value = "To delete the Policy", response = Contact.class)
	@DeleteMapping("/policy/delete/{policyNumber}") // working
	public ResponseEntity<String> deletePolicy(@PathVariable long policyNumber) throws DetailsNotFoundException {
		policyService.deletePolicy(policyNumber);
		return new ResponseEntity<String>("Policy Deleted", HttpStatus.OK);
	}

	@ApiOperation(value = "To get all Policies", response = Contact.class)
	@GetMapping("/policy/allpolicies") // working
	public ResponseEntity<List<PolicyResponseDTO>> getAllPolicies() throws EmptyListException {
		List<Policy> tempList = policyService.getAllPolicies();
		List<PolicyResponseDTO> temp = new ArrayList<PolicyResponseDTO>();
		for (Policy policy : tempList) {
			temp.add(policyResponseDTOConverter.getPolicyUsingDTO(policy));
		}
		return new ResponseEntity<List<PolicyResponseDTO>>(temp, HttpStatus.OK);
	}

	/**
	 * Transactions Functionality
	 */
	@ApiOperation(value = "To get the trasaction Details based on transaction Id", response = Contact.class)
	@GetMapping("/findTransactionById")
	public ResponseEntity<TransactionResponseDTO> findTransactionById(@RequestParam long transaction_id)
			throws DetailsNotFoundException {
		Transaction saved = transactionService.findTransactionById(transaction_id);
		TransactionResponseDTO dto = transactionDTOMapper.getTransactionUsingDTO(saved);
		logger.info("Transaction found with ID: {}", transaction_id);
		return new ResponseEntity<TransactionResponseDTO>(dto, HttpStatus.FOUND);
	}

}// end class
