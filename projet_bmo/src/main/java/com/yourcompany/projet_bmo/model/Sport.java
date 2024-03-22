package com.yourcompany.projet_bmo.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
	@Getter
	@Setter
public class Sport {

	@Id
	@Column(length=10)
	private Integer sportId;

		@Column(length=50)
	private String nom;

		@Column(length=50)
	private String regles;

	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	private EvenementSportif evenementsportif;

}