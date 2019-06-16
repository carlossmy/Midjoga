package com.example.carlossmy.midjogaapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.retrofit.UserClient;
import com.example.carlossmy.midjogaapp.entities.CustomDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.carlossmy.midjogaapp.activities.LoginActivity.PASSWORD_PATTERN;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    CustomDate birth;
    Button back,registration,birthdate;
    TextView date;
    TextInputLayout NomCompletInput,emailInput,passwordInput,phoneInput;
    EditText edit;

    ProgressBar loading;
    Spinner indicatif;
    public static final Pattern DATE_PATTERN =Pattern.compile("^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$");
    public static final Pattern PHONE_PATTERN =Pattern.compile("^[0-9]+$");
    public static final String INDICATIF_TOGO="00228";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }
    private void initViews(){
        back= (Button)findViewById(R.id.back);
        registration= (Button)findViewById(R.id.registration);
        birthdate =(Button)findViewById(R.id.birthdate);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        date=(TextView)findViewById(R.id.date);
        NomCompletInput= (TextInputLayout)findViewById(R.id.nom_complet);
        emailInput = (TextInputLayout)findViewById(R.id.email);
        phoneInput = (TextInputLayout)findViewById(R.id.phone);
        edit=(EditText)findViewById(R.id.input);
        passwordInput = (TextInputLayout)findViewById(R.id.password);
        edit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        loading=(ProgressBar)findViewById(R.id.loading);
        addCountryToIndicatif();


    }
    /*
                all the methodss
     */
    private  void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private Boolean validateNom(String nom,EditText edit){
        if(nom.isEmpty()){
            edit.setError("Ce champ doit être rempli");
            return  false;
        }
        else if (nom.length()<4 && nom.length()>22){
            edit.setError("Veuilliez entrer un nom valide sans chiffre ni caractère spéciaux d'au moins 4 lettre et au plus 22");
            return false;
        }
        else {
            edit.setError(null);
            return  true;
        }
    }
    private Boolean validateEmail(String email,EditText edit){
        if(email.isEmpty()){
            edit.setError("Ce champ doit être rempli");
            return  false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit.setError("Adresse Email invalide");
            return false;
        }
        else {
            edit.setError(null);
            return  true;
        }
    }
    private Boolean validatePassword(String password, EditText edit){

        if (password.isEmpty()){
            edit.setError("Ce champ doit être rempli");
            return  false;
        }
        else if (!PASSWORD_PATTERN.matcher(password).matches()){
            edit.setError("Le mot de passe doit être entre 4 et 8 chiffres avec une lettre majuscule et au moins un chiffre");
            return false;
        }
        else {
            edit.setError(null);
            return  true;
        }
    }
    private Boolean validatePhone(String date, EditText edit){

        if (date.isEmpty()){
            edit.setError("Ce champ doit être rempli");
            return  false;
        }
        else if (!PHONE_PATTERN.matcher(date).matches()){
            edit.setError("Entrez un numéro de téléphone valide");
            return false;
        }
        else {
            edit.setError(null);
            return  true;
        }
    }
    private Boolean validateDate(String birth,TextView texte){

        if (birth==null){
            texte.setError("Ce champ doit être rempli");
            return  false;
        }
        else if (!DATE_PATTERN.matcher(birth).matches()){
            texte.setError("Veuillez entrer une date");
            return false;
        }
        else {
            texte.setError(null);
            return  true;
        }
    }
    public void addCountryToIndicatif() {

        indicatif=(Spinner)findViewById(R.id.country);
        List<String> list = new ArrayList<String>();
        list.add("00228(Togo)");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        indicatif.setAdapter(dataAdapter);
        indicatif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                TODO listener
                 */
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    /*
    Onclick listeners
     */
    public void goBack(View v){
        this.finish();
    }
    public void onRegisterClicked(View v){
        loading.setVisibility(View.VISIBLE);
        registration.setVisibility(View.GONE);
        String nom_complet = NomCompletInput.getEditText().getText().toString().trim();
        String email = emailInput.getEditText().getText().toString().trim();
        String password = edit.getText().toString().trim();
        String phone = INDICATIF_TOGO+phoneInput.getEditText().getText().toString().trim();
        String date_birth=null;
        if(birth!=null)
            date_birth=birth.toStringSQL();

        if (validateNom(nom_complet,NomCompletInput.getEditText())&&validateEmail(email,emailInput.getEditText())&&validateDate(date_birth,date)&&validatePhone(phone,phoneInput.getEditText())&&validatePassword(password,passwordInput.getEditText())){
            Call<ResponseBody> call = UserClient
                    .getInstance()
                    .getApi()
                    .register(nom_complet,email,password,date_birth,phone);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject= new JSONObject(response.body().string());
                        Intent it = new Intent(getApplicationContext(),MainActivity.class);
                        LoginActivity.saveUserInfo(getApplicationContext(),"nom_complet",jsonObject.getString("nomComplet"));
                        LoginActivity.saveUserInfo(getApplicationContext(),"email",jsonObject.getString("email"));
                        LoginActivity.saveUserInfo(getApplicationContext(),"token",jsonObject.getString("token"));
                        startActivity(it);

                        Toast.makeText(getApplicationContext(),response.body().string(),Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    registration.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Impossible de se connecter, Veuillez réessayer",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        birth=new CustomDate(i2,i1+1,i);
        date.setText(birth.toStringSQL());
    }
}
