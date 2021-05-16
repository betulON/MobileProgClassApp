package tr.edu.yildiz.betul.on;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question> questions;
    String userName;

    public QuestionAdapter(Context context, ArrayList<Question> questions, String userName) {
        this.context = context;
        this.questions = questions;
        this.userName = userName;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer, option1, option2, option3, option4;
        ImageButton attachmentButton;
        ImageButton editButton;
        ImageButton deleteButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.questionCardQuestion);
            this.answer = itemView.findViewById(R.id.questionCardAnswer);
            this.option1 = itemView.findViewById(R.id.questionCardOption1);
            this.option2 = itemView.findViewById(R.id.questionCardOption2);
            this.option3 = itemView.findViewById(R.id.questionCardOption3);
            this.option4 = itemView.findViewById(R.id.questionCardOption4);
            this.editButton = itemView.findViewById(R.id.questionCardEditButton);
            this.deleteButton = itemView.findViewById(R.id.questionCardDeleteButton);
            this.attachmentButton = itemView.findViewById(R.id.questionCardAttachmentButton);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.question_card, parent, false);
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
        //holder.imageView.setImageURI(Uri.fromFile(new File(questions.get(position).getAttachmentPath())));
        holder.attachmentButton.setOnClickListener((v) -> {
            Toast.makeText(context, questions.get(position).getAttachmentPath(), Toast.LENGTH_SHORT).show();
        });
        holder.editButton.setOnClickListener((v) -> {
            Intent intent;
            intent = new Intent(context, EditQuestionActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("QUESTIONS",(Serializable)questions);
            args.putInt("POSITION", position);
            args.putString("USERNAME", userName);
            intent.putExtra("BUNDLE",args);
            context.startActivity(intent);
        });
        holder.deleteButton.setOnClickListener((v) -> {
            alertUser(position);

        });

    }

    private void alertUser(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this question?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                questions.remove(position);
                SerializableManager.saveSerializable(context, questions, userName + ".txt", false);
                Intent intent;
                intent = new Intent(context, ListQuestionsActivity.class);
                intent.putExtra("userName", userName);
                context.startActivity(intent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
