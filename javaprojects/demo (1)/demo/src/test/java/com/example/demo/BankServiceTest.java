package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.BankAccount;
import com.example.demo.repository.BankRepository;
import com.example.demo.service.BankService;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankRepository mockBankRepository;

    @InjectMocks
    private BankService mockBankService;

    private BankAccount bankAccount;

    @BeforeEach
    public void setup(){
        bankAccount=new BankAccount("2129YJKP", "Riyanka Kundu");
        bankAccount.setBalance(5000);
    }

    @Test
    public void testCreateAccount_Success(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList());
        when(mockBankRepository.save(bankAccount)).thenReturn(bankAccount);
        String result=mockBankService.createAccount(bankAccount);
        assertEquals("Account created successfully!", result);
        verify(mockBankRepository, times(1)).save(bankAccount);
    }

    @Test
    public void testGetAllAccounts_Success(){
        List<BankAccount> accounts=Arrays.asList(new BankAccount("2129YJKP", "Riyanka Kundu"),
                                                new BankAccount("2129YJLJ", "Tamoghna Ghosh"));
        when(mockBankRepository.findAll()).thenReturn(accounts);
        List<BankAccount> result=mockBankService.getAllAccounts();
        assertEquals(2, result.size());
        assertEquals("Riyanka Kundu", result.get(0).getAccountHolder());
        assertEquals("Tamoghna Ghosh", result.get(1).getAccountHolder());
        verify(mockBankRepository, times(1)).findAll();
    }

    @Test
    public void testCreateAccount_AlreadyExists(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.createAccount(new BankAccount("2129YJKP", "Riyanka Kundu"));
        assertEquals("Account number already exists!", result);
        verify(mockBankRepository, never()).save(any());
    }

    @Test
    public void testGetAccount_Found(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        BankAccount result=mockBankService.getAccount("2129YJKP");
        assertNotNull(result);
        assertEquals("Riyanka Kundu", result.getAccountHolder());
    }

    @Test
    public void testGetAccount_NotFound(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList());
        BankAccount result=mockBankService.getAccount("212789PK");
        assertNull(result);
    }

    @Test
    public void testUpdateAccountHolder_Success(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.updateAccountHolder("2129YJKP", "Riyanka Ghosh");
        assertEquals("Account holder updated successfully!", result);
        assertEquals("Riyanka Ghosh", bankAccount.getAccountHolder());
        verify(mockBankRepository, times(1)).save(bankAccount);
    }

    @Test
    public void testUpdateAccountHolder_NotFound(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList());
        String result=mockBankService.updateAccountHolder("212789PK", "Riyanka Ghosh");
        assertEquals("Account not found!", result);
    }

    @Test
    public void testDeposit_Success(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        when(mockBankRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        String result=mockBankService.deposit("2129YJKP", 2000.0);
        assertEquals("Deposited Rs. 2000.0 successfully!", result);
        assertEquals(7000.0, bankAccount.getBalance());
    }

    @Test
    public void testDeposit_InvalidAmount(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.deposit("2129YJKP", -500.0);
        assertEquals("Please enter a valid amount!", result);
    }

    @Test
    public void testDeposit_AccountNotFound(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.deposit("2129YJL", 1000.0);
        assertEquals("Account not found!", result);
    }

    @Test
    public void testWithdraw_Success(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        when(mockBankRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        String result=mockBankService.withdraw("2129YJKP", 1000.0);
        assertEquals("Withdrawn Rs. 1000.0 successfully!", result);
        assertEquals(4000.0, bankAccount.getBalance());
    }

    @Test
    public void testWithdraw_InsufficientBalance(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.withdraw("2129YJKP", 6000.0);
        assertEquals("Insufficient balance!", result);
    }

    @Test
    public void testWithdraw_AccountNotFound(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.withdraw("2129YJKL", 3000.0);
        assertEquals("Account not found!", result);
    }

    @Test
    public void testDeleteAccount_Success(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.deleteAccount("2129YJKP");
        assertEquals("Account deleted successfully!", result);
        verify(mockBankRepository, times(1)).delete(bankAccount);
    }

    @Test
    public void testDeleteAccount_NotFound(){
        when(mockBankRepository.findAll()).thenReturn(Arrays.asList(bankAccount));
        String result=mockBankService.deleteAccount("2129YJKO");
        assertEquals("Account not found!", result);
    }
}
