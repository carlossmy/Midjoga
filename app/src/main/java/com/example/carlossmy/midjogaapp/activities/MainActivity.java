package com.example.carlossmy.midjogaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.entities.Cagnotte;
import com.example.carlossmy.midjogaapp.entities.Category;
import com.example.carlossmy.midjogaapp.fragments.CategoryFragment;
import com.example.carlossmy.midjogaapp.fragments.HomeFragment;
import com.example.carlossmy.midjogaapp.fragments.SearchFragment;
import com.example.carlossmy.midjogaapp.retrofit.CagnotteClient;
import com.example.carlossmy.midjogaapp.fragments.SearchResultFragment;
import com.example.carlossmy.midjogaapp.retrofit.UserClient;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,HomeFragment.OnListFragmentInteractionListener,CategoryFragment.OnListFragmentInteractionListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private ImageView profile_image;
    private TextView profile_name;
    private NavigationView nv;
    BottomNavigationView navigation;
    Button drawerButton;
    GoogleSignInAccount ac;
    public static String name=null;
    public static final String GOOGLE_ACCOUNT= "google_account";
    public static final String TOKEN_NAME= "token";
    public static final String BEARER= "Bearer ";;
    public static String TOKEN;
    public static ArrayList<Cagnotte>list=new ArrayList<Cagnotte>(),searchList=new ArrayList<Cagnotte>();
    public static ArrayList<Category> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ac = (GoogleSignInAccount)getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        TOKEN= getIntent().getStringExtra(TOKEN_NAME);
        initViews();
        getCategories();
        getPublicCagnotte();
        //Toast.makeText(getApplicationContext(), LoginActivity.getUserInfo(getApplicationContext(),"nom_complet","Ok"), Toast.LENGTH_SHORT).show();
        loadFragment(new HomeFragment());
        if (ac!=null)
            name=ac.getDisplayName();

       // Toast.makeText(getApplicationContext(),"OK"+StaticFunctions.daysBetweenDates(new CustomDate(22,12,2020)),Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ac!=null){
            profile_name.setText(name);
        }
        else {
           //setUsersInformation(TOKEN);
            profile_name.setText(LoginActivity.getUserInfo(getApplicationContext(),"nom_complet",null));
        }


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        System.exit(1);
    }

    private void initViews(){
        drawerButton=findViewById(R.id.drawer_open);
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        nv = (NavigationView)findViewById(R.id.nv);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                boolean b= true;
                b = drawerItemSelected(id);


                return b;

            }
        });
        navigation= findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        profile_image=(ImageView)nv.getHeaderView(0).findViewById(R.id.nav_header_imageView);
        profile_name= nv.getHeaderView(0).findViewById(R.id.nav_header_textView);

    }
    private void enableFailureMessage(){
        findViewById(R.id.list).setVisibility(View.GONE);
        findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_bar).setVisibility(View.GONE);
    }
    private void disableHomeLoadingBar(){
        findViewById(R.id.list).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_bar).setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    /*
    Method to load Fragment
     */
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    /*
    OnClick methods
     */
    public void openDrawer(View v){
        dl.openDrawer(Gravity.LEFT);
    }
    public boolean drawerItemSelected(int id){
        switch(id)
        {
            case R.id.account:
                Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
            case R.id.activities:
                Intent intent = new Intent(getApplicationContext(),CreateActivity.class);
                startActivity(intent);
                break;//Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
            case R.id.settings:
                //dToast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();

                Intent et = new Intent(getApplicationContext(),SettingsActivity.class);

                startActivity(et);
                break;
            default:
                return true;
        }
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_search:
                fragment = new SearchFragment();
                break;

        }

        return loadFragment(fragment);
    }

    @Override
    public void onListFragmentInteraction(Cagnotte item) {
            Intent et = new Intent(getApplicationContext(),CagnotteActivity.class);
            et.putExtra(CagnotteActivity.CAGNOTTE_ID,(int)item.getId());
          //  Toast.makeText(getApplicationContext(),item.getId(),Toast.LENGTH_SHORT);
            startActivity(et);
    }

    @Override
    public void cagnotteListUpdate() {
        getPublicCagnotte();
    }

    @Override
    public void addCagnotte() {
        Intent it = new Intent(getApplicationContext(),CreateActivity.class);
        it.putExtra("token",LoginActivity.getUserInfo(getApplicationContext(),"token",null));
        startActivity(it);
    }

    @Override
    public void tryAgainClicked() {
            getPublicCagnotte();
    }

    /*
    API connection methods to return data
     */
    private void  setUsersInformation(String token){

        Call<ResponseBody> call = UserClient
                .getInstance()
                .getApi()
                .getUserProfile(BEARER+token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject json;
                try {
                     json = new JSONObject(response.body().string());
                     if (json.has("nomComplet")){
                         profile_name.setText(json.getString("nomComplet"));
                         Toast.makeText(getApplicationContext(),json.getString("nomComplet"),Toast.LENGTH_LONG).show();
                     }
                     else{
                         Toast.makeText(getApplicationContext(),"Erreur d'authentification",Toast.LENGTH_LONG).show();
                         Intent it = new Intent(getApplicationContext(),LoginActivity.class);
                         startActivity(it);
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e ){
                    Snackbar.make(findViewById(R.id.container),"Problème de connexion",Snackbar.LENGTH_LONG).show();
                }


            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();

            }
        });

    }
    private void getPublicCagnotte(){
        if (!list.isEmpty())
                    list.clear();
        list=new ArrayList<Cagnotte>();
        Call<ResponseBody>call=CagnotteClient
                .getInstance()
                .getApi()
                .getPublicCagnottes();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {


                    JSONArray array = new JSONArray(response.body().string());
                    if (array.getJSONObject(0).has("id")){
                       // Toast.makeText(getApplicationContext(),StaticFunctions.toCustomDate(array.getJSONObject(0).getString("dateFin")).toString(),Toast.LENGTH_LONG).show();
                        for (int i=0;i<array.length();i++){
                            JSONObject json= array.getJSONObject(i);


                            Cagnotte c = new Cagnotte(json.getInt("id"),json.getString("titre"),json.getString("description"),
                                    json.getDouble("montant"),json.getDouble("totalAttendu"),json.getString("dateFin")/*new Date(2000,01,22)*/,
                                    json.getString("localisation"),null,json.getInt("typeCagnotteId"),0
                                    );
                            list.add(c);

                        }
                        disableHomeLoadingBar();

                    }
                } catch (JSONException e) {
                    enableFailureMessage();
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();
                } catch (IOException e) {
                    enableFailureMessage();
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();
                } catch (NullPointerException e ){
                    enableFailureMessage();
                    Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(findViewById(R.id.fragment_container),"Problème d'authentification, veuillez réessayer",Snackbar.LENGTH_LONG).show();
             enableFailureMessage();

            }
        });

    }
    private void getPublicCagnotte(int id,String libelle){
        searchList.clear();
        Call<ResponseBody>call = CagnotteClient
                .getInstance()
                .getApi()
                .sortByType(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    if (array.getJSONObject(0).has("id")){
                        for ( int cpt  =0;cpt<array.length();cpt++){
                            JSONObject json = array.getJSONObject(cpt);
                            Cagnotte c = new Cagnotte(json.getInt("id"),json.getString("titre"),json.getString("description"),
                                    json.getDouble("montant"),json.getDouble("totalAttendu"),json.getString("dateFin")/*new Date(2000,01,22)*/,
                                    json.getString("localisation"),null,json.getInt("typeCagnotteId"),0
                            );
                            searchList.add(c);

                        }
                        loadFragment(new SearchResultFragment());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

//        ((TextView)findViewById(R.id.category_description)).setText("Categorie : "+libelle);
    }
    private void getCategories() {
        categoryList= new ArrayList<Category>();
        Call<ResponseBody> call = CagnotteClient
                .getInstance()
                .getApi()
                .getTypes();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONArray json = null;
                String imgUrl=null;
                try {
                    json = new JSONArray(response.body().string());
                    if (json.getJSONObject(0).has("id")) {
                        for (int i=0;i<=json.length();i++){
                            JSONObject j = json.getJSONObject(i);
                            if (j.getString("image")!=null)
                                imgUrl=StaticFunctions.IMG_URL+j.getString("image");
                            Category c = new Category(j.getInt("id"),j.getString("libelle"),StaticFunctions.IMG_URL+j.getString("image"));
                            categoryList.add(c);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();
                } catch (NullPointerException e){
                    Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion, veuillez réessayer",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(findViewById(R.id.fragment_container),"Problème de connexion",Snackbar.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onListFragmentInteraction(Category item) {
        getPublicCagnotte(item.getId(),item.getLibelle());


    }
}