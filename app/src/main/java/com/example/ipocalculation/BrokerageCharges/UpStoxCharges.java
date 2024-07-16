package com.example.ipocalculation.BrokerageCharges;

import android.util.Log;

import com.example.ipocalculation.CommonCharges;
import com.example.ipocalculation.MainActivity;
import com.example.ipocalculation.SetMoreDetails;

public class UpStoxCharges extends CommonCharges {
    double DPCharges = 18.5;

    public UpStoxCharges(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, SetMoreDetails callBack) {
        super(callBack);
        this.purchaseQuan = purchaseQuan;
        this.purchaseSharePrice = purchaseSharePrice;
        this.sellSharePrice = sellSharePrice;
        totalLotPrice = sellSharePrice * purchaseQuan;
        TotalAllCharges();
    }

    private void TotalAllCharges() {
        double Brokerage = totalLotPrice * (2.5 / 100);
        if (Brokerage > 20) {
            Brokerage = 20;   //20 or 2.5% whichever is lower
        }
        double SEBICharges = SebiCharges(totalLotPrice);
        double TransactionCharge = TransactionCharges(totalLotPrice);
        double STTCharge = STTCharges(totalLotPrice);
        GSTCharges(SEBICharges, TransactionCharge, Brokerage);
        double totalCharges = -(SEBICharges + TransactionCharge + STTCharge + gstCharge[0] + DPCharges + Brokerage);
        Log.e("Sanket", "SEBICharges" + SEBICharges + "TransactionCharge" + TransactionCharge + "STTCharge" + STTCharge + "GSTCharge" + gstCharge[0] + "totalCharges" + totalCharges);
        mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, totalCharges);
    }

    @Override
    public void GSTCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
        gstCharge[0] = (DPCharges + TransactionCharges + Brokerage) * ((double) 18 / 100);
    }
}
