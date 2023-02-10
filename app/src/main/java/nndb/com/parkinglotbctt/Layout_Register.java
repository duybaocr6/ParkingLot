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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Layout_Register extends AppCompatActivity {
    // POST Method
    public static final String URL_LOGIN = "http://192.168.37.160:8182/CRUD-Operation/Register.php";
    public static final String URL_Count = "http://192.168.37.160:8182/CRUD-Operation/Ticket.php";
    Button btnRegister;
    EditText editCode,editUserName,editPassword,editConfirmpassword;
    String code, username,pass_word, confirmpassword, position;
//    int c;
    ArrayList<String> holder = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        editCode =  (EditText) findViewById(R.id.code);

        editUserName =  (EditText) findViewById(R.id.username);
        editPassword =  (EditText) findViewById(R.id.password);
        editConfirmpassword =  (EditText) findViewById(R.id.confirm_password);

        btnRegister= (Button)findViewById(R.id.btnregister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try {
                    code = editCode.getText().toString().toUpperCase();
                    String url2 = URL_Count;
                    String type2 = "insert";
                    crud_MySQL2(url2, type2, code);
                } catch (Exception e) {
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });
    }// end onCreate

    void crud_MySQL(String url, String type, String code, String editUsername1, String editPassword1, String position1 )
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
                String ID = params[2];
                String TaiKhoan = params[3];
                String MatKhau = params[4];
                String ChucVu = params[5];

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

                        post_data =URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8") + "&"
                                + URLEncoder.encode("TaiKhoan", "UTF-8") + "=" + URLEncoder.encode(TaiKhoan, "UTF-8") + "&"
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Register.this);
        android_php.execute(url, type, code, editUsername1, editPassword1, position1);
    }
    void crud_MySQL2(String url, String type, String code)
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
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String Email1 = jo.getString("MaQL");
                        int c = Integer.parseInt(Email1);
                        try{
                            String code = editCode.getText().toString().toUpperCase();
                            username = editUserName.getText().toString();
                            pass_word = editPassword.getText().toString();
                            confirmpassword = editConfirmpassword.getText().toString();
        //                    position = record.toLowerCase();
                            if (c>0 && pass_word.equals(confirmpassword))
                            {
                                code = editCode.getText().toString().toUpperCase();
                                username = editUserName.getText().toString();
                                pass_word = editPassword.getText().toString();
                                confirmpassword = editConfirmpassword.getText().toString();
                                position= "admin";

                                String url = URL_LOGIN;
                                String type = "login";
                                crud_MySQL(url, type, code, username, pass_word,position);
                                Intent intent = new Intent(Layout_Register.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else if (c==0 && pass_word.equals(confirmpassword))
                            {
                                code = editCode.getText().toString().toUpperCase();
                                username = editUserName.getText().toString();
                                pass_word = editPassword.getText().toString();
                                confirmpassword = editConfirmpassword.getText().toString();
                                position= "employee";

                                String url = URL_LOGIN;
                                String type = "login";
                                crud_MySQL(url, type, code, username, pass_word,position);
                                Intent intent = new Intent(Layout_Register.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Layout_Register.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch(Exception e) {
                            System.out.println("Android Studio " + e.getMessage());
                        }
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Register.this);
        android_php.execute(url, type, code);
    }
}
