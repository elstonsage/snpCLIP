package snpclip;
import java.util.Vector;

public class SNP {
    Vector Values = new Vector();
    Vector Scores = new Vector();
    String MajorAllele = "";
    String MinorAllele = "";

    /**
     * Creates a new instance of SNP
     */
    public SNP() {
    }
    public String SetMajor(String MISSING)
    {
        String Major = "Not initialized";
        switch(Values.size())
        {
        case 1:
            if(Values.get(0).toString().compareTo(MISSING)==0)
            {
                System.out.println("ONLY MISSING VALUES!");
                break;
            }
            else
            {
                Major = Values.get(0).toString();
                break;
            }
        default:
            Major = Values.get(Values.size()-1).toString();
        }
        MajorAllele = Major;
        return Major;
    }
    public String SetMinor(String MISSING)
    {
        String Minor = "Not initialized";
        switch(Values.size())
        {
        case 1:
            Minor = "No Min Value - Size 1";
            break;
        default:
            if(Values.get(Values.size()-2).toString().compareTo(MISSING) == 0)
            {
                Minor = "No Min Value - Missingness present";
                break;
            }
            else
            {
                Minor = Values.get(Values.size()-2).toString();
                break;
            }
        }
        MinorAllele = Minor;
        return Minor;
    }

    public String ReturnMajor()
    {
        return MajorAllele;
    }
    public String ReturnMinor()
    {
        return MinorAllele;
    }
    }
