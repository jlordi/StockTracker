package com.example.jlordi.stocktracker.adapters;

/**
 * Created by lordij on 2/14/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.TextView;
        import android.widget.Toast;

import com.example.jlordi.stocktracker.R;
import com.example.jlordi.stocktracker.Server.StockTrackerAppInstance;
import com.example.jlordi.stocktracker.models.Stock;
import com.example.jlordi.stocktracker.database.StockDatabaseAdapter;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class AddStockListAdapter extends RecyclerView.Adapter<AddStockListAdapter.CustomViewHolder> {
    private List<Stock> StockList = Collections.emptyList();
    private Context mContext;
    private final LayoutInflater inflator;
    private StockTrackerAppInstance appInstance;


    public AddStockListAdapter(Context context, List<Stock> stockList ) {
        inflator = LayoutInflater.from(context);
        this.StockList = stockList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view=inflator.inflate(R.layout.stocklayout_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Stock stockItem = StockList.get(i);


        //Setting text view title
        customViewHolder.stockName.setText(stockItem.getName());
        customViewHolder.stockSymbol.setText(stockItem.getSymbol());
        customViewHolder.stockExchange.setText(stockItem.getexchange());
    }

    @Override
    public int getItemCount() {
        return (null != StockList ? StockList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stockName;
        TextView stockSymbol;
        TextView stockExchange;

        public CustomViewHolder(View itemView) {
            super(itemView);
            stockName = (TextView) itemView.findViewById(R.id.StockName);
            stockSymbol = (TextView) itemView.findViewById(R.id.StockSymbol);
            stockExchange = (TextView) itemView.findViewById(R.id.StockExchange);

            stockName.setOnClickListener(this);
            stockSymbol.setOnClickListener(this);
            stockExchange.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Item Clicked: " + getPosition(), Toast.LENGTH_LONG).show();

            Stock stock = new Stock(stockSymbol.getText().toString(), stockName.getText().toString(), stockExchange.getText().toString());
            appInstance = new StockTrackerAppInstance();
            if (appInstance.AddStock(stock)) {
                Toast.makeText(mContext, "Inserted Record", Toast.LENGTH_LONG ).show();
            } else {
                Toast.makeText(mContext, "Unable to insert record", Toast.LENGTH_LONG ).show();
            }
            //appInstance.AddStock(stock);
            //test = dba.insertStockRecord(stockName.getText().toString(), stockSymbol.getText().toString(), stockExchange.getText().toString());
            //Toast.makeText(mContext, "Insert Record Response: " + String.valueOf(test), Toast.LENGTH_LONG ).show();

        }

    }

}
