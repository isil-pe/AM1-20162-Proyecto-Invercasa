package com.vrm.invercasa.storage;

import com.vrm.invercasa.model.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductRepository {
    private List<ProductEntity> products;

    public ProductRepository() {
        products = new ArrayList<>();
    }

    public void mock() {
        Random r = new Random();
        for(int i = 1; i <= 5; i++)
            products.add(new  ProductEntity(i, "Test product " + i, "Test description " + i, r.nextDouble() * 5 + 1, 100));
    }

    public void add(ProductEntity Product) {
        products.add(Product);
    }

    public void remove(ProductEntity Product) {
        products.remove(Product);
    }

    public void removeById(int id) {
        int position = -1;
        ProductEntity product;
        for (int i = 0; i < products.size() ; i++) {
            product = products.get(i);
            if(product.getId() == id) {
                position = i;
                break;
            }
        }
        if(position >= 0 && position < products.size())
            products.remove(position);
    }

    public void update(ProductEntity product) {
        int position = -1;
        ProductEntity rest;
        for (int i = 0; i < products.size() ; i++) {
            rest = products.get(i);
            if(rest.getId() == product.getId()) {
                position = i;
                break;
            }
        }
        if(position>=0 && position< products.size())
            products.set(position, product);
    }

    public List<ProductEntity> list() {
        return products;
    }

    public int count() {
        return products.size();
    }

    public ProductEntity last() {
        if(products.size() > 0)
            return products.get(products.size() - 1);
        return null;
    }
}
