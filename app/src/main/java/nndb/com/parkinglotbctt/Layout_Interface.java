package nndb.com.parkinglotbctt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

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
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;

public class Layout_Interface extends AppCompatActivity {
    private ViewPager2 mviewpager;
    private CircleIndicator3 mcircleIndicator;
    private List<SliderData> mlistSlider;
//    TextView txtcode;
    String code, code1;
    RelativeLayout btnLicenceplate, btnBill, btnSetting, btnStatistics, btnSearch, btnEmployee, btnInformation;
    public static final String URL_Count = "http://192.168.37.160:8182/CRUD-Operation/count.php";
    public static final String URL_INSERT = "http://192.168.37.160:8182/CRUD-Operation/InsertStatistic.php";

    private List<SliderData> getListSlider(){
        List<SliderData> list = new ArrayList<>();
        list.add(new SliderData(R.drawable.imageparkinglot));
        list.add(new SliderData(R.drawable.imageparkinglot1));
        list.add(new SliderData(R.drawable.imageparkinglot3));
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String TextCode = getIntent().getStringExtra("ID");
        code = TextCode;
        String TextCode1 = getIntent().getStringExtra("ChucVu");
        code1 = TextCode1;

//        try {
//            String url1 = URL_CountAdmin;
//            String type1 = "data";
//            crud_MySQL1(url1, type1);
//        }catch (Exception e) {
//            System.out.println("Android Studio " + e.getMessage());
//        }

//        txtcode = (TextView) findViewById(R.id.codead);
        mviewpager = findViewById(R.id.txt_head);
        mcircleIndicator = findViewById(R.id.circle_center);
        btnLicenceplate = findViewById(R.id.itemlayout_1);
        btnStatistics = findViewById(R.id.itemlayout_3);
        btnSetting = findViewById(R.id.itemlayout_4);
        btnSearch = findViewById(R.id.itemlayout_2);
        btnEmployee = findViewById(R.id.itemlayout_5);
        btnInformation = findViewById(R.id.itemlayout_7);
        Slider();
        EnterParkinglot();
        Search();
        Statistic();
//        Bill();
        Setting();
        Employee();
        Information();
    }

    private void Slider(){
        mlistSlider = getListSlider();
        SliderAdapter adapter = new SliderAdapter(mlistSlider);
        mviewpager.setAdapter(adapter);

        mcircleIndicator.setViewPager(mviewpager);
    }


    private void EnterParkinglot()
    {
        btnLicenceplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Interface.this,Licenceplate_Interface.class);
                intent.putExtra("ID", code);
                startActivity(intent);
            }
        });
    }
    private void Search()
    {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Interface.this, Licence_Listview.class);
                intent.putExtra("ID", code);
                intent.putExtra("ChucVu", code1);
                startActivity(intent);
            }
        });
    }
    private void Statistic()
    {
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    String url = URL_INSERT;
                    String type = "insert";
                    crud_MySQL3(url, type);
                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }

                Intent intent = new Intent(Layout_Interface.this,Layout_Statistic.class);
                startActivity(intent);
            }
        });
    }
//    private void Bill()
//    {
//        btnBill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Layout_Interface.this,Current_Information.class);
//                intent.putExtra("ID", code);
//                startActivity(intent);
//            }
//        });
//    }
    private void Setting()
    {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Interface.this,Layout_Setting.class);
                startActivity(intent);
            }
        });
    }
    public void Employee()
    {
        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Interface.this,User_Item.class);
                intent.putExtra("ID", code);
                startActivity(intent);
            }
        });
    }
    private void Information()
    {
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Interface.this, Information_Parkinglot.class);
                startActivity(intent);
            }
        });
    }
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
                        String Email1 = jo.getString("ID");
                        String [] a = Email1.split(" ");
                        String b = a[0];
                        int c = Integer.parseInt(b)+1;
                        String e;
                        if( c < 10) {
                            e = "NV0"+Integer.toString(c);
                        }
                        else {
                            e = "NV"+Integer.toString(c);
                        }
//                        txtcode.setText(d);
                        Intent intent = new Intent(Layout_Interface.this, Layout_User.class);
                        intent.putExtra("ID", e);
                        intent.putExtra("ID1", code);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Interface.this);
        android_php.execute(url, type);
    }
    public void employee(View view)
    {
        try {
            String url = URL_Count;
            String type = "data";
            crud_MySQL(url, type);
        }catch (Exception e) {
            System.out.println("Android Studio " + e.getMessage());
        }
    }

    void crud_MySQL3(String url, String type)
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
//                        post_data = URLEncoder.encode("MaX", "UTF-8") + "=" + URLEncoder.encode(MaX, "UTF-8") + "&"
//                                + URLEncoder.encode("GioRa", "UTF-8") + "=" + URLEncoder.encode(GioRa, "UTF-8");

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
//                        String MaX1 = jo.getString("MaX");
//                        String GioRa1 = jo.getString("GioRa");
//                        holder.add(MaX1);
//                        holder.add(GioRa1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Interface.this);
        android_php.execute(url, type);
    }
}