package com.example.kartik.tutorfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kartik on 23-01-2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

Context ctx;
    ArrayList<Tutors> tutors = new ArrayList<>();
    public RecyclerAdapter(ArrayList<Tutors> tutors, Context ctx) {
        this.tutors = tutors;
        this.ctx=ctx;


    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tutors_recycler_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Tutors  tutor = tutors.get( position);

        holder.Name.setText(tutor.getName());
     //   System.out.println("Name of tutor["+position+"]"+holder.Name.getText());
        holder.Gender.setText(tutor.getGender());
        holder.Age.setText(Integer.toString(tutor.getAge())+"  years");
      //  holder.Email.setText("Email: "+tutor.getEmail());
        holder.Address.setText(tutor.getAddress());
      //  holder.Mobile.setText("Mobile: "+tutor.getMobile());
    //System.out.println("checkmail"+tutor.getEmail());
    }

    @Override
    public int getItemCount() {
        return tutors.size();

    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
    TextView Name, Gender,Address,Age;

        public RecyclerViewHolder(View view) {
            super(view);
            Name = (TextView)view.findViewById(R.id.tv_tutor_name);
            Gender=(TextView)view.findViewById(R.id.tv_tutor_gender);
            Age = (TextView)view.findViewById(R.id.tv_tutor_age);
        //    Email=(TextView)view.findViewById(R.id.tv_tutor_email);
            Address = (TextView)view.findViewById(R.id.tv_tutor_subs);
        //    Mobile = (TextView)view.findViewById(R.id.tv_tutor_mobile);
        }
    }


}
