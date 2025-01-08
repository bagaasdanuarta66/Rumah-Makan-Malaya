package com.example.tugasaplikasi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etPesanan, etName, etAddress, etPhone;
    private TextView tvQuantity, tvTotal;
    private Button btnIncrease, btnDecrease, btnSubmit;
    private RadioGroup rgPayment;
    private RadioButton rbCOD, rbTransfer;
    private int quantity = 1;
    private int pricePerItem = 10000; // Harga per item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPesanan = findViewById(R.id.etPesanan);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotal = findViewById(R.id.tvTotal);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnSubmit = findViewById(R.id.btnSubmit);
        rgPayment = findViewById(R.id.rgPayment);
        rbCOD = findViewById(R.id.rbCOD);
        rbTransfer = findViewById(R.id.rbTransfer);

        // Set total awal
        updateTotal();

        btnIncrease.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            updateTotal();
        });

        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotal();
            } else {
                Toast.makeText(MainActivity.this, "Jumlah pesanan tidak bisa kurang dari 1", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmit.setOnClickListener(v -> {
            String pesanan = etPesanan.getText().toString();
            String name = etName.getText().toString();
            String address = etAddress.getText().toString();
            String phone = etPhone.getText().toString();

            if (pesanan.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(MainActivity.this, "Silakan lengkapi data Anda", Toast.LENGTH_SHORT).show();
            } else {
                String paymentMethod = "";
                if (rbCOD.isChecked()) {
                    paymentMethod = "Cash on Delivery (COD)";
                } else if (rbTransfer.isChecked()) {
                    paymentMethod = "Transfer Bank";
                }

                if (paymentMethod.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Silakan pilih metode pembayaran", Toast.LENGTH_SHORT).show();
                } else {
                    String orderSummary = "Pesanan: " + pesanan + "\n" +
                            "Nama: " + name + "\n" +
                            "Alamat: " + address + "\n" +
                            "Nomor HP: " + phone + "\n" +
                            "Jumlah: " + quantity + "\n" +
                            "Total: Rp" + (quantity * pricePerItem) + "\n" +
                            "Metode Pembayaran: " + paymentMethod;

                    shareOrderSummary(orderSummary);
                }
            }
        });
    }

    private void updateTotal() {
        int total = quantity * pricePerItem;
        tvTotal.setText("Total: Rp" + total);
    }

    private void shareOrderSummary(String orderSummary) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Pesanan Baru dari Aplikasi");
        shareIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        try {
            startActivity(Intent.createChooser(shareIntent, "Bagikan Pesanan Melalui:"));
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }
}

