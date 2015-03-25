package snpclip;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class SnipAllVisualizer extends JPanel {
    private enum Type {
        MATCH_CASE,
        SUBSET_CASE,
        NONE
    };

    private static final Color[] arrowColors = new Color[] {
        new Color(0, 0, 255),
        new Color(0, 0, 218)
    };

    private static final Color subsetColor = new Color(255, 0, 144); //Magenta
    private static final Color matchColor = new Color(0, 180, 247); //Cyan
    private static final Color homozygoteColor = new Color(255, 255, 0); //Yellow

    private static final Color missingColor = new Color(232, 221, 194);

    private static final Color barColor = new java.awt.Color(75, 66, 49);

    private static final Color[] deaultColor = new Color[] {
        new Color(255, 255, 255),
        new Color(255, 255, 255),
        new Color(255, 255, 255)};

    private int indicatorHeight;
    public int indicatorWidth;
    private int indicatorXSpacing;
    private int indicatorYSpacing;
    private int maxColumns;
    private int maxRows;
    Insets insets;

    private int StartIndex;
    private int TotalSnpSize=1;
    int SnpAllFlag[];

    private GeneralPath path;
    private float size = 20;
    BasicStroke strokes; ;
    int width;

    public SnipAllVisualizer() {
        maxRows = 1;
        indicatorXSpacing =0;
        indicatorYSpacing = 0;
        indicatorWidth = 1;
        indicatorHeight = 4;
        insets = new Insets(20,10,20,10);

        path = new GeneralPath();
        path.moveTo(size - size / 2, -size / 2);
        path.lineTo(size, 0);
        path.lineTo(size - size / 2, +size / 2);
        path.lineTo(size - size / 2, -size / 2);

        strokes = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
    }

    public void setAllSnpFlag(int[] value)
    {
        SnpAllFlag = value;
        revalidate();
        repaint();
    }

    public void setTotalSnpSize(int value)
    {
        TotalSnpSize = value;
        width = (value*indicatorWidth) + insets.left + insets.right;
        this.setPreferredSize(new Dimension(width, this.getHeight()));

        revalidate();
        repaint();
    }

    public void updateSnpSizeWindow()
    {
        width = (TotalSnpSize*indicatorWidth) + insets.left + insets.right;
        this.setPreferredSize(new Dimension(width, this.getHeight()));

        revalidate();
        repaint();
    }

    public void setMaxRows(int rows) {
        if (rows < 1) {
            throw new IllegalArgumentException();
        }
        maxRows = rows;
    }

    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return calcSize();
    }

    public Dimension getMinimumSize() {
        if (isMinimumSizeSet()) {
            return super.getMinimumSize();
        }
        return calcSize();
    }

    private Dimension calcSize() {
        Insets insets = getInsets();
        return new Dimension(
                (indicatorWidth + indicatorXSpacing) * maxColumns - indicatorXSpacing + insets.left + insets.right,
                (indicatorHeight + indicatorYSpacing) * maxRows - indicatorYSpacing + insets.top + insets.bottom);
    }

    public void setSNP(int index) {
        this.StartIndex = index;
        revalidate();
        repaint();
    }

    public void getSNP(int index) {
        this.StartIndex = index;
        revalidate();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getForeground());

        // Base white rect
        int lx1 = insets.left;
        int ly1 = getHeight() / 2 - indicatorHeight-insets.bottom;
        int lx2 = width - insets.right;

        Graphics2D g2 = (Graphics2D)g.create();
        ly1 = indicatorHeight+insets.bottom;
        int x = insets.left;

        g.setColor(new Color(255, 255, 255));
        g.fillRect(lx1, ly1, getWidth() - (insets.right * 2), indicatorHeight);

        for (int i = 0; i < TotalSnpSize; i++) {

            g2.setColor(getColor(i));
            g2.fillRect(x, ly1, indicatorWidth, indicatorHeight);
            x += (indicatorWidth);
        }

        g.setColor(Color.black);
        g.drawLine(lx1, ly1 - indicatorHeight / 2, lx1, ly1 + indicatorHeight + indicatorHeight / 2);
        g.drawLine(lx2, ly1 - indicatorHeight / 2, lx2, ly1 + indicatorHeight + indicatorHeight / 2);

        if(StartIndex>=0)
        {
            int y = getHeight() / 2 -indicatorHeight*3 -insets.bottom;
            x = StartIndex+insets.left;
            g.setColor(barColor);
            g.fillRect(x, y, 1, indicatorHeight*3);
            g2.setPaint(new GradientPaint(0, 0, arrowColors[0], 1, 0, arrowColors[1]));
            AffineTransform at = AffineTransform.getTranslateInstance(x, ly1 + size + indicatorHeight * 2);
            at.rotate(Math.toRadians( -90));
            g2.setStroke(strokes);
            g2.draw(at.createTransformedShape(new Line2D.Float(0, 0, size, 0)));

            g2.fill(at.createTransformedShape(path));
        }

    }

    private Color getColor(int index) {
        Color color = null;

        int colortype = 0;
        if(SnpAllFlag!=null)
            colortype = SnpAllFlag[index];

        switch(colortype) {
            case 1:
                color = subsetColor; // Red
                break;
            case 2:
                color = matchColor;  // Green
                break;
            case 3:
                color = homozygoteColor;  // Yellow
                break;
            default:
                color = missingColor;
                break;
        }

        return color;
    }
}
