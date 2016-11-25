package com.vrm.invercasa.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vrm.invercasa.R;
import com.vrm.invercasa.model.ProductEntity;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<ProductEntity> products;

    public ProductAdapter(Context context, List<ProductEntity> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_product, null);
            ViewHolder holder = new ViewHolder();
            holder.txtName = (TextView)v.findViewById(R.id.textViewName);
            holder.txtPrice = (TextView)v.findViewById(R.id.textViewPrice);
            holder.imgProduct = (ImageView)v.findViewById(R.id.imageViewProduct);
            v.setTag(holder);
        }
        ProductEntity entry = products.get(position);
        if(entry != null)
        {
            ViewHolder holder = (ViewHolder)v.getTag();
            holder.txtName.setText(entry.getName());
            holder.txtPrice.setText("S/. " + entry.getPrice());
            //holder.imgProduct.setBackground(context.getResources().getDrawable());
        }
        return v;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
        ImageView imgProduct;
    }
}
