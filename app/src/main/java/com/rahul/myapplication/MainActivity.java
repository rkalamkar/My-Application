package com.rahul.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.rahul.myapplication.Model.Response;
import com.rahul.myapplication.Model.UserData;
import com.rahul.myapplication.Utils.GeneralUtils;
import com.rahul.myapplication.Utils.WebService;
import com.rahul.myapplication.Utils.WebServiceListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeDeck.SwipeEventCallback, WebServiceListener {
    SwipeDeck cardStack;
    WebService webService;
    ShimmerFrameLayout shimmerFrameLayout;
    Response response;
    TinderAdapter adapter;
    TextView txtNetwork;
    ImageView imgNetwork;
    LinearLayout viewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        imgNetwork = (ImageView) findViewById(R.id.imageViewNetwork);
        txtNetwork = (TextView) findViewById(R.id.textViewNetwork);
        viewError = (LinearLayout) findViewById(R.id.linerLayout);
        final ArrayList<UserData> testData = new ArrayList<>();
        //   testData.addAll(GeneralUtils.getArrayList());
        webService = new WebService();

        initialize();
        cardStack.setEventCallback(this);
    }

    public void showProgress(boolean isToShow) {
        if (isToShow) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            cardStack.setVisibility(View.GONE);
            shimmerFrameLayout.startShimmer();
        } else {
            shimmerFrameLayout.setVisibility(View.GONE);
            cardStack.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmer();
        }
    }

    public void getData() {
        webService.getUserList(this);
    }

    @Override
    public void cardSwipedLeft(int position) {
        Log.e("Swipe", "cardSwipedLeft");
    }

    @Override
    public void cardSwipedRight(int position) {
        Log.e("Swipe", "cardSwipedRight");
        if (response != null) {
            ArrayList<UserData> arrayList = new ArrayList<>();
            if (GeneralUtils.getFavList(getApplicationContext()) != null)
                arrayList.addAll(GeneralUtils.getFavList(getApplicationContext()));
            arrayList.add(response.getResults().get(position));
            GeneralUtils.addFav(getApplicationContext(), arrayList);
        }
    }

    @Override
    public void cardsDepleted() {
        showProgress(true);
        Log.e("Swipe", "cardsDepleted");
        if (GeneralUtils.isNetworkConnected(getApplicationContext())) {
            getData();
        } else {
            imgNetwork.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline));
            txtNetwork.setText(getResources().getString(R.string.offline));
            txtNetwork.setTextColor(getResources().getColor(R.color.red));
            onError("No more data");
        }
    }

    @Override
    public void cardActionDown() {
    }

    @Override
    public void cardActionUp() {
    }

    public void initialize() {
        if (GeneralUtils.isNetworkConnected(getApplicationContext())) {
             showProgress(true);
            imgNetwork.setImageDrawable(getResources().getDrawable(R.drawable.ic_online));
            txtNetwork.setText(getResources().getString(R.string.online));
            txtNetwork.setTextColor(getResources().getColor(R.color.green));
            getData();
            viewError.setVisibility(View.GONE);
        } else {
            showProgress(true);
            getOfflineData();
        }
    }

    public void getOfflineData() {
        imgNetwork.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline));
        txtNetwork.setText(getResources().getString(R.string.offline));
        txtNetwork.setTextColor(getResources().getColor(R.color.red));

        ArrayList<UserData> userData = GeneralUtils.getFavList(getApplicationContext());
        if (userData != null) {
            adapter = new TinderAdapter(userData, this);
            cardStack.setAdapter(adapter);
            viewError.setVisibility(View.GONE);
        } else {
            onError("No data available");
        }
        response = null;
        showProgress(false);
    }

    @Override
    public void onSuccess(Response response) {
        showProgress(false);
        Log.e("Response ", "Success ");
        this.response = response;
        imgNetwork.setImageDrawable(getResources().getDrawable(R.drawable.ic_online));
        txtNetwork.setText(getResources().getString(R.string.online));
        txtNetwork.setTextColor(getResources().getColor(R.color.green));

        adapter = new TinderAdapter(response.getResults(), this);
        cardStack.setAdapter(adapter);
    }

    @Override
    public void onError(String message) {
        showProgress(false);
        viewError.setVisibility(View.VISIBLE);
        Log.e("Response ", "Failure ");
    }
}
