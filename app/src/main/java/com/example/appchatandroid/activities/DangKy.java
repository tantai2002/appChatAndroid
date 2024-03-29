package com.example.appchatandroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
 import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appchatandroid.R;
import com.example.appchatandroid.databinding.ActivityDangKyBinding;
import com.example.appchatandroid.utilities.Constants;
import com.example.appchatandroid.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Pattern;
import androidx.activity.result.ActivityResultLauncher;



public class DangKy extends AppCompatActivity {
    private ActivityDangKyBinding binding;
    private PreferenceManager preferenceManager;
    String   encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangKyBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();

    }
    private void setListeners(){
        binding.txtSignIn.setOnClickListener( v-> getOnBackPressedDispatcher().onBackPressed());
        binding.btnSignup.setOnClickListener(v -> {
            if(isValidSignUpDetails()){
                signUp();
            }
        });
        binding.layoutImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            picImage.launch(intent);
        });
    }
      private void showToast(String message){
          Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
      }
      private void signUp(){
          loading(true);
          FirebaseFirestore database = FirebaseFirestore.getInstance();
          HashMap<String,Object> user = new HashMap<>();
          user.put(Constants.KEY_NAME, binding.inputName.getText().toString());
          user.put(Constants.KEY_EMAIL, binding.inputEmail.getText().toString());
          user.put(Constants.KEY_PASSWORD, binding.inputPass.getText().toString());
          user.put(Constants.KEY_IMAGE, encodedImage);
          database.collection(Constants.KEY_COLLECTION_USE)
                  .add(user)
                  .addOnSuccessListener(documentReference -> {
                      loading(false);
                      preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                      preferenceManager.putString(Constants.KEY_USER_ID,documentReference.getId());
                      preferenceManager.putString(Constants.KEY_NAME,binding.inputName.getText().toString());
                      preferenceManager.putString(Constants.KEY_IMAGE,encodedImage);
                      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);
                  })
                  .addOnFailureListener(exception -> {
                      loading(false);
                      showToast(exception.getMessage());
                  });

      }
      // chuyen anh thanh chuoi
    private String encodedImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap preViewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        preViewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        // Encode using android.util.Base64
        return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP);
    }
    private final ActivityResultLauncher<Intent> picImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),result -> {
                if(result.getResultCode() == RESULT_OK){
                    if(result.getData() !=null){
                        Uri imageUri =result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageProfile.setImageBitmap(bitmap);
                            binding.txtImage.setVisibility(View.GONE);
                            encodedImage = encodedImage(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

      private Boolean isValidSignUpDetails(){
        if(encodedImage == null){
            showToast("Chọn ảnh đại diện");
            return  false;
        }else if(binding.inputName.getText().toString().trim().isEmpty()){
            showToast("Nhập tên");
            return false;
        } else if (binding.inputEmail.getText().toString().trim().isEmpty()) {
            showToast("Nhập email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()) {
            showToast("Email không hợp lệ");
            return false;
        } else if (binding.inputPass.getText().toString().trim().isEmpty()) {
            showToast("Nhập mật khẩu");
            return false;
        } else if (binding.inputPassCf.getText().toString().trim().isEmpty()){
            showToast("Nhập lại mật khẩu");
            return false;
        } else if (!binding.inputPass.getText().toString().equals(binding.inputPassCf.getText().toString())) {
            showToast("Mật khẩu phải giống nhau");
            return false;
        }else{
            return true;
        }
      }
      private void loading(Boolean isLoading){
        if(isLoading){
            binding.btnSignup.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility((View.INVISIBLE));
            binding.btnSignup.setVisibility((View.VISIBLE));
        }
      }
}