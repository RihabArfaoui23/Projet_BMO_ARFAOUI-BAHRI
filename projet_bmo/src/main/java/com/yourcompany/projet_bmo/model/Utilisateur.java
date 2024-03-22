    package com.yourcompany.projet_bmo.model;

import javax.persistence.*;

import lombok.*;

@Entity
	@Getter
	@Setter
public class Utilisateur {

	@Id
	@Column(length=10)
	private Integer userId;

		@Column(length=50)
	private String email;

		@Column(length=50)
	private String password;

public void login(String email, String password) {
	// This method already implemented in openxava
}

}
    
