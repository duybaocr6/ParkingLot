package nndb.com.parkinglotbctt;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Licence_Listview extends AppCompatActivity implements TextWatcher {
    // POST Method
    public static final String URL_READ = "http://192.168.37.160:8182/CRUD-Operation/ListLicence.php";
    public static final String URL_INSERTDATETIME = "http://192.168.37.160:8182/CRUD-Operation/Insertdatetime.php";

//    SearchView editsearch;
    ListView lv;
    EditText editsearch;
    LicenseAdapter at;
    ArrayList<License> holder;
    String code, code1;
//    ArrayList<String> holder1 = new ArrayList();
//    ArrayAdapter<String> at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listviewlp);

        String TextCode = getIntent().getStringExtra("ID");
        code = TextCode;
        String TextCode1 = getIntent().getStringExtra("ChucVu");
        code1 = TextCode1;

        lv = (ListView)findViewById(R.id.listview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                License license = holder.get(i);
                Intent intent = new Intent(Licence_Listview.this, Layout_Bill.class);
                intent.putExtra("ID", code);
                intent.putExtra("MaX", license.getMaX());
                intent.putExtra("BSXe", license.getBSXe());
                intent.putExtra("LoaiXe", license.getLoaiXe());
                intent.putExtra("GioVao", license.getGioVao());
                intent.putExtra("ChucVu", code1);

//                try{
//                    NgayGioRa = editNgayGioRa.getText().toString();
//
//                    String url = URL_INSERTDATETIME;
//                    String type = "insert";
//                    crud_MySQL1(url, type, NgayGioRa);
//
//                } catch(Exception e){
//                    System.out.println("Android Studio " + e.getMessage());
//                }

                startActivity(intent);
//                Toast.makeText(Licence_Listview.this,"Remove"+holder.get(i),Toast.LENGTH_SHORT).show();
//                holder.remove(i);
//                at.notifyDataSetChanged();
            }
        });
        try{
            String url = URL_READ;
            String type = "read";
            crud_MySQL(url, type);
        } catch(Exception e){
            System.out.println("Android Studio " + e.getMessage());
        }

        editsearch = (EditText) findViewById(R.id.search);
        editsearch.addTextChangedListener(this);
    }// end onCreate

    void crud_MySQL(String url, String type)
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
                    holder = new ArrayList();
                    holder.clear();
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String MaX1 = jo.getString("MaX");
                        String BSXe1 = jo.getString("BSXe");
                        String LoaiXe1 = jo.getString("LoaiXe");
                        String GioVao1 = jo.getString("GioVao");
//                        String line = your_number_plate1 + "   " + type_vehicle1;
//                        holder.add(line);
                        holder.add(new License(MaX1,BSXe1,LoaiXe1, GioVao1));
                    }
                    at = new LicenseAdapter(getApplicationContext(),holder);
//                    at = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,holder);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Licence_Listview.this);
        android_php.execute(url,type);
    }

//    void crud_MySQL1(String url, String type, String textThoigianra)
//    {
//        class AndroidComunicatesPHPServevr extends AsyncTask<String,Void,String> {
//            Context context;
//            AlertDialog alertDialog;
//            AndroidComunicatesPHPServevr(Context ctx){
//                context = ctx;
//            }
//            @Override
//            protected String doInBackground(String... params) {
//                String login_url = params[0];
//                String type = params[1];
//                String Thoigianra = params[2];
//
//                if(type.equals("insert")){
//                    try {
//                        URL url = new URL(login_url);
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setDoOutput(true);
//                        httpURLConnection.setDoInput(true);
//
//                        OutputStream outputStream = httpURLConnection.getOutputStream();
//                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//
//                        String post_data = "";
//                        //post_data = URLEncoder.encode("number_plate", "UTF-8") + "=" + URLEncoder.encode(number_plate, "UTF-8") + "&"
//                        // + URLEncoder.encode("check_in", "UTF-8") + "=" + URLEncoder.encode(check_in, "UTF-8");
//
//                        post_data = URLEncoder.encode("Thoigianra", "UTF-8") + "=" + URLEncoder.encode(Thoigianra, "UTF-8");
//
//                        bufferedWriter.write(post_data);
//                        bufferedWriter.flush();
//                        bufferedWriter.close();
//                        outputStream.close();
//                        //Nhận data từ android
//                        InputStream inputStream = httpURLConnection.getInputStream();
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                        String result = "";
//                        String line = "";
//                        while ((line = bufferedReader.readLine()) != null) {
//                            result += line;
//                        }
//                        bufferedReader.close();
//                        inputStream.close();
//                        httpURLConnection.disconnect();
//                        return result;
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                return null;
//            }
//
//
//            @Override
//            protected void onPreExecute() {
//                alertDialog = new AlertDialog.Builder(context).create();
//                alertDialog.setTitle("Notification");
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                try{
//                    JSONArray ja = new JSONArray(result);
//                    JSONObject jo = null;
//                    holder1.clear();
//                    for (int i=0; i <ja.length();i++){
//                        jo = ja.getJSONObject(i);
//                        String Thoigianra1 = jo.getString("Thoigianra");
//                        holder1.add(Thoigianra1);
//                    }
//                }catch(Exception ex){
//                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
//                }
//
//            }
//            @Override
//            protected void onProgressUpdate(Void... values) {
//                super.onProgressUpdate(values);
//            }
//        }
//
//        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Licence_Listview.this);
//        android_php.execute(url, type, textThoigianra);
//    }

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
