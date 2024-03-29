package com.example.appchatandroid.models;

import java.io.Serializable;

public class User implements Serializable {
    public String name, email, image, token,id;

    public User() {
        // Cần có constructor mặc định để Firebase có thể deserialize dữ liệu
    }
}
