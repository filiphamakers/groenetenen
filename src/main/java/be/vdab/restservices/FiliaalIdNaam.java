package be.vdab.restservices;

import javax.xml.bind.annotation.XmlAttribute;

import be.vdab.entities.Filiaal;

class FiliaalIdNaam {
	@XmlAttribute
	private long id;
	@XmlAttribute
	private String naam;

	FiliaalIdNaam() {
	} // JAXB heeft een default constructor nodig

	FiliaalIdNaam(Filiaal filiaal) {
		this.id = filiaal.getId();
		this.naam = filiaal.getNaam();
	}

}
