package com.example.demo;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.example.demo.controller.BankController;
import com.example.demo.model.BankAccount;
import com.example.demo.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(BankController.class)
public class BankControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BankService mockBankService;

    @InjectMocks
    private BankController bankController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        // Initializes @Mock and @InjectMocks
        // objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc=MockMvcBuilders.standaloneSetup(bankController).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BankAccount account=new BankAccount("PK123789J", "Riyanka Kundu");
        when(mockBankService.createAccount(account)).thenReturn("Account created successfully!");

        mockMvc.perform(post("/api/bank/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                        .andExpect(status().isCreated())
                        .andExpect(content().string("Account created successfully!"));                   
    }

    @Test
    public void testGetAllAccounts() throws Exception{
        List<BankAccount> accounts=Arrays.asList(
            new BankAccount("PK123789J", "Riyanka Kundu"),
            new BankAccount("KJM23567L", "Tamoghna Ghosh"));
            when(mockBankService.getAllAccounts()).thenReturn(accounts);

            mockMvc.perform(get("/api/bank/accounts"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].accountNumber").value("PK123789J"));

                    // .andExpect(jsonPath("$[1].accountHolder").value("Riyanka Kundu"));
    }

    @Test
    public void testGetAccount() throws Exception{
        BankAccount account=new BankAccount("PK123789J", "Riyanka Kundu");
        when(mockBankService.getAccount("PK123789J")).thenReturn(account);

        mockMvc.perform(get("/api/bank/account/PK123789J"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("PK123789J"))
                .andExpect(jsonPath("$.accountHolder").value("Riyanka Kundu"));
    }

//     // @Test
//     // public void testUpdateAccountHolder() throws Exception{
//     //     when(mockBankService.updateAccountHolder("@Test\n" + //
//     //                     "    public void testGetAllAccounts() throws Exception{\n" + //
//     //                     "        List<BankAccount> accounts=Arrays.asList(\n" + //
//     //                     "            new BankAccount(\"PK123789J\", \"Riyanka Kundu\"),\n" + //
//     //                     "            new BankAccount(\"KJM23567L\", \"Tamoghna Ghosh\"));\n" + //
//     //                     "            when(mockBankService.getAllAccounts()).thenReturn(accounts);\n" + //
//     //                     " \n" + //
//     //                     "            mockMvc.perform(get(\"/api/bank/accounts\"))\n" + //
//     //                     "                    .andExpect(status().isOk())\n" + //
//     //                     "                    .andExpect(jsonPath(\"$[0].accountNumber\").value(\"PK123789J\"))\n" + //
//     //                     "                    .andExpect(jsonPath(\"$[1].accountHolder\").value(\"Riyanka Kundu\"));\n" + //
//     //                     "    }\n" + //
//     //                     " ", null));
//     // }

    @Test
    public void testUpdateAccountHolder() throws Exception{
        when(mockBankService.updateAccountHolder("PK123789J", "Riyanka Kundu")).thenReturn("Account holder updated successfully!");

        mockMvc.perform(put("/api/bank/account/PK123789J/holder/Riyanka Kundu"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account holder updated successfully!"));
    }

    @Test
    public void testDeposit() throws Exception{
        when(mockBankService.deposit("PK123789J", 500.0)).thenReturn("Deposited Rs. 500.0 successfully!");

        mockMvc.perform(put("/api/bank/account/PK123789J/deposit/500"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposited Rs. 500.0 successfully!"));
    }

    @Test
    public void testWithdraw() throws Exception{
        when(mockBankService.withdraw("PK123789J", 200.0)).thenReturn("Withdrawn Rs. 200.0 successfully!");
        
        mockMvc.perform(put("/api/bank/account/PK123789J/withdrawal/200"))
                .andExpect(status().isOk())
                .andExpect(content().string("Withdrawn Rs. 200.0 successfully!"));
    }

    @Test
    public void testDeleteAccount() throws Exception{
        when(mockBankService.deleteAccount("PK123789J")).thenReturn("Account deleted successfully!");

        mockMvc.perform(delete("/api/bank/account/PK123789J/delete"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted successfully!"));
    }
}
