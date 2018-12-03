package com.something.ihfaz.hmu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class AddItems extends AppCompatActivity {

    Button borrow, sell, submit;
    EditText name, description, price, condition, location, keywords;
    TextView add_photo;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth user = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //Buttons
        borrow = findViewById(R.id.button_borrow);
        sell = findViewById(R.id.button_sell);
        add_photo = findViewById(R.id.button_add_pic);
        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        submit = findViewById(R.id.button_submit);

        //Text fields
        name = findViewById(R.id.addName);
        description = findViewById(R.id.addDesc);
        price = findViewById(R.id.addPrice);
        condition = findViewById(R.id.addCond);
        location = findViewById(R.id.addLoc);
        keywords = findViewById(R.id.addKeywords);

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This button is for borrowing");
                //what the button is supposed to do
            }
        });


        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This button is for selling");
                //what the button is supposed to do
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.setLoggingEnabled(true);

                Map<String, Object> item = new HashMap<>();
                String itemName = name.getText().toString();
                item.put("itemId", name.getText().toString() + user.getUid().toString());

                item.put("name", name.getText().toString());
                item.put("description", description.getText().toString());
                item.put("status", false);
                item.put("keywords", keywords.getText().toString());
                item.put("location", location.getText().toString());

                //TODO FIX DATE POSTED IT IS NOT RIGHT I THINK 11/1/18
                //item.put("time posted", new Timestamp(0, 0));

                item.put("condition", condition.getText().toString());


                item.put("seller_netid", user.getUid());

                //TODO ADD GEO LOCATION 11/1/18

                item.put("price", price.getText().toString());
                item.put("buyer", "none");


                //TODO REVIEW HOW TO HANDLE THE NAMING OF ITEMS

                db.collection("items").document(name.getText().toString() + user.getUid().toString()).set(item);

                Map<String, String> message = new HashMap<>();
                message.put("Vendor", user.getUid());
                DocumentReference messageRef = db
                        .collection("items").document(name.getText().toString() + user.getUid().toString())
                        .collection("messages").document("initialVendorMessage");

                messageRef.set(message);

                //  Intent intentL = new Intent(this, FeedActivity.class);
                // startActivity(intentL);




                //what the button is supposed to do
                toNewsFeed();
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_explore:
                            toNewsFeed();
                            break;
                        case R.id.nav_chat:
                            toActiveSales();
                            break;
                        case R.id.nav_trade:
                            //toAddItems();
                            break;
                        case R.id.nav_history:
                            toTradeHistory();
                            break;
                        case R.id.nav_person:
                            toAccount();
                            break;
                    }

                    return true; //true means you want to select the item
                }
            };

    public void toNewsFeed(){
        Intent intentNF = new Intent(this, FeedActivity.class);
        startActivity(intentNF);
    }

    public void toActiveSales(){
        Intent intentAS = new Intent(this, ActiveSales.class);
        startActivity(intentAS);
    }

    public void toAddItems(){
        Intent intentAI = new Intent(this, AddItems.class);
        startActivity(intentAI);
    }

    public void toTradeHistory(){
        Intent intentTH = new Intent(this, TradeHistory.class);
        startActivity(intentTH);
    }

    public void toAccount(){
        Intent intentA = new Intent(this, Account.class);
        startActivity(intentA);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

   /* @Override                                                                                 //TODO THIS IS ALL FRONT END CODE FOR PICTURE COMMENTED OUT AT 11:08pm COME BACK AND UNCOMMENT WHEN READY FOR PICTURES
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 0) {
            if (data != null) {
                Uri contentURI = data.getData(); //TODO this is reference to picture chosen
                try {
                    Bitmap bitmap = null;

                    //TODO U WANT TO MOVE THIS BIT MAP INTO THE DATABASE IT IS THE IMAGE
                    bitmap = getCorrectlyOrientedImage(AddItems.this, contentURI);

                    Toast.makeText(AddItems.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    Bitmap cropImg = cropToSquare(bitmap);
                    add_photo.setBackground(new BitmapDrawable(getResources(), cropImg));


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddItems.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            Bundle extras = data.getExtras();
            Bitmap thumbnail = (Bitmap)extras.get("data");
            //Bitmap resized = Bitmap.createScaledBitmap(thumbnail,(int)(thumbnail.getWidth()*0.8), (int)(thumbnail.getHeight()*0.8), true);
            Bitmap cropImg = cropToSquare(thumbnail);
            add_photo.setBackground(new BitmapDrawable(getResources(), cropImg));

//            RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), cropImg);
//            mDrawable.setCircular(true);
//            mIcon.setImageDrawable(mDrawable);
            //saveImage(thumbnail);
            Toast.makeText(AddItems.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap cropToSquare(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        /*Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);*/
    }

    /*public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException { //TODO COMMENTED OUT AT 11:06pm 11-25-18 TO SEE IF MY CODES RUNS FOR PICTURE U NEED TO UNCOMMENT
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
 //       if (orientation > 0) {
  //          Matrix matrix = new Matrix();
 //           matrix.postRotate(orientation);
//
 //           srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
 //                   srcBitmap.getHeight(), matrix, true);
 //       }
//
 //       return srcBitmap;
   // }
//}
