package recipewebapp.model;

import java.time.LocalDate;


/**
 * This is a model class represents a Recipe entity
 *
 */
public class Recipe {

	private int id;
	private String title;
	private String owner;
	private String filename;
	private String description;
	private LocalDate publicationDate;
	
	public Recipe(int id, String title, String owner, String filename, String description, LocalDate publicationDate) {
		this.id = id;
		this.title = title;
		this.owner = owner;
		this.filename = filename;
		this.description = description;
		this.publicationDate = publicationDate;
	}

	public Recipe(String title, String owner, String filename, String description, LocalDate publicationDate) {
		this.title = title;
		this.owner = owner;
		this.filename = filename;
		this.description = description;
		this.publicationDate = publicationDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (id != other.id)
			return false;
		return true;
	}
}