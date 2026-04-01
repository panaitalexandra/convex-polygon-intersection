import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GraphicInterface extends JFrame {
    private JTextArea logArea;
    private final Color COLOR_PASTEL_PINK = new Color(255, 230, 240);
    private final Color COLOR_ROSE_ACCENT = new Color(255, 150, 190);
    private final Color COLOR_TEXT = new Color(80, 70, 90);

    public GraphicInterface() {
        setTitle("Convex Polygon Intersection");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        Drawing drawingArea = new Drawing(msg -> logArea.append(msg + "\n"));
        drawingArea.setBorder(new LineBorder(COLOR_PASTEL_PINK, 5, true));

        // Sidebar roz pudra
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, 750));
        sidebar.setBackground(COLOR_PASTEL_PINK);
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Action Tools");
        title.setForeground(COLOR_TEXT);
        title.setFont(new Font("Segoe UI", Font.ITALIC | Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCalc = createGirlyButton("CALCULATE INTERSECTION", COLOR_ROSE_ACCENT);
        btnCalc.addActionListener(e -> drawingArea.calculeaza());

        JButton btnReset = createGirlyButton("CLEAR CANVAS", new Color(230, 230, 235));
        btnReset.addActionListener(e -> { drawingArea.reset(); logArea.setText(""); });

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(255, 255, 255, 180));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(COLOR_ROSE_ACCENT, 1), "Action History", 0, 0, null, COLOR_TEXT));

        sidebar.add(title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(btnCalc);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnReset);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(logScroll);

        add(sidebar, BorderLayout.WEST);
        add(drawingArea, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createGirlyButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setBackground(bg);
        btn.setForeground(COLOR_TEXT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphicInterface::new);
    }
}