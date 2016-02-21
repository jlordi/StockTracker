package com.example.jlordi.stocktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.jlordi.stocktracker.Server.MarkitService;
import com.example.jlordi.stocktracker.adapters.AddStockListAdapter;
import com.example.jlordi.stocktracker.models.Stock;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddStockActivity extends Activity {

    private Button SearchButton;
    private EditText searchText;
    private RecyclerView mRecyclerView;
    private AddStockListAdapter stockListAdapter;
    public static final String MARKITAPI_BASE_URL = "http://dev.markitondemand.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        searchText = (EditText) findViewById(R.id.searchText);

        SearchButton = (Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStockLookup(AddStockActivity.this, searchText.getText().toString());
            }
        });


        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void requestStockLookup(final Context mctxt, String searchString) {

        Log.d("JEL", "Entering requestStockLookup");
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

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

        Call<List<Stock>> call = stockapi.getStocks(searchString);
        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Response<List<Stock>> response) {
                List<Stock> stocks = response.body();
                for (Stock stock : stocks) {
                    Log.d("JEL", "Name: " + stock.getName());
                    Log.d("JEL", "Symbol: " + stock.getSymbol());
                    Log.d("JEL", "Exchange: " + stock.getexchange());
                }

                stockListAdapter = new AddStockListAdapter(mctxt, stocks);
                mRecyclerView.setAdapter(stockListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mctxt));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("JEL", "ERROR: " + t.getMessage());
            }

            ;
        });
    }
}
