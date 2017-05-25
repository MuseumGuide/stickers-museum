package pl.zpi.museumguide.data.domain;

import java.util.ArrayList;
import java.util.List;

public class Work {

	private String title;
	private String description;
	private Author author;
	private WorkType type;
	private List<Context> information;
	private List<Material> materials;
	private Beacon beacon;
	private int id_drawable;

	public Work() {
	}

	public Work(String title, Author author, WorkType type, List<Context> information, List<Material> materials, Beacon beacon) {
		this.title = title;
		this.author = author;
		this.type = type;
		this.information = information;
		this.materials = materials;
		this.beacon = beacon;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public WorkType getType() {
		return type;
	}

	public void setType(WorkType type) {
		this.type = type;
	}

	public List<Context> getInformation() {
		return information;
	}

	public void setInformation(List<Context> information) {
		this.information = information;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public int getIdDrawable() {
		return id_drawable;
	}

	public void setIdDrawable(int id_drawable) {this.id_drawable = id_drawable;}

	public Beacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public void addContext(Context context) {
		if (information == null)
			information = new ArrayList<>();

		information.add(context);
	}

	public void addMaterial(Material material) {
		if (materials == null)
			materials = new ArrayList<>();
		materials.add(material);
	}
}