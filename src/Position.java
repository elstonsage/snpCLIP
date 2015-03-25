package snpclip;

import java.util.ArrayList;

class Position
{
    private int startIndex = 0;
    private int endIndex = 0;
    private int length = 0;
    private String startName = "";
    private String endName = "";
    private String ID="";
    int caseFlag[] = null;
    int controlFlag[] = null;
    int snpFlag[][] = new int[2][];
    double geneticLength=0.0;

    Boolean selected = true;

    private ArrayList caseMissingList = new ArrayList();
    private ArrayList controlMissingList = new ArrayList();


    String SnpNameList[] = null;

    public Position()
    {
        this.startIndex = 0;
        this.endIndex = 0;
        this.length = 0;
    }

    public Position(int startIndex, int endIndex, int length)
    {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.length = length;
    }

    void setStartIndex(int value)
    {
        startIndex = value;
    }

    void setEndIndex(int value)
    {
        endIndex = value;
    }

    void setLength()
    {
        length =  endIndex - startIndex + 1;
    }

    void setLength(int new_length)
    {
        length =  new_length;
    }

    int getLength()
    {
        return length;
    }

    double getgeneticLength()
    {
        return geneticLength;
    }

    void setgeneticLength(double new_length)
    {
        geneticLength =  new_length;
    }

    int getStartIndex()
    {
        return startIndex;
    }

    int getEndIndex()
    {
        return endIndex;
    }


    void setStartName(String name)
    {
        startName = name;
    }

    void setEndName(String name)
    {
        endName = name;
    }

    void setID(String id)
    {
        ID = id;
    }

    String getStartName()
    {
        return startName;
    }

    String getEndName()
    {
        return endName;
    }

    String getSelectedID()
    {
        return ID;
    }

    void setCaseSnpFlag(int in[])
    {
        caseFlag = in;
    }

    int[] getCaseSnpFlag()
    {
        return caseFlag;
    }

    void setControlSnpFlag(int in[])
    {
        controlFlag = in;
    }

    int[] getControlSnpFlag()
    {
        return controlFlag;
    }

    int[][] getSnpFlag()
    {
        snpFlag[0] = caseFlag;
        snpFlag[1] = controlFlag;
        return snpFlag;
    }

    void setSnpNameList(String in[])
    {
        SnpNameList = in;
    }

    String[] getSnpNameList()
    {
        return SnpNameList;
    }

    void setSelectedValue(Boolean in)
    {
        selected = in;
    }

    Boolean getSelectedValue()
    {
        return selected;
    }

    void invertSelected()
    {
        selected = !selected;
    }

    void setCaseMissingList(String item)
    {
        caseMissingList.add(item);
    }

    String getCaseMissingList()
    {
        String result = caseMissingList.toString();
        result = result.substring(1, result.length()-1);
        return result;
    }

    void setControlMissingList(String item)
    {
        controlMissingList.add(item);
    }

    String getControlMissingList()
    {
        String result = controlMissingList.toString();
        result = result.substring(1, result.length()-1);
        return result;

        //return controlMissingList.toString();
    }

    }
