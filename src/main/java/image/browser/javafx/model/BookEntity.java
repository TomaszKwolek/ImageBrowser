package image.browser.javafx.model;

import java.io.Serializable;



public class BookEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9105032630361196732L;
	private Long id;
	private String title;
	private String authors;
	private String status;

	public BookEntity() {
		super();
	}

	public BookEntity(Long id, String title, String authors, String status) {
		super();
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}
