package com.example.jlordi.stocktracker.Server;

/**
 * Created by lordij on 2/16/2016.
 */
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.example.jlordi.stocktracker.database.StockDatabaseAdapter;
import com.example.jlordi.stocktracker.models.Stock;

import java.sql.SQLException;


public class StockTrackerAppInstance extends MultiDexApplication {
    private StockDatabaseAdapter dba;
    static StockTrackerAppInstance mStockTrackerAppInstance;
    private static final String TAG = StockTrackerAppInstance.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        mStockTrackerAppInstance = this;
    }

    public static StockTrackerAppInstance getStockTrackerAppInstance() {
        return mStockTrackerAppInstance;
    }

    public boolean AddStock(Stock Stock) {
        boolean addStock = false;
        dba = StockDatabaseAdapter.getInstance(StockTrackerAppInstance.this);
        try {
            dba.OpenConnection();
            addStock = dba.insertUniqueStockRecord(Stock);
        } catch (SQLException e) {
            Log.d(TAG, e.toString());
        }
        return addStock;
    }


}
