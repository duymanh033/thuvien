package vn.poly.edu.bookmanagement.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
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
import vn.poly.edu.bookmanagement.adapter.SachAdapter;
import vn.poly.edu.bookmanagement.adapter.SpinnerTheLoaiAdapter;
import vn.poly.edu.bookmanagement.adapter.TheLoaiAdapter;
import vn.poly.edu.bookmanagement.dao.SachDAO;
import vn.poly.edu.bookmanagement.dao.TheLoaiDAO;
import vn.poly.edu.bookmanagement.model.Sach;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class SachFragment extends Fragment {
    EditText edtSearchSach;
    RecyclerView rcvSach;
    FloatingActionButton fabAddSach;

    ArrayList<Sach> listSach = new ArrayList<>();
    SachDAO sachDAO;
    SachAdapter sachAdapter;

    ArrayList<TheLoai> listTheLoai = new ArrayList<>();
    TheLoaiDAO theLoaiDAO;

    ArrayList<Sach> filterListSach = new ArrayList<>();
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearchSach = (EditText) view.findViewById(R.id.edtSearchSach);
        rcvSach = (RecyclerView) view.findViewById(R.id.rcvSach);
        fabAddSach = (FloatingActionButton) view.findViewById(R.id.fabAddSach);
        
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvSach.setLayoutManager(layoutManager);

        sachDAO = new SachDAO(getContext());
        listSach = sachDAO.getAllSach();
        sachAdapter = new SachAdapter(getContext(), listSach);
        rcvSach.setAdapter(sachAdapter);
        
        fabAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddSach();
            }
        });

        edtSearchSach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filterListSach.clear();

                if (editable.toString().isEmpty()){
                    rcvSach.setAdapter(sachAdapter);
                    sachAdapter.notifyDataSetChanged();
                } else {
                    Filter(editable.toString());
                }

            }
        });
    }

    private void Filter(String text){
        for (Sach sach : listSach){
            if (sach.getTenSach().toLowerCase().contains(text.toLowerCase())){
                filterListSach.add(sach);
            }
        }
        rcvSach.setAdapter(new SachAdapter(getContext(), filterListSach));
        sachAdapter.notifyDataSetChanged();
    }

    private void openDialogAddSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sach, null);
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
        Button btnThemSach = (Button) view.findViewById(R.id.btnThemSach);
        Button btnCancelSach = (Button) view.findViewById(R.id.btnCancelSach);

        theLoaiDAO = new TheLoaiDAO(getContext());
        listTheLoai = theLoaiDAO.getAllTheLoai();
        SpinnerTheLoaiAdapter adapterSpinnerSach = new SpinnerTheLoaiAdapter(getContext(), listTheLoai);
        spLoaiSach.setAdapter(adapterSpinnerSach);

        btnThemSach.setOnClickListener(new View.OnClickListener() {
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

                if (MaSach.equals("") || TenSach.equals("") || TacGia.equals("") || NhaXuatBan.equals("") || GiaBia <= 0 || SoLuong <= 0) {
                    Toasty.error(getContext(), "Lỗi!", Toast.LENGTH_SHORT,true).show();
                    edtMaSach.setText(MaSach);
                    edtTenSach.setText(TenSach);
                    edtTacGia.setText(TacGia);
                    edtNhaXuatBan.setText(NhaXuatBan);
                    edtGiaBia.setText(String.valueOf(GiaBia));
                    edtSoLuong.setText(String.valueOf(SoLuong));
                } else {
                    Sach sach = new Sach(MaSach, maLoai, TenSach, TacGia, NhaXuatBan, GiaBia, SoLuong);
                    sachDAO.insertSach(sach);
                    Toasty.success(getContext(), "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                    dialog.dismiss();
                    listSach.clear();
                    listSach.addAll(sachDAO.getAllSach());
                    sachAdapter.notifyDataSetChanged();
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
