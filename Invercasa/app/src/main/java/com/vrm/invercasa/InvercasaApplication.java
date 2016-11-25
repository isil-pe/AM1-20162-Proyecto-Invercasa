package com.vrm.invercasa;

import android.app.Application;

import com.vrm.invercasa.storage.ProductRepository;
import com.vrm.invercasa.storage.UserRepository;

public class InvercasaApplication extends Application{
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        productRepository = new ProductRepository();
        productRepository.mock();
        userRepository = new UserRepository();
        userRepository.mock();
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
