package com.example.bookingticket.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingticket.Domain.TicketDetail;
import com.example.bookingticket.R;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<TicketDetail> ticketList;

    public TicketAdapter(List<TicketDetail> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketDetail ticket = ticketList.get(position);
        holder.fullnameTextView.setText("Họ tên: " + ticket.getFullname());
        holder.filmName.setText("Tên phim: " + ticket.getTitle());
        holder.dateTimeTextView.setText("Ngày xem: " + ticket.getDateTime());
        holder.seatTextView.setText("Số ghế: " + ticket.getSeat());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView fullnameTextView;
        TextView filmName;
        TextView dateTimeTextView;
        TextView seatTextView;

        TicketViewHolder(View itemView) {
            super(itemView);
            fullnameTextView = itemView.findViewById(R.id.fullnameTextView);
            filmName = itemView.findViewById(R.id.filmName);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            seatTextView = itemView.findViewById(R.id.seatTextView);
        }
    }
}
