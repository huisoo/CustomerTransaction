package com.customertransaction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customertransaction.service.TransactionSearchService;

@RestController
public class TransactionSearchController {
	
	@Autowired
	private TransactionSearchService transactionSearchService;
	
	@GetMapping(value="/maxCustomerByYear")
	public List<Map<String,Object>> maxCustomerByYear() {
		//2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.
		//(단, 취소여부가 ‘Y’ 거래는 취소된 거래임, 합계 금액은 거래금액에서 수수료를 차감한 금액임)
		/*SELECT  years, accNo, maxcost, account.accountname
		FROM
		        (
					select substring(transactiondate,1,4) as years, accountnumber as accNo, sum(cost-fees) as maxcost, MAX(sum(cost-fees)) OVER(PARTITION BY substring(transactiondate,1,4)) AS MAX_SEQ
					from transaction.transaction
		            where transaction.iscancel = 'N'
					group by substring(transactiondate,1,4), accountnumber
		            
					
		        ) T, transaction.account
		WHERE   T.maxcost = T.MAX_SEQ
				and account.accountnumber = T.accNo*/	
		
		/*[
		{“year”:2018,
		   “name”:”계좌명”,
		   “acctNo”:”계좌번호”,
		   “sumAmt”:0000}
		{“year”:2019,
		   “name”:”계좌명”,
		   “acctNo”:”계좌번호”,
		   “sumAmt”:0000}
		]*/

		return transactionSearchService.maxCustomerByYear();
	}
	
	@GetMapping(value="/emptyTransactionCustomerByYear")
	public List<Map<String,Object>> emptyTransactionCustomerByYear() {
		//2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
		//(취소여부가 ‘Y’ 거래는 취소된 거래임)
		
		/*[
		{“year”:2018,
		“name”:”계좌명”,
		“acctNo”:”계좌번호”}
		…
		{“year”:2019,
		“name”:”계좌명”,
		“acctNo”:”계좌번호”}
		…
		]*/
		//List<Map<String,Object>> transactionSearchList = 
		return transactionSearchService.emptyCustomer();
	}
	
	@GetMapping(value="/BranchTransactionByYear")
	public List<Map<String,Object>> BranchTransactionByYear() {
		/*API에 맞게 수정 필요*/	
		//연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
		//( 취소여부가 ‘Y’ 거래는 취소된 거래임)	
		
		/*select substring(transactiondate,1,4), branch.branchcode, branch.branchname, sum(cost-fees)
		from transaction, account, branch
		where transaction.accountnumber = account.accountnumber and branch.branchcode = account.branchcode
			and transaction.iscancel='N'
		group by substring(transactiondate,1,4), branch.branchcode
		order by sum(cost-fees) desc*/
		
		/*[
			{“year”: 2018, 
				“dataList”:[
				    {
				“brName”: “관리점명”,
				“brCode”: “관리점코드”,
				“sumAmt”: 0000 },
				…
				  ]
			},
			{“year”: 2019, 
				“dataList”:[
				{
				“brName”: “관리점명”,
				“brCode”: “관리점코드”,
				“sumAmt”: 0000 },
				…
				]
			}
		]*/

		return transactionSearchService.BranchTransactionByYear();
	}
	
	@GetMapping(value="/TransactionCostByBranch")
	//code = HttpStatus.NOT_FOUND, reason = "video not found"
	public Map<String, Object> TransactionCostByBranch(@RequestBody Map<String, String> params) {
		//@RequestBody Map<String, String> params
		//분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 
		//지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발( 취소여부가 ‘Y’ 거래는 취소된 거래임,)
		//1) 지점명 검색, 코드결과
		//2) 코드로 계좌리스트를 가져온다 findByCode(String Code), 코드 입력
		//3) 계좌리스트 IN ('') and iscancel N 으로 한 것의 합계 출력해서 형태 만듬.
		/*입력	{
			“brName”:”관리점명” 
			}
			출력	{
			“brName”:”관리점명”, 
			 “brCode”:”관리점코드”, 
			 “sumAmt”:0000
			}
			★분당점 출력시
			http status : 404
			{
			“code”:”404”,
			“메세지”:”br code not found error”
			}*/
		return transactionSearchService.TransactionCostByBranch(params.get("brName"));
	}
	
	

}
