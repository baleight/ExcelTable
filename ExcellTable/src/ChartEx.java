import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;


public class ChartEx {
    JFrame frame;
    FunAdditional funAdditional = new FunAdditional();
    /**
     * Funzione che crea il grafico a barre passando le due liste
     */
    public void BarChartEx(ArrayList<String> X,ArrayList<String> Y) {
        if(X.size()==0||Y.size()==0)
            return;
        //createDatasetBar
        var datasetTemp = new DefaultCategoryDataset();
        //controllo il più grande delle due liste
        int dim = Math.max(X.size(), Y.size());
        String xx ;
        int yy ;
        for (int i = 1; i < dim; i++) {
            xx = "";
            yy = 0;

            if(!(X.get(i).isEmpty())){
                if((!Objects.equals(X.get(i), "")&&!funAdditional.isNumber(X.get(i).charAt(0))))
                    xx = X.get(i);
            }

            if(!(Y.get(i).isEmpty())){
                if((!Objects.equals(Y.get(i), "")&&funAdditional.isNumber(Y.get(i).charAt(0))))
                    yy = Integer.parseInt(Y.get(i));
            }


            datasetTemp.setValue(yy, "Titolo", xx);
        }
        String titleY = "X", titleX = "Y";
        if(!(X.get(0).isEmpty()))
            titleX = X.get(0);
        if(!(Y.get(0).isEmpty()))
            titleY = Y.get(0);

        JFreeChart chart = ChartFactory.createBarChart(
                "Grafico a Barre",
                titleX,
                titleY,
                datasetTemp,
                PlotOrientation.VERTICAL,
                false, true, false);
        initUI(chart);
        frame.setTitle("Bar chart");
    }

    /**
     * Funzione che crea il grafico a linee passando le due liste
     */

    public void LineaChartEx(ArrayList<String> X,ArrayList<String> Y) {
        if(X.size()==0||Y.size()==0)
            return;

        XYSeries series;
        series = new XYSeries("Linea");//chiave del puntatore in basso
        int dim = Math.max(X.size(), Y.size());
        int xx ;
        int yy ;
        for (int i = 1; i < dim; i++) {
            xx = 0;
            yy = 0;
            //se il valore esiste ed un numero
            if(!(X.get(i).isEmpty())){
                if((!Objects.equals(X.get(i), "")&&funAdditional.isNumber(X.get(i).charAt(0))))
                    xx = Integer.parseInt(X.get(i));
            }

            if(!(Y.get(i).isEmpty())){
                if((!Objects.equals(Y.get(i), "")&&funAdditional.isNumber(Y.get(i).charAt(0))))
                    yy = Integer.parseInt(Y.get(i));
            }


            series.add(xx,yy);
        }
        var datasetTemp = new XYSeriesCollection();
        datasetTemp.addSeries(series);

        String titleY = "X", titleX = "Y";
        if(!(X.get(0).isEmpty()))
            titleX = X.get(0);
        if(!(Y.get(0).isEmpty()))
            titleY = Y.get(0);

        JFreeChart chartTemp = ChartFactory.createXYLineChart(
                "Grafico Lineare",
                titleX,//sotto
                titleY,//sinistra
                datasetTemp,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        XYPlot plot = chartTemp.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chartTemp.getLegend().setFrame(BlockBorder.NONE);

        chartTemp.setTitle(new TextTitle("Grafico a Linea",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );


        initUI(chartTemp);
        frame.setTitle("Line chart");
    }
    /**
     * Creazione e modifica di una nuova finestra per i grafici
     */
    private void initUI(JFreeChart chart) {
        frame = new JFrame();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        frame.add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Creo il frame per il Grafico");
                //System.exit(0);
                //frame.setVisible(false);
                frame.setVisible(false);
            }
        });
    }
    public ChartEx() {

    }


    public void errorChartEX() {
        System.out.println("Errore Grafico!");
    }
}
