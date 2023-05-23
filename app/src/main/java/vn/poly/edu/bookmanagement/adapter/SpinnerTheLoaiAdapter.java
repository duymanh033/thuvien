package vn.poly.edu.bookmanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class SpinnerTheLoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<TheLoai> list;

    public SpinnerTheLoaiAdapter(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        view = layoutInflater.inflate(R.layout.item_spn_theloai, null);

        TextView tvMaTheLoaiTenTheLoai = view.findViewById(R.id.tvMaTheLoaiTenTheLoai);

        TheLoai theLoai = list.get(i);

        tvMaTheLoaiTenTheLoai.setText(theLoai.getMaTheLoai() + " | " + theLoai.getTenTheLoai());

        return view;
    }
}
