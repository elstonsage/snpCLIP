package snpclip;

import javax.swing.table.AbstractTableModel;
import javax.swing.JLabel;
import java.util.Vector;

class MyTableModel extends AbstractTableModel {

    public ColumnData columnNames[] = {
      new ColumnData( "", 10, JLabel.CENTER ),
      new ColumnData( "ID", 10, JLabel.CENTER ),
      new ColumnData( "From", 80, JLabel.LEFT ),
      new ColumnData( "To", 80, JLabel.LEFT ),
      new ColumnData( "# of SNPs", 80, JLabel.CENTER ),
      new ColumnData( "Genetic Length", 120, JLabel.CENTER ),
      new ColumnData( "Case vs Control", 150, JLabel.LEFT ),
      new ColumnData( "Missing Individuals (PED:ID)", 150, JLabel.LEFT ),
      };

    public Vector data = new Vector();

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col].m_title;
    }

    public Object getValueAt(int row, int col) {
        Position p = (Position)data.get(row);
        switch(col)
        {
        case 0 :
            return p.getSelectedValue();
        case 1 :
            return p.getSelectedID();
        case 2 :
            return p.getStartName();
        case 3 :
            return p.getEndName();
        case 4 :
            return p.getLength();
        case 5 :
            return p.getgeneticLength();
        case 6 :
            return p.getSnpFlag();
        case 7 :
            return  p.getCaseMissingList()+"\n"+p.getControlMissingList();
        default :
            return "";
        }
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        Position p = (Position)data.get(row);
        p.setSelectedValue((Boolean)value);
    }
    }
