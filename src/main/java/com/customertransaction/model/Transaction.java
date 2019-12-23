package com.customertransaction.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@IdClass(TransactionPK.class)
@Table(name = "transaction")
public class Transaction {
	
	//@EmbeddedId
	
	@Id
	@Column(name="transactiondate", length = 8, nullable=false)
	private String transactiondate;
	
	@Id
	@ManyToOne
	@JoinColumn(name="accountnumber")
	public Account account;
	//@Column(name="accountnumber", length = 8, nullable=false)  //FK
	//private String accountnumber;
	
	@Id
	@Column(name="transactionnumber", nullable=false)
	private int transactionnumber;
	
	private int cost;
	private int fees;
	
	@Column(name="iscancel", length = 1, nullable=false)
	private String iscancel;
	
	
	//private Branch branch;
	
	//@ManyToMany(mappedBy = "account")
	//private List<Account> accounts = new ArrayList<Account>();
	//@ManyToOne
		//@JoinColumn(name="branchcode")
	
}