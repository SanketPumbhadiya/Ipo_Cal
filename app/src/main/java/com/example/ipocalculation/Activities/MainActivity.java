package com.example.ipocalculation.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ipocalculation.Adapter.RecyclerItemAdapter;
import com.example.ipocalculation.IpoDetailsSetData;
import com.example.ipocalculation.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnMainBoardIpoDetails, btnSmeIpoDetails, btnAllIpo;
    LinearLayout linearLayoutPB;
    RecyclerView rcvIpoDetails;
    FirebaseFirestore firestoreDB;
    ArrayList<IpoDetailsSetData> ipoList;
    RecyclerItemAdapter listAdapter;
    LayoutInflater inflater;
    IpoDetailsSetData model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidgets();

        btnMainBoardIpoDetails.setOnClickListener(onClickBtnMainBoardIpoDetails);
        btnSmeIpoDetails.setOnClickListener(onClickBtnSmeIpoDetails);
        btnAllIpo.setOnClickListener(onClickBtnAllIpo);

        rcvIpoDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    View.OnClickListener onClickBtnAllIpo = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            if (btnAllIpo.getText().equals("All Ipo")) {
                getAllIpoDataFromFireStore();
                rcvIpoDetails.setVisibility(View.VISIBLE);
                btnAllIpo.setText("Hide All Ipo Details");
            } else if (btnAllIpo.getText().equals("Hide All Ipo Details")) {
                rcvIpoDetails.setVisibility(View.GONE);
                btnAllIpo.setText("All Ipo");
            }
        }
    };
    View.OnClickListener onClickBtnSmeIpoDetails = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (btnSmeIpoDetails.getText().equals("Sme Ipo")) {
                getSmeDataFromFireStore();
                rcvIpoDetails.setVisibility(View.VISIBLE);
                btnSmeIpoDetails.setText("Hide Ipo Details");
            } else if (btnSmeIpoDetails.getText().equals("Hide Ipo Details")) {
                rcvIpoDetails.setVisibility(View.GONE);
                btnSmeIpoDetails.setText("Sme Ipo");
                if (btnMainBoardIpoDetails.getText().equals("Hide Ipo Details")) {
                    getMainBoardDataFromFireStore();
                    rcvIpoDetails.setVisibility(View.VISIBLE);
                }
                if (btnAllIpo.getText().equals("Hide All Ipo Details")) {
                    getAllIpoDataFromFireStore();
                    rcvIpoDetails.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    View.OnClickListener onClickBtnMainBoardIpoDetails = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (btnMainBoardIpoDetails.getText().equals("Main Board Ipo")) {
                getMainBoardDataFromFireStore();
                rcvIpoDetails.setVisibility(View.VISIBLE);
                btnMainBoardIpoDetails.setText("Hide Ipo Details");
            } else if (btnMainBoardIpoDetails.getText().equals("Hide Ipo Details")) {
                rcvIpoDetails.setVisibility(View.GONE);
                btnMainBoardIpoDetails.setText("Main Board Ipo");
                if (btnSmeIpoDetails.getText().equals("Hide Ipo Details")) {
                    getSmeDataFromFireStore();
                    rcvIpoDetails.setVisibility(View.VISIBLE);
                }
                if (btnAllIpo.getText().equals("Hide All Ipo Details")) {
                    getAllIpoDataFromFireStore();
                    rcvIpoDetails.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void getAllIpoDataFromFireStore() {
        linearLayoutPB.setVisibility(View.VISIBLE);
        CollectionReference collectionReference = firestoreDB.collection("All Ipo");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.e("Sanket", "SmeDocumentList : " + queryDocumentSnapshots.getDocuments());

                        ipoList.clear();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                            List<Map<String, Object>> ipoDetailsList = (List<Map<String, Object>>) documentSnapshot.get("AllIpoDetails");
                            Log.e("Sanket", "Sme Document ID: " + documentSnapshot.getId());
                            Log.e("Sanket", "Sme Document Data: " + documentSnapshot.getData());
                            Log.e("Sanket", "Sme Document ipoDetailsList: " + ipoDetailsList);

                            getMapDataOfIpo(ipoDetailsList);

                        }
                        Log.e("Sanket", "Total items in ipoList: " + ipoList.size());
                        recyclerItemOnClickListener();

                    }
                }).

                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "OnFailureGetData" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMapDataOfIpo(List<Map<String, Object>> ipoDetailsList) {
        if (ipoDetailsList != null) {
            for (Map<String, Object> ipoDetailsMap : ipoDetailsList) {

                Log.e("Sanket", "MapDetails : " + ipoDetailsMap);
                if (ipoDetailsMap != null) {

                    String issuerCompany = (String) ipoDetailsMap.get("Issuer Company");
                    String openDate = (String) ipoDetailsMap.get("Open Date");
                    String closeDate = (String) ipoDetailsMap.get("Close Date");
                    String listingDate = (String) ipoDetailsMap.get("Listing Date");
                    String exchange = (String) ipoDetailsMap.get("Exchange");
                    String lotSizeStr = ipoDetailsMap.get("Lot Size").toString();
                    String issuePriceStr = ipoDetailsMap.get("Issue Price (Rs)").toString();
                    String s_hniLotStr = ipoDetailsMap.get("SHniLot").toString();
                    String b_hniLotStr = ipoDetailsMap.get("BHniLot").toString();

                    int issuePrice = 0, lotSize = 0, s_hniLot = 0, b_hniLot = 0;
                    if (issuePriceStr.contains("to")) {
                        String[] splitIssuePrice = issuePriceStr.split("to");
                        Log.e("Sanket", "Spliit" + Arrays.toString(splitIssuePrice));
                        try {
                            issuePrice = (int) Double.parseDouble(splitIssuePrice[1].trim());
                        } catch (NumberFormatException e) {
                            Log.e("Sanket", "Error parsing single issue price: " + e.getMessage());
                        }
                    } else {
                        try {
                            issuePrice = (int) Double.parseDouble(issuePriceStr.trim());
                        } catch (NumberFormatException e) {
                            Log.e("Sanket", "Error parsing single issue price: " + e.getMessage());
                        }
                    }

                    if (!lotSizeStr.equals("") || !s_hniLotStr.equals("") || !b_hniLotStr.equals("")) {
                        try {
                            lotSize = Integer.parseInt(lotSizeStr.trim());
                            s_hniLot = Integer.parseInt(s_hniLotStr.trim());
                            b_hniLot = Integer.parseInt(b_hniLotStr.trim());
                        } catch (NumberFormatException e) {
                            Log.e("Sanket", "Error parsing single lot size: " + e.getMessage());
                        }
                    }

                    model = new IpoDetailsSetData(issuerCompany, openDate, closeDate, listingDate, lotSize, issuePrice, exchange, s_hniLot, b_hniLot);
                    ipoList.add(model);
                }
            }
        }
    }

    private void recyclerItemOnClickListener() {
        if (listAdapter == null) {
            listAdapter = new RecyclerItemAdapter(ipoList, MainActivity.this, inflater);
            linearLayoutPB.setVisibility(View.GONE);
            rcvIpoDetails.setAdapter(listAdapter);
            listAdapter.setOnClickListener(new RecyclerItemAdapter.OnClickListener() {
                @Override
                public void onClick(int position, IpoDetailsSetData model) {
                    Intent intent = new Intent(MainActivity.this, CalculationActivity.class);
                    intent.putExtra("SerializeData", (Serializable) model);
                    startActivity(intent);
                }
            });

        } else {
            listAdapter.notifyDataSetChanged();
            linearLayoutPB.setVisibility(View.GONE);
        }
        Log.e("Sanket", "IpoList : " + ipoList);
    }

    private void getSmeDataFromFireStore() {
        linearLayoutPB.setVisibility(View.VISIBLE);
        CollectionReference collectionReference = firestoreDB.collection("IpoSmeDetails");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.e("Sanket", "SmeDocumentList : " + queryDocumentSnapshots.getDocuments());

                        ipoList.clear();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                            List<Map<String, Object>> ipoDetailsList = (List<Map<String, Object>>) documentSnapshot.get("IpoDetails");
                            Log.e("Sanket", "Sme Document ID: " + documentSnapshot.getId());
                            Log.e("Sanket", "Sme Document Data: " + documentSnapshot.getData());
                            Log.e("Sanket", "Sme Document ipoDetailsList: " + ipoDetailsList);
                            getMapDataOfIpo(ipoDetailsList);

                        }
                        Log.e("Sanket", "Total items in ipoList: " + ipoList.size());
                        recyclerItemOnClickListener();

                    }
                }).

                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "OnFailureGetData" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMainBoardDataFromFireStore() {
        linearLayoutPB.setVisibility(View.VISIBLE);
        CollectionReference collectionReference = firestoreDB.collection("IpoMainBoardDetails");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.e("Sanket", "DocumentList : " + queryDocumentSnapshots.getDocuments());
                ipoList.clear();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                    List<Map<String, Object>> ipoDetailsList = (List<Map<String, Object>>) documentSnapshot.get("IpoDeatails");
                    Log.e("Sanket", "Document ID: " + documentSnapshot.getId());
                    Log.e("Sanket", "Document Data: " + documentSnapshot.getData());
                    Log.e("Sanket", "Document ipoDetailsList: " + ipoDetailsList);

                    getMapDataOfIpo(ipoDetailsList);
                }
                Log.e("Sanket", "Total items in ipoList: " + ipoList.size());

                recyclerItemOnClickListener();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "OnFailureGetData" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setWidgets() {

        btnMainBoardIpoDetails = findViewById(R.id.btnMainBoardIpoDetails);
        btnSmeIpoDetails = findViewById(R.id.btnSmeIpoDetails);
        btnAllIpo = findViewById(R.id.btnAllIpo);

        linearLayoutPB = findViewById(R.id.linearLoadingPB);

        rcvIpoDetails = findViewById(R.id.rcvItem);

        ipoList = new ArrayList<>();

        firestoreDB = FirebaseFirestore.getInstance();

        inflater = getLayoutInflater();
    }
}