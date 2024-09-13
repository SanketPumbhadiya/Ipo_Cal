package com.example.ipocalculation;

import android.util.Log;

import com.example.ipocalculation.Interfaces.SetMoreDetails;

public abstract class CommonCharges {

    public int purchaseQuan, purchaseSharePrice, sellSharePrice, totalLotPrice;
    public final double[] gstCharge = {0.0};
    double STTCharge = 0.1;
    double NSECharge = 0.00322;
    double SEBICharge = 10;
    SetMoreDetails callBackInterface;

    public CommonCharges(SetMoreDetails callBack) {
        this.callBackInterface = callBack;
    }

    public double SebiCharges(int totalLotPrice) {
        Log.i("IPO", "totalLotPrice" + totalLotPrice);
        return (totalLotPrice * SEBICharge) / 10000000;
    }

    public double SttCharges(int totalLotPrice) {
        return (totalLotPrice * STTCharge) / 100;
    }

    public double TransactionCharges(int totalLotPrice) {
        return totalLotPrice * (NSECharge / 100);
    }

    public abstract void GstCharges(double SebiCharges, double TransactionCharges, double Brokerage);

    public void mainCalculation(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, double brokerage) {

        int totalLotPrice = purchaseQuan * purchaseSharePrice;

        Log.e("IPO", "totalLotPrice ::" + totalLotPrice);
        Log.i("IPO", "CustomBrokerage ::" + brokerage);

        int profit = purchaseQuan * (sellSharePrice - purchaseSharePrice);
        Log.e("IPO", "profit ::" + profit);

        profit = (int) (profit + brokerage);
        Log.e("IPO", "profit ::" + profit);

        double tax = profit * 0.20;
        Log.e("IPO", "totalTax ::" + tax);

        double finalProfit = profit - tax;
        Log.e("IPO", "finalProfit ::" + finalProfit);

        double totalProfit = finalProfit + totalLotPrice;
        Log.e("IPO", "totalProfit ::" + totalProfit);

        int totalSellQuan = (int) (purchaseQuan * (totalLotPrice / totalProfit) + 1);
        callBackInterface.setMoreDetails(purchaseQuan, purchaseSharePrice, sellSharePrice, (int) brokerage, totalSellQuan);
    }
}