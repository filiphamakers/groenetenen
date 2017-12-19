package be.vdab.restservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

import be.vdab.entities.Filiaal;

public class FilialenResource extends ResourceSupport {
	@XmlElement(name = "filiaal")
	@JsonProperty("filialen")
	private final List<FiliaalIdNaam> filialenIdNaam = new ArrayList<>();

	FilialenResource(Iterable<Filiaal> filialen, EntityLinks entityLinks) {
		for (Filiaal filiaal : filialen) {
			this.filialenIdNaam.add(new FiliaalIdNaam(filiaal));
			this.add(entityLinks.linkToSingleResource(Filiaal.class, filiaal.getId()).withRel("detail"));
		}
		this.add(entityLinks.linkToCollectionResource(Filiaal.class));
	}
}
