package snpclip;
import java.util.Vector;


public class MatrixCore {
    public Vector SNPs = new Vector();
    public Vector SNPErrors = new Vector();
    private int NUMSNPs;
    private int NUMALLELES;
    private int NUMINDIVIDUALS;

    public MatrixCore() {
    }

    public int SetNUMINDIVIDUALS(int Value) {
        NUMINDIVIDUALS = Value;
        return 0;
    }
    public int GetNUMINDIVIDUALS(){
        return NUMINDIVIDUALS;
    }
    public int SetNUMSNPs(int Value){
        NUMSNPs = Value;
        return 0;
    }
    public int GetNUMSNPs(){
        return NUMSNPs;
    }
    public int GetNUMALLELES(){
        return NUMALLELES;
    }
    public int SetNUMALLELES(int Value){
        NUMALLELES = Value;
        return 0;
    }
}
