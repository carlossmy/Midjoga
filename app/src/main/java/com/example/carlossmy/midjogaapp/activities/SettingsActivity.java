package com.example.carlossmy.midjogaapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carlossmy.midjogaapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingsActivity extends AppCompatActivity {
	private GoogleSignInClient client;
	Button signOut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initViews();
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		client=GoogleSignIn.getClient(this,gso);
	}
	private void initViews(){
		Button bt = (Button)findViewById(R.id.sign_out);
	}
	public void onClick(View v){
		switch (v.getId()){
			case R.id.sign_out:
				signOut();
				break;
		}

	}
	private void signOut(){
          /*
          Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
          listener which will be invoked once the sign out is the successful
           */
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
}
