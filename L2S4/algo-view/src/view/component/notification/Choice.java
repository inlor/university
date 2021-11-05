package view.component.notification;

import view.component.notification.Action;
import java.util.List;
import java.util.Optional;
import javafx.stage.StageStyle;
import javafx.scene.control.ChoiceDialog;

import resources.Resources;
import util.ArrayHelper;

/**
 * Classe qui sert pour l'affichage de la boite de dialog pour le changement de
 * la langue.
 * 
 * @author Lucian Petic
 * @see Resources
 * @see ArrayHelper
 */
public class Choice {
    /** La boite de dialogue */
    private String response;
    /** Title de l'entête et du context */
    private Optional<String> result;

    /**
     * Initialisation de la boîte de dialogue.
     */
    public Choice(String title, String content, String current, List<String> choices) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(current, choices);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(Resources.get(title));
        String parts[] = ArrayHelper.splitHalf(Resources.get(content));
        dialog.setHeaderText(parts[0] + System.lineSeparator() + parts[1]);
        result = dialog.showAndWait();
    }
    /** Getters */
    public void setAction(Action action){
        if (result.isPresent())
            action.execute();
    }

    public Optional<String> getResult() {
        return result;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response=response;
    }
}