
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 
 */

/**
 * @author Downstairs Better
 *
 */

public class ParseCoursera {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String coursesURL = ("https://api.coursera.org/api/catalog.v1/courses?fields=photo,shortDescription,video,instructor,previewLink,language"),
				coursesAboutURL = ("https://api.coursera.org/api/catalog.v1/courses?fields=aboutTheCourse"),
				sessionsURL = ("https://api.coursera.org/api/catalog.v1/sessions?fields=courseId,homeLink,durationString,startDay,startMonth,startYear,name,eligibleForCertificates"),
				instructorsURL = ("https://api.coursera.org/api/catalog.v1/instructors?fields=photo,firstName,middleName,lastName,fullName,title"),
				universitiesURL = ("https://api.coursera.org/api/catalog.v1/universities?fields=name"),
				categoriesURL = ("https://api.coursera.org/api/catalog.v1/categories?includes=courses"),
				sessionsCourseLINKURL = ("https://api.coursera.org/api/catalog.v1/sessions?includes=courses");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/moocs160", "root", "");
		
		CourseraParser parse = new CourseraParser();

		String coursesJSON = (String) parse.GetURL(coursesURL), sessionsJSON = (String) parse.GetURL(sessionsURL),
				coursesAboutJSON = (String) parse.GetURL(coursesAboutURL),
				instructorsJSON = (String) parse.GetURL(instructorsURL),
				universitiesJSON = (String) parse.GetURL(universitiesURL),
				categoriesJSON = (String) parse.GetURL(categoriesURL),
				sessionsCLJSON = (String) parse.GetURL(sessionsCourseLINKURL);

		
		coursesJSON = parse.JSONTrim(coursesJSON);
		coursesAboutJSON = parse.JSONTrim(coursesAboutJSON);		
		universitiesJSON = parse.JSONTrim(universitiesJSON);
		categoriesJSON = parse.JSONTrim(categoriesJSON);
		
		
		
	
		String[] jsonCourseObjects = coursesJSON.split("\\},\\{");
		
		HashMap<Integer, TsengDBEntry> dbMap = new HashMap<Integer, TsengDBEntry>();
		int courseCount = 0;
		for (String s : jsonCourseObjects) {
			TsengDBEntry db = new TsengDBEntry();
			
			db.id = parse.ParseID(s);
			System.out.println(db.id);

			db.title = parse.ParseTitle(s);
			System.out.println(db.title);

			db.course_image = parse.ParseCourseImageURL(s);
			System.out.println(db.course_image);

		

			db.short_desc = parse.ParseShortDescr(s);
			System.out.println(db.short_desc);
			
			
			
			db.video_link = parse.ParseVidLink(s);
			System.out.println(db.video_link);
			
			String language = parse.ParseLanguage(s);
			System.out.println("language used iss ::::::::::::::      " + language);
			db.language = language;
			//String category = parse.ParseLongDescr((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?include=categories"));
			
			
			//System.out.println((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?fields=aboutTheCourse"));
			String aboutTheCourse = parse.ParseLongDescr((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?fields=aboutTheCourse"));
			
			db.long_desc = aboutTheCourse;
			System.out.println(aboutTheCourse);
			
			
			
			
			
			//System.out.println((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?include=universities"));
			String uni = parse.ParseUniID((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?include=universities"));
			System.out.println("uni id is " + uni);
			try{
				//System.out.println((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/universities/"+uni+"?fields=name"));
				uni = parse.ParseUni((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/universities/"+uni+"?fields=name"));
				System.out.println("Uni name is ::: " + uni);
				db.university = uni;
			}
			catch(Exception e)
			{
				uni = "";
			}
			
			
			
			
			dbMap.put(db.id,db);
			
			courseCount++;
		}
		
		
		
		System.out.println("Total # of courses ::::    "  + jsonCourseObjects.length);
		System.out.println("Total # of parsed ::::    "  + courseCount);
		
		categoriesJSON = categoriesJSON.split(",\"linked\"")[0];
		String[] categoriesStringArray =categoriesJSON.split("\\},\\{");
		for(String s : categoriesStringArray)
		{
			String tempName = parse.ParseTitle(s);
			Pattern idInCategoriesObject = Pattern.compile("courses\":\\[(.*)\\]\\}");
			Matcher matcher = idInCategoriesObject.matcher(s);
			if(matcher.find())
			{
				String[] ids = matcher.group(1).split(",");
				for(String s2: ids)
				{
					int tID = Integer.parseInt(s2);
					System.out.println("temp id" + tID);
					if(dbMap.get(tID)!=null)
					{
						TsengDBEntry tempDB =dbMap.get(tID);
						if(tempDB.category ==null)
							tempDB.category = tempName;
						else
							tempDB.category = tempDB.category +", "+tempName;
					}
					
				}
			}
		}
		
		
		
		/*
		sessionsCLJSON = parse.JSONTrim(sessionsCLJSON);
		String[] jsonCourseLinks = sessionsCLJSON.split("\\},\\{");
		HashMap<String, String> courseIDtoSessionIDmap = new HashMap<String, String>();
		for(String s : jsonCourseLinks)
		{
			sessionIDtoCourseIDMap.put(parse.Parse, value)
		}
		
		*/
		sessionsJSON = parse.JSONTrim(sessionsJSON);
		String[] jsonSessionObjects = sessionsJSON.split("\\},\\{");
		//sessions
		//db.course_link = parse.ParseCourseLink(s);
		//System.out.println(db.course_link);
		
		
		for (String s : jsonSessionObjects) {
			
			System.out.println(s);
			int sessionID = parse.ParseID(s);
			System.out.println("Session ID isssss " + sessionID);
			//System.out.println("String course ID is :  " + );
			
			
			int courseID = parse.ParseCourseID(s);
			
			TsengDBEntry db =dbMap.get(courseID);
			
			db.course_link = parse.ParseCourseLink(s);
			System.out.println(db.course_link);
			
			db.course_length = parse.ParseCourseLength(s);
			System.out.println(db.course_length);
			
			
			db.start_date = parse.ParseStartDate(s);
			System.out.println(db.start_date);
			
			db.certificate = parse.ParseCert(s);
			System.out.println(db.certificate);
			
			
			
			
			db.site =parse.SetSite("Coursera");
			db.time_scraped= parse.TimeScraped();
			
			dbMap.replace(courseID, db);
			
			
			
			System.out.println(dbMap.get(courseID).toString());
			
			courseCount++;
		}
		
		
		System.out.println("Total # of courses ::::    "  + jsonCourseObjects.length);
		System.out.println("Total # of parsed ::::    "  + courseCount);
		
		
		
		
		int uploadCount = 0;
		for(int key : dbMap.keySet())
		{
			System.out.println(uploadCount++);
			Statement statement = connection.createStatement();
			statement.executeUpdate(dbMap.get(key).PrepareQuery());// skip writing to database; focus on data printout to a text file instead.
			statement.close(); 
		}
		
		
		
		
		
		
		
		
		
	}

	

}
