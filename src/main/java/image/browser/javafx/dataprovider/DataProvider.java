package image.browser.javafx.dataprovider;


import java.util.Collection;

import image.browser.javafx.dataprovider.data.BookVO;
import image.browser.javafx.dataprovider.data.StateVO;
import image.browser.javafx.dataprovider.impl.DataProviderImpl;


/**
 * Provides data.
 *
 * @author Tomek
 */
public interface DataProvider {


	DataProvider INSTANCE = new DataProviderImpl();

	Collection<BookVO> findBooks(String title, String author, StateVO state) throws Exception;
	void addBook(String title, String author, String state) throws Exception;
	void deleteBook(String id) throws Exception;

}
