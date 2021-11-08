package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.PendingIntent.getActivity;

public class DetailActivity extends AppCompatActivity  {


    private static final String TAG = "DetailActivity";
    @BindView(R.id.itemNameTitle)
    TextView itemNameTitle;
    @BindView(R.id.profileName)
    TextView profileName;
    @BindView(R.id.itemAvatar)
    ImageView itemAvatar;
    @BindView(R.id.socialNetwork)
    TextView socialNetwork;
    @BindView(R.id.flot_Favoris)
    ImageView flot_Favoris;
    @BindView(R.id.buttonBack)
    ImageView buttonBack;
    @BindView(R.id.adress)
    TextView adress;
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    @BindView(R.id.Me)
    TextView Me;

    private NeighbourApiService mApiService;
    protected Neighbour neighbour;
    private Serializable mNeighbour;
    public static String NEIGHBOUR = "NEIGHBOUR";
    boolean favorite;




    public static Intent navigate(Context context, Neighbour neighbour) {
        Intent intentDetailActivity = new Intent(context,DetailActivity .class);
        intentDetailActivity .putExtra(NEIGHBOUR, neighbour);
        return intentDetailActivity ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();
        getSupportActionBar().hide();
        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra(NEIGHBOUR);
        itemNameTitle.setText(neighbour.getName());
        profileName.setText(neighbour.getName());
        adress.setText(neighbour.getAddress());
        phoneNumber.setText(neighbour.getPhoneNumber());
        socialNetwork.setText(neighbour.getAvatarUrl());
        Me.setText(neighbour.getAboutMe());

        Glide.with(DetailActivity.this)
                .load(neighbour.getAvatarUrl())
                .centerCrop()
                .into(itemAvatar);

        favorite = neighbour.favorite() ;

        if (favorite) {
            flot_Favoris.setImageResource(R.drawable.ic_star_white_24dp);
            flot_Favoris.setColorFilter(Color.parseColor("#FECB23"));
        } else {
            flot_Favoris.setImageResource(R.drawable.ic_star_border_white_24dp);
            flot_Favoris.setColorFilter(Color.parseColor("#FECB23"));

        }

        flot_Favoris.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                favorite = !favorite;
Log.d(TAG,"Click on Favoris: "+favorite);
                if (favorite) {
                    flot_Favoris.setImageResource(R.drawable.ic_star_white_24dp);
                    flot_Favoris.setColorFilter(Color.parseColor("#FECB23"));
                    mApiService.setFavorite(neighbour,favorite);
                    Toast.makeText(DetailActivity.this, "Add to Favorite",
                            Toast.LENGTH_SHORT).show();
                } else {
                    flot_Favoris.setImageResource(R.drawable.ic_star_border_white_24dp);
                    flot_Favoris.setColorFilter(Color.parseColor("#FECB23"));
                    mApiService.setFavorite(neighbour,favorite);
                    Toast.makeText(DetailActivity.this, "Remove on favorite",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @OnClick(R.id.buttonBack)
    void returnBack() {
        finish();

    }

}











