package nndb.com.parkinglotbctt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.ArrayList;

public class User_Item extends AppCompatActivity {
    public static final String URL_UPDATEUSER = "http://192.168.37.160:8182/CRUD-Operation/Updateuser.php";
    public static final String URL_ACCOUNT = "http://192.168.37.160:8182/CRUD-Operation/Account.php";
    TextView codetext;
    EditText editTextFullName, editTextEmail,editTextPhoneNumber, editAddress, editTextBirthday, editTextGender;
    String code1, codenew, codenv, strTextCode, strTextFullName, strTextEmail, strTextPhoneNumber, strAddress, strTextBirthday, strTextGender;
    ArrayList<String> holder = new ArrayList();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_itemuser);

        codetext =  (TextView) findViewById(R.id.taikhoan);
        editTextFullName =  (EditText) findViewById(R.id.edit_Fullname);
        editTextEmail =  (EditText) findViewById(R.id.edit_Email);
        editTextPhoneNumber =  (EditText) findViewById(R.id.edit_PhoneNumber);
        editAddress =  (EditText) findViewById(R.id.edit_Address);
        editTextBirthday =  (EditText) findViewById(R.id.edit_Day);
        editTextGender =  (EditText) findViewById(R.id.edit_Gender);

        String TextCode = getIntent().getStringExtra("ID");
        code1= TextCode;
//        String TextFullName = getIntent().getStringExtra("HoTen");
//        String TextEmail = getIntent().getStringExtra("Email");
//        String TextPhoneNumber = getIntent().getStringExtra("SoDT");
//        String TextAddress = getIntent().getStringExtra("DiaChi");
//        String TextBirthday = getIntent().getStringExtra("NgaySinh");
//        String TextGender = getIntent().getStringExtra("GioiTinh");

        try {
            codenew = code1;
            codenv = code1;

            String url = URL_ACCOUNT;
            String type = "data";
            crud_MySQL1(url, type,codenew, codenv);
        }catch (Exception e) {
            System.out.println("Android Studio " + e.getMessage());
        }

        codetext.setText(TextCode);
//        editTextFullName.setText(TextFullName);
//        editTextEmail.setText(TextEmail);
//        editTextPhoneNumber.setText(TextPhoneNumber);
//        editAddress.setText(TextAddress);
//        editTextBirthday.setText(TextBirthday);
//        editTextGender.setText(TextGender);

        Button buttonCheckIn= (Button)findViewById(R.id.btnUpdate);
        buttonCheckIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
                    strTextCode = codetext.getText().toString();
                    strTextFullName = editTextFullName.getText().toString();
                    strTextEmail = editTextEmail.getText().toString();
                    strTextPhoneNumber = editTextPhoneNumber.getText().toString();
                    strAddress = editAddress.getText().toString();
                    strTextBirthday = editTextBirthday.getText().toString();
                    strTextGender = editTextGender.getText().toString();

                    String url = URL_UPDATEUSER;
                    String type = "insert";
                    crud_MySQL(url, type, strTextCode, strTextFullName,strTextEmail, strAddress,strTextBirthday,strTextPhoneNumber, strTextGender);

                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });

    }// end onCreate

    void crud_MySQL(String url, String type, String textCode,String textFullName, String textEmail, String textStreet, String textBirthday, String textPhonenumber, String textGender)
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
                String MaNV = params[2];
                String HoTen = params[3];
                String Email = params[4];
                String DiaChi = params[5];
                String NgaySinh = params[6];
                String SoDT = params[7];
                String GioiTinh = params[8];

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
                        //post_data = URLEncoder.encode("number_plate", "UTF-8") + "=" + URLEncoder.encode(number_plate, "UTF-8") + "&"
                        // + URLEncoder.encode("check_in", "UTF-8") + "=" + URLEncoder.encode(check_in, "UTF-8");

                        post_data =URLEncoder.encode("MaNV", "UTF-8") + "=" + URLEncoder.encode(MaNV, "UTF-8") + "&"
                                + URLEncoder.encode("HoTen", "UTF-8") + "=" + URLEncoder.encode(HoTen, "UTF-8") + "&"
                                + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&"
                                + URLEncoder.encode("DiaChi", "UTF-8") + "=" + URLEncoder.encode(DiaChi, "UTF-8")+ "&"
                                + URLEncoder.encode("NgaySinh", "UTF-8") + "=" + URLEncoder.encode(NgaySinh, "UTF-8")+ "&"
                                + URLEncoder.encode("SoDT", "UTF-8") + "=" + URLEncoder.encode(SoDT, "UTF-8")+ "&"
                                + URLEncoder.encode("GioiTinh", "UTF-8") + "=" + URLEncoder.encode(GioiTinh, "UTF-8");
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
                        String MaNV1 = jo.getString("MaNV");
                        String HoTen1 = jo.getString("HoTen");
                        String Email1 = jo.getString("Email");
                        String DiaChi1 = jo.getString("DiaChi");
                        String NgaySinh1 = jo.getString("NgaySinh");
                        String SoDT1 = jo.getString("SoDT");
                        String GioiTinh1 = jo.getString("GioiTinh");
                        holder.add(MaNV1);
                        holder.add(HoTen1);
                        holder.add(Email1);
                        holder.add(DiaChi1);
                        holder.add(NgaySinh1);
                        holder.add(SoDT1);
                        holder.add(GioiTinh1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(User_Item.this);
        android_php.execute(url, type, textCode, textFullName, textEmail, textStreet, textBirthday, textPhonenumber, textGender);
    }
    void crud_MySQL1(String url, String type, String code, String codenv)
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
                String MaNV = params[3];

                if(type.equals("data")){
                    try {
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                        String post_data = "";
                        post_data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8")+ "&"
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
                        String HoTen1 = jo.getString("HoTen");
                        String Email1 = jo.getString("Email");
                        String DiaChi1 = jo.getString("DiaChi");
                        String NgaySinh1 = jo.getString("NgaySinh");
                        String SoDT1 = jo.getString("SoDT");
                        String GioiTinh1 = jo.getString("GioiTinh");
                        editTextFullName.setText(HoTen1);
                        editTextEmail.setText(Email1);
                        editTextPhoneNumber.setText(SoDT1);
                        editAddress.setText(DiaChi1);
                        editTextBirthday.setText(NgaySinh1);
                        editTextGender.setText(GioiTinh1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(User_Item.this);
        android_php.execute(url, type, code, codenv);
    }
}
