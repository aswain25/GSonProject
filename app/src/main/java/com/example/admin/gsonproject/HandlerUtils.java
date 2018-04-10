package com.example.admin.gsonproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HandlerUtils
{
    public static HandlerUtils instance = null;
    Handler handler;

    private HandlerUtils(){}

    public static HandlerUtils getDefult()
    {
        if (instance == null)
        {
            instance = new HandlerUtils();
        }
        return instance;
    }

    public void setReceiver(Handler handler)
    {
        this.handler = handler;
    }

    public void sendMessage(String message)
    {

        Message handlerMessage = new Message();
        Bundle bundle = new Bundle();

        bundle.putString("results", message);
        handlerMessage.setData(bundle);
        handler.sendMessage(handlerMessage);


    }
}
