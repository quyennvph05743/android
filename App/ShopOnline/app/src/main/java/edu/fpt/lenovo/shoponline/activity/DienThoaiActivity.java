package edu.fpt.lenovo.shoponline.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.adapter.DienThoaiAdapter;
import edu.fpt.lenovo.shoponline.model.Sanpham;
import edu.fpt.lenovo.shoponline.ultil.Checkconection;
import edu.fpt.lenovo.shoponline.ultil.Server;

public class DienThoaiActivity extends AppCompatActivity {

    private Toolbar mToolbardt;
    private ListView mLvDT;

    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        AnhXa();
        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
            GetIdloaisp();
            ActionToolBar();
            GetData(page);
        } else {
            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại Internet");
            finish();
        }

    }

    private void GetData(final int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdienthoai + String.valueOf(Page);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(duongdan, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String Tendt = "";
                int giadt = 0;
                String Hinhdt = "";
                String Motadt = "";
                int Idspdt = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensp");
                            giadt = jsonObject.getInt("giasp");
                            Hinhdt = jsonObject.getString("hinhanhsp");
                            Motadt = jsonObject.getString("motasp");
                            Idspdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id, Tendt, giadt, Hinhdt, Motadt, Idspdt));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconection.ShowToast_short(getApplicationContext(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(iddt));
                return super.getParams();
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(mToolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void GetIdloaisp() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("gia tri loai san pham", iddt + "");
    }

    private void AnhXa() {
        mToolbardt = (Toolbar) findViewById(R.id.toolbardt);
        mLvDT = (ListView) findViewById(R.id.lvDT);
        mangdt = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), mangdt);
        mLvDT.setAdapter(dienThoaiAdapter);
    }
}
