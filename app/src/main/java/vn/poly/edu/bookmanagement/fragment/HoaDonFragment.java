package vn.poly.edu.bookmanagement.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import vn.poly.edu.bookmanagement.adapter.HoaDonAdapter;
import vn.poly.edu.bookmanagement.dao.HoaDonDAO;
import vn.poly.edu.bookmanagement.dao.SachDAO;
import vn.poly.edu.bookmanagement.model.HoaDon;
import vn.poly.edu.bookmanagement.model.Sach;

public class HoaDonFragment extends Fragment {
    EditText edtSearchHoaDon;
    RecyclerView rcvHoaDon;
    FloatingActionButton fabAddHoaDon;

    HoaDonDAO hoaDonDAO;
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    HoaDonAdapter hoaDonAdapter;
    String maHoaDon;

    private Integer selectedYear, selectedMonth, selectedDayOfMonth;
    String getDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearchHoaDon = (EditText) view.findViewById(R.id.edtSearchHoaDon);
        rcvHoaDon = (RecyclerView) view.findViewById(R.id.rcvHoaDon);
        fabAddHoaDon = (FloatingActionButton) view.findViewById(R.id.fabAddHoaDon);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvHoaDon.setLayoutManager(layoutManager);

        hoaDonDAO = new HoaDonDAO(getContext());
        listHoaDon = hoaDonDAO.getAllHoaDon();
        hoaDonAdapter = new HoaDonAdapter(getContext(), listHoaDon);
        rcvHoaDon.setAdapter(hoaDonAdapter);

        fabAddHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAddHoaDon();
            }
        });
    }

    private void openDialogAddHoaDon() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf = getLayoutInflater();
        View view = inf.inflate(R.layout.dialog_add_hoadon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtMaHoaDon = (TextInputEditText) view.findViewById(R.id.edtMaHoaDon);
        TextView tvChonNgay = (TextView) view.findViewById(R.id.tvChonNgay);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        Button btnThemHoaDon = (Button) view.findViewById(R.id.btnThemHoaDon);
        Button btnCancelHoaDon = (Button) view.findViewById(R.id.btnCancelHoaDon);

        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedDayOfMonth = datePicker.getDayOfMonth();
                selectedMonth = datePicker.getMonth() + 1;
                selectedYear = datePicker.getYear();
                getDate = selectedDayOfMonth + "/" + selectedMonth + "/" + selectedYear;

                maHoaDon = edtMaHoaDon.getText().toString().trim();

                // ktra trùng mã
                boolean kt = false;
                for (HoaDon hoaDon : listHoaDon) {
                    if (hoaDon.getMaHoadon().equals(maHoaDon)) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }
                // ktra trùng mã

                if (maHoaDon.equals("") || kt == true) {
                    Toasty.error(getContext(), "Lỗi!", Toast.LENGTH_SHORT, true).show();
                } else {
                    HoaDon hoaDon = new HoaDon(maHoaDon, getDate);
                    hoaDonDAO.insertHoaDon(hoaDon);
                    //TastyToast.makeText(getContext(), "Đ !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    openDialogAddHoaDonChiTiet();
                    Toasty.success(getContext(), "Đã thêm!", Toast.LENGTH_SHORT, true).show();
                    listHoaDon.clear();
                    listHoaDon.addAll(hoaDonDAO.getAllHoaDon());
                    hoaDonAdapter.notifyDataSetChanged();
                }
            }
        });

        btnCancelHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void openDialogAddHoaDonChiTiet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_hoadonchitiet, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtMaHoaDon2 = (TextInputEditText) view.findViewById(R.id.edtMaHoaDon);
        AutoCompleteTextView edtMaSach = (AutoCompleteTextView) view.findViewById(R.id.edtMaSach);
        TextInputEditText edtSoLuong = (TextInputEditText) view.findViewById(R.id.edtSoLuong);
        Button btnThemHoaDon = (Button) view.findViewById(R.id.btnThemHoaDon);

        // lấy mã sách
        ArrayList<Sach> listSach = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(getContext());
        listSach = sachDAO.getAllSach();
        ArrayList<String> listMS = new ArrayList<String>();
        for (Sach sach : listSach) {
            listMS.add(sach.getMaSach());
        }
        // lấy mã sách

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listMS);
        edtMaSach.setAdapter(adapter);

        edtMaHoaDon2.setText(maHoaDon);

        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
