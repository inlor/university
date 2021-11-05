import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

import configuration.Configuration;
import resources.Resources;
import view.View;

/**
 * Classe principale de l'application.
 *
 * @author Lucian Petic
 * @see Configuration
 * @see Resources
 * @see View
 */
public class Main extends Application {
    /**
     * Parametrage général de la fenêtre, taille, titre...
     * Définition du fichier de style et de l'icône.
     * @param fenêtre principale
     */
    public void start(Stage stage) {
        try {
            Group root = new Group();
            Scene scene = new Scene(root, Color.GREY);
            
            // Creation de la vue principale
            new View(stage, root);

            // Parametrage de la fenêtre
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setResizable(false);
            scene.getStylesheets().add(getClass().getResource(Configuration.PATH_STYLE).toExternalForm());
            stage.setTitle(Resources.get(Configuration.NAME_APP));
            stage.getIcons().add(new Image(Configuration.getIconPath("icon")));
            stage.toBack();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}