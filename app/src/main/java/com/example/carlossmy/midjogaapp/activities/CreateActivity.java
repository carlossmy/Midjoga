package com.example.carlossmy.midjogaapp.activities;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.entities.Cagnotte;
import com.example.carlossmy.midjogaapp.entities.Category;
import com.example.carlossmy.midjogaapp.entities.CustomDate;
import com.example.carlossmy.midjogaapp.fragments.CategoryFragment;
import com.example.carlossmy.midjogaapp.fragments.CreateFormFragment;
import com.example.carlossmy.midjogaapp.fragments.HomeFragment;
import com.example.carlossmy.midjogaapp.fragments.TitleFragment;
import com.example.carlossmy.midjogaapp.retrofit.CagnotteClient;
import com.example.carlossmy.midjogaapp.retrofit.UserClient;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity implements TitleFragment.OnFragmentInteractionListener, CategoryFragment.OnListFragmentInteractionListener, CreateFormFragment.OnFragmentInteractionListener, DatePickerDialog.OnDateSetListener {
    FrameLayout container;
    Button back, creation;
    CustomDate selectedDate;
    public static Cagnotte cagnotte;
    boolean bool;
    public static ArrayList<Category> categoryList;
    int userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initViews();

        getUserId();
        loadFragment(new TitleFragment());
    }


    // initialize views
    private void initViews() {
        container = findViewById(R.id.container);
        back = (Button) findViewById(R.id.back_to_main);


    }

    /*
  Method to load Fragment
   */
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onFragmentInteraction(View v) {

    }

    @Override
    public void onCreationClicked() {
        loadFragment(new CategoryFragment());
    }

    public void onBackClicked(View v) {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else
            super.onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(Category item) {
        if (CreateActivity.cagnotte.getTitle() == null) {
            loadFragment(new TitleFragment());
            Snackbar.make(findViewById(R.id.container),"Veuillez entrer un titre pour continuer",Toast.LENGTH_SHORT).show();
          //  Toast.makeText(getApplicationContext(), "Veuillez entrer un titre pour continuer", Toast.LENGTH_LONG).show();
        } else {
            CreateActivity.cagnotte.setTypeCagnotte(item.getId());
            loadFragment(new CreateFormFragment());
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void selectDate() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();


    }


    private void getUserId(){
        Call<ResponseBody>call = UserClient
                .getInstance()
                .getApi()
                .getUserProfile(MainActivity.BEARER+ LoginActivity.getUserInfo(getApplicationContext(),"token",null));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if (json.has("id"))
                        userId=json.getInt("id");
                    else
                        Snackbar.make((FrameLayout)findViewById(R.id.fragment_container),"Problème d'identification",Snackbar.LENGTH_LONG).show();;
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    Snackbar.make((FrameLayout)findViewById(R.id.fragment_container),"Problème d'identification",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(findViewById(R.id.container),StaticFunctions.FAILURE,Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void createCagnotte() {
        if (selectedDate==null){
            Snackbar.make(findViewById(R.id.container),"Veuillez entrer une date valide",Snackbar.LENGTH_LONG).show();

            return;

        }
        if (userId==0){
            Snackbar.make(findViewById(R.id.container),"Erreur de connexion veuillez réessayer ultérieurement",Snackbar.LENGTH_LONG).show();
            return;

        }
        Call<ResponseBody> call = CagnotteClient
                .getInstance()
                .getApi()
                .create(cagnotte.getTitle(), cagnotte.getDesc(), (float) cagnotte.getAmount(), (float) 0, selectedDate.toStringSQL(), cagnotte.getLocalisation(), cagnotte.getTypeCagnotte(), userId, cagnotte.getVisibility());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    if (obj.has("id")) {
                        Intent et = new Intent(getApplicationContext(), CagnotteActivity.class);
                        et.putExtra("cagnotteId", obj.getInt("id"));
                        cagnotte = new Cagnotte();
                        startActivity(et);

                    } else
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG);
                    //Toast.makeText(getApplicationContext(),"Problème de connexion veuillez reéssayer",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch (v.getId()) {
            case R.id.radio_public:
                if (checked)
                    cagnotte.setVisibility(true);
                break;
            case R.id.radio_private:
                if (checked)
                    cagnotte.setVisibility(false);

                break;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        selectedDate = new CustomDate(i2, i1 + 1, i);

    }
}
