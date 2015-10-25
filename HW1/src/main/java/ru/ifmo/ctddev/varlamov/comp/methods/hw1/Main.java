package ru.ifmo.ctddev.varlamov.comp.methods.hw1;

import java.io.IOException;
import java.io.PrintStream;
import java.util.DoubleSummaryStatistics;

public class Main {
    public static void main(String[] args) throws IOException {
        ConsoleInterface console = new ConsoleInterface(System.in, System.out);
        StateSingleton state = StateSingleton.getInstance();
        state.setValue("init", "edge");
        state.setValue("dx", "0.1");
        state.setValue("n", "100");
        state.setValue("dt", "1");
        state.setValue("u", "0.05");
        state.setValue("chi", "0.01");
        state.setValue("iterations", "100");
        state.setValue("interval", "100");
        state.setValue("method", "ecf");
        console.addCall("calculate", new ConsoleInterface.ConsoleCallback() {
            @Override
            public void call(PrintStream out) {
                out.println("Reading state...");
                StateSingleton state = StateSingleton.getInstance();
                String inits = (String) state.getValue("init");
                int n = Integer.parseInt((String) state.getValue("n"));
                double[] init = new double[n];
                double chi = Double.parseDouble((String) state.getValue("chi"));
                double u = Double.parseDouble((String) state.getValue("u"));
                double dx = Double.parseDouble((String) state.getValue("dx"));
                double dt = Double.parseDouble((String) state.getValue("dt"));
                int iterations = Integer.parseInt((String) state.getValue("iterations"));
                switch (inits) {
                    case "edge":
                        init = new double[n];
                        for (int i = 0; i < n / 2; i++) {
                            init[i] = 1;
                        }
                        break;
                    case "previous":
                    case "prev":
                        init = (double[]) state.getValue("previous");
                        break;
                    case "splash":
                    default:
                        init[n / 2] = 1;
                        break;
                }
                String method = (String) state.getValue("method");
                Model model = ModelFactory.build(method);
                out.println("Calculating...");
                double[][] answer = model.solve(init, dx, dt, u, chi, iterations);
                state.setValue("previous", answer[answer.length - 1]);
                out.println("Animating...");
                long interval = Long.parseLong((String) state.getValue("interval"));
                PlotAnimation.animate(answer, 0, dx, interval, method);
            }
        });
        console.addCall("fromrs", new ConsoleInterface.ConsoleCallback() {
            @Override
            public void call(PrintStream out) {
                StateSingleton state = StateSingleton.getInstance();
                out.println("Fetching (r, s)...");
                double r, s;
                try {
                    r = Double.parseDouble((String)state.getValue("r"));
                    s = Double.parseDouble((String)state.getValue("s"));
                } catch (Exception e) {
                    out.println("No (r, s) found.");
                    return;
                }
                out.println("r = " + r + ", s = " + s);
                double dx = Double.parseDouble((String)state.getValue("dx"));
                double dt = Double.parseDouble((String)state.getValue("dt"));
                double u = s * dx / dt;
                double chi = r * dx * dx / dt;
                out.println("Setting u = " + u + ", chi = " + chi);
                state.setValue("u", u + "");
                state.setValue("chi", chi + "");
            }
        });
        console.start();
    }
}
