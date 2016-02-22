package com.example.jlordi.stocktracker.Server;

import com.example.jlordi.stocktracker.models.Stock;
import com.example.jlordi.stocktracker.models.StockChart;
import com.example.jlordi.stocktracker.models.StockChartRequest;
import com.example.jlordi.stocktracker.models.StockQuote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jlordi on 1/30/2016.
 */
public interface MarkitService {

    @GET("/MODApis/Api/v2/Lookup/JSON")
    Call<List<Stock>> getStocks(@Query("input") String search);

    @GET("/MODApis/Api/v2/Quote/JSON")
    Call<StockQuote> getStockQuote(@Query("symbol") String symbol);

    @GET("/MODApis/Api/v2/InterActiveChart/JSON")
    Call<StockChart> getStockChart(@Query("parameters") StockChartRequest Request);

    //http://dev.markitondemand.com/MODApis/Api/v2/InteractiveChart/json?parameters=%7B%22Normalized%22%3Afalse%2C%22NumberOfDays%22%3A365%2C%22DataPeriod%22%3A%22Day%22%2C%22Elements%22%3A%5B%7B%22Symbol%22%3A%22AAPL%22%2C%22Type%22%3A%22price%22%2C%22Params%22%3A%5B%22c%22%5D%7D%5D%7D
    //http://dev.markitondemand.com/MODApis/Api/v2/InterActiveChart/JSON?Normalized=false&NumberOfDays=365&DatePeriod=Day&Symbol=AAPL&Type=Price&Params=C
}
