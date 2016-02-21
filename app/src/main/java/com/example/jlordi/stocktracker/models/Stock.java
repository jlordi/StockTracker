package com.example.jlordi.stocktracker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jlordi on 1/30/2016.
 */
public class Stock implements Serializable {
        @SerializedName("Symbol")
        private String Symbol;

        @SerializedName("Name")
        private String Name;

        @SerializedName("Exchange")
        private String Exchange;

        public Stock(String symbol, String name, String exchange) {
           this.Symbol = symbol;
           this.Name = name;
           this.Exchange = exchange;
        }

        public void setSymbol(String symbol) {
                this.Symbol = symbol;
        }

        public String getSymbol() {
                return this.Symbol;
        }

        public void setName(String name) {
                this.Name = name;
        }

        public String getName() {
                return this.Name;
        }

        public void setExchange(String exchange) {
                this.Exchange = exchange;
        }

        public String getexchange() {
                return this.Exchange;
        }

        @Override
        public String toString() {
                return(this.Name);
        }

}
