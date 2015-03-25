package snpclip;

import javax.swing.JPanel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;

class MissingMugsRenderer extends JPanel implements TableCellRenderer{
    private final GridLayout gl = new GridLayout(2,1,1,1);

        JLabel caseMissing;
        JLabel controlMissing;

        public MissingMugsRenderer() {
            super();
        setLayout(gl);

        this.setBackground(Color.WHITE);

        caseMissing = new JLabel();
        controlMissing = new JLabel();
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();
            String Missing = value.toString();

            String[] MissingList = Missing.split("\n", 2);

            caseMissing.setText(MissingList[0]);
            add(caseMissing);

            if(MissingList[1] != null)
            {
              controlMissing.setText(MissingList[1]);
              add(controlMissing);
            }

            if (isSelected) {
                this.setForeground(table.getSelectionForeground());
                this.setBackground(table.getSelectionBackground());

                caseMissing.setForeground(table.getSelectionForeground());
                caseMissing.setBackground(table.getSelectionBackground());
                controlMissing.setForeground(table.getSelectionForeground());
                controlMissing.setBackground(table.getSelectionBackground());

            } else {
                this.setForeground(table.getForeground());
                this.setBackground(table.getBackground());

                caseMissing.setForeground(table.getForeground());
                caseMissing.setBackground(table.getBackground());
                controlMissing.setForeground(table.getForeground());
                controlMissing.setBackground(table.getBackground());
            }

            return this;
        }

}
