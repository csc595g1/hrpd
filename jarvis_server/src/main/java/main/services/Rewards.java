package main.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import main.models.RewardEvent;

@Path("/rewards")
public class Rewards {

	@GET
	@Path("/eventtypes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRewardsEventTypes() throws JSONException{
		JSONObject jsonResponse = new JSONObject();
		
		ArrayList<String> ary = getRewardEventTypes();
		
		try {
			jsonResponse.put("rewardEventTypes", new JSONArray(ary));
		} catch (JSONException e) {
			// TODO Auto-generated catch block/events
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.OK).entity(jsonResponse).build();
		
	}

	@GET
	@Path("/events/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRewardsById(@PathParam ("userId")  String userId) throws JSONException{
		JSONObject jsonResponse = new JSONObject();
		
		ArrayList<String> ary = null;
		
		if (userId != null) {
			ary = getRewards(userId);
		}

		try {
			jsonResponse.put("rewardEvents", new JSONArray(ary));
		} catch (JSONException e) {
			// TODO Auto-generated catch block/events
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.OK).entity(jsonResponse).build();
		
	}

	@GET
	@Path("/events")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRewards() {
		JSONObject jsonResponse = new JSONObject();
		
		ArrayList<String> ary = getRewards(null);
		
		try {
			jsonResponse.put("rewardEvents", new JSONArray(ary));
		} catch (JSONException e) {
			// TODO Auto-generated catch block/events
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.OK).entity(jsonResponse).build();
		
	}

	@PUT
	@Path("/event")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createRewardEvent(String requestJson) throws JSONException{
		JSONObject jsonRequest = new JSONObject(requestJson);
		JSONObject jsonResponse = new JSONObject();
		RewardEvent createRewardEvent = null;
		
		if (jsonRequest.has("userId") &&
				jsonRequest.has("eventCategory") &&
				jsonRequest.has("units") &&
				jsonRequest.has("title")) {
			createRewardEvent = new RewardEvent("",
									jsonRequest.getString("userId"),
									jsonRequest.getString("eventCategory"),
									jsonRequest.getInt("units"),
									jsonRequest.getString("title"),
									"");
			
		}

		RewardEvent rewardEvent = createRewardEvent(createRewardEvent);
		
		try {
			if (rewardEvent != null) {
				jsonResponse.put("eventId", rewardEvent.getEventId());
				jsonResponse.put("userId", rewardEvent.getUserId());
				jsonResponse.put("eventCategory", rewardEvent.getEventCategory());
				jsonResponse.put("units", rewardEvent.getUnits().toString());
				jsonResponse.put("title", rewardEvent.getTitle());
				jsonResponse.put("tstamp", rewardEvent.getTstamp());
				jsonResponse.put("eventCreated", Boolean.TRUE.toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block/events
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.OK).entity(jsonResponse).build();
		
	}

	@DELETE
	@Path("/events/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRewardEventByUserId(@PathParam ("userId")  String userId) throws JSONException{
		JSONObject jsonResponse = new JSONObject();
		
		Boolean flag = Boolean.FALSE;
		if (userId != null) {
			flag = deleteRewardEvent(userId);
		} else {
			flag = Boolean.FALSE;
		}


		try {
			if (flag) {
				jsonResponse.put("eventsDeleted", Boolean.TRUE.toString());
			} else {
				jsonResponse.put("eventsDeleted", Boolean.FALSE.toString());
			}
			
		} catch (JSONException e) {
			jsonResponse.put("error", e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponse).build();
		}
		
		return Response.status(Response.Status.OK).entity(jsonResponse).build();
		
	}

	@DELETE
	@Path("/event/{eventId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRewardEventByEventId(@PathParam ("eventId")  Integer eventId) throws JSONException{
		JSONObject jsonResponse = new JSONObject();
		
		Boolean flag = Boolean.FALSE;
		if (eventId != null) {
			flag = deleteRewardEvent(eventId);
		} else {
			flag = Boolean.FALSE;
		}


		try {
			if (flag) {
				jsonResponse.put("eventDeleted", Boolean.TRUE.toString());
			} else {
				jsonResponse.put("eventDeleted", Boolean.FALSE.toString());
			}
			
		} catch (JSONException e) {
			jsonResponse.put("error", e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponse).build();
		}
		
		return Response.status(Response.Status.OK).entity(jsonResponse).build();
		
	}

	/* Internal Database calls */
	private RewardEvent createRewardEvent(RewardEvent rewardEvent) {
		Boolean isCreated = Boolean.FALSE;
		RewardEvent createdRewardEvent = null;
		
		final Calendar now = Calendar.getInstance(TimeZone.getDefault());
		
		if (rewardEvent.getUserId().equals("test1@test.com")) {
			createdRewardEvent = new RewardEvent("0","test1@test.com","maintenance",5,"Sump Pump Maintenance",String.valueOf(now.getTimeInMillis()));
			isCreated = Boolean.TRUE;
		} else if (rewardEvent.getUserId().equals("test2@test.com")) {
			createdRewardEvent = new RewardEvent("0","test2@test.com","maintenance",5,"Water Heater Maintenance",String.valueOf(now.getTimeInMillis()));
			isCreated = Boolean.TRUE;
		} else if (rewardEvent.getUserId().equals("test3@test.com")) {
			createdRewardEvent = new RewardEvent("0","test3@test.com","article",10,"Article for community",String.valueOf(now.getTimeInMillis()));
			isCreated = Boolean.TRUE;
		} else {
			isCreated = Boolean.FALSE;
		}
		
		if (isCreated == Boolean.FALSE) {
			createdRewardEvent = null;
		} 

		return createdRewardEvent;
	}
	
	private Boolean deleteRewardEvent(String userId) {
		Boolean isDeleted = Boolean.FALSE;
		
		if (userId.equals("test1@test.com")) {
			isDeleted = Boolean.TRUE;
		} else if (userId.equals("test2@test.com")) {
			isDeleted = Boolean.TRUE;
		} else if (userId.equals("test3@test.com")) {
			isDeleted = Boolean.TRUE;
		} else {
			isDeleted = Boolean.FALSE;
		}
		
		return isDeleted;
	}
	
	private Boolean deleteRewardEvent(Integer eventId) {
		Boolean isDeleted = Boolean.FALSE;
		
		if (eventId.equals(0)) {
			isDeleted = Boolean.TRUE;
		} else if (eventId.equals(1)) {
			isDeleted = Boolean.TRUE;
		} else if (eventId.equals(2)) {
			isDeleted = Boolean.TRUE;
		} else {
			isDeleted = Boolean.FALSE;
		}
		
		return isDeleted;
	}
	
	private ArrayList<String> getRewards(String userId) {
		
		ArrayList<String> ary = new ArrayList<String>();
		
		if (userId == null){
			ary.add("Test1OneRewardEvent");
			ary.add("Test1TwoRewardEvent");
			ary.add("Test1ThreeRewardEvent");
			ary.add("Test1FourRewardEvent");
			
			ary.add("Test2OneRewardEvent");
			ary.add("Test2TwoRewardEvent");
			ary.add("Test2ThreeRewardEvent");
			ary.add("Test2FourRewardEvent");
			
			ary.add("Test3OneRewardEvent");
			ary.add("Test3TwoRewardEvent");
			ary.add("Test3ThreeRewardEvent");
			ary.add("Test3FourRewardEvent");
		} else if (userId.equalsIgnoreCase("test1@test.com")){
			ary.add("Test1OneRewardEvent");
			ary.add("Test1TwoRewardEvent");
			ary.add("Test1ThreeRewardEvent");
			ary.add("Test1FourRewardEvent");
		} else if (userId.equalsIgnoreCase("test2@test.com")) {
			ary.add("Test2OneRewardEvent");
			ary.add("Test2TwoRewardEvent");
			ary.add("Test2ThreeRewardEvent");
			ary.add("Test2FourRewardEvent");
		} else if (userId.equalsIgnoreCase("test3@test.com")) {
			ary.add("Test3OneRewardEvent");
			ary.add("Test3TwoRewardEvent");
			ary.add("Test3ThreeRewardEvent");
			ary.add("Test3FourRewardEvent");
		} else {
			ary.add("No events found");
		}

		return ary;
		
	}
	
	private ArrayList<String> getRewardEventTypes() {
		
		ArrayList<String> ary = new ArrayList<String>();
		
		ary.add("maintenance");
		ary.add("article");
		ary.add("appointment");
		ary.add("luck");

		return ary;
		
	}
	
}
