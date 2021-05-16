package tr.edu.yildiz.betul.on;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChoosableQuestionAdapter extends RecyclerView.Adapter<ChoosableQuestionAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question> questions;
    String userName;

    public ChoosableQuestionAdapter(Context context, ArrayList<Question> questions, String userName) {
        this.context = context;
        this.questions = questions;
        this.userName = userName;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer, option1, option2, option3, option4;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.questionCardQuestion);
            this.answer = itemView.findViewById(R.id.questionCardAnswer);
            this.option1 = itemView.findViewById(R.id.questionCardOption1);
            this.option2 = itemView.findViewById(R.id.questionCardOption2);
            this.option3 = itemView.findViewById(R.id.questionCardOption3);
            this.option4 = itemView.findViewById(R.id.questionCardOption4);
            this.checkBox = itemView.findViewById(R.id.questionCardCheckBox);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.question_card2, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.question.setText(questions.get(position).getQuestion());
        holder.option1.setText(questions.get(position).getOptions().get(0));
        holder.option2.setText(questions.get(position).getOptions().get(1));
        holder.option3.setText(questions.get(position).getOptions().get(2));
        holder.option4.setText(questions.get(position).getOptions().get(3));
        holder.answer.setText(questions.get(position).getAnswer());

        holder.checkBox.setOnClickListener((v) -> {
            if(holder.checkBox.isChecked())
                questions.get(position).setChoseForExam(true);
        });

    }

    public ArrayList<Question> getCheckedQuestions(){
        return questions;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
