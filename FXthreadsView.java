import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class FXthreadsView {
    private FXthreadsModel model;
    private Stage stage;

	protected Label lblNumber;
	protected Label lblNumberTrue;
	protected Label lblNumberFalse;
	protected Button btnClick;

	ScatterChart<Number,Number> scatterChart;
	protected NumberAxis xAxis, yAxis;

	XYChart.Series series1;
	XYChart.Series series2;

	protected double x, y;
	
	protected Label lblx;
	protected Label lbly;

	protected FXthreadsView(Stage stage, FXthreadsModel model) {
		this.stage = stage;
		this.model = model;
		
		stage.setTitle("Monte Carlo Grafik");
		
		BorderPane root = new BorderPane();
		
		VBox vbox = new VBox();
		root.setRight(vbox);
		
		lblx = new Label();
		lblx.setText("X = " + Double.toString(model.getValue().get()));
		lblx.setId("x");
		
		lbly = new Label();
		lbly.setText("Y = " + Double.toString(model.getValue().get()));
		lbly.setId("y");
		
		lblNumber = new Label();
		lblNumber.setText("Anz. Punkte generiert = " + Integer.toString(model.getValue().get()));
		lblNumber.setId("lblNumber");
		
		lblNumberTrue = new Label();
		lblNumberTrue.setText("Anz. Punkte innerhalb = " + Integer.toString(model.getValue().get()));
		lblNumberTrue.setId("lblNumberTrue");
		
		lblNumberFalse = new Label();
		lblNumberFalse.setText("Anz. Punkte ausserhalb = " + Integer.toString(model.getValue().get()));
		lblNumberFalse.setId("lblNumberFalse");
		
		btnClick = new Button();
		btnClick.setText("Berechnung starten");
		btnClick.setId("btnClick");
		
		vbox.setSpacing(10);
		vbox.getChildren().addAll(btnClick, lblx, lbly, lblNumber, lblNumberTrue, lblNumberFalse);
		vbox.setPadding(new Insets(100, 50, 10, 50));
		vbox.setMinWidth(400);
		
		xAxis = new NumberAxis(0, 1, 0.01);
		yAxis = new NumberAxis(0, 1, 0.01);
		scatterChart = new ScatterChart<Number,Number>(xAxis,yAxis);
		xAxis.setLabel("X");
		yAxis.setLabel("Y");
		scatterChart.setTitle("Kreisviertel");
		root.setCenter(scatterChart);
		
		series1 = new XYChart.Series();
		series1.setName("innerhalb");

		series2 = new XYChart.Series();
		series2.setName("ausserhalb");
		
		scatterChart.getData().addAll(series1,series2);

		Scene scene = new Scene(root, 1080, 725);
		scene.getStylesheets().add("Style.css");
		stage.setScene(scene);;
	}

	public ScatterChart<Number, Number> getChart() {
		return this.scatterChart;
	}
	public XYChart.Series getSeries1() {
		return series1;
	}

	public XYChart.Series getSeries2() {
		return series2;
	}

	public void start() {
		stage.show();
	}
	
	/**
	 * Stopping the view - just make it invisible
	 */
	public void stop() {
		stage.hide();
	}
	
	/**
	 * Getter for the stage, so that the controller can access window events
	 */
	public Stage getStage() {
		return stage;
	}
}