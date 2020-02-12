package sunrise.test;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sunrise.game.SApplication;
import sunrise.game.SGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class STest extends SApplication {
    private static boolean setDimensions = true;

    private static double width = 400, height = 400;

    private static SGame game;

    private static Canvas canvas;
    private static GraphicsContext gc;

    public static void main(String[] args) {
        new Thread(() -> launch(args)).start();
    }

    @Override
    public void start(Stage primaryStage) {
        //Please ignore how messy the code in this function is xd
        VBox root = new VBox();
        Scene scene = new Scene(root, width, height);

        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        Affine affine = gc.getTransform();
        gc.setTransform(affine);

        primaryStage.setTitle("In progress");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);

        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());

        game = new SGame(this,
                new TestEnvironment(width, height, "gameplay"),
                width,
                height);

        primaryStage.setOnCloseRequest(t -> {
            game.end();
            Platform.exit();
            System.exit(0);
        });

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            setDimensions = true;
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            setDimensions = true;
        });

        new Thread(game).start();
    }

    public void draw(BufferedImage bufferedImage) {
        if(setDimensions) {
            width = canvas.getWidth();
            height = canvas.getHeight();

            game.setDimensions(width, height);
            gc = canvas.getGraphicsContext2D();
            setDimensions = false;
        }

        Image img = SwingFXUtils.toFXImage(bufferedImage, null);

        int widthDifference = (int) (canvas.getWidth() - img.getWidth());
        int heightDifference = (int) (canvas.getHeight() - img.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        gc.drawImage(img, widthDifference / 2.0, heightDifference / 2.0, img.getWidth(), img.getHeight());
    }
}
