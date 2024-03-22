package com.yourcompany.projet_bmo.model;

import javax.persistence.*;

import lombok.*;

@Entity
	@Getter
	@Setter
public class Paris {

	@Id
	@Column(length=10)
	private Integer idParis;

		@Column(length=50)
	private String typeParis;

		@Column(length=50)
	private Float montantParie;

		@Column(length=50)
	private String resultatAttendu;

		@Column(length=50)
	private Float cote;

	@ManyToOne(fetch=FetchType.LAZY,optional=true)
	private Parieur parieur;

public void placerParis() {
	// Method implementation
}

public Float calculerGain() {
return (cote -montantParie);}

public String consulterResultat() {
return resultatAttendu;}

}