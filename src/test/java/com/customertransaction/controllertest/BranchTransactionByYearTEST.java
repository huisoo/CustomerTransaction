package com.customertransaction.controllertest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.customertransaction.controller.TransactionSearchController;

@SpringBootTest
public class BranchTransactionByYearTEST {
	@Autowired
	private TransactionSearchController transactionSearchController;
	
	private MockMvc mockMvc;
	
	@Test
	public void BranchTransactionByYearAPI() throws Exception {
		mockMvc =MockMvcBuilders.standaloneSetup(transactionSearchController).addFilters(new CharacterEncodingFilter("UTF-8", true)).build();
		
		mockMvc.perform(
			MockMvcRequestBuilders.get("/BranchTransactionByYear")
		).andDo(MockMvcResultHandlers.print());
		
		
	}
	
}
