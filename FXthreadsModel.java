import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.chart.XYChart;

public class FXthreadsModel {
    private SimpleIntegerProperty value;
    private SimpleIntegerProperty valueTrue;
    private SimpleIntegerProperty valueFalse;
    private SimpleDoubleProperty lblx;
    private SimpleDoubleProperty lbly;

    private volatile boolean stop = true;

    Thread ourTaskThread = null;
    private FXthreadsView view;

    protected FXthreadsModel() {
        value = new SimpleIntegerProperty(0);
        valueTrue = new SimpleIntegerProperty(0);
        valueFalse = new SimpleIntegerProperty(0);
        lblx = new SimpleDoubleProperty(0);
        lbly = new SimpleDoubleProperty(0);
        stop.addListener(change -> {
            if (stop.get()) {
                view.btnClick.textProperty().set("Berechnung weiterführen");
            } else {
                view.btnClick.textProperty().set("Berechnung pausieren");
            }
        });
    }

    public void setView(FXthreadsView view) {
        this.view = view;
    }

    public SimpleIntegerProperty getValue() {
        return value;
    }
    
    public SimpleIntegerProperty getValueTrue(){
    	return valueTrue;
    }
    
    public SimpleIntegerProperty getValueFalse() {
    	return valueFalse;
    }
    
    public SimpleDoubleProperty getValuelblx() {
    	return lblx;
    }
    
    public SimpleDoubleProperty getValuelbly() {
    	return lbly;
    }
    
    public void startStop() {
        if (ourTaskThread == null) {
            OurTask task = new OurTask();
            ourTaskThread = new Thread(task, "Generierung von Zufallskoordinaten");
        }
        if (stop) {
            stop = false;
            ourTaskThread.start();
        } else {
            ourTaskThread.interrupt();
            ourTaskThread = null;
            stop = true;
        }
    }

    private class OurTask extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            while (!stop) {
            	final double x = Math.random();
            	final double y = Math.random();
                value.set(value.get()+1);
                lblx.set(x);
                lbly.set(y);
                if(x * x + y * y <1){
                    Platform.runLater(() -> view.series1.getData().add(new XYChart.Data(x, y)));
                    valueTrue.set(valueTrue.get()+1);
                } else {
                    Platform.runLater(() -> view.series2.getData().add(new XYChart.Data(x, y)));
                    valueFalse.set(valueFalse.get()+1);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

    }
}
