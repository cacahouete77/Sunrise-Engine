package sunrise.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sunrise.environment.graphics.GraphicsManager;
import sunrise.game.SApplication;
import sunrise.game.SGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class STest extends SApplication {
    private static boolean setDimensions = true;

    private static int width = 400, height = 400;

    private static SGame game;

    private static Canvas canvas;
    private static GraphicsContext gc;

    public static void main(String[] args) {
        new Thread(() -> launch(args)).start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        VBox root = new VBox();
        Scene scene = new Scene(root, width, height);

        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        Affine affine = gc.getTransform();
        gc.setTransform(affine);

        primaryStage.setTitle("Hopeless Bastards");
        primaryStage.setScene(scene);
        primaryStage.show();

        game = new SGame(this, width, height);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = newVal.intValue();
            canvas.setWidth(width);
            setDimensions = true;
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = newVal.intValue();
            canvas.setHeight(height);
            setDimensions = true;
        });

        new Thread(game).start();
    }

    public void draw(BufferedImage bufferedImage) {
        if(setDimensions) {
            game.setDimensions(width, height);
            setDimensions = false;
        }

        Image i = SwingFXUtils.toFXImage(bufferedImage, null);

        int widthDifference = (int) (width - i.getWidth());
        int heightDifference = (int) (height - i.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        gc.drawImage(i, widthDifference / 2.0, heightDifference / 2.0, i.getWidth(), i.getHeight());
    }
}
