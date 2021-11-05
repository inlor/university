package view.component.chart;

import java.util.HashMap;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import model.Model;
import model.StatMaker;
import model.sort.Bloc;
import resources.Resources;
/**
 * Statistiques sur les differents tris.
 * @author Lucian Petic
 */
public class ChartSort extends StackPane{
    /** Modèle principale*/
    private Model model;

    public ChartSort(Stage stage, Model model){
        this.model = model;
        createStatsOfSorts();

        Scene secondScene = new Scene(this, 1000, 600);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setScene(secondScene);
        newWindow.toFront();

        newWindow.setX(stage.getX() + 200);
        newWindow.setY(stage.getY() + 25);

        newWindow.show();
        
    }
    /** 
     * Création des stats.
    */
    @SuppressWarnings("unchecked")
    private void createStatsOfSorts(){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        
        bc.setTitle(Resources.get("stats"));
        xAxis.setLabel(Resources.get("sorts"));       
        yAxis.setLabel(Resources.get("value"));
 
        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName(Resources.get("comp")); 
        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName(Resources.get("memory"));
        XYChart.Series series3 = new XYChart.Series<>();
        series3.setName(Resources.get("time")+ " " + Resources.getSimple("mc"));


        HashMap<String, Bloc> stats = StatMaker.createHashOfSortsXTimes(10,model.bloc.tab.length);
        stats.entrySet().forEach(entry -> {
            series1.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue().comp));
            series2.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue().memory));
            series3.getData().add(new XYChart.Data<>(entry.getKey(), model.tri.timeInMc((int)entry.getValue().time)));
        });
        
        bc.getData().addAll(series1, series2, series3);
        this.getChildren().add(bc);
    }
}