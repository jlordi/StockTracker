package com.example.jlordi.stocktracker.models;

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

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getSymbol() {
        return this.Symbol;
    }

    public void setSymbol(String symbol) {
        this.Symbol = symbol;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public double getLastPrice() {
        return this.LastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.LastPrice = lastPrice;
    }

    public String getFormattedLastPrice() {
        return formatCurrency(this.getLastPrice());
    }

    public double getChange() {
        return this.Change;
    }

    public void setChange(double Change) {
        this.Change = Change;
    }

    public String getFormattedChange() {
        return formatCurrency(this.Change);
    }

    public double getChangePercent() {
        return this.ChangePercent;
    }

    public void setChangePercent(double ChangePercent) {
        this.ChangePercent = ChangePercent;
    }

    @Override
    public String toString() {
        return (this.Name);
    }

    private String formatCurrency(double Amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(Amount);
    }
}
