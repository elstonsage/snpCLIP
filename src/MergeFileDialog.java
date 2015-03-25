package snpclip;

import javax.swing.ButtonGroup;
import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.BorderLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import java.awt.CardLayout;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
/*
 * FileInfoJDialog.java
 *
 * Created on March 25, 2008, 2:56 PM
 */

/**
 *
 * @author  suna
 */
public class MergeFileDialog extends javax.swing.JDialog {


        // Variables declaration - do not modify
        private javax.swing.JPanel ButtonPanel;
        private javax.swing.JPanel OutputPanel;
        private javax.swing.JButton CancelButton;
        private javax.swing.JPanel CenterPanel;
        private javax.swing.JButton DataFileOpenButton;
        private javax.swing.JLabel DataFilesLabel;
        private javax.swing.JList DataFilesList;
        private javax.swing.JList MapFilesList;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JPanel DelimitersPanel;
        private javax.swing.JPanel InputFilesPanel;
        private javax.swing.JPanel MainPanel;
        private javax.swing.JButton MapFileOpenButton;
        private javax.swing.JLabel OutputFilesLabel;
        private javax.swing.JTextField OutputFilePathTextField;
        private javax.swing.JLabel MapFilesLabel;
        private javax.swing.JButton OKButton;
        private javax.swing.JPanel PedigreedataPanel;
        private javax.swing.JPanel SnpdataPanel;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JRadioButton TabRButton;
        private javax.swing.JRadioButton CommaRButton;
        private javax.swing.JRadioButton SpaceRButton;
        private javax.swing.JRadioButton OtherRButton;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JSpinner SnpSpinner;
        private javax.swing.JTable jTable1;
        private javax.swing.JTextField OtherTextField;
        private javax.swing.JTextField DelTextField;
        private javax.swing.JTextField MissingTextField;

        private javax.swing.JLabel pedigreeLabel;
        private javax.swing.JLabel individualLabel;
        private javax.swing.JSpinner PIdSpinner;
        private javax.swing.JSpinner IdSpinner;

        ButtonGroup bg1 = new ButtonGroup();
        ButtonGroup bg2 = new ButtonGroup();
        int row_limit = 20;
        int col_limit = 20;
        int SNP_Col = 6;

        int Pedigree_Col = 1;
        int Individual_Col = 2;

        Vector column;
        Vector linelist;
        DefaultTableModel dm;

        DefaultListModel datalm;
        DefaultListModel maplm;

        private File[] datafiles;
        private File[] mapfiles;
        //private String mapFilePath;

        private String TAB = "\t";
        private String COMMA = ",";
        private String SAPCE = " ";
        private String FILE_DELIMITER = TAB;

        String SNP_MISSING = ".";
        String SNP_DELIMITER = "/";

        int returnStatus = RET_CANCEL;
        /** A return status code - returned if Cancel button has been pressed */
        public static final int RET_CANCEL = 0;
        /** A return status code - returned if OK button has been pressed */
        public static final int RET_OK = 1;

        SpinnerNumberModel snpModel;
        SpinnerNumberModel snpModel2;
        SpinnerNumberModel snpModel3;

        JPopupMenu ListPopupMenu;
        JMenuItem jMenuItemDelete;

        ListPopupListener ListPopup;
        DeleteKeyListener ListKeyListener;

        Vector MapFiles;
        Vector DataFiles;
        Vector keycolumns;

        String outputFileName="";

         JFileChooser jFileChooser1 = new JFileChooser();
         File workingDir;

    /** Creates new form FileInfoJDialog */
    public MergeFileDialog(java.awt.Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initComponents();

        String init_dit = new String();
        String os_type = System.getProperty("os.name");
        if (os_type.indexOf("Windows") >= 0) {
            init_dit = System.getProperty("user.dir") + System.getProperty("file.separator");
        }
        else {
            init_dit = System.getProperty("user.home") + System.getProperty("file.separator");
        }
        workingDir = new File(init_dit);
        jFileChooser1.setCurrentDirectory(workingDir);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        ListPopupMenu = new JPopupMenu();
        jMenuItemDelete = new JMenuItem("Delete");

        OutputPanel = new javax.swing.JPanel();
        MainPanel = new javax.swing.JPanel();
        ButtonPanel = new javax.swing.JPanel();
        OKButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        CenterPanel = new javax.swing.JPanel();
        DelimitersPanel = new javax.swing.JPanel();
        TabRButton = new javax.swing.JRadioButton();
        CommaRButton = new javax.swing.JRadioButton();
        SpaceRButton = new javax.swing.JRadioButton();
        OtherRButton = new javax.swing.JRadioButton();
        OtherTextField = new javax.swing.JTextField();
        SnpdataPanel = new javax.swing.JPanel();
        PedigreedataPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        SnpSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        InputFilesPanel = new javax.swing.JPanel();
        DataFilesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        DataFilesList = new javax.swing.JList();
        MapFilesList = new javax.swing.JList();
        MapFilesLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        OutputFilesLabel = new javax.swing.JLabel("Output file name");
        OutputFilePathTextField = new javax.swing.JTextField();
        DelTextField = new javax.swing.JTextField();
        MissingTextField = new javax.swing.JTextField();

        DataFileOpenButton = new javax.swing.JButton();
        MapFileOpenButton = new javax.swing.JButton();
        pedigreeLabel = new javax.swing.JLabel();
        individualLabel = new javax.swing.JLabel();
        PIdSpinner = new javax.swing.JSpinner();
        IdSpinner = new javax.swing.JSpinner();

        MainPanel.setLayout(new java.awt.BorderLayout());

        ButtonPanel.setPreferredSize(new java.awt.Dimension(100, 45));

        OKButton.setText("OK");
        OKButton.setPreferredSize(new java.awt.Dimension(60, 23));
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        bg2.add(TabRButton);
        bg2.add(CommaRButton);
        bg2.add(SpaceRButton);
        bg2.add(OtherRButton);

        TabRButton.setFocusPainted(false);
        CommaRButton.setFocusPainted(false);
        SpaceRButton.setFocusPainted(false);
        OtherRButton.setFocusPainted(false);

        javax.swing.GroupLayout ButtonPanelLayout = new javax.swing.GroupLayout(ButtonPanel);
        ButtonPanel.setLayout(ButtonPanelLayout);
        ButtonPanelLayout.setHorizontalGroup(
            ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ButtonPanelLayout.createSequentialGroup()
                .addContainerGap(533, Short.MAX_VALUE)
                .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(CancelButton)
                .addContainerGap())
        );
        ButtonPanelLayout.setVerticalGroup(
            ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        OutputFilePathTextField.setText("MergedSNPFile.txt");
        OutputFilePathTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    public void changedUpdate(DocumentEvent event) {

                        String del = OutputFilePathTextField.getText();

                        if(del.length()<=0)
                            OKButton.setEnabled(false);
                        else
                            OKButton.setEnabled(true);
                    }
                    public void insertUpdate(DocumentEvent event) {
                      changedUpdate(event);
                    }

                    public void removeUpdate(DocumentEvent event) {
                      changedUpdate(event);
                  }
        });

        javax.swing.GroupLayout OutputPanelLayout = new javax.swing.GroupLayout(OutputPanel);
        OutputPanel.setLayout(OutputPanelLayout);
        OutputPanelLayout.setHorizontalGroup(
            OutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OutputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OutputFilesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(OutputFilePathTextField)
                .addContainerGap())
        );
        OutputPanelLayout.setVerticalGroup(
            OutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OutputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OutputFilesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OutputFilePathTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        DelimitersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Delimiters"));

        TabRButton.setSelected(true);
        TabRButton.setSelected(true);
        TabRButton.setText("Tab");
        TabRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TabRButtonActionPerformed();
            }
        });

        CommaRButton.setText("Comma");
        CommaRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommaRButtonActionPerformed();
            }
        });

        SpaceRButton.setText("Space");

        SpaceRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SpaceRButtonActionPerformed();
            }
        });

        OtherRButton.setText("Other");
        OtherRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OtherRButtonActionPerformed();
            }
        });

        OtherTextField.setEditable(false);
        OtherTextField.setMinimumSize(new java.awt.Dimension(40, 20));
        OtherTextField.setPreferredSize(new java.awt.Dimension(40, 20));
        OtherTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(DocumentEvent event) {

                String del = OtherTextField.getText();
                try {
                    FILE_DELIMITER = del;
                    ParseFile(FILE_DELIMITER);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            public void insertUpdate(DocumentEvent event) {
              changedUpdate(event);
            }

            public void removeUpdate(DocumentEvent event) {
              changedUpdate(event);
          }
        });

        javax.swing.GroupLayout DelimitersPanelLayout = new javax.swing.GroupLayout(DelimitersPanel);
        DelimitersPanel.setLayout(DelimitersPanelLayout);
        DelimitersPanelLayout.setHorizontalGroup(
            DelimitersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DelimitersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DelimitersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TabRButton)
                    .addComponent(CommaRButton)
                    .addComponent(SpaceRButton)
                    .addGroup(DelimitersPanelLayout.createSequentialGroup()
                        .addComponent(OtherRButton)
                        .addGap(18, 18, 18)
                        .addComponent(OtherTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)))
                .addContainerGap())
        );
        DelimitersPanelLayout.setVerticalGroup(
            DelimitersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DelimitersPanelLayout.createSequentialGroup()
                .addComponent(TabRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CommaRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SpaceRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DelimitersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OtherRButton)
                    .addComponent(OtherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(5, Short.MAX_VALUE))
        );

        pedigreeLabel.setText("Pedigree Id");
        pedigreeLabel.setPreferredSize(new java.awt.Dimension(60, 20));
        individualLabel.setText("Individual Id");
        individualLabel.setPreferredSize(new java.awt.Dimension(60, 20));

        PIdSpinner.setPreferredSize(new java.awt.Dimension(60, 20));
        PIdSpinner.setValue(1);
        IdSpinner.setPreferredSize(new java.awt.Dimension(60, 20));
        IdSpinner.setValue(2);

        snpModel2 = new SpinnerNumberModel(1, //initial value
                                           1, //min
                                           Integer.MAX_VALUE, //max
                                           1);

        snpModel3 = new SpinnerNumberModel(2, //initial value
                                           1, //min
                                           Integer.MAX_VALUE, //max
                                           1);

        PIdSpinner.setModel(snpModel2);
        IdSpinner.setModel(snpModel3);

        PedigreedataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedigree data"));

        javax.swing.GroupLayout pedigreedataPanelLayout = new javax.swing.GroupLayout(PedigreedataPanel);
        PedigreedataPanel.setLayout(pedigreedataPanelLayout);
        pedigreedataPanelLayout.setHorizontalGroup(
                pedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pedigreedataPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(pedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pedigreedataPanelLayout.createSequentialGroup()
                                              .addComponent(pedigreeLabel)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, 14)
                                              .addComponent(PIdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pedigreedataPanelLayout.createSequentialGroup()
                                              .addComponent(individualLabel)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, 10)
                                              .addComponent(IdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap())
                );
        pedigreedataPanelLayout.setVerticalGroup(
                pedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pedigreedataPanelLayout.createSequentialGroup()
                          .addGroup(pedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pedigreeLabel)
                                    .addComponent(PIdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(pedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(individualLabel)
                                    .addComponent(IdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                          .addContainerGap(40, Short.MAX_VALUE))
                );



        SnpdataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("SNP data"));

        jLabel2.setText("Start SNP data at column");

        SnpSpinner.setPreferredSize(new java.awt.Dimension(60, 20));
        SnpSpinner.setValue(SNP_Col);

        snpModel = new SpinnerNumberModel(SNP_Col, //initial value
                                       1, //min
                                       Integer.MAX_VALUE, //max
                                        1);

        SnpSpinner.setModel(snpModel);


       jLabel3.setText("Allele delimiter");
       jLabel4.setText("Allele Missing");
       DelTextField.setText("");
       DelTextField.setPreferredSize(new java.awt.Dimension(60, 20));
       MissingTextField.setText("");
       MissingTextField.setPreferredSize(new java.awt.Dimension(60, 20));
       DelTextField.setText("/");
       MissingTextField.setText(".");

       javax.swing.GroupLayout SnpdataPanelLayout = new javax.swing.GroupLayout(SnpdataPanel);
      SnpdataPanel.setLayout(SnpdataPanelLayout);
      SnpdataPanelLayout.setHorizontalGroup(
          SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(SnpdataPanelLayout.createSequentialGroup()
              .addContainerGap()
              .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(SnpdataPanelLayout.createSequentialGroup()
                      .addComponent(jLabel2)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                      .addContainerGap()
                      .addComponent(SnpSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(SnpdataPanelLayout.createSequentialGroup()
                      .addComponent(jLabel3)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                      .addContainerGap()
                      .addComponent(DelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(SnpdataPanelLayout.createSequentialGroup()
                      .addComponent(jLabel4)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                      .addComponent(MissingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addContainerGap())
      );
      SnpdataPanelLayout.setVerticalGroup(
          SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(SnpdataPanelLayout.createSequentialGroup()
              .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel2)
                  .addComponent(SnpSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel3)
                  .addComponent(DelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel4)
                  .addComponent(MissingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addContainerGap(30, Short.MAX_VALUE)
              )
                );

        dm = new DefaultTableModel();
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setIntercellSpacing(new Dimension(2, 0));
        jTable1.setRowSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Data preview");
        jLabel1.setForeground(new java.awt.Color(0, 70, 213));
        jLabel5.setText("Note: not all of the data may be displayed.");

        InputFilesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Files"));

        DataFilesLabel.setText("Data Files");
        jScrollPane2.setViewportView(DataFilesList);
        jScrollPane3.setViewportView(MapFilesList);

        datalm = new DefaultListModel();
        maplm = new DefaultListModel();

        ListPopup = new ListPopupListener();
        ListKeyListener = new DeleteKeyListener();

        DataFilesList.setModel(datalm);
        DataFilesList.addMouseListener(ListPopup);
        DataFilesList.addKeyListener(ListKeyListener);

        ListPopupMenu.add(jMenuItemDelete);

        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int index = DataFilesList.getSelectedIndex();
                datalm.remove(index);
            }
        });

        MapFilesLabel.setText("Map File");

        //MapFilePathTextField.setText("");
        MapFilesList.setModel(maplm);

        DataFileOpenButton.setText("...");
        DataFileOpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataFileOpenButtonActionPerformed();
            }
        });

        MapFileOpenButton.setText("...");
        MapFileOpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               MapFileOpenButtonActionPerformed();
            }
        });



        javax.swing.GroupLayout InputFilesPanelLayout = new javax.swing.GroupLayout(InputFilesPanel);
        InputFilesPanel.setLayout(InputFilesPanelLayout);
        InputFilesPanelLayout.setHorizontalGroup(
            InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputFilesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DataFilesLabel)
                    .addComponent(MapFilesLabel))
                .addGap(30, 30, 30)
                .addGroup(InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DataFileOpenButton)
                    .addComponent(MapFileOpenButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InputFilesPanelLayout.setVerticalGroup(
            InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputFilesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DataFileOpenButton)
                    .addComponent(DataFilesLabel)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGap(10, 10, 10)
                .addGroup(InputFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MapFilesLabel)
                    .addComponent(MapFileOpenButton)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

       javax.swing.GroupLayout CenterPanelLayout = new javax.swing.GroupLayout(CenterPanel);
       CenterPanel.setLayout(CenterPanelLayout);
       CenterPanelLayout.setHorizontalGroup(
           CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(CenterPanelLayout.createSequentialGroup()
               .addContainerGap()
               .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                   .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                   .addGroup(CenterPanelLayout.createSequentialGroup()
                       .addComponent(jLabel1)
                       .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                       .addComponent(jLabel5))
                   .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                   .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                       .addComponent(InputFilesPanel, javax.swing.GroupLayout.Alignment.LEADING, 0, 537, Short.MAX_VALUE)
                       .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CenterPanelLayout.createSequentialGroup()
                           .addComponent(PedigreedataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                           .addComponent(DelimitersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                           .addComponent(SnpdataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
               .addContainerGap())
       );
       CenterPanelLayout.setVerticalGroup(
           CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CenterPanelLayout.createSequentialGroup()
               .addContainerGap()
               .addComponent(InputFilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                   .addComponent(DelimitersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addComponent(PedigreedataPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                   .addComponent(SnpdataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                   .addComponent(jLabel1)
                   .addComponent(jLabel5))
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, Short.MAX_VALUE)
               .addContainerGap())
        );

        MainPanel.add(CenterPanel, java.awt.BorderLayout.CENTER);
        MainPanel.add(OutputPanel, java.awt.BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(600,550));
        this.setMinimumSize(new Dimension(600,550));

        //getContentPane().setLayout(new BorderLayout());
        //getContentPane().add(MainPanel, java.awt.BorderLayout.CENTER);
        //getContentPane().add(ButtonPanel, java.awt.BorderLayout.SOUTH);

        javax.swing.JPanel temp = new javax.swing.JPanel();
        temp.setLayout(new BorderLayout());
        temp.add(MainPanel, java.awt.BorderLayout.CENTER);
        temp.add(ButtonPanel, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(temp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(temp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void doClose(int retStatus) {
        SNP_Col = Integer.parseInt(SnpSpinner.getValue().toString());
        SNP_DELIMITER = DelTextField.getText();
        SNP_MISSING = MissingTextField.getText();
        outputFileName =  OutputFilePathTextField.getText().trim();

        Pedigree_Col = Integer.parseInt(PIdSpinner.getValue().toString());
        Individual_Col = Integer.parseInt(IdSpinner.getValue().toString());

        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_CANCEL);
    }

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MapFiles = new Vector();
        DataFiles = new Vector();
        for(int i=0;i<maplm.size();i++)
        {
            MapFiles.add(maplm.getElementAt(i).toString());
        }

        for(int i=0;i<datalm.size();i++)
        {
            DataFiles.add(datalm.getElementAt(i).toString());
        }

        keycolumns = new Vector();
        keycolumns.add(PIdSpinner.getValue().toString());
        keycolumns.add(IdSpinner.getValue().toString());

        doClose(RET_OK);
}

    private void DataFileOpenButtonActionPerformed() {


        jFileChooser1.setMultiSelectionEnabled(true);

        if (jFileChooser1.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
            datafiles = jFileChooser1.getSelectedFiles();
            workingDir = datafiles[0];
            jFileChooser1.setCurrentDirectory(workingDir);
            try {
                ParseFile("\t");

            } catch (Exception e) {
                e.printStackTrace();
            }

            for(int i=0;i<datafiles.length;i++)
            {
                datalm.addElement(datafiles[i].getAbsolutePath());
            }
        }
    }

    private void MapFileOpenButtonActionPerformed()
    {
       jFileChooser1.setMultiSelectionEnabled(true);

       if (jFileChooser1.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
           mapfiles = jFileChooser1.getSelectedFiles();

           workingDir = mapfiles[0];
           jFileChooser1.setCurrentDirectory(workingDir);

            for(int i=0;i<mapfiles.length;i++)
            {
                maplm.addElement(mapfiles[i].getAbsolutePath());
            }

       }
    }

    public String getDataFileDELIMITER()
    {
        return FILE_DELIMITER;
    }

    public String GetSnpDELIMITER()
    {
        return SNP_DELIMITER;
    }

    public String GetSnpMISSING()
    {
        return SNP_MISSING;
    }

    public int GetSnpColumn()
    {
        return SNP_Col;
    }

    public Vector GetMapFiles()
    {
        return MapFiles;
    }

    public Vector GetDataFiles()
    {
        return DataFiles;
    }

    public Vector GetKeyColumns()
    {
        return keycolumns;
    }

    public String GetOutputFileName()
    {
        return outputFileName;
    }

/*     public String[] getDataFiles()
    {
        String[] lists = new String[datalm.size()];

        for(int i=0;i<datalm.size();i++)
            {
                lists[i] = datalm.get(i).toString();
            }

        return lists;
    }

    public int getNumDataFiles()
    {
        return datalm.size();
    }

   public String[] getMapFiles()
    {
        String[] lists = new String[maplm.size()];

        for(int i=0;i<maplm.size();i++)
            {
                lists[i] = maplm.get(i).toString();
            }

        return lists;
    }

    public int getNumMapFiles()
    {
        return maplm.size();
    }*/


    public void ParseFile(String delimiter) throws Exception
    {
        String[] t = null;
        Vector eachlinecontents = null;
        column = new Vector();
        linelist = new Vector();

        int i = 0;

        FileReader fr = new FileReader(datafiles[0].toString());
        BufferedReader br = new BufferedReader(fr);
        String aLine = new String();

        int col_size = 0;
        while ((aLine = br.readLine()) != null && aLine.trim().length() > 0) {

            if (i >= row_limit)
                break;

            t = aLine.split(delimiter, col_limit + 1);

            if (i == 0) {
                for (int ti = 0; ti < t.length; ti++) {
                    if (ti >= col_limit)
                        break;
                    column.add(t[ti]);
                }

            } else if (i > 0) {
                eachlinecontents = new Vector();

                for (int ti = 0; ti < t.length; ti++) {
                    if (ti >= col_limit)
                        break;
                    eachlinecontents.add(t[ti]);
                }

                linelist.add(eachlinecontents);
                eachlinecontents = null;
            }
            i++;
        }

        linelist.trimToSize();

        set_table_data(linelist, column);
     }

    private void set_table_data(Vector data, Vector col)
    {
      dm.setDataVector(linelist, column);
      jTable1.setModel(dm);
    }

    //Tab
    private void TabRButtonActionPerformed() {
        try {
            FILE_DELIMITER = TAB;
            ParseFile(TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Comma
    private void CommaRButtonActionPerformed() {
        try {
            FILE_DELIMITER = COMMA;
            ParseFile(COMMA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SAPCE
    private void SpaceRButtonActionPerformed() {
        try {
            FILE_DELIMITER = SAPCE;
            ParseFile(SAPCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Other
    private void OtherRButtonActionPerformed() {
        OtherTextField.requestFocus(OtherRButton.isSelected());
        OtherTextField.setEditable(OtherRButton.isSelected());
    }


  class MyTable extends JTable {
      public String getToolTipText(MouseEvent evt) {
        if (columnAtPoint(evt.getPoint()) == -1)
          return null;
        return Integer.toString(columnAtPoint(evt.getPoint())+1);
    }
  }

  class ListPopupListener extends MouseAdapter {

      public void mousePressed(MouseEvent e) {
          maybeShowPopup(e);
      }

      public void mouseReleased(MouseEvent e) {
          maybeShowPopup(e);
      }

      private void maybeShowPopup(MouseEvent e) {
          if (DataFilesList.getSelectedValue() != null && (e.isPopupTrigger())) {
              ListPopupMenu.show(e.getComponent(), e.getX(), e.getY());
          }
      }
  }

  class DeleteKeyListener extends KeyAdapter {
      public void keyTyped(KeyEvent e) {
          if (e.getKeyChar() == KeyEvent.VK_DELETE) {


              int index = DataFilesList.getSelectedIndex();
              if (index >= 0)
                  datalm.remove(index);
          }
      }
    }

}
