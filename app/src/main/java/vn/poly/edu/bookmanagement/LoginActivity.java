package vn.poly.edu.bookmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vn.poly.edu.bookmanagement.dao.NguoiDungDAO;
import vn.poly.edu.bookmanagement.model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout tilUsername;
    TextInputEditText edtUsername;
    TextInputLayout tilPassword;
    TextInputEditText edtPassword;
    Button btnLogin;
    CheckBox chkRememberPass;

    private SharedPreferences sharedPreferences;

    private NguoiDungDAO nguoiDungDAO;
    private ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        edtUsername = (TextInputEditText) findViewById(R.id.edtUsername);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        edtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);

        nguoiDungDAO = new NguoiDungDAO(this);
        listNguoiDung = nguoiDungDAO.getAllNguoiDung();

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        try {
            edtUsername.setText(sharedPreferences.getString("tenDangNhap", ""));
            edtPassword.setText(sharedPreferences.getString("matKhau", ""));
            chkRememberPass.setChecked(sharedPreferences.getBoolean("kiemTra", false));
        } catch (Exception e) {
            Log.e("this is problem", "-->" + e.toString());
        }

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evenDangNhap();
            }
        });
    }

    private void evenDangNhap() {
        String tenDangNhap = edtUsername.getText().toString().trim();
        String matKhau = edtPassword.getText().toString().trim();
        boolean kt = false;
        for (NguoiDung nguoiDung : listNguoiDung) {
            if (tenDangNhap.equals(nguoiDung.getUserName()) && matKhau.equals(nguoiDung.getPassWord())) {
                kt = true;
                break;
            } else {
                kt = false;
            }
        }
        if (kt) {
            if (chkRememberPass.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tenDangNhap", tenDangNhap);
                editor.putString("matKhau", matKhau);
                editor.putBoolean("kiemTra", true);
                editor.commit();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("tenDangNhap");
                editor.remove("matKhau");
                editor.remove("kiemTra");
                editor.commit();
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toasty.success(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT, true).show();

        } else if (tenDangNhap.equals("Admin") && matKhau.equals("123")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toasty.success(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.error(LoginActivity.this, "Lỗi!", Toast.LENGTH_SHORT, true).show();
            edtUsername.setText("");
            edtPassword.setText("");
        }
    }

//    private boolean validateUsername(){
//        String username = edtUsername.getText().toString().trim();
//        if (username.isEmpty()){
//            tilUsername.setError("Không để trống username");
//            return false;
//        } else {
//            tilUsername.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validatePassword(){
//        String password = edtPassword.getText().toString().trim();
//        if (password.isEmpty()){
//            tilPassword.setError("Không để trống password");
//            return false;
//        } else {
//            tilPassword.setError(null);
//            return true;
//        }
//    }
}