package com.example.ipocalculation.BrokerageCharges;

import android.util.Log;

import com.example.ipocalculation.CommonCharges;
import com.example.ipocalculation.Interfaces.SetMoreDetails;

public class GrowwCharges extends CommonCharges {

    public GrowwCharges(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, SetMoreDetails callBack) {
        super(callBack);
        this.purchaseQuan = purchaseQuan;
        this.purchaseSharePrice = purchaseSharePrice;
        this.sellSharePrice = sellSharePrice;
        totalLotPrice = sellSharePrice * purchaseQuan;
        TotalAllCharges();
    }

    private void TotalAllCharges() {
        double brokerage = totalLotPrice * (0.05 / 100);
        if (brokerage > 20) {
            brokerage = 20;      //20 or O.05% whichever is lower
        }
        Log.e("IPO", "growwBrokerage" + brokerage);

        double SebiCharge = SebiCharges(totalLotPrice);
        double TransactionCharge = TransactionCharges(totalLotPrice);
        double SttCharge = SttCharges(totalLotPrice);
        GstCharges(SebiCharge, TransactionCharge, brokerage);
        double totalCharges = -(SebiCharge + TransactionCharge + SttCharge + gstCharge[0] + brokerage);
        Log.e("IPO", "SebiCharges :" + SebiCharge + "TransactionCharge :" + TransactionCharge + "STTCharge :" + SttCharge + "GSTCharge :" + gstCharge[0] + "totalCharges :" + totalCharges);
        mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, totalCharges);
    }

    @Override
    public void GstCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
        gstCharge[0] = (SebiCharges + TransactionCharges + Brokerage) * ((double) 18 / 100);
    }
}
