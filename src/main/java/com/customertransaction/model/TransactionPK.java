package com.customertransaction.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

//@Embeddable
@Data
public class TransactionPK implements Serializable{
	
	private String transactiondate;
	private String account;
	private int transactionnumber;
	
	//@ManyToMany(mappedBy = "account")
		//private List<Account> accounts = new ArrayList<Account>();
	/*
	public TransactionPK(){}
	public TransactionPK(String transactiondate, String accountnumber, int transactionnumber){
		super();
		this.transactiondate = transactiondate;
		this.transactionnumber = transactionnumber;
		this.accountnumber = accountnumber;
	}*/
}
