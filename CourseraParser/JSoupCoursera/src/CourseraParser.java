import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 
 */

/**
 * @author Downstairs Better
 *
 */
public class CourseraParser implements IMOOCSParser {

	@Override
	public String GetURL(String url) throws IOException {
		
		String temp = new String(Jsoup.connect(url).maxBodySize(1000000000).timeout(0).ignoreContentType(true).execute().body().getBytes("UTF-8"), "UTF-8");
		return temp;
	}

	@Override
	public String ParseTitle(String s) {

		if (s.contains("\"name\"")) {
			Pattern pattern = Pattern.compile("\"name\":\"(.*?)\",");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).replaceAll("'", "''");
		} else
			return "";
	}

	@Override
	public String ParseShortDescr(String s) {
		if (s.contains("\"shortDescription\"")) {
			Pattern pattern = Pattern.compile("\"shortDescription\":\"(.*?)\",\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).replaceAll("'", "''");
		} else
			return "";
	}

	@Override
	public String ParseLongDescr(String s) {
		if (s.contains("\"aboutTheCourse\"")) {
			Pattern pattern = Pattern.compile("\"aboutTheCourse\":\"(.*?)\",\"links\"");
			Matcher matcher = pattern.matcher(s);

			if (matcher.find()) {

				// Since this is picking up HTML "Snippet" we need to clean up
				// the string of any tags / attributes
				return html2text(matcher.group(1).trim().replaceAll("'", "''"));
				// return matcher.group(1).replaceAll("<span>",
				// "").replaceAll("<p>", "").replaceAll("</p>",
				// "").replaceAll("</b>", "")
				// .replaceAll("<b>", "").replaceAll("<br>",
				// "").replaceAll("<div>", "").replaceAll("</div>",
				// "").replaceAll("<strong>", "").replaceAll("</strong>", "")
				// .replaceAll("\\\\\n", "").replaceAll("<i>",
				// "").replaceAll("</i>", "").replaceAll("&nbsp",
				// "").replaceAll("</span>", "").

			} else
				return "";
		} else
			return "";

	}

	public static String html2text(String html) {

		return Jsoup.parse(html).text();
	}

	@Override
	public String ParseVidLink(String s) {
		if (s.contains("\"video\"")) {
			Pattern pattern = Pattern.compile("\"video\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			if (matcher.group(1).length()<5)
				return "N/A";
			else
				return "https://www.youtube.com/watch?v=" + matcher.group(1);
		} else
			return "";
	}

	@Override
	public Date ParseStartDate(String s) {
		if (s.contains("\"startDay\"")) {
			Pattern pattern = Pattern.compile("\"startDay\":(.*?),.*\"startMonth\":(.*?),.*\"startYear\":(.*?),");

			Matcher matcher = pattern.matcher(s);
			matcher.find();
			java.util.Date date = new Date(11);
			Calendar startCal = Calendar.getInstance();
			String year = matcher.group(3);
			String month = matcher.group(2);
			String day = matcher.group(1);
			if(Integer.parseInt(month)<10)
				month = "0".concat(month);
			if(Integer.parseInt(day)<0)
				day = "0".concat(day);
			return java.sql.Date.valueOf(year+"-"+month+"-"+day);
			/*startCal.set(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(1)));
			date = startCal.getTime();
			java.sql.Date sqlDate = new java.sql.Date(date.getYear(), date.getMonth(), date.getDay());
			return sqlDate;*/
		} else
			return java.sql.Date.valueOf("1999-01-01");
	}

	@Override
	public int ParseCourseLength(String s) {
		if (s.contains("\"durationString\"")) {
			Pattern pattern = Pattern.compile("\"durationString\":\"([0-9]*?) weeks\"");
			Matcher matcher = pattern.matcher(s);
			if (matcher.find())
				return Integer.parseInt(matcher.group(1));
			else
				return 0;
		} else
			return 0;

	}

	@Override
	public String ParseCourseImageURL(String s) {
		if (s.contains("\"photo\"")) {
			Pattern pattern = Pattern.compile("\"photo\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "";
	}

	@Override
	public String ParseCategory(String s) {
		if (s.contains("\"name\"")) {
			Pattern pattern = Pattern.compile("\"name\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "";
	}

	@Override
	public String SetSite(String s) {
		
		return s;

	}

	@Override
	public int ParseFee(String s) {

		return 0;
	}

	@Override
	public String ParseLanguage(String s) {
		if (s.contains("\"language\"")) {
			Pattern pattern = Pattern.compile("\"language\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "";
	}

	@Override
	public String ParseCert(String s) {
		if (s.contains("\"eligibleForCertificates\"")) {
			Pattern pattern = Pattern.compile("\"eligibleForCertificates\":(.*?),");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			if (Boolean.parseBoolean(matcher.group(1)) == true) {
				return "YES";
			} else
				return "NO";

		} else
			return "NO";
	}

	public String ParseUniID(String s) {
		if (s.contains("\"id\"")) {
			Pattern pattern = Pattern.compile("\"universities\":\\[\\{\"id\":(.*?),");
			Matcher matcher = pattern.matcher(s);

			Pattern pat2 = Pattern.compile("\"universities\":\\[(.*?)\\]");
			Matcher mat2 = pat2.matcher(s);
			if (matcher.find())
				return matcher.group(1);
			else if (mat2.find())
				return mat2.group(1);
			else
				return "";
		} else {

			return "";
		}

	}
	
	public String ParseInstructorCourseIDS(String s) {
		if (s.contains("\"id\"")) {
			Pattern pattern = Pattern.compile("\"instructors\":\\[\\{\"id\":(.*?),");
			Matcher matcher = pattern.matcher(s);

			Pattern pat2 = Pattern.compile("\"instructors\":\\[(.*?)\\]");
			Matcher mat2 = pat2.matcher(s);
			if (matcher.find())
				return matcher.group(1);
			else if (mat2.find())
				return mat2.group(1);
			else
				return "";
		} else {

			return "";
		}

	}

	@Override
	public String TimeScraped() {

		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		System.out.println("Current time before format   :::   " + dt);

		String currentTime = sdf.format(dt);
		System.out.println("Current time after format   :::   " + currentTime);

		return currentTime;
	}

	public String[] SeperateObjects(String s) {
		String[] list = s.split("},{");

		return list;
	}

	public String JSONTrim(String s) {
		return s.substring(14, s.length() - 1);
	}

	public int ParseID(String s) {

		if (s.contains("\"id\":"))
			return Integer.parseInt(s.split(",")[0].substring(5));
		else
			return -1;

	}

	@Override
	public String ParseCourseLink(String s) {
		if (s.contains("\"homeLink\"")) {
			Pattern pattern = Pattern.compile("\"homeLink\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "No Link Available";
	}

	public int ParseCourseID(String s) {
		if (s.contains("\"courseId\"")) {
			Pattern pattern = Pattern.compile("\"courseId\":(.*?),\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return Integer.parseInt(matcher.group(1));
		} else
			return -1;
	}

	@Override
	public String ParseUni(String s) {
		if (s.contains("\"name\"")) {
			Pattern pattern = Pattern.compile("\"name\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return (matcher.group(1)).replaceAll("'", "''");
		} else
			return "";
	}
	
	public String ParseProfName(String s) {

		if (s.contains("\"fullName\"")) {
			Pattern pattern = Pattern.compile("\"fullName\":\"(.*?)\",");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).replaceAll("'", "''");
		} else
			return "";
	}
	
	
	public String ParseProfPhoto(String s) {
		if (s.contains("\"photo\"")) {
			Pattern pattern = Pattern.compile("\"photo\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "";
	}
	
	public String ParseProfFirstName(String s) {

		if (s.contains("\"firstName\"")) {
			Pattern pattern = Pattern.compile("\"firstName\":\"(.*?)\",");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).replaceAll("'", "''");
		} else
			return "";
	}
	public String ParseProfLastName(String s) {

		if (s.contains("\"lastName\"")) {
			Pattern pattern = Pattern.compile("\"lastName\":\"(.*?)\",");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).replaceAll("'", "''");
		} else
			return "";
	}
	public String ParseShortName(String s)
	{
		if(s.contains("\"shortName\":"))
		{
			Pattern pattern = Pattern.compile("\"shortName\":\"(.*?)\",");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).replaceAll("'", "''");
		} else
			return "";
		
		
	}
	
	
}
