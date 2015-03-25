package snpclip;
/*
 * Copyright (C) 2006 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;

/**
 * ListCellRenderer for the PasswordEntrys.
 *
 * @author sky
 */
final class EntryListCellRenderer extends JPanel implements TableCellRenderer {
    private int LIST_CELL_WIDTH=10;

    private JCheckBox optionCheckbox;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel lengthLabel;

    private SnipVisualizer caseStrengthBar;
    private SnipVisualizer controlStrengthBar;

    private JLabel caseMissingLabel;
    private JLabel controlMissingLabel;

    EntryListCellRenderer(int LIST_CELL_WIDTH) {
        optionCheckbox = new JCheckBox();
        fromLabel = new JLabel(" ");
        toLabel = new JLabel(" ");
        lengthLabel = new JLabel(" ");

        caseStrengthBar = new SnipVisualizer(LIST_CELL_WIDTH);
        caseStrengthBar.setOpaque(true);
        caseStrengthBar.setBackground(Color.WHITE);

        controlStrengthBar = new SnipVisualizer(LIST_CELL_WIDTH);
        controlStrengthBar.setOpaque(true);
        controlStrengthBar.setBackground(Color.WHITE);

        caseMissingLabel = new JLabel(" ");
        controlMissingLabel = new JLabel(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addComponent(optionCheckbox, 30, 30, 30)
                          .addComponent(fromLabel, 100, 100, 100)
                          .addComponent(toLabel, 110, 110, 110)
                          .addComponent(lengthLabel, 70, 70, 70)
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                              .addComponent(caseStrengthBar, 110, 110, Short.MAX_VALUE)
                                              .addContainerGap(30,30)
                                              .addComponent(caseMissingLabel, 110, 110, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                              .addComponent(controlStrengthBar, 110, 110, Short.MAX_VALUE)
                                              .addContainerGap(30,30)
                                              .addComponent(controlMissingLabel, 110, 110, Short.MAX_VALUE)))
                ));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(optionCheckbox)
                                    .addComponent(lengthLabel)
                                    .addComponent(fromLabel)
                                    .addComponent(toLabel)
                                    .addComponent(caseStrengthBar)
                                    .addComponent(caseMissingLabel))
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(controlStrengthBar)
                                    .addComponent(controlMissingLabel))
                ));


        layout.linkSize(SwingConstants.VERTICAL, optionCheckbox, fromLabel, toLabel, lengthLabel, caseStrengthBar, controlStrengthBar, caseMissingLabel, controlMissingLabel);

        setOpaque(true);
    }

    public void setLIST_CELL_WIDTH(int width)
    {
            LIST_CELL_WIDTH = width;
            caseStrengthBar.setIndicatorWidth(LIST_CELL_WIDTH);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Position entry = (Position)value;
        String from = entry.getStartName();
        String to = entry.getEndName();
        String length = Integer.toString(entry.getLength());
        boolean selected = entry.getSelectedValue();

        caseStrengthBar.setBasicInfo(entry.getLength(), entry.getCaseSnpFlag());
        controlStrengthBar.setBasicInfo(entry.getLength(), entry.getControlSnpFlag());

        optionCheckbox.setSelected(selected);

        fromLabel.setText(from);
        toLabel.setText(to);
        lengthLabel.setText(length);

        caseMissingLabel.setText(entry.getCaseMissingList());
        controlMissingLabel.setText(entry.getControlMissingList());

        if (isSelected) {

            adjustColors(table.getSelectionBackground(),
                    table.getSelectionForeground(), this, optionCheckbox, fromLabel, toLabel, lengthLabel, caseStrengthBar, controlStrengthBar, caseMissingLabel, controlMissingLabel);
        } else {
            adjustColors(table.getBackground(),
                    table.getForeground(), this, optionCheckbox, fromLabel, toLabel, lengthLabel, caseStrengthBar, controlStrengthBar, caseMissingLabel, controlMissingLabel);
        }
        return this;
    }

    private void adjustColors(Color bg, Color fg, Component...components) {
        for (Component c : components) {
            c.setForeground(fg);
            c.setBackground(bg);
        }
    }
}
