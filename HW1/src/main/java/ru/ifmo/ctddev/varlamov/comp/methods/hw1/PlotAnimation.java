package ru.ifmo.ctddev.varlamov.comp.methods.hw1;

import com.sun.glass.ui.Application;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


public class PlotAnimation {
    public static void animate(final double[][] graphSeries, final double x0, final double dx, final long shiftInterval, String label) {
        final XYSeriesCollection collection = new XYSeriesCollection();
        final JFreeChart chart = ChartFactory.createXYLineChart(
                label, "X", "T", collection,
                PlotOrientation.VERTICAL, false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame applicationFrame = new JFrame(label);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        applicationFrame.setContentPane(chartPanel);
        applicationFrame.pack();
        RefineryUtilities.centerFrameOnScreen(applicationFrame);
        applicationFrame.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int runs = -1;

            @Override
            public void run() {
                if (runs + 1 >= graphSeries.length) {
                    cancel();
                    return;
                }
                runs++;
                System.out.println("Plotting " + (runs + 1) + "/" + graphSeries.length);
                XYSeries series = new XYSeries("Result");
                double x = x0;
                for (int i = 0; i < graphSeries[runs].length; i++) {
                    series.add(x, graphSeries[runs][i]);
                    x += dx;
                }
                collection.removeAllSeries();
                collection.addSeries(series);
            }
        }, 0, shiftInterval);
    }
}
