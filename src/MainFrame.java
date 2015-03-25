package snpclip;

import java.awt.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Vector;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.BitSet;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.net.URL;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class MainFrame extends javax.swing.JFrame {
    boolean LocationF = false;
    MapLocationDialog MLD = new MapLocationDialog(this, true);
    Vector LocationFilters = new Vector();
    Vector LocationIndexs = new Vector();
    Vector LDDs = new Vector();
    public String Version = "1.0";
    final int MIS_OPTION = 0;
    final int AF_OPTION = 1;
    final int CR_OPTION = 2;
    final int GF_OPTION = 3;
    final int HWE_OPTION = 4;
    final int MI_OPTION = 5;
    final int LD_OPTION = 6;
    final int LOC_OPTION = 7;
    final int LOC_OPTION2 = 8;
    public boolean HapMap = false;
    public boolean BySNP = false;
    public String filePath;
    public String fileName;
    MapFileDialog MFD;
    boolean reset = false;
    String title = "SNPClip 1.0";
    String[] labels = {"Missingness", "Allele Frequency", "Call Rate", "Genotype Frequency",
                      "Departure from HWE proportion", "Mendelian Inconsistency",
                      "Linkage Disequilibrium", "Location", "Region"};
    NumberFormat nf = NumberFormat.getInstance();
    Gauge samplegauge;
    Gauge snpgauge;
    Vector runorder;
    MatrixOperations M = new MatrixOperations();
    MatrixCore MC = new MatrixCore();
    MatrixCore MF = new MatrixCore();
    SAGEMap SM;
    int init_NUMSNPs;
    int init_NUMINDIVIDUALS;
    static int total_tab_count = 1;
    static int CurrentTab = 1;
    Parser Parse = new Parser();
    Mugs Mug = new Mugs();
    ProgressDialog PD = new ProgressDialog(this, true);
    static MainFrame mainFrame1;
    String fmt = "0.0#";
    DecimalFormat df = new DecimalFormat(fmt);

    /** Creates new form MainFrame */
    public MainFrame() {
        mainFrame1 = this;
    }

    public void initializeFrame() {
        samplegauge = new Gauge();
        snpgauge = new Gauge();
        initComponents();
        initMenu();
        initActionListeners();
        setFocusPaintedFalse();
        setEnableFalseComponents();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setMUGSResult(int numSnp, BitSet mugsFilter) {
        ResultPanel resultPanel = new ResultPanel();
        resultPanel.NumSNPs = numSnp;
        Vector data = new Vector();
        Vector each_row = new Vector();
        each_row.add(resultPanel.liststring[0]);
        each_row.add(numSnp);
        data.add(each_row);

        resultPanel.setResultTable(data);
        resultPanel.setFilter(mugsFilter);

        String title = Integer.toString(total_tab_count);
        jTabbedPane1.add(title, resultPanel);
        jTabbedPane1.setTabComponentAt(total_tab_count - 1, new ButtonTabComponent(jTabbedPane1));
        jTabbedPane1.setSelectedIndex(total_tab_count - 1);
        SNPCountText.setText(Integer.toString(numSnp));

        total_tab_count++;
    }

    private void jbInit() throws Exception {

        setTitle(title);

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension wd = t.getScreenSize();
        nf.setMinimumIntegerDigits(nf.getMinimumIntegerDigits());
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    void setEnableFalseComponents() {
        Component[] com = CriteriaMainPanel.getComponents();
        for (int i = 0; i < com.length; i++) {
            com[i].setEnabled(false);
        }
    }

    void setFocusPaintedFalse() {
        Component[] com = CriteriaMainPanel.getComponents();
        for (int i = 0; i < com.length; i++) {
            if (com[i] instanceof JRadioButton) {
                ((JRadioButton) com[i]).setFocusPainted(false);
            }
            if (com[i] instanceof JCheckBox) {
                ((JCheckBox) com[i]).setFocusPainted(false);
            }
        }
        BrowseFileButton.setFocusPainted(false);
        BrowseMapButton.setFocusPainted(false);
        ResetButton.setFocusPainted(false);
        ApplyButton.setFocusPainted(false);
    }

    void initMenu() {
        jMenu1.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileExitActionPerformed(evt);
            }
        });
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuHelpAboutActionPerformed(evt);
            }
        });
        UpdateCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateCheckActionPerformed(evt);
            }
        });

        jMenuFileOpen.setText("Open");
        jMenuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Runnable lookupData = new Runnable() {
                    public void run() {
                        jMenuFileOpenActionPerformed();
                    }
                };

                Thread lookupThread = new Thread(lookupData, "MyThread");
                lookupThread.start();
            }
        });

        jMenuFileExport.setText("Export");
        jMenuFileExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Runnable lookupData = new Runnable() {
                    public void run() {
                        jMenuFileExportActionPerformed();
                    }
                };

                Thread lookupThread = new Thread(lookupData, "MyThread");
                lookupThread.start();
            }
        });

        jMenuTools.setText("Tools");
        jMenuToolsMUGS.setText("MUGS");
        jMenuToolsMUGS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Runnable lookupData = new Runnable() {
                    public void run() {
                        jMenuToolsMUGSActionPerformed();
                    }
                };

                Thread lookupThread = new Thread(lookupData, "MyThread");
                lookupThread.start();
            }
        });

        jMenuBar1.add(jMenu1);
        jMenu1.add(jMenuFileOpen);
        jMenu1.addSeparator();
        jMenu1.add(jMenuFileExport);
        jMenu1.addSeparator();
        jMenu1.add(jMenuFileExit);
        jMenuBar1.add(jMenuTools);
        jMenuBar1.add(jMenuHelp);
        jMenuTools.add(jMenuToolsMUGS);
        jMenuHelp.add(jMenuHelpAbout);
        setJMenuBar(jMenuBar1);
    }

    private void initActionListeners() {
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) ce.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if (index > -1) {
                    ResultPanel rp = (ResultPanel) jTabbedPane1.getSelectedComponent();
                    SNPCountText.setText(Integer.toString(rp.NumSNPs));
                }
                if (index == -1) {
                    ResetButtonActionPerformed();
                }
            }
        });
        ApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Runnable lookupData = new Runnable() {
                    public void run() {
                        ApplyButtonActionPerformed();
                    }
                };

                Thread lookupThread = new Thread(lookupData, "MyThread");
                lookupThread.start();
            }
        });

        BrowseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrowseFileActionPerformed();
            }
        });

        BrowseMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BrowseMapActionPerformed();
            }
        });

        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed();
            }
        });

        MISCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MISCheckBoxActionPerformed();
            }
        });

        AFCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AFCheckBoxActionPerformed();
            }
        });

        LDCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LDCheckBoxActionPerformed();
            }
        });

        RegionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegionCheckBoxActionPerformed();
            }
        });
        SNPCountText.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent event) {
                if (SNPCountText.getText().length() > 0) {
                    int Ex_per_SNIP = Integer.parseInt(SNPCountText.getText());
                    final String percentage = getPercentage(init_NUMSNPs, Ex_per_SNIP);
                    PercentRemainingText.setText(percentage);
                    double temp = Double.parseDouble(percentage);
                    final double angle = temp * 180.0 / 100.0 - 180;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            snpgauge.setAngle(angle);
                        }
                    });
                }
            }

            public void insertUpdate(DocumentEvent event) {
                changedUpdate(event);
            }

            public void removeUpdate(DocumentEvent event) {
                changedUpdate(event);
            }
        });
    }

    private void BrowseFileActionPerformed() {

        SwingWorker worker = new SwingWorker<String, Void>() {
            public String doInBackground() {
                jMenuFileOpenActionPerformed();
                return "";
            }
        };

        worker.execute();
    }


    private void BrowseMapActionPerformed() {
        Runnable lookupData = new Runnable() {
            public void run() {
                try {

                    MapFileDialog MFD = new MapFileDialog(MainFrame.mainFrame1, true);
                    MFD.setLocationRelativeTo(MainFrame.mainFrame1);
                    MFD.setVisible(true);
                } catch (Exception exe) {
                    exe.printStackTrace();

                    return;
                }
            }
        };
        SwingUtilities.invokeLater(lookupData);
    }

    public void MapFileOpenActionPerformed(int type) {
        String init_dit = new String();
        String os_type = System.getProperty("os.name");
        if (os_type.indexOf("Windows") >= 0) {
            init_dit = System.getProperty("user.dir") + System.getProperty("file.separator");
        } else {
            init_dit = System.getProperty("user.home") + System.getProperty("file.separator");
        }
        JFileChooser jFileChooser1 = new JFileChooser();
        jFileChooser1.setCurrentDirectory(new File(init_dit));

        if (jFileChooser1.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
            String MapFilePath = jFileChooser1.getSelectedFile().getPath();
            String MapFileName = jFileChooser1.getSelectedFile().getName();

            SM = Parse.parseMapFile(MapFilePath, type);

            java.util.List subDisList = Parse.findStartEndIndex();

            int startIndex = Parse.GetStartIndex();
            int endIndex = Parse.GetEndIndex();

            if (subDisList != null) {
                double dis = 0.0;

                int k = 0;

                if (startIndex >= 0 && endIndex >= 0) {
                    for (int i = startIndex; i < endIndex; i++) {
                        Double eachDistance = (Double) subDisList.get(k);
                        dis += eachDistance;

                        String value = nf.format(dis);
                        k++;
                    }
                }
            }

             MapFilePathTextField.setText(MapFileName);
            MapFilePathTextField.setToolTipText(MapFilePath);
            RegionCheckBox.setEnabled(true);
        }
    }

    private void MISCheckBoxActionPerformed() {
        if (MISCheckBox.isSelected()) {
            reset = true;
            min_MIS.setText("0");
            max_MIS.setText("1");
            setEnableButton(true, MIS_OPTION);
        } else {
            reset = false;
            min_MIS.setText("");
            max_MIS.setText("");
            setEnableButton(false, MIS_OPTION);
        }
        min_MIS.setEnabled(reset);
        max_MIS.setEnabled(reset);
    }

    private void AFCheckBoxActionPerformed() {
        if (AFCheckBox.isSelected()) {
            reset = true;
            min_AF.setText("0");
            max_AF.setText("0.5");
            setEnableButton(true, AF_OPTION);
        } else {
            reset = false;
            min_AF.setText("");
            max_AF.setText("");
            setEnableButton(false, AF_OPTION);
        }

        min_AF.setEnabled(reset);
        max_AF.setEnabled(reset);
    }

    String getPercentage(int total, int part) {

        double result = ((double) part / (double) total) * 100;

        return df.format(result);
    }

    private void LDCheckBoxActionPerformed() {
        if (HapMap == false) {
            JOptionPane.showOptionDialog(this,
                                         "Warning: LD calculations will only be performed on founders.",
                                         "Warning", // title
                                         JOptionPane.CLOSED_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
        }
        if (LDCheckBox.isSelected()) {
            reset = true;
            min_LD.setText("0");
            max_LD.setText("1");
            setEnableButton(true, LD_OPTION);
        } else {
            reset = false;
            min_LD.setText("");
            max_LD.setText("");
            setEnableButton(false, LD_OPTION);
        }

        min_LD.setEnabled(reset);
        max_LD.setEnabled(reset);
    }


    private void RegionCheckBoxActionPerformed() {
        if (RegionCheckBox.isSelected()) {
            LocationButton.setEnabled(true);
            reset = true;
            if (LocationF == false) {
                LocationF = true;
                MLD = new MapLocationDialog(this, true);
                MapRegion MR;
                for (int i = 0; i < SM.Region.size(); i++) {
                    ((DefaultListModel) MLD.RegionList.getModel()).addElement(SM.Region.get(i));
                    MR = (MapRegion) SM.Regions.get(i);
                    for (int j = 0; j < MR.MarkerList.size(); j++) {
                        ((DefaultListModel) MLD.SNPList.getModel()).addElement(MR.Name + " - " + MR.MarkerList.get(j));
                    }
                }
            }
            setEnableButton(true, LOC_OPTION2);
        } else {
            LocationButton.setEnabled(false);
            reset = false;
            setEnableButton(false, LOC_OPTION2);
        }
    }

    private void ResetButtonActionPerformed() {

        Runnable lookupData = new Runnable() {
            public void run() {
                setEnableFalseComponents();
                setInitUIOption();
                SNPCountText.setText(String.valueOf(Parse.GetNumSNPs()));
                int Ex_per_SNIP = Integer.parseInt(SNPCountText.getText());
                final String percentage = getPercentage(init_NUMSNPs, Ex_per_SNIP);
                PercentRemainingText.setText(percentage);
                jTabbedPane1.removeAll();

                setInitUIOption();
                MISCheckBox.setSelected(false);
                min_MIS.setText("");
                max_MIS.setText("");
                min_MIS.setEnabled(false);
                max_MIS.setEnabled(false);
                AFCheckBox.setSelected(false);
                min_AF.setText("");
                max_AF.setText("");
                min_AF.setEnabled(false);
                max_AF.setEnabled(false);
                LDCheckBox.setSelected(false);
                min_LD.setText("");
                max_LD.setText("");
                min_LD.setEnabled(false);
                max_LD.setEnabled(false);
                if (SM == null) {
                    RegionCheckBox.setEnabled(false);
                } else {
                    RegionCheckBox.setEnabled(true);
                }

                RegionCheckBox.setSelected(false);
                ApplyButton.setEnabled(false);
                total_tab_count = 1;
                double temp = Double.parseDouble(percentage);
                final double angle = temp * 180.0 / 100.0 - 180;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        snpgauge.setAngle(angle);
                    }
                });
            }
        };

        Thread lookupThread = new Thread(lookupData, "MyThread");
        lookupThread.start();
    }

    private void jMenuToolsMUGSActionPerformed() {
        MugsDialog d = new MugsDialog();

        if (Parse.markerList != null && Parse.distanceList != null) {
            d.Mug.markerList = Parse.markerList;
            d.Mug.distanceList = Parse.distanceList;
        }

        BitSet T = new BitSet(MC.GetNUMSNPs());

        if (jTabbedPane1.getSelectedIndex() < 0) {
            d.FI = T;
        } else {

            ResultPanel rp = (ResultPanel) jTabbedPane1.getSelectedComponent();
            T = rp.FI;
            d.FI = T;
        }

        d.setCriteriaItems(Parse.getItemList());
        d.numCaseInd = MC.GetNUMINDIVIDUALS();
        d.CaseSampleLabel.setText("Sample Size : " + MC.GetNUMINDIVIDUALS());
        d.ControlSampleLabel.setText("Sample Size : 0");
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }

    private void jMenuFileExportActionPerformed() {
        int Trans = -1;
        int tabIndex = jTabbedPane1.getSelectedIndex();
        if (tabIndex < 0) {
            return;
        }

        TransposeDialog TD = new TransposeDialog(this, true);
        TD.setLocationRelativeTo(null);
        TD.setVisible(true);
    }

    private void jMenuFileOpenActionPerformed() {
        String init_dit = new String();
        String os_type = System.getProperty("os.name");
        if (os_type.indexOf("Windows") >= 0) {
            init_dit = System.getProperty("user.dir") + System.getProperty("file.separator");
        } else {
            init_dit = System.getProperty("user.home") + System.getProperty("file.separator");
        }
        JFileChooser jFileChooser1 = new JFileChooser();
        jFileChooser1.setCurrentDirectory(new File(init_dit));

        if (jFileChooser1.APPROVE_OPTION == jFileChooser1.showOpenDialog(this)) {
            filePath = jFileChooser1.getSelectedFile().getPath();
            fileName = jFileChooser1.getSelectedFile().getName();

            M = new MatrixOperations(); //create new class.
            Parse = new Parser();

            //original
            SetProgress(0);

            FileInfoDialog fid = new FileInfoDialog(this, "File Information", true);
            try {
                fid.setFilePath(filePath);
                fid.NoteLabel.setText(": " + fileName);
                fid.ParseFile("\t");
                fid.SnpSpinnerRow.setEnabled(false);
                fid.SnpSpinnerRow.setValue(1);
                fid.setLocationRelativeTo(this);
                fid.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fid.returnStatus == 1) {
                M = new MatrixOperations(); //create new class.
                Parse = new Parser();

                Parse.SetAlleleDelimeter(fid.GetDelTextField());
                Parse.SetSNPINDEX(fid.SNP_Col);
                Parse.SetSNPROWINDEX(fid.SNP_Row);
                Parse.SetMissing(fid.SNP_MISSING);

                Parse.SetPedigreeIndex(fid.Pedigree_Col);
                Parse.SetIndividualIndex(fid.Individual_Col);
                PD.OperationLabel.setText("Current Operation: File Import");
                PD.ProgressLabel.setText("Reading in data file: " + fileName + ".");
                Runnable r = new Runnable() {
                    public void run() {
                        PD.setLocationRelativeTo(MainFrame.mainFrame1);
                        PD.setVisible(true);
                    }
                };
                SwingUtilities.invokeLater(r);
                if (Parse.ParseFile(MC, MF, filePath, fid.getFileType(), fid.getDELIMITER(), fid, PD) != 0) {

                    return;
                }

                if (fid.getFileType() == 1) {
                    HapMap = true;
                }
                init_NUMSNPs = MC.GetNUMSNPs();
                init_NUMINDIVIDUALS = MC.GetNUMINDIVIDUALS();
                FilePathTextField.setText(filePath);
                FilePathTextField.setToolTipText(filePath);
                SnpInitNumLabel.setText(Integer.toString(init_NUMSNPs));
                SampleInitNumberLabel.setText(Integer.toString(init_NUMINDIVIDUALS));
                SNPCountText.setText(Integer.toString(init_NUMSNPs));
                setInitUIOption();
                SetProgress(0);
            }
        }
        PD.setVisible(false);
    }


    private void jMenuHelpAboutActionPerformed(ActionEvent actionEvent) {
        AboutBox dlg = new AboutBox(this, true);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);
    }

    private void UpdateCheckActionPerformed(ActionEvent actionEvent) {
        File F;
        URL url;
        try {
            url = new URL("http://darwin.epbi.cwru.edu/SNPClip/update");
            XMLParser XMLP = new XMLParser();
            XMLP.ParseGeneral(url);
            if (Version.compareTo(XMLP.Version) == 0) {
                UpdateDialog UD = new UpdateDialog(this, true);
                UD.StatusLabel.setText("SNPCLip is up to date!");
                UD.ChangeLabel.setText("");
                UD.DownloadURL.setText("");
                UD.pack();
                UD.setLocationRelativeTo(this);
                UD.setVisible(true);
            } else {
                UpdateDialog UD = new UpdateDialog(this, true);
                UD.StatusLabel.setText("SNPClip is out of date.  The current version is: " + XMLP.Version);
                UD.ChangeLabel.setText("Changelog: " + XMLP.Change);
                UD.DownloadURL.setText("Download: " + XMLP.Download);
                UD.pack();
                UD.setLocationRelativeTo(this);
                UD.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void ApplyButtonActionPerformed() {

        FilterInfo FI = new FilterInfo();

        for (int i = 0; i < runorder.size(); i++) {

            Vector rowdata = (Vector) runorder.get(i);

            String label = rowdata.get(1).toString();

            int option = getOptionIndex(label);

            System.out.println(i + " : " + label + " - " + option);

            if (option != -1) {
                switch (option) {
                case MIS_OPTION:
                    FilterMissing(FI);
                    break;
                case AF_OPTION:
                    if (FilterAlleleFreq(FI) == -1) {
                        return;
                    }
                    break;
                case CR_OPTION:
                    break;
                case GF_OPTION:
                    break;
                case HWE_OPTION:
                    break;
                case MI_OPTION:
                    break;
                case LD_OPTION:
                    FilterLD(FI);
                    break;
                case LOC_OPTION:
                    FilterLocation(FI);
                    break;
                case LOC_OPTION2:
                    FilterRegion(FI);
                    break;
                }
            }
        }

        System.out.println("SNPs: " + FI.GetNUMSNPs());
        System.out.println("Filters: ");

        ResultPanel resultPanel = new ResultPanel();
        resultPanel.NumSNPs = FI.GetNUMSNPs();
        Vector data = new Vector();
        Vector each_row = new Vector();
        each_row.add(resultPanel.liststring[0]);
        each_row.add(FI.GetNUMSNPs());
        System.out.print("Operations: ");
        data.add(each_row);
        each_row = new Vector();
        each_row.add("Percent Remaining");
        double percent = ((Double.valueOf(FI.GetNUMSNPs()) / Double.valueOf(SnpInitNumLabel.getText())) * 100);
        String str = df.format(percent);
        each_row.add(str);
        data.add(each_row);
        for (int i = 0; i < FI.GetOperations().size(); i++) {
            each_row = new Vector();
            each_row.add(FI.GetOperations().elementAt(i));
            if (FI.GetOperations().elementAt(i).toString().compareTo("Missingness Filter") == 0) {
                each_row.add("[min: " + min_MIS.getText() + ",max: " + max_MIS.getText() + "]");
            }
            if (FI.GetOperations().elementAt(i).toString().compareTo("Allele Frequency Filter") == 0) {
                each_row.add("[min: " + min_AF.getText() + ",max: " + max_AF.getText() + "]");
            }
            if (FI.GetOperations().elementAt(i).toString().compareTo("LD Filter") == 0) {
                each_row.add("[min: " + min_LD.getText() + ",max: " + max_LD.getText() + "]");
            }
            if (FI.GetOperations().elementAt(i).toString().compareTo("Location Filter") == 0) {
                each_row.add("Enabled");
            }
            data.add(each_row);
            System.out.print(FI.GetOperations().elementAt(i) + " ");

        }

        resultPanel.setResultTable(data);
        resultPanel.setFilter(FI.GetFilter());
        String title = "Filter " + CurrentTab;
        CurrentTab++;
        jTabbedPane1.add(title, resultPanel);
        jTabbedPane1.setTabComponentAt(total_tab_count - 1, new ButtonTabComponent(jTabbedPane1));
        jTabbedPane1.setSelectedIndex(total_tab_count - 1);
        SNPCountText.setText(Integer.toString(FI.GetNUMSNPs()));
        total_tab_count++;
        SetProgress(0);
    }

    private void jMenuFileExitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    void setInitUIOption() {
        MISCheckBox.setEnabled(true);
        AFCheckBox.setEnabled(true);
        LDCheckBox.setEnabled(true);
        runorder = new Vector();

        jMinLabel.setEnabled(true);
        jMaxLabel.setEnabled(true);

        ResetButton.setEnabled(true);

        jMenuToolsMUGS.setEnabled(true);
    }

    private int getOptionIndex(String option) {
        int result = -1;
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].compareTo(option) == 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    void SetProgress(int progress) {
        final int value = progress;
        Runnable r = new Runnable() {
            public void run() {
                setProgressValue(value);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    void setProgressValue(int val) {
        PD.ProgressBar.setValue(val);
    }


    int FilterAlleleFreq(FilterInfo FI) {
        int filter_option = 0;
        try {
            Double min_value = Double.parseDouble(min_AF.getText().trim());
            Double max_value = Double.parseDouble(max_AF.getText().trim());
            if (max_value > 0.5) {
                JOptionPane.showOptionDialog(this,
                                             "The Max value for MAF must be < 0.5.",
                                             "Error", // title
                                             JOptionPane.CLOSED_OPTION,
                                             JOptionPane.ERROR_MESSAGE, null, null, null);
                return -1;
            }

            BitSet E = M.FilterAlleleFreq(MC, min_value, max_value); // Keep if below this precentage
            M.ApplyFilter(MC, E, filter_option, "Allele Frequency Filter", FI); //Apply filter to data set.
        } catch (NumberFormatException en) {
            JOptionPane.showOptionDialog(this,
                                         "The Min and Max fields may contain only numeric characters.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return -2;
        }
        return 0;
    }

    void FilterLocation(FilterInfo FI) {
        int filter_option = 0;
        try {
            // -2 : for "" and "0";
            int minPos = 0;
            int maxPos = 1;

            int startIndex = Parse.GetStartIndex();

            Vector snp_list = Parse.getSnpList();

            System.out.println("minPos : " + minPos);
            System.out.println("maxPos : " + maxPos);
            System.out.println("startIndex : " + startIndex);

            java.util.List subMarkerList = snp_list.subList(minPos + startIndex, maxPos + startIndex);
            System.out.println("subMarkerList : " + subMarkerList);

            BitSet E = M.FilterLocation(MC, subMarkerList, Parse.GetHeader(), Parse.GetSNPINDEX()); // Keep if below this precentage
            M.ApplyFilter(MC, E, filter_option, "Filter Location in centimorgan", FI); //Apply filter to data set.

        } catch (NumberFormatException en) {
            JOptionPane.showOptionDialog(this,
                                         "The Min and Max fields may contain only numeric characters.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
    }

    void FilterRegion(FilterInfo FI) {
        int filter_option = 0;
        try {
            int CurIndex = -1;
            Vector SNPList = new Vector();
            Vector V = new Vector();
            LocationFilter LF;
            Object[] SList = ((DefaultListModel) MLD.RegionOList.getModel()).toArray();
            for (int i = 0; i < SList.length; i++) {
                CurIndex = SM.Region.indexOf(SList[i]);
                MapRegion MR = (MapRegion) SM.Regions.get(CurIndex);
                LF = (LocationFilter) LocationFilters.get(LocationIndexs.indexOf(SList[i]));
                if (LF.StartIndex == -1 && LF.EndIndex == -1) {
                    SNPList.addAll(MR.MarkerList);
                } else {
                    for (int j = LF.StartIndex; j < LF.EndIndex; j++) {
                        SNPList.add(MR.MarkerList.get(j));
                    }
                }
            }
            SList = ((DefaultListModel) MLD.SNPOList.getModel()).toArray();
            for (int i = 0; i < SList.length; i++) {
                SNPList.add(SList[i]);
            }
            BitSet E = M.FilterRegion(MC, SNPList, Parse.GetHeader(), Parse.GetSNPINDEX()); // Keep if below this precentage
            M.ApplyFilter(MC, E, filter_option, "Location Filter", FI); //Apply filter to data set.

        } catch (NumberFormatException en) {
            JOptionPane.showOptionDialog(this,
                                         "The Min and Max fields may contain only numeric characters.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
    }

    void FilterLD(FilterInfo FI) {
        int filter_option = 0;

        try {
            Double min_value = Double.parseDouble(min_LD.getText().trim());
            Double max_value = Double.parseDouble(max_LD.getText().trim());

            BitSet E; // Keep if below this precentage
            E = M.FilterLD(MF, min_value, max_value); // Keep if below this precentage
            M.ApplyFilter(MF, E, filter_option, "LD Filter", FI); //Apply filter to data set.
        } catch (NumberFormatException en) {
            JOptionPane.showOptionDialog(this,
                                         "The Min and Max fields may contain only numeric characters.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
    }

    void FilterMissing(FilterInfo FI) {
        int filter_option = 0;
        try {
            Double min_value = Double.parseDouble(min_MIS.getText().trim());
            Double max_value = Double.parseDouble(max_MIS.getText().trim());

            BitSet E = M.FilterMissingSNPs(MC, min_value, max_value); // Keep if below this precentage
            M.ApplyFilter(MC, E, filter_option, "Missingness Filter", FI); //Apply filter to data set.
        } catch (NumberFormatException en) {
            JOptionPane.showOptionDialog(this,
                                         "The Min and Max fields may contain only numeric characters.",
                                         "Error", // title
                                         JOptionPane.CLOSED_OPTION,
                                         JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
    }

    private void setEnableButton(boolean reset, int option) {
        if (reset) {
            Vector ele = new Vector();
            ele.add(option);
            ele.add(labels[option]);

            runorder.add(ele);
        } else {
            for (int i = 0; i < runorder.size(); i++) {
                Vector rowVector = (Vector) runorder.elementAt(i);
                String filtername = rowVector.get(1).toString();

                if (filtername.compareTo(labels[option]) == 0) {
                    runorder.remove(rowVector);
                }
            }
        }
        runorder.trimToSize();

        if (RunFlag()) {
            ApplyButton.setEnabled(true);
        } else {
            ApplyButton.setEnabled(false);
        }
    }

    private boolean RunFlag() {

        System.out.println("Currently Selected Filters");
        for (int i = 0; i < runorder.size(); i++) {
            System.out.println(runorder.get(i));
        }
        if (runorder.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        FilePathTextField = new javax.swing.JTextField();
        BrowseFileButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MapFilePathLabel = new javax.swing.JLabel();
        MapFilePathTextField = new javax.swing.JTextField();
        BrowseMapButton = new javax.swing.JButton();
        SnpInitNumLabel = new javax.swing.JLabel();
        SampleInitNumberLabel = new javax.swing.JLabel();
        CriteriaMainPanel = new javax.swing.JPanel();
        jMaxLabel = new javax.swing.JLabel();
        jMinLabel = new javax.swing.JLabel();
        min_MIS = new javax.swing.JTextField();
        min_AF = new javax.swing.JTextField();
        min_LD = new javax.swing.JTextField();
        max_MIS = new javax.swing.JTextField();
        max_AF = new javax.swing.JTextField();
        max_LD = new javax.swing.JTextField();
        ApplyButton = new javax.swing.JButton();
        ResetButton = new javax.swing.JButton();
        MISCheckBox = new javax.swing.JCheckBox();
        AFCheckBox = new javax.swing.JCheckBox();
        LDCheckBox = new javax.swing.JCheckBox();
        RegionCheckBox = new javax.swing.JCheckBox();
        LocationButton = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        SnpGaugePanel = new javax.swing.JPanel();
        SNPCountText = new javax.swing.JTextField();
        PercentRemainingText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuFileOpen = new javax.swing.JMenuItem();
        jMenuFileExport = new javax.swing.JMenuItem();
        jMenuFileExit = new javax.swing.JMenuItem();
        jMenuTools = new javax.swing.JMenu();
        jMenuToolsMUGS = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        UpdateCheck = new javax.swing.JMenuItem();
        jMenuHelpAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Source Files"));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 130));

        jLabel8.setText("Data File Path");

        BrowseFileButton.setText("...");
        BrowseFileButton.setPreferredSize(new java.awt.Dimension(40, 23));

        jLabel9.setText("# of SNPs");

        jLabel10.setText("# of Individuals");

        MapFilePathLabel.setText("Map File Path (optional)");

        BrowseMapButton.setText("...");
        BrowseMapButton.setPreferredSize(new java.awt.Dimension(40, 23));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MapFilePathLabel)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(SnpInitNumLabel, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                    .addComponent(MapFilePathTextField, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                    .addComponent(FilePathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 325,
                                                  Short.MAX_VALUE)
                                    .addComponent(SampleInitNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 325,
                                                  Short.MAX_VALUE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                              .addComponent(BrowseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addGap(9, 9, 9))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                              .addComponent(BrowseMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addContainerGap())))
                );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                          .addGap(22, 22, 22)
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BrowseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(FilePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(MapFilePathLabel)
                                    .addComponent(MapFilePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BrowseMapButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(SnpInitNumLabel))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(SampleInitNumberLabel))
                          .addContainerGap(59, Short.MAX_VALUE))
                );

        CriteriaMainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("SNP Selection Criteria"));

        jMaxLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMaxLabel.setText("Max");

        jMinLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMinLabel.setText("Min");

        min_MIS.setPreferredSize(new java.awt.Dimension(60, 20));

        ApplyButton.setText("Apply");
        ApplyButton.setPreferredSize(new java.awt.Dimension(73, 25));

        ResetButton.setText("Reset");
        ResetButton.setPreferredSize(new java.awt.Dimension(73, 25));

        MISCheckBox.setText("Retain SNPs whose missingness proportion lies inside the range:");

        AFCheckBox.setText("Retain SNPs whose minor allele frequency lies inside the range:");

        LDCheckBox.setText("Retain SNPs whose pairwise LD value lies inside the range:");

        RegionCheckBox.setText("Retain SNPs that lie within one or more specified genomic regions:");

        LocationButton.setText("...");
        LocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LocationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CriteriaMainPanelLayout = new javax.swing.GroupLayout(CriteriaMainPanel);
        CriteriaMainPanel.setLayout(CriteriaMainPanelLayout);
        CriteriaMainPanelLayout.setHorizontalGroup(
                CriteriaMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING)
                                    .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                                              .addComponent(ResetButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329,
                Short.MAX_VALUE)
                                              .addComponent(ApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.LEADING)
                .addComponent(LDCheckBox)
                .addComponent(AFCheckBox)
                .addComponent(MISCheckBox)
                .addComponent(RegionCheckBox))
                                              .addGap(8, 8, 8)
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.LEADING)
                .addComponent(LocationButton)
                .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                          .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING, false)
                                    .addComponent(min_MIS, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(min_AF, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(min_LD, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(jMinLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING, false)
                                    .addComponent(max_AF, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(max_MIS, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(max_LD, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(jMaxLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                          .addContainerGap())
                );
        CriteriaMainPanelLayout.setVerticalGroup(
                CriteriaMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.
                LEADING)
                                    .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.BASELINE)
                .addComponent(jMinLabel)
                .addComponent(jMaxLabel))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(max_MIS, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(max_AF, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(max_LD, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CriteriaMainPanelLayout.createSequentialGroup()
                                              .addGap(19, 19, 19)
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.BASELINE)
                .addComponent(MISCheckBox)
                .addComponent(min_MIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.BASELINE)
                .addComponent(AFCheckBox)
                .addComponent(min_AF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.BASELINE)
                .addComponent(LDCheckBox)
                .addComponent(min_LD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.BASELINE)
                .addComponent(RegionCheckBox)
                .addComponent(LocationButton))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76,
                Short.MAX_VALUE)
                                              .addGroup(CriteriaMainPanelLayout.createParallelGroup(javax.swing.
                GroupLayout.Alignment.BASELINE)
                .addComponent(ResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                              javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(ApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                              javax.swing.GroupLayout.PREFERRED_SIZE))))
                          .addContainerGap())
                );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));
        jPanel10.setPreferredSize(new java.awt.Dimension(330, 100));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("SNP Information"));
        jPanel9.setPreferredSize(new java.awt.Dimension(330, 100));

        SnpGaugePanel = new javax.swing.JPanel();
        SnpGaugePanel.setBorder(new DropShadowBorder(Color.BLACK, 0, 5, .5f, 12, false, true, true, true));
        SnpGaugePanel.setLayout(new BorderLayout());
        SnpGaugePanel.setPreferredSize(new Dimension(150, 90));
        SnpGaugePanel.add(snpgauge, BorderLayout.CENTER);
        SnpGaugePanel.setBackground(new java.awt.Color(255, 255, 255));

        SNPCountText.setEditable(false);
        SNPCountText.setText("Number Remaining");

        PercentRemainingText.setEditable(false);
        PercentRemainingText.setText("Percent Remaining");

        jLabel3.setText("Count");

        jLabel4.setText("% Remaining");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                          .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                          .addGap(19, 19, 19)
                          .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PercentRemainingText)
                                    .addComponent(SNPCountText, javax.swing.GroupLayout.DEFAULT_SIZE, 162,
                                                  Short.MAX_VALUE))
                          .addGap(18, 18, 18)
                          .addComponent(SnpGaugePanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap(42, Short.MAX_VALUE))
                );
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                          .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                              .addGap(19, 19, 19)
                                              .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(SNPCountText, javax.swing.GroupLayout.PREFERRED_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.
                Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(PercentRemainingText, javax.swing.GroupLayout.PREFERRED_SIZE,
                              javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(SnpGaugePanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING,
                                                  javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                          .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 121,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap())
                );

        jMenu1.setText("File");

        jMenuFileOpen.setText("Open");
        jMenu1.add(jMenuFileOpen);

        jMenuFileExport.setText("Export");
        jMenu1.add(jMenuFileExport);

        jMenuFileExit.setText("Exit");
        jMenu1.add(jMenuFileExit);

        jMenuBar1.add(jMenu1);

        jMenuTools.setText("Tools");

        jMenuToolsMUGS.setText("MUGS");
        jMenuToolsMUGS.setEnabled(false);

        jMenuHelp.setText("Help");

        UpdateCheck.setText("Check For Updates");
        jMenuHelp.add(UpdateCheck);

        jMenuHelpAbout.setText("About");
        jMenuHelp.add(jMenuHelpAbout);

        jMenuBar1.add(jMenuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                              .addGap(10, 10, 10)
                                              .addComponent(CriteriaMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 521,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                          .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 481,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                          .addContainerGap())
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                          .addContainerGap()
                          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 483,
                                                  javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                              .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 203,
                javax.swing.GroupLayout.PREFERRED_SIZE)
                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                              .addComponent(CriteriaMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

        pack();
    }


    private void LocationButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MLD.setLocationRelativeTo(MainFrame.mainFrame1);
        MLD.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JCheckBox AFCheckBox;
    private javax.swing.JButton ApplyButton;
    private javax.swing.JButton BrowseFileButton;
    private javax.swing.JButton BrowseMapButton;
    private javax.swing.JPanel CriteriaMainPanel;
    private javax.swing.JTextField FilePathTextField;
    private javax.swing.JCheckBox LDCheckBox;
    private javax.swing.JButton LocationButton;
    private javax.swing.JCheckBox MISCheckBox;
    private javax.swing.JLabel MapFilePathLabel;
    private javax.swing.JTextField MapFilePathTextField;
    private javax.swing.JTextField PercentRemainingText;
    private javax.swing.JCheckBox RegionCheckBox;
    private javax.swing.JButton ResetButton;
    private javax.swing.JTextField SNPCountText;
    private javax.swing.JPanel SnpGaugePanel;
    private javax.swing.JMenuItem UpdateCheck;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jMaxLabel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuFileExit;
    private javax.swing.JMenuItem jMenuFileExport;
    private javax.swing.JMenuItem jMenuFileOpen;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuHelpAbout;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JMenuItem jMenuToolsMUGS;
    private javax.swing.JLabel jMinLabel;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField max_AF;
    private javax.swing.JTextField max_LD;
    private javax.swing.JTextField max_MIS;
    private javax.swing.JTextField min_AF;
    private javax.swing.JTextField min_LD;
    private javax.swing.JTextField min_MIS;
    private javax.swing.JLabel SampleInitNumberLabel;
    private javax.swing.JLabel SnpInitNumLabel;

    // End of variables declaration

}
