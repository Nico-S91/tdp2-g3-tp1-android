package tp1.g3.tdp2.hoycomo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import tp1.g3.tdp2.hoycomo.R;

public class UserRegisterAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_address);

        ActionBar actionBarInstance = getSupportActionBar();
        if (actionBarInstance != null){
            actionBarInstance.setDisplayHomeAsUpEnabled(true);
        }

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.ddlCity);
        //create a list of items for the spinner.
        String[] items = new String[]{"Agronomía", "Almagro", "Balvanera", "Barracas", "Belgrano", "Boedo", "Caballito", "Chacarita", "Coghlan", "Colegiales", "Constitución", "Flores",
                "Floresta", "La Boca", "La Paternal", "Liniers", "Mataderos", "Monte Castro", "Monserrat", "Nueva Pompeya", "Núñez", "Palermo", "Parque Avellaneda",
                "Parque Chacabuco", "Parque Chas", "Parque Patricios", "Puerto Madero", "Recoleta", "Retiro", "Saavedra", "San Cristóbal", "San Nicolás", "San Telmo",
                "Vélez Sársfield", "Versalles", "Villa Crespo", "Villa del Parque", "Villa Devoto", "Villa General Mitre", "Villa Lugano", "Villa Luro", "Villa Ortúzar",
                "Villa Pueyrredón", "Villa Real", "Villa Riachuelo", "Villa Santa Rita", "Villa Soldati", "Villa Urquiza"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
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
