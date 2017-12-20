package com.example.spiros.myapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;

public class Create2 extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef;
    FirebaseStorage ds = FirebaseStorage.getInstance();

    Button btnIm;

    StorageReference ImStorage;

    int REQUEST_CODE = 2;
    ProgressDialog mPro;
    Button submit,locbutton;
    EditText editlastname, editfirstname, editemail, edittel, editaddress, editcountry, edittk, editcomments;
    ImageView imageView;
    Spinner editrent, edithouse;
    private Uri uri;
    double lat,lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.d("TheApp", "application created");
        dbRef = db.getReference("/Adds/");

        ImStorage = ds.getReference("/Adds/");

        mPro = new ProgressDialog(this);

        btnIm = (Button) findViewById(R.id.btnIm);

        btnIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select"), REQUEST_CODE);
            }

        });
        imageView = (ImageView) findViewById(R.id.imageView);
        editfirstname = (EditText) findViewById(R.id.firstname);
        editlastname = (EditText) findViewById(R.id.lastname);
        editemail = (EditText) findViewById(R.id.email);
        edittel = (EditText) findViewById(R.id.tel);
        editaddress = (EditText) findViewById(R.id.address);
        editcomments = (EditText) findViewById(R.id.comments);
        editrent = (Spinner) findViewById(R.id.rent);
        edithouse = (Spinner) findViewById(R.id.house);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPro.setMessage("Uploading your photo");
                mPro.show();

                StorageReference filepath = ImStorage.child("Photos" + System.currentTimeMillis() + "." +getImageExt(uri));
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                             mPro.dismiss();
                             Toast.makeText(getApplicationContext(), "Image Uploaded",Toast.LENGTH_SHORT).show();
                             String url = taskSnapshot.getDownloadUrl().toString();
                             String dist = null;
                             String id = dbRef.push().getKey();
                             String editLastname = editlastname.getText().toString().trim();
                             String editFirstname = editfirstname.getText().toString().trim();
                             String editEmail = editemail.getText().toString().trim();
                             String editTel = edittel.getText().toString().trim();
                             String editAddress = editaddress.getText().toString().trim();
                             String editCountry = Double.toString(lat);
                             String editTk = Double.toString(lng);
                             String editComments = editcomments.getText().toString().trim();
                             String rent = editrent.getSelectedItem().toString();
                             String house = edithouse.getSelectedItem().toString();
                             Vasi2 vasi = new Vasi2(id, editLastname, editFirstname, editEmail, editTel, rent, house, editAddress, editCountry, editTk, editComments,rent+"_"+house,dist,url);

                             dbRef.child(id).setValue(vasi);

                             Intent homeIntent = new Intent(Create2.this,HomeActivity.class);
                             startActivity(homeIntent);
                             finish();

                    }
                         })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mPro.dismiss();

                                Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount() ;
                                mPro.setMessage("Uploaded" + (int)progress + "%" );
                            }
                        });




            }
        });
        locbutton = (Button) findViewById(R.id.locbutton);
        locbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    loc();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK ) {


            uri = data.getData();


            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void loc() throws IOException {
        String editAddress = editaddress.getText().toString().trim();
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(editAddress, 1);
        Address address = list.get(0);
        String locality = address.getLocality();
        lat = address.getLatitude();
        lng = address.getLongitude();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

    }



}
