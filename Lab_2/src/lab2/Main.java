package lab2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JPanel implements ActionListener {
	Timer timer;

    private static int maxWidth;
    private static int maxHeight;
    
    private static int tvwidth = 220;
    private static int tvheight = 150;
    
    private static int screenwidth = 145;
    private static int screenheight = 120;
    private static int btndistance = 25;

    private double angle = 0;

    private double scale = 0.1;
    private double delta = 0.01;
    private double tx = 0;
    private double ty = 0;
    
    private static double radius = 200;

    public Main() {
        timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);             

        g2d.setBackground(new Color(127, 255, 0));
        g2d.clearRect(0, 0, maxWidth, maxHeight);

        g2d.setColor(new Color(255, 253, 56));
        BasicStroke bs1 = new BasicStroke(16, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g2d.setStroke(bs1);
        g2d.drawRect(20, 20, maxWidth-50, maxHeight-50);
        
        g2d.translate(maxWidth / 2, maxHeight / 2);
        g2d.translate(tx, ty);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)scale));

        GradientPaint gp = new GradientPaint(5, 25, Color.YELLOW, 20, 2, Color.BLUE, true);
        g2d.setPaint(gp);
        g2d.fillRoundRect(-tvwidth/2+15, -tvheight/2+15, screenwidth, screenheight, 25, 25);
        
        g2d.setColor(new Color(255, 165, 0));
        g2d.fillRect(-tvwidth/2, -tvheight/2, tvwidth, tvheight);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL));
        int[] antennas = new int[3];
        antennas[0] = -tvheight/2-35;
        antennas[1] = -tvheight/2;
        antennas[2] = -tvheight/2-35;
        g2d.drawPolyline(new int[] {-35, 0, 35}, antennas, 3 );
        
        g2d.setColor(Color.BLACK);
        BasicStroke bs2 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        g2d.setStroke(bs2);
        for(int i=1; i<=3; i++) {
        	g2d.drawLine(screenwidth/2+10, tvheight/2-btndistance*i, screenwidth/2+10, tvheight/2-btndistance*i);
        }

    }

    public void actionPerformed(ActionEvent e) {

        if (scale < 0.01) {
            delta = -delta;
        } else if (scale > 0.99) {
            delta = -delta;
        }
        angle -= 0.01;//
        scale += delta;
        
        tx = radius*Math.cos(angle);
        ty = radius*Math.sin(angle);
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("lab2");
        frame.add(new Main());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }
}
