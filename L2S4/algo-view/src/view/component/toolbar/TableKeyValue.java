package view.component.toolbar;

import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import resources.Resources;
import model.Model;

/**
 * Cette classe est responsable du tableau en lien avec le model.
 *
 * @author Lucian Petic
 * @see Resources
 * @see Model
 */
public class TableKeyValue{
    /** Model de l'application */
    private Model model;
    /** La table principale */
    private TableView<KeyValue> table;
    /** Variable pour un meilleur rendu lors de l'integration dans la toolbar */
    private HBox hBox;

    
    public TableKeyValue(Model model){
        this.model = model;
        table = new TableView<>();
        creationTable();
    }

    /**
     * Creation de la table avec tous les champs nécessaires.
     */
    @SuppressWarnings("unchecked")
    private void creationTable(){
        // Creation des colonnes avec les nom et les clefs correcpondants
        TableColumn<KeyValue, Integer> key = new TableColumn<>(Resources.get("key"));
        key.setCellValueFactory(new PropertyValueFactory<KeyValue, Integer>("key"));
        key.setPrefWidth(30);

        TableColumn<KeyValue, Integer> value = new TableColumn<>(Resources.get("value"));
        value.setCellValueFactory(new PropertyValueFactory<KeyValue, Integer>("value"));
        value.setPrefWidth(60);

        // Parametrage des proprietes de la table.=
        table.setMaxHeight(100);
        table.setMaxWidth(120);
        table.getColumns().addAll(key, value);
        table.setTableMenuButtonVisible(true);

        // Integration de la table des un HBox
        hBox = new HBox();
        hBox.getChildren().add(table);

        updateTable();
    }

    /**
     * Mise à jour de la table, avec les données récupérées depuis le modèle.
     */
    public void updateTable() {
        // Récuperation des données
        ObservableList<KeyValue> data = FXCollections.observableArrayList();
        for (int i = 0; i < model.getCurrent().tab.length;i++) {
            data.add(new KeyValue(i, model.getCurrent().tab[i]));
        }
        // Liaison entre les données et la table
        table.setItems(data);
        updateTableSelected(model.getCurrent().valSelected);
    }

    public void updateTableSelected(int index){
        table.getSelectionModel().select(index);
        table.scrollTo(index);
    }

    /**
     * Getter pour le hbox.
     * @return HBox
     */
    public HBox getHBox(){
        return hBox;
    }

    /**
     * Classe interne qui sert comme objet de liaison pour la table.
     */
    public class KeyValue {
        /** Clé */
        private Integer key;
        /** Valeur */
        private Integer value;

        public KeyValue(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }

        /** Getters */
        public Integer getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }
    }
}