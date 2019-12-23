package com.customertransaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
	
	@Id	@Column(name="accountnumber", length = 8, nullable = false)
	private String accountnumber;
	
	private String accountname;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branchcode")
	private Branch branch;
	

}

