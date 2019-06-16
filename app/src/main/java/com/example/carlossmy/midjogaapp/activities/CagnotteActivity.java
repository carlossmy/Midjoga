package com.example.carlossmy.midjogaapp.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.entities.Comment;
import com.example.carlossmy.midjogaapp.entities.CustomDate;
import com.example.carlossmy.midjogaapp.entities.Paiement;
import com.example.carlossmy.midjogaapp.entities.User;
import com.example.carlossmy.midjogaapp.fragments.CategoryFragment;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyCategoryRecyclerViewAdapter;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyCommentRecyclerViewAdapter;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyPaiementRecyclerViewAdapter;
import com.example.carlossmy.midjogaapp.retrofit.CagnotteClient;
import com.example.carlossmy.midjogaapp.retrofit.CommentClient;
import com.example.carlossmy.midjogaapp.retrofit.PaiementClient;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CagnotteActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView amount,nbParticipants,remaining,description,title,category,location;
    ProgressBar bar;
    RecyclerView donnors,comments;
    ArrayList<Paiement>paiementList;
    ArrayList<Comment>commentList;

    int cagnotteId;
    public static String CAGNOTTE_ID="cagnotteId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cagnotte);
        cagnotteId= getIntent().getIntExtra(CAGNOTTE_ID,0);
        initViews();
       setInformations(cagnotteId);
        getDonnors();
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(it);
    }

    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cagnotte");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent et = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(et);
            }
        });
        amount=(TextView)findViewById(R.id.amount);
        nbParticipants=(TextView)findViewById(R.id.nb_participants);
        remaining=(TextView)findViewById(R.id.remaining);
        category=(TextView)findViewById(R.id.category);
        location=(TextView)findViewById(R.id.location);
        description=(TextView)findViewById(R.id.cagnotte_description);
        title=(TextView)findViewById(R.id.cagnotte_title);
        bar=(ProgressBar) findViewById(R.id.totalBar);
        donnors = findViewById(R.id.donnors);
        comments = findViewById(R.id.comments);


    }
    private void setInformations(int id){
        if (id!=0){
            Call<ResponseBody>call= CagnotteClient
                    .getInstance()
                    .getApi()
                    .getCagnotte(id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                      //  JSONObject json = new JSONObject(response.body().string());
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        //Toast.makeText(getApplicationContext(),jsonArray.getJSONObject(0).getString("localisation"),Toast.LENGTH_LONG).show();
                        if(jsonArray.getJSONObject(0).has("id")){
                            JSONObject json = jsonArray.getJSONObject(0);
                            amount.setText(StaticFunctions.Amount(json.getDouble("montant"),(double)json.getDouble("totalAttendu")));
                            nbParticipants.setText(StaticFunctions.Donnors((int)json.get("donnors")));
                            remaining.setText(StaticFunctions.timeRemaining(json.getString("dateFin")));
                            category.setText(json.getString("libelle"));
                            location.setText(json.getString("localisation"));
                            description.setText(json.getString("description"));
                            title.setText(json.getString("titre"));
                            bar.setProgress(StaticFunctions.barPercentage(json.getDouble("montant"),json.getDouble("totalAttendu")));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.scroll),StaticFunctions.FAILURE,Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }

    }
    private void getDonnors(int cagnotteId){
        paiementList= new ArrayList<Paiement>();
        Call<ResponseBody>call = CagnotteClient
                .getInstance()
                .getApi()
                .getEachDonnors(this.cagnotteId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body().string()!=null){
                        JSONArray array = new JSONArray(response.body().string());
                        if (array.getJSONObject(0).has("donnorId")){
                            for (int i=0;i<array.length();i++){
                                JSONObject json = array.getJSONObject(i);
                             //   User user = new User()
                                Paiement p = new Paiement(json.getInt("id"),json.getDouble("montant")
                                        ,new User(json.getInt("donnorId"),json.getString("donnorName")));
                                paiementList.add(p);
                            }

                        }
                        donnors.setAdapter(new MyPaiementRecyclerViewAdapter(paiementList));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void getComments(int cagnotteId){
        paiementList= new ArrayList<Paiement>();
        Call<ResponseBody>call = CagnotteClient
                .getInstance()
                .getApi()
                .getComments(this.cagnotteId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body().string()!=null){
                        JSONArray array = new JSONArray(response.body().string());
                        if (array.getJSONObject(0).has("id")){
                            for (int i=0;i<array.length();i++){
                                JSONObject json = array.getJSONObject(i);
                                //   User user = new User()
                                Comment c = new Comment(json.getInt("id"),json.getString("comment"),
                                        StaticFunctions.toCustomDate(json.getString("date"))
                                        ,new User(0,json.getString("nomComplet")));
                                commentList.add(c);
                            }

                        }
                        comments.setAdapter(new MyCommentRecyclerViewAdapter(commentList));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void getDonnors(){
        paiementList = new ArrayList<Paiement>();
        paiementList.add(new Paiement(1,200,new User(1,"Carlos Samey")));
        paiementList.add(new Paiement(1,200,new User(1,"Carlos Samey")));
        paiementList.add(new Paiement(1,200,new User(1,"Carlos Samey")));
        paiementList.add(new Paiement(1,200,new User(1,"Carlos Samey")));
        paiementList.add(new Paiement(1,200,new User(1,"Carlos Samey")));
        paiementList.add(new Paiement(1,200,new User(1,"Carlos Samey")));
        donnors.setAdapter(new MyCategoryRecyclerViewAdapter(MainActivity.categoryList,null));

    }
    private void addComment(String content, int id, int userId){
        Call<ResponseBody> call= CommentClient
                .getInstance()
                .getApi()
                .New(content,id,userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        if (json.has("id")){
                            getComments(cagnotteId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Connexion internet non disponible, réessayez",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onPaiementClicked(View v){
        Intent it = new Intent(getApplicationContext(),PaiementActivity.class);
        it.putExtra(CAGNOTTE_ID,cagnotteId);
        startActivity(it);
    }
}
