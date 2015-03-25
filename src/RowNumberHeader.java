package snpclip;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.*;

public class RowNumberHeader extends JTable{

    protected JTable mainTable;

    public RowNumberHeader(JTable table) {
        super();
        mainTable = table;
        setModel(new RowNumberTableModel());
        setRowSelectionAllowed(true);
        JComponent renderer = (JComponent)getDefaultRenderer(Object.class);
        LookAndFeel.installColorsAndFont(renderer, "TableHeader.background",
                                         "TableHeader.foreground","TableHeader.font");

    setPreferredScrollableViewportSize(new Dimension(30,20));
    }

    public int getRowHeight(int row)
    {
        return mainTable.getRowHeight();
    }

    class RowNumberTableModel extends AbstractTableModel{

        public int getRowCount()
        {
            return mainTable.getModel().getRowCount();
        }

        public int getColumnCount()
        {
            return 1;
        }

        public Object getValueAt(int row, int column)
        {
            return "";
        }
    }
}
