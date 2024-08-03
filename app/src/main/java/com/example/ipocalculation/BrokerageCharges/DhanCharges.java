package com.example.ipocalculation.BrokerageCharges;

import android.util.Log;

import com.example.ipocalculation.CommonCharges;
import com.example.ipocalculation.Interfaces.SetMoreDetails;

public class DhanCharges extends CommonCharges {
    final double DP_CHARGES = 14.75;
    double IPFTCharges;

    public DhanCharges(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, SetMoreDetails callBack) {
        super(callBack);
        this.purchaseQuan = purchaseQuan;
        this.purchaseSharePrice = purchaseSharePrice;
        this.sellSharePrice = sellSharePrice;
        totalLotPrice = sellSharePrice * purchaseQuan;
        IPFTCharges = totalLotPrice * (0.0001 / 100);
        TotalAllCharges();
    }

    private void TotalAllCharges() {
        double SebiCharge = SebiCharges(totalLotPrice);
        double TransactionCharge = TransactionCharges(totalLotPrice);
        double SttCharge = SttCharges(totalLotPrice);
        GstCharges(SebiCharge, TransactionCharge, 0);
        double totalCharges = -(SebiCharge + TransactionCharge + SttCharge + gstCharge[0] + DP_CHARGES);
        Log.e("IPO", "SebiCharges :" + SebiCharge + "TransactionCharge :" + TransactionCharge + "STTCharge :" + SttCharge + "GSTCharge :" + gstCharge[0] + "totalCharges :" + totalCharges);
        mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, totalCharges);
    }

    @Override
    public void GstCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
        gstCharge[0] = (SebiCharges + TransactionCharges + IPFTCharges) * ((double) 18 / 100);
    }
}