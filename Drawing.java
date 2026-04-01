import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Drawing extends JPanel {
    private List<Point> polyP = new ArrayList<>();
    private List<Point> polyQ = new ArrayList<>();
    private List<Point> intersectie = new ArrayList<>();
    private boolean editandP = true;
    private Consumer<String> logger;

    public Drawing(Consumer<String> logger) {
        this.logger = logger;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                if (SwingUtilities.isRightMouseButton(e)) {
                    editandP = !editandP;
                    logger.accept("> Switched to Polygon " + (editandP ? "P" : "Q"));
                } else {
                    if (editandP) polyP.add(p); else polyQ.add(p);
                    repaint();
                }
            }
        });
    }

    public void calculeaza() {
        if (polyP.size() < 3 || polyQ.size() < 3) {
            logger.accept("> Error: Minimum 3 points required!");
            return;
        }
        intersectie = Algorithm.determinaIntersectia(polyP, polyQ);
        logger.accept("> Intersection calculated: " + intersectie.size() + " points.");
        repaint();
    }

    public void reset() {
        polyP.clear(); polyQ.clear(); intersectie.clear();
        editandP = true; repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Grid discret pastel
        g2.setColor(new Color(245, 245, 250));
        for(int i=0; i<getWidth(); i+=40) g2.drawLine(i, 0, i, getHeight());
        for(int i=0; i<getHeight(); i+=40) g2.drawLine(0, i, getWidth(), i);

        deseneazaPoligon(g2, polyP, new Color(150, 180, 255), "P");
        deseneazaPoligon(g2, polyQ, new Color(255, 180, 200), "Q");

        if (!intersectie.isEmpty()) {
            int[] x = intersectie.stream().mapToInt(p -> (int)p.x).toArray();
            int[] y = intersectie.stream().mapToInt(p -> (int)p.y).toArray();
            g2.setColor(new Color(248, 141, 141, 255)); // Portocaliu transparent conform cerintei
            g2.fillPolygon(x, y, intersectie.size());
            g2.setColor(new Color(140, 0, 96));
            g2.drawPolygon(x, y, intersectie.size());
        }
    }

    private void deseneazaPoligon(Graphics2D g2, List<Point> puncte, Color culoare, String et) {
        if (puncte.isEmpty()) return;
        g2.setColor(culoare);
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < puncte.size(); i++) {
            Point p1 = puncte.get(i);
            Point p2 = puncte.get((i + 1) % puncte.size());
            g2.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
            g2.fillOval((int)p1.x - 3, (int)p1.y - 3, 6, 6);
        }
    }
}