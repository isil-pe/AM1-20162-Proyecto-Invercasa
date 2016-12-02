package com.vrm.invercasa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vrm.invercasa.model.ProductEntity;

public class ProductDetailActivity extends AppCompatActivity {
    private ProductEntity product;
    private TextView txtName, txtPrice, txtDescription;
    private ImageView imgProduct;
    private Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ui();
    }

    private void ui() {
        txtName = (TextView) findViewById(R.id.textViewName);
        txtPrice = (TextView) findViewById(R.id.textViewPrice);
        txtDescription = (TextView) findViewById(R.id.textViewDescription);
        imgProduct = (ImageView) findViewById(R.id.imageViewProduct);
        btnBuy = (Button) findViewById(R.id.buttonBuy);
        product = (ProductEntity) getIntent().getExtras().getSerializable("PRODUCT");
        txtName.setText(product.getName());
        txtPrice.setText("S/. " + product.getPrice());
        txtDescription.setText(product.getDescription());
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getParent(), "NO DISPONIBLE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
