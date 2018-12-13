package user.service.entity;

public class Address extends BaseEntity {
	
	private String Country;
	
	private String province;
	
	private String city;
	
	private String region;
	
	private String line0;
	
	private String line1;
	
	private String line2;

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLine0() {
		return line0;
	}

	public void setLine0(String line0) {
		this.line0 = line0;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}
	
}
