package com.example.jlordi.stocktracker.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lordij on 2/8/2016.
 */
public class StockChartRequest {
    public Boolean Normalized;
    //public String StartDate;
    //public String EndDate;
    //public int EndOffSetDays;
    public int NumberOfDays;
    public String DataPeriod;
    //public int DataInterval;
    //public String LabelPeriod;
    //public int LabelInterval;
    public List<Elements> Elements;

    public StockChartRequest(Boolean Norm, String dataPeriod, int numberOfDays, ArrayList<Elements> elements) {
        this.Normalized = Norm;
        //this.StartDate = StartDate;
        //this.EndDate = EndDate;
        //this.EndOffSetDays = endOffSetDays;
        this.NumberOfDays = numberOfDays;
        this.DataPeriod = dataPeriod;
        //this.DataInterval = 0;
        //this.LabelPeriod = "Day";
        //this.LabelInterval = 0;
        this.Elements = new ArrayList<Elements>();

        this.Elements = elements;

    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class Elements {
        public String Symbol;
        public String Type;
        public List<String> Params;

        public Elements(String symbol, String type, ArrayList<String> price) {
            this.Symbol = symbol;
            this.Type = type;
            this.Params = new ArrayList<String>();
            this.Params = price;
        }
    }

}

