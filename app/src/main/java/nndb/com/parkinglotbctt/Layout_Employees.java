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

public class Layout_Employees extends AppCompatActivity {
    private ViewPager2 mviewpager;
    private CircleIndicator3 mcircleIndicator;
    private List<SliderData> mlistSlider;

    ArrayList<String> holder = new ArrayList();
//    TextView txtcode;
    String code1, codenew, codenv;
    RelativeLayout btnLicenceplate, btnBill, btnSetting, btnStatistics, btnSearch, btnEmployee, btnInformation;
//    public static final String URL_CountAdmin = "http://192.168.37.160:8182/CRUD-Operation/countadmin.php";
    public static final String URL_ACCOUNT = "http://192.168.37.160:8182/CRUD-Operation/Account.php";

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
        setContentView(R.layout.layout_employees);

        String TextCode = getIntent().getStringExtra("ID");
        code1 = TextCode;

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
        btnSearch = findViewById(R.id.itemlayout_2);
        btnEmployee = findViewById(R.id.itemlayout_3);
        btnInformation = findViewById(R.id.itemlayout_7);
        btnStatistics = findViewById(R.id.itemlayout_5);
        Slider();
        EnterParkinglot();
        Search();
        Bill();
        Statistic();
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
                Intent intent = new Intent(Layout_Employees.this,Licenceplate_Interface.class);
                intent.putExtra("ID", code1);
                startActivity(intent);
            }
        });
    }
    private void Search()
    {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Employees.this, Licence_Listview.class);
                intent.putExtra("ID", code1);
                startActivity(intent);
            }
        });
    }
    private void Bill()
    {
        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Employees.this,User_Item.class);
                intent.putExtra("ID", code1);
                startActivity(intent);
            }
        });
    }
    private void Information()
    {
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Employees.this, Information_Parkinglot.class);
                startActivity(intent);
            }
        });
    }

    private void Statistic()
    {
        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Layout_Employees.this,Layout_Statistic.class);
                startActivity(intent);
            }
        });
    }
//    void crud_MySQL(String url, String type, String code, String codenv)
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
//                String ID = params[2];
//                String MaNV = params[3];
//
//                if(type.equals("data")){
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
//                        post_data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8")+ "&"
//                         + URLEncoder.encode("MaNV", "UTF-8") + "=" + URLEncoder.encode(MaNV, "UTF-8");
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
//                    for (int i=0; i <ja.length();i++){
//                        jo = ja.getJSONObject(i);
//                        String ID1 = jo.getString("ID");
//                        String MaNV1 = jo.getString("MaNV");
//                        holder.add(ID1);
//                        holder.add(MaNV1);
//                        Intent intent = new Intent(Layout_Employees.this, User_Item.class);
//                        intent.putExtra("ID1", code1);
//                        startActivity(intent);
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
//        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Employees.this);
//        android_php.execute(url, type, code, codenv);
//    }
//    public void employee(View view)
//    {
//        try {
//            codenew = code1;
//            codenv = code1;
//
//            String url = URL_ACCOUNT;
//            String type = "data";
//            crud_MySQL(url, type,codenew, codenv);
//        }catch (Exception e) {
//            System.out.println("Android Studio " + e.getMessage());
//        }
//    }
}