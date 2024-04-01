package com.example.chatapp_btl;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.example.chatapp_btl.DTO.NguoiDungDTO;
import com.example.chatapp_btl.DAO.NguoiDungDAO;
import com.example.chatapp_btl.Database.CreateDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import com.example.chatapp_btl.DatabaseHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogupActivity extends AppCompatActivity {
    private EditText maNDEdit, nameEdit,emailEdit, passEdit, phoneEdit;
    private Button btnLogup;

    private CreateDataBase createDB;
    private SQLiteDatabase database;
//    private FirebaseAuth mAuth;
    NguoiDungDAO nguoiDungDAO;

    private static String generateRandomCode(int length) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        // Khởi tạo DatabaseHelper và mở cơ sở dữ liệu
        createDB = new CreateDataBase(this);
        database = createDB.getWritableDatabase();

        // Ánh xạ các view
        nameEdit = findViewById(R.id.name);
        emailEdit = findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        phoneEdit = findViewById(R.id.phone);
        btnLogup=findViewById(R.id.btnLogup);

        // Xử lý sự kiện khi click vào nút Đăng ký
        btnLogup.setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String currentDate = dateFormat.format(new Date());
            @Override
            public void onClick(View v) {
                String randomCode = generateRandomCode(6);
                // Lấy thông tin từ các EditText
                String maND = currentDate + randomCode;
                String tenND = nameEdit.getText().toString();
                String taiKhoan = emailEdit.getText().toString();
                String pass = passEdit.getText().toString();
                String matKhau = hashPassword(pass);
                String soDienThoai = phoneEdit.getText().toString();

                // Tạo một đối tượng NguoiDungDTO mới
                NguoiDungDTO nguoiDungDTO = new NguoiDungDTO(maND, tenND, taiKhoan, matKhau, soDienThoai);

//                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);

                // Thực hiện thêm người dùng vào cơ sở dữ liệu
                long kiemTra = nguoiDungDAO.ThemNguoiDung(nguoiDungDTO);

                // Kiểm tra xem thêm người dùng có thành công không
                if (kiemTra != -1) {
                    // Nếu thành công, hiển thị thông báo và chuyển về màn hình đăng nhập
                    Toast.makeText(LogupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogupActivity.this, LoginActivity.class));
                    finish();
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    Toast.makeText(LogupActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void logup() {
//        String email, pass, phone;
//        email = emailEdit.getText().toString();
//        pass = passEdit.getText().toString();
//        phone = phoneEdit.getText().toString();
//
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Vui long nhap email!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(pass)) {
//            Toast.makeText(this, "Vui long nhap mat khau!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(phone)) {
//            Toast.makeText(this, "Vui long nhap so dien thoai!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//    }
}

