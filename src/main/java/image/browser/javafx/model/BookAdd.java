package image.browser.javafx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data displayed on the person search screen.
 *
 * @author Tomek
 */
public class BookAdd {

	private final StringProperty author = new SimpleStringProperty();
	private final StringProperty title = new SimpleStringProperty();
	private final ObjectProperty<State> state = new SimpleObjectProperty<>();

	
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

	@Override
	public String toString() {
		return "BookSearch [author=" + author + ", title=" + title + ", state=" + state + "]";
	}


}
