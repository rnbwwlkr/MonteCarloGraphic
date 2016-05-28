import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class FXthreadsController {
    final private FXthreadsModel model;
    final private FXthreadsView view;

    protected FXthreadsController(FXthreadsModel model, FXthreadsView view) {
        this.model = model;
        this.view = view;

        // Update the view whenever the model value changes.
        model.getValue().addListener((observable, oldValue, newValue) -> {
            updateGUI((Integer) newValue);
        });
        model.getValuelblx().addListener((observable, oldValue, newValue) -> {
            updateGUI((Integer) newValue);
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
            view.lblNumber.setText(Integer.toString((Integer) newValue));
            view.lblx.setText(Integer.toString((Integer) newValue));
        });
    }
}
