package snpclip;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class SnipVisualizer extends JPanel {
    private static final int START = 218;
    private static final int MID = 255;
    private static final int END = 144;
    private static final Color[] subsetColor = new Color[] {//Magenta
            new Color(255, 0, 144),
            new Color(255, 0, 144) };
    private static final Color[] matchColor = new Color[] {//Cyan
            new Color(0, 180, 247),
            new Color(0, 180, 247) };
    private static final Color[] missingColor = new Color[] {//GRAY
            new Color(232, 221, 194),
            new Color(232, 221, 194) };

    private static final Color[] homozygoteColor = new Color[] {//YELLOW
            new Color(255, 255, 0),
            new Color(255, 255, 0) };




    private static final int missingCode = 0;
    private static final int matchCode = 1;
    private static final int subsetCode = 2;
    private static final int homozygoteCode = 3;

    private int indicatorHeight;
    private int indicatorWidth = 10;
    private int indicatorXSpacing;
    private int indicatorYSpacing;
    private int maxColumns;
    private int maxRows;
    private int flag[];
    Insets insets;

    private int LIST_CELL_WIDTH=10;

    public SnipVisualizer(int LIST_CELL_WIDTH) {
        maxRows = 1;
        indicatorXSpacing =1;
        indicatorYSpacing = 0;
        this.indicatorWidth = 10;
        this.LIST_CELL_WIDTH = LIST_CELL_WIDTH;
        indicatorHeight = 4;
        insets = new Insets(2,2,2,2);
    }

    public void setBasicInfo(int length, int snpflag[])
    {
        flag = snpflag;
    }

    public void setIndicatorHeight(int height) {
        this.indicatorHeight = height;
    }

    public void setIndicatorWidth(int width) {
        indicatorWidth = width;
        repaint();
    }

    public void setIndicatorXSpacing(int spacing) {
        indicatorXSpacing = spacing;
    }

    public void setIndicatorYSpacing(int spacing) {
        indicatorYSpacing = spacing;
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);



        indicatorHeight = getHeight() - insets.top - insets.bottom;
        int lx2 = getWidth() - insets.right-insets.left;
        indicatorWidth = lx2/LIST_CELL_WIDTH;

        int x = insets.left;
        int y = insets.top;

        for (int i = 0; i < flag.length; i++) {
            Graphics2D g2 = (Graphics2D)g.create();
            Color[] colors =getColor(flag[i]);

            g2.setPaint(new GradientPaint(0, 0, colors[0], indicatorWidth, 0, colors[1]));

            g2.fillRect(x, y, indicatorWidth, indicatorHeight);
            x += (indicatorWidth + indicatorXSpacing);
            g2.dispose();

        }

    }

    private Color[] getColor(int type) {
        Color color[] = null;
        switch(type) {
            case matchCode:
                color = matchColor;
                break;
            case subsetCode:
                color = subsetColor;
                break;
            case homozygoteCode:
                color = homozygoteColor;
                break;
            default:
                color = missingColor;
                break;
        }
        return color;
    }
}
