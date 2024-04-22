package com.tonigdev.api.auth.nikelao.model;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nikelao_user")
@Getter @Setter @NoArgsConstructor
public class NikelaoUsers implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7902293125310059757L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String pass;
	@Column(name = "active", nullable = false)
	private boolean active;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_has_role",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id"),
				foreignKey = @ForeignKey(name="fk_user_of_rol"),
				inverseForeignKey = @ForeignKey(name="fk_rol_of_user"))
	private Set<NikelaoRoles> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> auth = new HashSet<GrantedAuthority>();
		roles.forEach(r -> auth.add(new SimpleGrantedAuthority(r.getCode())));
		return auth;
	}

	@Override
	public String getPassword() {
		return pass;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
	
	public void createUser(String username, String pass) {
		this.username = username;
		this.pass = pass;
		this.active = true;
		this.roles = new HashSet<>();
	}
	

}
