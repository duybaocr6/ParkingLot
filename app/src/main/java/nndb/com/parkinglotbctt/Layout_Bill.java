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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Layout_Bill extends AppCompatActivity {
    public static final String URL_SELECT = "http://192.168.37.160:8182/CRUD-Operation/SelectType.php";
    public static final String URL_UPDATETYPE = "http://192.168.37.160:8182/CRUD-Operation/UpdateType.php";
    public static final String URL_INSERT = "http://192.168.37.160:8182/CRUD-Operation/InsertBill.php";
    public static final String URL_UPDATE = "http://192.168.37.160:8182/CRUD-Operation/UpdateXe.php";
    EditText editBSXe, editLoaiXe,editNgayGioVao, editNgayGioRa, editThoiGianDo, editGiave;
    String TextCode1, TextCode, TextMaX, TextBSXe, TextLoaiXe, TextNgayGioVao, BSXe, LoaiXe,NgayGioVao, NgayGioRa, ThoiGianDo, GiaVe, CodeX, TypeX, MaXe, datetime, InsertGiave, InsertCode, InsertNV;
    ArrayList<String> holder = new ArrayList();
    Date date1 = null;
    Date date2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill);
        editBSXe =  (EditText) findViewById(R.id.edit_Lisence);
        editLoaiXe =  (EditText) findViewById(R.id.edit_Vehicle);
        editNgayGioVao =  (EditText) findViewById(R.id.edit_Datetimein);
        editNgayGioRa =  (EditText) findViewById(R.id.edit_Datetimeout);
        editThoiGianDo =  (EditText) findViewById(R.id.edit_Parkingtime);
        editGiave = (EditText) findViewById(R.id.edit_Payment);

        TextCode = getIntent().getStringExtra("ID");
        TextMaX = getIntent().getStringExtra("MaX");
        TextBSXe = getIntent().getStringExtra("BSXe");
        TextLoaiXe = getIntent().getStringExtra("LoaiXe");
        TextNgayGioVao = getIntent().getStringExtra("GioVao");
        TextCode1 = getIntent().getStringExtra("ChucVu");

        editBSXe.setText(TextBSXe);
        editLoaiXe.setText(TextLoaiXe);
        editNgayGioVao.setText(TextNgayGioVao);

        if (TextLoaiXe.equals("car"))
        {
            try{
                CodeX = TextMaX;
                TypeX = editLoaiXe.getText().toString();

                String url1 = URL_UPDATETYPE;
                String type1 = "read";
                crud_MySQL1(url1, type1,CodeX,TypeX);
            } catch(Exception e){
                System.out.println("Android Studio " + e.getMessage());
            }
        }
        else {
            try{
                CodeX = TextMaX;
                TypeX = editLoaiXe.getText().toString();

                String url1 = URL_UPDATETYPE;
                String type1 = "read";
                crud_MySQL1(url1, type1,CodeX,TypeX);
            } catch(Exception e){
                System.out.println("Android Studio " + e.getMessage());
            }
        }

        editNgayGioRa =  (EditText) findViewById(R.id.edit_Datetimeout);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());
        editNgayGioRa.setText(currentDateAndTime);


        try {

            date1 = sdf.parse(TextNgayGioVao);
            date2 = sdf.parse(currentDateAndTime);

            long getDiff = date2.getTime() - date1.getTime();
            long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
            if (getDaysDiff == 0) {
                getDaysDiff = 1;
            }
            else {
                getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
            }

            datetime = String.valueOf(getDaysDiff);
            editThoiGianDo.setText(datetime);

            try{
                MaXe = TextMaX;

                String url = URL_SELECT;
                String type = "select";
                crud_MySQL(url, type, MaXe);
            } catch(Exception e){
                System.out.println("Android Studio " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        Button buttonCheckIn= (Button)findViewById(R.id.btnSubmit);
        buttonCheckIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
                    CodeX = TextMaX;
                    NgayGioRa = editNgayGioRa.getText().toString();

                    String url = URL_UPDATE;
                    String type = "insert";
                    crud_MySQL3(url, type, CodeX, NgayGioRa);
                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }
                try{
                    BSXe = editBSXe.getText().toString();
                    LoaiXe = editLoaiXe.getText().toString();
                    NgayGioVao = editNgayGioVao.getText().toString();
                    NgayGioRa = editNgayGioRa.getText().toString();
                    ThoiGianDo = editThoiGianDo.getText().toString();
                    GiaVe = editGiave.getText().toString();

                    try {
                        createPdf(BSXe, LoaiXe, NgayGioVao, NgayGioRa, ThoiGianDo, GiaVe);
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }
                try{
                    InsertGiave = editGiave.getText().toString();
                    InsertCode = TextMaX;
                    InsertNV = TextCode;


                    String url = URL_INSERT;
                    String type = "insert";
                    crud_MySQL2(url, type, InsertGiave, InsertCode, InsertNV);
                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }

                if (TextCode1.equals("admin")){
                    Intent intent = new Intent(Layout_Bill.this,Layout_Interface.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Layout_Bill.this,Layout_Employees.class);
                    startActivity(intent);
                }
            }
        });

    }

    void crud_MySQL(String url, String type, String TenL)
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
                String MaX = params[2];

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
                        post_data = URLEncoder.encode("MaX", "UTF-8") + "=" + URLEncoder.encode(MaX, "UTF-8");
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
                        String Giave1 = jo.getString("Giave");

                        int a = (Integer.parseInt(Giave1) * Integer.parseInt(datetime));
                        String b = String.valueOf(a);
                        editGiave.setText(b);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Bill.this);
        android_php.execute(url,type, TenL);
    }

    void crud_MySQL1(String url, String type, String editMaX,String editTenX)
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
                String MaX = params[2];
                String TenLoai = params[3];

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
                        post_data = URLEncoder.encode("MaX", "UTF-8") + "=" + URLEncoder.encode(MaX, "UTF-8") + "&"
                         + URLEncoder.encode("TenLoai", "UTF-8") + "=" + URLEncoder.encode(TenLoai, "UTF-8");

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
                        String MaX1 = jo.getString("MaX");
                        String TenLoai1 = jo.getString("TenLoai");
                        holder.add(MaX1);
                        holder.add(TenLoai1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Bill.this);
        android_php.execute(url, type, editMaX, editTenX);
    }

    void crud_MySQL2(String url, String type, String editGia,String editMaX,String editMaNV)
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
                String ThanhTien = params[2];
                String MaX = params[3];
                String MaNV = params[4];

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
                        post_data = URLEncoder.encode("ThanhTien", "UTF-8") + "=" + URLEncoder.encode(ThanhTien, "UTF-8") + "&"
                                + URLEncoder.encode("MaX", "UTF-8") + "=" + URLEncoder.encode(MaX, "UTF-8")+ "&"
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
                    holder.clear();
                    for (int i=0; i <ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String ThanhTien1 = jo.getString("ThanhTien");
                        String MaX1 = jo.getString("MaX");
                        String MaNV1 = jo.getString("MaNV");
                        holder.add(ThanhTien1);
                        holder.add(MaX1);
                        holder.add(MaNV1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Bill.this);
        android_php.execute(url, type, editGia, editMaX, editMaNV);
    }

    void crud_MySQL3(String url, String type, String editMaX,String giora)
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
                String MaX = params[2];
                String GioRa = params[3];

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
                        post_data = URLEncoder.encode("MaX", "UTF-8") + "=" + URLEncoder.encode(MaX, "UTF-8") + "&"
                                + URLEncoder.encode("GioRa", "UTF-8") + "=" + URLEncoder.encode(GioRa, "UTF-8");

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
                        String MaX1 = jo.getString("MaX");
                        String GioRa1 = jo.getString("GioRa");
                        holder.add(MaX1);
                        holder.add(GioRa1);
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

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Layout_Bill.this);
        android_php.execute(url, type,editMaX, giora);
    }

    private void createPdf(String BSXe, String LoaiXe, String NgayGioVao, String NgayGioRa, String ThoiGianDo, String GiaVe) throws FileNotFoundException
    {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"Parkinglot.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0,0,0,0);


        Paragraph ticket = new Paragraph("P").setBold().setFontSize(30).setTextAlignment(TextAlignment.CENTER);
        Paragraph ticket1 = new Paragraph("Parking Lot").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width = {50f, 150f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        table.addCell(new Cell().add(new Paragraph("Export date")));
        table.addCell(new Cell().add(new Paragraph(LocalDateTime.now().format(dateTimeFormatter))));

        table.addCell(new Cell().add(new Paragraph("License plates")));
        table.addCell(new Cell().add(new Paragraph(BSXe)));

        table.addCell(new Cell().add(new Paragraph("Vehicles")));
        table.addCell(new Cell().add(new Paragraph(LoaiXe)));

        table.addCell(new Cell().add(new Paragraph("Entry time")));
        table.addCell(new Cell().add(new Paragraph(NgayGioVao)));

        table.addCell(new Cell().add(new Paragraph("Time out")));
        table.addCell(new Cell().add(new Paragraph(NgayGioRa)));

        table.addCell(new Cell().add(new Paragraph("Parking time")));
        table.addCell(new Cell().add(new Paragraph(ThoiGianDo)));

        table.addCell(new Cell().add(new Paragraph("Payment")));
        table.addCell(new Cell().add(new Paragraph(GiaVe)));

        Paragraph ticket2 = new Paragraph("Price includes 10% VAT.").setBold().setFontSize(13).setTextAlignment(TextAlignment.CENTER);
        Paragraph ticket3 = new Paragraph("---------------------------------").setBold().setFontSize(13).setTextAlignment(TextAlignment.CENTER);
        Paragraph ticket4 = new Paragraph("Please keep your ticket for control").setBold().setFontSize(13).setTextAlignment(TextAlignment.CENTER);
        Paragraph ticket5 = new Paragraph("---------------------------------").setBold().setFontSize(13).setTextAlignment(TextAlignment.CENTER);
        Paragraph ticket6 = new Paragraph("1911549657-Nguyen Ngoc Duy Bao").setBold().setFontSize(13).setTextAlignment(TextAlignment.CENTER);

//        document.add(image);
        document.add(ticket);
        document.add(ticket1);
        document.add(table);
        document.add(ticket2);
        document.add(ticket3);
        document.add(ticket4);
        document.add(ticket5);
        document.add(ticket6);
        document.close();
        Toast.makeText(this,"Pdf Created", Toast.LENGTH_LONG).show();
    }
    protected void onStop() {
        super.onStop();
    }
}
