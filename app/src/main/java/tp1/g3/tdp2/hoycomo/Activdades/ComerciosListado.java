package tp1.g3.tdp2.hoycomo.Activdades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import tp1.g3.tdp2.hoycomo.Modelos.Comercio;
import tp1.g3.tdp2.hoycomo.R;
import tp1.g3.tdp2.hoycomo.Tasks.GetComerciosTask;

public class ComerciosListado extends Activity {
    private static final String TAG = ComerciosListado.class.getName();
    public static final int RESULT_FAIL = -999;
    private static final int INTERNET_PERMISSION_REQ_CODE = 100;
    private static List<Comercio> comercios;
    private static int page = 1;
    private ArrayAdapter<Comercio> adapter;
    private String srvUrl;
    private static AtomicBoolean ComerciosCargado = new AtomicBoolean(false);
    private View progressLayout;
    private AtomicBoolean bottomReached = new AtomicBoolean(false);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comercios_listado);

        srvUrl = getString(R.string.srv_base_url);
        progressLayout = findViewById(R.id.main_progress_layout);
        hideProgress();
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            run();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_REQ_CODE);
        }
    }

    private void hideProgress () {
        progressLayout.setVisibility(View.GONE);
    }

    private void showProgress () {
        progressLayout.setVisibility(View.VISIBLE);
    }
    private void run() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        setListAdapter(adapter);
        getListView().setOnScrollListener(this);

        if (ComerciosCargado.get()) {
            adapter.addAll(comercios);
        } else {
            new GetComerciosTask(
                    srvUrl,
                    this::showProgress,
                    response -> {
                        if (response.success) {
                            comercios = new ArrayList<>(response.data);
                            ComerciosCargado.set(comercios.size() > 0);
                            adapter.addAll(comercios);
                        } else {
                            Toast.makeText(this, R.string.srv_conn_err, Toast.LENGTH_SHORT).show();
                            finishFail();
                        }
                        hideProgress();
                    }).execute(++page);
        }
    }
    private void finishWithResult(Comercio comercio) {
        Bundle conData = new Bundle();
        conData.putSerializable(Comercio, comercio);
        Intent intent = new Intent();

        intent.putExtras(conData);
        setResult(RESULT_OK, intent);

        finish();
    }

    private void finishFail() {
        Intent intent = new Intent();
        setResult(RESULT_FAIL, intent);
        finish();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // SI hay pocos items, hacer nada
        if (visibleItemCount == totalItemCount) return;

        Log.d(TAG, "listView.getChildCount(): " + listView.getChildCount());

        int itemDiff = Math.abs(firstVisibleItem + visibleItemCount - totalItemCount);
        Log.d(TAG, "itemDiff: " + itemDiff);
        if (itemDiff <= 1) {
            Log.d(TAG, "BOTTOM REACHED!");
            if (bottomReached.compareAndSet(false, true)) {
                onBottomScrollReached();
            }
        }
        Log.d(TAG, "firstVisibleItem: " + firstVisibleItem + ", visibleItemCount: " + visibleItemCount + ", totalItemCount: " + totalItemCount);
    }
    private void onBottomScrollReached() {
        try {
            new GetComerciosTask(
                    srvUrl,
                    this.showProgress(),
                    response -> {
                        try {
                            if (response.success) {
                                if (response.data == null || response.data.isEmpty()) {
                                    Toast.makeText(this, getString(R.string.no_info), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                comercios.addAll(response.data);
                                adapter.addAll(response.data);
                                Log.d(TAG, "MORE CITIES LOADED!");
                            } else {
                                Toast.makeText(this, getString(R.string.srv_conn_err), Toast.LENGTH_SHORT).show();
                            }
                            bottomReached.set(false);
                        } finally {
                            hideProgress();
                        }
                    }).execute(++page);
        } catch (Exception e) {
            Log.e(TAG, getString(R.string.comercio_get_error), e);
            bottomReached.set(false);
            hideProgress();
        }
    }
}

