package com.example.ipocalculation.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipocalculation.BrokerageCharges.AngelOneCharges;
import com.example.ipocalculation.BrokerageCharges.DhanCharges;
import com.example.ipocalculation.BrokerageCharges.FivePaisaCharges;
import com.example.ipocalculation.BrokerageCharges.FyersCharges;
import com.example.ipocalculation.BrokerageCharges.GrowwCharges;
import com.example.ipocalculation.BrokerageCharges.ProStocksCharges;
import com.example.ipocalculation.BrokerageCharges.UpStoxCharges;
import com.example.ipocalculation.BrokerageCharges.ZerodhaCharges;
import com.example.ipocalculation.CommonCharges;
import com.example.ipocalculation.Interfaces.SetMoreDetails;
import com.example.ipocalculation.IpoDetailsSetData;
import com.example.ipocalculation.LotSizeData;
import com.example.ipocalculation.R;
import com.example.ipocalculation.SqlDB;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalculationActivity extends AppCompatActivity implements SetMoreDetails {
    EditText edtPurchaseQuantity, edtPurchaseSharePrice, edtSellSharePrice, edtBrokerage;
    Button btnSubmit, btnMoreDetails, btnSaveHistory;
    TextView tvSellQuantityShare, tvProfit, tvTaxCal, tvFinalProfit, tvInvestedAmount, tvFinalAmount, tvRemainingQuantity, tvRemainingQuantityPrice, tvCompanyName;
    Spinner spnrAccount;
    RadioButton rdRetails, rdSHni, rdBHni, rdCustom;
    RadioGroup radioGroup;
    LinearLayout linearLayoutMoreDetails, linearLayoutBrok;
    CommonCharges commonCharges;
    LotSizeData lotSizeData = new LotSizeData();
    int quantity, price, purchaseQuan, purchaseSharePrice, singleLotPrice;
    SqlDB sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        setWidgets();
        setupSpinner();
        sdb = new SqlDB(getApplicationContext());

        btnSubmit.setOnClickListener(onClickBtnSubmit);
        btnMoreDetails.setOnClickListener(onClickBtnMoreDetails);
        btnSaveHistory.setOnClickListener(onClickBtnSaveHistory);

        Intent intent = getIntent();

        IpoDetailsSetData model = (IpoDetailsSetData) intent.getSerializableExtra("SerializeData");

        if (model != null) {
            edtPurchaseQuantity.setText(String.valueOf(model.getLotSize()));
            edtPurchaseSharePrice.setText(String.valueOf(model.getIssuePrice()));
            tvCompanyName.setText(model.getCompanyName());

            quantity = model.getLotSize();
            price = model.getIssuePrice();
            singleLotPrice = quantity * price;

            if (singleLotPrice > 100000) {
                rdSHni.setVisibility(View.GONE);
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rdRetails.isChecked()) {
                    edtPurchaseQuantity.setEnabled(false);
                    lotSizeData.setRetail(quantity);
                    edtPurchaseQuantity.setText(String.valueOf(lotSizeData.getRetail()));

                    purchaseQuan = quantity;
                    purchaseSharePrice = price;

                } else if (rdSHni.isChecked()) {
                    edtPurchaseQuantity.setEnabled(false);
                    if (model != null) {
                        int sHniQuantity = quantity * model.getSHniLot();
                        lotSizeData.setS_hni(sHniQuantity);
                        edtPurchaseQuantity.setText(String.valueOf(lotSizeData.getS_hni()));

                        purchaseQuan = sHniQuantity;
                    }
                } else if (rdBHni.isChecked()) {
                    edtPurchaseQuantity.setEnabled(false);
                    if (model != null) {
                        int bHniQuantity = quantity * model.getBHniLot();

                        if (singleLotPrice > 100000) {
                            bHniQuantity = quantity * 2;
                        }
                        lotSizeData.setB_hni(bHniQuantity);
                        edtPurchaseQuantity.setText(String.valueOf(lotSizeData.getB_hni()));
                        purchaseQuan = bHniQuantity;
                    }
                } else if (rdCustom.isChecked()) {
                    edtPurchaseQuantity.setEnabled(true);
                    edtPurchaseQuantity.setText(" ");
                }
            }
        });
    }

    View.OnClickListener onClickBtnSaveHistory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(CalculationActivity.this);

            View dialogView = LayoutInflater.from(CalculationActivity.this).inflate(R.layout.popupsavehistory, null);
            TextView tvPrice = dialogView.findViewById(R.id.tvPurchasePrice);
            TextView tvDateTime = dialogView.findViewById(R.id.tvTime);
            EditText edtQuantity = dialogView.findViewById(R.id.edtSellQuantity);
            EditText edtPrice = dialogView.findViewById(R.id.edtSellPrice);

            tvPrice.setText(String.valueOf(price));
            edtQuantity.setText(tvSellQuantityShare.getText().toString());
            edtPrice.setText(edtSellSharePrice.getText().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String time = dateFormat.format(new Date());
            tvDateTime.setText(time);

            builder.setView(dialogView);
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String getEdtQuantityStr = edtQuantity.getText().toString().trim();
                    String getEdtPriceStr = edtPrice.getText().toString().trim();

                    if (getEdtQuantityStr.equals("")) {
                        Toast.makeText(CalculationActivity.this, "Enter Quantity ", Toast.LENGTH_SHORT).show();
                    } else if (getEdtPriceStr.equals("")) {
                        Toast.makeText(CalculationActivity.this, "Enter Price", Toast.LENGTH_SHORT).show();
                    } else {
                        int quantity = Integer.parseInt(getEdtQuantityStr);
                        int sellPrice = Integer.parseInt(getEdtPriceStr);
                        int purPrice = Integer.parseInt(tvPrice.getText().toString().trim());
                        String time = tvDateTime.getText().toString().trim();

                        sdb.SaveData(purPrice, sellPrice, quantity, time);
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    };
    View.OnClickListener onClickBtnMoreDetails = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            if (btnMoreDetails.getText() == "Hide Calculation") {
                linearLayoutMoreDetails.setVisibility(View.GONE);
                btnMoreDetails.setText("More Calculation");
            } else {
                linearLayoutMoreDetails.setVisibility(View.VISIBLE);
                btnMoreDetails.setText("Hide Calculation");
            }
        }
    };

    View.OnClickListener onClickBtnSubmit = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            setSpinnerItemChange();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtBrokerage.getWindowToken(), 0);
        }
    };

    private void setupSpinner() {
        String[] accountArray = getResources().getStringArray(R.array.Account_Array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accountArray);
        spnrAccount.setAdapter(adapter);
        spnrAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spnrAccount.getSelectedItemPosition() > 0) {
                    setSpinnerItemChange();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtSellSharePrice.getWindowToken(), 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerItemChange() {
        if (edtPurchaseQuantity.getText().toString().equals("")) {
            edtPurchaseQuantity.setError("Required field");
        } else if (edtPurchaseSharePrice.getText().toString().equals("")) {
            edtPurchaseSharePrice.setError("Required field");
        } else if (edtSellSharePrice.getText().toString().equals("")) {
            edtSellSharePrice.setError("Required field");
        } else {
            int purchaseQuan = Integer.parseInt(edtPurchaseQuantity.getText().toString().trim());
            int purchaseSharePrice = Integer.parseInt(edtPurchaseSharePrice.getText().toString().trim());
            int sellSharePrice = Integer.parseInt(edtSellSharePrice.getText().toString().trim());

            int position = spnrAccount.getSelectedItemPosition();
            switch (position) {
                case 0:
                    linearLayoutBrok.setVisibility(View.VISIBLE);

                    if (edtBrokerage.getText().toString().equals("")) {
                        edtBrokerage.setError("Required field");
                        return;
                    }
                    double brokerage = Double.parseDouble(edtBrokerage.getText().toString());
                    double brokerageCharges = -(sellSharePrice * purchaseQuan) * (brokerage / 100);
                    commonCharges.mainCalculation(purchaseQuan, purchaseSharePrice, sellSharePrice, brokerageCharges);
                    break;
                case 1:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new ZerodhaCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 2:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new UpStoxCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 3:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new GrowwCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 4:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new AngelOneCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 5:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new DhanCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 6:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new FyersCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 7:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new ProStocksCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
                case 8:
                    linearLayoutBrok.setVisibility(View.GONE);
                    new FivePaisaCharges(purchaseQuan, purchaseSharePrice, sellSharePrice, CalculationActivity.this);
                    break;
            }
        }
    }

    private void setWidgets() {
        edtPurchaseQuantity = findViewById(R.id.edtPurchaseQuantity);
        edtPurchaseSharePrice = findViewById(R.id.edtPurchaseSharePrice);
        edtSellSharePrice = findViewById(R.id.edtCurrentSharePrice);
        edtBrokerage = findViewById(R.id.edtBrokerage);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnMoreDetails = findViewById(R.id.btnMoreDetails);
        btnSaveHistory = findViewById(R.id.btnSaveHistory);

        tvSellQuantityShare = findViewById(R.id.tvSellQuantityShare);
        tvProfit = findViewById(R.id.tvProfit);
        tvTaxCal = findViewById(R.id.tvTaxCal);
        tvFinalProfit = findViewById(R.id.tvFinalProfit);
        tvInvestedAmount = findViewById(R.id.tvInvestedAmount);
        tvFinalAmount = findViewById(R.id.tvFinalAmount);
        tvRemainingQuantity = findViewById(R.id.tvRemainingQuantity);
        tvRemainingQuantityPrice = findViewById(R.id.tvRemainingQuantityPrice);
        tvCompanyName = findViewById(R.id.tvCompanyName);

        linearLayoutMoreDetails = findViewById(R.id.linearMoreDetails);
        linearLayoutBrok = findViewById(R.id.linearBrokerage);

        spnrAccount = findViewById(R.id.spnrAccount);

        rdRetails = findViewById(R.id.retailMinLotSize);
        rdSHni = findViewById(R.id.s_HNIMinLotSize);
        rdBHni = findViewById(R.id.b_HNILotSize);
        rdCustom = findViewById(R.id.radio_custom);

        radioGroup = findViewById(R.id.rdGroup);


        edtPurchaseQuantity.setEnabled(false);
        rdRetails.setChecked(true);

        commonCharges = new CommonCharges(this) {
            @Override
            public void GstCharges(double SebiCharges, double TransactionCharges, double Brokerage) {
                // MainCalculation function direct call in 0 position for Manual Brokerage put by user
                // if not called so null object reference get
            }
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setMoreDetails(int purchaseQuan, int purchaseSharePrice, int sellSharePrice, int brokerageCharges, int totalSellQuan) {

        tvSellQuantityShare.setText(" " + totalSellQuan);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        int totalLotPrice = totalSellQuan * purchaseSharePrice;
        Log.i("IPO", "SetMoreDetailsTotalLotPrice ::" + totalLotPrice);
        String lotPriceDF = decimalFormat.format(totalLotPrice);

        int profit = totalSellQuan * (sellSharePrice - purchaseSharePrice);
        Log.i("IPO", "SetMoreDetailsProfit ::" + profit);
        String profitDF = decimalFormat.format(profit);
        tvProfit.setText(profitDF);

        decimalFormat.format(brokerageCharges);

        profit = profit + brokerageCharges;
        Log.e("IPO", "profit ::" + profit);
        double tax = profit * 0.15;
        String taxDF = decimalFormat.format(tax);
        Log.e("IPO", "totalTax ::" + tax);
        double finalProfit = profit - tax;
        String finalProfitDF = decimalFormat.format(finalProfit);
        Log.e("IPO", "finalProfit ::" + finalProfit);
        double totalProfit = finalProfit + totalLotPrice;
        String totalProfitDF = decimalFormat.format(totalProfit);
        Log.e("IPO", "totalProfit ::" + totalProfit);

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