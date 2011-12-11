
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.DaoGenerator;


public class DemoGenerator {
	
	public static void main(String []args) {
		
		// create new schema
		Schema schema =  new Schema(1, "de.peacei.demo.greenDAO.database");
		
		// create entity
		Entity eventEntity = schema.addEntity("EventEntity");
		
		// add properties
		eventEntity.addIdProperty();
		eventEntity.addStringProperty("name");
		eventEntity.addStringProperty("location");
		eventEntity.setHasKeepSections(true);
		
		// set flag to keep labeled code in EventEntity.java
		eventEntity.setHasKeepSections(true);
			
		
		Entity attendeeEntity = schema.addEntity("AttendeeEntity");
		attendeeEntity.addIdProperty();
		attendeeEntity.addStringProperty("name");
		attendeeEntity.addStringProperty("secondName");
		
		// add LongProperty to be used as foreignKey
		Property prop = attendeeEntity.addLongProperty("eventId").getProperty();
		// add ToOne-Relation
		attendeeEntity.addToOne(eventEntity, prop);
		
		attendeeEntity.setHasKeepSections(true);
		
		
		try {
			// generate java-files in Android-Project (don't forget to add "src-gen" to the buildpath)
			new DaoGenerator().generateAll(schema, "../greenDAO_demo/src-gen");
		} catch(Exception ex) { 
			System.out.println(ex.getLocalizedMessage());
		}	
	}
}
