package vn.poly.edu.bookmanagement.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.adapter.NguoiDungAdapter;
import vn.poly.edu.bookmanagement.dao.NguoiDungDAO;
import vn.poly.edu.bookmanagement.model.NguoiDung;

public class NguoiDungFragment extends Fragment {
    RecyclerView rcvUser;
    FloatingActionButton fabAdd;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();
    NguoiDungAdapter nguoiDungAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvUser = (RecyclerView) view.findViewById(R.id.rcvUser);
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fabAdd);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvUser.setLayoutManager(layoutManager);

        nguoiDungDAO = new NguoiDungDAO(getContext());
        listNguoiDung = nguoiDungDAO.getAllNguoiDung();
        nguoiDungAdapter = new NguoiDungAdapter(getContext(), listNguoiDung);
        rcvUser.setAdapter(nguoiDungAdapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddNguoiDung();
            }
        });

    }

    private void openDialogAddNguoiDung() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_nguoidung, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtTenDangNhap = (TextInputEditText) view.findViewById(R.id.edtTenDangNhap);
        TextInputEditText edtMatKhau = (TextInputEditText) view.findViewById(R.id.edtMatKhau);
        TextInputEditText edtMatKhauXacNhan = (TextInputEditText) view.findViewById(R.id.edtMatKhauXacNhan);
        TextInputEditText edtSoDienThoai = (TextInputEditText) view.findViewById(R.id.edtSoDienThoai);
        TextInputEditText edtHoVaTen = (TextInputEditText) view.findViewById(R.id.edtHoVaTen);
        Button btnThemNguoiDung = (Button) view.findViewById(R.id.btnThemNguoiDung);
        Button btnCancelNguoiDung = (Button) view.findViewById(R.id.btnCancelNguoiDung);

        btnThemNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhap = edtTenDangNhap.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                String matKhauXacNhan = edtMatKhauXacNhan.getText().toString().trim();
                String soDienThoai = edtSoDienThoai.getText().toString().trim();
                String hoVaTen = edtHoVaTen.getText().toString().trim();

                boolean kt = false;
                // kiểm tra trùng username
                for (NguoiDung nguoiDung : listNguoiDung) {
                    if (tenDangNhap.equals(nguoiDung.getUserName())) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }
                NguoiDung nguoiDung = new NguoiDung(tenDangNhap, matKhau, soDienThoai, hoVaTen);
                if (kt || tenDangNhap.equals("") || !matKhau.equals(matKhauXacNhan)
                        || soDienThoai.equals("") || hoVaTen.equals("")) {
                    Toasty.error(getContext(), "Lỗi!", Toast.LENGTH_SHORT, true).show();
                    edtTenDangNhap.setText(tenDangNhap);
                    edtMatKhau.setText(matKhau);
                    edtMatKhauXacNhan.setText(matKhauXacNhan);
                    edtSoDienThoai.setText(soDienThoai);
                    edtHoVaTen.setText(hoVaTen);
                } else {
                    if (nguoiDungDAO.insertNguoiDung(nguoiDung) > 0){
                        Toasty.success(getContext(), "Đã thêm!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        listNguoiDung.clear();
                        listNguoiDung.addAll(nguoiDungDAO.getAllNguoiDung());
                        nguoiDungAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btnCancelNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toasty.success(getContext(), "Đã hủy", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        nguoiDungDAO = new NguoiDungDAO(getActivity());
        listNguoiDung = nguoiDungDAO.getAllNguoiDung();
        nguoiDungAdapter = new NguoiDungAdapter(getActivity(), listNguoiDung);
        rcvUser.setAdapter(nguoiDungAdapter);
    }
}
