package snpclip;

public class Individual {
    public String FamilyID;
    public String ID;
    public String FID;
    public String MID;
    public int Sex;
    public int Phenotype;
    public String Population;
    public boolean Founder;
    public Individual(String FamID,String IID, String FaID, String MoID, int S,int Pheno,String Pop, String Cur) {
        FamilyID = FamID;
        ID = IID;
        FID = FaID;
        MID = MoID;
        Sex = S;
        Phenotype = Pheno;
        Population = Pop;
        if(MID.compareTo("0") == 0 && FID.compareTo("0") == 0 && Pop.compareTo(Cur) == 0)
        {
            Founder = true;
        }
        else
        {
            Founder = false;
        }
    }
}
