package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class ListQuestionsActivity extends AppCompatActivity {

    private String userName;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Question> questions;
    RecyclerView recyclerView;
    QuestionAdapter adp;

    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        userName = getIntent().getStringExtra("userName");
        defineVariables();

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
        returnButton = findViewById(R.id.ListQuestionsImageButtonReturn);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        String filePath = userName + ".txt";
        questions = SerializableManager.readSerializable(ListQuestionsActivity.this, filePath);
        if (questions == null){
            Toast.makeText(ListQuestionsActivity.this, "No questions to list, back to Menu", Toast.LENGTH_SHORT).show();
            Intent intent;
            intent = new Intent(ListQuestionsActivity.this, MenuActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }else{
            adp = new QuestionAdapter(this, questions, userName);
            recyclerView.setAdapter(adp);
        }


    }

}