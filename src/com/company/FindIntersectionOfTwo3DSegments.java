package com.company;

import java.util.Objects;

public class FindIntersectionOfTwo3DSegments {

    private Point A;
    private Point B;
    private Point C;
    private Point D;

    public FindIntersectionOfTwo3DSegments(Point a, Point b, Point c, Point d) {
        A = a;
        B = b;
        C = c;
        D = d;
    }

    public String solve() {
        //the coordinates of the guide vector of the line passing through points A and B
        double p1 = B.getX() - A.getX(), q1 = B.getY() - A.getY(), r1 = B.getZ() - A.getZ();
        //the coordinates of the guide vector of the line passing through points C and D
        double p2 = D.getX() - C.getX(), q2 = D.getY() - C.getY(), r2 = D.getZ() - C.getZ();

        //check whether the lines are parallel and coincide
        if (isParallel(new Point(p1, q1, r1), new Point(p2, q2, r2))) {
            if (isInSegments(A) || isInSegments(C) && isInSegments(B) || isInSegments(D)) {
                return findCommonSegment();
            }
        }

        //check that the lines lie in the same plane
        if (solveMixedProduct(new double[]{p2 - p1, q2 - q1, r2 - r1, p1, q1, r1, p2, q2, r2}) != 0) {
            return "0";

            //create a matrix and solve it to find the point of intersection
        } else {
            double[][] a = new double[4][3];
            for (int i = 0; i < 4; i++) {
                a[i] = new double[3];
            }
            double[] b = new double[4];
            formMatrix(A, B, a, b, 0);
            formMatrix(C, D, a, b, 2);

            GaussianElimination res = new GaussianElimination(a, b);
            double[] coor = res.primal();

            if (Objects.equals(coor, null)) {
                return "0";
            } else {
                if (isInSegments(new Point(coor[0], coor[1], coor[2]))) {
                    //form answer
                    String result = "1";
                    for (double v : coor) {
                        result += " " + v;
                    }
                    return result.replaceAll("-0.0", "0.0");
                }
                return "0";
            }
        }
    }

    private String findCommonSegment() {

        Point firstCoord = isInSegments(A) ? A : C;
        Point secondCoord = isInSegments(B) ? B : D;

        //check if the total segment is <= 1e-8
        if ((Math.abs(firstCoord.getX() - secondCoord.getX()) <= 1e-8 || firstCoord.getX() == secondCoord.getX()) &&
                (Math.abs(firstCoord.getY() - secondCoord.getY()) <= 1e-8 || firstCoord.getY() == secondCoord.getY()) &&
                (Math.abs(firstCoord.getZ() - secondCoord.getZ()) <= 1e-8 || firstCoord.getZ() == secondCoord.getZ())) {
            firstCoord.setX(firstCoord.getX() + ((secondCoord.getX() - firstCoord.getX()) / 2));
            firstCoord.setY(firstCoord.getY() + ((secondCoord.getY() - firstCoord.getY()) / 2));
            firstCoord.setZ(firstCoord.getZ() + ((secondCoord.getZ() - firstCoord.getZ()) / 2));
            return "1 " + firstCoord.getX() + " " + firstCoord.getY() + " " + firstCoord.getZ();

        } else {
            return "4 " + firstCoord.getX() + " " + firstCoord.getY() + " " + firstCoord.getZ() + " " + secondCoord.getX() + " " + secondCoord.getY() + " " + secondCoord.getZ();
        }
    }


    //check whether the point belongs to the given segments
    private boolean isInSegments(Point point) {

        double startSegX = A.getX() < B.getX() ? A.getX() : B.getX();
        double endSegX = A.getX() > B.getX() ? A.getX() : B.getX();
        double startSegY = A.getY() < B.getY() ? A.getY() : B.getY();
        double endSegY = A.getY() > B.getY() ? A.getY() : B.getY();
        double startSegZ = A.getZ() < B.getZ() ? A.getZ() : B.getZ();
        double endSegZ = A.getZ() > B.getZ() ? A.getZ() : B.getZ();

        double startSegX1 = C.getX() < D.getX() ? C.getX() : D.getX();
        double endSegX1 = C.getX() > D.getX() ? C.getX() : D.getX();
        double startSegY1 = C.getY() < D.getY() ? C.getY() : D.getY();
        double endSegY1 = C.getY() > D.getY() ? C.getY() : D.getY();
        double startSegZ1 = C.getZ() < D.getZ() ? C.getZ() : D.getZ();
        double endSegZ1 = C.getZ() > D.getZ() ? C.getZ() : D.getZ();

        return startSegX <= point.getX() && point.getX() <= endSegX && startSegY <= point.getY()
                && point.getY() <= endSegY && startSegZ <= point.getZ() && point.getZ() <= endSegZ
                && startSegX1 <= point.getX() && point.getX() <= endSegX1 && startSegY1 <= point.getY()
                && point.getY() <= endSegY1 && startSegZ1 <= point.getZ() && point.getZ() <= endSegZ1;
    }

    private double solveMixedProduct(double[] vyz) {
        return vyz[0] * vyz[4] * vyz[8] + vyz[6] * vyz[1] * vyz[5] + vyz[3] * vyz[7] * vyz[2]
                - vyz[6] * vyz[4] * vyz[2] - vyz[0] * vyz[7] * vyz[5] - vyz[3] * vyz[1] * vyz[8];
    }

    private boolean isParallel(Point a, Point b) {
        double ab_X = a.getY() * b.getZ() - a.getZ() * b.getY();
        double ab_Y = a.getZ() * b.getX() - a.getX() * b.getZ();
        double ab_Z = a.getX() * b.getY() - a.getY() * b.getX();

        if (ab_X == 0 && ab_Y == 0 && ab_Z == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void formMatrix(Point A, Point B, double[][] matrix, double[] b, int i) {
        double ax = -A.getX(), ay = -A.getY(), az = -A.getZ();
        double p = B.getX() - A.getX(), q = B.getY() - A.getY(), r = B.getZ() - A.getZ();

        if (p != 0 || q != 0) {
            matrix[i][0] = q;
            matrix[i][1] = -p;
            matrix[i][2] = 0;
            b[i] = p * ay - (q * ax);
        } else {
            matrix[i][0] = r;
            matrix[i][1] = 0;
            matrix[i][2] = -p;
            b[i] = p * az - (r * ax);
        }
        if (q != 0 || r != 0) {
            matrix[i + 1][0] = 0;
            matrix[i + 1][1] = r;
            matrix[i + 1][2] = -q;
            b[i + 1] = az * q - (ay * r);

        } else {
            matrix[i + 1][0] = r;
            matrix[i + 1][1] = 0;
            matrix[i + 1][2] = -p;
            b[i + 1] = az * p - (ax * r);
        }
    }
}