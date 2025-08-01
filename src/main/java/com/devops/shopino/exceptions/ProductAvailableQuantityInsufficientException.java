package com.devops.shopino.exceptions;

public class ProductAvailableQuantityInsufficientException extends RuntimeException{
    public ProductAvailableQuantityInsufficientException(String message) {
        super(message);
    }
}
