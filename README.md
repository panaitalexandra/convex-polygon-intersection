# convex-polygon-intersection
# Intersection of Two Convex Polygons (Linear Time Algorithm)

## 1. Requirement

Given two convex polygons, **P** (with $n$ vertices) and **Q** (with $m$ vertices), the objective is to determine their intersection, **$P \cap Q$**. 

Since the intersection of two convex sets is always convex, the result will be a new convex polygon (or potentially an empty set, a point, or a segment).



---

## 2. Methodology: Boundary Traversal

The algorithm efficiently finds the intersection by simultaneously traversing the boundaries of both polygons. This approach avoids the $O(n \cdot m)$ complexity of brute-force segment intersection, achieving a linear complexity of **$O(n + m)$**.

### Step 1: Initialization
* Start with indices $i = 1$ for polygon **P** and $j = 1$ for polygon **Q**.
* Identify the current edges $e_P = [P_{i-1} P_i]$ and $e_Q = [Q_{j-1} Q_j]$.

### Step 2: Plane Half-space Analysis
The algorithm considers the supporting lines of the current edges. At each step, it determines:
* Whether the current edges intersect.
* Which edge "aims" at the other's supporting line.
* If a vertex of one polygon lies in the "inner" half-plane of the other polygon's edge.



### Step 3: Advance Rules (Decision Logic)
The decision to increment $i$ or $j$ is the core of the algorithm. It depends on:
1.  The relative position of $P_i$ with respect to the line $Q_{j-1}Q_j$.
2.  The relative position of $Q_j$ with respect to the line $P_{i-1}P_i$.
3.  The cross product of the edge vectors to determine which polygon is "closing in" on the intersection area.

### Step 4: Termination
The process continues until:
* One of the polygons has been fully traversed twice (to ensure all intersection points are captured).
* The resulting vertices form the boundary of the intersection polygon $P \cap Q$.

---

## 3. Complexity
* **Time Complexity:** **$O(n + m)$** – Each edge is processed a constant number of times.
* **Space Complexity:** **$O(n + m)$** – To store the vertices of the resulting intersection polygon.

## 4. Edge Cases Handled
* **Disjoint Polygons:** The algorithm correctly returns an empty set.
* **Containment:** If $P \subset Q$, the result is $P$ (and vice-versa).
* **Degenerate Intersection:** Handles cases where the intersection is a single point or a line segment.
