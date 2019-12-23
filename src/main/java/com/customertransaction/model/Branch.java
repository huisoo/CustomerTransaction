package com.customertransaction.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "branch")
public class Branch {
	
	@Id
	@Column(length = 1, nullable = false)
	private String branchcode;
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//private Long branchid;
	@Column(name = "branchname", length = 3, nullable = false)
	private String branchname;

	//@OneToOne(fetch = FetchType.LAZY, mappedBy = "branch", optional = false)
    //private Account account;
	
	//@OneToOne(mappedBy = "branch")
	//private List<Account> accounts = new ArrayList<Account>();

	
	
	
	
}