package nndb.com.parkinglotbctt;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
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

public class Current_Information extends AppCompatActivity {
    public static final String URL_CREATE = "http://192.168.37.160:8182/CRUD-Operation/Insertdatetime.php";
    EditText editTextCodeM, editTextName, editPosition, editCodeE;
    String strTextCodeM, strTextName, strPosition, strCodeE;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_currentinformation);

        editTextCodeM =  (EditText) findViewById(R.id.edit_CodeM);
        editTextName =  (EditText) findViewById(R.id.edit_Name);
        editPosition =  (EditText) findViewById(R.id.edit_Position);
        editCodeE = (EditText) findViewById(R.id.edit_CodeE);
        Button buttonCheckIn= (Button)findViewById(R.id.btnCheckIn);
        buttonCheckIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
                    strTextCodeM = editTextCodeM.getText().toString();
                    strTextName = editTextName.getText().toString();
                    strPosition = editPosition.getText().toString();
                    strCodeE = editCodeE.getText().toString();

                    String url = URL_CREATE;
                    String type = "update";
                    crud_MySQL(url, type, strTextCodeM,strTextName, strPosition, strCodeE);

                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });

    }// end onCreate

    void crud_MySQL(String url, String type, String textCodeM, String textName, String Position, String CodeE)
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
                String MaQL = params[2];
                String TenNV = params[3];
                String TenCV = params[4];
                String MaNV = params[5];

                if(type.equals("update")){
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

                        post_data = URLEncoder.encode("MaQL", "UTF-8") + "=" + URLEncoder.encode(MaQL, "UTF-8") + "&"
                                + URLEncoder.encode("TenNV", "UTF-8") + "=" + URLEncoder.encode(TenNV, "UTF-8") + "&"
                                + URLEncoder.encode("TenCV", "UTF-8") + "=" + URLEncoder.encode(TenCV, "UTF-8")+ "&"
                                + URLEncoder.encode("MaNV", "UTF-8") + "=" + URLEncoder.encode(MaNV, "UTF-8");
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
            }

            @Override
            protected void onPostExecute(String result) {
                try{
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String MaQL1 = jo.getString("MaQL");
                        String TenNV1 = jo.getString("TenNV");
                        String TenCV1 = jo.getString("TenCV");
                        String MaNV1 = jo.getString("MaNV");
                    }
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Current_Information.this);
        android_php.execute(url, type,  textCodeM, textName, Position, CodeE);
    }
}
