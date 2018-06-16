package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.ResponseHandler;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    // https://apiv2.bitcoinaverage.com/indices/{symbol_set}/ticker/{symbol}
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/";


    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner

        //HashMap<String,String> tickerRequest = new HashMap<>();
       // tickerRequest.put("symbol_set","global");
        //tickerRequest.put("symbol","BTCUSD");
        //letsDoSomeNetworking(tickerRequest);

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(HashMap<String,String> request) {
        // https://apiv2.bitcoinaverage.com/indices/global/ticker/{ticker}
//    // https://apiv2.bitcoinaverage.com/indices/{symbol_set}/ticker/{symbol}
//    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/";

        String url = BASE_URL + request.get("symbol_set") + "/ticker/" + request.get("symbol");
        Log.d("BitcoinTicker",url);
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String bitcoinAsk = response.getString("ask");
                    Log.d("BitcoinTicker","GET success! Latest ask price is " + bitcoinAsk);
                    mPriceTextView.setText(bitcoinAsk);
                }
                catch(Throwable e)
                {
                    Log.d("BitcoinTicker","JSONObject incorrect!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.d("BitcoinTicker","GET failed!");

            }
        });
    }


}
