package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ExamSettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    SharedPreferences sharedPreferences;
    Context context;
    Button update;
    TextView difficulty;
    SeekBar difficultySeekBar;
    EditText scoreEditText, durationEditText;
    SharedPreferences.Editor editor;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_settings);
        userName = getIntent().getStringExtra("userName");

        defineVariables();
        getSharedPreferences();
        setListeners();
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        switch (seekBar.getId()){
            case R.id.difficutySeekBar:
                difficulty.setText(String.valueOf(progress));
        }
    }


    public void getSharedPreferences(){
        context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(userName, context.MODE_PRIVATE);
        editor = sharedPreferences.edit() ;

        String questionDuration = sharedPreferences.getString("duration", "0");
        String questionScore = sharedPreferences.getString("score", "0");
        String questionDifficulty = sharedPreferences.getString("difficulty", "0");

        difficulty.setText(questionDifficulty);
        difficultySeekBar.setProgress(Integer.valueOf(questionDifficulty));
        durationEditText.setText(questionDuration);
        scoreEditText.setText(questionScore);

    }


    private void setListeners() {
        difficultySeekBar.setOnSeekBarChangeListener(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("difficulty", difficulty.getText().toString());
                editor.putString("duration", durationEditText.getText().toString());
                editor.putString("score", scoreEditText.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT).show();

                Intent intent;
                intent = new Intent(v.getContext(), MenuActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    private void defineVariables() {
        difficultySeekBar = findViewById(R.id.difficutySeekBar);
        durationEditText = findViewById(R.id.duratinEditText);
        scoreEditText = findViewById(R.id.scoreEditText);
        difficulty = findViewById(R.id.difficultyTextView);
        update = findViewById(R.id.buttonUpdate);
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}

