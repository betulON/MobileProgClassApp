package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout profile;
    private LinearLayout addQuestion;
    private LinearLayout listQuestions;
    private LinearLayout createExam;
    private LinearLayout examSettings;
    private LinearLayout logOut;
    private String userName;
    //private Person user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        userName = getIntent().getStringExtra("userName");
        defineVariables();
        defineListeners();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.linearLayoutProfile:
                intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                break;
            case R.id.linearLayoutAdd:
                intent = new Intent(v.getContext(), AddQuestionActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                break;
            case R.id.linearLayoutList:
                intent = new Intent(v.getContext(), ListQuestionsActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                break;

            case R.id.linearLayoutCreateExam:
                intent = new Intent(v.getContext(), CreateExamActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                break;

            case R.id.linearLayoutExamSettings:
                intent = new Intent(v.getContext(), ExamSettingsActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
                break;

            case R.id.linearLayoutLogOut:
                intent = new Intent(v.getContext(), MainActivity.class);
                Toast.makeText(this, "Goodbye " + userName, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    private void defineVariables() {
        profile = (LinearLayout) findViewById(R.id.linearLayoutProfile);
        addQuestion = (LinearLayout) findViewById(R.id.linearLayoutAdd);
        listQuestions = (LinearLayout) findViewById(R.id.linearLayoutList);
        createExam = (LinearLayout) findViewById(R.id.linearLayoutCreateExam);
        examSettings = (LinearLayout) findViewById(R.id.linearLayoutExamSettings);
        logOut = (LinearLayout) findViewById(R.id.linearLayoutLogOut);
    }

    private void defineListeners() {
        profile.setOnClickListener(this);
        addQuestion.setOnClickListener(this);
        listQuestions.setOnClickListener(this);
        createExam.setOnClickListener(this);
        examSettings.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }



}