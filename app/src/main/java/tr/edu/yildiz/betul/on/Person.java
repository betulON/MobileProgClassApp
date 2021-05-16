package tr.edu.yildiz.betul.on;

import android.net.Uri;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    //private Bitmap avatar;
    private String username;
    private String name;
    private String surname;
    private String birthDate;
    private String phone;
    private String password;
    private String imagePath;
    //private ArrayList<Question> questions;



    public Person(String username, String name, String surname, String password, String birthDate, String phone, String imagePath){
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthDate = birthDate;
        this.phone = phone;
        this.imagePath = imagePath;
        //this.questions = questions;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    //public ArrayList<Question> getQuestions() { return questions; }
    //public void setQuestions(ArrayList<Question> questions) { this.questions = questions; }


}

