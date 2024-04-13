package com.tonigdev.api.auth.nikelao.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="nikelao_role")
@Getter @Setter @NoArgsConstructor
public class NikelaoRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String code;
	@Column(nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<NikelaoUser> usuarios;

}
