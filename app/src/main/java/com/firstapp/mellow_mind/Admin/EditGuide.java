package com.firstapp.mellow_mind.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Home.Settings.EditProfileActivity;
import com.firstapp.mellow_mind.Model.Guide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class EditGuide extends AppCompatActivity {

    EditText title, time, author, body;
    Button back, update;
    ImageView image_change;

    String guideID;

    private Uri mImageUrl;
    private StorageTask uploadTask;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guide);

        title = findViewById(R.id.guide_title_edit);
        time = findViewById(R.id.guide_time_edit);
        author = findViewById(R.id.guide_author_edit);
        body = findViewById(R.id.guide_body_edit);
        image_change = findViewById(R.id.guide_img);

        back = findViewById(R.id.back_btn_edit);
        update = findViewById(R.id.guide_update);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        getSupportActionBar().hide();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        guideID = preferences.getString("guideID", "none");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides").child(guideID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Guide guide = snapshot.getValue(Guide.class);
                title.setText(guide.getTitle());
                time.setText(guide.getTime());
                author.setText(guide.getAuthor());
                body.setText(guide.getBody());
                Glide.with(getApplicationContext()).load(guide.getImageURL()).into(image_change);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .start(EditGuide.this);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGuide(title.getText().toString(),
                        time.getText().toString(),
                        author.getText().toString(),
                        body.getText().toString());

                Toast.makeText(EditGuide.this, "Guide Successfully Updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateGuide(String title, String time, String author, String body){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides").child(guideID);

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("title", title);
        hashMap.put("search_title", title.toLowerCase());
        hashMap.put("time", time);
        hashMap.put("author", author);
        hashMap.put("body", body);

        reference.updateChildren(hashMap);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (mImageUrl != null){
            StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(mImageUrl));

            uploadTask = filereference.putFile(mImageUrl);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guides").child(guideID);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageURL", "" + myUrl);

                        reference.updateChildren(hashMap);
                        pd.dismiss();
                    } else {
                        Toast.makeText(EditGuide.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditGuide.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUrl = result.getUri();

            uploadImage();
        } else {
            Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

}