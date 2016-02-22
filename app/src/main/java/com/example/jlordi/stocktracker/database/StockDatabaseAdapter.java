package com.example.jlordi.stocktracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jlordi.stocktracker.models.Stock;
import com.example.jlordi.stocktracker.models.StockQuote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lordij on 2/15/2016.
 */
public class StockDatabaseAdapter extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Stock_Tracker";
    private static final String STOCK_TABLE = "stock_list_table";
    private static final int SCHEMA_VERSION = 1;
    private static final String STOCK_KEY_ROWID = "stock_id";
    private static final String STOCK_NAME = "stock_name";
    private static final String STOCK_SYMBOL = "stock_symbol";
    private static final String STOCK_EXCHANGE = "stock_exchange";
    private static final String STOCK_LAST_PRICE = "stock_last_price";
    private static final String STOCK_CHANGE = "stock_change";
    private static final String STOCK_CHANGE_PERCENT = "stock_change_percent";
    private static final String STOCK_TIMESTAMP = "stock_timestamp";
    private static final String STOCK_MSDATE = "stock_msdate";
    private static final String STOCK_VOLUME = "stock_vloume";
    private static final String STOCK_CHANGE_YTD = "stock_change_ytd";
    private static final String STOCK_HIGH = "stock_high";
    private static final String STOCK_LOW = "stock_low";
    private static final String STOCK_OPEN = "stock_open";
    private static final String sort_order = "ASC";
    private static final String[] STOCK_ALL_COLUMNS = new String[]
            {STOCK_KEY_ROWID, STOCK_NAME, STOCK_SYMBOL, STOCK_EXCHANGE, STOCK_LAST_PRICE,
                    STOCK_CHANGE, STOCK_CHANGE_PERCENT, STOCK_TIMESTAMP, STOCK_MSDATE,
                    STOCK_VOLUME, STOCK_CHANGE_YTD, STOCK_HIGH, STOCK_LOW, STOCK_OPEN};
    private static final String DATABASE_CREATE_STOCKS =
            "CREATE TABLE " + STOCK_TABLE + " (" + STOCK_KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    STOCK_NAME + " TEXT, " +
                    STOCK_SYMBOL + " TEXT, " +
                    STOCK_EXCHANGE + " TEXT, " +
                    STOCK_LAST_PRICE + "REAL, " +
                    STOCK_CHANGE + " REAL, " +
                    STOCK_CHANGE_PERCENT + " REAL, " +
                    STOCK_TIMESTAMP + " TEXT, " +
                    STOCK_MSDATE + " REAL, " +
                    STOCK_VOLUME + " INTEGER, " +
                    STOCK_CHANGE_YTD + " REAL, " +
                    STOCK_HIGH + " REAL, " +
                    STOCK_LOW + " REAL, " +
                    STOCK_OPEN + " REAL); ";
    private static StockDatabaseAdapter sSingleton;
    private SQLiteDatabase mdb;


    private StockDatabaseAdapter(Context ctxt) {
        super(ctxt, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    public synchronized static StockDatabaseAdapter getInstance(Context ctxt) {
        if (sSingleton == null) {
            sSingleton = new StockDatabaseAdapter(ctxt);
        }
        return sSingleton;
    }

    public StockDatabaseAdapter OpenConnection() throws SQLException {
        if (mdb == null) {
            if (sSingleton == null) {
                return null;
            }
            mdb = sSingleton.getWritableDatabase();
        }
        return this;
    }

    public synchronized void closeConnection() {
        if (sSingleton != null) {
            sSingleton.close();
            mdb.close();
            sSingleton = null;
            mdb = null;
        }
    }


    @Override
    public void onCreate(SQLiteDatabase mdb) {
        try {
            mdb.beginTransaction();
            mdb.execSQL(DATABASE_CREATE_STOCKS);
            mdb.setTransactionSuccessful();

        } finally {
            mdb.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase mdb, int oldVersion, int newVersion) {
        mdb.execSQL("DROP TABLE IF EXISTS " + STOCK_TABLE);
        onCreate(mdb);
    }

    // Methods for Items
    public Cursor getAllStocks() {
        return mdb.query(STOCK_TABLE,
                STOCK_ALL_COLUMNS,
                null, null, null, null,
                STOCK_KEY_ROWID + " " + sort_order);
    }

    // Methods for Items
    public List<Stock> getAllStocksList() {
        String[] STOCK_COLUMNS = new String[]
                {STOCK_KEY_ROWID, STOCK_NAME, STOCK_SYMBOL, STOCK_EXCHANGE};

        Cursor cursor = mdb.query(STOCK_TABLE,
                STOCK_COLUMNS,
                null, null, null, null,
                STOCK_KEY_ROWID + " " + sort_order);

        List<Stock> stockList = new ArrayList<Stock>();

        while (cursor.moveToNext()) {
            Log.d("JEL1", cursor.getString(cursor.getColumnIndex(STOCK_SYMBOL)));
            Log.d("JEL1", cursor.getString(cursor.getColumnIndex(STOCK_NAME)));

            stockList.add(new Stock(cursor.getString(cursor.getColumnIndex(STOCK_SYMBOL)), cursor.getString(cursor.getColumnIndex(STOCK_NAME)), cursor.getString(cursor.getColumnIndex(STOCK_EXCHANGE))));
        }
        return stockList;
    }


    // Methods for Items
    public List<StockQuote> getAllStockQuotesList() {
        String[] STOCK_COLUMNS = new String[]
                {STOCK_KEY_ROWID, STOCK_NAME, STOCK_SYMBOL, STOCK_EXCHANGE};

        Cursor cursor = mdb.query(STOCK_TABLE,
                STOCK_COLUMNS,
                null, null, null, null,
                STOCK_KEY_ROWID + " " + sort_order);

        List<StockQuote> stockQuoteList = new ArrayList<StockQuote>();

        while (cursor.moveToNext()) {
            Log.d("JEL1", cursor.getString(cursor.getColumnIndex(STOCK_SYMBOL)));
            Log.d("JEL1", cursor.getString(cursor.getColumnIndex(STOCK_NAME)));

            stockQuoteList.add(new StockQuote(cursor.getString(cursor.getColumnIndex(STOCK_SYMBOL)), cursor.getString(cursor.getColumnIndex(STOCK_NAME))));
        }
        return stockQuoteList;
    }

    public Cursor getItemRecord(long rowId) throws SQLException {
        Cursor stockCursor = mdb.query(true, STOCK_TABLE,
                STOCK_ALL_COLUMNS,
                STOCK_KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (stockCursor != null) {
            stockCursor.moveToFirst();
        }
        return stockCursor;
    }

    private long insertStockRecord(Stock Stock) {
        ContentValues initialItemValues = new ContentValues();
        initialItemValues.put(STOCK_NAME, Stock.getName());
        initialItemValues.put(STOCK_SYMBOL, Stock.getSymbol());
        initialItemValues.put(STOCK_EXCHANGE, Stock.getexchange());
        return mdb.insert(STOCK_TABLE, null, initialItemValues);
    }

    public boolean insertUniqueStockRecord(Stock Stock) {
        Log.d("JEL", "Entering insertUniqueStockRecord");
        boolean retVal = true;
        ContentValues initialItemValues = new ContentValues();
        if (queryStock(Stock.getSymbol())) {
            if (insertStockRecord(Stock) == -1) {
                retVal = false;
            }
        }
        return retVal;
    }


    public boolean queryStock(String Symbol) {
        Log.d("JEL", "Entering queryStock");
        String[] STOCK_COLUMNS = new String[]
                {STOCK_KEY_ROWID, STOCK_NAME, STOCK_SYMBOL, STOCK_EXCHANGE};

        Cursor stockCursor = mdb.query(true, STOCK_TABLE,
                STOCK_COLUMNS,
                STOCK_SYMBOL + "= '" + Symbol + "'", null, null, null, null, null);
        Log.d("JEL", "Cursor Count: " + String.valueOf(stockCursor.getCount()));
        return (stockCursor.getCount() == 0);
    }

    public boolean deleteItemRecord(long rowId) {
        return mdb.delete(STOCK_TABLE, STOCK_KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updateItemRecord(long rowId, String card_name,
                                    String card_details) {
        //ContentValues ItemArgs = new ContentValues();
        //ItemArgs.put(CARD_NAME, card_name);
        //ItemArgs.put(CARD_LASTFOUR, card_details);
        //return mDb.update(CARD_TABLE, ItemArgs, CARD_KEY_ROWID + "=" + rowId,
        //        null) > 0;
        return true;
    }

    public boolean updateItemPosition(long rowId, Integer position) {
        //ContentValues ItemArgs = new ContentValues();
        //ItemArgs.put(CARD_BALANCE_INCENTS, position);
        //return mDb.update(CARD_TABLE, ItemArgs, CARD_KEY_ROWID + "=" + rowId,
        //        null) > 0;
        return true;
    }

}
