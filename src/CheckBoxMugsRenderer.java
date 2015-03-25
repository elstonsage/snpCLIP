package snpclip;

import javax.swing.JPanel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

class CheckBoxMugsRenderer extends JPanel implements TableCellRenderer{
    private final GridLayout gl = new GridLayout(2,1,1,1);

        JCheckBox caseMissing;
        JLabel controlMissing;

        public CheckBoxMugsRenderer() {
            super();
        setLayout(gl);

        this.setBackground(Color.WHITE);

        caseMissing = new JCheckBox("");
        controlMissing = new JLabel("");

        caseMissing.setPreferredSize(new Dimension(20,20));
        controlMissing.setPreferredSize(new Dimension(20,20));

        add(caseMissing);
         add(controlMissing);
    }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                this.setForeground(table.getSelectionForeground());
                this.setBackground(table.getSelectionBackground());

                super.setBackground(table.getSelectionBackground());


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

           boolean selected = ((Boolean) value).booleanValue();
           caseMissing.setSelected(selected);

            MyTableModel model = (MyTableModel)table.getModel();
            Position entry= (Position)(model.data.get(row));
            entry.setSelectedValue(selected);

            repaint();
            this.updateUI();
            return this;
        }

}
