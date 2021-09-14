package cits.application.vendor.atgsample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class recycler extends RecyclerView.Adapter<recycler.recyclerholder> {
    private List<senddata> list;
    private Context context;
    public recycler(List<senddata> list, Context context ) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public recyclerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlist, parent,false);
        return new recyclerholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final recyclerholder holder, int position) {
        final senddata currentitem =  list.get(position);
        try {
            URL urls = new URL(currentitem.getImageurl());
            Glide.with(context).load(urls).into(holder.imageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class recyclerholder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        public recyclerholder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardshow);
            imageView = itemView.findViewById(R.id.flagshow);
        }
    }
}
