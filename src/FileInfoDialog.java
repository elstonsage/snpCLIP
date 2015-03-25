package snpclip;
import javax.swing.ButtonGroup;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

public class FileInfoDialog extends javax.swing.JDialog {
    int row_limit = 20;
    int col_limit = 20;
    int SNP_Col = 6;
    int SNP_Row = 8;
    int Pedigree_Col = 1;
    int Individual_Col = 2;
    Vector column;
    Vector linelist;
    DefaultTableModel dm;
    private String FilePath;
    private String TAB = "\t";
    private String COMMA = ",";
    private String SAPCE = " ";
    private int FileType = 0;
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
    /** Creates new form FileInfoJDialog */
    public FileInfoDialog(java.awt.Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initComponents();
                ASWRButton.setEnabled(false);
        CEURButton.setEnabled(false);
        CHBRButton.setEnabled(false);
        CHDRButton.setEnabled(false);
        GIHRButton.setEnabled(false);
        JPTRButton.setEnabled(false);
        LWKRButton.setEnabled(false);
        MEXRButton.setEnabled(false);
        MKKRButton.setEnabled(false);
        TSIRButton.setEnabled(false);
        YRIRButton.setEnabled(false);
        DelTextField.setText("/");
        MissingTextField.setText(".");
    }
        public void setFilePath(String value)
    {
        FilePath = value;
    }

    public int getFileType()
    {
        return FileType;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        dm = new DefaultTableModel();
        bg1 = new javax.swing.ButtonGroup();
        bg1.add(SageRButton);
        bg1.add(HapmapRButton);
        bg1.add(AffymetrixRButton);
        bg2 = new javax.swing.ButtonGroup();
        bg2.add(TabRButton);
        bg2.add(CommaRButton);
        bg2.add(SpaceRButton);
        bg2.add(OtherRButton);
        bg3 = new javax.swing.ButtonGroup();
        bg3.add(ASWRButton);
        bg3.add(CHDRButton);
        bg3.add(CHBRButton);
        bg3.add(CEURButton);
        bg3.add(JPTRButton);
        bg3.add(GIHRButton);
        bg3.add(LWKRButton);
        bg3.add(MKKRButton);
        bg3.add(MEXRButton);
        bg3.add(TSIRButton);
        bg3.add(YRIRButton);
        MainPanel = new javax.swing.JPanel();
        CenterPanel = new javax.swing.JPanel();
        FileFormatPanel = new javax.swing.JPanel();
        SageRButton = new javax.swing.JRadioButton();
        HapmapRButton = new javax.swing.JRadioButton();
        AffymetrixRButton = new javax.swing.JRadioButton();
        DelimitersPanel = new javax.swing.JPanel();
        TabRButton = new javax.swing.JRadioButton();
        CommaRButton = new javax.swing.JRadioButton();
        SpaceRButton = new javax.swing.JRadioButton();
        OtherRButton = new javax.swing.JRadioButton();
        OtherTextField = new javax.swing.JTextField();
        SnpdataPanel = new javax.swing.JPanel();
        SnpColumnLabel = new javax.swing.JLabel();
        SnpSpinner = new javax.swing.JSpinner();
        AlleleDelLabel = new javax.swing.JLabel();
        DelTextField = new javax.swing.JTextField();
        AlleleMissingLabel = new javax.swing.JLabel();
        MissingTextField = new javax.swing.JTextField();
        SnpSpinnerRow = new javax.swing.JSpinner();
        SnpColumnLabel1 = new javax.swing.JLabel();
        DataPreviewLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        NoteLabel = new javax.swing.JLabel();
        PedigreedataPanel = new javax.swing.JPanel();
        PedigreeIdLabel = new javax.swing.JLabel();
        PIdSpinner = new javax.swing.JSpinner();
        IndividualIdLabel = new javax.swing.JLabel();
        IdSpinner = new javax.swing.JSpinner();
        BottomPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        HapMapPanel = new javax.swing.JPanel();
        ASWRButton = new javax.swing.JRadioButton();
        CHDRButton = new javax.swing.JRadioButton();
        CHBRButton = new javax.swing.JRadioButton();
        CEURButton = new javax.swing.JRadioButton();
        JPTRButton = new javax.swing.JRadioButton();
        GIHRButton = new javax.swing.JRadioButton();
        LWKRButton = new javax.swing.JRadioButton();
        MKKRButton = new javax.swing.JRadioButton();
        MEXRButton = new javax.swing.JRadioButton();
        TSIRButton = new javax.swing.JRadioButton();
        YRIRButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        MainPanel.setLayout(new java.awt.BorderLayout());

        FileFormatPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("File Format"));

        bg1.add(SageRButton);
        SageRButton.setSelected(true);
        SageRButton.setText("S.A.G.E.");
        SageRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SageRButtonActionPerformed(evt);
            }
        });

        bg1.add(HapmapRButton);
        HapmapRButton.setText("Hapmap");
        HapmapRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapmapRButtonActionPerformed(evt);
            }
        });

        bg1.add(AffymetrixRButton);
        AffymetrixRButton.setText("SNPs by Row");
        AffymetrixRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AffymetrixRButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FileFormatPanelLayout = new javax.swing.GroupLayout(FileFormatPanel);
        FileFormatPanel.setLayout(FileFormatPanelLayout);
        FileFormatPanelLayout.setHorizontalGroup(
            FileFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FileFormatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FileFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SageRButton)
                    .addComponent(HapmapRButton)
                    .addComponent(AffymetrixRButton))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        FileFormatPanelLayout.setVerticalGroup(
            FileFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FileFormatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SageRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HapmapRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AffymetrixRButton)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        DelimitersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Delimiters"));

        bg2.add(TabRButton);
        TabRButton.setSelected(true);
        TabRButton.setText("Tab");
        TabRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TabRButtonActionPerformed(evt);
            }
        });

        bg2.add(CommaRButton);
        CommaRButton.setText("Comma");
        CommaRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CommaRButtonActionPerformed(evt);
            }
        });

        bg2.add(SpaceRButton);
        SpaceRButton.setText("Space");
        SpaceRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SpaceRButtonActionPerformed(evt);
            }
        });

        bg2.add(OtherRButton);
        OtherRButton.setText("Other");
        OtherRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OtherRButtonActionPerformed(evt);
            }
        });

        OtherTextField.setEditable(false);
        OtherTextField.setMinimumSize(new java.awt.Dimension(40, 20));
        OtherTextField.setPreferredSize(new java.awt.Dimension(40, 20));

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
                .addContainerGap(39, Short.MAX_VALUE))
        );

        SnpdataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("SNP data"));

        SnpColumnLabel.setText("Start SNP data at column");

        SnpSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        SnpSpinner.setValue(0);

        AlleleDelLabel.setText("Allele delimiter");

        AlleleMissingLabel.setText("Allele Missing");

        SnpSpinnerRow.setPreferredSize(new java.awt.Dimension(50, 20));
        SnpSpinnerRow.setValue(0);

        SnpColumnLabel1.setText("Start SNP data at row");

        javax.swing.GroupLayout SnpdataPanelLayout = new javax.swing.GroupLayout(SnpdataPanel);
        SnpdataPanel.setLayout(SnpdataPanelLayout);
        SnpdataPanelLayout.setHorizontalGroup(
            SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SnpdataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SnpColumnLabel1)
                    .addComponent(AlleleDelLabel)
                    .addComponent(AlleleMissingLabel)
                    .addComponent(SnpColumnLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SnpSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(MissingTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(DelTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(SnpSpinnerRow, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addContainerGap())
        );
        SnpdataPanelLayout.setVerticalGroup(
            SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SnpdataPanelLayout.createSequentialGroup()
                .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SnpColumnLabel)
                    .addComponent(SnpSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SnpSpinnerRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SnpColumnLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AlleleDelLabel)
                    .addComponent(DelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SnpdataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AlleleMissingLabel)
                    .addComponent(MissingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        DataPreviewLabel.setText("Data preview");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        NoteLabel.setText("jLabel1");

        PedigreedataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedigree Data"));

        PedigreeIdLabel.setText("Pedigree Id");

        PIdSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        PIdSpinner.setValue(1);

        IndividualIdLabel.setText("Individual Id");

        IdSpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        IdSpinner.setValue(2);

        javax.swing.GroupLayout PedigreedataPanelLayout = new javax.swing.GroupLayout(PedigreedataPanel);
        PedigreedataPanel.setLayout(PedigreedataPanelLayout);
        PedigreedataPanelLayout.setHorizontalGroup(
            PedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PedigreedataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PedigreeIdLabel)
                    .addComponent(IndividualIdLabel))
                .addGap(49, 49, 49)
                .addGroup(PedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PIdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        PedigreedataPanelLayout.setVerticalGroup(
            PedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PedigreedataPanelLayout.createSequentialGroup()
                .addGroup(PedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PedigreeIdLabel)
                    .addComponent(PIdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PedigreedataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IndividualIdLabel)
                    .addComponent(IdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        BottomPanel.setPreferredSize(new java.awt.Dimension(100, 45));

        okButton.setText("OK");
        okButton.setPreferredSize(new java.awt.Dimension(60, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BottomPanelLayout = new javax.swing.GroupLayout(BottomPanel);
        BottomPanel.setLayout(BottomPanelLayout);
        BottomPanelLayout.setHorizontalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BottomPanelLayout.createSequentialGroup()
                .addContainerGap(763, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(cancelButton)
                .addContainerGap())
        );
        BottomPanelLayout.setVerticalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        HapMapPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("HapMap Population"));

        bg3.add(ASWRButton);
        ASWRButton.setText("ASW");
        ASWRButton.setEnabled(false);
        ASWRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ASWRButtonActionPerformed(evt);
            }
        });

        bg3.add(CHDRButton);
        CHDRButton.setText("CHD");
        CHDRButton.setEnabled(false);
        CHDRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CHDRButtonActionPerformed(evt);
            }
        });

        bg3.add(CHBRButton);
        CHBRButton.setText("CHB");
        CHBRButton.setEnabled(false);
        CHBRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CHBRButtonActionPerformed(evt);
            }
        });

        bg3.add(CEURButton);
        CEURButton.setText("CEU");
        CEURButton.setEnabled(false);
        CEURButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CEURButtonActionPerformed(evt);
            }
        });

        bg3.add(JPTRButton);
        JPTRButton.setText("JPT");
        JPTRButton.setEnabled(false);
        JPTRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JPTRButtonActionPerformed(evt);
            }
        });

        bg3.add(GIHRButton);
        GIHRButton.setText("GIH");
        GIHRButton.setEnabled(false);
        GIHRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GIHRButtonActionPerformed(evt);
            }
        });

        bg3.add(LWKRButton);
        LWKRButton.setText("LWK");
        LWKRButton.setEnabled(false);
        LWKRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LWKRButtonActionPerformed(evt);
            }
        });

        bg3.add(MKKRButton);
        MKKRButton.setText("MKK");
        MKKRButton.setEnabled(false);
        MKKRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MKKRButtonActionPerformed(evt);
            }
        });

        bg3.add(MEXRButton);
        MEXRButton.setText("MEX");
        MEXRButton.setEnabled(false);
        MEXRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MEXRButtonActionPerformed(evt);
            }
        });

        bg3.add(TSIRButton);
        TSIRButton.setText("TSI");
        TSIRButton.setEnabled(false);
        TSIRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TSIRButtonActionPerformed(evt);
            }
        });

        bg3.add(YRIRButton);
        YRIRButton.setText("YRI");
        YRIRButton.setEnabled(false);
        YRIRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YRIRButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HapMapPanelLayout = new javax.swing.GroupLayout(HapMapPanel);
        HapMapPanel.setLayout(HapMapPanelLayout);
        HapMapPanelLayout.setHorizontalGroup(
            HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HapMapPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HapMapPanelLayout.createSequentialGroup()
                        .addComponent(CHBRButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MEXRButton))
                    .addGroup(HapMapPanelLayout.createSequentialGroup()
                        .addComponent(ASWRButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JPTRButton)
                        .addGap(2, 2, 2)
                        .addComponent(YRIRButton))
                    .addGroup(HapMapPanelLayout.createSequentialGroup()
                        .addComponent(CHDRButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MKKRButton))
                    .addGroup(HapMapPanelLayout.createSequentialGroup()
                        .addComponent(GIHRButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TSIRButton))
                    .addGroup(HapMapPanelLayout.createSequentialGroup()
                        .addComponent(CEURButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LWKRButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HapMapPanelLayout.setVerticalGroup(
            HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HapMapPanelLayout.createSequentialGroup()
                .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ASWRButton)
                    .addComponent(YRIRButton)
                    .addComponent(JPTRButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CEURButton)
                    .addComponent(LWKRButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HapMapPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CHDRButton)
                            .addComponent(MKKRButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GIHRButton)
                            .addComponent(TSIRButton)))
                    .addGroup(HapMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CHBRButton)
                        .addComponent(MEXRButton)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CenterPanelLayout = new javax.swing.GroupLayout(CenterPanel);
        CenterPanel.setLayout(CenterPanelLayout);
        CenterPanelLayout.setHorizontalGroup(
            CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CenterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CenterPanelLayout.createSequentialGroup()
                        .addComponent(DataPreviewLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NoteLabel))
                    .addComponent(BottomPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CenterPanelLayout.createSequentialGroup()
                            .addComponent(FileFormatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(DelimitersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(PedigreedataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(HapMapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SnpdataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        CenterPanelLayout.setVerticalGroup(
            CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CenterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SnpdataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HapMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PedigreedataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DelimitersPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FileFormatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DataPreviewLabel)
                    .addComponent(NoteLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainPanel.add(CenterPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_CANCEL);
}

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_OK);
}

    private void ASWRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        ASWRButton.setSelected(true);
       okButton.setEnabled(true);
}

    private void MEXRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MEXRButton.setSelected(true);
       okButton.setEnabled(true);
}

    private void TabRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        TabRButtonActionPerformed();
    }

    private void AffymetrixRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        AffymetrixRButtonActionPerformed();
    }

    private void SageRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        SageRButtonActionPerformed();
    }

    private void HapmapRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        HapmapRButtonActionPerformed();
    }

    private void CommaRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CommaRButtonActionPerformed();
    }

    private void SpaceRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        SpaceRButtonActionPerformed();
    }

    private void OtherRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        OtherRButtonActionPerformed();
    }

    private void CEURButtonActionPerformed(java.awt.event.ActionEvent evt) {
       CEURButton.setSelected(true);
       okButton.setEnabled(true);
    }

    private void CHBRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CHBRButton.setSelected(true);
        okButton.setEnabled(true);
    }

    private void CHDRButtonActionPerformed(java.awt.event.ActionEvent evt) {
       CHDRButton.setSelected(true);
       okButton.setEnabled(true);
    }

    private void GIHRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        GIHRButton.setSelected(true);
        okButton.setEnabled(true);
    }

    private void JPTRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JPTRButton.setSelected(true);
        okButton.setEnabled(true);
    }

    private void LWKRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LWKRButton.setSelected(true);
        okButton.setEnabled(true);
    }

    private void MKKRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MKKRButton.setSelected(true);
        okButton.setEnabled(true);
    }

    private void TSIRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        TSIRButton.setSelected(true);
        okButton.setEnabled(true);
    }

    private void YRIRButtonActionPerformed(java.awt.event.ActionEvent evt) {
        YRIRButton.setSelected(true);
        okButton.setEnabled(true);
    }
    private void doClose(int retStatus) {
        SNP_Col = Integer.parseInt(SnpSpinner.getValue().toString());
        SNP_Row = Integer.parseInt(SnpSpinnerRow.getValue().toString());
        SNP_DELIMITER = DelTextField.getText();
        SNP_MISSING= MissingTextField.getText();

        Pedigree_Col = Integer.parseInt(PIdSpinner.getValue().toString());
        Individual_Col = Integer.parseInt(IdSpinner.getValue().toString());

        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    private void setDelimitersEnabled(boolean val)
    {
        TabRButton.setEnabled(val);
        CommaRButton.setEnabled(val);
        SpaceRButton.setEnabled(val);
        OtherRButton.setEnabled(val);
    }
  private void set_table_data(Vector data, Vector col)
    {
      dm.setDataVector(linelist, column);
      jTable1.setModel(dm);
    }

    //SAGE
    private void SageRButtonActionPerformed() {
                okButton.setEnabled(true);
        SnpSpinnerRow.setEnabled(false);
        SnpSpinnerRow.setValue(1);
        PedigreeIdLabel.setEnabled(true);
        IndividualIdLabel.setEnabled(true);
        AlleleDelLabel.setEnabled(true);
        setDelimitersEnabled(true);
        TabRButton.setSelected(true);
        PIdSpinner.setEnabled(true);
        IdSpinner.setEnabled(true);

        SnpColumnLabel.setEnabled(true);
        SnpSpinner.setEnabled(true);
        DelTextField.setText("/");
        MissingTextField.setText(".");
        DelTextField.setEnabled(true);
        MissingTextField.setEnabled(true);
        ASWRButton.setEnabled(false);
        CEURButton.setEnabled(false);
        CHBRButton.setEnabled(false);
        CHDRButton.setEnabled(false);
        GIHRButton.setEnabled(false);
        JPTRButton.setEnabled(false);
        LWKRButton.setEnabled(false);
        MEXRButton.setEnabled(false);
        MKKRButton.setEnabled(false);
        TSIRButton.setEnabled(false);
        YRIRButton.setEnabled(false);
        FileType = 0;
        try {
            ParseFile(TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Hapmap
    private void HapmapRButtonActionPerformed() {
        if(bg3.getSelection() == null)
        {
            okButton.setEnabled(false);
        }
        SnpSpinnerRow.setEnabled(false);
        SnpSpinner.setEnabled(false);
        SnpSpinner.setValue(12);
        SnpSpinner.setEnabled(false);
        ASWRButton.setEnabled(true);
        CEURButton.setEnabled(true);
        CHBRButton.setEnabled(true);
        CHDRButton.setEnabled(true);
        GIHRButton.setEnabled(true);
        JPTRButton.setEnabled(true);
        LWKRButton.setEnabled(true);
        MEXRButton.setEnabled(true);
        MKKRButton.setEnabled(true);
        TSIRButton.setEnabled(true);
        YRIRButton.setEnabled(true);
        SpaceRButton.setSelected(true);
        setDelimitersEnabled(false);

        PedigreeIdLabel.setEnabled(false);
        IndividualIdLabel.setEnabled(false);
        PIdSpinner.setEnabled(false);
        IdSpinner.setEnabled(false);

        SnpColumnLabel.setEnabled(true);
        AlleleDelLabel.setEnabled(false);
        AlleleMissingLabel.setEnabled(true);

        DelTextField.setText("/");
        MissingTextField.setText(".");
        DelTextField.setEnabled(false);
        MissingTextField.setEnabled(false);

        FileType = 1;
        try {
            ParseFile(SAPCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Affymetrix
    private void AffymetrixRButtonActionPerformed() {
        okButton.setEnabled(true);
        SnpSpinnerRow.setEnabled(true);
        TabRButton.setSelected(true);
        setDelimitersEnabled(true);
        ASWRButton.setEnabled(false);
        CEURButton.setEnabled(false);
        CHBRButton.setEnabled(false);
        CHDRButton.setEnabled(false);
        GIHRButton.setEnabled(false);
        JPTRButton.setEnabled(false);
        LWKRButton.setEnabled(false);
        MEXRButton.setEnabled(false);
        MKKRButton.setEnabled(false);
        TSIRButton.setEnabled(false);
        YRIRButton.setEnabled(false);
        PedigreeIdLabel.setEnabled(true);
        IndividualIdLabel.setEnabled(true);
        PIdSpinner.setEnabled(true);
        IdSpinner.setEnabled(true);

        SnpColumnLabel.setEnabled(true);
        SnpSpinner.setEnabled(true);
        AlleleDelLabel.setEnabled(true);
        AlleleMissingLabel.setEnabled(true);

        DelTextField.setText("/");
        MissingTextField.setText(".");
        DelTextField.setEnabled(true);
        MissingTextField.setEnabled(true);

        FileType = 2;
        try {
            ParseFile(TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Tab
    private void TabRButtonActionPerformed() {
        try {
            FILE_DELIMITER = TAB;
            DelTextField.setText("/");
            MissingTextField.setText(".");
            ParseFile(TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Comma
    private void CommaRButtonActionPerformed() {
        try {
            FILE_DELIMITER = COMMA;
            DelTextField.setText("/");
            MissingTextField.setText(".");
            ParseFile(COMMA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SPACE
    private void SpaceRButtonActionPerformed() {
        try {
            FILE_DELIMITER = SAPCE;
            DelTextField.setText("/");
            MissingTextField.setText(".");
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

    public String GetDelTextField(){
             return SNP_DELIMITER;
    }


  class MyTable extends JTable {
      public String getToolTipText(MouseEvent evt) {
        if (columnAtPoint(evt.getPoint()) == -1)
          return null;
        return Integer.toString(columnAtPoint(evt.getPoint())+1);
    }
  }
  public void ParseFile(String delimiter) throws Exception
     {
         String[] t = null;
         Vector eachlinecontents = null;
         column = new Vector();
         linelist = new Vector();

         int i = 0;

         FileReader fr = new FileReader(FilePath);
         BufferedReader br = new BufferedReader(fr);
         String aLine = new String();

         int col_size = 0;
         while ((aLine = br.readLine()) != null && aLine.trim().length() > 0) {

             if (i >= row_limit)
                 break;

             t = aLine.split(delimiter, col_limit + 1);

             if (i == 0) {
                 column.add("Column Numbers");
                 for (int ti = 1; ti < t.length; ti++) {
                     if (ti >= col_limit)
                         break;
                     column.add("Column " +(ti));
                 }

             }
                 eachlinecontents = new Vector();
                 eachlinecontents.add("Row " +(i+1));
                 for (int ti = 0; ti < t.length; ti++) {
                     if (ti >= col_limit)
                         break;
                     eachlinecontents.add(t[ti]);
                 }

                 linelist.add(eachlinecontents);
                 eachlinecontents = null;
             i++;
         }

         linelist.trimToSize();

         set_table_data(linelist, column);
     }
    /**
     * @param args the command line arguments
     */
        public String getDELIMITER()
    {
        return FILE_DELIMITER;
    }

    // Variables declaration - do not modify
    public javax.swing.JRadioButton ASWRButton;
    private javax.swing.JRadioButton AffymetrixRButton;
    private javax.swing.JLabel AlleleDelLabel;
    private javax.swing.JLabel AlleleMissingLabel;
    private javax.swing.JPanel BottomPanel;
    public javax.swing.JRadioButton CEURButton;
    public javax.swing.JRadioButton CHBRButton;
    public javax.swing.JRadioButton CHDRButton;
    private javax.swing.JPanel CenterPanel;
    private javax.swing.JRadioButton CommaRButton;
    private javax.swing.JLabel DataPreviewLabel;
    private javax.swing.JTextField DelTextField;
    private javax.swing.JPanel DelimitersPanel;
    private javax.swing.JPanel FileFormatPanel;
    public javax.swing.JRadioButton GIHRButton;
    private javax.swing.JPanel HapMapPanel;
    private javax.swing.JRadioButton HapmapRButton;
    private javax.swing.JSpinner IdSpinner;
    private javax.swing.JLabel IndividualIdLabel;
    public javax.swing.JRadioButton JPTRButton;
    public javax.swing.JRadioButton LWKRButton;
    public javax.swing.JRadioButton MEXRButton;
    public javax.swing.JRadioButton MKKRButton;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JTextField MissingTextField;
    public javax.swing.JLabel NoteLabel;
    private javax.swing.JRadioButton OtherRButton;
    private javax.swing.JTextField OtherTextField;
    private javax.swing.JSpinner PIdSpinner;
    private javax.swing.JLabel PedigreeIdLabel;
    private javax.swing.JPanel PedigreedataPanel;
    private javax.swing.JRadioButton SageRButton;
    private javax.swing.JLabel SnpColumnLabel;
    private javax.swing.JLabel SnpColumnLabel1;
    private javax.swing.JSpinner SnpSpinner;
    public javax.swing.JSpinner SnpSpinnerRow;
    private javax.swing.JPanel SnpdataPanel;
    private javax.swing.JRadioButton SpaceRButton;
    public javax.swing.JRadioButton TSIRButton;
    private javax.swing.JRadioButton TabRButton;
    public javax.swing.JRadioButton YRIRButton;
    private javax.swing.ButtonGroup bg1;
    private javax.swing.ButtonGroup bg2;
    private javax.swing.ButtonGroup bg3;
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton okButton;
    // End of variables declaration

}
