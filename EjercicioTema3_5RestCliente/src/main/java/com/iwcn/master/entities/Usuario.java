package com.iwcn.master.entities;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String usuario;
    private String contra;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> roles;
    
    protected Usuario() {
    	
    }

    public Usuario(String usuario, String contra, List<GrantedAuthority> roles) {
        this.usuario = usuario;
        this.contra = new BCryptPasswordEncoder().encode(contra);
        this.roles = roles;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}

	public List<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}

    public String getPasswordHash() {
        return contra;
    }

    public void setPasswordHash(String passwordHash) {
        this.contra = passwordHash;
    }

}