package image.browser.javafx.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import image.browser.javafx.model.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class BrowserController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@FXML
	private Button loadFolderButton;
	@FXML
	private ListView<String> fileList;
	@FXML
	private ImageView imageWindow;
	// @FXML
	// private TableColumn<BookVO, Long> idColumn;
	// @FXML
	// private TableColumn<BookVO, String> titleColumn;

	// private final DataProvider dataProvider = DataProvider.INSTANCE;

	// private final BookSearch model = new BookSearch();

	private List<File> imageFile = new ArrayList<>();

	public BrowserController() {
	}

	@FXML
	private void initialize() {

		// initializeFileList();

		// resultTable.itemsProperty().bind(model.resultProperty());

	}

	@SuppressWarnings("unused")
	private String getInternationalizedText(State state) {
		return resources.getString("state." + state.name());
	}

	@FXML
	private void loadFolderAction() {
		imageFile = getFiles();
		showFile(imageFile, getImages(imageFile));

	}

	@FXML
	private void showPictureAction() {
		String temp = fileList.getSelectionModel().getSelectedItem();

		System.out.println(temp);
		String absolutePath = "";
		for (File file : imageFile) {
			if(fileList.getSelectionModel().getSelectedItem().equals(file.getName())){
			absolutePath = "file:" + file.getAbsolutePath();
			}
		}
		Image selectedImage = new Image(absolutePath);
		imageWindow.setImage(selectedImage);
	}

	private List<File> getFiles() {
		Stage stage = new Stage();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open folder");
		File folder = directoryChooser.showDialog(stage);
		File[] files = folder.listFiles();
		List<File> images = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String file = files[i].getName();
				if (file.endsWith(".jpg") || file.endsWith(".JPG") || file.endsWith(".png") || file.endsWith(".png")) {
					images.add(files[i]);
					System.out.println(files[i].getName());
				}
			}
		}
		return images;
	}

	private List<Image> getImages(List<File> files) {
		List<Image> images = new ArrayList<Image>();
		for (File file : files) {
			images.add(new Image("file:" + file.getAbsolutePath()));
		}
		return images;
	}

	private void showFile(List<File> files, List<Image> images) {
		// ListView<String> listView = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList();
		for (File file : files) {
			items.add(file.getName());
		}

		fileList.setItems(items);

		fileList.setCellFactory(param -> new ListCell<String>() {
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(String name, boolean empty) {
				super.updateItem(name, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					for (File file : files) {
						if (file.getName().equals(name))
							imageView.setImage(new Image("file:" + file.getAbsolutePath()));
					}
					// imageView.setImage(images.get(0));
					imageView.setFitWidth(30);
					imageView.setFitHeight(30);
					setText(name);
					setGraphic(imageView);
				}
			}
		});
		// VBox box = new VBox(fileList);
		// box.setMaxSize(200, 20);
		// box.setAlignment(Pos.CENTER);
		// Scene scene = new Scene(box, 50, 50);
		// Stage stage = new Stage();
		// stage.setScene(scene);
		// stage.show();

	}

	// private void deleteBookAction(String id) {
	//
	// Task<Object> backgroundTask = new Task<Object>() {
	//
	// @Override
	// protected Object call() throws Exception {
	// try {
	// dataProvider.deleteBook(id);
	// } catch (Exception e) {
	// Alert alert = new Alert(AlertType.ERROR, "Http GET request Error!",
	// ButtonType.OK);
	// alert.showAndWait();
	// }
	// return new Object();
	// }
	//
	// @Override
	// protected void succeeded() {
	//
	//
	// Alert alert = new Alert(AlertType.INFORMATION, "Book deleted successful
	// :)", ButtonType.OK);
	// alert.showAndWait();
	// }
	// };
	//
	// new Thread(backgroundTask).start();
	// }

}
