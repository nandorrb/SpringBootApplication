package com.company.entity;

import java.io.Serializable;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", length = 50, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = 800, nullable = false)
	private String password;

	@Column(name = "first_name", length = 50, nullable = false)
	private String firstname;

	@Column(name = "last_name", length = 50, nullable = false)
	private String last_name;

	@Formula(" concat(first_name, ' ', last_name) ")
	private String fullname;

	@Column(name = "`Role`")
	@Enumerated(EnumType.STRING)
	private Role role;



	public enum Role {
		ADMIN,SELLER, BUYER
	}
}
