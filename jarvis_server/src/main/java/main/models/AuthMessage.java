/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.models;

/**
 *
 * @author Ed
 */
public class AuthMessage {
    
    public boolean auth;
    public String message;
    
    public AuthMessage(){}
    
    public AuthMessage(boolean auth, String message){
        this.auth = auth;
        this.message = message;
    }
    
}
