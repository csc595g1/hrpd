package main.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.TimeZone;

@XmlRootElement
public class RewardEvent {
	
	private String eventId;
	private String userId;
	private String eventCategory;
	private Integer units;
	private String title;
	private String tstamp;
	
	public RewardEvent() {}

	/* Purely for convenience */
	public RewardEvent(String eventId,String userId,String eventCategory,Integer units,String title,String tstamp) {
		this.eventId = eventId;
		this.userId = userId;
		this.eventCategory = eventCategory;
		this.units = units;
		this.title = title;
		this.tstamp = tstamp;
	}
	

	
	
	
	
	
	
	
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTstamp() {
		return tstamp;
	}

	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}

	

}
