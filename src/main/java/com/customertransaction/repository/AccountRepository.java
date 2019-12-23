package com.customertransaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customertransaction.model.Account;
import com.customertransaction.model.Branch;

public interface AccountRepository extends JpaRepository<Account, String>  {
	List<Account> findByBranch(Branch branch);
	
}
