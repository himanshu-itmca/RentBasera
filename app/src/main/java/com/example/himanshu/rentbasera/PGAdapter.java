package com.example.himanshu.rentbasera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by himanshu on 5/9/2017.
 */

public class PGAdapter extends RecyclerView.Adapter <PGAdapter.MyViewHolder> {

    private List<PG> pgList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder{
//        public TextView pgname,pglocation,no_of_rooms,rooms,no_of_beds,available_for,payableAmount;
//        public ImageView image;
//        private Button bookNowBtn;

         ImageView pgimage;
        TextView pgadress,pgCity,gender;
        Button viewMore;

        public MyViewHolder(View view) {
            super(view);
//            image = (ImageView) view.findViewById(R.id.pgimageview);
//            pgname = (TextView) view.findViewById(R.id.pgnameTextView);
//            rooms = (TextView) view.findViewById(R.id.roomsTextView);
//            no_of_rooms=(TextView) view.findViewById(R.id.noOfRoomsTextView);
//            pglocation = (TextView) view.findViewById(R.id.locationTextView);
//            no_of_beds=(TextView) view.findViewById(R.id.noOfBedsTextView);
//            available_for=(TextView) view.findViewById(R.id.availableTextView);
//            payableAmount=(TextView)view.findViewById(R.id.payableAmountTextView);
//            bookNowBtn=(Button)view.findViewById(R.id.booknowButton);
//            bookNowBtn.setOnClickListener(this);
                  pgadress=(TextView)view.findViewById(R.id.pglocation);
                  pgCity=(TextView)view.findViewById(R.id.pgcity);
                  gender=(TextView)view.findViewById(R.id.gender);
                  pgimage=(ImageView)view.findViewById(R.id.pgimage);
                  viewMore=(Button)view.findViewById(R.id.pgid);


        }

    }




    public PGAdapter(List<PG> pgList, Context context) {
        this.pgList=pgList;
        this.context=context;
    }


    @Override
    public PGAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pg_list, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("yy----","aaa gyaaaa");
//        PG mypg=pgList.get(position);
//        holder.no_of_rooms.setText(String.valueOf(mypg.getNo_of_rooms()));
//        holder.no_of_beds.setText(String.valueOf(mypg.getNo_of_beds()));
//        holder.available_for.setText(mypg.getAvailable_for());
//        holder.pglocation.setText(mypg.getLocation());
//        holder.payableAmount.setText("Rs. "+mypg.getAmount());
//
//        Glide.with(context)
//                .load("http://+WebServiceURL.IP+/RestServiceRegisterLogin/"+mypg.getImageurl())
//                .into(holder.image);

            final PG myPg=pgList.get(position);
            holder.pgadress.setText(myPg.getPgArea());
            holder.pgCity.setText(myPg.getLocation());
            holder.gender.setText(myPg.getAvailable_for());
        Glide.with(context).load("http://+WebServiceURL.IP+/RestServiceRegisterLogin/"+myPg.getImageurl()).into(holder.pgimage);

        Log.d("AA----"," : "+myPg);
        Log.d("AB----"," : "+myPg.getPgArea());
        Log.d("yy----",myPg.getPgname());
        Log.d("yy----",myPg.getLocation());
        Log.d("urllllllll",myPg.getImageurl());
        Log.d("Ava----"," : "+myPg.getAvailable_for());


        holder.viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent intent=new Intent(context,MoreDetailsAboutPG.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("pgdata",myPg);

                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);

                }
            });

    }


    @Override
    public int getItemCount() {
        return pgList.size();
    }


}
