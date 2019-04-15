package com.example.twitterpoetry.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.twitterpoetry.R;
import com.example.twitterpoetry.model.SyllableCounter;

public class MainActivity extends AppCompatActivity {
    TextView text;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.tv_default_main);
        editText = findViewById(R.id.et_user_word);
        button = findViewById(R.id.btn_count_syllables);
        button.setOnClickListener(view ->
                text.setText("" + SyllableCounter.countInLine(editText.getText().toString())));
    }
}
