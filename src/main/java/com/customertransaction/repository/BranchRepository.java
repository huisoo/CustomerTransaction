package com.customertransaction.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.customertransaction.model.Branch;


public interface BranchRepository extends JpaRepository<Branch, String>{
	Branch findByBranchname(String branchname);
	
}
