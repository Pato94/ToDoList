package com.pigmalionstudios.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by pigmalionstudios on 4/7/15.
 */
public class DetalleActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
//        Intent intent = getIntent();
//        String nombre = intent.getStringExtra("nombre");
//        Date fechaAlarm = new Date(intent.getStringExtra("fechaAlarm"));
//        Date fechaVenc = new Date(intent.getStringExtra("fechaVenc"));//No me consta que funcione
//        TextView tv1 = (TextView) findViewById(R.id.textView);
//        TextView tv2 = (TextView) findViewById(R.id.textView2);
//        TextView tv3 = (TextView) findViewById(R.id.textView3);
//        tv1.setText("hola");
    }
}
