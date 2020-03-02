package com.example.vollydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 慕课网 volley 学习
 * @author dhl
 */
public class MainActivity extends AppCompatActivity {


    private AppCompatButton btn_get;

    private AppCompatButton btn_post;

    private  AppCompatButton btn_img ;

    private AppCompatButton btn_img_image_loader ;

    private AppCompatButton btn_net_img ;

    private static final String TAG = "MainActivity";

    private ImageView imageView ;

    private NetworkImageView net_img ;

    private String url = "http://a4.att.hudong.com/21/09/01200000026352136359091694357.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_get = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        btn_img = findViewById(R.id.btn_img);
        imageView = findViewById(R.id.image);
        btn_img_image_loader = findViewById(R.id.btn_img_image_loader);
        btn_net_img = findViewById(R.id.btn_net_img);
        net_img = findViewById(R.id.net_img);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyGet("18796017665");
            }
        });
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyPost("18796017665");
            }
        });
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyImage(url);
            }
        });

        btn_img_image_loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String url) {
                        return null;
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {

                    }
                });
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                imageLoader.get(url,imageListener);
            }
        });
        btn_net_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net_img.setDefaultImageResId(R.mipmap.ic_launcher);
                net_img.setImageUrl(url,new ImageLoader(MyApplication.getRequestQueue(), new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String url) {
                        return null;
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {

                    }
                }));
            }
        });

    }

    private void volleyGet(String phone) {
        String url = "http://api.online-service.vip/phone?number=" + phone;
      /*  StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG,"response:"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setTag("get");
        MyApplication.getRequestQueue().add(request);*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e(TAG, "response:" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyApplication.getRequestQueue().add(jsonObjectRequest);

    }

    private void volleyPost(final String phone) {
        String url = "http://api.online-service.vip/phone"; // 这个接口不支持 post
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG,"response:"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("number",phone);
                return map;
            }
        };
        MyApplication.getRequestQueue().add(request);

    }

    private void volleyImage(String url)
    {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 800, 800, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
        });
        MyApplication.getRequestQueue().add(imageRequest);
    }

}
