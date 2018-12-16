package com.dms.database.mapping;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Formula(value = "concat(firstname,' ',lastname)")
	private String fullName;

	@Column(name = "email_id")
	private String email;

	@Column(name = "gender")
	private String gender;

	@Column(name = "password")
	private String password;

	@Column(name = "master_password")
	private String masterPassword;
	
	@Column(name = "mobilenumber")
	private String mobilenumber;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "active", nullable = false)
	private boolean active;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserRoleMapping> userRoleMappingSet = new HashSet<UserRoleMapping>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "actionTakenBy", cascade = CascadeType.ALL)
	private Set<TaskHistory> taskHistoriesSet  = new HashSet<TaskHistory>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMasterPassword() {
		return masterPassword;
	}

	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	
	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<UserRoleMapping> getUserRoleMappingSet() {
		return userRoleMappingSet;
	}

	public void setUserRoleMappingSet(Set<UserRoleMapping> userRoleMappingSet) {
		this.userRoleMappingSet = userRoleMappingSet;
	}

	public Set<TaskHistory> getTaskHistoriesSet() {
		return taskHistoriesSet;
	}

	public void setTaskHistoriesSet(Set<TaskHistory> taskHistoriesSet) {
		this.taskHistoriesSet = taskHistoriesSet;
	}
}
