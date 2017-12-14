package com.example.playspringboot;

import java.util.*;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class User implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@Column(nullable = false, unique = true, updatable = false)
	@NotBlank
	@Size(max = 10)
	private String id;

	@Column(nullable = false)
	@NotBlank
	@Size(max = 25)
	private String name;

	@Transient
	// unchecked the size that confirmPassword equals password
	private String password;

	@Column(nullable = false, length=25)
	private String createdBy;

	@Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date createdAt;

	@Column(nullable = false, length=25)
	private String updatedBy;

	@Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat (pattern="yyyy-MM-dd HH:mm:ss.SSS")
	private Date updatedAt;

	@Transient
	// unchecked the size that confirmPassword equals password
	private String confirmPassword;

	@Column(nullable = false, length=32)
	private String passwordDigest;

	public boolean isConfirmed() {
		return this.password.equals(this.confirmPassword);
	}

	public boolean isValidPassword() {
		//TODO check password size
		return true;
	}
}