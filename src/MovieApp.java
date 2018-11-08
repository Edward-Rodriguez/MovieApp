import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MovieAppView;

public class MovieApp extends Application {

    MovieAppView ui = new MovieAppView();

    @Override
    public void start(Stage primaryStage) throws Exception{

        ui.startUI(primaryStage, "MovieApp");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
