# IntersectionOfTwo3DSegments

Description

There are two segments in 3D space. Find their intersection.



Input:

Twelve reals, where consecutive groups of six numbers describe the 3D coordinates of two ends of segments.

Each length of the segments is greater than 1e-8.



Output:

There are the following output cases:

1.      Integer number 0 if the segments do not intersect.

2.      Integer number 1 and the 3D coordinates of the intersection point if the segments intersect at a point.

3.      Integer number 4 and the 3D coordinates of the ends of the intersection segment if the segments intersect at a segment.

4.      Integer number 1 and the 3D coordinates of the middle of the intersection segment if the segments intersect at a segment and the length of the intersection is less than 1e-8.



Examples:

Input:

0.0 0.0 0.0 1.0 0.0 0.0 3.0 5.0 4.0 0.0 1.0 0.0

Output:

0


Input:

0.0 0.0 0.0 3.0 5.0 0.0 -3.0 -5.0 0.0 0.0 0.0 0.0

Output:

1 0.0 0.0 0.0


Input:

3.0 5.0 7.0 30.0 50.0 7.0 0.0 0.0 7.0 6.0 10.0 7.0

Output:

4 3.0 5.0 7.0 6.0 10.0 7.0
