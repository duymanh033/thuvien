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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.dao.NguoiDungDAO;
import vn.poly.edu.bookmanagement.model.NguoiDung;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.NguoiDungViewHolder> {
    Context context;
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();

    public NguoiDungAdapter(Context context, ArrayList<NguoiDung> listNguoiDung) {
        this.context = context;
        this.listNguoiDung = listNguoiDung;
    }

    @NonNull
    @Override
    public NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_nguoidung, parent, false);
        return new NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungViewHolder holder, final int position) {
        final NguoiDung nguoiDung = listNguoiDung.get(position);
        holder.tvHoVaTen.setText(nguoiDung.getHoVaTen());
        holder.tvSoDienThoai.setText(nguoiDung.getPhone());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AwesomeSuccessDialog(context)
                        .setTitle("Bạn có chắc muốn xóa ?")
                        .setMessage("Dữ liệu sẽ không thể phục hồi.")
                        .setColoredCircle(R.color.red)
                        .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                        .setCancelable(true)
                        .setPositiveButtonText("OK")
                        .setPositiveButtonbackgroundColor(R.color.red)
                        .setPositiveButtonTextColor(R.color.white)
                        .setNegativeButtonText("Hủy")
                        .setNegativeButtonbackgroundColor(R.color.red)
                        .setNegativeButtonTextColor(R.color.white)
                        .setPositiveButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(context);
                                if (nguoiDungDAO.deleteNguoiDung(listNguoiDung.get(position).getUserName()) > 0){
                                    Toasty.success(context, "Xoá thành công!", Toast.LENGTH_LONG, true).show();
                                    listNguoiDung.clear();
                                    listNguoiDung.addAll(nguoiDungDAO.getAllNguoiDung());
                                    notifyDataSetChanged();
                                } else {
                                    Toasty.error(context, "Xoá thất bại!", Toast.LENGTH_LONG, true).show();
                                }
                            }
                        })
                        .setNegativeButtonClick(new Closure() {
                            @Override
                            public void exec() {
                                //click
                                notifyDataSetChanged();
                                Toasty.success(context, "Đã Hủy", Toast.LENGTH_LONG, true).show();
                            }
                        })
                        .show();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogEditNguoiDung(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNguoiDung.size();
    }

    private void openDialogEditNguoiDung(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inf = ((Activity) context).getLayoutInflater();
        View view1 = inf.inflate(R.layout.dialog_update_nguoidung, null);
        builder.setView(view1);
        Dialog dialog = builder.create();
        dialog.show();

        final EditText edtSoDientThoai = view1.findViewById(R.id.edtSoDienThoaiEdit);
        final EditText edtHoVaTen = view1.findViewById(R.id.edtHoVaTenEdit);
        Button btnEditNguoiDung = (Button) view1.findViewById(R.id.btnEditNguoiDung);
        Button btnCancelNguoiDung = (Button) view1.findViewById(R.id.btnCancelNguoiDung);

        final NguoiDung nguoiDung = listNguoiDung.get(position);
        edtHoVaTen.setText(nguoiDung.getHoVaTen());
        edtSoDientThoai.setText(nguoiDung.getPhone());

        btnEditNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String HoVaTen = edtHoVaTen.getText().toString().trim();
                String SoDienThoai = edtSoDientThoai.getText().toString().trim();

                if (SoDienThoai.equals("") || HoVaTen.equals("")) {
                    Toasty.error(context, "Lỗi!", Toast.LENGTH_SHORT, true).show();
                    edtHoVaTen.setText(HoVaTen);
                    edtSoDientThoai.setText(SoDienThoai);
                } else {
                    String index = nguoiDung.getUserName();
                    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(context);
                    NguoiDung nguoiDung = new NguoiDung(index, SoDienThoai, HoVaTen);
                    if (nguoiDungDAO.updateThongTinNguoiDung(nguoiDung) > 0){
                        Toasty.success(context, "Sửa thành công!", Toast.LENGTH_SHORT, true).show();
                        dialog.dismiss();
                        listNguoiDung.clear();
                        listNguoiDung.addAll(nguoiDungDAO.getAllNguoiDung());
                        notifyDataSetChanged();
                    } else {
                        Toasty.error(context, "Sửa thất bại!", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }
        });

        btnCancelNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toasty.success(context, "Đã hủy!", Toast.LENGTH_SHORT, true).show();
            }
        });
        dialog.show();
        //dialog
    }

    public static class NguoiDungViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEdit;
        ImageView imgDelete;
        ImageView imgNguoiDung;
        ImageView iconSwipe;
        TextView tvHoVaTen;
        TextView tvSoDienThoai;

        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgNguoiDung = (ImageView) itemView.findViewById(R.id.imgNguoiDung);
            tvHoVaTen = (TextView) itemView.findViewById(R.id.tvHoVaTen);
            tvSoDienThoai = (TextView) itemView.findViewById(R.id.tvSoDienThoai);
            iconSwipe = (ImageView) itemView.findViewById(R.id.icon_swipe);
        }
    }
}
