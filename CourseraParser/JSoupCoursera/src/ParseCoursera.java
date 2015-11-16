
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.*;

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
	 */
	public static void main(String[] args) throws IOException {

		String coursesURL = ("https://api.coursera.org/api/catalog.v1/courses?fields=photo,shortDescription,video,instructor,previewLink,language"),
				coursesAboutURL = ("https://api.coursera.org/api/catalog.v1/courses?fields=aboutTheCourse"),
				sessionsURL = ("https://api.coursera.org/api/catalog.v1/sessions?fields=homeLink,durationString,startDay,startMonth,startYear,name,eligibleForCertificates"),
				instructorsURL = ("https://api.coursera.org/api/catalog.v1/instructors?fields=photo,firstName,middleName,lastName,fullName,title"),
				universitiesURL = ("https://api.coursera.org/api/catalog.v1/universities"),
				categoriesURL = ("https://api.coursera.org/api/catalog.v1/categories");

		CourseraParser parse = new CourseraParser();

		String coursesJSON = (String) parse.GetURL(coursesURL), sessionsJSON = (String) parse.GetURL(sessionsURL),
				coursesAboutJSON = (String) parse.GetURL(coursesAboutURL),
				instructorsJSON = (String) parse.GetURL(instructorsURL),
				universitiesJSON = (String) parse.GetURL(universitiesURL),
				categoriesJSON = (String) parse.GetURL(categoriesURL);

		coursesJSON = parse.JSONTrim(coursesJSON);

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
			
			/*
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
			
			
			String language = parse.ParseLanguage((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?fields=language"));
			
			
			db.language = language;
			
			*/
			dbMap.put(db.id,db);
			
			courseCount++;
		}
		
		System.out.println("Total # of courses ::::    "  + jsonCourseObjects.length);
		System.out.println("Total # of parsed ::::    "  + courseCount);
		
		
		
		
		
		sessionsJSON = parse.JSONTrim(sessionsJSON);
		String[] jsonSessionObjects = sessionsJSON.split("\\},\\{");
		//sessions
		//db.course_link = parse.ParseCourseLink(s);
		//System.out.println(db.course_link);
		
		
		for (String s : jsonSessionObjects) {
			
			System.out.println(s);
			int tempId = parse.ParseID(s);
			System.out.println("Course ID isssss " + tempId);
			
			
			TsengDBEntry db =dbMap.get(tempId);
			
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
			
			dbMap.replace(tempId, db);
			
			
			
			System.out.println(dbMap.get(tempId).toString());
			
			courseCount++;
		}
		
		
		System.out.println("Total # of courses ::::    "  + jsonCourseObjects.length);
		System.out.println("Total # of parsed ::::    "  + courseCount);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	

}
