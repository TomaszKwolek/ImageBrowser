package image.browser.javafx.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import image.browser.javafx.model.State;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
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

		nextButtonIcon.setFitHeight(35);
		previousButtonIcon.setFitHeight(35);
		nextButtonIcon.setFitWidth(35);
		previousButtonIcon.setFitWidth(35);
		playButtonIcon.setFitHeight(35);
		pauseButtonIcon.setFitHeight(35);
		playButtonIcon.setFitWidth(35);
		pauseButtonIcon.setFitWidth(35);
		nextButton.setGraphic(nextButtonIcon);
		previousButton.setGraphic(previousButtonIcon);
		playButton.setGraphic(playButtonIcon);
		pauseButton.setGraphic(pauseButtonIcon);

		zoomProperty.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				imageWindow.setFitWidth(zoomProperty.get() * 4);
				imageWindow.setFitHeight(zoomProperty.get() * 3);

			}
		});

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

	@SuppressWarnings("unused")
	private String getInternationalizedText(State state) {
		return resources.getString("state." + state.name());
	}

	@FXML
	private void loadFolderAction() {
		imageFiles = getFiles();
		showFiles(imageFiles);
		currentImageIndex = 0;
		String absolutePath = "file:" + imageFiles.get(currentImageIndex).getAbsolutePath();
		Image selectedImage = new Image(absolutePath);
		imageWindow.setImage(selectedImage);
		setButtonsVisibility(currentImageIndex);
	}

	@FXML
	private void previousButtonAction() {
		String absolutePath = "";
		if (currentImageIndex > 0) {
			absolutePath = "file:" + imageFiles.get(currentImageIndex - 1).getAbsolutePath();
			currentImageIndex = currentImageIndex - 1;
			setButtonsVisibility(currentImageIndex);
		}
		Image selectedImage = new Image(absolutePath);
		imageWindow.setImage(selectedImage);
	}

	@FXML
	private void nextButtonAction() {
		String absolutePath = "";
		if (currentImageIndex < imageFiles.size() - 1) {
			absolutePath = "file:" + imageFiles.get(currentImageIndex + 1).getAbsolutePath();
			currentImageIndex = currentImageIndex + 1;
			setButtonsVisibility(currentImageIndex);
		}
		Image selectedImage = new Image(absolutePath);
		imageWindow.setImage(selectedImage);
	}

	@FXML
	private void playButtonAction() {
		disableNextPreviousButtons();

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				currentImageIndex++;
				if (currentImageIndex >= imageFiles.size()) {
					currentImageIndex = 0;
				}
				String absolutePath = "file:" + imageFiles.get(currentImageIndex).getAbsolutePath();
				Image selectedImage = new Image(absolutePath);
				imageWindow.setImage(selectedImage);
			};
		};
		timer.schedule(task, 1000, 2000);
		timerSlideShow = timer;
		taskSlideShow = task;
		playButton.setVisible(false);
		pauseButton.setVisible(true);
	}

	@FXML
	private void pauseButtonAction() {
		setButtonsVisibility(currentImageIndex);
		System.out.println(currentImageIndex);
		timerSlideShow.cancel();
		taskSlideShow.cancel();
		playButton.setVisible(true);
		pauseButton.setVisible(false);
	}

	@FXML
	private void showPictureAction() {
		String absolutePath = "";
		for (int i = 0; i < imageFiles.size(); i++) {
			if (fileList.getSelectionModel().getSelectedItem().equals(imageFiles.get(i).getName())) {
				absolutePath = "file:" + imageFiles.get(i).getAbsolutePath();
				currentImageIndex = i;
				setButtonsVisibility(currentImageIndex);
			}
		}
		Image selectedImage = new Image(absolutePath);
		imageWindow.setImage(selectedImage);
	}

	private void setButtonsVisibility(int i) {
		nextButton.setVisible(true);
		previousButton.setVisible(true);
		playButton.setVisible(true);
		if (i == imageFiles.size() - 1) {
			nextButton.setVisible(false);
			previousButton.setVisible(true);
		}
		if (i == 0) {
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

	private void disableNextPreviousButtons() {
		previousButton.setVisible(false);
		nextButton.setVisible(false);
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
				if (file.endsWith(".jpg") || file.endsWith(".JPG") || file.endsWith(".jpeg") || file.endsWith(".JPEG")
						|| file.endsWith(".tiff") || file.endsWith(".TIFF") || file.endsWith(".tif")
						|| file.endsWith(".TIF") || file.endsWith(".gif") || file.endsWith(".GIF")
						|| file.endsWith(".jpg") || file.endsWith(".JPG") || file.endsWith(".png")
						|| file.endsWith(".png")) {
					images.add(files[i]);
					System.out.println(files[i].getName());
				}
			}
		}
		return images;
	}

	private void showFiles(List<File> files) {
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
					imageView.setFitWidth(30);
					imageView.setFitHeight(30);
					setText(name);
					setGraphic(imageView);
				}
			}
		});

	}

}
