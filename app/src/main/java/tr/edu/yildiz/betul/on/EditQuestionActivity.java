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

import java.util.ArrayList;

public class EditQuestionActivity extends AppCompatActivity {
    private EditText question, option1, option2, option3, option4, option5;
    private ImageButton attachmentButton, returnButton;
    private Button saveQuestionButton;
    private int answerID;
    private ArrayList<CheckBox> checkBoxes;
    private Question questionObject;
    private String userName;
    Uri URI;
    private TextView textAttachment;


    private ArrayList<Question> questions;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        position = args.getInt("POSITION");
        questions = (ArrayList<Question>) args.getSerializable("QUESTIONS");
        userName = args.getString("USERNAME");
        defineVariables();
        defineListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 101 && resultCode == RESULT_OK ){
            URI = data.getData();
            Toast.makeText(EditQuestionActivity.this, "File attached", Toast.LENGTH_SHORT).show();
            textAttachment.setText(URI.getPath());
            textAttachment.setVisibility(View.VISIBLE);
        }
    }

    private void defineListeners() {
        saveQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCheckBoxes() == -1){
                    Toast.makeText(EditQuestionActivity.this, "Please check an answer", Toast.LENGTH_SHORT).show();
                }else if (checkCheckBoxes() == -2){
                    Toast.makeText(EditQuestionActivity.this, "Please check only one answer", Toast.LENGTH_SHORT).show();
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
                intent = new Intent(v.getContext(), ListQuestionsActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Question> readQuestionsFromFile() {
        String filename = userName + ".txt";
        ArrayList<Question> questions;
        questions = SerializableManager.readSerializable(EditQuestionActivity.this, filename);
        return questions;
    }


    private void saveQuestionToFileSerializable() {
        if (questions == null){
            questions = new ArrayList<>();
        }
        questions.set(position, questionObject);
        String filename = userName + ".txt";
        SerializableManager.saveSerializable(EditQuestionActivity.this, questions, filename, false);
        Toast.makeText(EditQuestionActivity.this, "Question is saved", Toast.LENGTH_SHORT).show();
    }

    private void defineVariables() {
        question = findViewById(R.id.editTextQuestion);
        question.setText(questions.get(position).getQuestion());
        option1 = findViewById(R.id.editTextOption1);
        option2 = findViewById(R.id.editTextOption2);
        option3 = findViewById(R.id.editTextOption3);
        option4 = findViewById(R.id.editTextOption4);
        option5 = findViewById(R.id.editTextOption5);
        option1.setText(questions.get(position).getAnswer());
        option2.setText(questions.get(position).getOptions().get(0));
        option3.setText(questions.get(position).getOptions().get(1));
        option4.setText(questions.get(position).getOptions().get(2));
        option5.setText(questions.get(position).getOptions().get(3));
        checkBoxes = new ArrayList<>();
        checkBoxes.add(findViewById(R.id.checkBox1));
        checkBoxes.add(findViewById(R.id.checkBox2));
        checkBoxes.add(findViewById(R.id.checkBox3));
        checkBoxes.add(findViewById(R.id.checkBox4));
        checkBoxes.add(findViewById(R.id.checkBox5));
        checkBoxes.get(0).setChecked(true);
        attachmentButton = findViewById(R.id.imageButtonAttachment);
        returnButton = findViewById(R.id.imageButtonReturn);
        saveQuestionButton = findViewById(R.id.buttonSave);
        textAttachment = findViewById(R.id.textView4);
        textAttachment.setText(questions.get(position).getAttachmentPath());
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