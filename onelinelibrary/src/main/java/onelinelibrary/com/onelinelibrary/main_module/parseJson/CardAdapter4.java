package onelinelibrary.com.onelinelibrary.main_module.parseJson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import onelinelibrary.com.onelinelibrary.R;

/**
 * Created by Belal on 10/29/2015.
 */
public class CardAdapter4 extends RecyclerView.Adapter<CardAdapter4.ViewHolder> {

    List<ListItem> items;
    Integer v11;
    String[] b11;
    String tag1;
    Context c2;

    public CardAdapter4(ArrayList<String> a1, ArrayList<String> a2, ArrayList<String> a3,ArrayList<String> a4, String[] b1, Integer v12,Context c1){
        super();
        items = new ArrayList<ListItem>();
        v11=v12;
        b11=b1;
        c2=c1;


        for(int i =0; i<a1.size(); i++){
            ListItem item = new ListItem();
            item.setp1(a1.get(i));
            item.setp2(a2.get(i));
            item.setp3(a3.get(i));
            item.setp4(a4.get(i));

            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(v11, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ListItem list =  items.get(position);
        if(b11[0].contains("text")) {
            holder.t1.setText(list.getp1());
            holder.t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c2, "Clicked at position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(b11[0].contains("image")){
            Picasso.with(c2).load(""+list.getp1()).into(holder.i1);
        }


        if(b11[1].contains("text")) {
            holder.t2.setText(list.getp2());
            holder.t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c2, "Clicked at position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (b11[1].contains("image")){
            Picasso.with(c2).load(""+list.getp2()).into(holder.i2);
        }
        if(b11[2].contains("text")) {
            holder.t3.setText(list.getp3());
            holder.t3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c2, "Clicked at position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(b11[2].contains("image")){
            Picasso.with(c2).load(""+list.getp3()).into(holder.i3);
        }

        if(b11[3].contains("text")) {
            holder.t4.setText(list.getp4());
            holder.t4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c2, "Clicked at position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(b11[3].contains("image")){
            Picasso.with(c2).load(""+list.getp4()).into(holder.i4);
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView t1,t2,t3,t4;
        public ImageView i1,i2,i3,i4;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);

            if(b11[0].contains("text")) {
                t1 = (TextView) itemView.findViewById(R.id.p1);
            }
            if(b11[0].contains("image")) {
                i1 = (ImageView) itemView.findViewById(R.id.p1);
            }

            if(b11[1].contains("text")) {
                t2 = (TextView) itemView.findViewById(R.id.p2);
            }
            if(b11[1].contains("image")) {
                i2 = (ImageView) itemView.findViewById(R.id.p2);
            }

            if(b11[2].contains("text")) {
                t3 = (TextView) itemView.findViewById(R.id.p3);
            }
            if(b11[2].contains("image")) {
                i3 = (ImageView) itemView.findViewById(R.id.p3);
            }
            if(b11[3].contains("text")) {
                t4 = (TextView) itemView.findViewById(R.id.p4);
            }
            if(b11[3].contains("image")) {
                i4 = (ImageView) itemView.findViewById(R.id.p4);
            }



        }
    }
}