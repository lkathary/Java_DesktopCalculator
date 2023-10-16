package com.example.calculator;

import com.example.calculator.controllers.ModelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class GraphController {

    @FXML
    private ScatterChart<Double, Double> scatterChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    void initialize() {
        XYChart.Series<Double, Double> dataSeries = new XYChart.Series<>();
        ObservableList<XYChart.Data<Double, Double>> data = FXCollections.observableArrayList();

        ModelController controller = ModelController.getInstance();
        dataSeries.setName(controller.getExpression());
        var graph = controller.calculateGraph();

        xAxis.setLowerBound(controller.getLeftBound());
        xAxis.setUpperBound(controller.getRightBound());
        xAxis.setAutoRanging(false);

        for(var it : graph) {
            data.add(new XYChart.Data<>(it.getKey(), it.getValue()));
        }
        dataSeries.setData(data);
        scatterChart.getData().add(dataSeries);
    }
}
