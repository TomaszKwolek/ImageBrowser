package image.browser.javafx.file.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class FileLoader {

	public static List<File> getFiles(Window window) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open folder");
		File folder = directoryChooser.showDialog(window);
		File[] files = folder.listFiles();
		List<File> images = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String file = files[i].getName();
				if (checkIfImageFile(file)) {
					images.add(files[i]);
				}
			}
		}
		return images;
	}

	public static boolean checkIfImageFile(String file) {
		return file.endsWith(".jpg") || file.endsWith(".JPG") || file.endsWith(".jpeg") || file.endsWith(".JPEG")
				|| file.endsWith(".tiff") || file.endsWith(".TIFF") || file.endsWith(".tif")
				|| file.endsWith(".TIF") || file.endsWith(".gif") || file.endsWith(".GIF")
				|| file.endsWith(".jpg") || file.endsWith(".JPG") || file.endsWith(".png")
				|| file.endsWith(".png");
	}
	
}
