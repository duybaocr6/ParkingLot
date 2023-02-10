package nndb.com.parkinglotbctt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // POST Method
    public static final String URL_LOGIN = "http://192.168.37.160:8182/CRUD-Operation/Login.php";
//    public static final String URL_Count = "http://192.168.37.160:8182/CRUD-Operation/Ticket.php";
    public static final String URL_SELECT = "http://192.168.37.160:8182/CRUD-Operation/Select.php";
    Button btnForgotPassword, btnLogin, btnRegister;
    EditText editUserName,editPassword;
    String username,pass_word, position;
    RadioGroup radioGroup;
    RadioButton radioButton;

    ArrayList<String> holder = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin);

        editUserName =  (EditText) findViewById(R.id.username);
        editPassword =  (EditText) findViewById(R.id.password);
        btnForgotPassword =  (Button) findViewById(R.id.forgotpassword);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Forgot_Password.class);
                startActivity(intent);
            }
        });
        btnLogin= (Button)findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try {
                    username = editUserName.getText().toString();
                    pass_word = editPassword.getText().toString();

                    radioGroup = (RadioGroup) findViewById(R.id.user);
                    int select = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(select);
                    position = radioButton.getText().toString().toLowerCase();

                    String url = URL_LOGIN;
                    String type = "login";
                    crud_MySQL(url, type, username, pass_word, position);
                } catch (Exception e) {
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });
        btnRegister= (Button)findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
//                try {
//                    String url2 = URL_Count;
//                    String type2 = "insert";
//                    crud_MySQL2(url2, type2);
//                } catch (Exception e) {
//                    System.out.println("Android Studio " + e.getMessage());
//                }
                Intent intent = new Intent(MainActivity.this, Layout_Register.class);
                startActivity(intent);
            }
        });
    }// end onCreate

    void crud_MySQL(String url, String type, String editUsername1, String editPassword1, String position1 )
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
                String TaiKhoan = params[2];
                String MatKhau = params[3];
                String ChucVu = params[4];

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

                        post_data = URLEncoder.encode("TaiKhoan", "UTF-8") + "=" + URLEncoder.encode(TaiKhoan, "UTF-8") + "&"
                                + URLEncoder.encode("MatKhau", "UTF-8") + "=" + URLEncoder.encode(MatKhau, "UTF-8")+ "&"
                                + URLEncoder.encode("ChucVu", "UTF-8") + "=" + URLEncoder.encode(ChucVu, "UTF-8");
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
                        String ID1 = jo.getString("ID");
                        String TaiKhoan1 = jo.getString("TaiKhoan");
                        String MatKhau1 = jo.getString("MatKhau");
                        String ChucVu1 = jo.getString("ChucVu");
                        holder.add(ID1);
                        holder.add(TaiKhoan1);
                        holder.add(MatKhau1);
                        holder.add(ChucVu1);
                        if (username.equals(TaiKhoan1) && pass_word.equals(MatKhau1) && position.equals("admin")) {
                            Intent intent = new Intent(MainActivity.this, Layout_Interface.class);
                            intent.putExtra("ID", ID1);
                            intent.putExtra("ChucVu", ChucVu1);
                            startActivity(intent);
                        }
                        else if (username.equals(TaiKhoan1) && pass_word.equals(MatKhau1) && position.equals("employee")){
                            Intent intent1 = new Intent(MainActivity.this, Layout_Employees.class);
                            intent1.putExtra("ID", ID1);
                            intent1.putExtra("ChucVu", ChucVu1);
                            startActivity(intent1);
                        }
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(MainActivity.this);
        android_php.execute(url, type, editUsername1, editPassword1, position1);
    }

    void crud_MySQL1(String url, String type)
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

                if(type.equals("select")){
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
                        String MatKhau1 = jo.getString("MatKhau");
                        Intent intent = new Intent(MainActivity.this,Forgot_Password.class);
                        intent.putExtra("MatKhau", MatKhau1);
                        startActivity(intent);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(MainActivity.this);
        android_php.execute(url, type);
    }

    void crud_MySQL2(String url, String type)
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

                if(type.equals("insert")){
                    try {
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                        String post_data = "";
//                        post_data = URLEncoder.encode("SO", "UTF-8") + "=" + URLEncoder.encode(SO, "UTF-8");
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
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String Email1 = jo.getString("MaQL");
//                        String [] a = Email1.split(" ");
//                        String b = a[0];
//                        int c = Integer.parseInt(b)+1;
//                        String d;
//                        if( c < 10) {
//                            d = "B0"+Integer.toString(c);
//                        }
//                        else {
//                            d = "B"+Integer.toString(c);
//                        }
////                        editTextFullName.setText(d);
                        Intent intent = new Intent(MainActivity.this, Layout_Register.class);
                        intent.putExtra("MaQL", Email1);
                        startActivity(intent);
                    }
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(MainActivity.this);
        android_php.execute(url, type);
    }

//    public void diplayResult(View view)
//    {
//        try {
//            String url2 = URL_CountAdmin;
//            String type2 = "insert";
//            crud_MySQL2(url2, type2);
//        } catch (Exception e) {
//            System.out.println("Android Studio " + e.getMessage());
//        }
//    }
//    public void forgotpass(View view)
//    {
//        try{
//            String url = URL_SELECT;
//            String type = "select";
//            crud_MySQL1(url, type);
//        } catch(Exception e) {
//            System.out.println("Android Studio " + e.getMessage());
//        }
//    }
}