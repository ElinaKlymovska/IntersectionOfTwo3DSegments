package com.company;

public class GaussianElimination {
    private static final double EPSILON = 1e-8;

    private final int m;      // number of rows
    private final int n;      // number of columns
    private double[][] a;     // m-by-(n+1) augmented matrix

    public GaussianElimination(double[][] A, double[] b) {
        m = A.length;
        n = A[0].length;

        //if (b.length != m) throw new IllegalArgumentException("Dimensions disagree");

        // build augmented matrix
        a = new double[m][n + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = A[i][j];
        for (int i = 0; i < m; i++)
            a[i][n] = b[i];

        forwardElimination();

        assert certifySolution(A, b);
    }

    // forward elimination
    private void forwardElimination() {
        for (int p = 0; p < Math.min(m, n); p++) {

            // find pivot row using partial pivoting
            int max = p;
            for (int i = p + 1; i < m; i++) {
                if (Math.abs(a[i][p]) > Math.abs(a[max][p])) {
                    max = i;
                }
            }

            // swap
            swap(p, max);

            // singular or nearly singular
            if (Math.abs(a[p][p]) <= EPSILON) {
                continue;
            }
            // pivot
            pivot(p);
        }
    }

    // swap row1 and row2
    private void swap(int row1, int row2) {
        double[] temp = a[row1];
        a[row1] = a[row2];
        a[row2] = temp;
    }

    // pivot on a[p][p]
    private void pivot(int p) {
        for (int i = p + 1; i < m; i++) {
            double alpha = a[i][p] / a[p][p];
            for (int j = p; j <= n; j++) {
                a[i][j] -= alpha * a[p][j];
            }
        }
    }

    public double[] primal() {
        // back substitution
        double[] x = new double[n];
        for (int i = Math.min(n - 1, m - 1); i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += a[i][j] * x[j];
            }

            if (Math.abs(a[i][i]) > EPSILON)
                x[i] = (a[i][n] - sum) / a[i][i];
            else if (Math.abs(a[i][n] - sum) > EPSILON)
                return null;
        }

        // redundant rows
        for (int i = n; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += a[i][j] * x[j];
            }
            if (Math.abs(a[i][n] - sum) > EPSILON)
                return null;
        }
        return x;
    }

    public boolean isFeasible() {
        return primal() != null;
    }

    private boolean certifySolution(double[][] A, double[] b) {
        if (!isFeasible()) return true;
        double[] x = primal();
        for (int i = 0; i < m; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            if (Math.abs(sum - b[i]) > EPSILON) {
                return false;
            }
        }
        return true;
    }
}