package com.customertransaction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.customertransaction.model.Account;
import com.customertransaction.model.Branch;
import com.customertransaction.model.Transaction;
import com.customertransaction.repository.AccountRepository;
import com.customertransaction.repository.BranchRepository;
import com.customertransaction.repository.TransactionRepository;



@Service
public class TransactionSearchService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public List<Map<String,Object>> emptyCustomer() {
		
		
		List<Map<String,String>> transactionYear = transactionRepository.TransactionYear();
		List<Map<String,Object>> retVal = new ArrayList<Map<String,Object>>();
		for(int idx=0; idx<transactionYear.size();idx++) {
			String targetYear = transactionYear.get(idx).get("year");
			List<Map<String,Object>> transactionAccountListbyYear = transactionRepository.TransactionAccountListbyYear(targetYear);
			List<Account> account = accountRepository.findAll();
			
			List<Object> sameVal = new ArrayList<Object>();
			for(int jdx=0;jdx<account.size();jdx++) {
				for(int transactionIdx=0;transactionIdx<transactionAccountListbyYear.size();transactionIdx++) {
					if(account.get(jdx).getAccountnumber().equals(transactionAccountListbyYear.get(transactionIdx).get("accountnumber"))) {
						sameVal.add(account.get(jdx));
						break;
					}		
				}
			}
			
			for(int sameValIdx=0;sameValIdx<sameVal.size();sameValIdx++) {
				account.remove(sameVal.get(sameValIdx));
			}
			
			for(int lv=0;lv<account.size();lv++) {
				Map<String,Object> resultObj = new HashMap<String,Object>();
				resultObj.put("year",targetYear);
				resultObj.put("name",account.get(lv).getAccountname());
				resultObj.put("acctNo",account.get(lv).getAccountnumber());
				retVal.add(resultObj);
			}
		}
		
		return retVal;
	}
	
	public List<Map<String, Object>> BranchTransactionByYear() {
		/*{“year”: 2018, 
			“dataList”:[
			    {
			“brName”: “관리점명”,
			“brCode”: “관리점코드”,
			“sumAmt”: 0000 },
			…
			  ]
			},*/
		
		List<Map<String,String>> transactionYear = transactionRepository.TransactionYear();
		
		
		List<Map<String, Object>> retVal = new ArrayList<Map<String, Object>>();
		for(int idx=0;idx<transactionYear.size();idx++) {
			
			List<Map<String,Object>> BranchTransactionByYear = transactionRepository.BranchTransactionByYear(transactionYear.get(idx).get("year"));
			Map<String,Object> resultObj = new HashMap<String,Object>();
			
			resultObj.put("year", (String) transactionYear.get(idx).get("year"));
			List<Object> dataList= new ArrayList<Object>();
			for(int jdx=0;jdx<BranchTransactionByYear.size();jdx++){
				Map<String,Object> mapData = new HashMap<String,Object>();
				mapData.put("brName", BranchTransactionByYear.get(jdx).get("brname"));
				mapData.put("brCode", BranchTransactionByYear.get(jdx).get("brCode"));
				mapData.put("sumAmt", BranchTransactionByYear.get(jdx).get("sumAmt"));
				dataList.add(mapData);
			}
			resultObj.put("dataList", dataList);
			retVal.add(resultObj);
		}
		return retVal;
	}
	
	public List<Map<String,Object>> maxCustomerByYear() {
		return transactionRepository.findmaxCustomerByYear();
	}
	
	//4번
	public Map<String,Object> TransactionCostByBranch(String brName) {
		
		Branch branch = branchRepository.findByBranchname(brName);
		Map<String,Object> resultObj = new HashMap<String,Object>();
		
		if(branch==null) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "br code not found error"
					);
		}else {
			List<Account> account = accountRepository.findByBranch(branch);
			List<Transaction> transaction = transactionRepository.findByiscancelEquals("N");
			int sum = 0; String brCode = "";
			
			brCode = branch.getBranchcode();
			resultObj.put("brName",brName);
			resultObj.put("brCode",brCode);
			
			for(int idx =0; idx<account.size();idx++) {
					for(int jdx=0;jdx<transaction.size();jdx++) {
						Transaction Ttransaction = transaction.get(jdx);
						String TtransactionaccountNum = Ttransaction.getAccount().getAccountnumber();
						if(TtransactionaccountNum.equals(account.get(idx).getAccountnumber())) {
							sum += Ttransaction.getCost() - Ttransaction.getFees();				
						}
						resultObj.put("sumAmt",sum);
	
					}
			}
		}
		
		return resultObj;
	}
	
	
	
	

}


