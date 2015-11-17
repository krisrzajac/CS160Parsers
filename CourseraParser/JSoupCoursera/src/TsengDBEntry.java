import java.sql.Date;
import java.util.Calendar;

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

	
	public TsengDBEntry() {
		super();
		this.id = -1;
		this.title = "";
		this.short_desc = "";
		this.long_desc = "";
		this.course_link = "";
		this.video_link = "";
		
		this.course_length = -1;
		this.course_image = "";
		this.category = "";
		this.site = "";
		this.course_fee = 0;
		this.language = "";
		this.certificate = "";
		this.university = "";
		
		
		Calendar startCal = Calendar.getInstance();
		java.util.Date date = new Date(11);
		startCal.set(0,0,0);
		date = startCal.getTime();
		this.start_date = new java.sql.Date(date.getYear(), date.getMonth(), date.getDay());
		
		
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		this.time_scraped = sdf.format(dt);
	}
	
	@Override
	public String PrepareQuery()
	{
		CleanCategories();
		return "INSERT INTO COURSE_DATA VALUES(null,'"+title+"','"+short_desc+"','"+long_desc+"','"+course_link+"','"
				+video_link+"','"+start_date+"',"+course_length+",'"+course_image+"','"+category+"','"+site+"',"+course_fee+",'"+language+"','YES','"
				+university+"','"+time_scraped+ "')";
	}
	 @Override public String toString() {
		    StringBuilder result = new StringBuilder();
		    String NEW_LINE = System.getProperty("line.separator");
		    
		    result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		    result.append(" id: " + id + NEW_LINE);
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

	public void CleanCategories()
	{
		if(category.contains("and"))
			category =category.replaceAll("and|And", "&");
		if(category.contains("Computer Science"))
			category =category.replaceAll("Computer Science", "ComSci");
		if(category.contains("Economics"))
			category =category.replaceAll("Economics", "Econ");
		if(category.contains("Social Sciences"))
			category =category.replaceAll("Social Sciences", "SocSci");
		if(category.contains("Sciences"))
			category =category.replaceAll("Sciences", "Sci");
		if(category.contains("Mathematics"))
			category =category.replaceAll("Mathematics", "Math");
		if(category.contains("Information"))
			category =category.replaceAll("Information", "Info");
		if(category.contains("Biology"))
			category =category.replaceAll("Biology", "Bio");
		if(category.contains("Business"))
			category =category.replaceAll("Business", "Bus");
		if(category.contains("Chemistry"))
			category =category.replaceAll("Chemistry", "Chem");
		if(category.contains("Management"))
			category =category.replaceAll("Management", "Mngmt");
		if(category.contains("Finance"))
			category =category.replaceAll("Finance", "Fin");	
		if(category.contains("Artificial Intelligence"))
			category =category.replaceAll("Artificial Intelligence", "AI");
		if(category.contains("Statistics & Data Analysis"))
			category =category.replaceAll("Statistics & Data Analysis", "Stats");
		if(category.contains("Engineering"))
			category =category.replaceAll("Engineering", "Engi");
		if(category.contains("Food & Nutrition"))
			category =category.replaceAll("Food & Nutrition", "Nutrit");
		if(category.contains("Health & Society"))
			category =category.replaceAll("Health & Society", "Health");
		if(category.contains("Tech & Design"))
			category =category.replaceAll("Tech & Design", "Tech");
		
		if(category.contains("Teacher Professional Development"))
			category =category.replaceAll("Teacher Professional Development", "Teach");
		if(category.contains("Music, Film, & Audio"))
			category =category.replaceAll("Music, Film, & Audio", "Music");	
		
		if(category.contains("Software Engi"))
			category =category.replaceAll("Software Engi", "SE");
		if(category.contains("Physical & Earth Sci"))
			category =category.replaceAll("Physical & Earth Sci", "EarthSci");
		category = category.trim();
		if(category.length()>99)
			category = category.substring(0, 90)+"CONCAT**";
	}
}
