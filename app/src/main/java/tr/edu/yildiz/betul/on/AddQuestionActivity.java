package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {
    private EditText question, option1, option2, option3, option4, option5;
    private ImageButton attachmentButton, returnButton;
    private Button saveQuestionButton;
    private int answerID;
    private ArrayList<CheckBox> checkBoxes;
    private Question questionObject;
    private String userName;
    Uri URI = null;
    private TextView textAttachment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        userName = getIntent().getStringExtra("userName");
        defineVariables();
        defineListeners();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 101 && resultCode == RESULT_OK ){
            URI = data.getData();
            Toast.makeText(AddQuestionActivity.this, "File attached", Toast.LENGTH_SHORT).show();
            textAttachment.setText(URI.getLastPathSegment());
            textAttachment.setVisibility(View.VISIBLE);
        }
    }

    private void defineListeners() {
        saveQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCheckBoxes() == -1){
                    Toast.makeText(AddQuestionActivity.this, "Please check an answer", Toast.LENGTH_SHORT).show();
                }else if (checkCheckBoxes() == -2){
                    Toast.makeText(AddQuestionActivity.this, "Please check only one answer", Toast.LENGTH_SHORT).show();
                }else {
                    createQuestion();
                    saveQuestionToFileSerializable();
                }
            }
        });
        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("return_data", true);
                startActivityForResult(Intent.createChooser(intent, "complete action using"), 101);
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

    private ArrayList<Question> readQuestionsFromFile() {
        String filename = userName + ".txt";
        ArrayList<Question> questions;
        questions = SerializableManager.readSerializable(AddQuestionActivity.this, filename);
        return questions;
    }


    private void saveQuestionToFileSerializable() {
        ArrayList<Question> questions;
        questions = readQuestionsFromFile();
        if (questions == null){
            questions = new ArrayList<>();
        }
        questions.add(questionObject);
        String filename = userName + ".txt";
        SerializableManager.saveSerializable(AddQuestionActivity.this, questions, filename, false);
        Toast.makeText(AddQuestionActivity.this, "Question is saved", Toast.LENGTH_SHORT).show();
    }

    private void defineVariables() {
        question = (EditText)findViewById(R.id.editTextQuestion);
        option1 = (EditText)findViewById(R.id.editTextOption1);
        option2 = (EditText)findViewById(R.id.editTextOption2);
        option3 = (EditText)findViewById(R.id.editTextOption3);
        option4 = (EditText)findViewById(R.id.editTextOption4);
        option5 = (EditText)findViewById(R.id.editTextOption5);
        checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox)findViewById(R.id.checkBox1));
        checkBoxes.add((CheckBox)findViewById(R.id.checkBox2));
        checkBoxes.add((CheckBox)findViewById(R.id.checkBox3));
        checkBoxes.add((CheckBox)findViewById(R.id.checkBox4));
        checkBoxes.add((CheckBox)findViewById(R.id.checkBox5));
        attachmentButton = (ImageButton)findViewById(R.id.imageButtonAttachment);
        returnButton = (ImageButton)findViewById(R.id.imageButtonReturn);
        saveQuestionButton = (Button)findViewById(R.id.buttonSave);
        textAttachment = (TextView)findViewById(R.id.textView4);
    }

    public void createQuestion() {
        ArrayList<String> options = new ArrayList<>();
        options.add(option1.getText().toString());
        options.add(option2.getText().toString());
        options.add(option3.getText().toString());
        options.add(option4.getText().toString());
        options.add(option5.getText().toString());
        String answer = options.get(answerID);
        options.remove(answerID);
        String question = this.question.getText().toString();
        questionObject = new Question(question, answer, options, textAttachment.getText().toString());
    }

    public int checkCheckBoxes(){
        int num = 0;
        int index = 0;
        for (CheckBox box: checkBoxes){
            if (box.isChecked()){
                num++;
                answerID = index;
            }
            index++;
        }
        if (num == 0){
            return -1;
        }else if (num > 1){
            return -2;
        }
        return answerID;
    }

}