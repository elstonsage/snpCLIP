package snpclip;

import java.util.Vector;
import java.util.List;
import java.io.BufferedReader;
import java.util.BitSet;
import java.io.IOException;
import java.io.FileReader;
import java.util.Vector;
import java.io.*;
import java.lang.*;

public class Parser {
    String TWO = "2";
    String ONE = "1";
    String THREE = "3";
    String FOUR = "4";
    String ZERO = "0";
    String SLASH = "/";
    String TAB = "\t";
    private String AlleleDelimeter = "";
    public int Ftype = -1;
    private int NUMSNPs;
    private int NUMALLELES;
    private int SNPLENGTH;
    private int NUMINDIVIDUALS;
    private int FNUMINDIVIDUALS;
    private String MISSING;
    private String MISSINGVALUE = ".";
    private Vector Subjects = new Vector();
    Progress P = new Progress();
    private Vector SNPs = new Vector();
    private Vector Keys = new Vector();
    private String[] Header;
    private int SNPINDEX = 6;
    private int SNPROWINDEX = 8;
    int PedigreeIndex = 1;
    int IndividualIndex = 2;
    private Vector FileHeaders = new Vector();
    public Vector<Integer> HapMapSNPs = new Vector();
    public Vector<Integer> HapMapLocs = new Vector();

    // Index for marker list in Map file with regard to Data file
    Vector markerList;
    Vector distanceList;
    private int startIndex = 0;
    private int endIndex = 0;
    SAGEMap M = new SAGEMap();
    SAGEMap HSM = new SAGEMap();

    String FileDelimeter = "";
    String DataFilePath = "";

    private Vector CriteriaDataByColumn = new Vector();
    private Vector CriteriaDataByRow = new Vector();
    private Vector GlobalMapMarkers = new Vector();


    public Parser() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /** ************************************************************************* **
     ** *****************          RemoveDelimators                 ***************** **
     ** ************************************************************************** **
     * Description: Removes delimator from a specified string
     * Used by: ParseFile to remove slashes from the raw SNP data.
     * Input: String
     * Output: String
     ----------------------------------------------------------------------------- */
    private String RemoveDelimators(String L) {

        String Stripped = "";
        for (int i = 0; i < L.length(); i++) {
            switch (L.substring(i, i + 1).compareTo(AlleleDelimeter)) {
            case 0:
                break;
            default:
                switch (L.substring(i, i + 1).compareTo(TAB)) {
                case 0:
                    break;
                default:
                    Stripped = Stripped + L.substring(i, i + 1);
                    break;
                }
            }

        }
        return Stripped;
    }

    /** ************************************************************************* **
     ** *****************          CalculateAlleles              ***************** **
     ** ************************************************************************** **
     * Description: Calculates the major, minor, and trace alleles for each SNP
     * Used By: ParseFile
     * Input: Vector (Subject SNP data stored at each element).
     * Output: Vector (Each element contains a Vector with Allele information).
     ----------------------------------------------------------------------------- */
    private int CalculateAlleles(Vector SubjectVector) {
        try {

            String Subject = "";
            int index = 0;
            /* For each SNP */
            for (int i = 0; i < NUMALLELES; ) {
                SNP CurrentSNP = new SNP();
                /* For Each Individual */
                for (int j = 0; j < SubjectVector.size(); j++) {
                    Subject = (String) SubjectVector.elementAt(j); //All of the SNPs in int form.

                    /* The first Allele in the SNP */
                    index = CurrentSNP.Values.indexOf(Subject.charAt(i));
                    switch (index) {
                    /* Allele not recorded yet */
                    default:
                        CurrentSNP.Scores.set(index, Integer.valueOf(CurrentSNP.Scores.elementAt(index).toString()) + 1); //add to the Allele Score
                        break;
                    case -1:
                        CurrentSNP.Values.add(Subject.charAt(i)); //add the SNP value to SNP.
                        CurrentSNP.Scores.add(1); //initialize the score to one.
                        break;
                    }
                    /* The second Allele in the SNP */
                    index = CurrentSNP.Values.indexOf(Subject.charAt(i + 1));
                    switch (index) {
                    /* Allele not recorded yet */
                    default:
                        CurrentSNP.Scores.set(index, Integer.valueOf(CurrentSNP.Scores.elementAt(index).toString()) + 1); //add to the Allele Score
                        break;
                    case -1:
                        CurrentSNP.Values.add(Subject.charAt(i + 1)); //add the SNP value to SNP.
                        CurrentSNP.Scores.add(1); //initialize the score to one.
                        break;
                    }
                }
                i = i + 2;
                SortSNP(CurrentSNP);
                SNPs.addElement(CurrentSNP);
            }

            System.out.println("End for loop");
            Subject = null;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /** ************************************************************************* **
     ** *****************          SortSNP                       ***************** **
     ** ************************************************************************** **
     * Description: Sorts the SNP information by allele frequency.
     * Used By: AddSubjects
     * Input: SNP.
     * Output: Sorted SNP object.
     ----------------------------------------------------------------------------- */
    private SNP SortSNP(SNP CurrentSNP) {
        Vector ScoreCopies = new Vector(CurrentSNP.Scores.size());
        Vector Values = new Vector();
        Values.setSize(CurrentSNP.Scores.size());
        int count = 0;

        /* Make a copy of Score Vector and sort it */
        for (int i = 0; i < CurrentSNP.Scores.size(); i++) {
            ScoreCopies.add(Integer.valueOf(CurrentSNP.Scores.get(i).toString()));
            Values.set(i, -1);
        }
        List<Integer> list = ScoreCopies.subList(0, ScoreCopies.size());
        java.util.Collections.sort(list);

        /* find the corresponding allele and match it with proper score */
        for (int i = 0; i < CurrentSNP.Values.size(); i++) {
            Values.set(i, CurrentSNP.Values.elementAt(CurrentSNP.Scores.indexOf(ScoreCopies.get(i))).toString());
            for (int j = 0; j < CurrentSNP.Values.size(); j++) {
                if (Values.get(i).toString().compareTo(Values.get(j).toString()) == 0 && i != j) {
                    count = CurrentSNP.Scores.indexOf(Integer.valueOf(ScoreCopies.get(j).toString()));
                    count = CurrentSNP.Scores.indexOf(Integer.valueOf(ScoreCopies.get(j).toString()), count + 1);
                    Values.set(i, CurrentSNP.Values.get(count));
                }
            }

        }
        switch (Values.size()) {
        case -1:
            break;
        case 0:
            break;
        case 1:
            break;
        case 2:
            if (Values.get(Values.size() - 1).toString().compareTo(String.valueOf(MISSINGVALUE)) == 0) {
                CurrentSNP.Values.set(0, Values.get(Values.size() - 1));
                CurrentSNP.Values.set(1, Values.get(Values.size() - 2));
            } else {
                CurrentSNP.Values = Values;
            }
            CurrentSNP.Scores = ScoreCopies;
            break;
        default:
            if (Values.get(Values.size() - 1).toString().compareTo(String.valueOf(MISSINGVALUE)) == 0) {
                for (int i = 0; i < Values.size() - 1; i++) {
                    Values.addElement(Values.get(0));
                    Values.remove(0);
                    ScoreCopies.add(ScoreCopies.get(0));
                    ScoreCopies.remove(0);
                }
            }
            if (Values.get(Values.size() - 2).toString().compareTo(String.valueOf(MISSINGVALUE)) == 0) {
                for (int i = 0; i < CurrentSNP.Values.size(); i++) {
                    if (CurrentSNP.Values.get(i).toString().compareTo(String.valueOf(MISSINGVALUE)) != 0 &&
                        CurrentSNP.Values.get(i).toString().compareTo(String.valueOf(Values.get(Values.size() - 1))) !=
                        0) {
                        count = Integer.valueOf(ScoreCopies.get(ScoreCopies.size() - 2).toString());
                        Values.set(0, MISSINGVALUE);
                        ScoreCopies.set(ScoreCopies.size() - 2, CurrentSNP.Scores.get(i));
                        Values.set(Values.size() - 2, CurrentSNP.Values.get(i).toString());
                        ScoreCopies.set(0, count);
                    }
                }
            }
            CurrentSNP.Scores = ScoreCopies;
            CurrentSNP.Values = Values;
        }
        CurrentSNP.SetMajor(MISSINGVALUE);
        CurrentSNP.SetMinor(MISSINGVALUE);
        ScoreCopies = null;
        Values = null;
        return CurrentSNP;

    }

    /** ************************************************************************* **
     ** *****************          Populate Matrix               ***************** **
     ** ************************************************************************** **
     * Description: Fills the Bit Matrix with SNP data.
     * Used By: ParseFile
     * Input: Vector of Subject SNP data, Matrixs to populate.
     * Output:Populated Matrixs (1 = Major, 0 = Minor)
     ----------------------------------------------------------------------------- */
    private int PopulateMatrix(MatrixCore MC, MatrixCore MF, int FType) {
        MC.SetNUMALLELES(SNPLENGTH);
        MC.SetNUMINDIVIDUALS(NUMINDIVIDUALS);
        MC.SetNUMSNPs(NUMSNPs);
        MF.SetNUMALLELES(SNPLENGTH);
        MF.SetNUMINDIVIDUALS(NUMINDIVIDUALS);
        MF.SetNUMSNPs(NUMSNPs);
        boolean Founder = false;

        String Subject = new String();
        String Allele = new String();
        int placement = 0;

        for (int j = 0; j < SNPs.size(); j++) {
            BitSet Info = new BitSet();
            BitSet Error = new BitSet();
            MC.SNPs.add(Info);
            MC.SNPErrors.add(Error);
            BitSet FInfo = new BitSet();
            BitSet FError = new BitSet();
            MF.SNPs.add(FInfo);
            MF.SNPErrors.add(FError);
        }

        for (int i = 0; i < Subjects.size(); i++) { //Each Individual
            Subject = Subjects.elementAt(i).toString();
            if((((Vector)CriteriaDataByRow.get(i)).get(2).toString().compareTo(".") == 0 || ((Vector)CriteriaDataByRow.get(i)).get(2).toString().compareTo("0") == 0) && (((Vector)CriteriaDataByRow.get(i)).get(3).toString().compareTo(".") == 0 || ((Vector)CriteriaDataByRow.get(i)).get(3).toString().compareTo("0") == 0))
            {
                Founder = true;
            }
            for (int j = 0; j < SNPs.size(); j++) { //Each SNP
                SNP CurrentSNP = new SNP();
                CurrentSNP = (SNP) SNPs.elementAt(j);
                placement = i * 2;
                for (int k = j * 2; k <= j * 2 + 1; k++) { //Place correctly
                    Allele = String.valueOf(Subject.charAt(k));
                    BitSet Info = (BitSet) MC.SNPs.elementAt(j);
                    BitSet Error = (BitSet) MC.SNPErrors.elementAt(j);
                    BitSet FInfo = (BitSet) MF.SNPs.elementAt(j);
                    BitSet FError = (BitSet) MF.SNPErrors.elementAt(j);
                    if (Allele.compareTo(CurrentSNP.MajorAllele) == 0) {
                        if(Founder)
                        {
                            FInfo.set(placement, true);
                            FError.set(placement, false);
                        }
                        Info.set(placement, true);
                        Error.set(placement, false);

                    }
                    if (Allele.compareTo(CurrentSNP.MinorAllele) == 0) {
                        if(Founder)
                        {
                            FInfo.set(placement, true);
                            FError.set(placement, false);
                        }
                        Info.set(placement, false);
                        Error.set(placement, false);
                    }
                    if (Allele.compareTo(MISSINGVALUE) == 0) {
                        if(Founder)
                        {
                            FInfo.set(placement, true);
                            FError.set(placement, false);
                        }
                        Info.set(placement, true);
                        Error.set(placement, true);
                    }
                    placement = placement + 1;
                }
            }
            Founder = false;
        }
        Subject = null;
        Allele = null;
        return 0;
    }

    public void ParseHaplotypeFile(MatrixCore MP,MatrixCore MF, String FileName, String Delimiter)
    {
        try {
            String Subject = "";
            BufferedReader in = new BufferedReader(new FileReader(FileName));
            //String temp_Header = in.readLine();

            //haplotype length = 32550
            NUMSNPs = 50;//Subject.length();

            while ((Subject = in.readLine()) != null)
            {
                Subject = Subject.replaceAll(" ","");
                if(Subject.length() == NUMSNPs)
                {
                    Subjects.addElement(Subject);
                }
            }

            NUMINDIVIDUALS = Subjects.size();
            //System.out.println(NUMINDIVIDUALS);

            int new_id = 0;

            String current;
            String next;

            int id[] = new int[NUMINDIVIDUALS];

            java.util.Hashtable idtable = new java.util.Hashtable();

            for(int i=0;i<NUMINDIVIDUALS;i++)
            {
                id[i] = -100;
            }

            for(int i=0;i<NUMINDIVIDUALS;i++)
            {
                current = Subjects.get(i).toString();
                if(id[i] == -100)
                {
                    int count_id = 0;
                    id[i] = i;

                    for(int j=0;j<NUMINDIVIDUALS;j++)
                    {
                        next = Subjects.get(j).toString();

                        if(current.compareTo(next)==0)
                        {
                            id[j] = i;
                            count_id++;
                        }
                    }

                    idtable.put(i, count_id);
                    System.out.println("put - key : " + i +"\t value " + count_id);
                }
                else //arealy have id
                {
                    continue;
                }
            }

            for(int i=0;i<NUMINDIVIDUALS;i++)
            {
                if(idtable.get(i) != null)
                {
                    System.out.println(Subjects.get(i).toString() + " " + idtable.get(i));
                }
            }

        } catch (IOException e) {
                    System.out.println("Error reading file: " + FileName + ".  Make sure the file exists and is readable!");
                    System.exit(1);
        }
    }

    /** ************************************************************************* **
     ** *****************          ParseSNPFile                  ***************** **
     ** ************************************************************************** **
     * Description: Parse files in the .SNP format
     * Used By: ParseFile
     * Input:  Filename (String)
     * Output: SNP Subjects (Vector)
     ----------------------------------------------------------------------------- */
    public int ParseSNPFile(String FileName, String delimiter) {
        try {
            String Subject = "";
            String[] Cells;
            BufferedReader in = new BufferedReader(new FileReader(FileName));
            String temp_Header = in.readLine();
            Header = temp_Header.split(delimiter);
            P.SetFileSize(FileName);

            CreateCriteriaData(SNPINDEX);

            int k = 0;
            System.out.println("File import started...");
            while ((Subject = in.readLine()) != null) {
                k = k + 1;
                P.UpdateParseSnp(Subject.length());

                Cells = Subject.split(delimiter);
                if(Cells.length < CriteriaDataByColumn.size())
                {
                    return -1;
                }
                if(SNPINDEX == 0)
                {
                    return -2;
                }
                if(Cells[SNPINDEX].contains(AlleleDelimeter)== false)
                {
                    return -4;
                }
                Vector CriteriaRow = new Vector();
                for (int i = 0; i < SNPINDEX - 1; i++) {
                    ((Vector) CriteriaDataByColumn.get(i)).add(Cells[i]);
                    CriteriaRow.add(Cells[i]);
                }
                CriteriaDataByRow.add(CriteriaRow);
                int index = 0;
                int j = 0;
                while( j < SNPINDEX-1)
                {
                    index = Subject.indexOf(delimiter,index+1);
                    j++;
                }
                Subject = Subject.substring(index+1);
                if(Subject.contains(AlleleDelimeter) == false)
                {
                    return -3;
                }
                Subject = Subject.replaceAll(AlleleDelimeter,"");
                Subject = Subject.replaceAll(delimiter,"");
                NUMALLELES = Subject.length();
                NUMSNPs = Subject.length() / 2;
                Subjects.addElement(Subject);
                P.SetIndex(P.GetIndex() + 1);
            }
            NUMINDIVIDUALS = k;
            SNPLENGTH = k * 2;
            Subject = null;
            Cells = null;
            temp_Header = null;



        } catch (IOException e) {
            System.out.println("Error reading file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        return 0;
    }

    public int CreateCriteriaData(int SNPIndex) {

        CriteriaDataByColumn = new Vector(SNPIndex);
        for (int i = 0; i < SNPIndex; i++) {
            CriteriaDataByColumn.add(new Vector());
        }
        return 0;
    }
    /** ************************************************************************* **
     ** *****************          ParseAffymatrixFile               ***************** **
     ** ************************************************************************** **
     * Description: Parse files in the .SNP format
     * Used By: ParseFile
     * Input:  Filename (String)
     * Output: SNP Subjects (Vector)
     ----------------------------------------------------------------------------- */
    public int ParseAffymatrixFile(String FileName, String del, MatrixCore MC, MatrixCore MF) {
        try {
            String Line = "";
            String[] Alleles;
            String[] SNPs;
            String AlleleLine = "";
            String AlleleOne = "";
            String AlleleTwo = "";
            String MajorAllele = "";
            String MinorAllele = "";
            double AlleleOneCount = 0;
            double AlleleTwoCount = 0;
            double MissingCount = 0;
            int NumSNPs = 0;
            double cur = 0.0;
            Double per = 0.0;
            int value = 0;
            boolean Founder;
            BitSet Individual = new BitSet();
            BufferedReader in = new BufferedReader(new FileReader(FileName));
            double FileSize = FileSize = new File(FileName).length();
            CreateCriteriaData(SNPROWINDEX-1);
            String[] Cells;
            Vector Temp_Header = new Vector();

            for(int i = 0; i < SNPROWINDEX-1; i++)
            {
                Line = in.readLine();
                Line = Line.replaceAll("\"","");
                Cells = Line.split(del);
                Temp_Header.add(Cells[0]);
                for(int j = 1; j < Cells.length; j++)
                {
                    ((Vector) CriteriaDataByColumn.get(i)).add(Cells[j]);
                    if(i == 2 && Cells[j].compareTo("0") == 0)
                    {
                        Individual.set(j-1);
                    }
                    if(i == 3 && Cells[j].compareTo("0") != 0 )
                    {
                        Individual.set(j-1, false);
                    }
                }
            }

            while ((Line = in.readLine()) != null) {
                Line = Line.replaceAll("\"","");
                NumSNPs = NumSNPs + 1;
                SNPs = Line.split(del);
                Temp_Header.add(SNPs[0]);
                if(SNPINDEX-1 < 0)
                {
                    return -1;
                }
                for (int i = SNPINDEX-1; i < SNPs.length; i++) {
                    if(SNPs[i].contains(AlleleDelimeter) == false)
                    {
                        return -2;
                    }
                    Alleles = SNPs[i].split(AlleleDelimeter);
                    if (MISSING.compareTo(Alleles[0]) == 0) {
                        MissingCount = MissingCount + 1;
                    } else if (AlleleOne.compareTo("") == 0) {
                        AlleleOne = Alleles[0];
                        AlleleOneCount = AlleleOneCount + 1;
                    }
                    else if (AlleleTwo.compareTo("") == 0 && AlleleOne.compareTo(Alleles[0]) != 0)
                    {
                        AlleleTwo = Alleles[0];
                        AlleleTwoCount = AlleleTwoCount + 1;
                    }
                    else if (AlleleOne.compareTo(Alleles[0]) == 0) {
                        AlleleOneCount = AlleleOneCount + 1;
                    } else if (AlleleTwo.compareTo(Alleles[0]) == 0) {
                        AlleleTwoCount = AlleleTwoCount + 1;
                    }
                    if (MISSING.compareTo(Alleles[1]) == 0) {
                        MissingCount = MissingCount + 1;
                    }
                    else if (AlleleOne.compareTo("") == 0) {
                        AlleleOne = Alleles[1];
                    }
                    else if (AlleleTwo.compareTo("") == 0 && AlleleOne.compareTo(Alleles[1]) != 0)
                    {
                        AlleleTwo = Alleles[1];
                    }
                    if (AlleleOne.compareTo(Alleles[1]) == 0) {
                        AlleleOneCount = AlleleOneCount + 1;
                    } else if (AlleleTwo.compareTo(Alleles[1]) == 0) {
                        AlleleTwoCount = AlleleTwoCount + 1;
                    }
                }
                if (AlleleOneCount > AlleleTwoCount) {
                    MajorAllele = AlleleOne;
                    MinorAllele = AlleleTwo;
                } else {
                    MajorAllele = AlleleTwo;
                    MinorAllele = AlleleOne;
                }
                AlleleOneCount = 0;
                AlleleTwoCount = 0;
                MissingCount = 0;
                BitSet Info = new BitSet();
                BitSet Error = new BitSet();
                BitSet FInfo = new BitSet();
                BitSet FError = new BitSet();
                Line = Line.substring(Line.indexOf(del),Line.length());
                Line = Line.replaceAll(del,"");
                Line = Line.replaceAll(AlleleDelimeter,"");
                for (int i = 0; i < Line.length(); i++) {
                    if (Line.substring(i, i + 1).compareTo(MajorAllele) == 0) {
                        if(Individual.get((int)Math.floor(i/2)))
                        {
                            FInfo.set(i);
                            FError.set(i, false);
                        }
                        Info.set(i);
                        Error.set(i, false);
                    } else if (Line.substring(i, i + 1).compareTo(MinorAllele) == 0) {
                        if(Individual.get((int)Math.floor(i/2)))
                        {
                            FInfo.set(i, false);
                            FError.set(i, false);
                        }
                        Info.set(i, false);
                        Error.set(i, false);
                    } else if (Line.substring(i, i + 1).compareTo(MISSING) == 0) {
                        if(Individual.get((int)Math.floor(i/2)))
                        {
                            FInfo.set(i);
                            FError.set(i);
                        }

                        Info.set(i);
                        Error.set(i);
                    }
                }
                MC.SNPs.add(Info);
                MC.SNPErrors.add(Error);
                MF.SNPs.add(Info);
                MF.SNPErrors.add(Error);


                NUMINDIVIDUALS = Line.length() / 2;
                NUMALLELES = Line.length();
                SNPLENGTH = Line.length();
                MajorAllele = "";
                MinorAllele = "";
                cur = cur +(Line.length()*1.75);
                per = (cur/FileSize)*100;
                value = per.intValue();
                P.SetProgress(value);
            }
            MC.SetNUMALLELES(SNPLENGTH);
            MC.SetNUMINDIVIDUALS(NUMINDIVIDUALS);
            NUMSNPs = NumSNPs;
            MC.SetNUMSNPs(NUMSNPs);
            MC.SetNUMALLELES(NUMALLELES);

            MF.SetNUMALLELES(SNPLENGTH);
            MF.SetNUMINDIVIDUALS(NUMINDIVIDUALS);
            MF.SetNUMSNPs(NUMSNPs);
            MF.SetNUMALLELES(NUMALLELES);


            Line = null;
            Alleles = null;
            AlleleLine = null;
            AlleleOne = null;
            AlleleTwo = null;
            MajorAllele = null;
            MinorAllele = null;

            Header = new String[Temp_Header.size()];
            for(int i = 0; i < Temp_Header.size(); i++)
            {
                Header[i] = Temp_Header.get(i).toString();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        return 0;
    }
    public Vector ParsePopulationFile(String[] IDs, FileInfoDialog Fid){
        Vector Founder = new Vector();
        Vector FounderSet = new Vector();
        for(int i = 11; i < (IDs.length*2);i++)
        {
            FounderSet.add(0);
        }
        int FounderNumber = 0;
        int FounderNumber2 = 0;
        Individual I;
        String CurPop = "";
        if(Fid.CHDRButton.isSelected())
        {
            CurPop = "CHD";
        }
        if(Fid.CHBRButton.isSelected())
        {
            CurPop = "CHB";
        }
        if(Fid.CEURButton.isSelected())
        {
            CurPop = "CEU";
        }
        if(Fid.JPTRButton.isSelected())
        {
            CurPop = "JPT";
        }
        if(Fid.GIHRButton.isSelected())
        {
            CurPop = "GIH";
        }
        if(Fid.YRIRButton.isSelected())
        {
            CurPop = "YRI";
        }
        if(Fid.TSIRButton.isSelected())
        {
            CurPop = "TSI";
        }
        if(Fid.MEXRButton.isSelected())
        {
            CurPop = "MEX";
        }
        if(Fid.MKKRButton.isSelected())
        {
            CurPop = "MKK";
        }
        if(Fid.LWKRButton.isSelected())
        {
            CurPop = "LWK";
        }

        try
        {
            String Line = new String();
            String[] Split;
            BufferedReader in = new BufferedReader(new FileReader("relationships_w_pops.txt"));
            in.readLine();
            while((Line = in.readLine()) != null)
            {
                Split = Line.split("\t");
                if(Split[6].compareTo(CurPop) == 0)
                {
                    I = new Individual(Split[0],Split[1],Split[2],Split[3],Integer.valueOf(Split[4]),Integer.valueOf(Split[5]),Split[6], CurPop);
                    if(I.Founder)
                    {
                        FounderNumber2++;
                        Founder.add(I.ID);
                    }
                }
            }
            int Index = 0;
            for(int i = 11; i < IDs.length; i++)
            {
                if(Founder.contains(IDs[i]))
                {
                    Index = Founder.indexOf(IDs[i])+(Founder.indexOf(IDs[i])%2);
                    FounderNumber++;
                    FounderSet.set(Index,1);
                    FounderSet.set(Index+1,1);
                }
            }
        }
        catch(IOException e)
        {

        }

        return FounderSet;
    }
    /** ************************************************************************* **
     ** *****************          ParseHapMapFile               ***************** **
     ** ************************************************************************** **
     * Description: Parse files in the .SNP format
     * Used By: ParseFile
     * Input:  Filename (String)
     * Output: SNP Subjects (Vector)
     ----------------------------------------------------------------------------- */
    public int ParseHapMapFile(String FileName, String del, MatrixCore M, MatrixCore MF, FileInfoDialog Fid) {
        markerList = new Vector();
        distanceList = new Vector();
        try {
            CreateCriteriaData(SNPINDEX-1);
            String Line = "";
            String AlleleLine = "";
            String AlleleOne = "";
            String AlleleTwo = "";
            String MajorAllele = "";
            String MinorAllele = "";
            int AlleleOneCount = 0;
            int AlleleTwoCount = 0;
            int MissingCount = 0;
            P.SetFileSize(FileName);
            BitSet Info;
            BitSet Error;
            BitSet FInfo;
            BitSet FError;
            int FounderCount = 0;
            int PopulationCount = 0;
            BufferedReader in = new BufferedReader(new FileReader(FileName));
            String temp_Header = in.readLine();
            Header = temp_Header.split(" ");
            temp_Header = null;
            Vector Founders = ParsePopulationFile(Header, Fid);
            String[] Cells;
            MapRegion MR = new MapRegion("");
            MapMarker MM;
            while ((Line = in.readLine()) != null) {
                P.UpdateParseHap(Line.length());
                if(Line.indexOf("QC+")< 0)
                {
                    return -1;
                }
                Cells = Line.split(" ");
               // for(int i = 1; i < SNPINDEX-1; i++)
               // {
               //         ((Vector) CriteriaDataByColumn.get(i)).add(Cells[i]);
               // }
                HapMapSNPs.add(Integer.valueOf(Line.substring(Line.indexOf("rs")+2, Line.indexOf(" "))));
                if(HSM.Region.size() == 0)
                {
                    HSM.Region.add(Cells[2]);
                    MR.Name = Cells[2];
                }
                //MM = new MapMarker(Cells[0],Double.valueOf(Cells[3]),Double.valueOf(Cells[3]));
                //MR.AddMarker(MM);
                //markerList.add(MM.Name);
                //distanceList.add(MM.GDistance);

                //if(NUMSNPs == 0)
               // {
                //    MR = new MapRegion(Line.substring(Line.indexOf("chr"), Line.indexOf(" ",Line.indexOf("chr"))));
                //}
                //MM = new MapMarker(Line.substring(Line.indexOf("rs")+2, Line.indexOf(" ")),Integer.valueOf(Line.substring( Line.indexOf(" ",Line.indexOf("chr")),Line.indexOf(" ",Line.indexOf(" ",Line.indexOf("chr"))+1))),Integer.valueOf(Line.substring( Line.indexOf(" ",Line.indexOf("chr")),Line.indexOf(" ",Line.indexOf(" ",Line.indexOf("chr"))+1))));
                //MR.AddMarker(MM);
                //HapMapLocs.add(Integer.valueOf(Line.substring(Line.indexOf(" ")+4, Line.indexOf(" "))));
                NUMSNPs = NUMSNPs+1;
                AlleleLine = Line.substring(Line.indexOf(" "), Line.indexOf(" ", Line.indexOf(" ") + 2));
                AlleleLine = AlleleLine.replaceAll(" ", "");
                AlleleOne = AlleleLine.substring(0, 1);
                AlleleTwo = AlleleLine.substring(2, 3);
                AlleleLine = null;
                Line = Line.substring(Line.indexOf("QC+") + 4, Line.length());
                Line = Line.replaceAll(" ", "");
                for (int i = 0; i < Line.length(); i++) {
                    if (AlleleOne.compareTo(Line.substring(i, i+1)) == 0) {
                        AlleleOneCount = AlleleOneCount + 1;
                    } else if (AlleleTwo.compareTo(Line.substring(i, i+1)) == 0) {
                        AlleleTwoCount = AlleleTwoCount + 1;
                    } else if ("N".compareTo(Line.substring(i, i+1)) == 0) {
                        MissingCount = MissingCount + 1;
                    }
                }
                if (AlleleOneCount > AlleleTwoCount) {
                    MajorAllele = AlleleOne;
                    MinorAllele = AlleleTwo;
                } else {
                    MajorAllele = AlleleTwo;
                    MinorAllele = AlleleOne;
                }
                AlleleOneCount = 0;
                AlleleTwoCount = 0;
                MissingCount = 0;
                Info = new BitSet();
                Error = new BitSet();
                FInfo = new BitSet();
                FError = new BitSet();
                FounderCount = 0;
                PopulationCount = 0;
                for (int i = 0; i < Line.length(); i++) {
                    if(Integer.valueOf(Founders.get(i).toString()) == 1)
                    {
                        FounderCount++;
                        if (Line.substring(i, i + 1).compareTo(MajorAllele) == 0) {
                            FInfo.set(i);
                            FError.set(i, false);
                        } else if (Line.substring(i, i + 1).compareTo(MinorAllele) == 0) {
                            FInfo.set(i, false);
                            FError.set(i, false);
                        } else if (Line.substring(i, i + 1).compareTo("N") == 0) {
                            FInfo.set(i);
                            FError.set(i);
                        }
                    }
                    PopulationCount++;
                    if (Line.substring(i, i + 1).compareTo(MajorAllele) == 0) {
                        Info.set(i);
                        Error.set(i, false);
                    } else if (Line.substring(i, i + 1).compareTo(MinorAllele) == 0) {
                        Info.set(i, false);
                        Error.set(i, false);
                    } else if (Line.substring(i, i + 1).compareTo("N") == 0) {
                        Info.set(i);
                        Error.set(i);
                    }
                }
                M.SNPs.add(Info);
                M.SNPErrors.add(Error);
                MF.SNPs.add(FInfo);
                MF.SNPErrors.add(FError);
            }
            in.close();
            HSM.Regions.add(MR);
            NUMINDIVIDUALS = PopulationCount/2;
            FNUMINDIVIDUALS = FounderCount/2;
            NUMALLELES = NUMINDIVIDUALS*2;
            SNPLENGTH = NUMALLELES;
            M.SetNUMALLELES(NUMALLELES);
            M.SetNUMINDIVIDUALS(NUMINDIVIDUALS);
            M.SetNUMSNPs(NUMSNPs);
            MF.SetNUMALLELES(SNPLENGTH);
            MF.SetNUMINDIVIDUALS(FNUMINDIVIDUALS);
            MF.SetNUMSNPs(NUMSNPs);
            Line = null;
            AlleleLine = null;
            AlleleOne = null;
            AlleleTwo = null;
            MajorAllele = null;
            MinorAllele = null;
            System.out.println("Import Complete");
        } catch (IOException e) {
            System.out.println("Error reading file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        return 0;
    }
    public String[] ParseHeader(String FileName, String Delimiter) {
        String[] Split;
        try {
            String Line = new String();
            BufferedReader in = new BufferedReader(new FileReader(FileName));
            Line = in.readLine();
            Split = Line.split(Delimiter);

            return Split;
        } catch (IOException e) {
            System.out.println("Error reading file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        return null;
    }

    /** ************************************************************************* **
     ** *****************          ParseFile                     ***************** **
     ** ************************************************************************** **
     * Description: Entry point for the application.  Parses the raw data file to
     *  extract SNP data and create all of the needed objects.
     * Used By: Main Class
     * Input:  String. (Data File Path),
     *         int File Type ( 0 - .SNP, 1 - HapMap, 2 - Affymatrix)
     * Output: Populated Matrixs
     ----------------------------------------------------------------------------- */
    public int ParseFile(MatrixCore MP, MatrixCore MF, String FileName, int FVal, String Delimiter, FileInfoDialog Fid, ProgressDialog PD) {


        String[] LineSplit = ParseHeader(FileName,Delimiter);
        DataFilePath = FileName;
        FileDelimeter = Delimiter;
        Ftype = FVal;
        int returnvalue = -5;
        switch (FVal) {
        case 0:
            returnvalue = ParseSNPFile(FileName, Delimiter);
            System.out.println(NUMALLELES);
            switch(returnvalue)
            {
            case -1:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("File structure incorrect - please verify your file.");
                System.out.println("File structure incorrect - please verify your file.");
                return -1;
            case -2:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("SNP Column set to 0.  Please specify a valid column number.");
                System.out.println("File structure misspecified in import window.");
                return -1;
            case -3:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("Allele Delimeter not found.  Please specify a valid allele delimeter.");
                System.out.println("File structure misspecified in import window.");
                return -1;
            case -4:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("Allele Delimeter not found in the first data column specified.");
                System.out.println("File structure misspecified in import window.");
                return -1;
            default:
                PD.ProgressLabel.setText("Performing post processing on the file.");
                System.out.println("Performing post processing on the file.");
                CalculateAlleles(Subjects);
                System.out.println("CalculateAlleles");
                PopulateMatrix(MP, MF, FVal);
                System.out.println("PopulateMatrix");
                System.out.println("File Import Complete!");
                break;
            }
            break;
        case 1:
            returnvalue = (ParseHapMapFile(FileName, Delimiter, MP,MF, Fid));
            switch(returnvalue)
            {
            case -1:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("The file does not appear to be in Hapmap format.");
                System.out.println("File structure incorrect - please verify your file.");
                return -1;
            case -2:
                PD.ProgressLabel.setText("File structure misspecified in import window.");
                System.out.println("File structure misspecified in import window.");
                return -1;
            default:
                System.out.println("File Import Complete!");
                break;
            }
            break;
        case 2:
            returnvalue = ParseAffymatrixFile(FileName, Delimiter, MP, MF);
            switch(returnvalue)
            {
            case -1:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("SNP Column set to 0.  Please specify a valid column number.");
                System.out.println("File structure incorrect - please verify your file.");
                return -1;
            case -2:
                PD.OperationLabel.setText("An error has occured.");
                PD.ProgressLabel.setText("File structure misspecified in import window.");
                System.out.println("File structure misspecified in import window.");
                return -1;
            default:
                System.out.println("File Import Complete!");
                break;
            }
            break;
        default:
            System.out.println("Unrecongized file type!");
            break;
        }
        return 0;
    }




    public void ExportData(String newfilepath, BitSet T, int Transpose) {
        if((Ftype == 0 && Transpose == 0) ||(Ftype == 0 && Transpose == 2))
        {
            try {
                File newFile = new File(newfilepath);
                FileWriter fos = new FileWriter(newFile);
                P.ClearProgress();
                String Subject = "";
                String[] Cells;
                BufferedReader in = new BufferedReader(new FileReader(DataFilePath));

                int k = 0;
                while ((Subject = in.readLine()) != null) {
                    Cells = Subject.split(FileDelimeter);
                    P.UpdateParseSnp(Subject.length());
                    for (int i = 0; i < SNPINDEX - 1; i++) {
                            fos.write(Cells[i]);
                            fos.write(FileDelimeter);
                    }

                    for (int i = SNPINDEX - 1; i < Cells.length; i++) {
                        if (!T.get(i - SNPINDEX + 1)) {
                            fos.write(Cells[i]);
                            fos.write(FileDelimeter);
                        }
                    }
                    fos.write("\n");
                }
                fos.close();

                P.ClearProgress();
            } catch (IOException e) {
        }
        }
        //Transpose
        if((Ftype == 0 && Transpose == 1) ||(Ftype == 0 && Transpose == 2))
        {
            try {
                String Value;
                File newFile = new File(newfilepath+"_T");
                FileWriter fos = new FileWriter(newFile);
                P.ClearProgress();
                String Subject = "";
                String[] Cells;
                Vector Criteria;
                for(int i = 0; i < CriteriaDataByColumn.size(); i++)
                {
                    Criteria = (Vector)CriteriaDataByColumn.get(i);
                    if(Criteria.size() > 0)
                    {
                        fos.write(Header[i] + FileDelimeter);
                        for (int j = 0; j < Criteria.size(); j++) {
                            fos.write(Criteria.get(j).toString() + FileDelimeter);
                        }
                        fos.write("\n");
                    }
                }
                for(int i = 0; i < MainFrame.mainFrame1.MC.SNPs.size(); i++)
                {
                    if(!T.get(i))
                    {
                        fos.write(Header[i + SNPINDEX-1] + FileDelimeter);
                        BitSet SNP = (BitSet) MainFrame.mainFrame1.MC.SNPs.get(i);
                        BitSet SNPE = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.get(i);
                        for (int j = 0; j < NUMINDIVIDUALS*2; j++) {
                            if (Boolean.toString(SNPE.get(j)).compareTo("true") == 0) {
                                Value = ".";
                            } else {
                                if (Boolean.toString(SNP.get(j)).compareTo("true") == 0) {
                                    Value = "1";
                                } else {
                                    Value = "0";
                                }
                            }
                            switch (j) {
                            case 0:
                                fos.write(Value);
                                break;
                            default:
                                switch (j % 2) {
                                case 0:
                                    fos.write(Value);
                                    break;
                                case 1:
                                    fos.write("/" + Value + FileDelimeter);
                                    break;
                                }
                            }

                        }
                        fos.write("\n");
                    }
                }
                fos.write("\n");
                fos.close();

                P.ClearProgress();
            } catch (IOException e) {
        }
        }

        if((Ftype == 2 && Transpose == 1) ||(Ftype == 2 && Transpose == 2))
        {
            try {
                String Value = "";
                File newFile = new File(newfilepath);
                FileWriter fos = new FileWriter(newFile);
                P.ClearProgress();
                for(int i = 0; i < CriteriaDataByColumn.size(); i++)
                {
                    Vector Ind = (Vector)CriteriaDataByColumn.get(i);
                    fos.write(Header[i]+FileDelimeter);
                    for(int j = 0; j < Ind.size(); j++)
                    {
                        fos.write(Ind.get(j).toString());
                        fos.write(FileDelimeter);
                    }
                    fos.write("\n");
                }

                for(int i = 0; i < MainFrame.mainFrame1.MC.SNPs.size(); i++)
                {
                    if(!T.get(i))
                    {
                        fos.write(Header[i + SNPINDEX + 1] + FileDelimeter);
                        BitSet SNP = (BitSet) MainFrame.mainFrame1.MC.SNPs.get(i);
                        BitSet SNPE = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.get(i);
                        for (int j = 0; j < NUMALLELES; j++) {
                            if (Boolean.toString(SNPE.get(j)).compareTo("true") == 0) {
                                Value = ".";
                            } else {
                                if (Boolean.toString(SNP.get(j)).compareTo("true") == 0) {
                                    Value = "1";
                                } else {
                                    Value = "0";
                                }
                            }
                            switch (j) {
                            case 0:
                                fos.write(Value);
                                break;
                            default:
                                switch (j % 2) {
                                case 0:
                                    fos.write(Value);
                                    break;
                                case 1:
                                    fos.write("/" + Value + FileDelimeter);
                                    break;
                                }
                            }

                        }
                        fos.write("\n");
                    }
                }
                fos.close();
                P.ClearProgress();
            } catch (IOException e) {
        }

        }
        //Transpose of Dataset
        if((Ftype == 2 && Transpose == 0) ||(Ftype == 2 && Transpose == 2))
        {
            try {
                String Value1 = "";
                String Value2 = "";
                Vector Criteria;
                File newFile = new File(newfilepath+"_T");
                FileWriter fos = new FileWriter(newFile);
                P.ClearProgress();

                for(int i = 0; i < SNPROWINDEX-1; i++)
                {
                    fos.write(Header[i] +FileDelimeter);
                }
                for(int i = SNPROWINDEX-1; i < Header.length; i++)
                {
                    if (!T.get(i - SNPINDEX + 1)) {
                          fos.write(Header[i]);
                          fos.write(FileDelimeter);
                        }
                }
                fos.write("\n");
                for(int i = 0; i < NUMINDIVIDUALS; i++)
                {
                    //write out metadata
                    for(int j = 0; j < CriteriaDataByColumn.size(); j++)
                    {
                        Criteria = (Vector)CriteriaDataByColumn.get(j);
                        fos.write(Criteria.get(i).toString());
                        fos.write(FileDelimeter);
                    }
                    for(int j = 0; j < MainFrame.mainFrame1.MC.SNPs.size(); j++)
                    {
                        if(!T.get(j))
                        {
                            BitSet SNP = (BitSet) MainFrame.mainFrame1.MC.SNPs.get(j);
                            BitSet SNPE = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.get(j);
                            if (Boolean.toString(SNPE.get(2*i)).compareTo("true") == 0) {
                                Value1 = ".";
                            }

                            else
                            {
                                if (Boolean.toString(SNP.get(2*i)).compareTo("true") == 0)
                                {
                                    Value1 = "1";
                                }
                                else
                                {
                                    Value1 = "0";
                                }
                            }
                            if(Boolean.toString(SNPE.get(2*i+1)).compareTo("true") == 0)
                            {
                                Value2 = ".";
                            }
                            else
                            {
                                if (Boolean.toString(SNP.get(2*i+1)).compareTo("true") == 0)
                                {
                                    Value2 = "1";
                                }
                                else
                                {
                                    Value2 = "0";
                                }

                            }
                            fos.write(Value1+"/"+Value2+FileDelimeter);
                        }
                }
                    fos.write("\n");
                }
                fos.close();
                P.ClearProgress();
            } catch (IOException e) {
        }

        }
        if((Ftype == 1 && Transpose == 1) ||(Ftype == 1 && Transpose == 2))
        {
            try {
                String Value = "";
                File newFile = new File(newfilepath);
                FileWriter fos = new FileWriter(newFile);
                P.ClearProgress();
                fos.write("RS#" +FileDelimeter);
                for(int i = 11; i < Header.length; i++)
                {
                    fos.write(Header[i] +FileDelimeter);
                }
                fos.write("\n");
                for(int i = 0; i < MainFrame.mainFrame1.MC.SNPs.size(); i++)
                {
                    if(!T.get(i))
                    {
                        fos.write("rs");
                        fos.write(String.valueOf(HapMapSNPs.elementAt(i).intValue()));
                        fos.write(FileDelimeter);
                        for(int j = 1; j < CriteriaDataByColumn.size(); j++)
                        {
                                Vector Ind = (Vector)CriteriaDataByColumn.get(j);
                                if(Ind.size() > 0)
                                {
                                    fos.write(Ind.get(i).toString());
                                    fos.write(FileDelimeter);
                                }
                            }
                        BitSet SNP = (BitSet) MainFrame.mainFrame1.MC.SNPs.get(i);
                        BitSet SNPE = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.get(i);
                        for (int j = 0; j < NUMINDIVIDUALS*2; j++) {
                            if (Boolean.toString(SNPE.get(j)).compareTo("true") == 0) {
                                Value = ".";
                            } else {
                                if (Boolean.toString(SNP.get(j)).compareTo("true") == 0) {
                                    Value = "1";
                                } else {
                                    Value = "0";
                                }
                            }
                            switch (j) {
                            case 0:
                                fos.write(Value);
                                break;
                            default:
                                switch (j % 2) {
                                case 0:
                                    fos.write(Value);
                                    break;
                                case 1:
                                    fos.write("/" + Value + FileDelimeter);
                                    break;
                                }
                            }

                        }
                        fos.write("\n");
                    }
                }
                fos.close();
                P.ClearProgress();
            } catch (IOException e) {
        }

        }
        //Transpose
        if((Ftype == 1 && Transpose == 0) ||(Ftype == 1 && Transpose == 2))
        {
            try {
                String Value1 = "";
                String Value2 = "";
                Vector Criteria;
                File newFile = new File(newfilepath+"_T");
                FileWriter fos = new FileWriter(newFile);
                P.ClearProgress();
                fos.write("ID"+FileDelimeter);
                 for(int i = 0; i < NUMSNPs; i++)
                 {
                     if (!T.get(i)) {
                         fos.write("rs");
                         fos.write(String.valueOf(HapMapSNPs.elementAt(i).intValue()));
                         fos.write(FileDelimeter);
                         }
                 }
                 fos.write("\n");
                 for(int i = 0; i < NUMINDIVIDUALS; i++)
                 {
                     fos.write(Header[SNPINDEX-1+i] +FileDelimeter);
                     for(int j = 0; j < MainFrame.mainFrame1.MC.SNPs.size(); j++)
                     {
                         if(!T.get(j))
                         {

                             BitSet SNP = (BitSet) MainFrame.mainFrame1.MC.SNPs.get(j);
                             BitSet SNPE = (BitSet) MainFrame.mainFrame1.MC.SNPErrors.get(j);
                             if (Boolean.toString(SNPE.get(2*i)).compareTo("true") == 0) {
                                 Value1 = ".";
                             }

                             else
                             {
                                 if (Boolean.toString(SNP.get(2*i)).compareTo("true") == 0)
                                 {
                                     Value1 = "1";
                                 }
                                 else
                                 {
                                     Value1 = "0";
                                 }
                             }
                             if(Boolean.toString(SNPE.get(2*i+1)).compareTo("true") == 0)
                             {
                                 Value2 = ".";
                             }
                             else
                             {
                                 if (Boolean.toString(SNP.get(2*i+1)).compareTo("true") == 0)
                                 {
                                     Value2 = "1";
                                 }
                                 else
                                 {
                                     Value2 = "0";
                                 }

                             }
                             fos.write(Value1+"/"+Value2+FileDelimeter);
                         }
                 }
                     fos.write("\n");
                }

                fos.close();
                P.ClearProgress();
            } catch (IOException e) {
        }

        }

    }

    public void ExportDataOld(String newfilepath, BitSet T) {
        try {
            File newFile = new File(newfilepath);
            FileWriter fos = new FileWriter(newFile);
            P.ClearProgress();
            String Subject = "";
            String[] Cells;
            BufferedReader in = new BufferedReader(new FileReader(DataFilePath));

            int k = 0;
            while ((Subject = in.readLine()) != null) {
                Cells = Subject.split(FileDelimeter);
                P.UpdateParseSnp(Subject.length());
                for (int i = 0; i < SNPINDEX - 1; i++) {
                    fos.write(Cells[i]);
                    fos.write(FileDelimeter);
                }

                for (int i = SNPINDEX - 1; i < Cells.length; i++) {
                    if (!T.get(i - SNPINDEX + 1)) {
                        fos.write(Cells[i]);
                        fos.write(FileDelimeter);
                    }
                }
                fos.write("\n");
            }
            fos.close();

            P.ClearProgress();
        } catch (IOException e) {
        }
    }

    public List findStartEndIndex() {
        Vector snp_list = getSnpList();
        Vector distance_list = getDistanceList();

        if (distance_list != null) {
            String[] Header = GetHeader();

            boolean find = true;
            int findStartIndex = 0;
            int findEndIndex = 0;

            while (find) {

                String startName = Header[GetSNPINDEX() - 1 + findStartIndex];
                String endName = Header[Header.length - 1 - findEndIndex];

                startIndex = snp_list.indexOf(startName);
                endIndex = snp_list.indexOf(endName);

                if (startIndex == -1) {
                    findStartIndex++;
                }
                if (endIndex == -1) {
                    findEndIndex++;
                }
                if (startIndex != -1 && endIndex != -1) {
                    find = false;
                }
            }
            if (startIndex >= 0 && endIndex >= 0) {
                return distance_list.subList(startIndex, endIndex);
            } else
                return null;
        } else
            return null;
    }

    public int GetStartIndex() {
        return startIndex;
    }

    public int GetEndIndex() {
        return endIndex;
    }

    public int GetNumSNPs() {
        return NUMSNPs;
    }

    public int GetNumINDIs() {
        return NUMINDIVIDUALS;
    }

    public String[] GetHeader() {
        return Header;
    }

    public int GetSNPINDEX() {
        if(Ftype == 0)
        {
         return SNPINDEX;
        }
        else
        {
            return SNPROWINDEX;
        }
    }

    public int SetSNPINDEX(int Value) {
        SNPINDEX = Value;
        return SNPINDEX;
    }
    public int SetSNPROWINDEX(int Value) {
        SNPROWINDEX = Value;
        return SNPROWINDEX;
    }

    public String GetMissing() {
        return MISSING;
    }

    public void SetMissing(String Value) {
        MISSING = Value;
    }

    public int GetPedigreeIndex() {
        return PedigreeIndex;
    }

    public void SetPedigreeIndex(int Value) {
        PedigreeIndex = Value;
    }

    public int GetIndividualIndex() {
        return IndividualIndex;
    }

    public void SetIndividualIndex(int Value) {
        IndividualIndex = Value;
    }

    public Vector GetAllPedigreeList() {
        return (Vector) CriteriaDataByColumn.get(PedigreeIndex - 1);
    }

    public Vector GetAllIndividualList() {
        return (Vector) CriteriaDataByColumn.get(IndividualIndex - 1);
    }

    public Vector GetCriteriaDataIndex(int Value) {
        return (Vector) CriteriaDataByColumn.get(Value);
    }

    public String[] getItemList() {
        int index = -1;
        if(Ftype == 0)
        {
            index = SNPINDEX;
        }
        else
        {
            index = SNPROWINDEX;
        }
        String[] itemlist = new String[index];
        itemlist[0] = "";
        for (int i = 1; i < index; i++) {
            itemlist[i] = Header[i - 1];
        }

        return itemlist;
    }

    public Vector GetSubjects() {
        return Subjects;
    }

    private int PrintMatrix(MatrixCore M) {
        System.out.println("-- Print Info ---");
        for (int i = 0; i < NUMSNPs; i++) {
            BitSet Info = (BitSet) M.SNPs.elementAt(i);
            BitSet Error = (BitSet) M.SNPErrors.elementAt(i);
            System.out.print("SNP " + i + " Data: ");
            for (int j = 0; j < SNPLENGTH; j++) {
                System.out.print(Info.get(j) + " ");
            }
            System.out.println();
            System.out.print("SNP " + i + " Error: ");
            for (int j = 0; j < SNPLENGTH; j++) {
                System.out.print(Error.get(j) + " ");
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("--- End Print Info ---");
        return 0;
    }

    public SAGEMap ParseSAGEMapWithCorrection(Vector FileName) {
        SAGEMap M = new SAGEMap();
        String Line = "";
        String QUOTE = "\"";
        try {
            int count;
            for (int i = 0; i < FileName.size(); i++) {
                count = 0;
                BufferedReader in = new BufferedReader(new FileReader(FileName.elementAt(i).toString()));
                while ((Line = in.readLine()) != null) {
                    if (Line.startsWith("\tregion") == true) {
                        M.Region.add(Line.substring(Line.indexOf(QUOTE) + 1,
                                Line.indexOf(QUOTE, Line.indexOf(QUOTE) + 1)));
                        while ((Line = in.readLine()).contains("}") == false) {
                            Line = Line.replaceAll("\t", "");
                            Line = Line.replaceAll("\"", "");
                            if (Line.startsWith("distance") == true && count == 0) {
                                M.Marker.add("Dummy");
                                M.Distance.add(Line.substring(Line.indexOf("=") + 2, Line.length()));
                                M.Previous = "Distance";
                            }
                            if (Line.startsWith("marker") && M.Previous.compareTo("Distance") == 0) {
                                M.Marker.add(Line.substring(Line.indexOf("=") + 2, Line.length()));
                                M.Previous = "Marker";
                            } else if (Line.startsWith("marker") && M.Previous.compareTo("Marker") == 0) {
                                System.out.println("WARNING: Two markers side by side at line: " + count + " in file: " +
                                        FileName.elementAt(i).toString() + "." + "\n\tAdding a distance of zero.");
                                M.Distance.add(0);
                                M.Marker.add(Line.substring(Line.indexOf("=") + 2, Line.length()));
                                M.Previous = "Marker";
                            } else if (Line.startsWith("distance") && M.Previous.compareTo("Marker") == 0) {
                                M.Distance.add(Line.substring(Line.indexOf("=") + 2, Line.length()));
                                M.Previous = "Distance";
                            } else if (Line.startsWith("distance") && M.Previous.compareTo("Distance") == 0 &&
                                       count != 0) {
                                System.out.println("FATAL ERROR: Two distances side by side at line: " + count +
                                        "in file: " + FileName.elementAt(i).toString() + ".");
                            }
                            count = count + 1;
                        }
                        if (count == 0) {
                            M.Region.remove(M.Region.size() - 1);
                        } else {
                            String name = M.Region.lastElement().toString() + " " + count / 2;
                            M.Region.set(M.Region.size() - 1, name);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(Line);
            System.out.println("Error reading map file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        return M;
    }

    /* Step One of Map File Merging */
    public SAGEMap parseMapFile(String FileName, int type) {
        markerList = new Vector();
        distanceList = new Vector();
        M = new SAGEMap();
        MapRegion MR;
        MapMarker MM = new MapMarker();
        String Line = "";
        String Name = "";
        double PD = 0.0;
        double GD = 0.0;
        try
        {
            if(type == 0)
            {
                BufferedReader in = new BufferedReader(new FileReader(FileName));
                while ((Line = in.readLine()) != null) {
                    Line = Line.replaceAll("\t", "");
                    Line = Line.replaceAll("\"", "");
                    Line = Line.replaceAll(" ", "");
                    if (Line.startsWith("region") == true) {
                        MR = new MapRegion((Line.substring(Line.indexOf("=") + 1, Line.length() - 1)));
                        M.Region.add(Line.substring(Line.indexOf("=") + 1, Line.length() - 1));
                        while ((Line = in.readLine()).contains("}") == false) {
                            Line = Line.replaceAll(" ", "");
                            Name = Line.substring(Line.indexOf("=") + 1, Line.indexOf("d"));
                            PD = Double.valueOf(Line.substring(Line.indexOf("=", Line.indexOf("=") + 1) + 1,
                                    Line.indexOf("#")));
                            GD = Double.valueOf(Line.substring(Line.indexOf("at") + 2, Line.indexOf("cM")));

                            MM = new MapMarker(Name, PD, GD);
                            MR.AddMarker(MM);
                            markerList.add(Name);
                            distanceList.add(GD);
                        }
                        M.Regions.add(MR);
                        MainFrame.mainFrame1.LocationFilters.add(new LocationFilter(MR.Name));
                    }
                }
            }
            if(type == 1)
            {
                BufferedReader in = new BufferedReader(new FileReader(FileName));
                while ((Line = in.readLine()) != null) {
                    Line = Line.replaceAll("\t", "");
                    Line = Line.replaceAll("\"", "");
                    Line = Line.replaceAll(" ", "");
                    if (Line.startsWith("region") == true) {
                        MR = new MapRegion((Line.substring(Line.indexOf("=") + 1, Line.length() - 1)));
                        M.Region.add(Line.substring(Line.indexOf("=") + 1, Line.length() - 1));
                        while ((Line = in.readLine()).contains("}") == false) {
                            Line = Line.replaceAll("\t", "");
                            Line = Line.replaceAll("\"", "");
                            Line = Line.replaceAll(" ", "");
                            if (Line.startsWith("marker"))
                            {
                                MM = new MapMarker();
                                M.Marker.add(Line.substring(Line.indexOf("=") + 1, Line.length()));
                                MM.Name = Line.substring(Line.indexOf("=") + 1, Line.length());
                                M.Previous = "Marker";
                            } else if (Line.startsWith("distance") && M.Previous.compareTo("Marker") == 0) {
                                M.Distance.add(Line.substring(Line.indexOf("=") + 1, Line.length()));
                                MM.PDistance = Double.valueOf(Line.substring(Line.indexOf("=") + 1, Line.length()));
                                M.Previous = "Distance";
                                markerList.add(MM.Name);
                                distanceList.add(MM.PDistance);
                                MR.AddMarker(MM);
                            }
                        }
                        M.Regions.add(MR);
                    }
                }

            }

        } catch (IOException e) {
            System.out.println(Line);
            System.out.println("Error reading map file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        GlobalMapMarkers = M.Marker;
        return M;
    }
    public SAGEMap ParseSAGEMapWithoutCorrection(Vector FileName) {
        SAGEMap M = new SAGEMap();
        MapRegion MR;
        MapMarker MM;
        String Line = "";
        try
        {
            int count;
            for (int i = 0; i < FileName.size(); i++)
            {
                count = 0;
                BufferedReader in = new BufferedReader(new FileReader(FileName.elementAt(i).toString()));
                while ((Line = in.readLine()) != null)
                {
                    Line = Line.replaceAll("\t", "");
                    Line = Line.replaceAll("\"", "");
                    Line = Line.replaceAll(" ", "");
                    if (Line.startsWith("region") == true)
                    {
                        MR = new MapRegion((Line.substring(Line.indexOf("=") + 1, Line.length())));
                        while ((Line = in.readLine()).contains("}") == false)
                        {
                            Line = Line.replaceAll("\t", "");
                            Line = Line.replaceAll("\"", "");
                            Line = Line.replaceAll(" ", "");

                            if (Line.startsWith("marker"))
                            {
                                M.Marker.add(Line.substring(Line.indexOf("=") + 1, Line.length()));
                                M.Previous = "Marker";
                            }
                            count = count + 1;
                        }
                        if (count == 0) {
                            M.Region.remove(M.Region.size() - 1);
                        } else {
                            String name = M.Region.lastElement().toString() + " " + count / 2;
                            M.Region.set(M.Region.size() - 1, name);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(Line);
            System.out.println("Error reading map file: " + FileName + ".  Make sure the file exists and is readable!");
            System.exit(1);
        }
        GlobalMapMarkers = M.Marker;
        return M;
    }

    /* Step Two of Map File Merging */
    public Vector ParseForSNPs(Vector DataFiles, String delimiter, int row, int column, int orientation) throws Exception {
        for(int i = 0; i < DataFiles.size(); i++)
        {
            FileReader fr = new FileReader(DataFiles.get(i).toString());
            BufferedReader br = new BufferedReader(fr);
            String Line = new String();
            String[] Cells;
            int currentrow = 0;
            while ((Line = br.readLine()) != null) {
                if (currentrow == row - 1) {
                    Cells = Line.split(delimiter);
                    switch (orientation) {
                    case 0: //horizonital
                        for (int j = column - 1; j < Cells.length; j++) {
                            SNPs.add(Cells[j]);
                        }
                        break;
                    case 1: //vertical
                        SNPs.add(Cells[column]);
                        break;
                    }
                }
                currentrow = currentrow + 1;
            }
        }
        return SNPs;
    }

    /* Step Three of Map File Merging */
    public String[] DefineOrder(SAGEMap M, Vector SNPs) {
        String[] Order = new String[M.Marker.size()];
        String Marker = "";
        String SNP = "";
        for (int i = 0; i < M.Marker.size(); i++) {
            Marker = M.Marker.elementAt(i).toString();
            for (int j = 0; j < SNPs.size(); j++) {
                SNP = SNPs.elementAt(j).toString();
                if (Marker.compareTo(SNP) == 0) {
                    Order[i] = SNP;
                }
                if (Order[i] == null) {
                    Order[i] = "NA";
                }
            }
        }

        return Order;
    }

    /* Step Four of Map File Merging*/
    public Vector ParseKeys(Vector DataFiles, String delimiter, int row, Vector Columns) throws Exception {
        FileReader fr = new FileReader(DataFiles.get(0).toString());
        java.util.Collections.sort(Columns);
        BufferedReader br = new BufferedReader(fr);
        String Line = new String();
        String[] Cells;
        int currentrow = 0;
        while ((Line = br.readLine()) != null) {
            if (currentrow >= row) {
                Cells = Line.split(delimiter);
                String TempKey = "";
                for (int i = 0; i < Columns.size(); i++) {
                    TempKey = TempKey + Cells[Integer.valueOf(Columns.elementAt(i).toString()) - 1];
                }
                Keys.add(TempKey);
            }
            currentrow = currentrow + 1;
        }
        return Keys;
    }

    private Vector GetAllKeys(Vector FileNames, String delimiter, Vector Columns){
        Vector AllFileKeys = new Vector();
        try{
            String Line = "";
            String Key = "";
            String[] Cells;

            for (int i = 0; i < FileNames.size(); i++) {
                Vector FileKeys = new Vector();
                BufferedReader in = new BufferedReader(new FileReader(FileNames.elementAt(i).toString()));
                Line = in.readLine();
                while ((Line = in.readLine()) != null) {
                    Cells = Line.split(delimiter);
                    for (int j = 0; j < Columns.size(); j++) {
                        Key = Key.concat(Cells[Integer.valueOf(Columns.get(j).toString()) - 1]);
                    }
                    FileKeys.add(Key);
                    Key = "";
                }
                java.util.Collections.sort(FileKeys);
                AllFileKeys.add(FileKeys);
            }
        }catch(IOException e){};
        return AllFileKeys;
    }
    private Vector GetCommonKeys(Vector AllKeys){
        Vector First = (Vector) AllKeys.get(0);
        Vector Current;
        Vector CommonKeys = new Vector();
        int Common = 0;
        for (int i = 0; i < First.size(); i++) {
            Common = 0;
            for (int j = 1; j < AllKeys.size(); j++) {
                Current = (Vector) AllKeys.get(j);
                if (First.get(i).toString().compareTo(Current.get(i).toString()) == 0) {
                    Common = Common + 1;
                }
                if (Common == AllKeys.size() - 1) {
                    CommonKeys.add(First.get(i).toString());
                }
            }
        }
        return CommonKeys;
    }
    private Vector AddRows(Vector FileName, String delimiter, Vector Columns, Vector CommonKeys){
        Vector Files = new Vector();
        String[] Headers;
        String[] Cells;
        String Line = "";
        String Key = "";
        try{
            for (int i = 0; i < FileName.size(); i++) {
                Vector Rows = new Vector();
                BufferedReader in = new BufferedReader(new FileReader(FileName.elementAt(i).toString()));
                Line = in.readLine();
                Headers = Line.split(delimiter);
                FileHeaders.add(Headers);
                while ((Line = in.readLine()) != null) {
                    Cells = Line.split(delimiter);
                    for (int j = 0; j < Columns.size(); j++) {
                        Key = Key.concat(Cells[Integer.valueOf(Columns.get(j).toString()) - 1]);
                    }
                    for (int j = 0; j < CommonKeys.size(); j++) {
                        if (Key.compareTo(CommonKeys.get(j).toString()) == 0) {
                            Rows.add(Cells);
                            break;
                        }
                    }
                    Key = "";
                    Line= null;
                }
                Files.add(Rows);
            }
        }catch(IOException e){};
        return Files;
    }
    private Vector OuputData(Vector Files, SAGEMap M, int NumIndividuals){
        String[] FileHeader;
        int RowLength = M.Marker.size() + SNPINDEX-1;
        int Card = -1;
        String FileSNP = "";
        String MapSNP = "";
        Vector RowOutput = new Vector();
        Vector Individuals = new Vector();
        String[] CurrentIndividual = new String[RowLength];
        //Populate each individual's record
        for(int i = 0; i < NumIndividuals; i++)
        {
            String[] CurrentRow = new String[RowLength];
            //Go file by file
            for(int j = 0; j < Files.size(); j++)
            {
                Individuals = (Vector) Files.get(j);
                FileHeader = (String[]) FileHeaders.get(j);
                BitSet HeaderUsed = new BitSet(FileHeader.length);
                CurrentIndividual = (String[]) Individuals.get(i);
                //Copy phenotype information
                if(j == 0)
                {
                    for (int k = 0; k < SNPINDEX - 1; k++) {
                        CurrentRow[k] = CurrentIndividual[k];
                    }
                }
                for (int k = 0; k < M.Marker.size(); k++) {
                    MapSNP = M.Marker.get(k).toString();
                    Card = FileHeader.length - SNPINDEX+1;
                    if(HeaderUsed.cardinality() < Card)
                    {
                        for (int w = SNPINDEX - 1; w < FileHeader.length; w++) {
                            FileSNP = FileHeader[w].toString();
                            //Check if there is a match between marker snp and file snp.
                            if (FileSNP.compareTo(MapSNP) == 0) {
                                HeaderUsed.set(w);
                                CurrentRow[SNPINDEX - 1 +k] = CurrentIndividual[w];
                                w = FileHeader.length;
                            }
                        }
                    }
                    else
                    {
                        k = M.Marker.size();
                    }
                }
            }

            for(int j = 0; j < CurrentRow.length; j++)
            {
                if(CurrentRow[j] == null)
                {
                    CurrentRow[j] = MISSING + AlleleDelimeter +MISSING;
                }
            }
            RowOutput.add(CurrentRow);
        }
        return RowOutput;
    }
    private void OutputMergedFile(String FileName, SAGEMap M, Vector DataOutput){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FileName));
            String[] Temp = (String[]) FileHeaders.get(0);
            for (int i = 0; i < SNPINDEX - 1; i++) {
                out.write(Temp[i]);
                out.write("\t");
            }
            for (int i = 0; i < M.Marker.size(); i++) {
                out.write(M.Marker.get(i).toString());
                out.write("\t");
            }
            out.write("\n");
            for (int i = 0; i < DataOutput.size(); i++) {
                String[] Row = (String[]) DataOutput.get(i);
                for (int j = 0; j < Row.length; j++) {
                    out.write(Row[j]);
                    out.write("\t");
                }
                out.write("\n");
            }
            out.close();
        }catch (IOException e) {
            System.out.println("Error writing output file: " + FileName);
            System.exit(1);
        }

    }
    public SAGEMap MergeRawFiles(Vector FileName, String delimiter, Vector Columns, SAGEMap M, String OutFileName) {
        //Get keys from all files
        Vector FileKeys = GetAllKeys(FileName,delimiter,Columns);
        System.out.println("SNP Keys generated");
        //Get keys they have in common
        Vector CommonKeys = GetCommonKeys(FileKeys);
        System.out.println("SNP Common List generated");
        //Find CommonKeys across files & take their rows
        Vector Files = AddRows(FileName,delimiter,Columns,CommonKeys);
        System.out.println("Rows in Files generated");
        //Order the SNPs correctly
        Vector OutputRows = OuputData(Files, M, CommonKeys.size());
        System.out.println("Rows for Output generated");
        //output the new file
        OutputMergedFile(OutFileName, M, OutputRows);
        return M;
    }

    public String GetAlleleDelimeter() {
        return AlleleDelimeter;
    }

    public String SetAlleleDelimeter(String Value) {
        AlleleDelimeter = Value;
        return AlleleDelimeter;
    }

    public void parseMapFile1(String MapFilePath) {
        P.ClearProgress();
        P.SetFileSize(MapFilePath);

        markerList = new Vector();
        distanceList = new Vector();

        String strFileLine = new String();

        try {
            BufferedReader in = new BufferedReader(new FileReader(MapFilePath));
            int i = 0;
            while ((strFileLine = in.readLine()) != null) {
                strFileLine = strFileLine.trim();

                MainFrame.mainFrame1.setProgressValue(i++);

                if (strFileLine.startsWith("{")) {
                    strFileLine = strFileLine.substring(1, strFileLine.length()).trim();
                }
                if (strFileLine.startsWith("}")) {
                    strFileLine = strFileLine.substring(1, strFileLine.length()).trim();
                }

                java.util.StringTokenizer st = new java.util.StringTokenizer(strFileLine, "=");

                if (st.countTokens() == 2) {

                    String type = st.nextToken().trim();
                    String name = st.nextToken().trim().replaceAll("\"", "");

                    if (type.compareTo("marker") == 0) {
                        markerList.add(name);
                    } else if (type.compareTo("distance") == 0 || type.compareTo("theta") == 0) {
                        Double dis = new Double(name);
                        distanceList.add(dis);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector getSnpList() {
        return markerList;
    }

    public Vector getDistanceList() {
        return distanceList;
    }

    private void jbInit() throws Exception {
    }


}
