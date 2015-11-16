import java.sql.Date;
import java.sql.Timestamp;

public class TsengDBEntry implements IDBEntry {
	int id;
	String title;
	String short_desc;
	String long_desc;
	String course_link;
	String video_link;
	Date start_date;
	int course_length;
	String course_image;
	String category;
	String site;
	int course_fee;
	String language;
	String certificate;
	String university;
	String time_scraped;

	@Override
	public String PrepareQuery()
	{
		
		return "INSERT INTO COURSE_DATA VALUES(null,'"+title+"','"+short_desc+"','"+long_desc+"','"+course_link+"','"
				+video_link+"','"+start_date+"',"+course_length+",'"+course_image+"','"+category+"','"+site+"',"+course_fee+",'"+language+"','YES','"
				+university+"','"+time_scraped+ "')";
	}
	 @Override public String toString() {
		    StringBuilder result = new StringBuilder();
		    String NEW_LINE = System.getProperty("line.separator");

		    result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		    result.append(" Title: " + title + NEW_LINE);
		    result.append(" Short Description:  " + short_desc + NEW_LINE);
		    result.append(" Long Description: " + long_desc + NEW_LINE );
		    result.append(" Course Link: " + course_link + NEW_LINE); 
		    result.append(" Video Link: " + video_link + NEW_LINE);
		    result.append(" Start Date: " + start_date + NEW_LINE);
		    result.append(" Course Length: " + course_length + NEW_LINE);
		    result.append(" Course Image URL " + course_image + NEW_LINE);
		    result.append(" Category: " + category + NEW_LINE);
		    result.append(" Site: " + site + NEW_LINE);
		    result.append(" Course fee: " + course_fee + NEW_LINE);
		    result.append(" Language: " + language + NEW_LINE);
		    result.append(" Certificate?: " + certificate + NEW_LINE);
		    result.append(" University: " + university + NEW_LINE);
		    result.append(" Time Scraped: " + time_scraped + NEW_LINE);
		    result.append("}");

		    return result.toString();
		  }


}
