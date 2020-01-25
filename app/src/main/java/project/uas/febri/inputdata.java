package project.uas.febri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.febri.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;

public class inputdata extends AppCompatActivity {
  private RequestQueue mRequestQueue;
  private TextView txtData;
  private EditText edtKodeBarang, edtNamaBarang, edtTanggalMasuk, edtStok;

  private Button btnMenu;
  private Button btnTambah;
  private ProgressDialog pDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_input_data);

    getSupportActionBar().hide();
    mRequestQueue = Volley.newRequestQueue(this);

    edtKodeBarang = (EditText) findViewById(R.id.edtKodeBarang);
    edtNamaBarang = (EditText) findViewById(R.id.edtNamamBarang);
    edtTanggalMasuk = (EditText) findViewById(R.id.edtTanggalMasuk);
    edtStok = (EditText) findViewById(R.id.edtStok);

    btnTambah = (Button) findViewById(R.id.btnTambah);
    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    btnTambah.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String strKodeBarang = edtKodeBarang.getText().toString();
        String strNamaBarang = edtNamaBarang.getText().toString();
        String strTanggalMasuk = edtTanggalMasuk.getText().toString();
        String strStok = edtStok.getText().toString();

        if (strKodeBarang.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Kode Barang Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strNamaBarang.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Nama Barang Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strTanggalMasuk.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Tanggal Masuk Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strStok.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Stok Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else {
          inputData(strKodeBarang, strNamaBarang, strTanggalMasuk, strStok);
          edtKodeBarang.setText("");
          edtNamaBarang.setText("");
          edtTanggalMasuk.setText("");
          edtStok.setText("");
          edtKodeBarang.requestFocus();
        }
      }

      private void inputData(String strKodeBarang, String strNamamBarang, String strTanggalMasuk, String strStok) {
      }
    });

    getSupportActionBar().hide();

    btnMenu = (Button) findViewById(R.id.btnMenu);

    btnMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(inputdata.this, MainActivity.class);
        startActivity(i);
        finish();
      }
    });
//        fetchJsonResponse();
  }

  private void inputData(String KodeBarang, String NamamBarang, String TanggalMasuk, String Stok){

// Post params to be sent to the server
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("Kode Barang", KodeBarang);
    params.put("Nama Barang", NamamBarang);
    params.put("Tanggal Masuk", TanggalMasuk);
    params.put("Stok", Stok);

    pDialog.setMessage("Please Wait");
    showDialog();

    JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.inputDataBarang, new JSONObject(params),
      new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
          hideDialog();
          try {
            boolean status = response.getBoolean("eror");
            String msg;
            if (status == true) {
              msg = response.getString("pesan");
            } else {
              msg = response.getString("pesan");
              Intent i = new Intent(inputdata.this,MainActivity.class);
              startActivity(i);
              finish();
            }
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            VolleyLog.v("Response:%n %s", response.toString(7));
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        hideDialog();

        VolleyLog.e("Error: ", error.getMessage());
      }
    });

// add the request object to the queue to be executed
    //ApplicationController.getInstance().addToRequestQueue(req);

    mRequestQueue.add(req);
  }

  private void showDialog() {
    if (!pDialog.isShowing())
      pDialog.show();
  }
  private void hideDialog() {
    if (pDialog.isShowing())
      pDialog.dismiss();
  }
}

