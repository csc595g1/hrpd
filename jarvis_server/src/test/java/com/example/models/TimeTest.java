package com.example.models;

import main.models.Time;
import main.models.UserLoginDB;
import main.services.LoginService;
import org.junit.Test;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.


import java.util.TimeZone;
import javax.swing.text.html.parser.Entity;

import static org.junit.Assert.assertEquals;

public class TimeTest {
    
//    @Test
//    public void testDefaultTimezone() throws Exception {
//        final Time time = new Time();
//        assertEquals(TimeZone.getDefault().getDisplayName(), time.getTimezone());
//    }
//
//    @Test
//    public void testSpecifiedTimezone() throws Exception {
//        final TimeZone est = TimeZone.getTimeZone("EST");
//        final Time time = new Time(est);
//        assertEquals(est.getDisplayName(), time.getTimezone());
//    }
    
    @Test
    public void testUserLogin() throws Exception {
        String test = "{\"user\": \"em7293@gmail.com\",\"pw\": \"test\"}";
        //Entity entity = Entity.json(test);
        
        //Client
        //System.out.println(new LoginService().login(test));
    }
    
}
