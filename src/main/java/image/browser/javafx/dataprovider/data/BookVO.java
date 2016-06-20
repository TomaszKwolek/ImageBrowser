package image.browser.javafx.dataprovider.data;

/**
 * Book data.
 *
 * @author Tomek
 */
public class BookVO {

	 private Long id;
	 private String title;
	 private String author;
	 private StateVO state;

	public BookVO() {
		super();
	}

	public BookVO(Long id, StateVO state, String author, String title) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.state = state;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public StateVO getState() {
		return state;
	}

	public void setState(StateVO state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "BookVO [id=" + id + ", title=" + title + ", author=" + author + ", state=" + state + "]";
	}


}
