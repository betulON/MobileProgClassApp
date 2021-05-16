package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class CreateExamActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Question> questions;
    RecyclerView recyclerView;
    ChoosableQuestionAdapter adp;

    private String userName;
    Context context;
    SharedPreferences sharedPreferences;
    Button returnButton, saveExamButton;
    TextView difficulty;
    SeekBar difficultySeekBar;
    EditText scoreEditText, durationEditText, examNameEditText;

    ArrayList<Question> checkedQuestions;
    ArrayList<Question> examQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        userName = getIntent().getStringExtra("userName");
        defineVariables();
        getSharedPreferences();
        setListeners();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        difficulty.setText(String.valueOf(progress));
    }


    public void getSharedPreferences(){
        context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(userName, context.MODE_PRIVATE);

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

        saveExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String examFileName = userName + "_" + examNameEditText.getText().toString() + ".txt";
                getCheckedQuestions();
                createExam(examFileName);
                Toast.makeText(getApplicationContext(), "Exam Saved to the " + examFileName, Toast.LENGTH_SHORT).show();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), MenuActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    private void defineVariables() {
        difficultySeekBar = findViewById(R.id.difficutySeekBar);
        durationEditText = findViewById(R.id.createExamDurationEditText);
        scoreEditText = findViewById(R.id.createExamScoreEditText);
        examNameEditText = findViewById(R.id.examNameEditText);
        difficulty = findViewById(R.id.difficultyTextView);
        saveExamButton = findViewById(R.id.createExamButtonSaveExam);
        returnButton = findViewById(R.id.createExamButtonReturn);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        String filePath = userName + ".txt";
        questions = SerializableManager.readSerializable(CreateExamActivity.this, filePath);
        if (questions == null){
            Toast.makeText(CreateExamActivity.this, "No questions to add to exam, back to Menu.", Toast.LENGTH_SHORT).show();
            Intent intent;
            intent = new Intent(CreateExamActivity.this, MenuActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
        else {
            adp = new ChoosableQuestionAdapter(this, questions, userName);
            recyclerView.setAdapter(adp);

            checkedQuestions = new ArrayList<>();
            examQuestions = new ArrayList<>();
        }

    }

    private void getCheckedQuestions(){
        checkedQuestions = adp.getCheckedQuestions();
        for (Question question : checkedQuestions){
            if (question.isChoseForExam()){
                examQuestions.add(question);
            }
        }
    }
    private void createExam(String fileName){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeBytes("\n" + examNameEditText.getText().toString() + "\n\n" +
                    "Duration: " + durationEditText.getText().toString() + "\n" +
                    "Score:  " + scoreEditText.getText().toString());
            int questionNum = 1;
            for (Question question : examQuestions){
                objectOutputStream.writeBytes("\n\n" + questionNum + ") ");
                objectOutputStream.writeBytes(question.getQuestion());
                ArrayList<String> options = question.getOptions();

                int num = Integer.valueOf(difficulty.getText().toString()); //difiiculty (2-5)
                int index = 0;
                Random rn = new Random();
                int k = rn.nextInt(num); //random value between 0-num-1

                //writing answer and other options
                for (int i=0; i<num; i++){
                    objectOutputStream.writeBytes("\n" + (i+1) + ") ");
                    if (i == k){
                        objectOutputStream.writeBytes(question.getAnswer());
                    }else{
                        objectOutputStream.writeBytes(options.get(index));
                        index++;
                    }
                }
                questionNum++;
            }
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}