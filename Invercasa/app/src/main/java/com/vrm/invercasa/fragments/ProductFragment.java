package com.vrm.invercasa.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vrm.invercasa.InvercasaApplication;
import com.vrm.invercasa.R;
import com.vrm.invercasa.model.ProductEntity;
import com.vrm.invercasa.view.adapters.ProductAdapter;

import java.util.List;

public class ProductFragment extends Fragment {
    private ListView lstProduct;
    private List<ProductEntity> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        app();
    }

    private void app() {
        ui();
        events();
    }

    @Override
    public void onResume() {
        super.onResume();
        populate();
    }

    private void populate() {
        ProductAdapter productAdapter = new ProductAdapter(getContext(), products);
        lstProduct.setAdapter(productAdapter);
    }

    private void loadData() {
        InvercasaApplication application = (InvercasaApplication)getActivity().getApplication();
        products = application.getProductRepository().list();
    }

    public void ui() {
        lstProduct = (ListView)getActivity().findViewById(R.id.lstProduct);
    }
    private void events() {
        lstProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProductEntity product = (ProductEntity)adapterView.getAdapter().getItem(position);
                //gotoProductDetail(product);
                Toast.makeText(getContext(), product.getName(), Toast.LENGTH_SHORT);
            }
        });
    }

    /*private void gotoProductDetail(ProductEntity product) {
        Bundle bundle= new Bundle();
        bundle.putSerializable("PRODUCT", product);
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }*/
}
