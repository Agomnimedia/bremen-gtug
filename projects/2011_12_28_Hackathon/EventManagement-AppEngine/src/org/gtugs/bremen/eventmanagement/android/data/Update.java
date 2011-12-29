package org.gtugs.bremen.eventmanagement.android.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Update {

	public final static int ALL_EVENTS = 1;
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Integer kind;
	
	@Persistent
	private Date date;
	
	@Persistent
	private String additionalInfo;

	public Key getKey() {
		return key;
	}

	public void setPkId(Key key) {
		this.key = key;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getAdditionalInfo(){
		return additionalInfo;
	}
	
	public void setAdditionalInfo(String additionalInfo){
		this.additionalInfo = additionalInfo;
	}
}