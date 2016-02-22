package com.example.jlordi.stocktracker.models;

import java.util.List;

/**
 * Created by lordij on 2/5/2016.
 */
public class StockChart {
    private String Labels;
    private List<Double> Positions;
    private List<String> Dates;
    private List<Element> Elements;

    public String getLabel() {
        return this.Labels;
    }

    public List<Double> getPositions() {
        return Positions;
    }

    public List<String> getDates() {
        return this.Dates;
    }

    public Element getElements() {
        return this.Elements.get(0);
    }


    public class Element {
        private String Currency;
        private String TimeStamp;
        private String Symbol;
        private String Type;
        private DataSeries DataSeries;

        public String getCurrency() {
            return this.Currency;
        }

        public String getTimeStamp() {
            return this.TimeStamp;
        }

        public String getSymbol() {
            return this.Symbol;
        }

        public String getType() {
            return this.Type;
        }

        public DataSeries getDataSeries() {
            return this.DataSeries;
        }
    }

    public class DataSeries {
        public String Type;
        public Double Min;
        public Double Max;
        public String MinDate;
        public String MaxDate;
        public String Labels;
        public List<Double> Values;

    }


}


