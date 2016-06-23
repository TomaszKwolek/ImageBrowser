package image.browser.javafx.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import image.browser.javafx.file.loader.FileLoader;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

public class BrowserController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Button loadFolderButton;
	@FXML
	private Button previousButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button playButton;
	@FXML
	private Button pauseButton;
	@FXML
	private ListView<String> fileList;
	@FXML
	private ImageView imageWindow;
	@FXML
	private ScrollPane imageScrollPane;

	final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

	private List<File> imageFiles = new ArrayList<>();
	private int currentImageIndex = 0;

	private Timer timerSlideShow = new Timer();
	private TimerTask taskSlideShow = null;
	
	private Image selectedImage = null;

	private ImageView nextButtonIcon = new ImageView(
			new Image(getClass().getResourceAsStream("/image/browser/javafx/icons/next.png")));
	private ImageView previousButtonIcon = new ImageView(
			new Image(getClass().getResourceAsStream("/image/browser/javafx/icons/previous.png")));
	private ImageView playButtonIcon = new ImageView(
			new Image(getClass().getResourceAsStream("/image/browser/javafx/icons/play.png")));
	private ImageView pauseButtonIcon = new ImageView(
			new Image(getClass().getResourceAsStream("/image/browser/javafx/icons/stop.png")));

	public BrowserController() {
	}

	@FXML
	private void initialize() {

		setButtonsVisibility(currentImageIndex);

		// REV: moznaby to zrobic w FXMLu
		nextButtonIcon.setFitHeight(55);
		nextButtonIcon.setFitWidth(55);
		nextButton.setGraphic(nextButtonIcon);
		previousButtonIcon.setFitHeight(55);
		previousButtonIcon.setFitWidth(55);
		previousButton.setGraphic(previousButtonIcon);
		playButtonIcon.setFitHeight(55);
		playButtonIcon.setFitWidth(55);
		playButton.setGraphic(playButtonIcon);
		pauseButtonIcon.setFitHeight(55);
		pauseButtonIcon.setFitWidth(55);
		pauseButton.setGraphic(pauseButtonIcon);

		// REV: dlaczego InvalidationListener, a nie ChangeListener?
		zoomProperty.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				imageWindow.setFitWidth(zoomProperty.get() * 4);
				imageWindow.setFitHeight(zoomProperty.get() * 3);
			}
		});

		// REV: Dlaczego EventFilter, a nie EventHandler?
		imageWindow.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() > 0) {
					zoomProperty.set(zoomProperty.get() * 1.1);
				} else if (event.getDeltaY() < 0) {
					zoomProperty.set(zoomProperty.get() / 1.1);
				}
			}
		});

		imageScrollPane.setContent(imageWindow);

	}

	@FXML
	private void loadFolderAction() {
		imageFiles = FileLoader.getFiles(loadFolderButton.getScene().getWindow());
		showFiles(imageFiles);
		currentImageIndex = 0;
		setButtonsVisibility(currentImageIndex);
		showImage(currentImageIndex);
		fileList.scrollTo(currentImageIndex);
	}

	@FXML
	private void previousButtonAction() {
		if (currentImageIndex > 0) {
			currentImageIndex--;
			setButtonsVisibility(currentImageIndex);
			showImage(currentImageIndex);
			fileList.scrollTo(currentImageIndex);
		}
	}

	@FXML
	private void nextButtonAction() {
		if (currentImageIndex < imageFiles.size() - 1) {
			currentImageIndex++;
			setButtonsVisibility(currentImageIndex);
			showImage(currentImageIndex);
			fileList.scrollTo(currentImageIndex);
		}
	}

	@FXML
	private void playButtonAction() {
		startSlideShow();
		// REV: bindy
		playButton.setVisible(false);
		pauseButton.setVisible(true);
	}

	@FXML
	private void pauseButtonAction() {
		setButtonsVisibility(currentImageIndex);
		timerSlideShow.cancel();
		if (taskSlideShow != null) {
			taskSlideShow.cancel();
		}
		// REV: j.w.
		playButton.setVisible(true);
		pauseButton.setVisible(false);
		fileList.scrollTo(currentImageIndex);
	}

	@FXML
	private void showPictureAction() {
		// REV: lepiej zrobic to na listenerze dla selectedItem
		currentImageIndex = fileList.getSelectionModel().getSelectedIndex();
		setButtonsVisibility(currentImageIndex);
		showImage(currentImageIndex);
	}

	private void showImage(int imageNumber) {
		if (!imageFiles.isEmpty() && imageFiles != null) {
			String absolutePath = "file:" + imageFiles.get(imageNumber).getAbsolutePath();
			selectedImage = new Image(absolutePath);
			imageWindow.setImage(selectedImage);
			imageWindow.setFitHeight(imageScrollPane.getHeight() - 2);
			imageWindow.setFitWidth(imageScrollPane.getWidth() - 2);
			imageWindow.setPreserveRatio(true);
			fileList.getSelectionModel().select(imageNumber);
		}
	}

	private void setButtonsVisibility(int index) {
		// REV: lepiej zrobic to bindami
		nextButton.setVisible(true);
		previousButton.setVisible(true);
		playButton.setVisible(true);
		if (index == imageFiles.size() - 1) {
			nextButton.setVisible(false);
			previousButton.setVisible(true);
		}
		if (index == 0) {
			previousButton.setVisible(false);
			nextButton.setVisible(true);
		}
		if (imageFiles.isEmpty()) {
			nextButton.setVisible(false);
			previousButton.setVisible(false);
			playButton.setVisible(false);
			pauseButton.setVisible(false);
		}
	}

	private void startSlideShow() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				currentImageIndex++;
				if (currentImageIndex >= imageFiles.size()) {
					currentImageIndex = 0;
				}
				// REV: zmiany UI powinny byc robione w watku JavaFX
				setButtonsVisibility(currentImageIndex);
				showImage(currentImageIndex);
			};
		};
		timer.schedule(task, 1000, 2000);
		timerSlideShow = timer;
		taskSlideShow = task;
	}

	private void showFiles(List<File> files) {

		loadFolderButton.getParent().setCursor(Cursor.WAIT);

		ObservableList<String> items = FXCollections.observableArrayList();
		for (File file : files) {
			items.add(file.getName());
		}

		fileList.setItems(items);
		// REV: fileList moglby wyswietlac obiekty typu File
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
					imageView.setFitWidth(30);
					imageView.setFitHeight(30);
					setText(name);
					setGraphic(imageView);
				}
			}
		});

		loadFolderButton.getParent().setCursor(Cursor.DEFAULT);
	}

}
