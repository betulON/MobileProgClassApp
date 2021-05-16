package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "usersData.txt";

    EditText username, password;
    TextView textMassage;
    Button signInButton, signUpButton;
    ArrayList<Person> people;
    Integer attempts;
    Boolean canTryLogin;
    String message;
    Person user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineVariables();
        defineListeners();
    }


    private void defineListeners() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (checkUser()){
                    intent = new Intent(v.getContext(), MenuActivity.class);
                    intent.putExtra("userName", user.getUsername());
                    cleanTextBoxes();
                    Toast.makeText(MainActivity.this, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    cleanTextBoxes();
                    attempts++;
                    message = "Number of incorrect attempts: " + attempts;
                    textMassage.setText(message);
                    if (attempts >= 3){
                        Toast.makeText(MainActivity.this, "You've tried 3 times wrongly, login is no longer allowed", Toast.LENGTH_LONG).show();
                        //disable the button
                        signInButton.setEnabled(false);
                        Toast.makeText(MainActivity.this, "You're being directed to the sign up page", Toast.LENGTH_SHORT).show();
                        intent = new Intent(v.getContext(), SignUpActivity.class);
                        Bundle args = new Bundle();
                        //bundle.putParcelableArrayList("Birds", birds);
                        //intent.putExtras(bundle);
                        args.putSerializable("ARRAYLIST",(Serializable)people);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }

                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTextBoxes();
                Intent intent;
                intent = new Intent(v.getContext(), SignUpActivity.class);
                people = SerializableManager.readSerializable(MainActivity.this, FILE_NAME);
                Bundle args = new Bundle();
                //bundle.putParcelableArrayList("Birds", birds);
                //intent.putExtras(bundle);
                args.putSerializable("ARRAYLIST",(Serializable)people);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
    }

    private void cleanTextBoxes() {
        username.setText("");
        password.setText("");
    }

    private boolean checkUser() {
        System.out.println("in checkUser");
        try {
            user = findUser(username.getText().toString());
            if ((user != null) && user.getPassword().equals(password.getText().toString()))
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void defineVariables(){
        attempts=0;
        message="Number of incorrect attempts: " + attempts;
        canTryLogin=true;
        username = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        textMassage = (TextView) findViewById(R.id.tvAttempts);
        signInButton = (Button)findViewById(R.id.btnLogin);
        signUpButton = (Button)findViewById(R.id.btnSignup);
        people = new ArrayList<Person>();
    }


    public Person findUser(String userName) throws IOException {
        boolean found = false;
        Person objectToReturn = null;
        people = SerializableManager.readSerializable(MainActivity.this, FILE_NAME);

        int index = 0;
        if (people == null){
            return null;
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