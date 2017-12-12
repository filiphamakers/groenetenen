package be.vdab.valueobjects;

import javax.validation.constraints.NotNull;

import be.vdab.constraints.Postcode;
import be.vdab.constraints.PostcodeReeksVanKleinerDanOfGelijkAanTot;

@PostcodeReeksVanKleinerDanOfGelijkAanTot
public class PostcodeReeks {
	@NotNull
	@Postcode
	private Integer vanpostcode;
	@NotNull
	@Postcode
	private Integer totpostcode;

	public boolean bevat(int postcode) {
		// bevat de reeks een bepaalde postcode ? (gebruikt in de repository layer)
		return postcode >= vanpostcode && postcode <= totpostcode;
	}

	public Integer getVanpostcode() {
		return vanpostcode;
	}

	public Integer getTotpostcode() {
		return totpostcode;
	}

}
