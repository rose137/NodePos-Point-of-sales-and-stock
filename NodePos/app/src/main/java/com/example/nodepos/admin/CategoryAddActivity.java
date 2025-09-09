package com.example.nodepos.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nodepos.R;
import com.example.nodepos.helper.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class CategoryAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivThumbnail;
    private Button btnUpload, btnSave;
    private TextInputEditText etName, etPrice, etStock, etDescription, etKodeProduk;

    private Uri imageUri;
    private Bitmap selectedBitmap;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        dbHelper = new DBHelper(this);

        ivThumbnail = findViewById(R.id.imgPreview);
        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etStock = findViewById(R.id.etStock);
        etDescription = findViewById(R.id.etDescription);
        etKodeProduk = findViewById(R.id.etKodeProduk); // tambahkan di XML

        btnUpload.setOnClickListener(v -> openGallery());
        btnSave.setOnClickListener(v -> saveProduct());
    }

    private String generateProductId(String kodeProduk) {
        String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM products WHERE ProdukId LIKE ?",
                new String[]{date + kodeProduk + "%"}
        );


        int sequence = 1;
        if (cursor.moveToFirst()) {
            sequence = cursor.getInt(0) + 1;
        }
        cursor.close();
        db.close();

        String seqStr = String.format("%04d", sequence);
        return date + kodeProduk + seqStr;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar Produk"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivThumbnail.setImageBitmap(selectedBitmap); // preview
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProduct() {
        String kodeProduk = etKodeProduk.getText().toString().trim();
        if (kodeProduk.isEmpty()) kodeProduk = "PRD"; // default

        String produkId = generateProductId(kodeProduk);
        String name = etName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String stock = etStock.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || stock.isEmpty() || description.isEmpty() || selectedBitmap == null) {
            Toast.makeText(this, "Lengkapi semua data produk!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Bitmap → byte[]
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();

        boolean inserted = dbHelper.insertProduct(produkId, name, price, stock, description, imageBytes);

        if (inserted) {
            Toast.makeText(this, "Produk berhasil disimpan dengan ID: " + produkId, Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan produk", Toast.LENGTH_SHORT).show();
        }
    }
}
