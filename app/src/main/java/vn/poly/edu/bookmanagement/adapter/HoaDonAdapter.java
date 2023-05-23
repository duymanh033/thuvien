package vn.poly.edu.bookmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.model.HoaDon;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {
    Context context;
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();

    public HoaDonAdapter(Context context, ArrayList<HoaDon> listHoaDon) {
        this.context = context;
        this.listHoaDon = listHoaDon;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_hoadon, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, final int position) {
        HoaDon hoaDon = listHoaDon.get(position);
        holder.tvMaHoaDon.setText(hoaDon.getMaHoadon());
        holder.tvNgayMua.setText(hoaDon.getNgayMua());
    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }

    public static class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHoaDon;
        TextView tvNgayMua;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHoaDon = (TextView) itemView.findViewById(R.id.tvMaHoaDon);
            tvNgayMua = (TextView) itemView.findViewById(R.id.tvNgayMua);
        }
    }
}
