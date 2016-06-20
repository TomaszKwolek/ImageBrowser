package image.browser.javafx.dataprovider.impl;

import java.util.Collection;

import image.browser.javafx.dataprovider.DataProvider;
import image.browser.javafx.dataprovider.data.BookVO;
import image.browser.javafx.dataprovider.data.StateVO;

/**
 * Provides data. Data is stored locally in this object. Additionally a call
 * delay is simulated.
 *
 * @author Tomek
 */
public class DataProviderImpl implements DataProvider {


	public DataProviderImpl() {
		
	}

	@Override
	public Collection<BookVO> findBooks(String title, String author, StateVO state) throws Exception{
		return null;
	}

	@Override
	public void addBook(String title, String author, String state) throws Exception {
	}

	@Override
	public void deleteBook(String id) throws Exception {
	}

}
