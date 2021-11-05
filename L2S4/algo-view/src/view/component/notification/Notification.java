package view.component.notification;

import javafx.stage.StageStyle;
import resources.Resources;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
/**
 * Notification pour afficher une information.
 * @author Lucian Petic
 */
public class Notification{

    public Notification(String title, String content, AlertType type, Action action){
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(Resources.get(title));
        alert.setContentText(Resources.get(content));

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK && action!=null)
            action.execute();
    }
}