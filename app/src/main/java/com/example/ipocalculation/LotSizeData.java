package com.example.ipocalculation;

import java.io.Serializable;

public class LotSizeData implements Serializable {
    int retail, S_hni, B_hni, Custom;
    public LotSizeData(){}

//    public LotSizeData(int retail,int s_hni,int b_hni,int custom){
//        this.retail = retail;
//        this.S_hni = s_hni;
//        this.B_hni = b_hni;
//        this.Custom = custom;
//    }

    public void setRetail(int retail) {
        this.retail = retail;
    }

    public void setS_hni(int s_hni) {
        S_hni = s_hni;
    }

    public void setB_hni(int b_hni) {
        B_hni = b_hni;
    }

    public void setCustom(int custom) {
        Custom = custom;
    }

    public int getRetail() {
        return retail;
    }
    public int getS_hni() {
        return S_hni;
    }
    public int getB_hni() {
        return B_hni;
    }
    public int getCustom() {
        return Custom;
    }
}