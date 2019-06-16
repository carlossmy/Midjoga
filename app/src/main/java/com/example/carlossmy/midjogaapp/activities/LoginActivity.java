package com.example.carlossmy.midjogaapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.retrofit.UserClient;

import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button back;
    Button connection;
    TextInputLayout loginInput;
    TextInputLayout passwordInput;
    ProgressBar loading;
    public static String PREFERENCES_FILE="user_info";
    public static String PREF_USER_FIRST_TIME="false";
    public static String LOGIN_ERROR = "L'E-mail et/ou le mot de passe entré est incorrect, Veuillez rééssayer ";
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{4,8}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        loading.setVisibility(View.GONE);
    }

    private void initViews() {
        // initiation of all views
        back = (Button) findViewById(R.id.back);
        connection = (Button) findViewById(R.id.connection);
        loginInput = (TextInputLayout) findViewById(R.id.login);
        passwordInput = (TextInputLayout) findViewById(R.id.password);
        //set the edittext to a password type
        passwordInput.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordInput.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        loading = (ProgressBar) findViewById(R.id.loading);
    }

    //go the the previous view
    public void goBack(View v) {
        this.finish();
    }

    private Boolean validateEmail(String email, EditText edit) {
        if (email.isEmpty()) {
            edit.setError("Ce champ doit être rempli");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit.setError("Adresse Email invalide");
            return false;
        } else {
            edit.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(String password, EditText edit) {

        if (password.isEmpty()) {
            edit.setError("Ce champ doit être rempli");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            edit.setError("Le mot de passe doit être entre 4 et 8 chiffres avec une lettre majuscule et au moins un chiffre");
            return false;
        } else {
            edit.setError(null);
            return true;
        }
    }

    //action when the connection button is clicked
    public void connectionClicked(View v) {
        String login = loginInput.getEditText().getText().toString().trim();
        String password = passwordInput.getEditText().getText().toString().trim();
        // make the progress bar visible and button invisible
        loading.setVisibility(View.VISIBLE);
        connection.setVisibility(View.GONE);
        //make the request to the api
        if (validateEmail(login, loginInput.getEditText()) && validatePassword(password, passwordInput.getEditText())) {
            try {
                Call<ResponseBody> call = UserClient
                        .getInstance()
                        .getApi()
                        .login(login, password);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            //get the response of the request
                            String stringRes = response.body().string();
                            JSONObject res = new JSONObject(stringRes);

                            if (res.getBoolean("auth")) {

                                String token = null;
                                if (res.getString("token") != null) {
                                    token = res.getString("token");
                                }
                                saveUserInfo(getApplicationContext(),"token",token);
                                saveUserInfo(getApplicationContext(),"nom_complet",res.getString("nomComplet"));
                                saveUserInfo(getApplicationContext(),"email",res.getString("email"));
                                saveUserInfo(getApplicationContext(),"date_naissance",res.getString("dateNaissance"));
                                saveUserInfo(getApplicationContext(),"phone",res.getString("phone"));
                                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(getApplicationContext(), res.get("auth").toString(), Toast.LENGTH_LONG).show();
                                it.putExtra(MainActivity.TOKEN_NAME, token);
                                startActivity(it);


                            } else {

                                Snackbar.make(findViewById(R.id.linear_login),LOGIN_ERROR,Toast.LENGTH_LONG).show();
                                loading.setVisibility(View.GONE);
                                connection.setVisibility(View.VISIBLE);
                            }
                            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            connection.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // if a failure occurs
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        connection.setVisibility(View.VISIBLE);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), LOGIN_ERROR, Toast.LENGTH_LONG).show();
                loading.setVisibility(View.GONE);
                connection.setVisibility(View.VISIBLE);
            }
        } else {

            loading.setVisibility(View.GONE);
            connection.setVisibility(View.VISIBLE);
        }

    }


    public static void saveUserInfo(Context ct,String settingName,String settingValue){
        SharedPreferences shared = ct.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(settingName,settingValue);
        editor.apply();
    }
    public  static String getUserInfo(Context ct, String settingName,String defaultValue){
        SharedPreferences shared = ct.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        return shared.getString(settingName,defaultValue);
    }
    public static void deleteUserInfo(Context ct){
        SharedPreferences shared = ct.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }

}




