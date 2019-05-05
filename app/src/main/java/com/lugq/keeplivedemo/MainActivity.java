package com.lugq.keeplivedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn_exit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(MainActivity.this, LongRunningService.SHARED_IS_EXIT_BY_PEOPLE, true);
                finish();
            }
        });

        SPUtils.put(this, LongRunningService.SHARED_IS_EXIT_BY_PEOPLE, false);

        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }
}
