package com.example.ipocalculation;


import java.io.Serializable;

public class IpoDetailsSetData implements Serializable {

    String companyName, openDate, closeDate, listingDate, exchange;
    int issuePrice,lotSize,SHniLot,BHniLot;

    public IpoDetailsSetData(String companyName, String openDate, String closeDate, String listingDate, int lotSize, int issuePrice, String exchange,int SHniLot,int BHniLot) {
        this.companyName = companyName;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.listingDate = listingDate;
        this.issuePrice = issuePrice;
        this.lotSize = lotSize;
        this.exchange = exchange;
        this.SHniLot = SHniLot;
        this.BHniLot = BHniLot;
    }

    public int getSHniLot() {
        return SHniLot;
    }

    public int getBHniLot() {
        return BHniLot;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getListingDate() {
        return listingDate;
    }

    public int getIssuePrice() {
        return issuePrice;
    }

    public int getLotSize() {
        return lotSize;
    }

    public String getExchange() {
        return exchange;
    }
}
