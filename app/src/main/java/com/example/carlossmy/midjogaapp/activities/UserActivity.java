package com.example.carlossmy.midjogaapp.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.retrofit.UserApi;
import com.example.carlossmy.midjogaapp.retrofit.UserClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView userFullName,userEmail,userBirthdate,userPhone;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initViews();
    }


    private void initViews(){
        toolbar = findViewById(R.id.toolbar);
        setToolbar(toolbar);
        userFullName= findViewById(R.id.user_full_name);
        userEmail= findViewById(R.id.user_email);
        userBirthdate= findViewById(R.id.user_birthdate);
        userPhone= findViewById(R.id.user_phone);
        edit=findViewById(R.id.edit_button);
        addInformations();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setToolbar(Toolbar toolbar){
        toolbar.setTitle("Utilisateur");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void updateProfile(String phone,String nom,String date){

        Call<ResponseBody> call = UserClient
                .getInstance()
                .getApi()
                .updateProfile(nom,phone,date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent it = new Intent(getApplicationContext(),UpdateActivity.class);
                startActivity(it);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void addInformations(){
       userEmail.setText(LoginActivity.getUserInfo(getApplicationContext(),"nom_complet",null));
       userFullName.setText(LoginActivity.getUserInfo(getApplicationContext(),"email",null));
       userPhone.setText(LoginActivity.getUserInfo(getApplicationContext(),"phone",null));
       userBirthdate.setText(LoginActivity.getUserInfo(getApplicationContext(),"date",null));

    }
}
