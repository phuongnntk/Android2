package com.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.dao.NguoiDungDAO;

public class Register extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //ánh xạ
        EditText edtUser = findViewById(R.id.edtUser);
        EditText  edtPass = findViewById(R.id.edtPass);
        EditText  edtRePass = findViewById(R.id.edtRePass);
        EditText  edtFullname = findViewById(R.id.edtFullname);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnGoBack = findViewById(R.id.btnGoBack);

        nguoiDungDAO = new NguoiDungDAO(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String rePass = edtRePass.getText().toString();
                String fullname = edtFullname.getText().toString();

                if(!pass.equals(rePass)){
                    Toast.makeText(Register.this,"Nhập mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = nguoiDungDAO.Register(user,pass,fullname);
                    if(check){
                        Toast.makeText(Register.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Register.this,"Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}