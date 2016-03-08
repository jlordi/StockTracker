package com.example.jlordi.stocktracker.Server;

/**
 * Created by lordij on 2/16/2016.
 */

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jlordi.stocktracker.StockTrackerMainActivity;
import com.example.jlordi.stocktracker.database.StockDatabaseAdapter;
import com.example.jlordi.stocktracker.models.Stock;
import com.example.jlordi.stocktracker.models.StockQuote;

import java.sql.SQLException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class StockTrackerAppInstance {
    private static final String TAG = StockTrackerAppInstance.class.getSimpleName();
    static StockTrackerAppInstance mStockTrackerAppInstance;
    private Context mctxt;
    private StockDatabaseAdapter dba;
    public static final String MARKITAPI_BASE_URL = "http://dev.markitondemand.com";

    public StockTrackerAppInstance getStockTrackerAppInstance(Context ctxt) {
        this.mctxt = ctxt;
        dba = StockDatabaseAdapter.getInstance(ctxt);
        dba.getInstance(ctxt);
        try {
            dba.OpenConnection();
        } catch (SQLException e) {
            Log.d(TAG, e.toString());
        }
        return mStockTrackerAppInstance;
    }


    public boolean AddStock(Stock Stock, Context ctxt) {
        boolean addStock = false;
        if (dba == null) {
            dba = StockDatabaseAdapter.getInstance(ctxt);
            dba.getInstance(ctxt);
            try {
                dba.OpenConnection();
            } catch (SQLException e) {
                Log.d(TAG, e.toString());
            }
        }
        addStock = dba.insertUniqueStockRecord(Stock);
        return addStock;
    }

    private void updatePrices(List<StockQuote> stockQuoteList, RecyclerView Recycler) {
        for (StockQuote stockQuote : stockQuoteList) {
            Log.d("JEL", "Name: " + stockQuote.getName());
            Log.d("JEL", "Symbol: " + stockQuote.getSymbol());
            requestStockQuote(stockQuote, Recycler);
        }

    }


    public void requestStockQuote(final StockQuote StockQuote, final RecyclerView Recycler) {

        Log.d("JEL", "Entering requestStockQuote");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit adapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MARKITAPI_BASE_URL)
                .client(httpClient.build())
                .build();

        MarkitService stockapi = adapter.create(MarkitService.class);

        Call<StockQuote> call = stockapi.getStockQuote(StockQuote.getSymbol());
        call.enqueue(new Callback<StockQuote>() {
            @Override
            public void onResponse(Response<StockQuote> response) {
                StockQuote quote = response.body();
                //Log.d("JEL", "Got Response New log message test!:  " + response.body().toString() );
                if (quote != null) {
                    Log.d("JEL", "Got Response!");
                    logResponse(quote);
                    StockQuote.setLastPrice(quote.getLastPrice());
                    StockQuote.setChange(quote.getChange());
                    StockQuote.setChangePercent(quote.getChangePercent());
                    Integer rowId = locateStockList(quote.getSymbol(), quote.getName(), Recycler);
                    Log.d("JEL", "Got Response - rowId: " + String.valueOf(rowId));
                    dba.updateItemRecord(rowId, quote);
                    Recycler.getAdapter().notifyItemChanged(rowId - 1);
                } else {
                    Log.d("JEL", "Quote is null");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("JEL", "ERROR: " + t.getMessage());
            }

            ;
        });
    }

    private Integer locateStockList(String symbol, String name, RecyclerView Recycler) {
        Integer stockListId = 0;
        List<StockQuote> stockQuoteList;

        if (dba == null) {
            dba = StockDatabaseAdapter.getInstance(this.mctxt);
        }
        stockQuoteList = dba.getAllStockQuotesList();

        int x = 1;
        for (StockQuote stockQuote : stockQuoteList) {
            requestStockQuote(stockQuote, Recycler);
            if (stockQuote.getName().contentEquals(name) && stockQuote.getSymbol().contentEquals(symbol)) {
                stockListId = stockQuote.getRowId();
                break;
            }
            x++;
        }

        return stockListId;
    }

    private void logResponse(StockQuote quote) {
        Log.d("JEL", "Entering LogResponse");
        Log.d("JEL", "Name: " + quote.getName());
        Log.d("JEL", "Symbol: " + quote.getSymbol());
        Log.d("JEL", "Status: " + quote.getStatus());
        Log.d("JEL", "Last Price: " + String.valueOf(quote.getLastPrice()));
        Log.d("JEL", "Change: " + String.valueOf(quote.getChange()));

    }
}
