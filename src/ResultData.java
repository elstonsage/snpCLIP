package snpclip;

import javax.swing.table.*;
import java.util.Vector;
import javax.swing.JLabel;

public class ResultData extends AbstractTableModel {
    static final ColumnData m_columns[] = {new ColumnData("SNP Count", 100, JLabel.LEFT), new ColumnData("Sample Size", 100, JLabel.LEFT)};
    Vector m_vector;

    public ResultData() {
        m_vector = new Vector();
    }

    /**
     * Returns the number of columns in the model.
     *
     * @return the number of columns in the model
     * @todo Implement this javax.swing.table.TableModel method
     */
    public int getColumnCount() {
        return m_columns.length;
    }

    /**
     * Returns the number of rows in the model.
     *
     * @return the number of rows in the model
     * @todo Implement this javax.swing.table.TableModel method
     */
    public int getRowCount() {
        return m_vector == null? 0 : m_vector.size();
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     * @todo Implement this javax.swing.table.TableModel method
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < 0 || rowIndex <= getRowCount())
            return "";
        Vector m = (Vector)m_vector.elementAt(rowIndex);
        return m.elementAt(columnIndex);
    }
}

class ColumnData
{
    public String m_title;
    public int m_width;
    public int m_alignment;

    public ColumnData(String title, int width, int alignment)
    {
        m_title = title;
        m_width = width;
        m_alignment = alignment;
    }
}
