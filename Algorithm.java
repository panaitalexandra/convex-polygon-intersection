import java.util.*;

public class Algorithm {

    public static List<Point> determinaIntersectia(List<Point> P, List<Point> Q) {
        List<Point> rezultat = new ArrayList<>();
        int n = P.size();
        int m = Q.size();

        int i = 0;
        int j = 0;

        // Algoritmul O'Rourke: parcurgerea se face de pana la 2*(n+m) ori
        int maxIteratii = 2 * (n + m);
        int pasi = 0;

        while (pasi < maxIteratii) {
            Point p1 = P.get(i % n);
            Point p2 = P.get((i + 1) % n);

            Point q1 = Q.get(j % m);
            Point q2 = Q.get((j + 1) % m);

            // 1. Căutăm intersecția segmentelor curente
            Point intersectie = intersectieSegmente(p1, p2, q1, q2);
            if (intersectie != null) {
                adaugaDacaUnic(rezultat, intersectie);
            }

            // 2. Determinăm poziția relativă (stânga/dreapta)
            boolean pInInterior = Point.esteInStanga(q1, q2, p2);
            boolean qInInterior = Point.esteInStanga(p1, p2, q2);

            double produsVectorialDirectii = produsVectorialLaturi(p1, p2, q1, q2);

            // 3. Logica de avansare a indicilor conform notițelor de curs
            if (produsVectorialDirectii >= 0) {
                if (qInInterior) {
                    if (pInInterior) adaugaDacaUnic(rezultat, p2);
                    i++;
                } else {
                    j++;
                }
            } else {
                if (pInInterior) {
                    if (qInInterior) adaugaDacaUnic(rezultat, q2);
                    j++;
                } else {
                    i++;
                }
            }
            pasi++;
        }

        // Dacă nu s-au găsit intersecții de laturi, verificăm incluziunea totală
        if (rezultat.size() < 3) {
            rezultat = trateazaIncluziuneTotala(P, Q);
        }

        // 4. Sortare finală pentru a garanta vizualul corect (fără linii frânte)
        return finalizeazaPoligon(rezultat);
    }

    private static void adaugaDacaUnic(List<Point> lista, Point p) {
        // Metoda cea mai simplă: verificăm dacă punctul există deja oriunde în listă
        for (Point existent : lista) {
            if (Math.abs(existent.x - p.x) < 1e-7 && Math.abs(existent.y - p.y) < 1e-7) {
                return;
            }
        }
        lista.add(p);
    }

    private static List<Point> finalizeazaPoligon(List<Point> puncte) {
        if (puncte.size() < 3) return puncte;
        // Sortăm punctele în jurul centrului lor pentru a fi desenate corect de fillPolygon
        double cx = puncte.stream().mapToDouble(p -> p.x).average().orElse(0);
        double cy = puncte.stream().mapToDouble(p -> p.y).average().orElse(0);
        puncte.sort((a, b) -> Double.compare(Math.atan2(a.y - cy, a.x - cx), Math.atan2(b.y - cy, b.x - cx)));
        return puncte;
    }

    private static double produsVectorialLaturi(Point p1, Point p2, Point q1, Point q2) {
        double ax = p2.x - p1.x;
        double ay = p2.y - p1.y;
        double bx = q2.x - q1.x;
        double by = q2.y - q1.y;
        return ax * by - ay * bx;
    }

    private static Point intersectieSegmente(Point a, Point b, Point c, Point d) {
        double det = (b.x - a.x) * (d.y - c.y) - (d.x - c.x) * (b.y - a.y);
        if (Math.abs(det) < 1e-9) return null;

        double t = ((c.x - a.x) * (d.y - c.y) - (c.y - a.y) * (d.x - c.x)) / det;
        double u = ((c.x - a.x) * (b.y - a.y) - (c.y - a.y) * (b.x - a.x)) / det;

        if (t >= 0.0 && t <= 1.0 && u >= 0.0 && u <= 1.0) {
            return new Point(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y));
        }
        return null;
    }

    private static List<Point> trateazaIncluziuneTotala(List<Point> P, List<Point> Q) {
        if (estePunctInPoligon(Q, P.get(0))) return new ArrayList<>(P);
        if (estePunctInPoligon(P, Q.get(0))) return new ArrayList<>(Q);
        return new ArrayList<>();
    }

    private static boolean estePunctInPoligon(List<Point> poligon, Point p) {
        for (int k = 0; k < poligon.size(); k++) {
            if (!Point.esteInStanga(poligon.get(k), poligon.get((k + 1) % poligon.size()), p)) return false;
        }
        return true;
    }
}