package com.example.ipocalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtPurchaseQuantity, edtPurchaseSharePrice, edtSellSharePrice, edtBrokerage;
    Button btnSubmit, btnMoreDetails;
    TextView tvSellQuantityShare, tvProfit, tvTaxCal, tvFinalProfit, tvInvestedAmount, tvFinalAmount, tvRemainingQuantity, tvRemainingQuantityPrice;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWidgets();

        btnSubmit.setOnClickListener(onClickBtnSubmit);
        btnMoreDetails.setOnClickListener(onClickBtnMoreDetails);
    }

    View.OnClickListener onClickBtnMoreDetails = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            if (btnMoreDetails.getText() == "Hide Details") {
                relativeLayout.setVisibility(View.GONE);
                btnMoreDetails.setText("More Details");
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                btnMoreDetails.setText("Hide Details");
            }
        }
    };
    View.OnClickListener onClickBtnSubmit = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            int purchaseQuan = Integer.parseInt(edtPurchaseQuantity.getText().toString());
            int purchaseSharePrice = Integer.parseInt(edtPurchaseSharePrice.getText().toString());
            int sellSharePrice = Integer.parseInt(edtSellSharePrice.getText().toString());
            double brokerageCharges = Double.parseDouble(edtBrokerage.getText().toString());


            int totalLotPrice = purchaseQuan * purchaseSharePrice;
            double charges = ((sellSharePrice * purchaseQuan) * brokerageCharges);
            Log.e("Sanket", "totalCharges" + charges);
            int profit = purchaseQuan * (sellSharePrice - purchaseSharePrice);
            profit = (int) (profit - charges);
            Log.e("Sanket", "profit" + profit);
            double tax = profit * 0.15;

            Log.e("Sanket", "totalTax" + tax);
            double finalProfit = profit - tax;
            Log.e("Sanket", "finalProfit" + finalProfit);
            double totalProfit = finalProfit + totalLotPrice;
            Log.e("Sanket", "totalProfit" + totalProfit);

            int totalSellQuan = (int) (purchaseQuan * (totalLotPrice / totalProfit) + 1);

            tvSellQuantityShare.setText(" " + totalSellQuan);
            setMoreDetails(purchaseQuan, purchaseSharePrice, sellSharePrice, brokerageCharges, totalSellQuan);
        }
    };

    @SuppressLint("SetTextI18n")
    private void setMoreDetails(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, double brokerageCharges, int totalSellQuan) {

        int totalLotPrice = totalSellQuan * purchaseSharePrice;
        int profit = totalSellQuan * (sellSharePrice - purchaseSharePrice);

        tvProfit.setText(" " + profit);

        int charges = (int) ((totalSellQuan * sellSharePrice) * brokerageCharges);

        profit = (int) (profit - charges);

        Log.e("Sanket", "profit" + profit);
        double tax = profit * 0.15;

        Log.e("Sanket", "totalTax" + tax);
        double finalProfit = profit - tax;
        Log.e("Sanket", "finalProfit" + finalProfit);
        double totalProfit = finalProfit + totalLotPrice;
        Log.e("Sanket", "totalProfit" + totalProfit);

        int remainingQuan = purchaseQuan - totalSellQuan;
        int remainingQuanValue = remainingQuan * sellSharePrice;

        tvTaxCal.setText(" " + tax);
        tvFinalProfit.setText(" " + finalProfit);
        tvInvestedAmount.setText(" " + totalLotPrice);
        tvFinalAmount.setText(" " + totalProfit);
        tvRemainingQuantity.setText(" " + remainingQuan);
        tvRemainingQuantityPrice.setText(" " + remainingQuanValue);
    }

    private void setWidgets() {
        edtPurchaseQuantity = findViewById(R.id.edtPurchaseQuantity);
        edtPurchaseSharePrice = findViewById(R.id.edtPurchaseSharePrice);
        edtSellSharePrice = findViewById(R.id.edtCurrentSharePrice);
        edtBrokerage = findViewById(R.id.edtBrokerage);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnMoreDetails = findViewById(R.id.btnMoreDetails);

        tvSellQuantityShare = findViewById(R.id.tvSellQuantityShare);
        tvProfit = findViewById(R.id.tvProfit);
        tvTaxCal = findViewById(R.id.tvTaxCal);
        tvFinalProfit = findViewById(R.id.tvFinalProfit);
        tvInvestedAmount = findViewById(R.id.tvInvestedAmount);
        tvFinalAmount = findViewById(R.id.tvFinalAmount);
        tvRemainingQuantity = findViewById(R.id.tvRemainingQuantity);
        tvRemainingQuantityPrice = findViewById(R.id.tvRemainingQuantityPrice);

        relativeLayout = findViewById(R.id.relativeMain);

    }
}