package nndb.com.parkinglotbctt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Forgot_Password extends AppCompatActivity {
    // POST Method
    public static final String URL_LOGIN = "http://192.168.37.160:8182/CRUD-Operation/Forgotpassword.php";
    Button btnForgot;
    EditText editCode,editUserName,editPassword,editConfirmpassword;
    String count, code, code1, username,pass_word, confirmpassword;

    ArrayList<String> holder = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgotpassword);

        String TextCode = getIntent().getStringExtra("MatKhau");
        code1 = TextCode;

        editCode =  (EditText) findViewById(R.id.code);
        editUserName =  (EditText) findViewById(R.id.username);
        editPassword =  (EditText) findViewById(R.id.newpassword);
        editConfirmpassword =  (EditText) findViewById(R.id.confirm_password);

        btnForgot= (Button)findViewById(R.id.btnforgotpass);
        btnForgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
                    code = editCode.getText().toString().toUpperCase();
                    username = editUserName.getText().toString();
                    pass_word = editPassword.getText().toString();
                    confirmpassword = editConfirmpassword.getText().toString();
//                    if (!code1.equals(pass_word)) {
//                        Toast.makeText(getApplicationContext(), "Password already exists ", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
                        if (pass_word.equals(confirmpassword)) {
                            String url = URL_LOGIN;
                            String type = "login";
                            crud_MySQL(url, type, code, username, pass_word);
                            Intent intent = new Intent(Forgot_Password.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            editPassword.getText().clear();
                            editConfirmpassword.getText().clear();
                            Toast.makeText(getApplicationContext(), "Password is not the same! Please try again ", Toast.LENGTH_LONG).show();
                        }
//                    }
                } catch(Exception e) {
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });
    }// end onCreate

    void crud_MySQL(String url, String type, String code, String editUsername1, String editPassword1 )
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
                                + URLEncoder.encode("MatKhau", "UTF-8") + "=" + URLEncoder.encode(MatKhau, "UTF-8");
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
                        holder.add(ID1);
                        holder.add(TaiKhoan1);
                        holder.add(MatKhau1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Forgot_Password.this);
        android_php.execute(url, type, code, editUsername1, editPassword1);
    }
}
