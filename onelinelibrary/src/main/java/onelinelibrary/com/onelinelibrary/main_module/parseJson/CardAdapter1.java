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
 * Created by Subham on 10/29/2016.
 */
public class CardAdapter1 extends RecyclerView.Adapter<CardAdapter1.ViewHolder> {

    List<ListItem> items;
    Integer v11;
    String[] b11;
    String tag1;
    Context c2;

    public CardAdapter1(ArrayList<String> a1,String[] b1,Integer v1,Context c1){
        super();
        items = new ArrayList<ListItem>();
        v11=v1;
        b11=b1;
        c2=c1;


        for(int i =0; i<a1.size(); i++){
            ListItem item = new ListItem();
            item.setp1(a1.get(i));

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
                    Toast.makeText(c2,"Clicked at position"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(b11[0].contains("image")){
            //holder.t1.setText(list.getp1());
//Picasso
            Picasso.with(c2).load(""+list.getp1()).into(holder.i1);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView t1;
        public ImageView i1;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);

            if(b11[0].contains("text")) {
                t1 = (TextView) itemView.findViewById(R.id.p1);
            }
            if(b11[0].contains("image")) {
                i1 = (ImageView) itemView.findViewById(R.id.p1);
            }

        }
    }
}