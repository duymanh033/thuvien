package vn.poly.edu.bookmanagement.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.dao.SachDAO;
import vn.poly.edu.bookmanagement.dao.TheLoaiDAO;
import vn.poly.edu.bookmanagement.model.Sach;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    Context context;
    ArrayList<Sach> listSach = new ArrayList<>();

    ArrayList<TheLoai> listTheLoai = new ArrayList<>();

    public SachAdapter(Context context, ArrayList<Sach> listSach) {
        this.context = context;
        this.listSach = listSach;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, final int position) {
        holder.tvMaSach.setText("Mã sách: " + listSach.get(position).getMaSach());
        holder.tvTenSach.setText("Tên sách: " + listSach.get(position).getTenSach());
        holder.tvSoLuong.setText("Số lượng: " + listSach.get(position).getSoLuong());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận?");
                builder.setMessage("Bạn có chắc chắn muốn xoá không?");
                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SachDAO sachDAO = new SachDAO(context);
                        sachDAO.deleteSach(listSach.get(position).getMaSach());
                        Toasty.success(context, "Xoá thành công!", Toast.LENGTH_LONG, true).show();
                        listSach.clear();
                        listSach.addAll(sachDAO.getAllSach());
                        notifyDataSetChanged();
                    }
                });
                builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogEditSach(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSach.size();
    }

    public static class SachViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEdit;
        ImageView imgDelete;
        TextView tvMaSach;
        TextView tvTenSach;
        TextView tvSoLuong;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            tvMaSach = (TextView) itemView.findViewById(R.id.tvMaSach);
            tvTenSach = (TextView) itemView.findViewById(R.id.tvTenSach);
            tvSoLuong = (TextView) itemView.findViewById(R.id.tvSoLuong);
        }
    }

    private void openDialogEditSach(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spLoaiSach = (Spinner) view.findViewById(R.id.spLoaiSach);
        TextInputEditText edtMaSach = (TextInputEditText) view.findViewById(R.id.edtMaSach);
        TextInputEditText  edtTenSach = (TextInputEditText) view.findViewById(R.id.editTenSach);
        TextInputEditText edtTacGia = (TextInputEditText) view.findViewById(R.id.edtTacGia);
        TextInputEditText edtNhaXuatBan = (TextInputEditText) view.findViewById(R.id.edtNhaXuatBan);
        TextInputEditText edtGiaBia = (TextInputEditText) view.findViewById(R.id.edtGiaBia);
        TextInputEditText edtSoLuong = (TextInputEditText) view.findViewById(R.id.edtSoLuong);
        Button btnSuaSach = (Button) view.findViewById(R.id.btnSuaSach);
        Button btnCancelSach = (Button) view.findViewById(R.id.btnCancelSach);

        //spinner
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(((Activity) context));
        listTheLoai = theLoaiDAO.getAllTheLoai();
        SpinnerTheLoaiAdapter spinnerTheLoaiAdapter = new SpinnerTheLoaiAdapter(context, listTheLoai);
        spLoaiSach.setAdapter(spinnerTheLoaiAdapter);

        //spinner selection
        int IndexSpinner = 0;
        for (int i = 0; i <= listTheLoai.size() - 1; i++) {
            if (listSach.get(position).getMaTheLoai().equalsIgnoreCase(listTheLoai.get(i).getMaTheLoai())) {
                IndexSpinner = i;
            }
        }
        spLoaiSach.setSelection(IndexSpinner);

        Sach sach = listSach.get(position);
        edtMaSach.setText(sach.getMaSach());
        edtTenSach.setText(sach.getTenSach());
        edtTacGia.setText(sach.getTacGia());
        edtNhaXuatBan.setText(sach.getNXB());
        edtGiaBia.setText(String.valueOf(sach.getGiaBia()));
        edtSoLuong.setText(String.valueOf(sach.getSoLuong()));

        btnSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLoai = "";
                try {
                    int index = spLoaiSach.getSelectedItemPosition();
                    TheLoai theLoai = listTheLoai.get(index);
                    maLoai = theLoai.getMaTheLoai();
                } catch (Exception e) {
                }

                String MaSach = edtMaSach.getText().toString().trim();
                String TenSach = edtTenSach.getText().toString().trim();
                String TacGia = edtTacGia.getText().toString().trim();
                String NhaXuatBan = edtNhaXuatBan.getText().toString().trim();
                double GiaBia = 0;
                int SoLuong = 0;
                try {
                    GiaBia = Double.parseDouble(edtGiaBia.getText().toString().trim());
                    SoLuong = Integer.parseInt(edtSoLuong.getText().toString().trim());
                } catch (Exception e) {
                }

                if (TenSach.equals("") || TacGia.equals("") || NhaXuatBan.equals("") || GiaBia <= 0 || SoLuong <= 0) {
                    Toasty.error(context, "Lỗi!", Toast.LENGTH_SHORT, true).show();

                } else {
                    SachDAO sachDAO = new SachDAO(context);
                    Sach sach1 = new Sach(listSach.get(position).getMaSach(), maLoai, TenSach, TacGia, NhaXuatBan, GiaBia, SoLuong);
                    sachDAO.updateSach(sach1);
                    Toasty.success(context, "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();
                    listSach.clear();
                    listSach.addAll(sachDAO.getAllSach());
                    notifyDataSetChanged();
                }
            }
        });

        btnCancelSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
