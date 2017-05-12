package pl.zpi.museumguide.data.domain;

import java.util.*;

public class Author {

	private String firstname;
	private String lastname;
	private String alias;
	private Collection<Work> works;
	private int id_drawable;

	public Author() {
	}

	public Author(String firstname, String lastname, String alias, Collection<Work> works) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.alias = alias;
		this.works = works;
	}

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

	public Collection<Work> getWorks() {
		return works;
	}

	public void setWorks(Collection<Work> works) {
		this.works = works;
	}

	public int getIdDrawable() { return id_drawable; }

	public void setIdDrawable(int id_drawable) { this.id_drawable = id_drawable; }

	public void addWork(Work work) {
		if (works == null)
			works = new ArrayList<>();

		works.add(work);
	}

	public String getDisplayName(){
		return firstname + lastname + '(' + alias + ')';
	}
}