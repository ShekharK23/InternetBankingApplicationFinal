package com.cg.iba.controller;

import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

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
import com.cg.iba.dto.AccountUpdateRequestSubmitDTO;
import com.cg.iba.dto.BeneficiaryRequestDTO;
import com.cg.iba.dto.BeneficiaryResponseDTO;
import com.cg.iba.dto.CurrentAccountRequestSubmitDTO;
import com.cg.iba.dto.CurrentAccountResponseDTO;
import com.cg.iba.dto.DebitCardResponseDTO;
import com.cg.iba.dto.NomineeRequestSubmitDTO;
import com.cg.iba.dto.NomineeResponseDTO;
import com.cg.iba.dto.PolicyResponseDTO;
import com.cg.iba.dto.RequestResponseDTO;
import com.cg.iba.dto.RequestSubmitDTO;
import com.cg.iba.dto.SavingAccountRequestSubmitDTO;
import com.cg.iba.dto.SavingAccountResponseDTO;
import com.cg.iba.dto.TransactionRequestDTO;
import com.cg.iba.dto.TransactionResponseDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Beneficiary;
import com.cg.iba.entity.CurrentAccount;
import com.cg.iba.entity.DebitCard;
import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.Policy;
import com.cg.iba.entity.Request;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.service.IAccountService;
import com.cg.iba.service.IBeneficiaryService;
import com.cg.iba.service.IDebitCardService;
import com.cg.iba.service.INomineeService;
import com.cg.iba.service.IPolicyService;
import com.cg.iba.service.IRequestService;
import com.cg.iba.service.ITransactionService;
import com.cg.iba.util.AccountDTOMapper;
import com.cg.iba.util.BeneficiaryDTOMapper;
import com.cg.iba.util.CurrentAccountDTOMapper;
import com.cg.iba.util.DebitCardRequestDTOConverter;
import com.cg.iba.util.DebitCardResponseDTOConverter;
import com.cg.iba.util.NomineeDTOMapper;
import com.cg.iba.util.PolicyResponseDTOConverter;
import com.cg.iba.util.RequestDTOMapper;
import com.cg.iba.util.SavingsAccountDTOMapper;
import com.cg.iba.util.TransactionDTOMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Contact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/customer")
@Validated
@CrossOrigin(origins = {"http://localhost:5005", "http://localhost:4200"},allowedHeaders = "*")
public class CustomerRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	IAccountService accountService;

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
	
	@Autowired
	IRequestService requestService;

	@Autowired
	RequestDTOMapper requestDTO;
	
	@Autowired
	SavingsAccountDTOMapper savAccDTO;

	@Autowired
	CurrentAccountDTOMapper curAccDTO;
	
	@Autowired
	AccountDTOMapper accountDTO;
	
	/**
	 * Account Functionality By Customer
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
			@Valid @RequestBody AccountUpdateRequestSubmitDTO updateDTO) {
		try {
			logger.info("Updating savings account with ID: {}", accountId);
			SavingsAccount updatedAccount = accountService.updateSavingsAccount(accountId, updateDTO);
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
			@Valid @RequestBody AccountUpdateRequestSubmitDTO updateDTO) {
		try {
			logger.info("Updating currents account with ID: {}", accountId);
			CurrentAccount updatedAccount = accountService.updateCurrentAccount(accountId, updateDTO);
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
	 * Debit Card Functionality By Customer
	 */
	
	@ApiOperation(value = "You can change the pin of Debit card", response = Contact.class)
	@PutMapping("/{debitCardNumber}/change-pin")
	public ResponseEntity<DebitCardResponseDTO> changePin(@PathVariable long debitCardNumber, @RequestParam int newPin)
			throws DetailsNotFoundException {
		try {
			logger.info("Attempting to change PIN for debit card with number: {}", debitCardNumber);

			DebitCard card = debitCardService.getDebitCardByDebitCardNumber(debitCardNumber);

			if (card != null) {
				card.setDebitCardPin(newPin);
				DebitCard updatedCard = debitCardService.changePin(debitCardNumber, newPin);

				if (updatedCard != null) {
					DebitCardResponseDTO responseDTO = debitCardResponseConverter.convertToDTO(updatedCard);
					logger.info("PIN changed successfully for debit card with number: {}", debitCardNumber);
					return new ResponseEntity<>(responseDTO, HttpStatus.OK);
				} else {
					logger.warn("Failed to update PIN for debit card with number: {}", debitCardNumber);
				}
			} else {
				logger.warn("Debit card with number {} not found.", debitCardNumber);
			}
		} catch (Exception e) {
			logger.error("An error occurred while changing PIN for debit card with number: {}", debitCardNumber, e);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Request to block the debit card", response = Contact.class)
	@PutMapping("/{debitCardNumber}/block-card")
	public ResponseEntity<DebitCardResponseDTO> blockCard(@PathVariable long debitCardNumber,
			@RequestParam String cardStatus) throws DetailsNotFoundException {

		logger.info("Blocking debit card with number: {} and status: {}", debitCardNumber, cardStatus);

		DebitCard card = debitCardService.blockCard(debitCardNumber, cardStatus);

		if (card != null) {
			DebitCardResponseDTO responseDTO = debitCardResponseConverter.convertToDTO(card);
			logger.info("Debit card successfully blocked. Returning response.");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			logger.warn("Debit card with number {} not found.", debitCardNumber);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Check the Expiry Of the credit card", response = Contact.class)
	@GetMapping("/{debitCardNumber}/check-expiry")
	public ResponseEntity<String> checkExpiry(@PathVariable long debitCardNumber) throws DetailsNotFoundException {
		String expiryStatus = debitCardService.checkExpiry(debitCardNumber);
		if (!expiryStatus.equals("Debit Card Not Allocated")) {
			return new ResponseEntity<>(expiryStatus, HttpStatus.OK);
		} else {
			logger.warn("Debit card not allocated", debitCardNumber);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Request for new debit card", response = Contact.class)
	@PutMapping("/{debitCardNumber}/request-new-card")
	public ResponseEntity<DebitCardResponseDTO> requestNewCard(@PathVariable long debitCardNumber) {
		logger.trace("debitCardService() service started");

		try {
			logger.debug("Received request for new card for debit card number: {}", debitCardNumber);

			DebitCard newCard = debitCardService.requestNewCard(debitCardNumber);

			if (newCard != null) {
				DebitCardResponseDTO responseDTO = debitCardResponseConverter.convertToDTO(newCard);
				logger.info("New card request successful for debit card number: {}", debitCardNumber);
				return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			} else {
				logger.warn("No debit card found for the given debit card number: {}", debitCardNumber);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error processing request for new card for debit card number: {}", debitCardNumber, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Nominee Functionality By Admin
	 */
	@ApiOperation(value = "Add the new Nominee ", notes = "Add all the required details", response = Contact.class)
	@PostMapping("/addNominee") // working
	public ResponseEntity<NomineeResponseDTO> addNominee(@Valid @RequestBody NomineeRequestSubmitDTO nominee)
			throws InvalidDetailsException {
		try {
			logger.info("Received request to add nominee: {}", nominee);

			Nominee n = nomineeDTOMapper.setNomineeUsingDTO(nominee);
			Nominee savedNominee = nomineeService.addNominee(n);
			NomineeResponseDTO dto = nomineeDTOMapper.getNomineeUsingDTO(savedNominee);

			logger.info("Nominee added successfully: {}", savedNominee);

			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (InvalidDetailsException e) {
			logger.error("Error adding nominee: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error adding nominee", e);
			throw new RuntimeException("Unexpected error adding nominee", e);
		}
	}

	@ApiOperation(value = "Update the Details of Nominees", notes = "Give all the correct Details", response = Contact.class)
	@PutMapping("/updateNomineeDetails") // working
	public ResponseEntity<NomineeResponseDTO> updateNominee(@RequestParam long id,
			@Valid @RequestBody NomineeRequestSubmitDTO nominee)
			throws InvalidDetailsException, DetailsNotFoundException {
		try {
			Nominee updatedNominee = nomineeService.updateNominee(id, nominee);
			NomineeResponseDTO n = nomineeDTOMapper.getNomineeUsingDTO(updatedNominee);

			logger.info("Nominee with ID {} updated successfully", id);

			return new ResponseEntity<>(n, HttpStatus.FOUND);
		} catch (InvalidDetailsException e) {
			logger.error("Invalid details provided for updating nominee with ID {}: {}", id, e.getMessage());
			throw e;
		} catch (DetailsNotFoundException e) {
			logger.error("Nominee with ID {} not found for update: {}", id, e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("An unexpected error occurred while updating nominee with ID {}: {}", id, e.getMessage(), e);
			throw e;
		}
	}

	@ApiOperation(value = "Delete Nominee By nominee id", response = Contact.class)
	@DeleteMapping("/deleteNomineeById/{nomineeId}")
	public ResponseEntity<String> deleteNomineeById(@PathVariable long nomineeId) throws DetailsNotFoundException {
		nomineeService.deleteNominee(nomineeId);
		return new ResponseEntity<String>("The Nominee is deleted for the id-:" + nomineeId, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Allocate the Nominee To Account", response = Contact.class)
	@PutMapping("/allocateNomineeToAccount")
	public ResponseEntity<Account> allocateNomineeToAccount(@RequestParam long nomineeId, @RequestParam long accNum)
			throws InvalidAccountException, InvalidDetailsException, DetailsNotFoundException {
		logger.info("Received Request To update the Nominee Details: ");
		Account updatedAccount = accountService.addNomineeToAccount(nomineeId, accNum);
		if (updatedAccount != null) {
			return new ResponseEntity<Account>(updatedAccount, HttpStatus.CREATED);
		} else {
			logger.error("Error During allocating Nominee to account");
		}
		return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Beneficiery Functionality
	 */
	@ApiOperation(value = "Create new Beneficiary", response = Contact.class)
	@PostMapping("addBeneficiary")
	public ResponseEntity<BeneficiaryResponseDTO> addBeneficiaryDto(@Valid @RequestBody BeneficiaryRequestDTO dto)
			throws InvalidDetailsException {
		logger.info("Entering addBeneficiaryDto method");
		Beneficiary b = beneficiaryDTO.setBeneficiaryUsingDTO(dto);
		Beneficiary ba = beneficiaryService.addBeneficiary(b);
		BeneficiaryResponseDTO dtores = beneficiaryDTO.getBeneficiaryUsingDTO(ba);
		return new ResponseEntity<BeneficiaryResponseDTO>(dtores, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete the  Beneficiary by id", response = Contact.class)
	@DeleteMapping("/deleteBeneficiary")
	public ResponseEntity<Boolean> deleteBeneficiary(@RequestParam long beneficiaryId) throws DetailsNotFoundException {
		Boolean flag = beneficiaryService.deleteBeneficiary(beneficiaryId);
		return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
	}

	@ApiOperation(value = "Find the  Beneficiary by id", response = Contact.class)
	@GetMapping("findBeneficiaryById")
	public ResponseEntity<BeneficiaryResponseDTO> findBeneficiaryById(@RequestParam long beneficiaryId)
			throws DetailsNotFoundException {
		Beneficiary savedBenificiery = beneficiaryService.findBeneficiaryById(beneficiaryId);
		BeneficiaryResponseDTO dto = beneficiaryDTO.getBeneficiaryUsingDTO(savedBenificiery);
		return new ResponseEntity<BeneficiaryResponseDTO>(dto, HttpStatus.OK);
	}

	@ApiOperation(value = "Allocate the Beneficiary To account", response = Contact.class)
	@PutMapping("/allocateBeneficiaryToAccount")
	public Account allocateBeneficiaryToAccount(@RequestParam long beneficiaryId, @RequestParam long accNum)
			throws InvalidAccountException, InvalidDetailsException, DetailsNotFoundException {
		logger.info("Allocating beneficiary with ID {} to account with number {}", beneficiaryId, accNum);
		Account updatedAccount = accountService.addBeneficiaryToAccount(beneficiaryId, accNum);
		logger.info("Beneficiary successfully allocated. Updated account details: {}", updatedAccount);
		return updatedAccount;
	}

	/**
	 * Policy Functionality
	 */
	@ApiOperation(value = "Allocate Policy To Account", response = Contact.class)
	@PutMapping("/allocatePolicyToAccount")
	public Account allocatePolicyToAccount(@RequestParam long policyId, @RequestParam long accNum)
			throws InvalidAccountException, InvalidDetailsException, DetailsNotFoundException {
		logger.info("Allocating policy with ID {} to account with number {}", policyId, accNum);
		Account updatedAccount = accountService.addPolicyToAccount(policyId, accNum);
		logger.info("Policy allocated successfully. Updated account details: {}", updatedAccount);
		return updatedAccount;
	}

	@ApiOperation(value = "To get the policy By Policy Number", response = Contact.class)
	@GetMapping("/policy/fetch/num") // working
	public ResponseEntity<PolicyResponseDTO> getPolicyByPolicyNumber(@RequestParam long policyNumber)
			throws DetailsNotFoundException {
		try {
			logger.info("Fetching policy for policy number: {}", policyNumber);

			Policy savedPolicy = policyService.getPolicyByPolicyNumber(policyNumber);

			if (savedPolicy == null) {
				logger.warn("Policy not found for policy number: {}", policyNumber);
				throw new DetailsNotFoundException("Policy not found for policy number: " + policyNumber, "");
			}

			PolicyResponseDTO dto = policyResponseDTOConverter.getPolicyUsingDTO(savedPolicy);
			logger.info("Policy fetched successfully for policy number: {}", policyNumber);

			return new ResponseEntity<>(dto, HttpStatus.OK);

		} catch (DetailsNotFoundException ex) {
			logger.error("Error fetching policy details: {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("Unexpected error fetching policy details", ex);
			throw new RuntimeException("Unexpected error fetching policy details", ex);
		}
	}

	@ApiOperation(value = "To check the expiry date of policy", response = Contact.class)
	@GetMapping("/policy/check-expiry") // working
	public ResponseEntity<String> checkExpiryDate(@RequestParam long policyNumber) throws DetailsNotFoundException {
		String msg = policyService.checkExpiryDate(policyNumber);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	/**
	 * Transactions Functionality
	 */
	@ApiOperation(value = "Get My Transaction By Account Id", response = Contact.class)
	@GetMapping("/getAllMyAccTransactions")
	public ResponseEntity<List<TransactionResponseDTO>> getAllMyAccTransactions(@RequestParam long account_id)
			throws InvalidAccountException, EmptyListException {
		try {
			logger.info("Fetching transactions for account_id: {}", account_id);

			List<Transaction> allMyTransactions = transactionService.getAllMyAccTransactions(account_id);
			List<TransactionResponseDTO> temp = new ArrayList<>();

			for (Transaction transaction : allMyTransactions) {
				temp.add(transactionDTOMapper.getTransactionUsingDTO(transaction));
			}
			logger.info("Returning {} transactions for account_id: {}", temp.size(), account_id);
			return new ResponseEntity<>(temp, HttpStatus.FOUND);
		} catch (InvalidAccountException e) {
			logger.error("Invalid account_id: {}", account_id, e);
			throw e;
		} catch (EmptyListException e) {
			logger.warn("No transactions found for account_id: {}", account_id);
			throw e;
		} catch (Exception e) {
			logger.error("An unexpected error occurred while processing transactions for account_id: {}", account_id,
					e);
			throw e;
		}
	}

	@ApiOperation(value = "List all the Transaction for a given account number", response = Contact.class)
	@GetMapping("/listAllTransactions")
	public ResponseEntity<List<TransactionResponseDTO>> listAllTransactions(@RequestParam long accountId,
			@RequestParam String from, @RequestParam String to) throws InvalidAccountException, EmptyListException {
		logger.info("Fetching transactions for account_id: {}", accountId);
		List<Transaction> betweenTransaction = transactionService.listAllTransactions(accountId, from, to);
		List<TransactionResponseDTO> temp = new ArrayList<TransactionResponseDTO>();
		for (Transaction transaction : betweenTransaction) {
			temp.add(transactionDTOMapper.getTransactionUsingDTO(transaction));
		}
		logger.info("Returning {} transactions for accountId: {}", temp.size(), accountId);
		return new ResponseEntity<List<TransactionResponseDTO>>(temp, HttpStatus.FOUND);
	}

	@ApiOperation(value = "Deposit the certain amount into your account", response = Contact.class)
	@PutMapping("/deposit")
	public ResponseEntity<TransactionResponseDTO> deposit(@RequestParam long accountId, @RequestParam double amount,
			@Valid @RequestBody TransactionRequestDTO transaction)
			throws InvalidAccountException, InvalidAmountException, InvalidDetailsException {

		try {
			logger.info("Deposit request received for account ID: {}, amount: {}", accountId, amount);

			Transaction t = transactionDTOMapper.setTransactionUsingDTO(transaction);
			Transaction saved = accountService.deposit(accountId, amount, t);

			logger.info("Deposit successful for account ID: {}", accountId);

			TransactionResponseDTO tdto = transactionDTOMapper.getTransactionUsingDTO(t);
			return new ResponseEntity<>(tdto, HttpStatus.OK);
		} catch (InvalidAccountException | InvalidAmountException | InvalidDetailsException e) {
			logger.error("Error processing deposit request", e);
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error during deposit processing", e);
			throw new RuntimeException("Unexpected error during deposit processing", e);
		}
	}

	@ApiOperation(value = "Withdraw certain amount from Your account", response = Contact.class)
	@PutMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestParam long accountId, @RequestParam double amount,
			@Valid @RequestBody TransactionRequestDTO transaction)
			throws InvalidAccountException, LowBalanceException, InvalidDetailsException {
		logger.info("Withdraw request received for account {} with amount {}", accountId, amount);
		Transaction t = transactionDTOMapper.setTransactionUsingDTO(transaction);
		Transaction saved = transactionService.withdraw(accountId, amount, t);

		logger.info("Withdrawal successful. Amount {} withdrawn from account {}", amount, accountId);
		return new ResponseEntity<String>("You've Withdrawed " + amount + " from your account " + accountId,
				HttpStatus.OK);

	}

	@ApiOperation(value = "Transfer the money from one Account to receivers account", response = Contact.class)
	@PutMapping("/transferMoney")
	public ResponseEntity<String> transferMoney(@RequestParam long senderAccounId, @RequestParam long receiverAccountId,
			@RequestParam double amount, @RequestParam long userId, String password)
			throws LowBalanceException, InvalidAccountException, InvalidDetailsException {
		Transaction t = new Transaction();
		Beneficiary b = new Beneficiary();
		logger.info("Transfer method is called and money is sent from current account to Beneficiary Account");
		accountService.transferMoney(senderAccounId, receiverAccountId, amount, userId, password);
		return new ResponseEntity<String>("You've Transfered " + amount
				+ "from Your Account to Beneficiary Account Number_:" + b.getBeneficiaryAccNo(), HttpStatus.OK);
	}
	
	
	///// ---------------------Additional Functions ----------------------------
	
	@PostMapping("/request/save")
	public ResponseEntity<RequestResponseDTO> saveRequest(@RequestBody RequestSubmitDTO dto) {
		Request r = requestDTO.setRequestUpdateUsingDTO(dto);
		Request r1 = requestService.saveRequest(r);
		RequestResponseDTO resDto = requestDTO.getRequestUsingDTO(r1);
		return new ResponseEntity<RequestResponseDTO>(resDto, HttpStatus.OK);
	}

}
