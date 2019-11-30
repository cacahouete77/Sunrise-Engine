package sunrise;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

public class Main extends Application {
    private Canvas canvas;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = new VBox();
        Scene scene = new Scene(root, 400, 400);

        canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        Affine affine = gc.getTransform();
        gc.setTransform(affine);

        primaryStage.setTitle("Hopeless Bastards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
