package vn.poly.edu.bookmanagement.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.adapter.NguoiDungAdapter;
import vn.poly.edu.bookmanagement.adapter.TheLoaiAdapter;
import vn.poly.edu.bookmanagement.dao.NguoiDungDAO;
import vn.poly.edu.bookmanagement.dao.TheLoaiDAO;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class TheLoaiFragment extends Fragment {
    EditText edtSearchTheLoai;
    RecyclerView rcvTheLoai;
    FloatingActionButton fabAddTheLoai;

    TheLoaiDAO theLoaiDAO;
    ArrayList<TheLoai> listTheLoai = new ArrayList<>();
    TheLoaiAdapter adapter;

    ArrayList<TheLoai> filterListTheLoai = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_the_loai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSearchTheLoai = (EditText) view.findViewById(R.id.edtSearchTheLoai);
        rcvTheLoai = (RecyclerView) view.findViewById(R.id.rcvTheLoai);
        fabAddTheLoai = (FloatingActionButton) view.findViewById(R.id.fabAddTheLoai);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvTheLoai.setLayoutManager(layoutManager);

        theLoaiDAO = new TheLoaiDAO(getContext());
        listTheLoai = theLoaiDAO.getAllTheLoai();
        adapter = new TheLoaiAdapter(getContext(), listTheLoai);
        rcvTheLoai.setAdapter(adapter);

        fabAddTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddTheLoai();
            }
        });

        edtSearchTheLoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filterListTheLoai.clear();

                if (editable.toString().isEmpty()){
                    rcvTheLoai.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Filter(editable.toString());
                }

            }
        });
    }

    private void Filter(String text){
        for (TheLoai theLoai : listTheLoai){
            if (theLoai.getTenTheLoai().toLowerCase().contains(text.toLowerCase())){
                filterListTheLoai.add(theLoai);
            }
        }
        rcvTheLoai.setAdapter(new TheLoaiAdapter(getContext(), filterListTheLoai));
        adapter.notifyDataSetChanged();
    }

    private void openDialogAddTheLoai() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_theloai, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtMaTheLoai = (TextInputEditText) view.findViewById(R.id.edtMaTheLoai);
        TextInputEditText edtTenTheLoai = (TextInputEditText) view.findViewById(R.id.edtTenTheLoai);
        TextInputEditText edtViTri = (TextInputEditText) view.findViewById(R.id.edtViTri);
        TextInputEditText edtMoTa = (TextInputEditText) view.findViewById(R.id.edtMoTa);
        Button btnThemTheLoai = (Button) view.findViewById(R.id.btnThemTheLoai);
        Button btnCancelTheLoai = (Button) view.findViewById(R.id.btnCancelTheLoai);

        btnThemTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MaTheLoai = edtMaTheLoai.getText().toString().trim();
                String TenTheLoai = edtTenTheLoai.getText().toString().trim();
                int ViTri = 0;
                try {
                    ViTri = Integer.parseInt(edtViTri.getText().toString().trim());
                } catch (Exception e) {
                }
                String MoTa = edtMoTa.getText().toString().trim();

                if (MaTheLoai.equals("") || TenTheLoai.equals("") || ViTri <= 0 || MoTa.equals("")) {
                    edtMaTheLoai.setText(MaTheLoai);
                    edtTenTheLoai.setText(TenTheLoai);
                    edtViTri.setText(String.valueOf(ViTri));
                    edtMoTa.setText(MoTa);

                    Toasty.error(getContext(), "Lỗi!", Toast.LENGTH_SHORT,true).show();

                } else {
                    TheLoai theLoai = new TheLoai(MaTheLoai, TenTheLoai, MoTa, ViTri);
                    theLoaiDAO.insertTheLoai(theLoai);
                    Toasty.success(getContext(), "Đã thêm", Toast.LENGTH_SHORT,true).show();
                    dialog.dismiss();
                    listTheLoai.clear();
                    listTheLoai.addAll(theLoaiDAO.getAllTheLoai());
                    adapter.notifyDataSetChanged();
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
