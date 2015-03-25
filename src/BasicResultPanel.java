package snpclip;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class BasicResultPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    DefaultTableModel dm = new DefaultTableModel();
    Vector columnname = new Vector();
    Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    Border border2 = BorderFactory.createEmptyBorder(8, 2, 2, 2);
    String liststring[] = {"SNP Count", "Sample Size"};
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JTable jTable1 = new JTable();
    TitledBorder titledBorder1 = new TitledBorder("Criteria");
    Border border3 = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.pink);
    TitledBorder border4 = new TitledBorder(border3, "Criteria");
    BorderLayout borderLayout3 = new BorderLayout();
    JTextPane jTextPane1 = new JTextPane();

    public BasicResultPanel() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jPanel1.setBorder(border4);
        jPanel1.setLayout(borderLayout3);
        jTextPane1.setBackground(SystemColor.control);
        jTextPane1.setMargin(new Insets(5, 15, 5, 15));
        columnname.add("1");
        columnname.add("2");

        jPanel1.setPreferredSize(new Dimension(400,200));
        jPanel2.setPreferredSize(new Dimension(400,100));

        jPanel2.setLayout(borderLayout2);
        jPanel2.setBorder(border2);
        jPanel2.add(jTable1, BorderLayout.CENTER);

        this.setLayout(borderLayout1);
        this.setBorder(border);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(jTextPane1, java.awt.BorderLayout.CENTER);
        this.add(jPanel2, java.awt.BorderLayout.CENTER);
        jTable1.setModel(dm);
    }

    void setResultTable(Vector data)
    {
        dm.setDataVector(data, columnname);
    }
}
