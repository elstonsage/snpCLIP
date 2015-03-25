package snpclip;

import javax.swing.JPanel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;

class SeqMugsRenderer extends JPanel implements TableCellRenderer{
    private final GridLayout gl = new GridLayout(2,1,1,1);

        SnipVisualizer caseStrengthBar;
        SnipVisualizer controlStrengthBar;

        public SeqMugsRenderer(int max_width) {
        setLayout(gl);

        this.setBackground(Color.WHITE);

        caseStrengthBar = new SnipVisualizer(max_width);
        caseStrengthBar.setOpaque(true);
        caseStrengthBar.setBackground(Color.WHITE);

        controlStrengthBar = new SnipVisualizer(max_width);
        controlStrengthBar.setOpaque(true);
        controlStrengthBar.setBackground(Color.WHITE);

        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();

            int[][] snpFlag = (int[][]) value;

            int caseFlag[] = snpFlag[0];
            int controlFlag[] = snpFlag[1];

            caseStrengthBar.setBasicInfo(caseFlag.length, (int[]) caseFlag);
            add(caseStrengthBar);

            if(controlFlag != null)
            {
                controlStrengthBar.setBasicInfo(controlFlag.length, (int[]) controlFlag);
                add(controlStrengthBar);
            }

            if (isSelected) {
                this.setForeground(table.getSelectionForeground());
                this.setBackground(table.getSelectionBackground());

                caseStrengthBar.setForeground(table.getSelectionForeground());
                caseStrengthBar.setBackground(table.getSelectionBackground());
                controlStrengthBar.setForeground(table.getSelectionForeground());
                controlStrengthBar.setBackground(table.getSelectionBackground());

            } else {

                this.setForeground(table.getForeground());
                this.setBackground(table.getBackground());

                caseStrengthBar.setForeground(table.getForeground());
                caseStrengthBar.setBackground(table.getBackground());
                controlStrengthBar.setForeground(table.getForeground());
                controlStrengthBar.setBackground(table.getBackground());

            }
            return this;
        }

}
