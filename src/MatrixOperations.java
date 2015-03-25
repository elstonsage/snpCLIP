package snpclip;
import java.util.BitSet;
import java.util.Vector;
import java.text.NumberFormat;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class MatrixOperations {
    Progress P = new Progress();
    Vector AlleleFreq =  new Vector();
    NumberFormat nf = NumberFormat.getInstance();

    /** Creates a new instance of BMatrix */
    public MatrixOperations() {
        nf.setMinimumIntegerDigits (nf.getMinimumIntegerDigits());
        nf.setMinimumFractionDigits (1);
        nf.setMaximumFractionDigits (3);
    }
    /** ************************************************************************* **
    ** *****************          FilterMissingSNPs             ***************** **
    ** ************************************************************************** **
     * Description: Filters the SNP data based on missingness
     * Used By: GUI
     * Input:  Matrix to filter, Min % Missing, Max % Missing
     * Output: BitSet representing enabled SNPs
    ----------------------------------------------------------------------------- */
    public BitSet FilterMissingSNPs(MatrixCore M, double MinThreshold, double MaxThreshold){
        double NumIndividuals = M.GetNUMINDIVIDUALS();
        double PercentMissing = 0.0;
        BitSet Filter = new BitSet(M.GetNUMSNPs());
        BitSet Error = new BitSet(M.GetNUMALLELES());
        P.ClearProgress();
        P.SetProgress(0);
        P.SetPer(50/NumIndividuals);

        for(int i = 0; i < M.GetNUMSNPs(); i++)
        {
            P.SetProgress((int)(P.GetPer()+0.5));
            Error = (BitSet)M.SNPErrors.get(i);
            PercentMissing = (double)((double)Error.cardinality()/(double)M.GetNUMALLELES());
            if(PercentMissing >= MinThreshold && PercentMissing <= MaxThreshold)
            {
                Filter.set(i,false);
            }
            else
            {
                Filter.set(i);
            }
        }
        return Filter;
    }
    /** ************************************************************************* **
    ** *****************          FilterAlleleFreq              ***************** **
    ** ************************************************************************** **
     * Description: Filter based on minor allele frequency
     * Used By: GUI
     * Input:  MatrixCore, Min % and Max % Minor Allele Frequency
     * Output: Filter of enabled snps
    ----------------------------------------------------------------------------- */
    public BitSet FilterAlleleFreq(MatrixCore M, double MinThreshold, double MaxThreshold){
        AlleleFreq.removeAllElements();

            double NumAlleles = M.GetNUMALLELES();
            double MinorAlleles = 0.0;
            double PercentMinor = 0.0;
            BitSet Filter = new BitSet();
            BitSet Info = new BitSet();
            BitSet Error = new BitSet();

            for (int i = 0; i < M.GetNUMSNPs(); i++) {
                Info = (BitSet) M.SNPs.elementAt(i);
                Error = (BitSet) M.SNPErrors.elementAt(i);
                NumAlleles = M.GetNUMALLELES() - Error.cardinality();
                MinorAlleles = M.GetNUMALLELES() - Info.cardinality();
                PercentMinor = MinorAlleles / NumAlleles;
                if (PercentMinor >= MinThreshold && PercentMinor <= MaxThreshold || (NumAlleles == 0 && MinorAlleles == 0)) {
                    Filter.set(i, false);
                }
                else {
                    Filter.set(i);
                }

                AlleleFreq.add(nf.format(PercentMinor));

            }
        return Filter;

    }
    public double ReturnMinorAlleleFreq(MatrixCore M, int SNP){
        double NumAlleles = M.GetNUMALLELES();
        double MinorAlleles = 0.0;
        double PercentMinor = 0.0;
        BitSet Info = new BitSet();
        BitSet Error = new BitSet();
        Info = (BitSet)M.SNPs.elementAt(SNP);
        Error = (BitSet)M.SNPErrors.elementAt(SNP);
        NumAlleles = M.GetNUMALLELES() - Error.cardinality();
        MinorAlleles = M.GetNUMALLELES() - Info.cardinality();
        PercentMinor = MinorAlleles/NumAlleles;
        return PercentMinor;
    }
    /** ************************************************************************* **
    ** *****************          FilterLD            ***************** **
    ** ************************************************************************** **
     * Description: Pairwise LD Calculation
     * Used By: GUI
     * Input:  MatrixCore, Min % and Max % LD
     * Output: Filter of enabled snps
    ----------------------------------------------------------------------------- */
    public BitSet FilterLD(MatrixCore M, double MinThreshold, double MaxThreshold){
        BitSet Filter = new BitSet();
        BitSet Filter2 = new BitSet();
        BitSet Added = new BitSet();
        Filter.set(0,M.GetNUMSNPs(),true);
        Filter2.set(0,M.GetNUMSNPs(),true);
        Added.set(0,M.GetNUMSNPs(),true);
        BitSet SNPOneInfo = new BitSet();
        BitSet SNPOneError = new BitSet();
        BitSet SNPTwoInfo = new BitSet();
        BitSet SNPTwoError = new BitSet();

        double NumAlleles = M.GetNUMALLELES();
        int[][] LDMatrix = new int[3][3];
        double MinorAlleles = 0.0;
        Vector LDBlock = new Vector();
        int StartIndex = -1;
        double LD = 0.0;
        int difference = 1;
        int run = 0;
        boolean SNP1 = false;
        boolean SNP2 = false;
        int count = 0;
        int Previous = 0;
        int S1 = -1;
        int S2 = -2;
        while(difference > 0)
        {
            for (int i = 0; i < M.GetNUMSNPs()-1; i++) {
                switch(run){
                case 0:
                    SNPOneInfo = (BitSet) M.SNPs.elementAt(i);
                    SNPOneError = (BitSet) M.SNPErrors.elementAt(i);
                    SNPTwoInfo = (BitSet) M.SNPs.elementAt(i + 1);
                    SNPTwoError = (BitSet) M.SNPErrors.elementAt(i + 1);
                    break;
                default:
                    if(M.GetNUMSNPs() - Filter.cardinality() > 1 )
                    {
                        while (SNP1 == false || SNP2 == false) {
                            for (int j = Previous; j < M.GetNUMSNPs(); j++) {
                                if(j == 5520)
                                {
                                    System.out.println("");
                                }
                                if (Filter.get(j) == false && SNP1 == false)
                                {
                                    SNPOneInfo = (BitSet) M.SNPs.elementAt(j);
                                    SNPOneError = (BitSet) M.SNPErrors.elementAt(j);
                                    System.out.print("SNP1: " + j + "\t");
                                    SNP1 = true;
                                    S1 = j;
                                } else if (Filter.get(j) == false && SNP1 == true)
                                {
                                        SNPTwoInfo = (BitSet) M.SNPs.elementAt(j);
                                        SNPTwoError = (BitSet) M.SNPErrors.elementAt(j);
                                        SNP2 = true;
                                        System.out.println("SNP2: " + j);
                                        Previous = j;
                                        S2 = j;
                                        j = M.GetNUMSNPs();

                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Only One SNP Left");
                        return Filter;
                    }
                }
                SNP1 = false;
                SNP2 = false;
                LDMatrix = CalculatePairwiseLD(M,SNPOneInfo,SNPOneError,SNPTwoInfo,SNPTwoError);
                LD = 2 * LDMatrix[0][0] + LDMatrix[0][1] + LDMatrix[1][0] + (0.5) * LDMatrix[1][2];
                LD = Math.abs((1 / M.GetNUMINDIVIDUALS()) * LD - 2 * ReturnMinorAlleleFreq(M, i) * ReturnMinorAlleleFreq(M, i + 1));
                if(run > 0)
                {
                    count++;
                    System.out.println("Run: "+run +"\t" +count);
                }

                if (LD >= MinThreshold && LD <= MaxThreshold)
                {
                    if(LDBlock.size() == 0)
                    {
                        StartIndex = i;
                    }
                    if(Added.get(i))
                    {
                        LDBlock.add(new Double(ReturnMinorAlleleFreq(M, i)));
                        Added.set(i, false);
                    }
                    if(Added.get(i+1))
                    {
                        LDBlock.add(new Double(ReturnMinorAlleleFreq(M, i + 1)));
                        Added.set(i+1,false);
                    }
                }
                else if (LDBlock.size() > 0)
                {
                    Object max = Collections.max(LDBlock);
                    int MaxIndex = LDBlock.indexOf(max);
                    for (int j = 0; j < LDBlock.size(); j++)
                    {
                        if (j == MaxIndex)
                        {
                            Filter.set(j + StartIndex, false);
                        }
                        else
                        {
                            Filter.set(j + StartIndex);
                        }
                    }
                    StartIndex = -1;
                    LDBlock.removeAllElements();
                }
                if(S1 == S2 && run >0)
                {
                    i = M.GetNUMSNPs();
                }
                S1 = -1;
                S2 = -2;
            }

            if (LDBlock.size() > 0)
            {
                Object max = Collections.max(LDBlock);
                int MaxIndex = LDBlock.indexOf(max);
                for (int j = 0; j < LDBlock.size(); j++)
                {
                    if (j == MaxIndex)
                    {
                        Filter.set(j + StartIndex, false);
                    }
                    else
                    {
                        Filter.set(j + StartIndex);
                    }
                }
                StartIndex = -1;
                LDBlock.removeAllElements();
            }
            difference = Math.abs(Filter2.cardinality() - Filter.cardinality());
            System.out.println("The Diff: " +difference);
            Filter2 = Filter;
            System.out.println("F1 Card: "+Filter.cardinality());
            Filter = new BitSet();
            Filter.set(0,M.GetNUMSNPs(),false);
            Filter.or(Filter2);
            System.out.println("Run Number: " +run);
            run = run+1;

        }
        return Filter2;
    }

    private int[][] CalculatePairwiseLD(MatrixCore M, BitSet SNPOneInfo, BitSet SNPOneError, BitSet SNPTwoInfo, BitSet SNPTwoError){
        double NumAlleles = M.GetNUMALLELES();
        int[][] LDMatrix = new int[3][3];
        for (int j = 0; j < NumAlleles; )
        {
            if (SNPOneError.get(j) || SNPOneError.get(j + 1) || SNPTwoError.get(j) || SNPTwoError.get(j + 1)) {
                //Do Nothing.
            }
            //if all four are Major
            else if (SNPOneInfo.get(j) && SNPOneInfo.get(j + 1) && SNPTwoInfo.get(j) && SNPTwoInfo.get(j + 1)) {
                LDMatrix[0][0] = LDMatrix[0][0] + 1;
            }
            //If first SNP Alleles are Major & One of the SNP Two Alleles are Minor
            else if (SNPOneInfo.get(j) && SNPOneInfo.get(j + 1) &&
                     ((SNPTwoInfo.get(j) == false && SNPTwoInfo.get(j + 1)) ||
                      (SNPTwoInfo.get(j) && SNPTwoInfo.get(j + 1) == false))) {
                LDMatrix[0][1] = LDMatrix[0][1] + 1;
            }
            //If first SNP alleles are Major && both of SNP Two Alleles are Minor
            else if (SNPOneInfo.get(j) && SNPOneInfo.get(j + 1) && SNPTwoInfo.get(j) == false &&
                     SNPTwoInfo.get(j + 1) == false) {
                LDMatrix[0][2] = LDMatrix[0][2] + 1;
            }
            //One of SNP Ones are Minor and both of SNO Two are Major
            else if (((SNPOneInfo.get(j) && SNPOneInfo.get(j + 1) == false) ||
                      (SNPOneInfo.get(j) == false && SNPOneInfo.get(j + 1))) && SNPTwoInfo.get(j) &&
                     SNPTwoInfo.get(j + 1)) {
                LDMatrix[1][0] = LDMatrix[1][0] + 1;
            }
            //One of SNP Ones are Minor and one of SNP Two are Minor
            else if (((SNPOneInfo.get(j) && SNPOneInfo.get(j + 1) == false) ||
                      (SNPOneInfo.get(j) == false && SNPOneInfo.get(j + 1))) &&
                     ((SNPTwoInfo.get(j) == false && SNPTwoInfo.get(j + 1)) ||
                      (SNPTwoInfo.get(j) && SNPTwoInfo.get(j + 1) == false))) {
                LDMatrix[1][1] = LDMatrix[1][1] + 1;
            }
            //One of SNP Ones are Minor and both SNP Two are Minor
            else if (((SNPOneInfo.get(j) && SNPOneInfo.get(j + 1) == false) ||
                      (SNPOneInfo.get(j) == false && SNPOneInfo.get(j + 1))) && SNPTwoInfo.get(j) == false &&
                     SNPTwoInfo.get(j + 1) == false) {
                LDMatrix[1][2] = LDMatrix[1][2] + 1;
            }
            //Both of SNP Ones are Minor and both SNP Two are Major
            else if (SNPOneInfo.get(j) == false && SNPOneInfo.get(j + 1) == false && SNPTwoInfo.get(j) &&
                     SNPTwoInfo.get(j + 1)) {
                LDMatrix[2][0] = LDMatrix[2][0] + 1;
            }
            //Both of SNP Ones are Minor and One of SNP Two are Minor
            else if (SNPOneInfo.get(j) == false && SNPOneInfo.get(j + 1) == false &&
                     ((SNPTwoInfo.get(j) == false && SNPTwoInfo.get(j + 1)) ||
                      (SNPTwoInfo.get(j) && SNPTwoInfo.get(j + 1) == false))) {
                LDMatrix[2][1] = LDMatrix[2][1] + 1;
            }
            //All Four Are Minor
            else if (SNPOneInfo.get(j) == false && SNPOneInfo.get(j + 1) == false && SNPTwoInfo.get(j) == false &&
                     SNPTwoInfo.get(j + 1) == false) {
                LDMatrix[2][2] = LDMatrix[2][2] + 1;
            }
            j = j + 2;
        }
        return LDMatrix;
    }

    /** ************************************************************************* **
    ** *****************          FilterRegion             ***************** **
    ** ************************************************************************** **
     * Description: Filters the SNP data based on location
     * Used By: GUI
     * Input:  Matrix to filter, Snp list, Heder of data file, snpIndex of datafile
     * Output: BitSet representing enabled SNPs
    ----------------------------------------------------------------------------- */
    public BitSet FilterRegion(MatrixCore M, Vector snpList, String Header[], int SnpIndex){
        double NumIndividuals = M.GetNUMINDIVIDUALS();
        List Plz = Arrays.asList(Header);
        Vector V = new Vector(Plz);
        BitSet Filter = new BitSet(M.GetNUMSNPs());
        Filter.set(0,M.GetNUMSNPs(),true);
        P.ClearProgress();
        P.SetProgress(0);
        P.SetPer(50/NumIndividuals);
        int indexD = 0;
        int indexM = 0;

        for(indexM = 0; indexM < snpList.size(); indexM++)
        {
            indexD = V.indexOf(snpList.get(indexM));
            if( indexD > 0)
            {
                Filter.set(indexD-MainFrame.mainFrame1.Parse.GetSNPINDEX()+1, false);
            }
        }
        V = null;
        Plz = null;

        return Filter;
    }


    /** ************************************************************************* **
    ** *****************          FilterLocation             ***************** **
    ** ************************************************************************** **
     * Description: Filters the SNP data based on location
     * Used By: GUI
     * Input:  Matrix to filter, Snp list, Heder of data file, snpIndex of datafile
     * Output: BitSet representing enabled SNPs
    ----------------------------------------------------------------------------- */
    public BitSet FilterLocation(MatrixCore M, List snpList, String Header[], int SnpIndex){
        double NumIndividuals = M.GetNUMINDIVIDUALS();
        BitSet Filter = new BitSet(M.GetNUMSNPs());
        P.ClearProgress();
        P.SetProgress(0);
        P.SetPer(50/NumIndividuals);


        int indexD = 0;
        int indexM = 0;

        String snpNameinDataFile = "";
        String snpNameinMapFile = "";

        for(indexD = 0; indexD < M.GetNUMSNPs(); indexD++)
        {
            P.SetProgress((int)(P.GetPer()+0.5));

            snpNameinDataFile = Header[indexD+SnpIndex-1];
            if(indexM < snpList.size())
                snpNameinMapFile = snpList.get(indexM).toString();

            if(snpNameinDataFile.compareTo(snpNameinMapFile)==0)
            {
                Filter.set(indexD,false);
                indexM++;
            }
            else
            {
                Filter.set(indexD);
            }

        }
        return Filter;
    }

    /** ************************************************************************* **
    ** *****************          ApplyFilter                   ***************** **
    ** ************************************************************************** **
     * Description: Applys a existing filter to a specified data set.
     * Used By: Main Class
     * Input:  MatrixCore (Data to apply filter to), BitSet (Include/Exclude Information), Include Bit (Include or Exclude data)
     *         0 - Include, 1 - Exclude
     * Output: FilterInfo for reporting
    ----------------------------------------------------------------------------- */
    public FilterInfo ApplyFilter(MatrixCore M, BitSet F, int Include, String Applied, FilterInfo FI){
        switch (Include) {
            case 0:
                F.or(FI.GetFilter()) ;
                FI.SetFilter(F);
                FI.AddOperation(Applied);
                break;
            case 1:
                F.flip(0,M.GetNUMSNPs());
                F.or(FI.GetFilter()) ;
                FI.SetFilter(F);
                FI.AddOperation(Applied);
                break;
            default:
                System.out.println("Fatal Error: Invalid include/exclude information!");
                break;
        }
        return FI;
    }
}
