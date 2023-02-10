package nndb.com.parkinglotbctt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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

public class Listview_User extends AppCompatActivity implements TextWatcher {
    // POST Method
    public static final String URL_READ = "http://192.168.37.160:8182/CRUD-Operation/ListUser.php";

    String code;
    ListView lv;
    EditText editsearch;
    UserAdapter at;
    ArrayList<User> holder;
    //    ArrayList<String> holder = new ArrayList();
//    ArrayAdapter<String> at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listviewuser);

        String TextCode = getIntent().getStringExtra("ID1");
        code = TextCode;
        lv = (ListView)findViewById(R.id.listview);
        try{
            String url = URL_READ;
            String type = "read";
            String code1 = code;
            crud_MySQL(url, type, code1);
        } catch(Exception e){
            System.out.println("Android Studio " + e.getMessage());
        }

        editsearch = (EditText) findViewById(R.id.search);
        editsearch.addTextChangedListener(this);
    }// end onCreate

    void crud_MySQL(String url, String type, String Maql)
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

                if(type.equals("read")){
                    try {
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                        String post_data = "";
                        post_data = URLEncoder.encode("MaQL", "UTF-8") + "=" + URLEncoder.encode(MaQL, "UTF-8");
                        // + URLEncoder.encode("check_in", "UTF-8") + "=" + URLEncoder.encode(check_in, "UTF-8");

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
                    holder = new ArrayList();
                    holder.clear();
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String HoTen1 = jo.getString("HoTen");
                        String Email1 = jo.getString("Email");
                        String DiaChi1 = jo.getString("DiaChi");
                        String NgaySinh1 = jo.getString("NgaySinh");
                        String SoDT1 = jo.getString("SoDT");
                        String GioiTinh1 = jo.getString("GioiTinh");
                        holder.add(new User(HoTen1, Email1, DiaChi1, NgaySinh1, SoDT1,GioiTinh1));
                    }
                    at = new UserAdapter(getApplicationContext(),holder);
                    lv.setAdapter(at);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Listview_User.this);
        android_php.execute(url,type, Maql);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.at.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
