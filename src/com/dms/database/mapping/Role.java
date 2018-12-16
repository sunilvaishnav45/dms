package com.dms.database.mapping;

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

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "role_name")
	private String roleName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
	private Set<UserRoleMapping> userRoleMappingSet = new HashSet<UserRoleMapping>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<UserRoleMapping> getUserRoleMappingSet() {
		return userRoleMappingSet;
	}

	public void setUserRoleMappingSet(Set<UserRoleMapping> userRoleMappingSet) {
		this.userRoleMappingSet = userRoleMappingSet;
	}

}
