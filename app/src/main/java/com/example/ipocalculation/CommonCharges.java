package com.example.ipocalculation;

import android.util.Log;

import java.text.DecimalFormat;

public abstract class CommonCharges {

    double STTCharge = 0.1;
    double NSECharge = 0.00322;
    double SEBICharge = 10;
    SetMoreDetails callBackInterface;

    public CommonCharges(SetMoreDetails callBack) {
        this.callBackInterface = callBack;
    }

    public double SebiCharges(int totalLotPrice) {
        Log.i("Sanket", "totalLotPrice" + totalLotPrice);
        return (totalLotPrice * SEBICharge) / 10000000;
    }

    public double STTCharges(int totalLotPrice) {
        return (totalLotPrice * STTCharge) / 100;
    }

    public double TransactionCharges(int totalLotPrice) {
        return totalLotPrice * (NSECharge / 100);
    }

    public abstract void GSTCharges(double SebiCharges, double TransactionCharges, double Brokerage);

    public void mainCalculation(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, double brokerageCharges) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        int totalLotPrice = purchaseQuan * purchaseSharePrice;
        decimalFormat.format(totalLotPrice);

        Log.i("Sanket", "CustomBrokerage" + brokerageCharges);
        decimalFormat.format(brokerageCharges);
        Log.e("Sanket", "totalCharges" + brokerageCharges);

        int profit = purchaseQuan * (sellSharePrice - purchaseSharePrice);
        Log.e("Sanket", "profit" + profit);

        profit = (int) (profit + brokerageCharges);
        decimalFormat.format(profit);
        Log.e("Sanket", "profit" + profit);

        double tax = profit * 0.15;
        decimalFormat.format(tax);
        Log.e("Sanket", "totalTax" + tax);

        double finalProfit = profit - tax;
        decimalFormat.format(finalProfit);
        Log.e("Sanket", "finalProfit" + finalProfit);

        double totalProfit = finalProfit + totalLotPrice;
        decimalFormat.format(totalProfit);
        Log.e("Sanket", "totalProfit" + totalProfit);

        int totalSellQuan = (int) (purchaseQuan * (totalLotPrice / totalProfit) + 1);
        callBackInterface.setMoreDetails(purchaseQuan, purchaseSharePrice, sellSharePrice, (int) brokerageCharges, totalSellQuan);
    }
}