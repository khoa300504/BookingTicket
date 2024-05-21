package com.example.bookingticket.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingticket.Activities.DetailActivity;
import com.example.bookingticket.Domain.FilmItem;
import com.example.bookingticket.R;

import java.util.List;

public class FilmSearchAdapter extends RecyclerView.Adapter<FilmSearchAdapter.ViewHolder> {
    private List<FilmItem> filmCollection;

    public FilmSearchAdapter(List<FilmItem> filmCollection) {
        this.filmCollection = filmCollection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (filmCollection == null || filmCollection.size() <= position) {
            return; // Xử lý trường hợp dữ liệu không hợp lệ
        }

        FilmItem filmItem = filmCollection.get(position);
        holder.titleTxt.setText(filmItem.getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(holder.itemView.getContext())
                .load(filmItem.getPoster())
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("id", filmItem.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (filmCollection != null) {
            return filmCollection.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
