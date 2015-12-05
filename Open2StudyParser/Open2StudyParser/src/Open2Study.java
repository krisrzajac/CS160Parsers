import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.Calendar;
class Open2Study {

	public static Elements GetHREF(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements coursePages = doc.select("a[href]");
			return coursePages;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
	
	
	
	public static void main(String[] args)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		
		Elements cp;
		cp = GetHREF("https://www.open2study.com/courses");
		HashSet<String> nodeUrls = new HashSet();
		for (Element href : cp) {
			if (href.attr("abs:href").contains("node")) {

				nodeUrls.add(href.attr("abs:href"));
			}
		}
		
		
		System.out.println(nodeUrls.size());
		for (String s : nodeUrls) {
			System.out.println(s);
		}

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/moocs160", "root", "");
		
		
		HashMap picMap = new HashMap();
		Document cpages = Jsoup.connect("https://www.open2study.com/courses").get();
		
		for(String s : nodeUrls)
		{
			String substring = s.substring(26);
			
			Elements picel =cpages.select("a[href=\""+substring+"\"]");
			
			picMap.put(s, picel.select("figure>img[class=image-style-course-logo-subjects-block]").attr("src"));
			
		}
		
		Document categoriesDoc = Jsoup.connect("https://www.open2study.com/").get();
		Elements categoryElements = categoriesDoc.select("div[class*=category]>div[class=view-content]");
		
		
		ArrayList<String> catList = new ArrayList();
		HashMap categoryMap = new HashMap();
		
		String currentCategory = "";
		for(Element el: categoryElements.select("h3,ul"))
		{
			if(el.tagName()=="h3")
				currentCategory = el.text();
			else
				for(Element el2 : el.select("a[href]"))
						categoryMap.put(el2.text(), currentCategory);
			
				
		}
		
		
		
		
		
		
		Document uniDoc = Jsoup.connect("https://www.open2study.com/educators").get();
	   
		HashMap uniMap = new HashMap();
		
		Elements uniElements = uniDoc.select("[class = item-list wrapper-1]>ul");
		//System.out.println(uniElements.select("li"));
		for(Element e : uniElements.select("li"))
		{
			//System.out.println();
			
			for(Element e2 : e.select("div.rightcontainer>ul>li"))
			{
				//System.out.println();
				uniMap.put(e2.select("a.linkedsubject").text(), e.select("div.toprow>h3.title>a[href]").text());
			}
		}
			
		
		uniDoc = Jsoup.connect("https://www.open2study.com/educators?page=1").get();
		   
	
		
		uniElements = uniDoc.select("[class = item-list wrapper-1]>ul");
		//System.out.println(uniElements.select("li"));
		for(Element e : uniElements.select("li"))
		{
			//System.out.println();
			
			for(Element e2 : e.select("div.rightcontainer>ul>li"))
			{
				//System.out.println();
				uniMap.put(e2.select("a.linkedsubject").text(), e.select("div.toprow>h3.title>a[href]").text());
			}
		}
		
		uniDoc = Jsoup.connect("https://www.open2study.com/educators?page=2").get();
		   
		
		
		uniElements = uniDoc.select("[class = item-list wrapper-1]>ul");
		//System.out.println(uniElements.select("li"));
		for(Element e : uniElements.select("li"))
		{
			//System.out.println();
			
			for(Element e2 : e.select("div.rightcontainer>ul>li"))
			{
				//System.out.println();
				uniMap.put(e2.select("a.linkedsubject").text(), e.select("div.toprow>h3.title>a[href]").text());
			}
		}
		System.out.println(uniMap);
		
		
		
		
		
		
		
		
		
		
		
		for (String s : nodeUrls) {
			
			
			Statement statement = connection.createStatement();
			
			
			
			Document d = Jsoup.connect(s).get();

			int courseID = Integer.parseInt(s.split("/")[s.split("/").length - 1]);
			System.out.println("Course ID is :: " + courseID);

			Elements courseURL = d.select("meta+[about]");
			String sCourseURL = "https://www.open2study.com" + courseURL.attr("about");
			System.out.println("Course URL is ::  " + sCourseURL);

			String sCourseTitle = d.select("h1[class=page-title offering_title]").text();
			String sCourseTitleLong = sCourseTitle.split("\\(")[0].trim();
			if (sCourseTitle.split("\\(").length > 1) {
				String sCourseTitleShort = sCourseTitle.split("\\(")[sCourseTitle.split("/").length].substring(0,
						sCourseTitle.split("\\(")[sCourseTitle.split("/").length].length() - 1).trim();
				System.out.println("Short Course Title is :: " + sCourseTitleShort);
			}
			System.out.println("Long Course Title is :: " + sCourseTitleLong);

			
			
			
			String sCourseImage=(String) picMap.get(s);
			
				
			
			
			System.out.println("Course image url is   ::   " + sCourseImage);
			
			
			
			// Elements courseDescription =
			// d.select("div[class=offering_body]");

			String sShortCourseDescription = d.select("div[class=offering_body]").text().replaceAll("'","''");

			System.out.println("Short description    :" + sShortCourseDescription);

			Elements longDesc = d.select("div[class=full-body]");

			String sLongDesc = longDesc.select("p").text().replaceAll("'","''");
			System.out.println("Long description   ::   " + sLongDesc);

			Elements courseDates = d.select("div[class=offering_dates_start]");
			Date startDate =new Date(1, 1, 1),
					endDate = new Date(1,1,1);
			java.sql.Date sqlStartDate = java.sql.Date.valueOf("2021-01-01");
			int courseLength=0;
			if (!courseDates.isEmpty()) {
				String sCourseStartDate = courseDates.get(0).text();
				System.out.println(sCourseStartDate);
				String sCourseEndDate = courseDates.get(1).text();
				System.out.println(sCourseEndDate);

				sCourseStartDate = sCourseStartDate.substring(18, sCourseStartDate.length());
				System.out.println(sCourseStartDate);

				sCourseEndDate = sCourseEndDate.substring(16, sCourseEndDate.length());
				System.out.println(sCourseEndDate);
				
				int startMonth, startDay,startYear;
				
				
				
				String [] dateTokens = sCourseStartDate.split("/");
			
				startDay=Integer.parseInt(dateTokens[0]);
				startMonth=Integer.parseInt(dateTokens[1]);
				startYear=Integer.parseInt(dateTokens[2]);
				
				if( Integer.parseInt(dateTokens[0])<10)
				{
					dateTokens[0] ="0".concat( dateTokens[0]);
				}
				if( Integer.parseInt(dateTokens[1])<10)
				{
					dateTokens[1] ="0".concat( dateTokens[1]);
				}
				
				sqlStartDate = java.sql.Date.valueOf(dateTokens[2] +"-" + dateTokens[1]+ "-"+dateTokens[0]);
				System.out.println("start year month day" + startYear+startMonth+startDay);
				
				
				
				
				
				int endMonth, endDay, endYear;
				dateTokens = sCourseEndDate.split("/");
				endDay = Integer.parseInt(dateTokens[0]);
				endMonth = Integer.parseInt(dateTokens[1]);
				endYear = Integer.parseInt(dateTokens[2]);
				
				
				
				
				
				
				
				
				Calendar startCal = Calendar.getInstance();
				startCal.set(startYear,startMonth,startDay);
				startDate = startCal.getTime();
				
				Calendar endCal = Calendar.getInstance();
				endCal.set(endYear,endMonth,endDay);
				endDate = endCal.getTime();
				//endDate = new Date(endYear,endMonth,endDay);
				
				courseLength = (int)( (endDate.getTime() - startDate.getTime()) 
		                 / (1000 * 60 * 60 * 24*7) );
				
				
				System.out.println("COURSELENGTH******** ::: " +courseLength);
				System.out.println("end year month day" + endYear+endMonth+endDay);
				
				System.out.println(startDate);
				System.out.println(endDate);
			}

			/*
			 * Elements whatwillyoulearn =
			 * d.select("div[class=whatwillyoulearncontainer]"); for(Element e :
			 * whatwillyoulearn) { System.out.println(
			 * "Element in whatyouwilllearn       " + e);
			 * 
			 * }
			 */

			
			Elements youTubeEmbed = d.select("[class=media-youtube-player]");
			String sYouTubeEmbed = youTubeEmbed.attr("src");
			System.out.println("Youtube link    ::  " +  sYouTubeEmbed);
			sYouTubeEmbed="https:"+sYouTubeEmbed;
			
			
			
			String category = "none";
			if(categoryMap.containsKey(sCourseTitleLong))
				category = (String) categoryMap.get(sCourseTitleLong);
			
			System.out.println("Category is    ::   " + category);
			
			
			
			
			
			String site = "Open2Study";
			
			System.out.println("Site this is available on ::   " + site);
			
			int courseFee = 0;
			String language = "English";
			boolean accredation = true;
			
			//Elements uniElements = d.select("div[id=provider-logo");
			//System.out.println(uniElements);
			
			
			String university = "NA";
			if(uniMap.containsKey(sCourseTitleLong))
			{
				university = (String) uniMap.get(sCourseTitleLong);
			}
			
			System.out.println("University listed as :::    "+ university);
			
			Date time = new Date();
			java.sql.Timestamp sqlTime = new java.sql.Timestamp(time.getTime());
			System.out.println("Time scraped at   ::"  + time);
			
			
			
			
			System.out.println("*********COURSEDETAILS********");
			System.out.println("*********COURSEDETAILS********");
			System.out.println("*********COURSEDETAILS********");
			
			String sProfName = d.select("span.field-content>h3").first().text();
			System.out.println(sProfName);
			//java.sql.Date sqlStartDate = new java.sql.Date(startDate.getYear(),startDate.getMonth(),startDate.getDay());
			System.out.println("Date is!!!! !!!      " + sqlStartDate);
			String sProfPhoto;
			System.out.println(d.select("div[class=views-field views-field-field-teacher-photo] > div.field-content > img[src]").attr("abs:src"));
			String queryCourseDetails="";
			String queryCourseData = "";
			String sTeacherPhoto =d.select("div[class=views-field views-field-field-teacher-photo] > div.field-content > img[src]").attr("abs:src");
			java.util.Date dt = new java.util.Date();
			
			

			java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			System.out.println("Current time before format   :::   " + dt);
			
			
			
			String currentTime = sdf.format(dt);
			System.out.println("Current time after format   :::   " + currentTime);
			
			
			
			
			
			queryCourseData = "INSERT INTO COURSE_DATA VALUES(null,'"+sCourseTitleLong+"','"+sShortCourseDescription+"','"+sLongDesc+"','"+sCourseURL+"','"
			+sYouTubeEmbed+"','"+sqlStartDate+"',"+courseLength+",'"+sCourseImage+"','"+category+"','"+site+"',"+courseFee+",'"+language+"','YES','"
					+university+"','"+currentTime+ "')";
			
			statement.executeUpdate(queryCourseData);// skip writing to database; focus on data printout to a text file instead.
			
			
			
			queryCourseData = "SELECT id FROM COURSE_DATA WHERE short_desc= '"+sShortCourseDescription+"'";
			ResultSet queryID = statement.executeQuery(queryCourseData);
			queryID.next();
			System.out.println("ID IN TABLE OF THIS ENTRY IS :::: " + queryID.getInt(1));
			int course_dataID =  queryID.getInt(1);
			if(sProfName.length()>29)
			{
				String[] subName = sProfName.split(" ");
				if(subName[0] == "Professor")
				{
					sProfName = "";
					for(int i =1; i<subName.length;i++)
						sProfName = sProfName + " " + subName[i];
					sProfName = sProfName.trim();
				}
				int nameIterator =0;
				while(sProfName.length()>29)
				{
					sProfName.replaceAll(subName[nameIterator], subName[nameIterator].substring(0, 1)+".");
					
				}
			}
			statement.executeUpdate("INSERT INTO COURSEDETAILS VALUES('"+course_dataID+"','"+sProfName+"','"+sTeacherPhoto+"','"+course_dataID+"')");
			
			statement.close(); 
			
		}

	}

}
