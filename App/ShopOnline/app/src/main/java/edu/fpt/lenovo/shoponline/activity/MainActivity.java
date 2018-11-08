package edu.fpt.lenovo.shoponline.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.adapter.LoaispAdapter;
import edu.fpt.lenovo.shoponline.adapter.SanphamAdapter;
import edu.fpt.lenovo.shoponline.model.Loaisp;
import edu.fpt.lenovo.shoponline.model.Sanpham;
import edu.fpt.lenovo.shoponline.ultil.Checkconection;
import edu.fpt.lenovo.shoponline.ultil.Server;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private Toolbar toolbartrangchu;
    private ViewFlipper Viewflipper;
    private RecyclerView recy;
    private NavigationView Navi;
    private ListView lvNavi;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    ArrayList<Loaisp> Mangloaisp;
    LoaispAdapter LoaispAdapter;


    int id = 0;
    String tenloaisp = "";
    String hinhloaisanpham = "";
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewflipper();
            Getdulieuloaisp();
            Getdulieusanphammoinhat();
            CachOnItemListView();
        } else {
            Checkconection.ShowToast_short(getApplicationContext(), "Ban hay kiem tra lai ket noi!   ");
            finish();
        }
    }

    private void CachOnItemListView() {
        lvNavi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại kết nối !");

                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại kết nối !");

                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisanpham", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại kết nối !");

                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        } else {
                            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại kết nối !");

                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        } else {
                            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại kết nối !");

                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Getdulieusanphammoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String Tensanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "";
                    int Idsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motasp");
                            Idsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID, Tensanpham, Giasanpham, Hinhanhsanpham, Motasanpham, Idsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhloaisanpham = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new Loaisp(id, tenloaisp, hinhloaisanpham));
                            LoaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    mangloaisp.add(3, new Loaisp(0, "Liên Hệ", "https://www.air-it.co.uk/wp-content/uploads/2015/02/kpi-icons-01.png"));
                    mangloaisp.add(4, new Loaisp(0, "Thông Tin", "http://www.mobilegiving.ca/wp-content/uploads/2015/06/icon_info_lg2.png"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconection.ShowToast_short(getApplicationContext(), error.toString());
            }

        });
        requestQueue.add(jsonArrayRequest);
    }


    private void ActionViewflipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://fptshop.com.vn/Uploads/images/2015/SANPHAM/MinhtriBin/11_02_dell-vostro-v5568i5-7200u-25ghz/dell-vostro-v5568i5-7200u-25ghz1.jpg");
        mangquangcao.add("https://www.lenovo.com/medias/ww-lenovo-thinkpad-x1-carbon-2017-feature4.png?context=bWFzdGVyfHJvb3R8OTAwMzd8aW1hZ2UvcG5nfGhhYi9oMzgvOTM1NzAyODIyOTE1MC5wbmd8NmY4MTY3OWY0NGI2ZDkzOTQ0MWI4NTBiZTJkNjg1OWRjM2JhMjI4MDBmNWU2OWZkMTJmOWMzMWU4ZjI3MWQ3NA&w=1920");
        mangquangcao.add("https://www.lenovo.com/medias/ww-lenovo-laptop-thinkpad-x1-carbon5-gallery-13.png?context=bWFzdGVyfHJvb3R8MTAyMzg0fGltYWdlL3BuZ3xoY2IvaGRiLzkzNTcwMjU3Mzg3ODIucG5nfDE0N2I5OWFkODllYjNjZmE0YTJiMWY3OTlkMmVhMzkwMTdhYmVlZWRiZDAyMDAzNGJlMTlmYTY0NGE0ZmNlNDY");
        mangquangcao.add("https://store.storeimages.cdn-apple.com/4981/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone/xs/iphone-xs-max-gold-select-2018?wid=1200&hei=630&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1535655075417");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Viewflipper.addView(imageView);
        }
        Viewflipper.setFlipInterval(5000);
        Viewflipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        Viewflipper.setInAnimation(animation_slide_in);
        Viewflipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbartrangchu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartrangchu.setNavigationIcon(R.drawable.menu);
        toolbartrangchu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toolbartrangchu = (Toolbar) findViewById(R.id.toolbartrangchu);
        Viewflipper = (ViewFlipper) findViewById(R.id.Viewflipper);
        recy = (RecyclerView) findViewById(R.id.recy);
        Navi = (NavigationView) findViewById(R.id.Navi);
        lvNavi = (ListView) findViewById(R.id.lvNavi);
        mangloaisp = new ArrayList<>();
        loaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        lvNavi.setAdapter(loaispAdapter);
        Mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "Trang Chủ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSezDEUYGChOZMdZHGM3BSsEGqzGiSgoT0YNrnGrKRVmm6Qj5OC"));
        LoaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        lvNavi.setAdapter(LoaispAdapter);
        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(), mangsanpham);
        recy.setHasFixedSize(true);
        recy.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recy.setAdapter(sanphamAdapter);
    }


}
