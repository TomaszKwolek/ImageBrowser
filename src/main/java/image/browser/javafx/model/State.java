package image.browser.javafx.model;

import image.browser.javafx.dataprovider.data.StateVO;

/**
 * state values used in GUI.
 *
 * @author Tomek
 */
public enum State {

	ANY, FREE, LOAN;

	/**
	 * Converts {@link stateVO} to corresponding {@link State}.
	 *
	 * @param state
	 *            {@link stateVO} value
	 * @return {@link State} value
	 */
	public static State fromStateVO(StateVO state) {
		return State.valueOf(state.name());
	}

	/**
	 * Converts this {@link State} to corresponding {@link stateVO}. For values that
	 * do not have corresponding value {@code null} is returned.
	 *
	 * @return {@link stateVO} value or {@code null}
	 */
	public StateVO toStateVO() {
		if (this == ANY) {
			return null;
		}
		return StateVO.valueOf(this.name());
	}

}
