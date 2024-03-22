package com.yourcompany.projet_bmo.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

@Entity
	@Getter
	@Setter
public class EvenementSportif {

	@Id
	@Column(length=10)
	private Integer idEvenement;

		@Column(length=50)
	private String typeEvenement;


		@Column(length=50)
	private Date dateDebut;

		@Column(length=50)
	private Date dateFin;

public String consulterEvenementSportif() {
return typeEvenement+" "+ dateDebut +" "+ dateFin;
}

}