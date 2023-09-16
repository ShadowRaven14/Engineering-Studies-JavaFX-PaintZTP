package paintztp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


//Klasa typu Main
public class PaintZTP extends Application 
{
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    //Ustawienie widoku
    @Override
    public void start(Stage stage) throws Exception 
    {
        //wybranie pliku widoku
        Parent root = FXMLLoader.load(getClass().getResource("PaintView.fxml"));
        
        //ustawienie instancji aplikacji (sceny
        stage.setTitle("Best Paint App");
        stage.setScene(new Scene (root));
        stage.show();
    }
}