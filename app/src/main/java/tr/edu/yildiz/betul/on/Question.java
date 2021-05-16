package tr.edu.yildiz.betul.on;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    //private String questionID;
    private String question;
    private String answer;
    private ArrayList<String> options;
    private String attachmentPath;
    private static final long serialVersionUID = 4L;

    private boolean choseForExam;

    public Question(String question, String answer, ArrayList<String> options, String attachmentPath) {
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.attachmentPath = attachmentPath;
        this.choseForExam = false;
    }

    public Question(String question, String answer, ArrayList<String> options, String attachmentPath, boolean choseForExam) {
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.attachmentPath = attachmentPath;
        this.choseForExam = choseForExam;
    }

    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }

//    public int getAnswerID() {
//        return answerID;
//    }

    public boolean isChoseForExam() { return choseForExam; }
    public void setChoseForExam(boolean choseForExam) {
        this.choseForExam = choseForExam;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

//    public void setAnswerID(int answerID) {
//        this.answerID = answerID;
//    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }


}
