package com.firstapp.mellow_mind.Home.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firstapp.mellow_mind.Model.User;
import com.firstapp.mellow_mind.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class EditProfileActivity extends AppCompatActivity {

    Button close, save;
    ImageView image_change;
    TextView update;
    EditText fullname, bio, username;;

    FirebaseUser firebaseUser;

    AdView adView;

    private Uri mImageUrl;
    private StorageTask uploadTask;
    StorageReference storageReference;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        image_change = findViewById(R.id.edit_profile_img);
        username = findViewById(R.id.Update_Username);
        fullname = findViewById(R.id.Update_Name);
        bio = findViewById(R.id.Update_Bio);
        save = findViewById(R.id.save_profile);
        update = findViewById(R.id.image_change);
        close = findViewById(R.id.close);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




        image_change.setTranslationX(800);
        username.setTranslationX(800);
        fullname.setTranslationX(800);
        bio.setTranslationX(800);
        update.setTranslationX(800);
        save.setTranslationX(800);
        close.setTranslationX(800);

        image_change.setAlpha(v);
        username.setAlpha(v);
        fullname.setAlpha(v);
        bio.setAlpha(v);
        update.setAlpha(v);
        save.setAlpha(v);
        close.setAlpha(v);

        image_change.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        fullname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        bio.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        update.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        save.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();
        close.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(100).start();





        getSupportActionBar().hide();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fullname.setText(user.getFullname());
                username.setText(user.getUsername());
                bio.setText(user.getBio());
                Glide.with(getApplicationContext()).load(user.getImageURL()).into(image_change);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        image_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .start(EditProfileActivity.this);
            }
        });


//        image_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CropImage.activity()
//                        .setAspectRatio(1, 1)
//                        .setCropShape(CropImageView.CropShape.OVAL)
//                        .start(EditProfileActivity.this);
//            }
//        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(fullname.getText().toString(),
                        username.getText().toString(),
                        bio.getText().toString());
                Toast.makeText(EditProfileActivity.this, "Profile Successfully Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateProfile(String fullname, String username, String bio){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("fullname", fullname);
        hashMap.put("username", username);
        hashMap.put("bio", bio);

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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageURL", "" + myUrl);

                        reference.updateChildren(hashMap);
                        pd.dismiss();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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