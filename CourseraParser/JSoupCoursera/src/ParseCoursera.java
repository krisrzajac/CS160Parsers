
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	public static void main(String[] args)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String coursesURL = ("https://api.coursera.org/api/catalog.v1/courses?fields=photo,shortDescription,video,instructor,previewLink,language"),
				coursesAboutURL = ("https://api.coursera.org/api/catalog.v1/courses?fields=aboutTheCourse"),
				sessionsURL = ("https://api.coursera.org/api/catalog.v1/sessions?fields=courseId,homeLink,durationString,startDay,startMonth,startYear,name,eligibleForCertificates"),
				instructorsURL = ("https://api.coursera.org/api/catalog.v1/instructors?fields=photo,fullName"),
				universitiesURL = ("https://api.coursera.org/api/catalog.v1/universities?fields=name"),
				categoriesURL = ("https://api.coursera.org/api/catalog.v1/categories?includes=courses"),
				sessionsCourseLINKURL = ("https://api.coursera.org/api/catalog.v1/sessions?includes=courses");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		java.sql.Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost/moocs160?characterEncoding=UTF-8", "root", "");

		CourseraParser parse = new CourseraParser();

		String coursesJSON = (String) parse.GetURL(coursesURL), sessionsJSON = (String) parse.GetURL(sessionsURL),
				coursesAboutJSON = (String) parse.GetURL(coursesAboutURL),
				instructorsJSON = (String) parse.GetURL(instructorsURL),
				universitiesJSON = (String) parse.GetURL(universitiesURL),
				categoriesJSON = (String) parse.GetURL(categoriesURL),
				sessionsCLJSON = (String) parse.GetURL(sessionsCourseLINKURL);

		instructorsJSON = parse.JSONTrim(instructorsJSON);
		coursesJSON = parse.JSONTrim(coursesJSON);
		coursesAboutJSON = parse.JSONTrim(coursesAboutJSON);

		categoriesJSON = parse.JSONTrim(categoriesJSON);

		// Map the university id values (returned when querying
		// course?include=universities) to their names, will look up using
		// get(id) to retrieve associated name

		universitiesJSON = parse.JSONTrim(universitiesJSON);
		String[] universitiesObjects = universitiesJSON.split("\\},\\{");
		HashMap<Integer, String> uniIdToNameMap = new HashMap<Integer, String>();
		for (String s : universitiesObjects) {
			int id = parse.ParseID(s);
			String name = parse.ParseTitle(s);
			uniIdToNameMap.put(id, name);

		}
		System.out.println(uniIdToNameMap.toString());

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

			db.course_link = "https://www.coursera.org/course/" + parse.ParseShortName(s);

			// String category =
			// parse.ParseLongDescr((String)parse.GetURL("https://api.coursera.org/api/catalog.v1/courses/"+db.id+"?include=categories"));

			/*
			 * //System.out.println((String)parse.GetURL(
			 * "https://api.coursera.org/api/catalog.v1/courses/"+db.id+
			 * "?fields=aboutTheCourse")); String aboutTheCourse =
			 * parse.ParseLongDescr((String)parse.GetURL(
			 * "https://api.coursera.org/api/catalog.v1/courses/"+db.id+
			 * "?fields=aboutTheCourse"));
			 * 
			 * db.long_desc = aboutTheCourse;
			 * System.out.println(aboutTheCourse);
			 * 
			 * 
			 * 
			 * 
			 * 
			 * //System.out.println((String)parse.GetURL(
			 * "https://api.coursera.org/api/catalog.v1/courses/"+db.id+
			 * "?include=universities")); String uni =
			 * parse.ParseUniID((String)parse.GetURL(
			 * "https://api.coursera.org/api/catalog.v1/courses/"+db.id+
			 * "?include=universities")); System.out.println("uni id is " +
			 * uni); try{ //System.out.println((String)parse.GetURL(
			 * "https://api.coursera.org/api/catalog.v1/universities/"+uni+
			 * "?fields=name")); uni = parse.ParseUni((String)parse.GetURL(
			 * "https://api.coursera.org/api/catalog.v1/universities/"+uni+
			 * "?fields=name")); System.out.println("Uni name is ::: " + uni);
			 * db.university = uni; } catch(Exception e) { uni = ""; }
			 * 
			 */
			db.site = parse.SetSite("Coursera");
			dbMap.put(db.id, db);

			courseCount++;
		}

		System.out.println("Total # of courses ::::    " + jsonCourseObjects.length);
		System.out.println("Total # of parsed ::::    " + courseCount);

		String coursesToUniJSON = (String) parse
				.GetURL("https://api.coursera.org/api/catalog.v1/courses?includes=universities");
		coursesToUniJSON = parse.JSONTrim(coursesToUniJSON);
		coursesToUniJSON = coursesToUniJSON.split(",\"linked\"")[0];
		String[] coursesToUniObjects = coursesToUniJSON.split("\\},\\{");

		for (String s : coursesToUniObjects) {
			String tempName = parse.ParseTitle(s);
			String uniID = parse.ParseUniID(s);
			int id = parse.ParseID(s);
			TsengDBEntry tempDB = dbMap.get(id);
			System.out.println(uniIdToNameMap.toString());
			System.out.println("full text: " + s);
			System.out.println("tempName retrieve : " + tempName);
			System.out.println("uniID retrieve : " + uniID);
			System.out.println("id retrieve : " + id);
			// System.out.println("uni Name : "
			// +uniIdToNameMap.get(Integer.parseInt(uniID)));
			if (uniID.contains(",")) {
				for (String s2 : uniID.split(",")) {
					if (tempDB.university == null || tempDB.university == "")
						tempDB.university = uniIdToNameMap.get(Integer.parseInt(s2));
					else
						tempDB.university = tempDB.university + ", " + uniIdToNameMap.get(Integer.parseInt(s2));

				}
			} else
				tempDB.university = uniIdToNameMap.get(Integer.parseInt(uniID));
			dbMap.put(id, tempDB);

		}

		String aboutTheCoursesJSON = (String) parse
				.GetURL("https://api.coursera.org/api/catalog.v1/courses?fields=aboutTheCourse");
		aboutTheCoursesJSON = parse.JSONTrim(aboutTheCoursesJSON);
		String[] aboutTheCoursesObjects = aboutTheCoursesJSON.split("\\},\\{");
		int howManyCoursesFoundInAboutTheCourse = 0;
		for (String s : aboutTheCoursesObjects) {

			int id = parse.ParseID(s);
			String aboutTheCourse = parse.ParseLongDescr(s);
			TsengDBEntry tempDB = dbMap.get(id);
			tempDB.long_desc = aboutTheCourse;
			dbMap.put(id, tempDB);
			howManyCoursesFoundInAboutTheCourse++;
		}
		System.out.println("COURSES FOUND IN ABOUT THECOURSES" + howManyCoursesFoundInAboutTheCourse);

		categoriesJSON = categoriesJSON.split(",\"linked\"")[0];
		String[] categoriesStringArray = categoriesJSON.split("\\},\\{");
		for (String s : categoriesStringArray) {
			String tempName = parse.ParseTitle(s);
			Pattern idInCategoriesObject = Pattern.compile("courses\":\\[(.*)\\]\\}");
			Matcher matcher = idInCategoriesObject.matcher(s);
			if (matcher.find()) {
				String[] ids = matcher.group(1).split(",");
				for (String s2 : ids) {
					int tID = Integer.parseInt(s2);
					System.out.println("temp id" + tID);
					if (dbMap.get(tID) != null) {
						TsengDBEntry tempDB = dbMap.get(tID);
						if (tempDB.category == "")
							tempDB.category = tempName;
						else
							tempDB.category = tempDB.category + ", " + tempName;
					}

				}
			}
		}

		/*
		 * sessionsCLJSON = parse.JSONTrim(sessionsCLJSON); String[]
		 * jsonCourseLinks = sessionsCLJSON.split("\\},\\{"); HashMap<String,
		 * String> courseIDtoSessionIDmap = new HashMap<String, String>();
		 * for(String s : jsonCourseLinks) {
		 * sessionIDtoCourseIDMap.put(parse.Parse, value) }
		 * 
		 */
		sessionsJSON = parse.JSONTrim(sessionsJSON);
		String[] jsonSessionObjects = sessionsJSON.split("\\},\\{");
		// sessions
		// db.course_link = parse.ParseCourseLink(s);
		// System.out.println(db.course_link);
		courseCount = 0;
		for (String s : jsonSessionObjects) {

			System.out.println(s);
			int sessionID = parse.ParseID(s);
			System.out.println("Session ID isssss " + sessionID);
			// System.out.println("String course ID is : " + );

			int courseID = parse.ParseCourseID(s);

			TsengDBEntry db = dbMap.get(courseID);

			System.out.println(db.course_link);

			db.course_length = parse.ParseCourseLength(s);
			System.out.println(db.course_length);
			if (db.start_date.before(parse.ParseStartDate(s))) {
				db.start_date = parse.ParseStartDate(s);
				db.sessionId = sessionID;

			}

			System.out.println(db.start_date);

			db.certificate = parse.ParseCert(s);
			System.out.println(db.certificate);

			db.site = parse.SetSite("Coursera");
			db.time_scraped = parse.TimeScraped();

			dbMap.replace(courseID, db);

			System.out.println(dbMap.get(courseID).toString());

			courseCount++;
		}

		System.out.println("Total # of courses ::::    " + jsonCourseObjects.length);
		System.out.println("Total # of parsed ::::    " + courseCount);

		// Instructors info
		int instructorsFoundInCourses = 0;
		String instructorsFromCoursesJSON = (String) parse
				.GetURL("https://api.coursera.org/api/catalog.v1/courses?includes=instructors");
		instructorsFromCoursesJSON = parse.JSONTrim(instructorsFromCoursesJSON);
		instructorsFromCoursesJSON = instructorsFromCoursesJSON.split(",\"linked\"")[0];
		String[] instructorsFromCoursesObjects = instructorsFromCoursesJSON.split("\\},\\{");
		HashMap<Integer, String> instructorCourseMap = new HashMap<Integer, String>();

		for (String s : instructorsFromCoursesObjects) {
			String instructorId = parse.ParseInstructorCourseIDS(s);
			int coursId = parse.ParseID(s);
			instructorCourseMap.put(coursId, instructorId);
		}

		System.out.println("NUMBER OF INSTRUCTORS MAPPED TO COURSES     ::: " + instructorsFoundInCourses);

		int instructorcount = 0;

		String[] instructorsObjects = instructorsJSON.split("\\},\\{");
		HashMap<Integer, Instructors> instructorsMap = new HashMap<Integer, Instructors>();
		for (String s : instructorsObjects) {
			String currentInstructorID = "" + parse.ParseID(s);
			Instructors temp = new Instructors();
			temp.firstName = parse.ParseProfFirstName(s);
			temp.lastName = parse.ParseProfLastName(s);
			temp.fullName = parse.ParseProfName(s);
			temp.photoURL = parse.ParseProfPhoto(s);

		}
		System.out.println("NUMBER OF MATCHES TO INSTRUCTORCOURSEMAP     ::: " + instructorcount);

		int uploadCount = 3;
		int instructorCount=1;
		for (int key : dbMap.keySet()) {
			System.out.println(uploadCount);

			System.out.println(dbMap.get(key).PrepareQuery());
			System.out.println(dbMap.get(key).toString());
			Statement statement = connection.createStatement();
			statement.executeUpdate(dbMap.get(key).PrepareQuery());

			String tempLink = dbMap.get(key).course_link;
			Document tempD;
			try {
				tempD = Jsoup.connect(dbMap.get(key).course_link).timeout(0).get();
			} catch (java.net.ConnectException e) {
				System.out.println("attempting reconnect to :" + dbMap.get(key).course_link);
				tempD = Jsoup.connect(dbMap.get(key).course_link).timeout(0).get();
			}
			String instructorName = "";
			if (tempD.select("[class=c-cs-instructor-name]").first() != null) {
				instructorName = tempD.select("[class=c-cs-instructor-name]").first().text().replaceAll("'", "''");
				
			} else {

				if (instructorCourseMap.containsKey(key)) {
					String instID = instructorCourseMap.get(key);
					if (instructorsMap.containsKey(instID)) {
						if (instructorsMap.get(instID).fullName != "") {
							instructorName = instructorsMap.get(instID).fullName.replaceAll("'", "''");
							;
						} else if (instructorsMap.get(instID).firstName != "") {
							instructorName = instructorsMap.get(instID).firstName + " "
									+ instructorsMap.get(instID).lastName;
							instructorName = instructorName.replaceAll("'", "''");
						} else {
							instructorName = "N/A";
						}
					}
				} else {
					instructorName = "N/A";
				}

				/*
				 * if (tempD.select("[property=og:url]").first() != null) {
				 * Document tempD2 =
				 * Jsoup.connect(tempD.select("[property=og:url]").first().attr(
				 * "content")) .timeout(0).get(); if
				 * (tempD.select("[class*=instructor-name]").first() != null) {
				 * String instructorName =
				 * tempD.select("[class=c-cs-instructor-name]").first().text();
				 * } else { String s = parse.GetURL(url); }
				 * 
				 * }
				 */
			}
			String instructorPic = "";
			if (tempD.select("[class=c-cs-instructor-img]").first() != null) {
				instructorPic = tempD.select("[class=c-cs-instructor-img]").first().attr("src").split("\\?")[0]
						.replaceAll("'", "''");
			} else {

				if (instructorCourseMap.containsKey(key)) {
					String instID = instructorCourseMap.get(key);
					if (instructorsMap.containsKey(instID)) {
						if (instructorsMap.get(instID).photoURL != "") {
							instructorPic = instructorsMap.get(instID).photoURL.replaceAll("'", "''");

						} else {
							instructorPic = "N/A";
						}
					}
				} else {
					instructorPic = "N/A";
				}

				/*
				 * TsengDBEntry tempDb2 = dbMap.get(key); if
				 * (tempD.select("[property=og:url]").first() != null) { String
				 * s =
				 * tempD.select("[property=og:url]").first().attr("content");
				 * Document tempD2 =
				 * Jsoup.connect(tempD.select("[property=og:url]").first().attr(
				 * "content")) .timeout(0).get(); if
				 * (tempD.select("[class*=instructor-name]").first() != null) {
				 * String instructorPic =
				 * tempD.select("[class*=instructor-photo]").first().attr("src")
				 * .split("\\?")[0]; }
				 * 
				 * }
				 */
			}

			if (instructorName.length() > 4) {
				String queryCourseData = "SELECT id FROM COURSE_DATA WHERE short_desc= '" + dbMap.get(key).short_desc
						+ "'";
				ResultSet queryID = statement.executeQuery(queryCourseData);
				queryID.next();

				System.out.println("ID IN TABLE OF THIS ENTRY IS :::: " + queryID.getInt(1));

				int course_dataID = queryID.getInt(1);
				if (instructorName.length() > 29) {

					String[] subName = instructorName.split(" ");
					instructorName = instructorName.split(",")[0];
					int nameIterator = 0;
					while (instructorName.length() > 29) {
						System.out.println("hhehehehe" + instructorName);
						instructorName = instructorName.replaceAll(subName[nameIterator],
								subName[nameIterator].substring(0, 1) + ".");
						nameIterator++;

					}
				}

				statement.executeUpdate("INSERT INTO COURSEDETAILS VALUES('" + instructorCount + "','" + instructorName
						+ "','" + instructorPic + "','" + uploadCount + "')");
				instructorCount++;
			}
			uploadCount++;
		}

	}

}
