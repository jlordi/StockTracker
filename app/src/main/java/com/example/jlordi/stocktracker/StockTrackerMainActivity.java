package com.example.jlordi.stocktracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jlordi.stocktracker.Server.MarkitService;
import com.example.jlordi.stocktracker.Server.StockTrackerAppInstance;
import com.example.jlordi.stocktracker.adapters.StockListAdapter;
import com.example.jlordi.stocktracker.database.StockDatabaseAdapter;
import com.example.jlordi.stocktracker.models.Stock;
import com.example.jlordi.stocktracker.models.StockChart;
import com.example.jlordi.stocktracker.models.StockChartRequest;
import com.example.jlordi.stocktracker.models.StockQuote;
import com.example.lordij.mystocktrackerapp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class StockTrackerMainActivity extends Activity {
    public static final String MARKITAPI_BASE_URL = "http://dev.markitondemand.com";
    private Button AddButton;
    private Button UpdateButton;
    private TextView TextView;
    private RecyclerView mRecyclerView;
    private StockListAdapter stockListAdapter;
    private List<StockQuote> stockQuoteList;
    private StockTrackerAppInstance appInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_tracker_main);

        //TextView = (TextView) findViewById(R.id.textView);

        AddButton = (Button) findViewById(R.id.AddButton);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddStockIntent = new Intent(StockTrackerMainActivity.this, AddStockActivity.class);
                startActivity(AddStockIntent);
            }
        });

        UpdateButton = (Button) findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrice();
            }
        });


        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setStockList(StockTrackerMainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_tracker_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestStockLookup() {

        Log.d("JEL", "Entering requestStockLookup");
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

        Call<List<Stock>> call = stockapi.getStocks("Apple");
        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Response<List<Stock>> response) {
                List<Stock> stocks = response.body();
                for (Stock stock : stocks) {
                    Log.d("JEL", "Name: " + stock.getName());
                    Log.d("JEL", "Symbol: " + stock.getSymbol());
                    Log.d("JEL", "Exchange: " + stock.getexchange());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("JEL", "ERROR: " + t.getMessage());
            }

            ;
        });
    }


    private void requestStockQuote(final StockQuote StockQuote) {

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
                Log.d("JEL", "Name: " + quote.getName());
                Log.d("JEL", "Symbol: " + quote.getSymbol());
                Log.d("JEL", "Status: " + quote.getStatus());
                Log.d("JEL", "Last Price: " + String.valueOf(quote.getLastPrice()));
                Log.d("JEL", "Change: " + String.valueOf(quote.getChange()));
                StockQuote.setLastPrice(quote.getLastPrice());
                StockQuote.setChange(quote.getChange());
                StockQuote.setChangePercent(quote.getChangePercent());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("JEL", "ERROR: " + t.getMessage());
            }

            ;
        });
    }

    private void requestStockChart() {

        Log.d("JEL", "Entering requestStockChart");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        Retrofit adapter = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MARKITAPI_BASE_URL)
                .client(httpClient.build())
                .build();

        MarkitService stockapi = adapter.create(MarkitService.class);

        ArrayList<String> pricetypes = new ArrayList<String>();
        pricetypes.add("c");
        //pricetypes.add("o");

        StockChartRequest.Elements Element = new StockChartRequest.Elements("AAPL", "Price", pricetypes);

        ArrayList<StockChartRequest.Elements> ElementList = new ArrayList<StockChartRequest.Elements>();
        ElementList.add(Element);

        StockChartRequest StockChartRequest = new StockChartRequest(false, "DAY", 365, ElementList);
        Call<StockChart> call = stockapi.getStockChart(StockChartRequest);

        call.enqueue(new Callback<StockChart>() {
            @Override
            public void onResponse(Response<StockChart> response) {
                StockChart chart = response.body();
                Log.d("JEL", "Name: " + chart.getLabel());
                Log.d("JEL", "Symbol: " + chart.getElements().getSymbol());
                //Log.d("JEL", "Exchange: " + chart.);
                //Log.d("JEL", "Last Price: " + String.valueOf(quote.getLastPrice()) );
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("JEL", "ERROR: " + t.getMessage());
            }

            ;
        });
    }


    private void setStockList(Context mctxt) {
        StockDatabaseAdapter dba = StockDatabaseAdapter.getInstance(mctxt);
        try {
            dba.OpenConnection();
        } catch (SQLException e) {

        }
        stockQuoteList = dba.getAllStockQuotesList();

        for (StockQuote stockQuote : stockQuoteList) {
            Log.d("JEL", "Name: " + stockQuote.getName());
            Log.d("JEL", "Symbol: " + stockQuote.getSymbol());
        }

        stockListAdapter = new StockListAdapter(mctxt, stockQuoteList);
        mRecyclerView.setAdapter(stockListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mctxt));

    }

    private void updatePrice() {

        for (StockQuote stockQuote : stockQuoteList) {
            Log.d("JEL", "Name: " + stockQuote.getName());
            Log.d("JEL", "Symbol: " + stockQuote.getSymbol());
            requestStockQuote(stockQuote);
        }

        updateStockList();

    }

    private void updateStockList() {
//        for ()
    }
}
