package pl.zpi.museumguide.data.domain;

public class Material {

	private MaterialType type;
	private String name;

	public Material() {
	}

	public Material(MaterialType type, String name) {
		this.type = type;
		this.name = name;
	}


	public MaterialType getType() {
		return type;
	}

	public void setType(MaterialType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}