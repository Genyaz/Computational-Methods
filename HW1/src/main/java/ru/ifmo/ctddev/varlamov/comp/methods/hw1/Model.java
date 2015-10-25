package ru.ifmo.ctddev.varlamov.comp.methods.hw1;

public interface Model {
    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations);
}
