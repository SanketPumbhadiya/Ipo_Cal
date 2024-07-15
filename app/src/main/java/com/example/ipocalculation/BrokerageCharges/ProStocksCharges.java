package com.example.ipocalculation.BrokerageCharges;

import android.util.Log;

import com.example.ipocalculation.CommonCharges;
import com.example.ipocalculation.MainActivity;
import com.example.ipocalculation.SetMoreDetails;

public class ProStocksCharges extends CommonCharges {
    int purchaseQuan, purchaseSharePrice, sellSharePrice;
    int totalLotPrice;

    private final double[] gstCharge = {0.0};

    public ProStocksCharges(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, SetMoreDetails callBack) {
        super(callBack);
        this.purchaseQuan = purchaseQuan;
        this.purchaseSharePrice = purchaseSharePrice;
        this.sellSharePrice = sellSharePrice;
        totalLotPrice = sellSharePrice * purchaseQuan;
        TotalAllCharges();
    }

    private void TotalAllCharges() {
        double SEBICharges = SebiCharges(totalLotPrice);
        double TransactionCharge = TransactionCharges(totalLotPrice);
        double STTCharge = STTCharges(totalLotPrice);
        GSTCharges(SEBICharges, TransactionCharge, 0);
        double totalCharges = -(SEBICharges + TransactionCharge + STTCharge + gstCharge[0]);
        Log.e("Sanket", "SEBICharges" + SEBICharges + "TransactionCharge" + TransactionCharge + "STTCharge" + STTCharge + "GSTCharge" + gstCharge[0] + "totalCharges" + totalCharges);
        mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, totalCharges);
    }

    @Override
    public void GSTCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
        gstCharge[0] = (SebiCharges + TransactionCharges + Brokerage) * ((double) 18 / 100);
    }
}
