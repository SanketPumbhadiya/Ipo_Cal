package com.example.ipocalculation;

import java.io.Serializable;

public class LotSizeData {
    int retail, S_hni, B_hni;

    public LotSizeData() {
    }

//    public LotSizeData(int retail,int s_hni,int b_hni){
//        this.retail = retail;
//        this.S_hni = s_hni;
//        this.B_hni = b_hni;
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

    public int getRetail() {
        return retail;
    }

    public int getS_hni() {
        return S_hni;
    }

    public int getB_hni() {
        return B_hni;
    }

}