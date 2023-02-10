package nndb.com.parkinglotbctt;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

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


public class Licenceplate_Interface extends AppCompatActivity{

    private MaterialButton inputimagebtn;
    private MaterialButton recognizeimagebtn;
    private ShapeableImageView imageIv;
//    private EditText recognizeTextEt;

    private static final String TAG = "MAIN_TAG";

    private Uri imageUri = null;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private ProgressDialog progressDialog;

    private TextRecognizer textRecognizer;

    // POST Method
    public static final String URL_CREATE = "http://192.168.37.160:8182/CRUD-Operation/Create.php";
    EditText editTextLicense;
    String strTextLicense, strTextLoaiXe;
    ArrayList<String> holder = new ArrayList();

    Spinner sp;
    String record= "", a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_licenceplate);

        inputimagebtn = findViewById(R.id.inputimagebtn);
        recognizeimagebtn = findViewById(R.id.recognizeimagebtn);
        imageIv = findViewById(R.id.imageIL);
//        recognizeTextEt= findViewById(R.id.edit_Lisence);
        editTextLicense =  (EditText) findViewById(R.id.edit_Lisence);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        inputimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputImageDialog();
            }
        });

        recognizeimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri == null){
                    Toast.makeText(Licenceplate_Interface.this,"Pick image first...",Toast.LENGTH_SHORT).show();
                }
                else {
                    recognizeTextFromImage();
                }
            }
        });

        sp = (Spinner)findViewById(R.id.edit_Typevehicle);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.array_vehicle,R.layout.select_colorspinner);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        record = "Motorcycle";
                        break;
                    case 1:
                        record = "Car";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button buttonCheckIn= (Button)findViewById(R.id.btnCheckIn);
        buttonCheckIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try{
                    strTextLicense = editTextLicense.getText().toString();
                    strTextLoaiXe = record.toLowerCase();
                    String url = URL_CREATE;
                    String type = "insert";
                    crud_MySQL(url, type, strTextLicense,strTextLoaiXe);

                } catch(Exception e){
                    System.out.println("Android Studio " + e.getMessage());
                }
            }
        });
    }// end onCreate

    void crud_MySQL(String url, String type, String textLisence, String textLoaiXe)
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
                String BSXe = params[2];
                String LoaiXe = params[3];

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

                        post_data = URLEncoder.encode("BSXe", "UTF-8") + "=" + URLEncoder.encode(BSXe, "UTF-8") + "&"
                                + URLEncoder.encode("LoaiXe", "UTF-8") + "=" + URLEncoder.encode(LoaiXe, "UTF-8");
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
                        String BSXe1 = jo.getString("BSXe");
                        String LoaiXe1 = jo.getString("LoaiXe");
//                        holder.add(your_number_plate);
                    }
//                    ArrayAdapter<String> at = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,holder);
//                    lv.setAdapter(at);
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

        AndroidComunicatesPHPServevr android_php = new AndroidComunicatesPHPServevr(Licenceplate_Interface.this);
        android_php.execute(url, type, textLisence, textLoaiXe);
    }
    private void recognizeTextFromImage() {
        Log.d(TAG,"recognizeTextFromImage: ");
        progressDialog.setMessage("Preparing image...");
        progressDialog.show();

        try {
            InputImage inputImage = InputImage.fromFilePath(this,imageUri);
            progressDialog.setMessage("Recognizing text...");
            Task<Text> textTaskResult = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            progressDialog.dismiss();
                            String recognizedText = text.getText();
                            Log.d(TAG, "onSuccess: recognizedText:"+recognizedText);
                            editTextLicense.setText(recognizedText);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.e(TAG,"onFailure", e);
                            Toast.makeText(Licenceplate_Interface.this,"Failed recognizing text due to"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            progressDialog.dismiss();
            Log.e(TAG,"recognizeTextFromImage", e);
            Toast.makeText(this,"Failed preparing image due to"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void showInputImageDialog(){
        PopupMenu popupMenu = new PopupMenu(this,inputimagebtn);

        popupMenu.getMenu().add(Menu.NONE,1,1,"CAMERA");
        popupMenu.getMenu().add(Menu.NONE,2,2,"GALLERY");

        popupMenu.show();


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == 1){
                    Log.d(TAG, "onMenuItemClick: Camera Clicked...");
                    if (checkCameraPermission()){
                        pickImageCamera();
                    }
                    else {
                        requestCameraPermission();
                    }
                }
                else if (id ==2){
                    Log.d(TAG, "onMenuItemClick: Gallery Clicked");
                    if (checkStoragePermission()){
                        pickImageGallery();
                    }
                    else {
                        requestStoragePermission();
                    }
                }
                return true;
            }
        });
    }
    private void pickImageGallery(){
        Log.d(TAG, "pickImageGallery:");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Log.d(TAG, "onActivityResult: imageUri:"+imageUri);
                        imageIv.setImageURI(imageUri);
                    }
                    else {
                        Log.d(TAG, "onActivityResult:cancelled");
                        Toast.makeText(Licenceplate_Interface.this,"Cancelled", Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
    private void pickImageCamera(){
        Log.d(TAG, "pickImageCamera");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Sample description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        cameraActivityResultLauncher.launch(intent);
    }
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==Activity.RESULT_OK){
                        Log.d(TAG, "onActivityResult: imageUri"+imageUri);
                        imageIv.setImageURI(imageUri);
                    }
                    else {
                        Log.d(TAG, "onActivityResult:cancelled");
                        Toast.makeText(Licenceplate_Interface.this,"Cancelled",Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions,STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){
        boolean cameraResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return cameraResult && storageResult;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickImageCamera();
                    }
                    else {
                        Toast.makeText(this, "Camera & Storage permission are required", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickImageGallery();
                    }
                    else {
                        Toast.makeText(this,"Storage permission is require",Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }
}