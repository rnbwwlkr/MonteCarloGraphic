import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class FXthreadsView {
    private FXthreadsModel model;
    private Stage stage;

	protected Label lblNumber;
	protected Button btnClick;
	
	protected NumberAxis xAxis, yAxis;
	
	protected double x, y;
	
	protected Label lblx;
	protected Label lbly;

	protected FXthreadsView(Stage stage, FXthreadsModel model) {
		this.stage = stage;
		this.model = model;
		
		stage.setTitle("Monte Carlo Grafik");
		
		GridPane root = new GridPane();
		
		lblx = new Label();
		lblx.setText("X = " + Double.toString(model.getValue().get()));
		root.add(lblx, 0, 0);
		
		lbly = new Label();
		lbly.setText("Y = " + Double.toString(model.getValue().get()));
		root.add(lbly, 0, 1);
		
		lblNumber = new Label();
		lblNumber.setText("Anzahl Versuche = " + Integer.toString(model.getValue().get()));
		root.add(lblNumber, 0, 2);
		
		btnClick = new Button();
		btnClick.setText("Berechnung starten/stoppen");
		root.add(btnClick, 0, 3);
		
		xAxis = new NumberAxis(0, 1, 0.01);
		yAxis = new NumberAxis(0, 1, 0.01);
		ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
		xAxis.setLabel("X");
		yAxis.setLabel("Y");
		sc.setTitle("Kreisviertel");
		root.add(sc, 0, 4);
		
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("innerhalb");
		
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("ausserhalb");
		
		/*series1.getData().add(new XYChart.Data(x,y));
		series2.getData().add(new XYChart.Data(x,y));*/
		
		sc.getData().addAll(series1,series2);

		Scene scene = new Scene(root);
		/*scene.getStylesheets().add(
				getClass().getResource("Example.css").toExternalForm());*/
		stage.setScene(scene);;
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