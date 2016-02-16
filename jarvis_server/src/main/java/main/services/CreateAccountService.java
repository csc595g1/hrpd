package main.services;

import main.models.UserLogin;
import main.models.UserLoginDB;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/createaccount")
public class CreateAccountService {

    //url: https://......../login?user=USERNAMEHERE&pw=PWHERE
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(String json) throws JSONException{
        
        JSONObject input = new JSONObject(json);
        JSONObject retJson = new JSONObject();
        String email,pw,firstName,lastName;
        boolean returnBool = false;
        if(input.has("email") && input.has("pw") && input.has("firstName") && input.has("lastName")){
            email = input.getString("email");
            pw = input.getString("pw");
            firstName = input.getString("firstName");
            lastName = input.getString("lastName");
            returnBool = new UserLoginDB().createAccount(email, pw, firstName, lastName);
            if(returnBool = true){
                retJson.put("accountCreated", "true");
                return Response.status(Response.Status.OK).entity(retJson).build();
            }
            else{
            retJson.put("error", "User Information exists");
            return Response.status(Response.Status.CONFLICT).entity(retJson).build();
            }
        }
        else{
            retJson.put("error", "Insufficient Parameters provided");
            return Response.status(Response.Status.BAD_REQUEST).entity(retJson).build();
            }
        
        //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        
        //boolean isAuthed = userTemp.isLoggedIn();
        //return new Time();
        //return isAuthed;
    }
}