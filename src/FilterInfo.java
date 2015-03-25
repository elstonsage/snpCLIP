package snpclip;
import java.util.BitSet;
import java.util.Vector;
public class FilterInfo {
    private int NUMSNPs;
    private Vector Operations = new Vector();
    private BitSet Filter = new BitSet();
    public FilterInfo() {
    }
    public FilterInfo(FilterInfo F) {
        this.Operations = F.Operations;
        this.Filter = F.Filter;
        this.NUMSNPs = F.NUMSNPs;
    }
    public int GetNUMSNPs(){
        return NUMSNPs;
    }
    public Vector GetOperations(){
        return Operations;
    }
    public int AddOperation(String Op){
        Operations.add(Op);
        return 0;
    }
    public int SetFilter(BitSet F){
        Filter = F;
        NUMSNPs = (MainFrame.mainFrame1.MC.GetNUMSNPs() - F.cardinality());
        return 0;
    }
    public BitSet GetFilter(){
        return Filter;
    }
    public int PrintFilter(MatrixCore M, String Applied){
        System.out.println("--- " +Applied + " ---");
        System.out.println("Individuals: " +M.GetNUMINDIVIDUALS());
        System.out.println("SNPs: " +M.GetNUMSNPs());
        System.out.println("Alleles: " +M.GetNUMALLELES());
        System.out.println("Cardinality: " +Filter.cardinality());
        System.out.println("--- End " +Applied + " ---");
        System.out.println();
        return 0;
    }
}
