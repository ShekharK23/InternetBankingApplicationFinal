package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.dto.CurrentAccountRequestSubmitDTO;
import com.cg.iba.dto.SavingAccountRequestSubmitDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.CurrentAccount;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.service.IAccountService;

@SpringBootTest
public class AccountServiceImplTest {

	@MockBean
	IAccountRepository accountRepository;
	
	@Autowired
	IAccountService accountService;
	
	@Test
	public void testAddSavingsAccount() throws InvalidDetailsException {
		
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountHolderName("Shubham"); 

        when(accountRepository.save(any(SavingsAccount.class))).thenReturn(savingsAccount);

        SavingsAccount actalOutput = accountService.addSavingsAccount(savingsAccount);

        assertNotNull(actalOutput);
        assertEquals("Shubham", actalOutput.getAccountHolderName());
     
        verify(accountRepository, times(1)).save(savingsAccount);
	}
	
	
	@Test
	public void testAddCurrentAccount() throws InvalidDetailsException {
		
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountHolderName("Shubham"); 

        when(accountRepository.save(any(CurrentAccount.class))).thenReturn(currentAccount);

        CurrentAccount actalOutput = accountService.addCurrentAccount(currentAccount);

        assertNotNull(actalOutput);
        assertEquals("Shubham", actalOutput.getAccountHolderName());
     
        verify(accountRepository, times(1)).save(currentAccount);
	}
	
	@Test
    @DisplayName("Test update SavingsAccount")
    void testUpdateSavingsAccount() throws InvalidDetailsException {
 
        long accountId = 1L;
        SavingAccountRequestSubmitDTO savingRequestDTO = new SavingAccountRequestSubmitDTO();
        savingRequestDTO.setAccountHolderName("Shubham");

        SavingsAccount existingAccount = new SavingsAccount();
        existingAccount.setAccountId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(SavingsAccount.class))).thenReturn(existingAccount);

        SavingsAccount updatedAccount = accountService.updateSavingsAccount(accountId, savingRequestDTO);

        assertNotNull(updatedAccount);
        assertEquals("Shubham", updatedAccount.getAccountHolderName());

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(SavingsAccount.class));
    }
	
	@Test
	@DisplayName("Test update CurrentAccount")
	void testUpdateCurrentAccount() throws InvalidDetailsException {
		
		long accountId = 1L;
		CurrentAccountRequestSubmitDTO currentRequestDTO = new CurrentAccountRequestSubmitDTO();
		currentRequestDTO.setAccountHolderName("Shubham");
		currentRequestDTO.setPhoneNo("1234567890");
		currentRequestDTO.setEmailId("shubham@gmail.com");
		
		CurrentAccount existingAccount = new CurrentAccount();
		existingAccount.setAccountId(accountId);
		
		when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
		when(accountRepository.save(any(CurrentAccount.class))).thenReturn(existingAccount);
		
		CurrentAccount updatedAccount = accountService.updateCurrentAccount(accountId, currentRequestDTO);
		
		assertNotNull(updatedAccount);
		assertEquals("Shubham", updatedAccount.getAccountHolderName());
		assertEquals("1234567890", updatedAccount.getPhoneNo());
		assertEquals("shubham@gmail.com", updatedAccount.getEmailId());
		
		verify(accountRepository, times(1)).findById(accountId);
		verify(accountRepository, times(1)).save(any(CurrentAccount.class));
	}

    @Test
    @DisplayName("Test update SavingsAccount Exception Handling")
    void testUpdateSavingsAccountException() {

        long accountId = 1L;
        SavingAccountRequestSubmitDTO savingRequestDTO = new SavingAccountRequestSubmitDTO();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(InvalidDetailsException.class, () -> {
            accountService.updateSavingsAccount(accountId, savingRequestDTO);
        });

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).save(any());
    }
	
    @Test
    @DisplayName("Test update CurrentAccount Exception Handling")
    void testUpdateCurrentAccountException() {

        long accountId = 1L;
        CurrentAccountRequestSubmitDTO currentRequestDTO = new CurrentAccountRequestSubmitDTO();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(InvalidDetailsException.class, () -> {
            accountService.updateCurrentAccount(accountId, currentRequestDTO);
        });

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Test close SavingsAccount")
    void testCloseSavingsAccount() throws InvalidAccountException {
 
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId(1L);

        when(accountRepository.findById(savingsAccount.getAccountId())).thenReturn(Optional.of(savingsAccount));

        boolean result = accountService.closeSavingsAccount(savingsAccount);

        assertTrue(result);

        verify(accountRepository, times(1)).findById(savingsAccount.getAccountId());
        verify(accountRepository, times(1)).deleteById(savingsAccount.getAccountId());
    }

//    @Test
//    @DisplayName("Test close SavingsAccount Exception Handling")
//    void testCloseSavingsAccountException() {
//
//        SavingsAccount savingsAccount = new SavingsAccount();
//        savingsAccount.setAccountId(1L);
//
//        when(accountRepository.findById(savingsAccount.getAccountId())).thenReturn(Optional.empty());
//
//        assertThrows(InvalidAccountException.class, () -> {
//            accountService.closeSavingsAccount(savingsAccount);
//        });
//
//        verify(accountRepository, times(1)).findById(savingsAccount.getAccountId());
//        verify(accountRepository, never()).deleteById(anyLong());
//    }
    
    @Test
    @DisplayName("Test close CurrentAccount")
    void testCloseCurrentAccount() throws InvalidAccountException {
 
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountId(1L);

        when(accountRepository.findById(currentAccount.getAccountId())).thenReturn(Optional.of(currentAccount));

        boolean result = accountService.closeCurrentAccount(currentAccount);

        assertTrue(result);

        verify(accountRepository, times(1)).findById(currentAccount.getAccountId());
        verify(accountRepository, times(1)).deleteById(currentAccount.getAccountId());
    }

//    @Test
//    @DisplayName("Test close CurrentAccount Exception Handling")
//    void testCloseCurrentAccountException() {
//
//        CurrentAccount currentAccount = new CurrentAccount();
//        currentAccount.setAccountId(1L);
//
//        when(accountRepository.findById(currentAccount.getAccountId())).thenReturn(Optional.empty());
//
//        assertThrows(InvalidAccountException.class, () -> {
//            accountService.closeCurrentAccount(currentAccount);
//        });
//
//        verify(accountRepository, times(1)).findById(currentAccount.getAccountId());
//        verify(accountRepository, never()).deleteById(anyLong());
//    }
    
    @Test
	void testFindAccountById() throws InvalidAccountException {
		Account actualOutput = new Account();
		actualOutput.setAccountHolderName("Shubham");
		
		Account a = new Account();
		a.setAccountHolderName("Shubham");
		
		Optional<Account> thisTypeOfOutput = Optional.of(a);
		when(accountRepository.findById(1L)).thenReturn(thisTypeOfOutput);
		
		assertEquals(actualOutput, accountService.findAccountById(1L));
	}
	
//    @Test
//    @DisplayName("Test to check findAccountByID Exception")
//    void testGetEmployeeByIdException() {
//        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
//
//        InvalidAccountException exception = assertThrows(InvalidAccountException.class, () -> {
//            accountService.findAccountById(1L);
//        });
//
//        assertEquals("Account Not Found.", exception.getMessage());
//
//        verify(accountRepository, times(1)).findById(1L);
//    }


    @Test
    @DisplayName("Test viewAccounts")
    void testViewAccounts() {

        List<Account> mockAccounts = Arrays.asList(
                new SavingsAccount(),
                new CurrentAccount()      
        );
        
        when(accountRepository.findAll()).thenReturn(mockAccounts);

        List<Account> result = accountService.viewAccounts();

        assertNotNull(result);
        assertEquals(mockAccounts.size(), result.size());

        verify(accountRepository, times(1)).findAll();
    }
}


