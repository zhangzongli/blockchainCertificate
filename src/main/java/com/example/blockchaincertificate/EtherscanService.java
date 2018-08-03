package com.example.blockchaincertificate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by guo on 2018/7/25.
 * desc:
 */
public interface EtherscanService {


    //&address=0x6194Ab1ec4A1E67dF89537ed913fc014e538b303
    @GET("/api?module=account&action=txlist&startblock=0&endblock=99999999&sort=asc&apikey=5TRI6TWSZFQ2V5G6EK5JQ8TJS5FQX3CR2I")
    public Call<Txlist> getTransactionsByAddress(@Query("address") String address);
}
