package application;
	
import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;
import interfaces.impl.IncController;
import interfaces.impl.IncModel;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class Main extends Application implements IView
{   
	private IView view = this;
	private IModel modelo= new IncModel();
	private	IController controlador= new IncController(view,modelo);

	private TextField txtValue = new TextField();;
	private Button btnIncrementar;

	@Override
	public void start(Stage primaryStage)
	{

		try
		{
			// Create the OK-Button
			btnIncrementar = new Button("Incrementar");
			
			// add an EventHandler to the OK-Button
			btnIncrementar.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
		        	controlador.incvalue();
				}
			});
			
			txtValue.setPrefWidth(20);
     		txtValue.focusedProperty().addListener(new WeakInvalidationListener(txtValueListener));	

     		// Create the BorderPane
			BorderPane root = new BorderPane();
			root.setPadding(new Insets (25, 25, 10, 25));
			
			 HBox hbox = new HBox(12);//space
			 HBox.setHgrow(txtValue, Priority.ALWAYS);
			 HBox.setHgrow(btnIncrementar, Priority.ALWAYS);
			 
			 hbox.getChildren().addAll(txtValue, btnIncrementar);
			 
			 hbox.setPrefWidth(250);
			    
     		root.setCenter(hbox);
     		
			// Create the Scene
			Scene scene = new Scene(root, 300, 150);
			// Add the StyleSheets to the Scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// Add the scene to the Stage
			primaryStage.setScene(scene);
			// Set the title of the Stage
			primaryStage.setTitle("JAVAFX MVC sample ");
			// Display the Stage
			btnIncrementar.requestFocus();
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	
		launch(args);
	}
	@Override
	public void setController(IController controller) {
        controlador= controller;
	}

	@Override
	public void setValue(String s) {
		txtValue.setText(s);
	}
	InvalidationListener txtValueListener = (evt) -> {
		 if( !txtValue.isFocused() ) {
			 try {
				 controlador.setModelValue(Integer.parseInt(txtValue.getText()));
			 }
			 catch (NumberFormatException e ) {
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error");
			 alert.setHeaderText("No ha ingresado un número entero");
			 alert.setContentText("Se requiere un número entero a partir del cual se reinicia el conteo.");
			 alert.show();}
		 };
		};
}
