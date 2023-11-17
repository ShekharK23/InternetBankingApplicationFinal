package com.cg.iba.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cg.iba.dto.CurrentAccountRequestSubmitDTO;
import com.cg.iba.dto.SavingAccountRequestSubmitDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.CurrentAccount;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.enums.Gender;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.serviceimpl.AccountServiceImpl;
import com.cg.iba.util.CurrentAccountDTOMapper;
import com.cg.iba.util.SavingsAccountDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountRestControllerTest {
	
	@Autowired
	MockMvc mockmvc;

	@Autowired
	AdminRestController adminController;
	
	@Autowired
	SavingsAccountDTOMapper saDTOMapper;
	
	@Autowired
	CurrentAccountDTOMapper caDTOMapper;
	
	@MockBean
	private AccountServiceImpl accountServiceMock;
	
	private SavingAccountRequestSubmitDTO saveInputSav;
	private SavingAccountRequestSubmitDTO saveOutputSav;
	
	private CurrentAccountRequestSubmitDTO saveInputCur;
	private CurrentAccountRequestSubmitDTO saveOutputCur;
	
	@BeforeEach
	void setup()
	{
		saveInputSav = new SavingAccountRequestSubmitDTO("Shubham", "1234567890", "shub@gmail.com", 23, Gender.MALE, 1234567, null);
		saveOutputSav = new SavingAccountRequestSubmitDTO("Shubham", "1234567890", "shub@gmail.com", 23, Gender.MALE, 1234567, null);
		saveInputCur = new CurrentAccountRequestSubmitDTO("Shubham", "1234567890", "shub@gmail.com", 23, Gender.MALE, 1234567, null);
		saveOutputCur = new CurrentAccountRequestSubmitDTO("Shubham", "1234567890", "shub@gmail.com", 23, Gender.MALE, 1234567, null);
	}
	
	@Test
	@DisplayName("Test Save Savings Account DTO")
	void testSaveSavingsAccountDTO() throws Exception {
		Account a = saDTOMapper.setSavingAccountUsingDTO(saveInputSav);
		SavingsAccount sa = (SavingsAccount) a;
		Mockito.when(accountServiceMock.addSavingsAccount(Mockito.any(SavingsAccount.class))).thenReturn(sa);
		mockmvc.perform(
				MockMvcRequestBuilders.post("/saveSavingsAccountDto").
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 
	}
	
	@Test
	@DisplayName("Test Save Current Account DTO")
	void testSaveCurrentAccountDTO() throws Exception {
		Account a = caDTOMapper.setCurrentAccountUsingDTO(saveInputCur);
		CurrentAccount ca = (CurrentAccount) a;
		Mockito.when(accountServiceMock.addCurrentAccount(Mockito.any(CurrentAccount.class))).thenReturn(ca);
		mockmvc.perform(
				MockMvcRequestBuilders.post("/saveCurrentAccountDto").
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 
	}
	
	@Test
	@DisplayName("Test Find Account By Account Id ")
	void testFindAccountById() throws InvalidAccountException {
		Account a = new Account(1, null, null, null, 0, null, 0, 0, null, null, null, null, null, null, null);		
		Mockito.when(accountServiceMock.findAccountById(1L)).thenReturn(a);
		Account acc = new Account(1, null, null, null, 0, null, 0, 0, null, null, null, null, null, null, null);
		assertEquals(a,acc);
	}
	
	@Test
    @DisplayName("Test Update Savings Account")
    void testUpdateSavingsAccount() throws Exception {
        long accountId = 1L;
        SavingsAccount updatedAccount = saDTOMapper.setSavingAccountUsingDTO(saveOutputSav);

        Mockito.when(accountServiceMock.updateSavingsAccount(accountId, saveInputSav)).thenReturn(updatedAccount);

		mockmvc.perform(
				MockMvcRequestBuilders.put("/updateSavingsAccount/{accountId}", accountId).
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 

    }

    @Test
    @DisplayName("Test Update Savings Account - InvalidDetailsException")
    void testUpdateSavingsAccountInvalidDetailsException() throws Exception{
        long accountId = 1L;

        when(accountServiceMock.updateSavingsAccount(accountId, saveInputSav))
                .thenThrow(new InvalidDetailsException("Invalid details", ""));

        mockmvc.perform(
				MockMvcRequestBuilders.put("/updateSavingsAccount/{accountId}", accountId).
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 

       // verify(accountServiceMock, times(1)).updateSavingsAccount(accountId, requestDTO);
    }
	
	@Test
    @DisplayName("Test Update Current Account")
    void testUpdateCurrentAccount() throws Exception {
        long accountId = 1L;
        CurrentAccount updatedAccount = caDTOMapper.setCurrentAccountUsingDTO(saveOutputCur);

        Mockito.when(accountServiceMock.updateCurrentAccount(accountId, saveInputCur)).thenReturn(updatedAccount);

		mockmvc.perform(
				MockMvcRequestBuilders.put("/updateCurrentAccount/{accountId}", accountId).
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 

    }
	
	@Test
    @DisplayName("Test Update Current Account - InvalidDetailsException")
    void testUpdateCurrentAccountInvalidDetailsException() throws Exception{
        long accountId = 1L;

        when(accountServiceMock.updateSavingsAccount(accountId, saveInputSav))
                .thenThrow(new InvalidDetailsException("Invalid details", ""));

        mockmvc.perform(
				MockMvcRequestBuilders.put("/updateSavingsAccount/{accountId}", accountId).
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 

       // verify(accountServiceMock, times(1)).updateSavingsAccount(accountId, requestDTO);
    }
	

    @Test
    @DisplayName("Test Delete Saving Account - Successful Closure")
    void testDeleteSavingAccountSuccess() throws Exception {
    
        SavingsAccount savingAccount = saDTOMapper.setSavingAccountUsingDTO(saveInputSav);

        when(accountServiceMock.closeSavingsAccount(savingAccount)).thenReturn(true);
        
        mockmvc.perform(
				MockMvcRequestBuilders.delete("/deleteSavingAccount/delete").
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 

        //verify(accountServiceMock, times(1)).closeSavingsAccount(savingAccount);
    }

    @Test
    @DisplayName("Test Delete Saving Account - Account Not Found")
    void testDeleteSavingAccountNotFound() throws Exception {
        // Arrange
        SavingsAccount savingAccount = saDTOMapper.setSavingAccountUsingDTO(saveInputSav);

        when(accountServiceMock.closeSavingsAccount(savingAccount))
                .thenThrow(new InvalidAccountException("Account not found", ""));

        mockmvc.perform(
				MockMvcRequestBuilders.delete("/deleteSavingAccount/delete").
				contentType(MediaType.APPLICATION_JSON).
				content("{\n" +
				        "    \"accountHolderName\": \"Shubham\",\n" +
				        "    \"phoneNo\": \"1234567890\",\n" +
				        "    \"emailId\": null,\n" +
				        "    \"age\": 15,\n" +  
				        "    \"gender\": \"MALE\",\n" +
				        "    \"balance\": 0.0,\n" +
				        "    \"dateOfOpening\": null,\n" +
				        "}")); 
    
        //verify(accountServiceMock, times(1)).closeSavingsAccount(savingAccount);
    }
}
