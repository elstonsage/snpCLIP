package snpclip;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import javax.swing.JComponent;
import java.awt.geom.AffineTransform;

/**
 * Provides a graphical view of the overall strength of a password.
 *
 * @author sky
 */
public class TitlePanel extends JComponent {
    private static final int START = 218;
    private static final int MID = 255;
    private static final int END = 130;
    private static final Color[] RED_COLORS = new Color[] {
            new Color(START, 0, 0),
            new Color(MID, 0, 0),
            new Color(END, 0, 0) };
    private static final Color[] YELLOW_COLORS = new Color[] {
            new Color(START, START, 0),
            new Color(MID, MID, 0),
            new Color(END, END, 0) };
    private static final Color[] BLUE_COLORS = new Color[] {
            new Color(0, START, 0),
            new Color(0, MID, 0),
            new Color(0, END, 0) };

    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        Insets insets = getInsets();
        Graphics2D g2 = (Graphics2D)g.create();
     //   g2.translate(insets.left, insets.top);

        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;

        int p1Width = w * 5 / 8;
        int barHeight = getWidth();

//        colors = BLUE_COLORS;

        java.awt.geom.Rectangle2D rec = new java.awt.geom.Rectangle2D.Double(0, 0,w,h);

        AffineTransform at = AffineTransform.getTranslateInstance(w/2, h/2);
        at.rotate(Math.toRadians(90));
        at.scale(10,1);
        g2.setPaint(new GradientPaint(0, 0, Color.blue,h, w, Color.white));
        g2.fill(at.createTransformedShape(rec));
        //g2.fill(rec);

      //  g2.fillRect(0, 0, w, h);
        //g2.setPaint(new GradientPaint(p1Width, 0, colors[1], w, 0, colors[2]));
        //g2.fillRect(p1Width, h - barHeight, w - p1Width, barHeight);
        //g2.dispose();
    }
}
