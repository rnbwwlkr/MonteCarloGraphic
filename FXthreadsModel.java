import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.chart.XYChart;

public class FXthreadsModel {
    private SimpleIntegerProperty value;
    private SimpleIntegerProperty valueTrue;
    private SimpleIntegerProperty valueFalse;
    private SimpleDoubleProperty lblPi;
    private SimpleDoubleProperty lblx;
    private SimpleDoubleProperty lbly;
    private volatile BooleanProperty stop;

    Thread ourTaskThread = null;
    private FXthreadsView view;

    protected FXthreadsModel() {
        value = new SimpleIntegerProperty(0);
        valueTrue = new SimpleIntegerProperty(0);
        valueFalse = new SimpleIntegerProperty(0);
        lblPi = new SimpleDoubleProperty(0);
        lblx = new SimpleDoubleProperty(0);
        lbly = new SimpleDoubleProperty(0);
        stop = new SimpleBooleanProperty(true);
        
        // Code-Zeilen 27-33: Hilfe von Raphael Lückl
        stop.addListener(change -> {
            if (stop.get()) {
                view.btnClick.textProperty().set("Berechnung weiterf\u00fchren");
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
    
    public SimpleDoubleProperty getValuelblPi() {
        return lblPi;
    }
    
    public SimpleDoubleProperty getValuelblx() {
        return lblx;
    }
    
    public SimpleDoubleProperty getValuelbly() {
        return lbly;
    }
    
    public final int getValueValue() {
        return value.get();
    }
    /* zusätzlicher getter welcher mir ein double mit dem Wert von valueTrue zurückgibt
     * ich benötige double für die Berechnung von PI (auch wenn Zahl INT ist) siehe Code-Zeile 130*/
    public final double getValueValueTrue() {
        return valueTrue.get();
    }
    
    public void startStop() {
        if (ourTaskThread == null) {
            OurTask task = new OurTask();
            ourTaskThread = new Thread(task, "Generierung von Zufallskoordinaten");
        }
        if (stop.get()) {
            stop.set(false);
            ourTaskThread.start();
        } else {
            ourTaskThread.interrupt();
            ourTaskThread = null;
            stop.set(true);
        }
    }

    public BooleanProperty booleanProperty() {
        return this.stop;
    }

    private class OurTask implements Runnable {
        @Override
        public void run() {
            while (!stop.get()) {
                
                // Generierung von Zufallskoordinaten X und Y (Double zwischen 0 und 1)
                final double x = Math.random();
                final double y = Math.random();
                

                // Counter value zählt die Anzahl generierte Punkte
                value.set(value.get()+1);
                
                // Setze die zufällig erstellten X und Y Koordinaten ins Label für GUI
                lblx.set(x);
                lbly.set(y);
                
                // Überprüfung ob Zufallskoordinate innerhalb oder ausserhalb des Kreisviertels
                if(x * x + y * y <1){
                    
                    // Code-Zeile 114 + 121: Hint mit Platform.runLater() innerhalb des IFs von Raphael Lückl
                    // XYChart.Data verwendet wie beschrieben bei http://docs.oracle.com/javafx/2/charts/scatter-chart.htm
                    Platform.runLater(() -> view.series1.getData().add(new XYChart.Data(x, y)));
                    
                    // Counter valueTrue zählt die Anzahl Punkte innerhalb des Kreisviertels
                    valueTrue.set(valueTrue.get()+1);
                } else {
                    
                    // XYChart.Data verwendet wie beschrieben bei http://docs.oracle.com/javafx/2/charts/scatter-chart.htm
                    Platform.runLater(() -> view.series2.getData().add(new XYChart.Data(x, y)));
                    
                    // Counter valueFalse zählt die Anzahl Punkte ausserhalb des Kreisviertels
                    valueFalse.set(valueFalse.get()+1);
                }
                
                /* Schätzung von pi
                 * valueTrue muss ich casten, weil int/int kein double ausgibt,
                 * aber double/int schon.*/
                final double pi = ((double)valueTrue.get()/value.get())*4;
                
                // Setze Pi ins Label für GUI
                lblPi.set(pi);
                
                try {
                    // Damit Animation schöner, verlangsamt auf 0.01 Sekunden
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }

    }
}
