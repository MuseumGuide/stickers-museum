package pl.zpi.museumguide.data.domain;

import java.util.*;

public class Work {

	private String title;
	private List<Author> authors;
	private WorkType type;
	private List<Context> information;
	private List<Material> materials;
	private Beacon beacon;
	private int id_drawable;

	public Work() {
	}

	public Work(String title, List<Author> authors, WorkType type, List<Context> information, List<Material> materials, Beacon beacon) {
		this.title = title;
		this.authors = authors;
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

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
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

	//todo think about initialising in constructor
	public void addAuthor(Author author) {
		if (authors == null)
			authors = new ArrayList<>();

		authors.add(author);
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