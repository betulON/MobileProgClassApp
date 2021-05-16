package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private String userName;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Question> questions;
    RecyclerView recyclerView;
    QuestionAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        userName = getIntent().getStringExtra("userName");
        defineVariables();
    }

    private void defineVariables() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        questions = new ArrayList<>();
        String filePath = userName + ".txt";
        questions = SerializableManager.readSerializable(RecyclerViewActivity.this, filePath);
        adp = new QuestionAdapter(this, questions, userName);
        recyclerView.setAdapter(adp);

    }
}