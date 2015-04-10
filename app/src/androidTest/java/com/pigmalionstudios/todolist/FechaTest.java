package com.pigmalionstudios.todolist;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Created by pigmalionstudios on 4/10/15.
 */
public class FechaTest extends InstrumentationTestCase {
    public void setUp() throws Exception{
        super.setUp();

    }
    public void testPasajeFechas(){
        Fecha fecha = new Fecha(10, 4, 2014);
        Tarea tarea = new Tarea("cocinar", new Fecha(10, 4, 2014), fecha, false);
        Assert.assertTrue(tarea.getFechaAlarm().getDia() == fecha.getDia());
    }
}
