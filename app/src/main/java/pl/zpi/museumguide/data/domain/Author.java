package pl.zpi.museumguide.data.domain;

import java.util.ArrayList;

public class Author {

	private String firstname;
	private String lastname;
	private String alias;
	private String description;
	private ArrayList<Work> works = new ArrayList<>();
	private int id_drawable;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Work> getWorks() {
		return works;
	}

	public void setWorks(ArrayList<Work> works) {
		this.works = works;
	}

	public int getIdDrawable() { return id_drawable; }

	public void setIdDrawable(int id_drawable) { this.id_drawable = id_drawable; }

	public void addWork(Work work) {
		works.add(work);
	}

	public String getDisplayName(){
		return firstname + lastname + '(' + alias + ')';
	}
}