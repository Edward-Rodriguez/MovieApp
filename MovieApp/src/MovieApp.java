import database.DatabaseManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MovieAppView;

public class MovieApp extends Application {

    DatabaseManager db = new DatabaseManager();
    MovieAppView ui = new MovieAppView(db);

    @Override
    public void start(Stage primaryStage) throws Exception{

        try{
            this.db.getConnection();
            ui.startUI(primaryStage, "MovieApp");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
