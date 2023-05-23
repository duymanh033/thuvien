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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.dao.TheLoaiDAO;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.TheLoaiViewHolder> {
    Context context;
    ArrayList<TheLoai> listTheLoai = new ArrayList<>();

    public TheLoaiAdapter(Context context, ArrayList<TheLoai> listTheLoai) {
        this.context = context;
        this.listTheLoai = listTheLoai;
    }

    @NonNull
    @Override
    public TheLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_theloai, parent, false);
        return new TheLoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiViewHolder holder, final int position) {
        TheLoai theLoai = listTheLoai.get(position);

        holder.tvMaTheLoai.setText(theLoai.getMaTheLoai());
        holder.tvTenTheLoai.setText(theLoai.getTenTheLoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận?");
                builder.setMessage("Bạn có chắc chắn muốn xoá không?");
                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(context);
                        theLoaiDAO.deleteTheLoai(listTheLoai.get(position).getMaTheLoai());
                        Toasty.success(context, "Xoá thành công!", Toast.LENGTH_LONG, true).show();
                        listTheLoai.clear();
                        listTheLoai.addAll(theLoaiDAO.getAllTheLoai());
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
                openDialogEditTheLoai(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTheLoai.size();
    }

    public static class TheLoaiViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEdit;
        ImageView imgDelete;
        TextView tvMaTheLoai;
        TextView tvTenTheLoai;

        public TheLoaiViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            tvMaTheLoai = (TextView) itemView.findViewById(R.id.tvMaTheLoai);
            tvTenTheLoai = (TextView) itemView.findViewById(R.id.tvTenTheLoai);
        }
    }

    private void openDialogEditTheLoai(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_theloai, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtMaTheLoai = (TextInputEditText) view.findViewById(R.id.edtMaTheLoai);
        TextInputEditText edtTenTheLoai = (TextInputEditText) view.findViewById(R.id.edtTenTheLoai);
        TextInputEditText edtViTri = (TextInputEditText) view.findViewById(R.id.edtViTri);
        TextInputEditText edtMoTa = (TextInputEditText) view.findViewById(R.id.edtMoTa);
        Button btnSuaTheLoai = (Button) view.findViewById(R.id.btnSuaTheLoai);
        Button btnCancelTheLoai = (Button) view.findViewById(R.id.btnCancelTheLoai);

        TheLoai theLoai = listTheLoai.get(position);
        edtMaTheLoai.setText(theLoai.getMaTheLoai());
        edtTenTheLoai.setText(theLoai.getTenTheLoai());
        edtViTri.setText(theLoai.getViTri() + "");
        edtMoTa.setText(theLoai.getMoTa());

        btnSuaTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maTheLoai = edtMaTheLoai.getText().toString().trim();
                String tenTheLoai = edtTenTheLoai.getText().toString().trim();

                int viTri = 0;
                try {
                    viTri = Integer.parseInt(edtViTri.getText().toString().trim());
                } catch (Exception e) {
                }

                String moTa = edtMoTa.getText().toString().trim();

                if (maTheLoai.equals("") || tenTheLoai.equals("") || viTri <= 0 || moTa.equals("")) {
                    Toasty.error(context, "Lỗi!", Toast.LENGTH_SHORT, true).show();
                } else {
                    TheLoaiDAO theLoaiDAO = new TheLoaiDAO(context);
                    TheLoai theLoai1 = new TheLoai(listTheLoai.get(position).getMaTheLoai(), tenTheLoai, moTa, viTri);
                    theLoaiDAO.updateTheLoai(theLoai1);
                    Toasty.success(context, "Hoàn tất!", Toast.LENGTH_SHORT, true).show();
                    listTheLoai.clear();
                    listTheLoai.addAll(theLoaiDAO.getAllTheLoai());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        btnCancelTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
