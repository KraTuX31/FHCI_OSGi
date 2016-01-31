package m2dl.osgi.editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import m2dl.osgi.decorationbundle.DecoratorService;


public class CodeViewerController {
	private DecoratorService deco;
	
	/**
	 * The main window of the application.
	 */
	private Stage primaryStage;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/**
	 * Radio button: indicate if the html bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuJava;

	/**
	 * Radio button: indicate if the decorator bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuDecorator;

	/**
	 * The viewer to display the content of the opened file.
	 */
	@FXML
	private WebView webViewer;

	/**
	 * The radio button: indicate if the css bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuCSS;

	/**
	 * The button "À propos" have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuAPropos(ActionEvent event) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		final VBox dialogVbox = new VBox(50);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.getChildren().add(new Text("This is a modulable code viewer"));
		final Scene dialogScene = new Scene(dialogVbox, 300, 80);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	@FXML
	void fireMenuCloseFile(ActionEvent event) {
		WebEngine webEngine = webViewer.getEngine();
		webEngine.loadContent("");
	}

	@FXML
	void fireMenuExit(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * The button to load a bundle have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuLoadBundle(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

		if (selectedFile != null) {
			deco.loadBundle(selectedFile);
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}

	/**
	 * The button to open a file have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuOpenFile(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		try {
			String content = deco.decorate(fileChooser.showOpenDialog(primaryStage));
			WebEngine webEngine = webViewer.getEngine();
			webEngine.loadContent(content);
		
			webEngine.reload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	@FXML
	void fireRadioMenuCSS(ActionEvent event) {
		/*
		 * If the css bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
		deco.toggleCss();
	}

	@FXML
	void fireRadioMenuDecorator(ActionEvent event) {
		/*
		 * If the decorator bundle is stated -> stop it otherwise start it (if
		 * it has been loaded before)
		 */
		toggleStatusBundle("decorator");
	}

	private void toggleStatusBundle(String bundle) {
		for(Bundle b : Activator.context.getBundles()) {
			String s = b.getSymbolicName();
			if(s != null && s.toLowerCase().contains("decorator")) {
				try {
					if(b.getState() == Bundle.ACTIVE) {
						b.start();

					} else {
						b.stop();
					}
					return;
				} catch (BundleException e) {
					Activator.logger.error("Error on toggling decorator bundle : "+e.getMessage());
				}					
			}
		}
		Activator.logger.error("There is no decorator bundle installed");
	}
	
	@FXML
	void fireRadioMenuJava(ActionEvent event) {
		/*
		 * If the java bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
		deco.toggleJava();
	}

	@FXML
	void initialize() {
		assert radioMenuJava != null : "fx:id=\"radioMenuJava\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert radioMenuDecorator != null : "fx:id=\"radioMenuDecorator\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert webViewer != null : "fx:id=\"webViewer\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert radioMenuCSS != null : "fx:id=\"radioMenuCSS\" was not injected: check your FXML file 'main-window-exercice.fxml'.";

	}

	public void setPrimaryStage(final Stage _stage) {
		primaryStage = _stage;
	}
	

	private void setDecoService() {
		ServiceReference<?>[] references;
		try {
			references = Activator.context.getServiceReferences( DecoratorService.class.getName(), "(type=good_property)");
			deco = (( DecoratorService) Activator.context.getService(references[0]));
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			// Today, it's dirty time. Exceptions, see you later.
			e.printStackTrace();
		}
	}
	
	public CodeViewerController() {
		setDecoService();
	}
}
