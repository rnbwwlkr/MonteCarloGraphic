import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.stage.WindowEvent;

public class FXthreadsController {
    final private FXthreadsModel model;
    final private FXthreadsView view;

    protected FXthreadsController(FXthreadsModel model, FXthreadsView view) {
        this.model = model;
        this.view = view;

        // Aktualisiere die View wenn der model Wert ändert.
        
        model.getValue().addListener((observable, oldValue, newValue) -> {
            updateGUI((Integer) newValue);
        });
        
        model.getValuelblx().addListener((observable, oldValue, newValue) -> {
            updateGUIX((Double) newValue);
        });
        
        model.getValuelbly().addListener((observable, oldValue, newValue) -> {
        	updateGUIY((Double) newValue);
        });

        // register ourselves to listen for button clicks
        view.btnClick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.startStop();
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                view.stop();
                Platform.exit();
            }
        });
    }

    /* Ensure the update happens on the JavaFX Application Thread,
     * by using Platform.runLater() */
    private void updateGUI(int newValue) {
        Platform.runLater(() -> {
            view.lblNumber.setText("Anzahl Versuche = " + Integer.toString((Integer) newValue));
        });
    }
    // Aktualisiert die X-Koordinate im Label des GUI
    private void updateGUIX(Double newValue) {
    	Platform.runLater(() -> {
    		view.lblx.setText("X = " + Double.toString((Double) newValue));
    	});
    }
    // Aktualisiert die Y-Koordinate im Label des GUI
    private void updateGUIY(Double newValue) {
    	Platform.runLater(() -> {
    		view.lbly.setText("Y = " + Double.toString((Double) newValue));
    	});
    }
    
}
