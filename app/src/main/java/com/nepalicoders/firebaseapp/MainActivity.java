package com.nepalicoders.firebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    ***************************************************************************
//    Saving data to the database
//    ***************************************************************************
//    Saving data to the database
//    private EditText mValueField;
//    private Button mAddBtn;
//    private EditText mKeyField;
//    private DatabaseReference mRootRef;


//    ***************************************************************************
//    Retrieving data from the database
//    ***************************************************************************
//    private TextView mValueView;
//    private DatabaseReference mRef;


//    ***************************************************************************
//    Retrieving data in ListView from the database
//    ***************************************************************************
//    private DatabaseReference mRef;
//    private ListView mListView;
//    private ArrayList<String> mUsernames = new ArrayList<>();


//    ***************************************************************************
//    Firebase Email/Password Authentication
//    ***************************************************************************
//    private EditText mEmailField;
//    private EditText mPasswordField;
//    private Button mLoginBtn;
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;


//    ***************************************************************************
//    Firebase ListAdapter
//    ***************************************************************************
//    private ListView mListView;


//    ***************************************************************************
//    Firebase Store Image
//    ***************************************************************************
//    private Button mSelectImage;
//    private StorageReference mStorage;
//    private static final int GALLERY_INTENT = 2;
//    private ProgressDialog mProgressDialog;


//    ***************************************************************************
//    Firebase Store Camera Captured Image
//    ***************************************************************************
//    private Button mUploadBtn;
//    private ImageView mImageView;
//    private static final int CAMERA_REQUEST_CODE = 1;
//    private StorageReference mStorage;
//    private ProgressDialog mProgress;
//    Uri uriSavedImage;


    private Button mRecordBtn;
    private TextView mRecordLabel;
    private MediaRecorder mRecorder;
    private String mFileName = null;
    private static final String LOG_TAG = "Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mStorage = FirebaseStorage.getInstance().getReference();
        mRecordBtn = (Button) findViewById(R.id.recordBtn);
        mRecordLabel = (TextView) findViewById(R.id.recordLabel);
        mProgress = new ProgressDialog(this);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.3gp";

        mRecordBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();
                    mRecordLabel.setText("Recording Started...");

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    mRecordLabel.setText("Recording Stopped...");

                }

                return false;
            }
        });


//        ***************************************************************************
//        Firebase Store Camera Captured Image
//        ***************************************************************************
//        mStorage = FirebaseStorage.getInstance().getReference();
//        mUploadBtn = (Button) findViewById(R.id.upload);
//        mImageView = (ImageView) findViewById(R.id.imageView);
//        mProgress = new ProgressDialog(this);
//
//        mUploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//                File imagesFolder = new File(Environment.getExternalStorageDirectory().getPath(), "FirebaseApp");
//                imagesFolder.mkdirs();
//
//                File image = new File(imagesFolder, "IMAGE_" + timeStamp + ".png");
//                uriSavedImage = Uri.fromFile(image);
//
//                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//                startActivityForResult(imageIntent, CAMERA_REQUEST_CODE);
//
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(intent, CAMERA_REQUEST_CODE);
//
//            }
//        });


//        ***************************************************************************
//        Firebase Store Image
//        ***************************************************************************
//        mStorage = FirebaseStorage.getInstance().getReference();
//        mSelectImage = (Button) findViewById(R.id.selectImage);
//        mProgressDialog = new ProgressDialog(this);
//
//        mSelectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALLERY_INTENT);
//
//            }
//        });


//        ***************************************************************************
//        Firebase ListAdapter
//        ***************************************************************************
//        mListView = (ListView) findViewById(R.id.listView);
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, databaseReference) {
//            @Override
//            protected void populateView(View v, String model, int position) {
//
//                TextView textView = (TextView) v.findViewById(android.R.id.text1);
//                textView.setText(model);
//
//            }
//        };
//        mListView.setAdapter(firebaseListAdapter);


//        ***************************************************************************
//        Firebase Email/Password Authentication
//        ***************************************************************************
//        mAuth = FirebaseAuth.getInstance();
//        mEmailField = (EditText) findViewById(R.id.emailField);
//        mPasswordField = (EditText) findViewById(R.id.passwordField);
//        mLoginBtn = (Button) findViewById(R.id.loginBtn);
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                if (firebaseAuth.getCurrentUser() != null) {
//
//                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
//
//                }
//
//            }
//        };
//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startSignIn();
//
//            }
//        });


//        ***************************************************************************
//        Saving data to the database
//        ***************************************************************************
//        mRootRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        mValueField = (EditText) findViewById(R.id.valueField);
//        mAddBtn = (Button) findViewById(R.id.addBtn);
//        mKeyField = (EditText) findViewById(R.id.keyField);
//        mAddBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String value = mValueField.getText().toString();
//                String key = mKeyField.getText().toString();
//
//                DatabaseReference childRef = mRootRef.child(key);
//
//                childRef.setValue(value);
//
//            }
//        });


//        ***************************************************************************
//        Retrieving data from the database
//        ***************************************************************************
//        mValueView = (TextView) findViewById(R.id.valueView);
//        mRef = FirebaseDatabase.getInstance().getReference();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
//                String name = map.get("Name");
//                String age = map.get("Age");
//                String profession = map.get("Profession");
//
//                mValueView.setText("Name: " + name + "\nAge: " + age + "\nProfession: " + profession);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//        ***************************************************************************
//        Retrieving data in ListView from the database
//        ***************************************************************************
//        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        mListView = (ListView) findViewById(R.id.listView);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);
//        mListView.setAdapter(arrayAdapter);
//
//        mRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                String value = (String) dataSnapshot.getValue();
//                mUsernames.add(0, value);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//    });

    }


//    ***************************************************************************
//    Firebase Email/Password Authentication
//    ***************************************************************************
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//    private void startSignIn() {
//
//        String email = mEmailField.getText().toString();
//        String password = mPasswordField.getText().toString();
//
//        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//
//            Toast.makeText(MainActivity.this, "Fields cannot be empty!", Toast.LENGTH_LONG).show();
//
//        } else {
//
//            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if (!task.isSuccessful()) {
//
//                        Toast.makeText(MainActivity.this, "Sign In Problem!", Toast.LENGTH_LONG).show();
//
//                    }
//
//                }
//            });
//
//        }
//
//    }


//    ***************************************************************************
//    Firebase Store Image
//    ***************************************************************************
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
//
//            mProgressDialog.setMessage("Uploading...");
//            mProgressDialog.show();
//            Uri uri = data.getData();
//            StorageReference filePath = mStorage.child("Photos").child(uri.getLastPathSegment());
//
//            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    Toast.makeText(MainActivity.this, "Upload Done!", Toast.LENGTH_SHORT).show();
//                    mProgressDialog.dismiss();
//
//                }
//            });
//
//        }
//    }


//    ***************************************************************************
//    Firebase Store Camera Captured Image
//    ***************************************************************************
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
//
//            mProgress.setMessage("Uploading Image...");
//            mProgress.show();
//            StorageReference filePath = mStorage.child("Photos").child(uriSavedImage.getLastPathSegment());
//
//            filePath.putFile(uriSavedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    mProgress.dismiss();
//                    Uri downloadUri = taskSnapshot.getDownloadUrl();
//                    Picasso.with(MainActivity.this).load(downloadUri).fit().centerCrop().into(mImageView);
//                    Toast.makeText(MainActivity.this, "Uploading Finished!", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//        }
//
////        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
////
////            mProgress.setMessage("Uploading Image...");
////            mProgress.show();
////            Uri uri = data.getData();
////            StorageReference filePath = mStorage.child("Photos").child(uri.getLastPathSegment());
////            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                @Override
////                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////
////                    mProgress.dismiss();
////                    Toast.makeText(MainActivity.this, "Uploading Finished!", Toast.LENGTH_SHORT).show();
////
////                }
////            });
////
////        }
//    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        uploadAudio();
    }

    private void uploadAudio() {

        mProgress.setMessage("Uploading Audio...");
        mProgress.show();
        StorageReference filePath = mStorage.child("Audios").child("new_audio.3gp");
        Uri uri = Uri.fromFile(new File(mFileName));
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mProgress.dismiss();
                mRecordLabel.setText("Uploading Finished!");

            }
        });

    }
}
