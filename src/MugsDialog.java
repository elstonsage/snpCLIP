package snpclip;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListDataEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import java.util.Vector;
import java.util.Collections;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.BitSet;
import javax.swing.tree.*;
import javax.swing.event.*;


public class MugsDialog extends JFrame implements TreeSelectionListener {
    BorderLayout borderLayout1 = new BorderLayout();
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel SelectionPanel;
    private javax.swing.JPanel CaseSelectionPanel;
    private javax.swing.JPanel ControlSelectionPanel;

    private javax.swing.JPanel MUGSPanel;
    private javax.swing.JPanel MUGSListPanel;
    private javax.swing.JPanel MUGSOptionPanel;
    private javax.swing.JPanel OkCancelButtonPanel;

    private javax.swing.JButton CaseOKButton;
    private javax.swing.JButton CaseMUGSButton;
    private javax.swing.JComboBox CaseVariableComboBox;
    private javax.swing.JComboBox CaseOperatorComboBox;

    private javax.swing.JButton ControlOKButton;
    private javax.swing.JButton ControlMUGSButton;
    private javax.swing.JComboBox ControlVariableComboBox;
    private javax.swing.JComboBox ControlOperatorComboBox;

    private javax.swing.JLabel CaseVariableLabel;
    private javax.swing.JLabel CaseOperatorLabel;
    private javax.swing.JLabel CaseValueLabel;
    javax.swing.JLabel CaseSampleLabel;

    private javax.swing.JLabel ControlVariableLabel;
    private javax.swing.JLabel ControlOperatorLabel;
    private javax.swing.JLabel ControlValueLabel;
    javax.swing.JLabel ControlSampleLabel;

    private javax.swing.JList CaseList;
    private javax.swing.JList ControlList;

    private javax.swing.JTextField CaseValueTextField;
    private javax.swing.JTextField ControlValueTextField;

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane MUGSListScrollPane;
    private javax.swing.JScrollPane SNPListScrollPane;
    private javax.swing.JScrollPane jScrollPane2;

    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private DefaultListModel CaselistModel;

    private DefaultListModel ControllistModel;

    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;

    private javax.swing.JSplitPane MUGSSplitPane;
    private javax.swing.JCheckBox NCheckBox;
    private javax.swing.JCheckBox N_1CheckBox;
    private javax.swing.JLabel NColorLabel;
    private javax.swing.JLabel N_1ColorLabel;
    private javax.swing.JPanel MUGSDiplayPanel;
    private javax.swing.JTabbedPane jTabbedPane1;

    private javax.swing.JLabel FindLabel;
    private javax.swing.JTextField FindTextField;
    private javax.swing.JPanel SearchPanel;
    private javax.swing.JTree SearchResultTree;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton SearchButton;

    // End of variables declaration
    int returnStatus = RET_CANCEL;
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    JPopupMenu CasePopup;
    JMenuItem CasePopupDeleteMenu;

    JPopupMenu ControlPopup;
    JMenuItem ControlDeleteMenu;

    JPopupMenu DetailDialogPopup;
    JMenuItem DetailDialogShowMenu;

    CaseListDataListener CaseListLinstener;
    ControlListDataListener ControlListLinstener;

    CaseListPopupListener CaseListPopup;
    ControlListPopupListener ControlListPopup;
    CaseDeleteKeyListener CaseKeyListener;
    ControlDeleteKeyListener ControlKeyListener;

    TitledBorder CasePanelborder;
    TitledBorder ControlPanelborder;
    TitledBorder MUGSPanelborder;

    SnipAllVisualizer visualizerall;

    ButtonGroup bg1 = new ButtonGroup();

    Vector mugs_data;

    boolean m_sortAsc_length = true;
    int LengthCol = 3;

    int numCaseInd = 0;
    int numControlInd = 0;

    JTable seqTable;
    MyTableModel mugsDataModel;

    BitSet FI;
    Mugs Mug = new Mugs();

    public static final int DIS_BOTH = 0;
    public static final int DIS_N_ONLY = 1;
    public static final int DIS_N_1_ONLY = 2;

    int displayOption = 0;

    DefaultMutableTreeNode top =  null;
    DefaultTreeModel treeModel = null;


    private javax.swing.JPanel ZoombuttonPanel;
    private javax.swing.JButton Zoom2xButton;
    private javax.swing.JButton Zoom5xButton;
    private javax.swing.JButton Zoom10xButton;


    public MugsDialog() {
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            initComponents();
            jbInit();
            this.setIconImage(new ImageIcon(MainFrame.class.getResource("snp.png")).getImage());
            this.setTitle("Maximum Unbroken Genotype Sequence Analysis");
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void initComponents() throws Exception {

        ZoombuttonPanel = new javax.swing.JPanel();
        Zoom2xButton = new javax.swing.JButton();
        Zoom5xButton = new javax.swing.JButton();
        Zoom10xButton = new javax.swing.JButton();

        CasePopup = new JPopupMenu();
        CasePopupDeleteMenu = new JMenuItem("Delete");

        ControlDeleteMenu = new JMenuItem("Delete");
        ControlPopup = new JPopupMenu();

        DetailDialogShowMenu = new JMenuItem("Show Details..");
        DetailDialogPopup = new JPopupMenu();

        CaseListLinstener = new CaseListDataListener();
        ControlListLinstener = new ControlListDataListener();
        CaseListPopup = new CaseListPopupListener();
        ControlListPopup = new ControlListPopupListener();
        CaseKeyListener = new CaseDeleteKeyListener();
        ControlKeyListener = new ControlDeleteKeyListener();

        CaselistModel = new DefaultListModel();
        ControllistModel = new DefaultListModel();

        MainPanel = new javax.swing.JPanel();

        SNPListScrollPane = new javax.swing.JScrollPane();
        MUGSPanel = new javax.swing.JPanel();
        MUGSListPanel = new javax.swing.JPanel();
        MUGSOptionPanel = new javax.swing.JPanel();
        MUGSListScrollPane = new javax.swing.JScrollPane();
        OkCancelButtonPanel = new javax.swing.JPanel();

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        SelectionPanel = new javax.swing.JPanel();

        CaseSelectionPanel = new javax.swing.JPanel();
        CaseOKButton = new javax.swing.JButton();
        CaseMUGSButton = new javax.swing.JButton();
        CaseVariableComboBox = new javax.swing.JComboBox();
        CaseOperatorComboBox = new javax.swing.JComboBox();
        CaseVariableLabel = new javax.swing.JLabel();
        CaseOperatorLabel = new javax.swing.JLabel();
        CaseValueLabel = new javax.swing.JLabel();
        CaseSampleLabel = new javax.swing.JLabel();
        CaseValueTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        CaseList = new javax.swing.JList();

        ControlSelectionPanel = new javax.swing.JPanel();
        ControlOKButton = new javax.swing.JButton();
        ControlMUGSButton = new javax.swing.JButton();
        ControlVariableComboBox = new javax.swing.JComboBox();
        ControlOperatorComboBox = new javax.swing.JComboBox();
        ControlVariableLabel = new javax.swing.JLabel();
        ControlOperatorLabel = new javax.swing.JLabel();
        ControlValueLabel = new javax.swing.JLabel();
        ControlSampleLabel = new javax.swing.JLabel();
        ControlValueTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        ControlList = new javax.swing.JList();

        MUGSSplitPane = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        NColorLabel = new javax.swing.JLabel();
        N_1ColorLabel = new javax.swing.JLabel();
        NCheckBox = new javax.swing.JCheckBox();
        N_1CheckBox = new javax.swing.JCheckBox();
        MUGSDiplayPanel = new javax.swing.JPanel();
        seqTable = new JTable();

        SearchPanel = new javax.swing.JPanel();
        FindLabel = new javax.swing.JLabel();
        FindTextField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        SearchButton = new javax.swing.JButton();

        CasePanelborder = javax.swing.BorderFactory.createTitledBorder(null, "Group1 Criteria",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 1, 11), new Color(0, 0, 0));
        ControlPanelborder = javax.swing.BorderFactory.createTitledBorder(null, "Group2 Criteria",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 1, 11), new Color(0, 0, 0));
        MUGSPanelborder = javax.swing.BorderFactory.createTitledBorder(null, "Longest SNP sequence",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 1, 11), new Color(0, 0, 0));

        initMUGSOptionPanel();
        initSelectionPanel();
        initMUGSPanel();
    }

    private void initSelectionPanel() {
        //Case Selection Panel
        CaseSelectionPanel.setMinimumSize(new java.awt.Dimension(290, 250));
        CaseSelectionPanel.setPreferredSize(new java.awt.Dimension(290, 250));

        CaseMUGSButton.setText("Go");

        CaseMUGSButton.setVerticalTextPosition(AbstractButton.CENTER);
        CaseMUGSButton.setHorizontalTextPosition(AbstractButton.TRAILING);
        CaseMUGSButton.setPreferredSize(new java.awt.Dimension(50, 22));

        CaseMUGSButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        CaseMUGSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMUGSButtonActionPerformed();
            }
        });

        CaseVariableLabel.setText("Variable");
        CaseOperatorLabel.setText("Operator");
        CaseOperatorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"=", "<", ">", "<=", ">=",
                "<>"}));
        CaseVariableComboBox.setPreferredSize(new java.awt.Dimension(80, 20));

        CaseOperatorComboBox.setMinimumSize(new java.awt.Dimension(45, 20));
        CaseOperatorComboBox.setPreferredSize(new java.awt.Dimension(45, 20));

        CaseValueLabel.setText("Value");
        CaseValueTextField.setPreferredSize(new java.awt.Dimension(60, 20));

        CaselistModel = new DefaultListModel();

        CaseOKButton.setText("OK");
        CaseOKButton.setPreferredSize(new java.awt.Dimension(50, 22));
        CaseOKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CaseOKButtonActionPerformed();
            }
        });

        CaseList.setModel(CaselistModel);
        jScrollPane1.setViewportView(CaseList);

        CaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        CaseList.setToolTipText("Select and right-click to delete");
        CaselistModel.addListDataListener(CaseListLinstener);
        CaseList.addMouseListener(CaseListPopup);
        CaseList.addKeyListener(CaseKeyListener);

        CasePopup.add(CasePopupDeleteMenu);

        CasePopupDeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int index = CaseList.getSelectedIndex();
                CaselistModel.remove(index);
            }
        });

        CaseSampleLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        CaseSampleLabel.setMinimumSize(new java.awt.Dimension(40, 22));
        CaseSampleLabel.setPreferredSize(new java.awt.Dimension(40, 22));

        javax.swing.GroupLayout CaseSelectionPanelLayout = new javax.swing.GroupLayout(CaseSelectionPanel);
        CaseSelectionPanel.setLayout(CaseSelectionPanelLayout);
        CaseSelectionPanelLayout.setHorizontalGroup(
                CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CaseSelectionPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CaseSelectionPanelLayout.createSequentialGroup() .addGroup(CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CaseVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(CaseVariableLabel))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CaseSelectionPanelLayout.createSequentialGroup()
                          .addComponent(CaseOperatorLabel)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(CaseValueLabel))
                .addGroup(CaseSelectionPanelLayout.createSequentialGroup()
                          .addComponent(CaseOperatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(CaseValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addGap(10, 10, 10)
                          .addComponent(CaseOKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                    .addGroup(CaseSelectionPanelLayout.createSequentialGroup()
                                              .addComponent(CaseSampleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                              .addComponent(CaseMUGSButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        CaseSelectionPanelLayout.setVerticalGroup(
                CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CaseSelectionPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CaseVariableLabel)
                                    .addComponent(CaseOperatorLabel)
                                    .addComponent(CaseValueLabel))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CaseVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CaseOperatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CaseValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CaseOKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addGap(4, 4, 4)
                          .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(CaseSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(CaseMUGSButton)
                                    .addComponent(CaseSampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap())
                );

        CaseSelectionPanel.setBorder(CasePanelborder);

        //Control Selection Panel
        ControlSelectionPanel.setMinimumSize(new java.awt.Dimension(290, 250));
        ControlSelectionPanel.setPreferredSize(new java.awt.Dimension(290, 250));

        ControlMUGSButton.setText("Go");

        ControlMUGSButton.setVerticalTextPosition(AbstractButton.CENTER);
        ControlMUGSButton.setHorizontalTextPosition(AbstractButton.TRAILING);
        ControlMUGSButton.setPreferredSize(new java.awt.Dimension(50, 22));

        ControlMUGSButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        ControlMUGSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMUGSButtonActionPerformed();
            }
        });

        ControlVariableLabel.setText("Variable");
        ControlOperatorLabel.setText("Operator");
        ControlOperatorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"=", "<", ">", "<=", ">=", "<>"}));
        ControlVariableComboBox.setPreferredSize(new java.awt.Dimension(80, 20));

        ControlOperatorComboBox.setMinimumSize(new java.awt.Dimension(45, 20));
        ControlOperatorComboBox.setPreferredSize(new java.awt.Dimension(45, 20));

        ControlValueLabel.setText("Value");
        ControlValueTextField.setPreferredSize(new java.awt.Dimension(60, 20));

        ControlOKButton.setText("OK");
        ControlOKButton.setPreferredSize(new java.awt.Dimension(50, 22));
        ControlOKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ControlOKButtonActionPerformed();
            }
        });

        ControllistModel = new DefaultListModel();

        ControlList.setModel(ControllistModel);
        jScrollPane2.setViewportView(ControlList);

        ControlList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ControlList.setToolTipText("Select and right-click to delete");

        ControllistModel.addListDataListener(ControlListLinstener);

        ControlList.addMouseListener(ControlListPopup);
        ControlList.addKeyListener(ControlKeyListener);

        ControlPopup.add(ControlDeleteMenu);

        ControlDeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int index = ControlList.getSelectedIndex();
                ControllistModel.remove(index);
            }
        });

        ControlSampleLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ControlSampleLabel.setMinimumSize(new java.awt.Dimension(40, 22));
        ControlSampleLabel.setPreferredSize(new java.awt.Dimension(40, 22));

        javax.swing.GroupLayout ControlSelectionPanelLayout = new javax.swing.GroupLayout(ControlSelectionPanel);
        ControlSelectionPanel.setLayout(ControlSelectionPanelLayout);
        ControlSelectionPanelLayout.setHorizontalGroup(
                ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlSelectionPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                              ControlSelectionPanelLayout.createSequentialGroup()
                                              .addGroup(ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(ControlVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(ControlVariableLabel))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ControlSelectionPanelLayout.createSequentialGroup()
                          .addComponent(ControlOperatorLabel)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(ControlValueLabel))
                .addGroup(ControlSelectionPanelLayout.createSequentialGroup()
                          .addComponent(ControlOperatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(ControlValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addGap(10, 10, 10)
                          .addComponent(ControlOKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                    .addGroup(ControlSelectionPanelLayout.createSequentialGroup()
                                              .addComponent(ControlSampleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                              .addComponent(ControlMUGSButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        ControlSelectionPanelLayout.setVerticalGroup(
                ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ControlSelectionPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ControlVariableLabel)
                                    .addComponent(ControlOperatorLabel)
                                    .addComponent(ControlValueLabel))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ControlVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ControlOperatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ControlValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ControlOKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addGap(4, 4, 4)
                          .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(ControlSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ControlMUGSButton)
                                    .addComponent(ControlSampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap())
                );

        ControlSelectionPanel.setBorder(ControlPanelborder);

        javax.swing.GroupLayout SelectionPanelLayout = new javax.swing.GroupLayout(SelectionPanel);
        SelectionPanel.setLayout(SelectionPanelLayout);
        SelectionPanelLayout.setHorizontalGroup(
                SelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(CaseSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ControlSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
        SelectionPanelLayout.setVerticalGroup(
                SelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SelectionPanelLayout.createSequentialGroup()
                          .addComponent(CaseSelectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(ControlSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
                );
    }


    private void initMUGSPanel() {
        DetailDialogPopup.add(DetailDialogShowMenu);
        DetailDialogShowMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DetailDialogShowMenuActionPerformed();
            }
        });

        MUGSPanel.setLayout(new java.awt.BorderLayout());
        MUGSListScrollPane.setViewportView(seqTable);

        seqTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        seqTable.setRowHeight(60);
        seqTable.setRowMargin(10);
        seqTable.setIntercellSpacing(new Dimension(0, 0));

        seqTable.setAutoCreateRowSorter(true);
        seqTable.setShowGrid(false);
        seqTable.setPreferredScrollableViewportSize(new Dimension(600, 70));
        seqTable.setFillsViewportHeight(true);
        seqTable.setFocusable(false);

        ListSelectionModel listSelectionModel = seqTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(new TableSelectionHandler());
        seqTable.setSelectionModel(listSelectionModel);
        seqTable.addMouseListener(new TablePopupListener());

        Zoom2xButton.setText("1x");
        Zoom2xButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Zoom2xButtonActionPerformed(evt);
            }
        });

        Zoom5xButton.setText("5x");
        Zoom5xButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Zoom5xButtonActionPerformed(evt);
            }
        });

        Zoom10xButton.setText("10x");
        Zoom10xButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Zoom10xButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ZoombuttonPanelLayout = new javax.swing.GroupLayout(ZoombuttonPanel);
        ZoombuttonPanel.setLayout(ZoombuttonPanelLayout);
        ZoombuttonPanelLayout.setHorizontalGroup(
            ZoombuttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ZoombuttonPanelLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(Zoom2xButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Zoom5xButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Zoom10xButton)
                .addContainerGap())
        );
        ZoombuttonPanelLayout.setVerticalGroup(
            ZoombuttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ZoombuttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ZoombuttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Zoom10xButton)
                    .addComponent(Zoom5xButton)
                    .addComponent(Zoom2xButton))
                .addContainerGap())
        );

        MUGSPanel.setPreferredSize(new Dimension(650, 330));
        MUGSPanel.setMinimumSize(new Dimension(650, 330));

        javax.swing.GroupLayout MUGSListPanelLayout = new javax.swing.GroupLayout(MUGSListPanel);
        MUGSListPanel.setLayout(MUGSListPanelLayout);
        MUGSListPanelLayout.setHorizontalGroup(
                MUGSListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MUGSListPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(MUGSListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(MUGSListScrollPane, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(SNPListScrollPane, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ZoombuttonPanel, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                          )
                          .addContainerGap())
                );
        MUGSListPanelLayout.setVerticalGroup(
                MUGSListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(MUGSListPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(MUGSListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(SNPListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addComponent(ZoombuttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          //.addContainerGap()
                          )
                );

        MUGSListPanel.setPreferredSize(MUGSPanel.getPreferredSize());
        MUGSListPanel.setMinimumSize(MUGSPanel.getMinimumSize());

        visualizerall = new SnipAllVisualizer();
        visualizerall.setPreferredSize(new Dimension(300, 100));
        visualizerall.setOpaque(true);
        visualizerall.setBackground(Color.white);

        visualizerall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visualizerallMouseClicked(evt);
            }
        });

        SNPListScrollPane.setPreferredSize(new Dimension(200, 60));
        SNPListScrollPane.setMinimumSize(new Dimension(200, 60));

        SNPListScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        SNPListScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        SNPListScrollPane.setViewportView(visualizerall);

        //MUGSOptionPanel
        jTabbedPane1.addTab("Options", MUGSDiplayPanel);

        MUGSOptionPanel.setLayout(new java.awt.BorderLayout());
        MUGSOptionPanel.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        MUGSOptionPanel.setPreferredSize(new Dimension(150, 500));
        MUGSOptionPanel.setMinimumSize(new Dimension(150, 500));

        MUGSSplitPane.setBorder(MUGSPanelborder);
        MUGSSplitPane.setLeftComponent(MUGSListPanel);
        MUGSSplitPane.setRightComponent(MUGSOptionPanel);
        MUGSSplitPane.setOneTouchExpandable(true);
        MUGSSplitPane.setDividerSize(10);
        MUGSSplitPane.setDividerLocation(MUGSPanel.getPreferredSize().width);
    }

    private void Zoom2xButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int zoom = 1;
        ZoomVisualizerAll(zoom);
    }

    private void Zoom5xButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int zoom = 10;
        ZoomVisualizerAll(zoom);
    }

    private void Zoom10xButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int zoom = 20;
        ZoomVisualizerAll(zoom);
    }

    private void ZoomVisualizerAll(int zoom)
    {
        visualizerall.indicatorWidth = zoom;
        visualizerall.updateSnpSizeWindow();

        int selectedIndex = seqTable.getSelectedRow();

        if (selectedIndex >= 0) {
            MyTableModel model = (MyTableModel) seqTable.getModel();
            Position entry = (Position) (model.data.get(seqTable.convertRowIndexToModel(selectedIndex)));

            if (entry != null) {
                visualizerall.setSNP(entry.getStartIndex()*(visualizerall.indicatorWidth));
                JScrollBar hbar = SNPListScrollPane.getHorizontalScrollBar();
                hbar.setValue(entry.getStartIndex()*(visualizerall.indicatorWidth)-visualizerall.indicatorWidth);
            }
        }
    }

    private void initMUGSOptionPanel() {
        NColorLabel.setText("");
        N_1ColorLabel.setText("");

        NCheckBox.setText("All N Individuals");
        NCheckBox.setSelected(true);
        NCheckBox.setFocusPainted(false);
        NCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NCheckBoxActionPerformed(evt);
            }
        });

        N_1CheckBox.setText("N-1 Individuals");
        N_1CheckBox.setSelected(true);
        N_1CheckBox.setFocusPainted(false);

        N_1CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                N_1CheckBoxActionPerformed(evt);
            }
        });

        NColorLabel.setBackground(new Color(0, 180, 247));
        NColorLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        NColorLabel.setOpaque(true);
        NColorLabel.setPreferredSize(new java.awt.Dimension(20, 20));
        NColorLabel.setMinimumSize(new java.awt.Dimension(20, 20));

        N_1ColorLabel.setBackground(new Color(255, 0, 144));
        N_1ColorLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        N_1ColorLabel.setOpaque(true);
        N_1ColorLabel.setPreferredSize(new java.awt.Dimension(20, 20));
        N_1ColorLabel.setMinimumSize(new java.awt.Dimension(20, 20));

        JPanel AgreementPanel = new javax.swing.JPanel();
        AgreementPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Collective Agreement"));
        javax.swing.GroupLayout AgreementPanelLayout = new javax.swing.GroupLayout(AgreementPanel);
        AgreementPanel.setLayout(AgreementPanelLayout);
        AgreementPanelLayout.setHorizontalGroup(
                AgreementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AgreementPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(AgreementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AgreementPanelLayout.createSequentialGroup()
                                              .addComponent(NCheckBox)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                                              .addComponent(NColorLabel)
                                              .addGap(10, 10, 10))
                                    .addGroup(AgreementPanelLayout.createSequentialGroup()
                                              .addComponent(N_1CheckBox)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                                              .addComponent(N_1ColorLabel)
                                              .addGap(10, 10, 10))))
                );
        AgreementPanelLayout.setVerticalGroup(
                AgreementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AgreementPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(AgreementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(NCheckBox)
                                    .addComponent(NColorLabel))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addGroup(AgreementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(N_1CheckBox)
                                    .addComponent(N_1ColorLabel))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        SearchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));
        FindLabel.setText("SNP(s)");
        SearchButton.setText("Find");
        SearchButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed();
            }
        });

        top =  new DefaultMutableTreeNode(new NodeInfo("Search results","root"));
        treeModel = new DefaultTreeModel(top);
        SearchResultTree  = new JTree(treeModel);
        SearchResultTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        SearchResultTree.addTreeSelectionListener(this);
        ImageIcon mugsIcon = new ImageIcon(MainFrame.class.getResource("snp.png"));
        ImageIcon searchIcon = new ImageIcon(MainFrame.class.getResource("button.png"));
        if ((mugsIcon != null) && (searchIcon != null))
        {
                SearchResultTree.setCellRenderer(new MyRenderer(mugsIcon, searchIcon));
        }

        jScrollPane3.setViewportView(SearchResultTree);

        javax.swing.GroupLayout SearchPanelLayout = new javax.swing.GroupLayout(SearchPanel);
        SearchPanel.setLayout(SearchPanelLayout);
        SearchPanelLayout.setHorizontalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addGroup(SearchPanelLayout.createSequentialGroup()
                        .addComponent(FindLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FindTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        SearchPanelLayout.setVerticalGroup(
            SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FindLabel)
                    .addComponent(FindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout MUGSDiplayPanelLayout = new javax.swing.GroupLayout(MUGSDiplayPanel);
        MUGSDiplayPanel.setLayout(MUGSDiplayPanelLayout);
        MUGSDiplayPanelLayout.setHorizontalGroup(
                MUGSDiplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(MUGSDiplayPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(MUGSDiplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                              .addComponent(AgreementPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .addComponent(SearchPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
                );
        MUGSDiplayPanelLayout.setVerticalGroup(
                MUGSDiplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(MUGSDiplayPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addComponent(AgreementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                          .addComponent(SearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
                );
    }

    private void jbInit() throws Exception {

        this.setSize(new Dimension(1280, 700));
        this.setMinimumSize(new Dimension(1280, 700));

        MainPanel.setLayout(new java.awt.BorderLayout(5, 5));
        MainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        okButton.setText("OK");
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

        javax.swing.GroupLayout OkCancelButtonPanelLayout = new javax.swing.GroupLayout(OkCancelButtonPanel);
        OkCancelButtonPanel.setLayout(OkCancelButtonPanelLayout);
        OkCancelButtonPanelLayout.setHorizontalGroup(
                OkCancelButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OkCancelButtonPanelLayout.createSequentialGroup()
                          .addContainerGap(250, Short.MAX_VALUE)
                          .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(cancelButton)
                          .addContainerGap())
                );

        OkCancelButtonPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
                                           new java.awt.Component[] {cancelButton, okButton});

        OkCancelButtonPanelLayout.setVerticalGroup(
                OkCancelButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OkCancelButtonPanelLayout.createSequentialGroup()
                          .addGroup(OkCancelButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cancelButton)
                                    .addComponent(okButton))
                          .addContainerGap())
                );

        MainPanel.add(SelectionPanel, java.awt.BorderLayout.WEST);
        MainPanel.add(MUGSSplitPane, java.awt.BorderLayout.CENTER);
        getContentPane().add(MainPanel, java.awt.BorderLayout.CENTER);
        getContentPane().add(OkCancelButtonPanel, java.awt.BorderLayout.SOUTH);
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_OK);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        doClose(RET_CANCEL);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        MainFrame.mainFrame1.setMUGSResult(getMUGSResultList(), getMUGSResultFilter());

        setVisible(false);
        dispose();
    }

    private int getMUGSResultList() {
        int list = mugsDataModel.getRowCount();

        int result = 0;
        for (int i = 0; i < list; i++) {
            Boolean entrydata = (Boolean) mugsDataModel.getValueAt(i, 0);
            int snpseq = Integer.parseInt(mugsDataModel.getValueAt(i, 4).toString());

            if (entrydata.booleanValue()) {
                result = result + snpseq;
            }
        }

        return result;
    }

    private BitSet getMUGSResultFilter() {
        int numSnpOriginal = MainFrame.mainFrame1.MC.GetNUMSNPs();

        BitSet T = new BitSet(numSnpOriginal);
        T.flip(0, numSnpOriginal);

        int list = mugsDataModel.getRowCount();
        for (int i = 0; i < list; i++) {
            Position eachdata = (Position) mugsDataModel.data.get(i);

            int startIndex = eachdata.getStartIndex(); //inclusive
            int endIndex = eachdata.getEndIndex(); //exclusive

            if (eachdata.getSelectedValue().booleanValue()) {
                T.set(startIndex, endIndex + 1, false);
            }

            for (int j = startIndex; j <= endIndex; j++) {
                if (FI.get(j)) {
                    T.set(j, true);
                }
            }
        }

        return T;
    }

    void setCriteriaItems(String[] data) {
        CaseVariableComboBox.removeAllItems();
        CaseVariableComboBox.setModel(new javax.swing.DefaultComboBoxModel(data));
        ControlVariableComboBox.removeAllItems();
        ControlVariableComboBox.setModel(new javax.swing.DefaultComboBoxModel(data));
    }

    private void CaseOKButtonActionPerformed() {
        String variable = CaseVariableComboBox.getSelectedItem().toString();
        int operator = CaseOperatorComboBox.getSelectedIndex();
        String value = CaseValueTextField.getText().trim();

        ListDataModel in = new ListDataModel(variable, operator, value);

        CaselistModel.addElement(in);
    }

    private void ControlOKButtonActionPerformed() {
        String variable = ControlVariableComboBox.getSelectedItem().toString();
        int operator = ControlOperatorComboBox.getSelectedIndex();
        String value = ControlValueTextField.getText().trim();

        ListDataModel in = new ListDataModel(variable, operator, value);

        ControllistModel.addElement(in);
    }

    private void NCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        setDisplayOption();
        mugs_data = Mug.GetMUGSResults(displayOption);
        Collections.sort(mugs_data, new MugsComparator(LengthCol, false));

        Position max = (Position) mugs_data.get(0);

        mugsDataModel = new MyTableModel();
        mugsDataModel.data = mugs_data;

        seqTable.setModel(mugsDataModel);
        seqTable.getColumnModel().getColumn(6).setCellRenderer(new SeqMugsRenderer(max.getLength() + 1));
        seqTable.getColumnModel().getColumn(7).setCellRenderer(new MissingMugsRenderer());
    }

    private void N_1CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
        setDisplayOption();

        mugs_data = Mug.GetMUGSResults(displayOption);
        Collections.sort(mugs_data, new MugsComparator(LengthCol, false));

        Position max = (Position) mugs_data.get(0);

        mugsDataModel = new MyTableModel();
        mugsDataModel.data = mugs_data;

        seqTable.setModel(mugsDataModel);
        seqTable.getColumnModel().getColumn(6).setCellRenderer(new SeqMugsRenderer(max.getLength() + 1));
        seqTable.getColumnModel().getColumn(7).setCellRenderer(new MissingMugsRenderer());
    }

    private void setDisplayOption() {
        if (NCheckBox.isSelected() && N_1CheckBox.isSelected()) {
            displayOption = DIS_BOTH;
        } else if (NCheckBox.isSelected()) {
            displayOption = DIS_N_ONLY;
        } else if (N_1CheckBox.isSelected()) {
            displayOption = DIS_N_1_ONLY;
        }
    }

    private void jMUGSButtonActionPerformed() {
        numCaseInd = Mug.setListofCaseGroupSamples(CaselistModel);

        if (numCaseInd <= 0) {
            JOptionPane.showOptionDialog(this,
                                         "No case samples found. \nNo matches were found.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            return;
        }

        numControlInd = Mug.setListofControlGroupSamples(ControllistModel);
        Mug.InitMUGS(FI);
        mugs_data = Mug.GetMUGSResults(displayOption);

        // Found Nothing!
        if (mugs_data.size() <= 0) {
            JOptionPane.showOptionDialog(this,
                                         "No MUGS results found. \nNo matches were found.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            return;
        }

        mugsDataModel = new MyTableModel();
        mugsDataModel.data = mugs_data;

        seqTable.setModel(mugsDataModel);

        Position max = (Position) mugs_data.get(0);
        seqTable.getColumnModel().getColumn(6).setCellRenderer(new SeqMugsRenderer(max.getLength() + 1));
        seqTable.getColumnModel().getColumn(7).setCellRenderer(new MissingMugsRenderer());

        // for all snp visualizer
        int snpAllFlag[] = Mug.getAllSnpFlag();
        visualizerall.setTotalSnpSize(snpAllFlag.length);
        visualizerall.setAllSnpFlag(snpAllFlag);
    }

     private void SearchButtonActionPerformed()
     {
         top.removeAllChildren();
         treeModel.reload();

         String list = FindTextField.getText().trim();

         String snps[] = list.split(",");

         MyTableModel model = (MyTableModel) seqTable.getModel();
         Vector current_data = (Vector)(model.data);

         String ids[] = new String[snps.length];

         //Find each snp
         for (int j = 0; j < snps.length; j++) { //for each search snp name

             String findsnpname = snps[j].trim();

             DefaultMutableTreeNode snpname = new DefaultMutableTreeNode(new NodeInfo(findsnpname, "find"));
             treeModel.insertNodeInto(snpname, top, top.getChildCount());
             SearchResultTree.scrollPathToVisible(new TreePath(snpname.getPath()));

             String search_id = "NONE";
             ids[j] = search_id;

             for (int i = 0; i < current_data.size(); i++) { // check the MUGS results
                 Position t = (Position) current_data.get(i);
                 String snpnamelist[] = t.getSnpNameList();

                 for (int k = 0; k < snpnamelist.length; k++) { // for each MUGS sequence
                     if (findsnpname.compareTo(snpnamelist[k]) == 0) {
                         search_id = t.getSelectedID();
                         ids[j] = search_id;
                     }
                 }
             }

             if(search_id.compareTo("NONE")!=0) // find!
             {
                 DefaultMutableTreeNode searchresult = new DefaultMutableTreeNode(new NodeInfo(search_id, "mugs"));
                 treeModel.insertNodeInto(searchresult, snpname, snpname.getChildCount());
                 SearchResultTree.scrollPathToVisible(new TreePath(searchresult.getPath()));
             }
             else
             {
                 DefaultMutableTreeNode searchresult = new DefaultMutableTreeNode(new NodeInfo("none", "none"));
                 treeModel.insertNodeInto(searchresult, snpname, snpname.getChildCount());
                 SearchResultTree.scrollPathToVisible(new TreePath(searchresult.getPath()));
             }
        }

        if(snps.length>1)
        {
                //Fine all snp in one sequence
                 boolean allflag = true;

                 for (int j = 0; j < ids.length-1; j++)
                 {
                     if(ids[j].compareTo(ids[j+1]) != 0)
                     {
                         allflag = false;
                         break;
                     }
                 }

                 DefaultMutableTreeNode snpname = new DefaultMutableTreeNode(new NodeInfo(list, "find"));
                 treeModel.insertNodeInto(snpname, top, top.getChildCount());
                 SearchResultTree.scrollPathToVisible(new TreePath(snpname.getPath()));

                if(allflag)
                {
                    DefaultMutableTreeNode searchresult = new DefaultMutableTreeNode(new NodeInfo(ids[0], "mugs"));
                    treeModel.insertNodeInto(searchresult, snpname, snpname.getChildCount());
                    SearchResultTree.scrollPathToVisible(new TreePath(searchresult.getPath()));
                }
                else
                {
                    DefaultMutableTreeNode searchresult = new DefaultMutableTreeNode(new NodeInfo("none", "none"));
                    treeModel.insertNodeInto(searchresult, snpname, snpname.getChildCount());
                    SearchResultTree.scrollPathToVisible(new TreePath(searchresult.getPath()));
                }
        }
     }

    public void valueChanged(TreeSelectionEvent e) {

         DefaultMutableTreeNode node = (DefaultMutableTreeNode) SearchResultTree.getLastSelectedPathComponent();
         if (node == null)
             //Nothing is selected.
             return;

         if (node.isLeaf()) {

             NodeInfo nodeInfo = (NodeInfo) (node.getUserObject());
             String id = nodeInfo.NodeID;
             if (id.compareTo("mugs") != 0) {
                 return;
             }

             String selected_id = node.getUserObject().toString();
             int selectedrow = Integer.parseInt(selected_id.substring(3, selected_id.length()))-1;

             if (selectedrow >= 0)
             {
                 seqTable.changeSelection(selectedrow, 0, true, false);
             }
         }
  }


    class TableSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {

            int selectedIndex = seqTable.getSelectedRow();

            if (selectedIndex >= 0) {
                MyTableModel model = (MyTableModel) seqTable.getModel();
                Position entry = (Position) (model.data.get(seqTable.convertRowIndexToModel(selectedIndex)));

                if (entry != null) {
                    visualizerall.setSNP(entry.getStartIndex()*(visualizerall.indicatorWidth));
                    JScrollBar hbar = SNPListScrollPane.getHorizontalScrollBar();
                    hbar.setValue(entry.getStartIndex()*(visualizerall.indicatorWidth)-visualizerall.indicatorWidth);
                }
            }
        }
    }


    class TablePopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            ListSelectionModel lsm = seqTable.getSelectionModel();

            if (!lsm.isSelectionEmpty() && (e.isPopupTrigger())) {
                DetailDialogPopup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }


    private void DetailDialogShowMenuActionPerformed() {
        MainFrame.mainFrame1.M.FilterAlleleFreq(MainFrame.mainFrame1.MC, 0, 1);
        Vector allelef = MainFrame.mainFrame1.M.AlleleFreq;

        int selectedIndex = seqTable.getSelectedRow();
        MyTableModel model = (MyTableModel) seqTable.getModel();

        Position entry = (Position) (model.data.get(seqTable.convertRowIndexToModel(selectedIndex)));

        String[] snpnamelist = entry.getSnpNameList();

        int[] caseFlag = entry.getCaseSnpFlag();
        String caseMissing = entry.getCaseMissingList();
        String caseMissinglist[] = caseMissing.split(",");

        int caseMissingIndex = 0;
        int startIndex = entry.getStartIndex();
        int endIndex = entry.getEndIndex();

        Vector eachallelef = new Vector();
        for (int k = startIndex; k <= endIndex; k++) {
            if (!FI.get(k)) {
                eachallelef.add(allelef.get(k));
            }
        }

        Vector caseData = new Vector();

        for (int j = 0; j < caseFlag.length; j++) {

            Vector eachline = new Vector();
            for (int i = 0; i < 4; i++) {
                switch (i) {
                case 0:
                    eachline.add(new Integer(caseFlag[j]));
                    break;
                case 1:
                    eachline.add(snpnamelist[j]);
                    break;

                case 2:
                    eachline.add(eachallelef.get(j));
                    break;
                case 3:
                    if (caseFlag[j] == 2) {
                        eachline.add(caseMissinglist[caseMissingIndex].trim());
                        caseMissingIndex++;
                    }
                    break;
                }
            }
            caseData.add(eachline);
        }

        Vector controlData = new Vector();

        // Add Group2 tab
        if (numControlInd > 0) {
            int[] controlFlag = entry.getControlSnpFlag();

            String controlMissing = entry.getControlMissingList();
            String controlMissinglist[] = controlMissing.split(",");

            int controlMissingIndex = 0;

            for (int j = 0; j < controlFlag.length; j++) {

                Vector eachline = new Vector();
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                    case 0:
                        eachline.add(new Integer(controlFlag[j]));
                        break;
                    case 1:
                        eachline.add(snpnamelist[j]);
                        break;
                    case 2:
                        eachline.add(eachallelef.get(j));
                        break;
                    case 3:
                        if (controlFlag[j] == 2) {
                            eachline.add(controlMissinglist[controlMissingIndex].trim());
                            controlMissingIndex++;
                        }
                        break;
                    }
                }
                controlData.add(eachline);
            }
        }
        Vector columnNames = new Vector();
        columnNames.add("Type");
        columnNames.add("SNIP Name");
        columnNames.add("Allele Frequency");
        columnNames.add("Missing Individual");

        MugsDetailDialog md = new MugsDetailDialog(this, true);
        md.setCaseTableModel(new DefaultTableModel(caseData, columnNames));

        if (numControlInd > 0) {
            md.setControlTableModel(new DefaultTableModel(controlData, columnNames));
        }

        md.setNumSnips(entry.getLength());
        md.setVisible(true);
    }

    private void visualizerallMouseClicked(java.awt.event.MouseEvent evt) {
        int index = evt.getX() - 10;
        Position entry = (Position) mugs_data.get(0);

        boolean find = false;
        int result = 0;

        for (int i = 0; i < mugs_data.size(); i++) {
            entry = (Position) mugs_data.get(i);
            if (entry.getStartIndex() == index) {
                find = true;
                result = i;
                break;
            }
        }

        if (!find) {
            int min_diff = 100000;
            for (int i = 0; i < mugs_data.size(); i++) {
                entry = (Position) mugs_data.get(i);

                int diff = Math.abs(entry.getStartIndex() - index);
                if (diff <= min_diff) {
                    min_diff = diff;
                    result = i;
                }
            }
        }

        entry = (Position) mugs_data.get(result*(visualizerall.indicatorWidth));
        seqTable.changeSelection(result, 0, true, false);
    }

    class CaseListDataListener implements ListDataListener {
        public void contentsChanged(ListDataEvent e) {
            if (CaselistModel.size() >= 0) {
                numCaseInd = Mug.setListofCaseGroupSamples(CaselistModel);
                CaseSampleLabel.setText("Sample Size : " + numCaseInd);
            }
        }

        public void intervalAdded(ListDataEvent e) {
            contentsChanged(e);
        }

        public void intervalRemoved(ListDataEvent e) {
            contentsChanged(e);
        }
    }

    class ControlListDataListener implements ListDataListener {
        public void contentsChanged(ListDataEvent e) {
            if (ControllistModel.size() >= 0) {

                numControlInd = Mug.setListofControlGroupSamples(ControllistModel);
                ControlSampleLabel.setText("Sample Size : " + numControlInd);
            }
        }

        public void intervalAdded(ListDataEvent e) {
            contentsChanged(e);
        }

        public void intervalRemoved(ListDataEvent e) {
            contentsChanged(e);
        }
    }

    class CaseListPopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (CaseList.getSelectedValue() != null && (e.isPopupTrigger())) {
                CasePopup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }


    class ControlListPopupListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (ControlList.getSelectedValue() != null && (e.isPopupTrigger())) {
                ControlPopup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }


    class CaseDeleteKeyListener extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_DELETE) {

                int index = CaseList.getSelectedIndex();
                if (index >= 0) {
                    CaselistModel.remove(index);
                }
            }
        }
    }

    class ControlDeleteKeyListener extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_DELETE) {

                int index = ControlList.getSelectedIndex();
                if (index >= 0) {
                    ControllistModel.remove(index);
                }
            }
        }
    }

    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode) (node.getChildAt(index));
        }

        public void treeNodesInserted(TreeModelEvent e) {}

        public void treeNodesRemoved(TreeModelEvent e) {}

        public void treeStructureChanged(TreeModelEvent e) {}
    }

    private class NodeInfo {
        public String NodeName;
        public String NodeID;

        public NodeInfo(String name, String id) {
            NodeName = name;
            NodeID = id;
        }

        public String toString() {
            return NodeName;
        }
    }


    private class MyRenderer extends DefaultTreeCellRenderer {
        Icon mugsIcon;
        Icon searchIcon;
        public MyRenderer(Icon icon1, Icon icon2) {
            mugsIcon = icon1;
            searchIcon = icon2;
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (isMUGSNode(value)) {
                setIcon(mugsIcon);
            }
            else if(!leaf && isNotRootNode(value))
            {
                setIcon(searchIcon);
            }
            return this;
        }

        protected boolean isMUGSNode(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            NodeInfo nodeInfo = (NodeInfo) (node.getUserObject());
            String id = nodeInfo.NodeID;
            if (id.compareTo("mugs") == 0) {
                return true;
            }
            return false;
        }

        protected boolean isNotRootNode(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            NodeInfo nodeInfo = (NodeInfo) (node.getUserObject());
            String id = nodeInfo.NodeID;
            if (id.compareTo("root") != 0) {
                return true;
            }
            return false;
        }

    }
}
