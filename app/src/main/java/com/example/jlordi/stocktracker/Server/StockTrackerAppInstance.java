package com.example.jlordi.stocktracker.Server;

/**
 * Created by lordij on 2/16/2016.
 */

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.example.jlordi.stocktracker.database.StockDatabaseAdapter;
import com.example.jlordi.stocktracker.models.Stock;

import java.sql.SQLException;


public class StockTrackerAppInstance {
    private static final String TAG = StockTrackerAppInstance.class.getSimpleName();
    static StockTrackerAppInstance mStockTrackerAppInstance;
    private StockDatabaseAdapter dba;

    public static StockTrackerAppInstance getStockTrackerAppInstance() {
        return mStockTrackerAppInstance;
    }


    public boolean AddStock(Stock Stock, Context ctxt) {
        boolean addStock = false;
        dba = StockDatabaseAdapter.getInstance(ctxt);
        try {
            dba.OpenConnection();
            addStock = dba.insertUniqueStockRecord(Stock);
        } catch (SQLException e) {
            Log.d(TAG, e.toString());
        }
        return addStock;
    }


}
