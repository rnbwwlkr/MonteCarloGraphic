import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

public class FXthreadsModel {
    private SimpleIntegerProperty value;
    private SimpleIntegerProperty valueTrue;
    private SimpleIntegerProperty valueFalse;
    private SimpleDoubleProperty lblx;
    private SimpleDoubleProperty lbly;
    private SimpleStringProperty lblStartStop;

    private volatile boolean stop = true;

    Thread ourTaskThread = null;
    private FXthreadsView view;

    protected FXthreadsModel() {
        value = new SimpleIntegerProperty(0);
        valueTrue = new SimpleIntegerProperty(0);
        valueFalse = new SimpleIntegerProperty(0);
        lblx = new SimpleDoubleProperty(0);
        lbly = new SimpleDoubleProperty(0);
        lblStartStop = new SimpleStringProperty();
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
    
    public SimpleStringProperty getStringStartStop() {
        return lblStartStop;
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
//              System.out.print("x = " + x + " and ");
                final double y = Math.random();
//              System.out.println("y = " + y);
                value.set(value.get()+1);
                lblx.set(x);
                lbly.set(y);
                if(x * x + y * y <1){
                    Platform.runLater(() -> view.series1.getData().add(new XYChart.Data(x, y)));
                    valueTrue.set(valueTrue.get()+1);
//                  System.out.println("YES");
                } else {
                    Platform.runLater(() -> view.series2.getData().add(new XYChart.Data(x, y)));
                    valueFalse.set(valueFalse.get()+1);
//                  System.out.println("NO");
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
