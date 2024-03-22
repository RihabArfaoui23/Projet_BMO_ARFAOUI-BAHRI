package com.yourcompany.projet_bmo.model;

import javax.persistence.*;

import lombok.*;

@Entity
	@Getter
	@Setter
public class PariSimple extends Paris {



		@Column(length=50)
	private String typePari;

		@Column(length=50)
	private Float montantMise;

		@Column(length=50)
	private String resultatAttendu;

		@Column(length=50)
	private Float cote;

}