    package com.yourcompany.projet_bmo.model;

import javax.persistence.*;

import lombok.*;

@Entity
	@Getter
	@Setter
public class Parieur extends Utilisateur {



		@Column(length=50)
	private Float capitalInitial;

		@Column(length=50)
	private Float jetons;

public void rechargerJetons(Float montant) {
this.jetons+=montant; 
}

}
    
