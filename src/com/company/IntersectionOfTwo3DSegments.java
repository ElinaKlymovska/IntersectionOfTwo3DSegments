package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class IntersectionOfTwo3DSegments {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] data = Arrays.stream(scanner.nextLine().split(" "))
                .mapToDouble(Double::parseDouble).toArray();
        FindIntersectionOfTwo3DSegments intersectionOfTwo3DSegments = new FindIntersectionOfTwo3DSegments(
                new Point(data[0], data[1], data[2]), new Point(data[3], data[4], data[5]),
                new Point(data[6], data[7], data[8]), new Point(data[9], data[10], data[11])
        );

        System.out.println(intersectionOfTwo3DSegments.solve());


    }
}
