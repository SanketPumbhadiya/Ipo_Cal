package com.example.ipocalculation.BrokerageCharges;

import android.util.Log;

import com.example.ipocalculation.CommonCharges;
import com.example.ipocalculation.MainActivity;
import com.example.ipocalculation.SetMoreDetails;

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
        double Brokerage = totalLotPrice * (0.05 / 100);
        if (Brokerage > 20) {
            Brokerage = 20;      //20 or O.05% whichever is lower
        }
        Log.e("Sanket", "growwBrokerage" + Brokerage);

        double SEBICharges = SebiCharges(totalLotPrice);
        double TransactionCharge = TransactionCharges(totalLotPrice);
        double STTCharge = STTCharges(totalLotPrice);
        GSTCharges(SEBICharges, TransactionCharge, Brokerage);
        double totalCharges = -(SEBICharges + TransactionCharge + STTCharge + gstCharge[0] + Brokerage);
        Log.e("Sanket", "SEBICharges" + SEBICharges + "TransactionCharge" + TransactionCharge + "STTCharge" + STTCharge + "GSTCharge" + gstCharge[0] + "totalCharges" + totalCharges);
        mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, totalCharges);
    }

    @Override
    public void GSTCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
        gstCharge[0] = (SebiCharges + TransactionCharges + Brokerage) * ((double) 18 / 100);
    }
}
