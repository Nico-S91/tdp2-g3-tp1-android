package tp1.g3.tdp2.hoycomo.Activdades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import tp1.g3.tdp2.hoycomo.Adapter.ComercioAdapter;
import tp1.g3.tdp2.hoycomo.Dominio.ComercioList;
import tp1.g3.tdp2.hoycomo.Helpers.MyData;
import tp1.g3.tdp2.hoycomo.Helpers.ToastMessage;
import tp1.g3.tdp2.hoycomo.Modelos.Comercio;
import tp1.g3.tdp2.hoycomo.R;
import tp1.g3.tdp2.hoycomo.Server.ApiRestConsumer;
import tp1.g3.tdp2.hoycomo.Tasks.GetComercios2Task;
import tp1.g3.tdp2.hoycomo.Tasks.GetComerciosTask;
import tp1.g3.tdp2.hoycomo.activities.HomeActivity;
import tp1.g3.tdp2.hoycomo.activities.MainActivity;

public class ComerciosListado extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private MyData pref;
    private ListView listView;
    private EditText iSearch;
    private ComercioList comercios;
    private ComercioAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercios_list);

        ActionBar actionBarInstance = getSupportActionBar();
        if (actionBarInstance != null){
            actionBarInstance.setDisplayHomeAsUpEnabled(true);
        }

        pref = new MyData(this);

        listView = findViewById(R.id.lstComercios);
        listView.setTextFilterEnabled(true);
        iSearch = findViewById(R.id.inputSearch);

        progressDialog = new ProgressDialog(ComerciosListado.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Cargando comercios...");
        progressDialog.show();

        if (ApiRestConsumer.isOnline(this)) {
            new GetComercios2Task(ComerciosListado.this).execute(getString(R.string.url_Comercios));
        }

        /* Activando el filtro de busqueda */
        iSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                adapter.getFilter().filter(arg0.toString());
                Log.i("Busqueda", ""+arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ComerciosListado.this, HomeActivity.class));
                break;
        }
        return true;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_profile:
                intent = new Intent(AlbumsList.this, Profile.class);
                startActivity(intent);
                break;
            case R.id.nav_tracks:
                finish();
                break;
            case R.id.nav_logout:
                intent = new Intent(AlbumsList.this, Login.class);
                startActivity(intent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
*/
    public void onGetComerciosSuccess(ComercioList comercioList) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (comercioList != null) {
            comercios = comercioList;
            adapter = new ComercioAdapter(this, comercios.getComercios());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    Comercio comercio = (Comercio) listView.getAdapter().getItem(position);
                    ToastMessage.toastMessageCorto(ComerciosListado.this, comercio.getNombre().toString());
                    /*Intent intent;
                    intent = new Intent(ComerciosListado.this, MenuList.class);
                    intent.putExtra("albumId", comercio.getId());
                    startActivity(intent);*/
                }

            });
        }
    }
}