import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class FXthreadsModel {
    private SimpleIntegerProperty value;
    private SimpleIntegerProperty lblx;
    private volatile boolean stop = true;
    
    protected FXthreadsModel() {
        value = new SimpleIntegerProperty(0);
        lblx = new SimpleIntegerProperty(3);
    }

    public SimpleIntegerProperty getValue() {
        return value;
    }
    
    public SimpleIntegerProperty getValuelblx() {
    	return lblx;
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
                lblx.set(lblx.get()+1);
                if(x * x + y * y <1){
//                	series1.getData().add(new XYChart.Data(x, y));
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
