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

public class Layout_User extends AppCompatActivity {
    public static final String URL_CREATE = "http://192.168.37.160:8182/CRUD-Operation/InsertEmploy.php";
    EditText editTextCode, editTextFullName, editTextEmail,editTextPhoneNumber, editAddress, editTextBirthday, editTextGender;
    String code, strTextCode, strTextFullName, strTextEmail, strTextPhoneNumber, strAddress, strTextBirthday, strTextGender;
    ArrayList<String> holder = new ArrayList();
    String TextCode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);

        String TextCode1 = getIntent().getStringExtra("ID1");
        code = TextCode1;

        TextCode = getIntent().getStringExtra("ID");
        editTextCode =  (EditText) findViewById(R.id.edit_Code);
        editTextFullName =  (EditText) findViewById(R.id.edit_Fullname);
        editTextEmail =  (EditText) findViewById(R.id.edit_Email);
        editTextPhoneNumber =  (EditText) findViewById(R.id.edit_PhoneNumber);
        editAddress =  (EditText) findViewById(R.id.edit_Address);
        editTextBirthday =  (EditText) findViewById(R.id.edit_Day);
        editTextGender =  (EditText) findViewById(R.id.edit_Gender);

        String TextCode = getIntent().getStringExtra("ID");

        editTextCode.setText(TextCode);
        Button buttonCreateAdmin= (Button)findViewById(R.id.btncreateadmin);
        buttonCreateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_User.this, Current_Information.class);
                intent.putExtra("ID1", code);
                startActivity(intent);
            }
        });

        Button buttonList= (Button)findViewById(R.id.btnList);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_User.this, Listview_User.class);
                intent.putExtra("ID1", code);
                startActivity(intent);
            }
        });
        Button buttonCheckIn= (Button)findViewById(R.id.btnCheckIn);
        buttonCheckIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
//                    strTextCode = editTextCode.getText().toString();
                    strTextCode = TextCode;
                    strTextFullName = editTextFullName.getText().toString();
                    strTextEmail = editTextEmail.getText().toString();
                    strAddress = editAddress.getText().toString();
                    strTextBirthday = editTextBirthday.getText().toString();
                    strTextPhoneNumber = editTextPhoneNumber.getText().toString();
                    strTextGender = editTextGender.getText().toString();

                    String url = URL_CREATE;
                    String type = "insert";
                    crud_MySQL(url, type, strTextCode, strTextFullName,strTextEmail, strAddress,strTextBirthday,strTextPhoneNumber,strTextGender);

                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }

            }
        });

    }// end onCreate

    void crud_MySQL(String url, String type, String textCode,String textFullName, String textEmail,  String textStreet, String textBirthday, String textPhonenumber, String textGender)
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_User.this);
        android_php.execute(url, type, textCode, textFullName, textEmail,  textStreet, textBirthday, textPhonenumber, textGender);
    }
}
