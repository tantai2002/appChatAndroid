package com.example.chatapp_btl;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.chatapp_btl.DAO.NguoiDungDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity{

    private EditText emailEdit, passEdit;
    private Button btnLogin, btnLogup;
    private FirebaseAuth mAuth;

    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();

        emailEdit=findViewById(R.id.email);
        passEdit=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogup=findViewById(R.id.btnLogup);
        nguoiDungDAO = new NguoiDungDAO(this);
//        btnLogin.setVisibility(View.GONE);

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });
//        btnLogup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                logup();
//            }
//        });
    }

    private void HienThiCacButton(){
        boolean kiemtra = nguoiDungDAO.KiemTraNguoiDung();
        if(kiemtra){
            btnLogin.setVisibility(View.GONE);
            btnLogup.setVisibility(View.VISIBLE);
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            btnLogup.setVisibility(View.GONE);
        }
    }


    private void logup(){
        Intent i = new Intent(LoginActivity.this,LogupActivity.class);
        startActivity(i);
    }

    public static String hashPassword(String password) {
        try {
            // Tạo một đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Áp dụng thuật toán băm vào mật khẩu
            byte[] hash = digest.digest(password.getBytes());

            // Chuyển đổi byte array thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý nếu thuật toán băm không tồn tại
            e.printStackTrace();
            return null;
        }
    }

    private void login(){
        String taiKhoan = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String matKhau = hashPassword(pass);
        boolean kiemtra = nguoiDungDAO.kiemTraDangNhap(taiKhoan,matKhau);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiCacButton();
    }

    //    @Override
//    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle saveInstanceState){
//
//    }
}
