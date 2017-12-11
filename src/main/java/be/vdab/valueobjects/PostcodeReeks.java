package be.vdab.valueobjects;

public class PostcodeReeks {
	private Integer vanpostcode;
	private Integer totpostcode;

	public boolean bevat(int postcode) {
		// bevat de reeks een bepaalde postcode ? (gebruikt in de repository layer)
		return postcode >= vanpostcode && postcode <= totpostcode;
	}

	public Integer getVanpostcode() {
		return vanpostcode;
	}

	public void setVanpostcode(Integer vanpostcode) {
		this.vanpostcode = vanpostcode;
	}

	public Integer getTotpostcode() {
		return totpostcode;
	}

	public void setTotpostcode(Integer totpostcode) {
		this.totpostcode = totpostcode;
	}

}
