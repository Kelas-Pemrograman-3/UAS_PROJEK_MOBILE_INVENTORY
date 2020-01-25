package project.uas.febri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.febri.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.ConfigUrl;

public class LihatDataActivity extends AppCompatActivity {

  private RequestQueue mRequestQueue;
  private TextView vdatabarang;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lihat_data);

    getSupportActionBar().hide();

    mRequestQueue = Volley.newRequestQueue(this);

  vdatabarang = (TextView) findViewById(R.id.tvdatabarang);
    fetchJsonResponse();
  }
  @Override
  public void onBackPressed(){
    Intent i = new Intent(LihatDataActivity.this, MainActivity.class);
    startActivity(i);
    finish();

    super.onBackPressed();
  }
  private void fetchJsonResponse() {
      // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ConfigUrl.getAllLihatData, null,
                new Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {
                    try {
                      String result = response.getString("data");
                      //           Toast.makeText(LihatDataActivity.this, result, Toast.LENGTH_SHORT).show();
                      //Log.v("Ini data dari server", result.toString());

                      JSONArray res = new JSONArray(result);
                      for (int i = 0; i < res.length(); i++) {
                        JSONObject jobj = res.getJSONObject(i);
                        vdatabarang.append("Kode Barang = " + jobj.getString("KodeBarang") + "\n" +
                          "Nama Barang = " + jobj.getString("Nama Barang") + "\n" +
                          "Tanggal Masuk = " + jobj.getString("Tanggal Masuk") + "\n" +
                          "Stok = " + jobj.getString("Stok") + "\n"

                        );
                      }

                    } catch (JSONException e) {
                      e.printStackTrace();
                    }
                  }

  }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

            VolleyLog.e("Error: ", error.getMessage());
          }
        });
            mRequestQueue.add(req);

          }
        }


