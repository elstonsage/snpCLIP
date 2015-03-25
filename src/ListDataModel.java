package snpclip;

import javax.swing.DefaultListModel;


class ListDataModel extends DefaultListModel
{
    String variable;
    int operator;
    String value;
    String op_s[] = {"=", "<", ">", "<=", ">=", "<>"};

    ListDataModel(String var, int op, String val)
    {
        variable = var;
        operator = op;
        value = val;
    }

    public String toString()
    {
        return variable + op_s[operator] + value;
    }
     }
