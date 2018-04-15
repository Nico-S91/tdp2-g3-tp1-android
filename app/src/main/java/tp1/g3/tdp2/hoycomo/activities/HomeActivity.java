package tp1.g3.tdp2.hoycomo.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import com.loopj.android.http.*;
import org.json.*;

import cz.msebera.android.httpclient.Header;
import tp1.g3.tdp2.hoycomo.R;
import tp1.g3.tdp2.hoycomo.helpers.AppServerClient;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView myNavigationView;


    //declaro los textos del nombre y mail de usuario de facebook
    TextView txtEmail, txtName;
    ImageView imgAvatar;

    //declaro un objeto de dialogo
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        //View header = LayoutInflater.from(this).inflate(R.layout.nav_header_home, null);

        //linkeo los objetos texto
        txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        txtName = (TextView) header.findViewById(R.id.txtFbName);

        //linkeo el objeto imagen
        imgAvatar = (ImageView) header.findViewById(R.id.avatar);

        //Activo un mensaje de que estoy trayendo data de facebook
        mDialog = new ProgressDialog(HomeActivity.this);
        mDialog.setMessage("Procesando Datos...");
        mDialog.show();

        //obtengo el accesstoken de facebook
        String accesToken = AccessToken.getCurrentAccessToken().getToken();

        //hago el request con la graph api de facebook para pedirle los datos pertinentes
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                mDialog.dismiss();
                Log.d("response", response.toString());

                //chequeo que tenga mas de 5 amigos
                try {
                    int friendCount = Integer.parseInt(object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

                    if(friendCount < 5){
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                        builder.setCancelable(false);
                        builder.setTitle("Error de Autenticación!");
                        builder.setMessage("La cuenta de facebook debe tener 5 amigos o más");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(HomeActivity.this, MainActivity.class));
                            }
                        });

                        builder.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getData(object);

                //Voy a ver si el usuario es nuevo o ya existe
                try {
                    //creo el parametro de consulta
                    RequestParams params = new RequestParams();
                    params.put("fb_id", object.getString("id"));

                    AppServerClient.get("user/exist", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            //capturo la respuesta Json recibida
                            if(statusCode == 204) {
                                //El usuario no existe, debo mandarlo hacia la pantalla de Registro de usuarios.
                                startActivity(new Intent(HomeActivity.this, UserRegisterAddressActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                            builder.setCancelable(false);
                            builder.setTitle("Error de comunicación con el servidor");
                            builder.setMessage("No se pudo acceder al servidor para conseguir los datos");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LoginManager.getInstance().logOut();
                                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                                }
                            });
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //API de Graph
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,friends");
        request.setParameters(parameters);
        request.executeAsync();

        //TODO: Testear en un celular real!
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_cerrar_sesion) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cerrar_sesion) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getData(JSONObject object) {
        try {
            int imgWidth = imgAvatar.getWidth();
            int imgHeight = imgAvatar.getHeight();
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id")+"/picture?width=" + imgWidth +"&height=" + imgHeight);
            Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

            txtEmail.setText(object.getString("email"));
            txtName.setText(object.getString("name"));
            //txtFriends.setText("Friends: " + object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
