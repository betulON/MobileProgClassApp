package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private static final String FILE_NAME = "usersData.txt";

    EditText username, name, surname, birthDate, phone, password, password2;
    ImageView image;
    ImageButton editImageButton;
    Button signUpButton;
    Person user;
    ArrayList<Person> people;
    Uri URI;
    DateInputMask date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Person> object = (ArrayList<Person>) args.getSerializable("ARRAYLIST");
        if (object != null){
            people = object;
        }
        defineVariables();
        defineListeners();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 101 && resultCode == RESULT_OK ){
            URI = data.getData();
            Toast.makeText(SignUpActivity.this, "File attached", Toast.LENGTH_SHORT).show();
            image.setImageURI(URI);
        }
    }


    private void defineListeners() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("sign up button clicked");
                try {
                    user = null;
                    user = findUser(username.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (user != null){
                    Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("userID", username.getText().toString());
                    startActivity(intent);
                }else if (!checkPasswords()){
                    Toast.makeText(SignUpActivity.this, "Given passwords do not match", Toast.LENGTH_SHORT).show();
                }else{
                    String path;

                    if (URI==null){
                        path = "";
                    }else {
                        path=URI.getPath();
                    }
                    user = new Person(username.getText().toString(), name.getText().toString(), surname.getText().toString(), password.getText().toString(), date.getDate(), phone.getText().toString(), path);
                    saveUsersSerializable(user);
                    cleanTextBoxes();
                    Intent intent;
                    intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("userID", username.getText().toString());
                    startActivity(intent);
                }
            }
        });
        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("return_data", true);
                startActivityForResult(Intent.createChooser(intent, "complete action using"), 101);
            }
        });
    }


    private boolean checkPasswords() {
        return password.getText().toString().equals(password2.getText().toString());
    }



    private void cleanTextBoxes() {
        username.setText("");
        password.setText("");
    }



    public void defineVariables(){
        username = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        password2 = (EditText) findViewById(R.id.editTextPassword2);
        name = (EditText)findViewById(R.id.editTextName);
        surname = (EditText)findViewById(R.id.editTextSurname);
        birthDate = (EditText)findViewById(R.id.editTextBirthdate);
        phone = (EditText)findViewById(R.id.editTextPhone);
        image = (ImageView)findViewById(R.id.profile_image);
        editImageButton = (ImageButton)findViewById(R.id.imageButtonEditImage);
        signUpButton = (Button)findViewById(R.id.button2);
        date = new DateInputMask(birthDate);
    }



    public void saveUsersSerializable(Person objectToSave) {
        if (people == null){
            people = new ArrayList<Person>();
        }
        people.add(objectToSave);
        SerializableManager.saveSerializable(SignUpActivity.this, people, FILE_NAME, false);
        Toast.makeText(SignUpActivity.this, "Your are registered", Toast.LENGTH_SHORT).show();
    }

    public Person findUser(String userName) throws IOException {
        boolean found = false;
        Person objectToReturn = null;

        int index = 0;
        if (people == null){
            return objectToReturn;
        }
        while(!found && index < people.size()) {
            objectToReturn = people.get(index);
            if (objectToReturn.getUsername().equals(userName)){
                found = true;
            }
            index++;
        }
        if (found)
            return objectToReturn;
        return null;
    }



}