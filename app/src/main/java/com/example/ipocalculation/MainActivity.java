package com.example.ipocalculation;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ipocalculation.BrokerageCharges.AngelOneCharges;
import com.example.ipocalculation.BrokerageCharges.DhanCharges;
import com.example.ipocalculation.BrokerageCharges.FivePaisaCharges;
import com.example.ipocalculation.BrokerageCharges.FyersCharges;
import com.example.ipocalculation.BrokerageCharges.GrowwCharges;
import com.example.ipocalculation.BrokerageCharges.ProStocksCharges;
import com.example.ipocalculation.BrokerageCharges.UpStoxCharges;
import com.example.ipocalculation.BrokerageCharges.ZerodhaCharges;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SetMoreDetails {

    EditText edtPurchaseQuantity, edtPurchaseSharePrice, edtSellSharePrice, edtBrokerage;
    Button btnSubmit, btnMoreDetails;
    TextView tvSellQuantityShare, tvProfit, tvTaxCal, tvFinalProfit, tvInvestedAmount, tvFinalAmount, tvRemainingQuantity, tvRemainingQuantityPrice;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    Spinner spnrAccount;
    CommonCharges commonCharges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidgets();

        btnSubmit.setOnClickListener(onClickBtnSubmit);
        btnMoreDetails.setOnClickListener(onClickBtnMoreDetails);

        setupSpinner();
    }

    private void setupSpinner() {
        String[] accountArray = getResources().getStringArray(R.array.Account_Array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accountArray);
        spnrAccount.setAdapter(adapter);
        spnrAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelect(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void onItemSelect(int position) {
        if (edtPurchaseQuantity.length() == 0 || edtPurchaseSharePrice.length() == 0 || edtSellSharePrice.length() == 0) {
            return;
        } else {
            int purchaseQuan = Integer.parseInt(edtPurchaseQuantity.getText().toString());
            int purchaseSharePrice = Integer.parseInt(edtPurchaseSharePrice.getText().toString());
            int sellSharePrice = Integer.parseInt(edtSellSharePrice.getText().toString());

            switch (position) {
                case 0:
                    linearLayout.setVisibility(View.VISIBLE);
                    if (edtBrokerage.getText().toString().equals("")) {
                        edtBrokerage.setError("Required field");
                        return;
                    }
                    double brokerage = Double.parseDouble(edtBrokerage.getText().toString());
                    double brokerageCharges = -(sellSharePrice * purchaseQuan) * (brokerage / 100);
                    commonCharges.mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, brokerageCharges);
                    break;
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    new ZerodhaCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    new UpStoxCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 3:
                    linearLayout.setVisibility(View.GONE);
                    new GrowwCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 4:
                    linearLayout.setVisibility(View.GONE);
                    new AngelOneCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 5:
                    linearLayout.setVisibility(View.GONE);
                    new DhanCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 6:
                    linearLayout.setVisibility(View.GONE);
                    new FyersCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 7:
                    linearLayout.setVisibility(View.GONE);
                    new ProStocksCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
                case 8:
                    linearLayout.setVisibility(View.GONE);
                    new FivePaisaCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, this);
                    break;
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtSellSharePrice.getWindowToken(), 0);
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

        @Override
        public void onClick(View v) {

            if (edtPurchaseQuantity.getText().toString().equals("")) {
                edtPurchaseQuantity.setError("Required field");
                return;
            }
            if (edtPurchaseSharePrice.getText().toString().equals("")) {
                edtPurchaseSharePrice.setError("Required field");
                return;
            }
            if (edtSellSharePrice.getText().toString().equals("")) {
                edtSellSharePrice.setError("Required field");
                return;
            }
            int purchaseQuan = Integer.parseInt(edtPurchaseQuantity.getText().toString());
            int purchaseSharePrice = Integer.parseInt(edtPurchaseSharePrice.getText().toString());
            int sellSharePrice = Integer.parseInt(edtSellSharePrice.getText().toString());

            int position = spnrAccount.getSelectedItemPosition();
            switch (position) {
                case 0:
                    linearLayout.setVisibility(View.VISIBLE);
                    if (edtBrokerage.getText().toString().equals("")) {
                        edtBrokerage.setError("Required field");
                        return;
                    }
                    double brokerage = Double.parseDouble(edtBrokerage.getText().toString());
                    double brokerageCharges = -(sellSharePrice * purchaseQuan) * (brokerage / 100);
                    commonCharges.mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, brokerageCharges);
                    break;
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    new ZerodhaCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    new UpStoxCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 3:
                    linearLayout.setVisibility(View.GONE);
                    new GrowwCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 4:
                    linearLayout.setVisibility(View.GONE);
                    new AngelOneCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 5:
                    linearLayout.setVisibility(View.GONE);
                    new DhanCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 6:
                    linearLayout.setVisibility(View.GONE);
                    new FyersCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 7:
                    linearLayout.setVisibility(View.GONE);
                    new ProStocksCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
                case 8:
                    linearLayout.setVisibility(View.GONE);
                    new FivePaisaCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, MainActivity.this);
                    break;
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtBrokerage.getWindowToken(), 0);
        }
    };

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
        linearLayout = findViewById(R.id.ll4);
        spnrAccount = findViewById(R.id.spnrAccount);

        commonCharges = new CommonCharges(this) {
            @Override
            public void GSTCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
                //
            }
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setMoreDetails(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, int brokerageCharges, int totalSellQuan) {

        tvSellQuantityShare.setText(" " + totalSellQuan);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        int totalLotPrice = totalSellQuan * purchaseSharePrice;
        Log.i("Sanket", "SetMoreDetailsTotalLotPrice" + totalLotPrice);
        String lotPriceDF = decimalFormat.format(totalLotPrice);

        int profit = totalSellQuan * (sellSharePrice - purchaseSharePrice);
        Log.i("Sanket", "SetMoreDetailsProfit" + profit);
        String profitDF = decimalFormat.format(profit);
        tvProfit.setText(profitDF);

        decimalFormat.format(brokerageCharges);

        profit = profit + brokerageCharges;
        Log.e("Sanket", "profit" + profit);
        double tax = profit * 0.15;
        String taxDF = decimalFormat.format(tax);
        Log.e("Sanket", "totalTax" + tax);
        double finalProfit = profit - tax;
        String finalProfitDF = decimalFormat.format(finalProfit);
        Log.e("Sanket", "finalProfit" + finalProfit);
        double totalProfit = finalProfit + totalLotPrice;
        String totalProfitDF = decimalFormat.format(totalProfit);
        Log.e("Sanket", "totalProfit" + totalProfit);

        int remainingQuan = purchaseQuan - totalSellQuan;
        String remainingQuanDF = decimalFormat.format(remainingQuan);
        int remainingQuanValue = remainingQuan * sellSharePrice;
        String remainingQuanValueDF = decimalFormat.format(remainingQuanValue);

        tvTaxCal.setText(taxDF);
        tvFinalProfit.setText(finalProfitDF);
        tvInvestedAmount.setText(lotPriceDF);
        tvFinalAmount.setText(totalProfitDF);
        tvRemainingQuantity.setText(remainingQuanDF);
        tvRemainingQuantityPrice.setText(remainingQuanValueDF);
    }
}