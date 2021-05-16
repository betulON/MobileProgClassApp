package tr.edu.yildiz.betul.on;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SerializableManager {


    public static <T extends Serializable> void saveSerializable(Context context, T objectToSave, String fileName, boolean append) {
        try {
            int mode;
            if (append){ mode = Context.MODE_APPEND;
            }else { mode = Context.MODE_PRIVATE; }

            FileOutputStream fileOutputStream = context.openFileOutput(fileName, mode);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objectToSave);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static<T extends Serializable> T readSerializable(Context context, String fileName) {

        T objectToReturn = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (T) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objectToReturn;
    }

}