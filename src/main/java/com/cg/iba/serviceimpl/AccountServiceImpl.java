package com.cg.iba.serviceimpl;

import java.util.Collections;
import java.util.List;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.dto.CurrentAccountRequestSubmitDTO;
import com.cg.iba.dto.SavingAccountRequestSubmitDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Beneficiary;
import com.cg.iba.entity.CurrentAccount;
import com.cg.iba.entity.DebitCard;
import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.Policy;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.Transaction;
import com.cg.iba.entity.BankUser;
import com.cg.iba.entity.enums.Role;
import com.cg.iba.entity.enums.TransactionStatus;
import com.cg.iba.entity.enums.TransactionType;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.IBeneficiaryRepository;
import com.cg.iba.repository.IDebitCardRepository;
import com.cg.iba.repository.INomineeRepository;
import com.cg.iba.repository.IPolicyRepository;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.repository.IUserRepository;
import com.cg.iba.service.IAccountService;
import com.cg.iba.service.INomineeService;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	IDebitCardRepository debitCardRepository;

	@Autowired
	INomineeRepository nomineeRepository;

	@Autowired
	IPolicyRepository policyRepository;

	@Autowired
	IBeneficiaryRepository beneficiaryRepository;

	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	INomineeService nomineeService;

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional
	public Transaction transferMoney(long senderAccounId, long receiverAccountId, double amount, long userId,
			String password) throws LowBalanceException, InvalidAccountException, InvalidDetailsException {
		BankUser user = userRepository.findById(userId)
				.orElseThrow(() -> new InvalidDetailsException("User ID And password are not matching",
						AccountServiceImpl.class + ""));
		if (user.getUserId() == userId && user.getPassword().equals(password)) {
			Account senderAccount = accountRepository.findById(senderAccounId)
					.orElseThrow(() -> new InvalidAccountException(
							"The Account Number " + senderAccounId + "You've Intered is Not valid Account number",
							AccountServiceImpl.class + ""));
			Beneficiary receiverAccount = beneficiaryRepository.findById(receiverAccountId)
					.orElseThrow(() -> new InvalidAccountException(
							"The Account Number " + receiverAccountId + "You've Intered is Not valid Account number",
							AccountServiceImpl.class + ""));
			if (senderAccount instanceof Account) {
				SavingsAccount sa = (SavingsAccount) senderAccount;
				if (senderAccount.getBalance() < sa.getSavingMinBalance()) {
					throw new LowBalanceException("The Minimum Amount should be maintained",
							AccountServiceImpl.class + "");
				} else {
					if(amount<=0) {
						throw new InvalidDetailsException("Amount less than or equal to zero cannot be entered",AccountServiceImpl.class+"");
					}
					senderAccount.setBalance(senderAccount.getBalance() - amount);
					Transaction t = new Transaction();
					t.setAmount(amount);
					t.setTransactionType(TransactionType.DEBIT);
					t.setTransactionStatus(TransactionStatus.SUCCESSFUL);
					t.setTransactionRemarks("Done");
					transactionRepository.save(t);
					accountRepository.save(senderAccount);
					addTransactionToAccount(t.getTransactionId(), senderAccounId);
					return t;

				}
			} else if (senderAccount instanceof CurrentAccount) {
				CurrentAccount currentAcc = (CurrentAccount) senderAccount;
				if (senderAccount.getBalance() < currentAcc.getCurrentMinBalance()) {
					throw new LowBalanceException("The Minimum Amount should be maintained",
							AccountServiceImpl.class + "");
				} else {
					if(amount<=0) {
						throw new InvalidDetailsException("Amount less than or equal to zero cannot be entered",AccountServiceImpl.class+"");
					}
					senderAccount.setBalance(senderAccount.getBalance() - amount);
					Transaction t = new Transaction();
					t.setAmount(amount);
					t.setTransactionType(TransactionType.DEBIT);
					t.setTransactionStatus(TransactionStatus.SUCCESSFUL);
					t.setTransactionRemarks("Done");
					transactionRepository.save(t);
					accountRepository.save(senderAccount);
					addTransactionToAccount(t.getTransactionId(), senderAccounId);
					return t;
				}
			}
		}

		return null;

	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional
	public Transaction deposit(long accounId, double amount, Transaction t)
			throws InvalidAccountException, InvalidAmountException, InvalidDetailsException {
		Account existingAccount = accountRepository.findById(accounId)
				.orElseThrow(() -> new InvalidAccountException(
						"The Account Number " + accounId + "You've Intered is Not valid Account number",
						AccountServiceImpl.class + ""));
		if (existingAccount instanceof SavingsAccount) {
			SavingsAccount sa = (SavingsAccount) existingAccount;
			if (existingAccount.getBalance() < sa.getSavingMinBalance()) {
				throw new InvalidAmountException("The Minimum Amount should be maintained",
						AccountServiceImpl.class + "");
			} else {
				if(amount<=0) {
					throw new InvalidDetailsException("Amount less than or equal to zero cannot be entered",AccountServiceImpl.class+"");
				}
				existingAccount.setBalance(existingAccount.getBalance() + amount);

				t.setAmount(amount);
				t.setTransactionType(TransactionType.CREDIT);
				transactionRepository.save(t);

				accountRepository.save(existingAccount);

				addTransactionToAccount(t.getTransactionId(), accounId);
				return t;
			}

		} else if (existingAccount instanceof CurrentAccount) {
			CurrentAccount currentAcc = (CurrentAccount) existingAccount;
			if (existingAccount.getBalance() < currentAcc.getCurrentMinBalance()) {
				throw new InvalidAmountException("The Minimum Amount should be maintained",
						AccountServiceImpl.class + "");
			} else {
				if(amount<=0) {
					throw new InvalidDetailsException("Amount less than or equal to zero cannot be entered",AccountServiceImpl.class+"");
				}
				existingAccount.setBalance(existingAccount.getBalance() + amount);
				t.setAmount(amount);
				t.setTransactionType(TransactionType.CREDIT);
				transactionRepository.save(t);
				accountRepository.save(existingAccount);
				addTransactionToAccount(t.getTransactionId(), accounId);
				return t;
			}
		}
		return t;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public SavingsAccount addSavingsAccount(SavingsAccount saving) throws InvalidDetailsException {
		SavingsAccount savedAccount = accountRepository.save(saving);
		return savedAccount;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public CurrentAccount addCurrentAccount(CurrentAccount current) throws InvalidDetailsException {
		CurrentAccount savedAccount = accountRepository.save(current);
		return savedAccount;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------	
	@Override
	@Transactional
	public SavingsAccount updateSavingsAccount(long accountId, SavingAccountRequestSubmitDTO savingRequestDTO)
			throws InvalidDetailsException {
		Account existingAccount = accountRepository.findById(accountId)
				.orElseThrow(() -> new InvalidDetailsException("Account not found", ""));

		if (existingAccount instanceof SavingsAccount) {
			SavingsAccount savingsAccount = (SavingsAccount) existingAccount;

			savingsAccount.setAccountHolderName(savingRequestDTO.getAccountHolderName());
			savingsAccount.setPhoneNo(savingRequestDTO.getPhoneNo());
			savingsAccount.setEmailId(savingRequestDTO.getEmailId());
			savingsAccount.setAge(savingRequestDTO.getAge());
			savingsAccount.setGender(savingRequestDTO.getGender());
			savingsAccount.setBalance(savingRequestDTO.getBalance());
			savingsAccount.setDateOfOpening(savingRequestDTO.getDateOfOpening());

			SavingsAccount updatedAccount = accountRepository.save(savingsAccount);

			return updatedAccount;
		} else {
			throw new InvalidDetailsException("Account is not a Savings Account", "");
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------   
	@Override
	@Transactional
	public CurrentAccount updateCurrentAccount(long accountId, CurrentAccountRequestSubmitDTO currentRequestDTO)
			throws InvalidDetailsException {
		Account existingAccount = accountRepository.findById(accountId)
				.orElseThrow(() -> new InvalidDetailsException("Account not found", ""));

		if (existingAccount instanceof CurrentAccount) {
			CurrentAccount currentAccount = (CurrentAccount) existingAccount;

			currentAccount.setAccountHolderName(currentRequestDTO.getAccountHolderName());
			currentAccount.setPhoneNo(currentRequestDTO.getPhoneNo());
			currentAccount.setEmailId(currentRequestDTO.getEmailId());
			currentAccount.setAge(currentRequestDTO.getAge());
			currentAccount.setGender(currentRequestDTO.getGender());
			
			currentAccount.setBalance(currentRequestDTO.getBalance());
			currentAccount.setDateOfOpening(currentRequestDTO.getDateOfOpening());
			

			CurrentAccount updatedAccount = accountRepository.save(currentAccount);

			return updatedAccount;
		} else {
			throw new InvalidDetailsException("Account is not a Savings Account", "");
		}
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean closeSavingsAccount(SavingsAccount accountNo) throws InvalidAccountException {
		Account existingAccount = accountRepository.findById(accountNo.getAccountId()).get();

		if (existingAccount == null) {
			new InvalidAccountException("Account not found", "");
			return false;
		} else {
			accountRepository.deleteById(accountNo.getAccountId());
			return true;
		}

	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean closeCurrentAccount(CurrentAccount accountNo) throws InvalidAccountException {
		Account existingAccount = accountRepository.findById(accountNo.getAccountId()).get();

		if (existingAccount == null) {
			new InvalidAccountException("Account not found", "");
			return false;
		} else {
			accountRepository.deleteById(accountNo.getAccountId());
			return true;
		}

	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------	
	@Override
	public Account findAccountById(long account_id) throws InvalidAccountException {
		Optional<Account> accountOptional = accountRepository.findById(account_id);

		if (accountOptional.isPresent()) {
			return accountOptional.get();
		} else {
			throw new InvalidAccountException("Account Not Found.", "");
		}
	}

	@Override
	public List<Account> viewAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts;
	}


	@Override
	@Transactional
	public Account addDebitCardToAccount(long accNum, long debitCardNum) throws InvalidAccountException {
		Account savedAcc = findAccountById(accNum);
		DebitCard card = debitCardRepository.findById(debitCardNum).get();
		if (savedAcc != null && card != null) {
			savedAcc.setDebitCard(card);
			return savedAcc;
		}
		return null;
	}

	@Override
	@Transactional
	public Account addNomineeToAccount(long nomineeId, long accNum)
			throws InvalidAccountException, DetailsNotFoundException, InvalidDetailsException {
		Account savedAcc = findAccountById(accNum);
		Nominee nominee = nomineeService.findNomineeById(nomineeId);
		if (savedAcc != null && nominee != null) {
			List<Nominee> allNominees = savedAcc.getNominees();
			allNominees.add(nominee);
			savedAcc.setNominees(allNominees);

			if (savedAcc instanceof SavingsAccount) {
				SavingsAccount sa = (SavingsAccount) savedAcc;
				addSavingsAccount(sa);
				return sa;
			}
			if (savedAcc instanceof CurrentAccount) {
				CurrentAccount ca = (CurrentAccount) savedAcc;
				addCurrentAccount(ca);
				return ca;
			}

		}
		return null;
	}

	@Override
	@Transactional
	public Account addPolicyToAccount(long policyId, long accNum)
			throws InvalidAccountException, InvalidDetailsException {
		Account savedAcc = findAccountById(accNum);
		Policy policy = policyRepository.findById(policyId).get();
		if (savedAcc != null && policy != null) {
			List<Policy> allPolicy = savedAcc.getPolicies();
			allPolicy.add(policy);
			savedAcc.setPolicies(allPolicy);
			if (savedAcc instanceof SavingsAccount) {
				SavingsAccount sa = (SavingsAccount) savedAcc;
				addSavingsAccount(sa);
				return sa;
			}
			if (savedAcc instanceof CurrentAccount) {
				CurrentAccount ca = (CurrentAccount) savedAcc;
				addCurrentAccount(ca);
				return ca;
			}
		}
		return null;
	}

	@Override
	public List<Policy> getPolicyByAccountId(long accNum) {
		Account account = accountRepository.findById(accNum).get();

		if (account != null) {
			return account.getPolicies();
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public Account addBeneficiaryToAccount(long beneficiaryId, long accNum)
			throws InvalidAccountException, InvalidDetailsException {
		Account savedAcc = findAccountById(accNum);
		Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId).get();
		if (savedAcc != null && beneficiary != null) {
			List<Beneficiary> allBeneficiary = savedAcc.getBeneficiaries();
			allBeneficiary.add(beneficiary);
			savedAcc.setBeneficiaries(allBeneficiary);
			if (savedAcc instanceof SavingsAccount) {
				SavingsAccount sa = (SavingsAccount) savedAcc;
				addSavingsAccount(sa);
				return sa;
			}
			if (savedAcc instanceof CurrentAccount) {
				CurrentAccount ca = (CurrentAccount) savedAcc;
				addCurrentAccount(ca);
				return ca;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public Account addTransactionToAccount(long transactionId, long accNum)
			throws InvalidAccountException, InvalidDetailsException {
		Account savedAcc = findAccountById(accNum);
		Transaction transaction = transactionRepository.findById(transactionId).get();
		if (savedAcc != null && transaction != null) {
			List<Transaction> allTransactions = savedAcc.getTransactions();
			allTransactions.add(transaction);
			savedAcc.setTransactions(allTransactions);
			if (savedAcc instanceof SavingsAccount) {
				SavingsAccount sa = (SavingsAccount) savedAcc;
				addSavingsAccount(sa);
				return sa;
			}
			if (savedAcc instanceof CurrentAccount) {
				CurrentAccount ca = (CurrentAccount) savedAcc;
				addCurrentAccount(ca);
				return ca;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public Account allocateUserToAccount(long accNum, long userId)
			throws InvalidAccountException, InvalidDetailsException {
		Account account = accountRepository.findById(accNum).orElseThrow(
				() -> new InvalidAccountException("Enter Correct Account Number.", UserServiceImpl.class + ""));
		BankUser user = userRepository.findById(userId).orElseThrow(
				() -> new InvalidDetailsException("Enter Correct Details of User.", UserServiceImpl.class + ""));
		if (account != null && user != null && user.getRole() == Role.CUSTOMER) {
			account.setUser(user);
			return account;
		}
		return null;
	}
}
