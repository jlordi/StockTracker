package com.example.jlordi.stocktracker.models;

import com.fasterxml.jackson.core.io.SerializedString;
import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;

/**
 * Created by lordij on 2/4/2016.
 */
public class StockQuote {
    private String Status;
    private String Symbol;
    private String Name;
    private double LastPrice;
    private double Change;
    private double ChangePercent;
    private String TimeStamp;
    private String MSDate;
    private double MarketCap;
    private double Volume;
    private double ChangeYTD;
    private double High;
    private double Low;
    private double Open;

    public StockQuote(String symbol, String name) {
        this.Symbol = symbol;
        this.Name = name;
    }


    public void setStatus(String status) {
        this.Status = status;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setSymbol(String symbol) {
        this.Symbol = symbol;
    }

    public String getSymbol() {
        return this.Symbol;
    }

    public void setName(String name) {this.Name = name; }

    public String getName() {return this.Name;}

    public void setLastPrice(double lastPrice) {this.LastPrice = lastPrice; }

    public double getLastPrice() {return this.LastPrice;}

    public String getFormattedLastPrice() {
        return formatCurrency(this.getLastPrice());
    }

    public void setChange(double Change) {this.Change = Change; }

    public double getChange() {return this.Change;}

    public String getFormattedChange() {
        return formatCurrency(this.Change);
    }

    public void setChangePercent(double ChangePercent) {this.ChangePercent = ChangePercent; }

    public double getChangePercent() {return this.ChangePercent;}


    @Override
    public String toString() {
        return(this.Name);
    }

    private String formatCurrency(double Amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(Amount);
    }
}
