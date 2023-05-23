package vn.poly.edu.bookmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.dao.NguoiDungDAO;
import vn.poly.edu.bookmanagement.fragment.HoaDonFragment;
import vn.poly.edu.bookmanagement.fragment.HomeFragment;
import vn.poly.edu.bookmanagement.fragment.NguoiDungFragment;
import vn.poly.edu.bookmanagement.fragment.SachFragment;
import vn.poly.edu.bookmanagement.fragment.TheLoaiFragment;
import vn.poly.edu.bookmanagement.fragment.ThongKeFragment;
import vn.poly.edu.bookmanagement.fragment.TopSachFragment;
import vn.poly.edu.bookmanagement.model.NguoiDung;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;
    TextView tvName, tvPhone;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_THE_LOAI = 1;
    private static final int FRAGMENT_SACH = 2;
    private static final int FRAGMENT_HOA_DON = 3;
    private static final int FRAGMENT_TOP_SACH = 4;
    private static final int FRAGMENT_THONG_KE = 5;
    private static final int FRAGMENT_NGUOI_DUNG = 6;

    private int mCurrentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvPhone = navigationView.getHeaderView(0).findViewById(R.id.tvPhone);
        nguoiDungDAO = new NguoiDungDAO(this);
        listNguoiDung = new ArrayList<>();
        listNguoiDung = nguoiDungDAO.getAllNguoiDung();
        for (NguoiDung nguoiDung : listNguoiDung) {
            tvName.setText(nguoiDung.getHoVaTen());
            tvPhone.setText(nguoiDung.getPhone());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (mCurrentFragment != FRAGMENT_HOME) {
                    toolbar.setTitle("Home");
                    replaceFragment(new HomeFragment());
                    mCurrentFragment = FRAGMENT_HOME;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(false);
                }
                break;
            case R.id.nav_the_loai:
                if (mCurrentFragment != FRAGMENT_THE_LOAI) {
                    toolbar.setTitle("Thể loại");
                    replaceFragment(new TheLoaiFragment());
                    mCurrentFragment = FRAGMENT_THE_LOAI;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(false);
                }
                break;
            case R.id.nav_sach:
                if (mCurrentFragment != FRAGMENT_SACH) {
                    toolbar.setTitle("Sách");
                    replaceFragment(new SachFragment());
                    mCurrentFragment = FRAGMENT_SACH;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(false);
                }
                break;
            case R.id.nav_hoa_don:
                if (mCurrentFragment != FRAGMENT_HOA_DON) {
                    toolbar.setTitle("Hoá đơn");
                    replaceFragment(new HoaDonFragment());
                    mCurrentFragment = FRAGMENT_HOA_DON;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(false);
                }
                break;
            case R.id.nav_sach_ban_chay:
                if (mCurrentFragment != FRAGMENT_TOP_SACH) {
                    toolbar.setTitle("Top 10 sách bán chạy");
                    replaceFragment(new TopSachFragment());
                    mCurrentFragment = FRAGMENT_TOP_SACH;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(false);
                }
                break;
            case R.id.nav_thong_ke:
                if (mCurrentFragment != FRAGMENT_THONG_KE) {
                    toolbar.setTitle("Thống kê");
                    replaceFragment(new ThongKeFragment());
                    mCurrentFragment = FRAGMENT_THONG_KE;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(true);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(false);
                }
                break;
            case R.id.nav_user:
                if (mCurrentFragment != FRAGMENT_NGUOI_DUNG) {
                    toolbar.setTitle("Người dùng");
                    replaceFragment(new NguoiDungFragment());
                    mCurrentFragment = FRAGMENT_NGUOI_DUNG;
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_the_loai).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_hoa_don).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_sach_ban_chay).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_thong_ke).setChecked(false);
                    navigationView.getMenu().findItem(R.id.nav_user).setChecked(true);
                }
                break;
            case R.id.nav_change_pass:
                openDialogChangePass();
                break;
            case R.id.nav_logout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openDialogChangePass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_change_pass, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText edtTenDangNhapDoiMk = (TextInputEditText) view.findViewById(R.id.edtTenDangNhapDoiMk);
        TextInputEditText edtMatKhauCu = (TextInputEditText) view.findViewById(R.id.edtMatKhauCu);
        TextInputEditText edtMatKhauMoi = (TextInputEditText) view.findViewById(R.id.edtMatKhauMoi);
        TextInputEditText edtXacNhanMatKhauMoi = (TextInputEditText) view.findViewById(R.id.edtXacNhanMatKhauMoi);
        Button btnXacNhanDoiMk = (Button) view.findViewById(R.id.btnXacNhanDoiMk);
        Button btnCancelDoiMk = (Button) view.findViewById(R.id.btnCancelDoiMk);

        btnXacNhanDoiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhapDoiMK = edtTenDangNhapDoiMk.getText().toString().trim();
                String mkCu = edtMatKhauCu.getText().toString().trim();
                String mkMoi = edtMatKhauMoi.getText().toString().trim();
                String mkXacNhan = edtXacNhanMatKhauMoi.getText().toString().trim();

                boolean kt = false;
                for (int i = 0; i < listNguoiDung.size(); i++) {
                    if (tenDangNhapDoiMK.equals(listNguoiDung.get(i).getUserName()) && mkCu.equals(listNguoiDung.get(i).getPassWord())) {
                        kt = true;
                        break;
                    } else {
                        kt = false;
                    }
                }

                if (kt && !mkCu.equals(mkMoi) && mkMoi.equals(mkXacNhan)) {
                    NguoiDung nguoiDung = new NguoiDung(tenDangNhapDoiMK, mkXacNhan);
                    nguoiDungDAO.updateMatKhauNguoiDung(nguoiDung);
                    Toasty.success(MainActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_LONG, true).show();
                    dialog.dismiss();
                } else {
                    edtTenDangNhapDoiMk.setText(tenDangNhapDoiMK);
                    edtMatKhauCu.setText(mkCu);
                    edtMatKhauMoi.setText(mkMoi);
                    edtXacNhanMatKhauMoi.setText(mkXacNhan);
                    Toasty.error(MainActivity.this, "Lỗi đổi mật khẩu!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancelDoiMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framer, fragment);
        transaction.commit();
    }
}