package snpclip;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class Gauge extends JPanel {
    private int numlocs = 2;
    private int numcells = numlocs * numlocs;
    private int[] cells;
    private BufferedImage bi;
    int w, h, cw, ch;
    private double angle = -180;
    URL imageSrc = null;
    private GeneralPath path;
    private float size = 40;
    BasicStroke strokes; ;
    public Gauge() {
        try {
            imageSrc = ((Gauge.class.getResource("bg.png")).toURI()).toURL();

            path = new GeneralPath();
            path.moveTo(size - size / 8, -size / 8);
            path.lineTo(size, 0);
            path.lineTo(size - size / 8, +size / 8);

            strokes = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
            try {
                bi = ImageIO.read(imageSrc);
                w = bi.getWidth(null);
                h = bi.getHeight(null);

            } catch (IOException e) {
                System.out.println("Image could not be read");
                System.exit(1);
            }
            cw = w / numlocs;
            ch = h / numlocs;
            cells = new int[numcells];
            for (int i = 0; i < numcells; i++) {
                cells[i] = i;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAngle(double angle1) {
        angle = angle1;
        this.update(this.getGraphics());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int dx, dy;
        for (int x = 0; x < numlocs; x++) {
            int sx = x * cw;
            for (int y = 0; y < numlocs; y++) {
                int sy = y * ch;
                int cell = cells[x * numlocs + y];
                dx = (cell / numlocs) * cw;
                dy = (cell % numlocs) * ch;
                g2.drawImage(bi,
                             dx, dy, dx + cw, dy + ch,
                             sx, sy, sx + cw, sy + ch,
                             null);
            }
        }

        AffineTransform at = AffineTransform.getTranslateInstance(w / 2 - 1, h / 2 + 25);
        at.rotate(Math.toRadians(angle));
        g2.setStroke(strokes);
        g2.setColor(new Color(75, 66, 49));
        g2.draw(at.createTransformedShape(new Line2D.Float(0, 0, size, 0)));
        g2.draw(at.createTransformedShape(path));
    }

    private void jbInit() throws Exception {
    }

}
