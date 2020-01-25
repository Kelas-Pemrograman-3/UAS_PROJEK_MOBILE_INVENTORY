package project.uas.febri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.project.febri.R;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

import session.SessionManager;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private SessionManager session;
    private CircularRevealCardView cardKeluar;
    private ImageView tambahData;
  private ImageView lihatdata;
    private  Button btnMenu;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        tambahData = (ImageView) findViewById(R.id.tambahdata);
        tambahData.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent i = new Intent(MainActivity.this, inputdata.class);
          startActivity(i);
          finish();
        }
      });

      lihatdata = (ImageView) findViewById(R.id.lihatdata);
      lihatdata.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent i = new Intent(MainActivity.this,LihatDataActivity.class);
          startActivity(i);
          finish();
        }
      });

        session = new SessionManager(this);
        cardKeluar = (CircularRevealCardView) findViewById(R.id.cardKeluar);
        cardKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSkip(false);
                session.setSessid(0);

                Intent i = new Intent(MainActivity.this,Loginactivity.class);
                startActivity(i);
                finish();
            }
        });
      }
    }



//    private void fetchJsonResponse() {
//        // Pass second argument as "null" for GET requests
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ConfigUrl.getAllMhs, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String result = response.getString("data");
//                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
//                            //Log.v("Ini data dari server", result.toString());
//
//                            JSONArray res = new JSONArray(result);
//                            for (int i = 0; i < res.length(); i++){
//                                JSONObject jobj = res.getJSONObject(i);
//                                txtData.append("Npm = " + jobj.getString("npm") + "\n"+
//                                               "Nama = " + jobj.getString("nama") + "\n\n");
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
//
//        /* Add your Requests to the RequestQueue to execute */
//        mRequestQueue.add(req);
//    }

