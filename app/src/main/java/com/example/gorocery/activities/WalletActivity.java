package com.example.gorocery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gorocery.R;

import java.util.Objects;

public class WalletActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvWalletAmmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Objects.requireNonNull(getSupportActionBar()).hide();

        init();
    }

    public void init(){

        toolbar=findViewById(R.id.toolbar_wallet);

        tvWalletAmmount=findViewById(R.id.tv_wallet_amount);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
