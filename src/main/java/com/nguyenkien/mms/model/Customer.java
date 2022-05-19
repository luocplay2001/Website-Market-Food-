package com.nguyenkien.mms.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Serializable{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="customerId")
	private Long customerId;	
	
	@Column(name = "name")
	private String name;

	@Column(name = "email",unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone")
	private String phone;	
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "gender")
	private int gender;
	
	@Column(name = "registerDate")
	private String registerDate;
	
	@ManyToOne
	@JoinColumn(name = "discountId")
	private Discount discount;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Order> orders;
	
}
