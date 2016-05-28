import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class FXthreadsModel {
    private SimpleIntegerProperty value;
    private SimpleDoubleProperty lblx;
    private SimpleDoubleProperty lbly;
    private XYChart.Series<Double, Double> series1;
    private XYChart.Series<Double, Double> series2;
    
    private volatile boolean stop = true;
    
    protected FXthreadsModel() {
        value = new SimpleIntegerProperty(0);
        lblx = new SimpleDoubleProperty(0);
        lbly = new SimpleDoubleProperty(0);
        series1 = new XYChart.Series<>();
        series2 = new XYChart.Series<>();
    }

    public SimpleIntegerProperty getValue() {
        return value;
    }
    
    public SimpleDoubleProperty getValuelblx() {
    	return lblx;
    }
    
    public SimpleDoubleProperty getValuelbly() {
    	return lbly;
    }
    
    public XYChart.Series<Double, Double> getValueseries1() {
    	return series1;
    }
    
    public XYChart.Series<Double, Double> getValueseries2() {
    	return series2;
    }
    
    public void startStop() {
        if (stop) {
            stop = false;
            OurTask task = new OurTask();
            new Thread(task, "Generierung von Zufallskoordinaten").start();
        } else {
            stop = true;
        }
    }

    private class OurTask extends Task<Void> {
        @Override
        protected Void call() throws Exception {
        	double x, y;
            while (!stop) {
            	x = Math.random();
            	System.out.print("x = " + x + " and ");
            	y = Math.random();
            	System.out.println("y = " + y);
                value.set(value.get()+1);
                lblx.set(x);
                lbly.set(y);
//                sc.getData().addAll(series1,series2);
                if(x * x + y * y <1){
//                	series2.getData().add(new XYChart.Data(x, y));
                	System.out.println("YES");
                } else {
//                	series2.getData().add(new XYChart.Data(x, y));
                	System.out.println("NO");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

    }
}
