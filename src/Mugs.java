package snpclip;

import java.util.Vector;
import java.util.Collections;
import javax.swing.DefaultListModel;
import java.util.BitSet;

public class Mugs {
    Progress P = new Progress();

    int[] SnpAllFlag;
    boolean[] CaseHomozygoteFlag;
    boolean[] ControlHomozygoteFlag;

    static int matchCode = 1;
    static int subsetCode = 2;
    static int homozygoteCode = 3;

    Vector markerList;
    Vector distanceList;

    static int INIT = -100;

    BitSet currentSnpFilter;
    int caseNumInd;
    int controlNumInd;

    int CaseDomMajor[][];
    int CaseDomMinor[][];

    int CaseDomMajorSum[];
    int CaseDomMinorSum[];

    int ControlDomMajor[][];
    int ControlDomMinor[][];
    int ControlDomMajorSum[];
    int ControlDomMinorSum[];

    int CaseMax[];
    BitSet CaseMaxfromMajor;
    int ControlMax[];
    BitSet ControlMaxfromMajor;

    int numSnpOriginal;
    boolean CaseHomozygote[][];
    boolean ControlHomozygote[][];

    boolean[] caseIndFlag;
    boolean[] controlIndFlag;

    Vector pedigreeAllList;
    Vector individualAllList;

    boolean initState = false;

    public static final int DIS_BOTH = 0;
    public static final int DIS_N_ONLY = 1;
    public static final int DIS_N_1_ONLY = 2;


    public Mugs() {
    }

     public int setListofCaseGroupSamples(DefaultListModel listModel) {
        int criteria_count = listModel.getSize();

        caseIndFlag = new boolean[MainFrame.mainFrame1.MC.GetNUMINDIVIDUALS()];
        for (int j = 0; j < caseIndFlag.length; j++) {
            caseIndFlag[j] = true;
        }

        for (int i = 0; i < criteria_count; i++) {

            ListDataModel in = (ListDataModel) listModel.get(i);

            boolean eachflag[] = getEachofGroupSamples(in);

            for (int j = 0; j < caseIndFlag.length; j++) {
                    caseIndFlag[j] = caseIndFlag[j] && eachflag[j];
          }
        }

        caseNumInd = 0;
        for (int j = 0; j < caseIndFlag.length; j++) {
            if (caseIndFlag[j])
                caseNumInd++;
        }
        return caseNumInd;
    }

    public int setListofControlGroupSamples(DefaultListModel listModel) {
        int criteria_count = listModel.getSize();

        controlIndFlag = new boolean[MainFrame.mainFrame1.MC.GetNUMINDIVIDUALS()];
        for (int j = 0; j < controlIndFlag.length; j++) {
            controlIndFlag[j] = true;
        }

        for (int i = 0; i < criteria_count; i++) {

            ListDataModel in = (ListDataModel) listModel.get(i);

            boolean eachflag[] = getEachofGroupSamples(in);

            for (int j = 0; j < controlIndFlag.length; j++) {
                    controlIndFlag[j] = controlIndFlag[j] && eachflag[j];
          }
        }

        controlNumInd = 0;

        if(criteria_count > 0)
        {
            for (int j = 0; j < controlIndFlag.length; j++) {
                if (controlIndFlag[j])
                    controlNumInd++;
            }
        }


       return controlNumInd;
    }


    public boolean[] getEachofGroupSamples(ListDataModel in) {
        String col_name = in.variable;
        int operator = in.operator;
        String value = in.value;

        int col_index = 0;
        for (int j = 0; j < MainFrame.mainFrame1.Parse.GetSNPINDEX(); j++) {
            if (col_name.compareTo(MainFrame.mainFrame1.Parse.GetHeader()[j]) == 0) {
                col_index = j;
                break;
            }
        }

        Vector data = MainFrame.mainFrame1.Parse.GetCriteriaDataIndex(col_index);

        boolean[] flag = new boolean[MainFrame.mainFrame1.MC.GetNUMINDIVIDUALS()];
        for (int j = 0; j < flag.length; j++) {
            flag[j] = false;
        }

        for (int j = 0; j < data.size(); j++) {
            switch (operator) {
            case 0: // =
                if (data.get(j).toString().compareTo(value) == 0)
                    flag[j] = true;
                break;
            case 1: // <
                if (data.get(j).toString().compareTo(value) < 0)
                    flag[j] = true;
                break;
            case 2: // >
                if (data.get(j).toString().compareTo(value) > 0)
                    flag[j] = true;
                break;
            case 3: // <=
                if (data.get(j).toString().compareTo(value) <= 0)
                    flag[j] = true;
                break;
            case 4: // >=
                if (data.get(j).toString().compareTo(value) >= 0)
                    flag[j] = true;
                break;
            case 5: // <>
                if (data.get(j).toString().compareTo(value) != 0)
                    flag[j] = true;
                break;
            }
        }

        return flag;
    }

    /** ************************************************************************* **
     ** *****************       DoMUGS       ***************** **
     ** ************************************************************************** **
     * Description: Do MUGS
     * Used By: MugsDialog Class
     * Input:  Bitset of Filter.
     * Output: Int number of SNPs currently enabled.
     ----------------------------------------------------------------------------- */

    public void InitMUGS(BitSet filter)
    {
        numSnpOriginal = MainFrame.mainFrame1.MC.GetNUMSNPs();
        currentSnpFilter = filter;

        CaseDomMajor = new int[caseNumInd][numSnpOriginal];
        CaseDomMinor = new int[caseNumInd][numSnpOriginal];
        CaseDomMajorSum = new int[numSnpOriginal];
        CaseDomMinorSum = new int[numSnpOriginal];

        ControlDomMajor = new int[controlNumInd][numSnpOriginal];
        ControlDomMinor = new int[controlNumInd][numSnpOriginal];
        ControlDomMajorSum = new int[numSnpOriginal];
        ControlDomMinorSum = new int[numSnpOriginal];

        CaseMax = new int[numSnpOriginal];
        CaseMaxfromMajor = new BitSet(numSnpOriginal);

        ControlMax = new int[numSnpOriginal];
        ControlMaxfromMajor = new BitSet(numSnpOriginal);

        SnpAllFlag = new int[numSnpOriginal];
        CaseHomozygoteFlag = new boolean[numSnpOriginal];
        ControlHomozygoteFlag = new boolean[numSnpOriginal];

        CaseHomozygote = new boolean[caseNumInd][numSnpOriginal];
        ControlHomozygote = new boolean[controlNumInd][numSnpOriginal];

        for (int j = 0; j < numSnpOriginal;j++)
        {
            SnpAllFlag[j] = 0;
            CaseHomozygoteFlag[j] = false;
            ControlHomozygoteFlag[j] = false;
            CaseMax[j]=INIT;
        }

        pedigreeAllList = (Vector) MainFrame.mainFrame1.Parse.GetAllPedigreeList();
        individualAllList = (Vector) MainFrame.mainFrame1.Parse.GetAllIndividualList();

        CalculateDomRecFunction();
        SumDomRecFunction();
        SetAllSnpFlag();

        initState = true;
    }

    public void SetAllSnpFlag()
    {
        // All snp visulaizer
        for (int j = 0; j < numSnpOriginal; j++)
        {
            if (!currentSnpFilter.get(j))
            {
                int each = CaseMax[j];
                if (each == caseNumInd) // N
                    SnpAllFlag[j] = 2; // Green
                else if (each == caseNumInd - 1) // N-1
                    SnpAllFlag[j] = 1; // Red
                else if (CaseHomozygoteFlag[j]) {
                    SnpAllFlag[j] = 3; // Yellow
                }
            }
        }
    }


    public Vector GetMUGSResults(int disOption)
    {

        Vector pedigreeCaseFlagList = new Vector();
        Vector individualCaseFlagList = new Vector();
        Vector pedigreeControlFlagList = new Vector();
        Vector individualControlFlagList = new Vector();

        for (int i = 0; i < caseIndFlag.length; i++) {
            if (caseIndFlag[i]) {
                pedigreeCaseFlagList.add(pedigreeAllList.get(i));
                individualCaseFlagList.add(individualAllList.get(i));
            }
        }
        for (int i = 0; i < controlIndFlag.length; i++) {
            if (controlIndFlag[i]) {
                pedigreeControlFlagList.add(pedigreeAllList.get(i));
                individualControlFlagList.add(individualAllList.get(i));
            }
        }

        int length = 0;
        int startIndex = 0;
        int endIndex = 0;
        int value = 0;

        int CaseSnpInd[] = null;
        int ControlSnpInd[] = null;

        String SnpNameList[] = null;

        String startName;
        String endName;

        String HeaderList[] = MainFrame.mainFrame1.Parse.GetHeader();
        int snpIndex = MainFrame.mainFrame1.Parse.GetSNPINDEX();

        Vector result = getLongestSubsequence(disOption);

        for (int j = 0; j < result.size(); j++)
        {
            Position t = (Position) result.get(j);

            length = t.getLength();
            startIndex = t.getStartIndex();
            endIndex = t.getEndIndex();

            CaseSnpInd = new int[length];

            SnpNameList = new String[length];

            if (controlNumInd > 0)
                ControlSnpInd = new int[length];

            double geneticLength = 0.0;

            int k = startIndex - 1;

            for (int l = startIndex; l <= endIndex; l++) {

                if (!currentSnpFilter.get(l)) {
                    k++;
                    SnpNameList[k - startIndex] = HeaderList[l + snpIndex - 1];
                }

                //for CASE
                if (CaseMax[l] == caseNumInd) {
                    if(disOption == DIS_N_1_ONLY)
                        CaseSnpInd[k - startIndex] = homozygoteCode;
                    else
                        CaseSnpInd[k - startIndex] = matchCode;
                }
                else if (CaseMax[l] == (caseNumInd - 1)) {
                    if(disOption == DIS_N_ONLY)
                        CaseSnpInd[k - startIndex] = homozygoteCode;
                    else
                        CaseSnpInd[k - startIndex] = subsetCode;

                    for (int i = 0; i < caseNumInd; i++) {

                        if (CaseMaxfromMajor.get(l))
                            value = CaseDomMajor[i][l];
                        else
                            value = CaseDomMinor[i][l];

                        if (value == 0) {
                            t.setCaseMissingList(pedigreeCaseFlagList.get(i) + ":" + individualCaseFlagList.get(i));
                            break;
                        }
                    }
                }
                else if (CaseHomozygoteFlag[l]) {
                    CaseSnpInd[k - startIndex] = homozygoteCode;
                }

                //for CONTROL
                if (controlNumInd > 0) {
                    if (ControlMax[l] == controlNumInd) {
                        if(disOption == DIS_N_1_ONLY)
                            ControlSnpInd[k - startIndex] = homozygoteCode;
                        else
                            ControlSnpInd[k - startIndex] = matchCode;
                    }
                    else if (ControlMax[l] == (controlNumInd - 1)) {
                        if(disOption == DIS_N_ONLY)
                            ControlSnpInd[k - startIndex] = homozygoteCode;
                        else
                            ControlSnpInd[k - startIndex] = subsetCode;

                        for (int i = 0; i < caseNumInd; i++) {

                            if (ControlMaxfromMajor.get(l))
                                value = ControlDomMajor[i][l];
                            else
                                value = ControlDomMinor[i][l];

                            if (value == 0) {
                                t.setControlMissingList(pedigreeControlFlagList.get(i) + ":" + individualControlFlagList.get(i));
                                break;
                            }
                        }
                    }
                    else if (ControlHomozygoteFlag[l]) {
                        ControlSnpInd[k - startIndex] = homozygoteCode;
                    }
                }

            }

            startName = HeaderList[startIndex + snpIndex - 1];
            endName = HeaderList[endIndex + snpIndex - 1];

            t.setStartName(startName);
            t.setEndName(endName);
            t.setCaseSnpFlag(CaseSnpInd);
            t.setControlSnpFlag(ControlSnpInd);
            t.setSnpNameList(SnpNameList);

            if (distanceList != null) {
                    double sumDistance = getSumofDistance(startName, endName);
                    geneticLength = sumDistance;
            }
            t.setgeneticLength(geneticLength);
        }

        // Remove sequences that only contain Homozygote region
        for (int i = 0; i < result.size(); i++) {
            Position current = (Position) result.get(i);

            int flag[] = current.getCaseSnpFlag();
            int count = 0;

            for (int j = 0; j < flag.length; j++) {
                if (flag[j] == homozygoteCode)
                    count++;
            }

            if (count == flag.length)
                result.remove(i);
        }

        //Sort and set ID
        int LengthColumn = 4;
        Collections.sort(result, new MugsComparator(LengthColumn, false));

        for (int j = 0; j < result.size(); j++)
        {
            Position t = (Position) result.get(j);
            t.setID("Seq"+(j+1));
        }

        return result;
    }

    /** ************************************************************************* **
     ** *****************       CalculateDominanceFunction       ***************** **
     ** ************************************************************************** **
     * Description: Calculates number of SNPs currently in use by Filter
     * Used By: Main Class
     * Input:  Bitset of Filter.
     * Output: Int number of SNPs currently enabled.
     ----------------------------------------------------------------------------- */
    public void CalculateDomRecFunction() {

        int domMajorValue = 1;
        int domMinorvalue = 1;

        boolean AlleleFlag1 = false;
        boolean AlleleFlag2 = false;
        boolean AlleleMissingFlag1 = false;
        boolean AlleleMissingFlag2 = false;

        int case_ind_count = 0;
         int control_ind_count = 0;

         boolean case_ind = false;
         boolean control_ind = false;

        for (int j = 0; j < numSnpOriginal; j++)
        {
            case_ind_count = 0;
            control_ind_count = 0;

            if (!currentSnpFilter.get(j)) // only for the filtered snip
            {
                for (int i = 0; i < MainFrame.mainFrame1.Parse.GetSubjects().size()*2; i=i+2)
                {
                    if (caseIndFlag[i/2]) // case individuals
                    {
                        BitSet Info = (BitSet) MainFrame.mainFrame1.MC.SNPs.elementAt(j);
                        BitSet Error = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.elementAt(j);

                        domMajorValue = 0;
                        domMinorvalue = 0;

                        AlleleFlag1 = Info.get(i);
                        AlleleFlag2 = Info.get(i+1);

                        AlleleMissingFlag1 = Error.get(i);
                        AlleleMissingFlag2 = Error.get(i+1);

                        boolean domMajor = AlleleFlag1 || AlleleFlag2;
                        boolean domMinor = !(AlleleFlag1 && AlleleFlag2);

                        if (domMajor)
                            domMajorValue = 1;

                        if (domMinor)
                            domMinorvalue = 1;

                        if (AlleleMissingFlag1 || AlleleMissingFlag2) {
                            domMajorValue = 1;
                            domMinorvalue = 1;
                        }

                        CaseDomMajor[case_ind_count][j] = domMajorValue;
                        CaseDomMinor[case_ind_count][j] = domMinorvalue;

                        case_ind = true;

                        //for Homozygote
                        CaseHomozygote[case_ind_count][j] = !(AlleleFlag1 ^ AlleleFlag2);
                    }
                    else
                        case_ind = false;

                    if(controlNumInd>0)
                    {
                        if (controlIndFlag[i/2]) // control individuals
                        {
                            BitSet Info = (BitSet) MainFrame.mainFrame1.MC.SNPs.elementAt(j);
                            BitSet Error = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.elementAt(j);

                            domMajorValue = 0;
                            domMinorvalue = 0;

                            AlleleFlag1 = Info.get(i);
                            AlleleFlag2 = Info.get(i+1);

                            AlleleMissingFlag1 = Error.get(i);
                            AlleleMissingFlag2 = Error.get(i+1);

                            boolean domMajor = AlleleFlag1 || AlleleFlag2;
                            boolean domMinor = !(AlleleFlag1 && AlleleFlag2);

                            if (domMajor)
                                domMajorValue = 1;

                            if (domMinor)
                                domMinorvalue = 1;

                            if (AlleleMissingFlag1 || AlleleMissingFlag2) {
                                domMajorValue = 1;
                                domMinorvalue = 1;
                            }

                            ControlDomMajor[control_ind_count][j] = domMajorValue;
                            ControlDomMinor[control_ind_count][j] = domMinorvalue;

                            control_ind = true;

                            //for Homozygote
                            ControlHomozygote[control_ind_count][j] = !(AlleleFlag1 ^ AlleleFlag2);
                        }
                        else
                        control_ind = false;
                    }

                    if(case_ind)
                        case_ind_count++;
                    if(control_ind)
                        control_ind_count++;
                }
            }
        }

    }


   public void SumDomRecFunction()
   {
             // System.out.println("----- CASE -----");
              for (int j = 0; j < numSnpOriginal; j++)
              {
                  if (!currentSnpFilter.get(j))
                  {
                      for(int i=0;i<caseNumInd;i++)
                      {
                          CaseDomMajorSum[j] = CaseDomMajorSum[j] + CaseDomMajor[i][j];
                          CaseDomMinorSum[j] = CaseDomMinorSum[j] + CaseDomMinor[i][j];

                          CaseMax[j] = Math.max(CaseDomMajorSum[j], CaseDomMinorSum[j]);

                          if(CaseMax[j] == CaseDomMajorSum[j])
                              CaseMaxfromMajor.set(j, true);
                      }
                  }
             }

              //System.out.println("----- CONTROL -----");
              for (int j = 0; j < numSnpOriginal; j++)
              {
                  if (!currentSnpFilter.get(j))
                  {
                      for (int i = 0; i < controlNumInd; i++)
                      {
                          ControlDomMajorSum[j] = ControlDomMajorSum[j] + ControlDomMajor[i][j];
                          ControlDomMinorSum[j] = ControlDomMinorSum[j] + ControlDomMinor[i][j];

                          ControlMax[j] = Math.max(ControlDomMajorSum[j], ControlDomMinorSum[j]);

                          if (ControlMax[j] == ControlDomMajorSum[j])
                              ControlMaxfromMajor.set(j, true);
                      }
                  }
              }

              //System.out.println("----- CASE -----");
              for (int j = 0; j < numSnpOriginal; j++)
              {
                  int homozygote = 0;
                  for(int i=0;i<caseNumInd;i++)
                  {
                      if(CaseHomozygote[i][j])
                          homozygote++;

                      if(homozygote == caseNumInd)
                          CaseHomozygoteFlag[j] = true;
                      else
                          CaseHomozygoteFlag[j] = false;
                  }
              }

              //System.out.println("/n----- CONTROL -----");
              for (int j = 0; j < numSnpOriginal; j++)
              {
                  int homozygote = 0;
                  for(int i=0;i<controlNumInd;i++)
                  {
                      if(ControlHomozygote[i][j])
                          homozygote++;

                      if(homozygote == controlNumInd)
                          ControlHomozygoteFlag[j] = true;
                      else
                          ControlHomozygoteFlag[j] = false;
                  }
        }
   }

    public double getSumofDistance(String startName, String endName)
    {
        double dis = 0.0;

        int startIndex = markerList.indexOf(startName);
        int endIndex = markerList.indexOf(endName);

        if (startIndex >= 0 && endIndex >= 0) {
            for (int i = startIndex; i < endIndex; i++) {

                Double eachDistance = (Double) distanceList.get(i);
                dis += eachDistance;
            }
        }
        return dis;
    }


    public int[] getAllSnpFlag() {
        return SnpAllFlag;
    }


    private Vector getNSequencesOnly()
    {
        int input[] = CaseMax;
        int value = caseNumInd;

        Vector r = new Vector();
        int length = 0;
        Position seq;

        for (int i = 0; i < input.length; ) {
            length = 0;
            int init_k = input[i];

            if (init_k == value || CaseHomozygoteFlag[i]) {
                seq = new Position();
                seq.setStartIndex(i);

                for (int j = i; j < input.length; j++) {
                    int next_k = input[j];

                    if(next_k == INIT)//snp was clipped out with filtering.
                    {
                        boolean con = false;
                        int INITcount = 0;
                       for (int k = j; k < input.length; k++)
                       {
                               if(input[k] == INIT)
                               {
                                   INITcount++;
                                   continue;
                               }
                               else if(input[k] == value || CaseHomozygoteFlag[k])
                               {
                                   con = true;
                                   break;
                               }
                               else
                               {
                                   con = false;
                                   break;
                               }
                       }

                       if(con)
                       {
                           i = j + INITcount;
                           continue;
                       }
                       else
                       {
                            seq.setEndIndex(j - 1);
                            i = j + INITcount;
                            break;
                        }
                    }

                    else if (next_k == value || CaseHomozygoteFlag[i])
                    {
                        seq.setEndIndex(j);
                        i = j + 1;
                        length ++;
                    }
                    else {
                        seq.setEndIndex(j - 1);
                        i++;
                        break;
                    }
                }

                seq.setLength(length);
                r.add(seq);
            } else {
                i++;
            }
            seq = null;
        }

        return r;
    }

    private Vector getN_1SequencesOnly()
    {
        int input[] = CaseMax;
        int value = caseNumInd;

        Vector r = new Vector();
        int length = 0;
        Position seq;

        for (int i = 0; i < input.length; ) {
            length = 0;
            int init_k = input[i];


            if (init_k == value - 1 || CaseHomozygoteFlag[i]) {
                seq = new Position();
                seq.setStartIndex(i);

                for (int j = i; j < input.length; j++) {
                    int next_k = input[j];

                    if(next_k == INIT)//snp was clipped out with filtering.
                    {
                        boolean con = false;
                        int INITcount = 0;
                       for (int k = j; k < input.length; k++)
                       {
                               if(input[k] == INIT)
                               {
                                   INITcount++;
                                   continue;
                               }
                               else if(input[k] == value - 1 || CaseHomozygoteFlag[k])
                               {
                                   con = true;
                                   break;
                               }
                               else
                               {
                                   con = false;
                                   break;
                               }
                       }

                       if(con)
                       {
                           i = j + INITcount;
                           continue;
                       }
                       else
                       {
                            seq.setEndIndex(j - 1);
                            i = j + INITcount;
                            break;
                        }
                    }

                    else if (next_k == value - 1 || CaseHomozygoteFlag[i])
                    {
                        seq.setEndIndex(j);
                        i = j + 1;
                        length ++;
                    }
                    else {
                        seq.setEndIndex(j - 1);
                        i++;
                        break;
                    }
                }

                seq.setLength(length);
                r.add(seq);
            } else {
                i++;
            }
            seq = null;
        }

        return r;
    }

    private Vector getBothSequences()
    {
        int input[] = CaseMax;
        int value = caseNumInd;

        Vector r = new Vector();
        int length = 0;
        Position seq;

        for (int i = 0; i < input.length; ) {
            length = 0;
            int init_k = input[i];

            if (init_k == value || init_k == value - 1 || CaseHomozygoteFlag[i]) {
                seq = new Position();
                seq.setStartIndex(i);

                for (int j = i; j < input.length; j++) {
                    int next_k = input[j];

                    if(next_k == INIT)//snp was clipped out with filtering.
                    {
                        boolean con = false;
                        int INITcount = 0;
                       for (int k = j; k < input.length; k++)
                       {
                               if(input[k] == INIT)
                               {
                                   INITcount++;
                                   continue;
                               }
                               else if(input[k] == value || input[k] == value - 1 || CaseHomozygoteFlag[k])
                               {
                                   con = true;
                                   break;
                               }
                               else
                               {
                                   con = false;
                                   break;
                               }
                       }

                       if(con)
                       {
                           i = j + INITcount;
                           continue;
                       }
                       else
                       {
                            seq.setEndIndex(j - 1);
                            i = j + INITcount;
                            break;
                        }
                    }

                    else if (next_k == value || next_k == value - 1 || CaseHomozygoteFlag[i]) {
                        seq.setEndIndex(j);
                        i = j + 1;
                        length ++;
                    }
                    else {
                        seq.setEndIndex(j - 1);
                        i++;
                        break;
                    }
                }

                seq.setLength(length);
                r.add(seq);
            } else {
                i++;
            }
            seq = null;
        }

        return r;
    }


    public Vector getLongestSubsequence(int disOption) {

        if (CaseMax.length <= 1)
            return null;

        if(disOption == DIS_BOTH)
        {
            return getBothSequences();
        }
        else if(disOption == DIS_N_ONLY)
        {
            return getNSequencesOnly();
        }
        else
        {
            return getN_1SequencesOnly();
        }
    }
}
