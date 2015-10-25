package ru.ifmo.ctddev.varlamov.comp.methods.hw1;

import java.util.ArrayList;
import java.util.Arrays;

public class ModelFactory {
    /**
     * Equation a_i * x_i-1 + c_i * x_i + b_i * x_i+1 = f_i
     * @param equations {a, b, c, f}[]
     * @return solution x
     */
    public static double[] triDiagonalSolve(double[][] equations) {
        double[] alpha = new double[equations.length], beta = new double[equations.length];
        alpha[1] = -equations[0][1] / equations[0][2];
        beta[1] = equations[0][3] / equations[0][2];
        for (int i = 1; i < equations.length - 1; i++) {
            alpha[i + 1] = -equations[i][1] / (alpha[i] * equations[i][0] + equations[i][2]);
            beta[i + 1] = (equations[i][3] - beta[i] * equations[i][0]) / (alpha[i] * equations[i][0] + equations[i][2]);
        }
        double[] x = new double[equations.length];
        x[x.length - 1] = (equations[x.length - 1][3] - beta[x.length - 1] * equations[x.length - 1][0])
                            / (equations[x.length - 1][2] + alpha[x.length - 1] * equations[x.length - 1][0]);
        for (int i = x.length - 2; i >= 0; i--) {
            x[i] = alpha[i + 1] * x[i + 1] + beta[i + 1];
        }
        return x;
    }

    public static Model build(String name) {
        switch (name.toLowerCase()) {
            case "explicit counter flow":
            case "ecf":
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        for (int i = 1; i <= iterations; i++) {
                            result[i] = new double[init.length];
                            result[i][0] = left;
                            result[i][init.length - 1] = right;
                            for (int j = 1; j < init.length - 1; j++) {
                                result[i][j] = result[i - 1][j] * (1 - s - 2 * r) + (r + s) * result[i - 1][j - 1] + r * result[i - 1][j + 1];
                            }
                        }
                        return result;
                    }
                };
            case "explicit by flow":
            case "ebf":
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        for (int i = 1; i <= iterations; i++) {
                            result[i] = new double[init.length];
                            result[i][0] = left;
                            result[i][init.length - 1] = right;
                            for (int j = 1; j < init.length - 1; j++) {
                                result[i][j] = result[i - 1][j] * (1 + s - 2 * r) + (r - s) * result[i - 1][j + 1] + r * result[i - 1][j - 1];
                            }
                        }
                        return result;
                    }
                };
            case "explicit central":
            case "ec":
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        for (int i = 1; i <= iterations; i++) {
                            result[i] = new double[init.length];
                            result[i][0] = left;
                            result[i][init.length - 1] = right;
                            for (int j = 1; j < init.length - 1; j++) {
                                result[i][j] = result[i - 1][j] * (1 - 2 * r) + (r - s / 2) * result[i - 1][j + 1] + (r + s / 2) * result[i - 1][j - 1];
                            }
                        }
                        return result;
                    }
                };
            case "leapfrog":
            case "lf":
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        result[1] = new double[init.length];
                        result[1][0] = left;
                        result[1][init.length - 1] = right;
                        for (int j = 1; j < init.length - 1; j++) {
                            result[1][j] = result[0][j] * (1 - 2 * r) + (r - s / 2) * result[0][j + 1] + (r + s / 2) * result[0][j - 1];
                        }
                        for (int i = 2; i <= iterations; i++) {
                            result[i] = new double[init.length];
                            result[i][0] = left;
                            result[i][init.length - 1] = right;
                            for (int j = 1; j < init.length - 1; j++) {
                                result[i][j] = result[i - 2][j] - result[i - 1][j] * 2 * r + (r - s / 2) * result[i - 1][j + 1] + (r + s / 2) * result[i - 1][j - 1];
                            }
                        }
                        return result;
                    }
                };
            case "implicit counter flow":
            case "icf":
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        for (int i = 1; i <= iterations; i++) {
                            double[][] equations = new double[init.length][];
                            equations[0] = new double[]{0, 0, 1, left};
                            equations[init.length - 1] = new double[]{0, 0, 1, right};
                            for (int j = 1; j < init.length - 1; j++) {
                                equations[j] = new double[]{-(s + r), -r, (1 + s + 2 * r), result[i - 1][j]};
                            }
                            result[i] = triDiagonalSolve(equations);
                        }
                        return result;
                    }
                };
            case "implicit by flow":
            case "ibf":
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        for (int i = 1; i <= iterations; i++) {
                            double[][] equations = new double[init.length][];
                            equations[0] = new double[]{0, 0, 1, left};
                            equations[init.length - 1] = new double[]{0, 0, 1, right};
                            for (int j = 1; j < init.length - 1; j++) {
                                equations[j] = new double[]{-r, s - r, (1 - s + 2 * r), result[i - 1][j]};
                            }
                            result[i] = triDiagonalSolve(equations);
                        }
                        return result;
                    }
                };
            case "implicit central":
            case "ic":
            default:
                return new Model() {
                    @Override
                    public double[][] solve(double[] init, double dx, double dt, double u, double chi, int iterations) {
                        double s = u * dt / dx, r = chi * dt / (dx * dx);
                        double left = init[0];
                        double right = init[init.length - 1];
                        double[][] result = new double[iterations + 1][];
                        result[0] = Arrays.copyOf(init, init.length);
                        for (int i = 1; i <= iterations; i++) {
                            double[][] equations = new double[init.length][];
                            equations[0] = new double[]{0, 0, 1, left};
                            equations[init.length - 1] = new double[]{0, 0, 1, right};
                            for (int j = 1; j < init.length - 1; j++) {
                                equations[j] = new double[]{-(r + s / 2), -(r - s / 2), (1 + 2 * r), result[i - 1][j]};
                            }
                            result[i] = triDiagonalSolve(equations);
                        }
                        return result;
                    }
                };
        }
    }
}
