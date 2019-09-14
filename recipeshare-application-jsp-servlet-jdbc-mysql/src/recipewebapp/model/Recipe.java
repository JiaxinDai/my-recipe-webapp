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
	private int likes;
	
	public Recipe(final int id, final String title, final String owner, final String filename, final String description, final LocalDate publicationDate, final int likes) {
		this.id = id;
		this.title = title;
		this.owner = owner;
		this.filename = filename;
		this.description = description;
		this.publicationDate = publicationDate;
		this.likes = likes;
	}

	public Recipe(final String title, final String owner, final String filename, final String description, final LocalDate publicationDate) {
		this.title = title;
		this.owner = owner;
		this.filename = filename;
		this.description = description;
		this.publicationDate = publicationDate;
		this.likes = 0;
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
	
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(final String owner) {
		this.owner = owner;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(final LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public void setLikes(final int likes) {
		this.likes = likes;
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