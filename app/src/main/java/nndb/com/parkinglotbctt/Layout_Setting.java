package nndb.com.parkinglotbctt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Layout_Setting extends AppCompatActivity {
    // POST Method
    public static final String URL_LOGIN = "http://192.168.37.160:8182/CRUD-Operation/Settings.php";
    Button btnRegister;
    TextView name, name1;
    String typevehicle, typevehicle1, pricecar1, pricemoto1;

    ArrayList<String> holder = new ArrayList();

    Spinner sp,sp1;
    String record= "",record1= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);

        name = (TextView) findViewById(R.id.Car);
        name1 = (TextView) findViewById(R.id.Motorcycle);

        sp = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.array_pricecar,R.layout.select_colorspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        record = "10000";
                        break;
                    case 1:
                        record = "15000";
                        break;
                    case 2:
                        record = "20000";
                        break;
                    case 3:
                        record = "25000";
                        break;
                    case 4:
                        record = "30000";
                        break;
                    case 5:
                        record = "35000";
                        break;
                    case 6:
                        record = "40000";
                        break;
                    case 7:
                        record = "45000";
                        break;
                    case 8:
                        record = "50000";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sp1 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.array_pricemoto,R.layout.select_colorspinner);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        record1 = "2000";
                        break;
                    case 1:
                        record1 = "3000";
                        break;
                    case 2:
                        record1 = "4000";
                        break;
                    case 3:
                        record1 = "5000";
                        break;
                    case 4:
                        record1 = "6000";
                        break;
                    case 5:
                        record1 = "7000";
                        break;
                    case 6:
                        record1 = "8000";
                        break;
                    case 7:
                        record1 = "9000";
                        break;
                    case 8:
                        record1 = "10000";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnRegister= (Button)findViewById(R.id.btnsetup);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
                    typevehicle = name.getText().toString().toLowerCase();
                    pricecar1 = record;
                    String url = URL_LOGIN;
                    String type = "login";
                    crud_MySQL(url, type, typevehicle, pricecar1);
                } catch(Exception e) {
                    System.out.println("Android Studio " + e.getMessage());
                }
                try{
                    typevehicle1 = name1.getText().toString().toLowerCase();
                    pricemoto1 = record1;
                    String url = URL_LOGIN;
                    String type = "login";
                    crud_MySQL(url, type, typevehicle1, pricemoto1);
                } catch(Exception e) {
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });
    }// end onCreate

    void crud_MySQL(String url, String type, String TenLoai, String GiaVe )
    {
        class AndroidComunicatesPHPServevr extends AsyncTask<String,Void,String> {
            Context context;
            AlertDialog alertDialog;
            AndroidComunicatesPHPServevr(Context ctx){
                context = ctx;
            }
            @Override
            protected String doInBackground(String... params) {
                String login_url = params[0];
                String type = params[1];
                String TenLoai = params[2];
                String GiaVe = params[3];

                if(type.equals("login")){
                    try {
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                        String post_data = "";
                        //post_data = URLEncoder.encode("number_plate", "UTF-8") + "=" + URLEncoder.encode(number_plate, "UTF-8") + "&"
                        // + URLEncoder.encode("check_in", "UTF-8") + "=" + URLEncoder.encode(check_in, "UTF-8");

                        post_data =URLEncoder.encode("TenLoai", "UTF-8") + "=" + URLEncoder.encode(TenLoai, "UTF-8")+ "&"
                                + URLEncoder.encode("GiaVe", "UTF-8") + "=" + URLEncoder.encode(GiaVe, "UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();
                        //Nhận data từ android
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        String result = "";
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return result;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }


            @Override
            protected void onPreExecute() {
                alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Notification");
            }

            @Override
            protected void onPostExecute(String result) {
                try{
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    holder.clear();
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String TenLoai1 = jo.getString("TenLoai");
                        String GiaVe1 = jo.getString("GiaVe");
                        holder.add(TenLoai1);
                        holder.add(GiaVe1);
                    }
                }catch(JSONException ex){
                    Toast.makeText(context.getApplicationContext(),result,Toast.LENGTH_LONG).show();
                    ex.getMessage();
                }
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Setting.this);
        android_php.execute(url, type, TenLoai, GiaVe);
    }
}

