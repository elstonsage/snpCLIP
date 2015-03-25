package snpclip;

import java.io.File;

public class Progress {
    private double Progress = 0;
    private double FileSize = 0;
    private long LineSize = 0;
    private double CurSize = 0;
    private double PerNor = 0;
    private double Per = 0.00;
    private int Index = 0;
    private int Rest = 0;

    public Progress() {
    }

    /* Initialization Calls */
    public int InitializeCalculateAlleles(int NUMALLELES){
        ClearProgress();
        SetRest(100 - GetProgress());
        MainFrame.mainFrame1.SetProgress(GetProgress());
        SetPer(NUMALLELES/GetRest());
        return 0;
    }

    public int UpdateParseSnp(int length){
        CurSize = CurSize+length;
        PerNor = (CurSize/FileSize);
        Progress = (int)(PerNor*100);
        MainFrame.mainFrame1.SetProgress((int)Progress);
        return 0;
    }
    public int UpdateParseHap(int length){
        CurSize = CurSize+length;
        PerNor = (CurSize/FileSize);
        Progress = (int)(PerNor*100);
        MainFrame.mainFrame1.SetProgress((int)Progress);
        return 0;
    }
    public int UpdateCalculateAlleles(int i){
        if (i % Per == 0) {
            SetProgress(GetProgress()+1);
            MainFrame.mainFrame1.SetProgress(GetProgress());
        }
        return 0;
    }


/* Basic calls to update variables */
    public int ClearProgress(){
        Progress = 0;
        FileSize = 0;
        LineSize = 0;
        PerNor = 0;
        Per = 1;
        Index = 0;
        Rest = 0;
        MainFrame.mainFrame1.SetProgress((int)Progress);
        return 0;
    }
    public int SetFileSize(String FileName)    {
        FileSize = new File(FileName).length();
        return 0;
    }
    public int SetLineSize(int length){
        LineSize = length;
        return 0;
    }
    public int SetIndex(int length){
        Index = length;
        return 0;
    }
    public int SetProgress(int Value){
        Progress = Value;
        MainFrame.mainFrame1.SetProgress(Value);
        return 0;
    }
    public int GetProgress(){
        return (int)Progress;
    }
    public int SetRest(int Value){
        Rest = Value;
        return 0;
    }
    public int GetRest(){
        return Rest;
    }
    public double GetPer(){
        return Per;
    }
    public int SetPer(double Value){
        Per = Value;
        return 0;
    }
    public int GetIndex(){
        return Index;
    }
}
