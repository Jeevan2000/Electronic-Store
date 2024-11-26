package com.example.electronic.store.exceptions;

public class BadApiRequest extends  RuntimeException{

    public BadApiRequest()
    {
        super("Bad API Request !!!");
    }

    public  BadApiRequest(String message)
    {
        super(message);
    }
}
