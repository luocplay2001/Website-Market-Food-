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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="orderId")
	private Long orderId;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "status_shipper")
	private int status_shipper;
	
	@Column(name = "status_customer")
	private int status_customer;
	
	@Column(name = "status_restaurant")
	private int status_restaurant;
	
	@Column(name = "feedback_shipper")
	private String feedback_shipper;
	
	@Column(name = "feedback_restaurant")
	private String feedback_restaurant;
	
	@Column(name = "orderDate")
	private String orderDate;
	
	@Column(name = "orderDateDelivered")
	private String orderDateDelivered;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "shipperId")
	private Shipper shipper;
	
	@ManyToOne
	@JoinColumn(name = "restaurantId")
	private Restaurant restaurant;
	
}
