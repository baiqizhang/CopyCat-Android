package com.copycat.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.copycat.model.Category;
import com.copycat.util.CoreUtil;
import com.copycat.view.CategoryAdapter;
import com.example.baiqizhang.copycat.R;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 666;
    CategoryAdapter categoryAdapter;
    List<Category> categories;
    View dialogView;
    Bitmap chosenBannerBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //setup toolbar and hide status bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set title text style
        TextView mTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mTitleTextView.setLetterSpacing(0.13f);

        categories = CoreUtil.getCategoryListFromDB(this);
        categories.remove(categories.size() - 1);

        categoryAdapter = new CategoryAdapter(categories,this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(categoryAdapter);

        //Back button
        ImageButton mBackButton = (ImageButton) findViewById(R.id.toolbar_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Add button
        ImageButton mAddButton = (ImageButton) findViewById(R.id.addCategory);
        LayoutInflater inflater = CategoryActivity.this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_newcategory, null);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);

                ImageButton imageButton = (ImageButton) dialogView.findViewById(R.id.banner);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        CategoryActivity.this.startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), RESULT_LOAD_IMAGE);
                    }
                });

                // Get the layout inflater
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(dialogView)
                        // Add action buttons
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText editText = (EditText) dialogView.findViewById(R.id.dialogEditText);
                                Log.d("dialog", editText.getText().toString());
                                Bitmap banner;
                                if (chosenBannerBitmap!=null)
                                    banner = chosenBannerBitmap;
                                else
                                    banner = BitmapFactory.decodeResource(CategoryActivity.this.getResources(),
                                                                            R.drawable.banner);
                                Category category = new Category(editText.getText().toString(), banner, null);
                                CoreUtil.addCategory(category,CategoryActivity.this);
                                categories.add(category);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case RESULT_LOAD_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    chosenBannerBitmap = BitmapFactory.decodeFile(filePath);
                    ImageButton imageButton = (ImageButton) dialogView.findViewById(R.id.banner);
                    imageButton.setImageBitmap(chosenBannerBitmap);
                }
        }
    }

}

