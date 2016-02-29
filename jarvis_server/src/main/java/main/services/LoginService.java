package main.services;

import main.models.UserLogin;
import main.models.UserLoginDB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import main.models.AuthMessage;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/login")
//@Produces(MediaType.APPLICATION_JSON)
public class LoginService {

    //url: https://......../login?user=USERNAMEHERE&pw=PWHERE
    
    @GET
    @Path("/getNames")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNames(@QueryParam("email") String email) throws JSONException{
        UserLogin temp;
        JSONObject resp = new JSONObject();
        if(email != null){
            temp = new UserLoginDB().getUserInformation(email);
            if(temp != null){
                resp.put("firstName", temp.fn);
                resp.put("lastName", temp.ln);
            }
            else{
                resp.put("error", "Problem getting user data");
            }
            return Response.status(Response.Status.OK).entity(resp).build();
        }
        else{
            resp.put("error", "No param passed.");
            return Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
        }
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) throws JSONException {
        
        //Response rspns = new Response();
        JSONObject input = new JSONObject(json);
        JSONObject returnJson = new JSONObject();
        String user = "";
        String pw = "";
        
        //try{
        if(input.has("email")){
            user = input.getString("email");
        }
        else{
            returnJson.put("auth", false);
            return Response.status(Response.Status.OK).entity(returnJson).build();
        }
        
        if(input.has("pw")){
            pw = input.getString("pw");
        }
        else{
            returnJson.put("auth", false);
            return Response.status(Response.Status.OK).entity(returnJson).build();
        }
        //}
        //catch(JSONException e){
            //returnJson.put("auth", false);
            //Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        //}
        
        AuthMessage isAuthed = new UserLoginDB().authUser(user, pw);
        
        returnJson.put("auth", isAuthed.auth);
        returnJson.put("message", isAuthed.message);
        return Response.status(Response.Status.OK).entity(returnJson).build();
    }
}