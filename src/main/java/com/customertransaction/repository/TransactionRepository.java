package com.customertransaction.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;

import com.customertransaction.model.Transaction;
import com.customertransaction.model.TransactionPK;



public interface TransactionRepository extends JpaRepository<Transaction, TransactionPK>{
	
	List<Transaction>findByiscancelEquals(String iscancel);
	
	
	@Query(value = "SELECT  year, acctNo, sumAmt, account.accountname as name\r\n" + 
			"FROM\r\n" + 
			"        (\r\n" + 
			"			select substring(transactiondate,1,4) as year, accountnumber as acctNo, sum(cost-fees) as maxcost, MAX(sum(cost-fees)) OVER(PARTITION BY substring(transactiondate,1,4)) AS sumAmt\r\n" + 
			"			from transaction.transaction\r\n" + 
			"            where transaction.iscancel = 'N'\r\n" + 
			"			group by substring(transactiondate,1,4), accountnumber\r\n" + 
			"            \r\n" + 
			"			\r\n" + 
			"        ) T, transaction.account\r\n" + 
			"WHERE   T.maxcost = T.sumAmt\r\n" + 
			"		and account.accountnumber = T.acctNo", nativeQuery=true)
	List<Map<String,Object>> findmaxCustomerByYear();
	
	
	@Query(value="select substring(transactiondate,1,4) as year, branch.branchcode as brCode, branch.branchname as brname, sum(cost-fees) as sumAmt \r\n" + 
			"from transaction, account, branch\r\n" + 
			"where transaction.accountnumber = account.accountnumber and branch.branchcode = account.branchcode\r\n" + 
			"	and transaction.iscancel='N' and substring(transactiondate,1,4) = :year\r\n" + 
			"group by substring(transactiondate,1,4), branch.branchcode\r\n" + 
			"order by substring(transactiondate,1,4) asc , sum(cost-fees) desc", nativeQuery=true)
	List<Map<String,Object>> BranchTransactionByYear(@Param("year") String year);
	
	
	@Query(value="select substring(transactiondate,1,4) as year, accountnumber\r\n" + 
			"    from transaction\r\n" + 
			"    where iscancel='N' and substring(transactiondate,1,4) = :year\r\n" + 
			"    group by substring(transactiondate,1,4), accountnumber", nativeQuery=true)
	List<Map<String,Object>> TransactionAccountListbyYear(@Param("year") String year);
	
	@Query(value=" select distinct substring(transactiondate,1,4) as year\r\n" + 
			"    from transaction", nativeQuery=true)
	List<Map<String, String>> TransactionYear();
}

