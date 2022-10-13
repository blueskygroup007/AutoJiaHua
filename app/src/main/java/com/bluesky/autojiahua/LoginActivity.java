package com.bluesky.autojiahua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bluesky.autojiahua.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.bluesky.autojiahua.databinding.ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btnEnter = binding.btnSplashEnter;
        btnEnter.setOnClickListener(view -> {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
        initData();
    }

    /**
     * 初始化一些准备数据
     */
    private void initData() {
        //Todo 预处理
    }

}