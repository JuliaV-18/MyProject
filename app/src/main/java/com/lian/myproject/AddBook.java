package com.lian.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.lian.myproject.model.Book;
import com.lian.myproject.services.DatabaseService;
import com.lian.myproject.services.ImageUtil;

import java.util.Calendar;
import java.util.Date;

public class AddBook extends AppCompatActivity {

    private EditText etBookTitle, etBookAuthor, etBookCopies, etCategory, etDescription;
    private Spinner spCategory;
    private Button btnGallery, btnTakePic, btnAddItem;
    private ImageView imageView;

    private ImageButton btnBack;


    private DatabaseService databaseService;

 
    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;



    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

  

        InitViews();

        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();



 

        /// register the activity result launcher for capturing image from camera
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                    }
                });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddBook.this, AdminActivity.class);
            startActivity(intent);
            finish();
        });




        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();


            }
        });

        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageFromCamera();

            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookTitle= etBookTitle.getText().toString();
                String bookAuthor= etBookAuthor.getText().toString();
                String stCopiesAvailabale = etBookCopies.getText().toString();
                String bookCategory = spCategory.getSelectedItem().toString();
                String bookDescription = etDescription.getText().toString();


                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                Date added=new Date(year,month,day);



                String coverPic = ImageUtil.convertTo64Base(imageView);
                int copiesAvailable = Integer.parseInt(stCopiesAvailabale);

                if (bookTitle.isEmpty() || bookAuthor.isEmpty() || bookCategory.isEmpty() ||
                        bookDescription.isEmpty()) {
                    Toast.makeText(AddBook.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddBook.this, "הספר נוסף בהצלחה!", Toast.LENGTH_SHORT).show();
                }

                /// generate a new id for the item
                String id = databaseService.generateBookId();

                Book newBook = new Book( id,  bookTitle,  bookAuthor,  true,  copiesAvailable,  copiesAvailable,  bookCategory,  coverPic, added,  bookDescription);



                    /// save the item to the database and get the result in the callback
                databaseService.createNewBook(newBook, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Log.d("TAG", "Item added successfully");
                        Toast.makeText(AddBook.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                        /// clear the input fields after adding the item for the next item
                        Log.d("TAG", "Clearing input fields");

                        Intent intent = new Intent(AddBook.this, AdminActivity.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e("TAG", "Failed to add item", e);
                        Toast.makeText(AddBook.this, "Failed to add food", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });
    }

    private void InitViews() {
        etBookTitle = findViewById(R.id.etBookTitle);
        etBookAuthor = findViewById(R.id.etBookAuthor);
        etBookCopies = findViewById(R.id.etCopiesAvailable);
        etDescription = findViewById(R.id.etDescription);
        spCategory = findViewById(R.id.spCategory);
        imageView = findViewById(R.id.ivCover);
    }


    /// select image from gallery
            private void selectImageFromGallery() {
                //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //  selectImageLauncher.launch(intent);

                imageChooser();
            }

            /// capture image from camera
            private void captureImageFromCamera() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                captureImageLauncher.launch(takePictureIntent);
            }





            void imageChooser() {

                // create an instance of the
                // intent of the type image
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                // pass the constant to compare it
                // with the returned requestCode
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            }

            // this function is triggered when user
            // selects the image from the imageChooser
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (resultCode == RESULT_OK) {

                    // compare the resultCode with the
                    // SELECT_PICTURE constant
                    if (requestCode == SELECT_PICTURE) {
                        // Get the url of the image from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // update the preview image in the layout
                            imageView.setImageURI(selectedImageUri);
                        }
                    }
                }
            }
        }




