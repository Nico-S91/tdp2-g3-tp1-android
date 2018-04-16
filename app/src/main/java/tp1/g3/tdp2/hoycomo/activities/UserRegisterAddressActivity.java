package tp1.g3.tdp2.hoycomo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import fr.ganfra.materialspinner.MaterialSpinner;
import tp1.g3.tdp2.hoycomo.Activdades.ComerciosListado;
import tp1.g3.tdp2.hoycomo.R;
import tp1.g3.tdp2.hoycomo.Helpers.AppServerClient;

public class UserRegisterAddressActivity extends AppCompatActivity {

    private TextView tvAddress;
    private MaterialSpinner spLocation;
    private HashMap<String, Integer> spinnerMap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_address);

        ActionBar actionBarInstance = getSupportActionBar();
        actionBarInstance.setTitle("HoyComo");
        if (actionBarInstance != null){
            actionBarInstance.setDisplayHomeAsUpEnabled(true);
        }

        //get the spinner from the xml.
        MaterialSpinner dropdown = findViewById(R.id.ddlCity);

        //lista de backup para el spinner
        String[] items = new String[]{"Agronomía", "Almagro", "Balvanera", "Barracas", "Belgrano", "Boedo", "Caballito", "Chacarita", "Coghlan", "Colegiales", "Constitución", "Flores",
                "Floresta", "La Boca", "La Paternal", "Liniers", "Mataderos", "Monte Castro", "Monserrat", "Nueva Pompeya", "Núñez", "Palermo", "Parque Avellaneda",
                "Parque Chacabuco", "Parque Chas", "Parque Patricios", "Puerto Madero", "Recoleta", "Retiro", "Saavedra", "San Cristóbal", "San Nicolás", "San Telmo",
                "Vélez Sársfield", "Versalles", "Villa Crespo", "Villa del Parque", "Villa Devoto", "Villa General Mitre", "Villa Lugano", "Villa Luro", "Villa Ortúzar",
                "Villa Pueyrredón", "Villa Real", "Villa Riachuelo", "Villa Santa Rita", "Villa Soldati", "Villa Urquiza"};


        //preparo la llamada para poblar el spinner con los partidos de capital federal
        AppServerClient.get("api/v1/districts", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(statusCode == 200) {
                    try {
                        JSONArray localidades = response.getJSONArray("districts");
                        String[] spinnerArray = new String[localidades.length()];

                        for (int i=0; i<localidades.length(); i++) {
                            JSONObject localidad = localidades.getJSONObject(i);
                            spinnerMap.put(localidad.getString("name"), localidad.getInt("id"));
                            spinnerArray[i] = localidad.getString("name");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(UserRegisterAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);

                        dropdown.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(UserRegisterAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);
            }
        });


        Button button = (Button) findViewById(R.id.btnAddAddress);
        tvAddress = (TextView) findViewById(R.id.inputAddress);
        spLocation = (MaterialSpinner) findViewById(R.id.ddlCity);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String userAddress = tvAddress.getText().toString();
                String userLocation = spLocation.getItemAtPosition(spLocation.getSelectedItemPosition()).toString();

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("response", response.toString());
                        try {
                            String fbId = object.getString("id");
                            String mail = object.getString("email");

                            //realizo el post a la api del servidor para el alta de cliente
                            JsonObject request = new JsonObject();
                            request.addProperty("id", fbId);
                            request.addProperty("email", mail);
                            request.addProperty("enabled", true);
                            request.addProperty("address", userAddress);
                            request.addProperty("zone", userLocation);

                            StringEntity jsonString = new StringEntity(request.toString());

                            AppServerClient.postJson(UserRegisterAddressActivity.this,"api/v1/user", jsonString, "application/json", new JsonHttpResponseHandler(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    Log.d("response", response.toString());
                                    if(statusCode == 200) {
                                        startActivity(new Intent(UserRegisterAddressActivity.this, ComerciosListado.class));
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                                    Log.d("response", response.toString());
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //API de Graph
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email");
                request.setParameters(parameters);
                request.executeAsync();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(UserRegisterAddressActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Atención!");
                builder.setMessage("Si no agrega al menos una dirección no podrá utilizar la aplicación ¿Está seguro que desea salir?");

                final AlertDialog alert = builder.create();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(UserRegisterAddressActivity.this, MainActivity.class));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.cancel();
                    }
                });

                builder.show();

                break;
        }
        return true;
    }
}
