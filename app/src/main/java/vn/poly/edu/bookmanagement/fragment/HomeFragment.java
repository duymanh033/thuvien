package vn.poly.edu.bookmanagement.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import vn.poly.edu.bookmanagement.MainActivity;
import vn.poly.edu.bookmanagement.R;
import vn.poly.edu.bookmanagement.dao.NguoiDungDAO;
import vn.poly.edu.bookmanagement.dao.SachDAO;
import vn.poly.edu.bookmanagement.dao.TheLoaiDAO;
import vn.poly.edu.bookmanagement.model.NguoiDung;
import vn.poly.edu.bookmanagement.model.Sach;
import vn.poly.edu.bookmanagement.model.TheLoai;

public class HomeFragment extends Fragment {
    CardView cardThongKe;
    CardView cardUser;
    CardView cardTheLoai;
    CardView cardSach;
    CardView cardHoaDon;
    CardView cardSachBanChay;

    TextView tvCountUser;
    TextView tvCountBill;
    TextView tvCountTheLoai;
    TextView tvCountBook;

    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();

    TheLoaiDAO theLoaiDAO;
    ArrayList<TheLoai> listTheLoai = new ArrayList<>();

    SachDAO sachDAO;
    ArrayList<Sach> listSach = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardThongKe = (CardView) view.findViewById(R.id.cardThongKe);
        cardUser = (CardView) view.findViewById(R.id.cardUser);
        cardTheLoai = (CardView) view.findViewById(R.id.cardTheLoai);
        cardSach = (CardView) view.findViewById(R.id.cardSach);
        cardHoaDon = (CardView) view.findViewById(R.id.cardHoaDon);
        cardSachBanChay = (CardView) view.findViewById(R.id.cardSachBanChay);

        tvCountUser = (TextView) view.findViewById(R.id.tvCountUser);
        tvCountBill = (TextView) view.findViewById(R.id.tvCountBill);
        tvCountTheLoai = (TextView) view.findViewById(R.id.tvCountTheLoai);
        tvCountBook = (TextView) view.findViewById(R.id.tvCountBook);

        nguoiDungDAO = new NguoiDungDAO(getContext());
        listNguoiDung = nguoiDungDAO.getAllNguoiDung();
        tvCountUser.setText(listNguoiDung.size()+"");

        theLoaiDAO = new TheLoaiDAO(getContext());
        listTheLoai = theLoaiDAO.getAllTheLoai();
        tvCountTheLoai.setText(listTheLoai.size()+"");

        sachDAO = new SachDAO(getContext());
        listSach = sachDAO.getAllSach();
        tvCountBook.setText(listSach.size()+"");
//        MainActivity mainActivity = new MainActivity();
//        mainActivity.toolbar = view.findViewById(R.id.toolbar);
        cardThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setTitle("Thống kê");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framer, new ThongKeFragment()).commit();
            }
        });

        cardSachBanChay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setTitle("Top 10 sách bán chạy");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framer, new TopSachFragment()).commit();
            }
        });
    }
}
