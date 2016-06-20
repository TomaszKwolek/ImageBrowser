package image.browser.javafx.model;

import java.util.ArrayList;
import java.util.List;

import image.browser.javafx.dataprovider.data.BookVO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Data displayed on the person search screen.
 *
 * @author Tomek
 */
public class BookSearch {

	private final LongProperty id = new SimpleLongProperty();
	private final StringProperty author = new SimpleStringProperty();
	private final StringProperty title = new SimpleStringProperty();
	private final ObjectProperty<State> state = new SimpleObjectProperty<>();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final Long getId() {
		return id.get();
	}

	public final void setId(Long value) {
		id.set(value);
	}
	
	public final String getAuthor() {
		return author.get();
	}

	public final void setAuthor(String value) {
		author.set(value);
	}
	
	public final String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}
	
	public LongProperty idProperty() {
		return id;
	}
	
	public StringProperty authorProperty() {
		return author;
	}

	public StringProperty titleProperty() {
		return title;
	}

	public final State getState() {
		return state.get();
	}

	public final void setState(State value) {
		state.set(value);
	}

	public ObjectProperty<State> stateProperty() {
		return state;
	}

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "BookSearch [id=" + id + ", author=" + author + ", title=" + title + ", state=" + state + ", result="
				+ result + "]";
	}


}
