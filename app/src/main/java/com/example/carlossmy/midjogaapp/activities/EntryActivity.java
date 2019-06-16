package com.example.carlossmy.midjogaapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.retrofit.UserClient;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryActivity extends AppCompatActivity {

    RelativeLayout registerGoogle;
    FrameLayout frameStart;
    Button sign_in;
    CardView register;
    GoogleSignInClient client;
    GoogleSignInAccount alreadyloggedAccount2;
    boolean exists,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        if (LoginActivity.getUserInfo(getApplicationContext(),"email",null)!=null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client  = GoogleSignIn.getClient(this, gso);


        initViews();


    }
    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
         alreadyloggedAccount2 = GoogleSignIn.getLastSignedInAccount(this);

        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
           onLoggedIn(alreadyloggedAccount);
        } else {
            Toast.makeText(this, "Not Logged In", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        alreadyloggedAccount2=account;
                        exists=false;
                        onLog(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Toast.makeText(getApplicationContext(),e.getStatusCode(),Toast.LENGTH_LONG).show();
                        //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }
    private void initViews(){
        sign_in= (Button)findViewById(R.id.sign_in);
        register =(CardView)findViewById(R.id.register);
        registerGoogle=findViewById(R.id.registerGoogle);
    }
    public void login(View v){
        Intent it = new Intent(this,LoginActivity.class);
        startActivity(it);
    }
    public void registerGoogleClicked(View v){
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, 101);

    }
    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.GOOGLE_ACCOUNT,googleSignInAccount);
        startActivity(intent);
        finish();
    }
    private void onLog(GoogleSignInAccount googleSignInAccount) {
            Call<ResponseBody>call = UserClient
                    .getInstance()
                    .getApi()
                    .googleRegister(googleSignInAccount.getDisplayName(),googleSignInAccount.getEmail(),StaticFunctions.GOOGLE_PASSWORD);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        if (json.has("exist")){
                            if (json.getBoolean("exist")){

                                LoginActivity.saveUserInfo(getApplicationContext(),"nom_complet",json.getString("nomComplet"));
                                LoginActivity.saveUserInfo(getApplicationContext(),"email",json.getString("email"));
                                LoginActivity.saveUserInfo(getApplicationContext(),"token",json.getString("token"));
                                Toast.makeText(getApplicationContext(),"Utilisateur déja inscrit",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra(MainActivity.GOOGLE_ACCOUNT,alreadyloggedAccount2);
                                startActivity(intent);
                               // finish();
                            }
                        }
                       else if (json.has("user")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(MainActivity.GOOGLE_ACCOUNT,alreadyloggedAccount2);
                            JSONObject j = json.getJSONObject("user");
                            LoginActivity.saveUserInfo(getApplicationContext(),"nom_complet",j.getString("nomComplet"));
                            LoginActivity.saveUserInfo(getApplicationContext(),"email",j.getString("email"));
                            LoginActivity.saveUserInfo(getApplicationContext(),"token",json.getString("token"));
                            startActivity(intent);
                            finish();
                        }
                        else{

                            Snackbar.make(findViewById(R.id.linear_entry),"Problème d'authentification, veuillez réessayer",Toast.LENGTH_LONG).show();
                            client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //On Succesfull signout we navigate the user back to LoginActivity
                                    Intent intent=new Intent(getApplicationContext(),EntryActivity.class);
                                    LoginActivity.deleteUserInfo(getApplicationContext());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.linear_entry),"Problème d'authentification, veuillez réessayer",Toast.LENGTH_LONG).show();
                    client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //On Succesfull signout we navigate the user back to LoginActivity
                            Intent intent=new Intent(getApplicationContext(),EntryActivity.class);
                            LoginActivity.deleteUserInfo(getApplicationContext());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                }
            });



    }
    public void registerClicked(View v){
        Intent it = new Intent(this,RegisterActivity.class);
        startActivity(it);
    }

    private boolean verifyEmail(String email){
        Call<ResponseBody> call = UserClient
                .getInstance()
                .getApi()
                .verify(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);
                    if (json.has("exist")){
                        exists=json.getBoolean("exist");
                        b=true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Problème de connextion",Toast.LENGTH_SHORT).show();
            }
        });

        return exists;
    }

}
