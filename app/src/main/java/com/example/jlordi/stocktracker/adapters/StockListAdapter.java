package com.example.jlordi.stocktracker.adapters;

/**
 * Created by lordij on 2/14/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jlordi.stocktracker.models.StockQuote;
import com.example.lordij.mystocktrackerapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.CustomViewHolder> {
    private final LayoutInflater inflator;
    private List<StockQuote> StockQuoteList = Collections.emptyList();
    private Context mContext;

    public StockListAdapter(Context context, List<StockQuote> stockQuoteList) {
        inflator = LayoutInflater.from(context);
        this.StockQuoteList = stockQuoteList;
        this.mContext = context;
    }

    public void updateData(ArrayList<StockQuote> StockQuoteList) {
        StockQuoteList.clear();
        StockQuoteList.addAll(StockQuoteList);
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflator.inflate(R.layout.stockquotelayout_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        StockQuote stockQuoteItem = StockQuoteList.get(i);


        //Setting text view title
        customViewHolder.stockName.setText(stockQuoteItem.getName());
        customViewHolder.stockSymbol.setText(stockQuoteItem.getSymbol());
        customViewHolder.stockPrice.setText(stockQuoteItem.getFormattedLastPrice());
        customViewHolder.stockChange.setText(stockQuoteItem.getFormattedChange());

        if (stockQuoteItem.getChange() > 0) {
            customViewHolder.stockPriceLayout.setBackgroundColor(Color.parseColor("#fd1ac03b"));
//            customViewHolder.stockNameLayout.setBackgroundColor(Color.GREEN);
        }else if (stockQuoteItem.getChange() < 0) {
            customViewHolder.stockPriceLayout.setBackgroundColor(Color.parseColor("#e3f04949"));
//            customViewHolder.stockNameLayout.setBackgroundColor(Color.RED);
        } else {
            customViewHolder.stockPriceLayout.setBackgroundColor(Color.WHITE);
//            customViewHolder.stockNameLayout.setBackgroundColor(Color.WHITE);
        }

        Log.d("JEL", String.valueOf(stockQuoteItem.getChange()));
    }

    @Override
    public int getItemCount() {
        return (null != StockQuoteList ? StockQuoteList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stockName;
        TextView stockSymbol;
        TextView stockPrice;
        TextView stockChange;
        LinearLayout stockPriceLayout;
        LinearLayout stockNameLayout;

        public CustomViewHolder(View itemView) {
            super(itemView);
            stockName = (TextView) itemView.findViewById(R.id.StockName);
            stockSymbol = (TextView) itemView.findViewById(R.id.StockSymbol);
            stockPrice = (TextView) itemView.findViewById(R.id.StockPrice);
            stockChange = (TextView) itemView.findViewById(R.id.StockChange);
            stockPriceLayout = (LinearLayout) itemView.findViewById(R.id.StockPriceLayout);
            stockNameLayout = (LinearLayout) itemView.findViewById(R.id.StockNameLayout);


            stockName.setOnClickListener(this);
            stockSymbol.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Item Clicked: " + getPosition(), Toast.LENGTH_LONG).show();
        }

    }

}
