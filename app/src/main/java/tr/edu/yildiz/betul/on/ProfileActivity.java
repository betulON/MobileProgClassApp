package tr.edu.yildiz.betul.on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String FILE_NAME = "usersData.txt";

    TextView name,surname,date,phone,email;
    ImageView image;
    Button profileReturnButton;
    ArrayList<Person> people;
    Person user;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = getIntent().getStringExtra("userName");
        try {
            defineVariables();
        } catch (IOException e) {
            e.printStackTrace();
        }
        defineListeners();
    }



    private void defineListeners() {

        profileReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), MenuActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }


    public void defineVariables() throws IOException {
        profileReturnButton = (Button)findViewById(R.id.profileReturnButton);
        people = new ArrayList<Person>();
        user = findUser(userName);
        name = findViewById(R.id.textViewName);
        surname = findViewById(R.id.textViewSurname);
        email = findViewById(R.id.textViewEmail);
        phone = findViewById(R.id.textViewPhone);
        date = findViewById(R.id.textViewDate);
        image = findViewById(R.id.profileActivityImage);

        image.setImageURI(Uri.fromFile(new File(user.getImagePath())));
        name.setText(user.getName());
        surname.setText(user.getSurname());
        email.setText(user.getUsername());
        phone.setText(user.getPhone());
        date.setText(user.getBirthDate());

    }

    public Person findUser(String userName) throws IOException {
        boolean found = false;
        Person objectToReturn = null;
        people = SerializableManager.readSerializable(ProfileActivity.this, FILE_NAME);

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