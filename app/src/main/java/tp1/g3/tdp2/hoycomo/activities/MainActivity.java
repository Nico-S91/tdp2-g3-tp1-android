package tp1.g3.tdp2.hoycomo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import tp1.g3.tdp2.hoycomo.R;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //chequeo si ya esta logueado con facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            //lo redirijo a la pantalla home
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }

        callbackManager = CallbackManager.Factory.create();

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //Inicio una nueva actividad en android, dirigiendome al Home de la App
                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                //String accessToken = loginResult.getAccessToken().getToken();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if(AccessToken.getCurrentAccessToken() != null){
            //txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

    }



}
