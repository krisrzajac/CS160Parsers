import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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
		String temp = Jsoup.connect(url).timeout(0).ignoreContentType(true).execute().body();
		return temp;
	}

	@Override
	public String ParseTitle(String s) {

		if (s.contains("\"name\"")) {
			Pattern pattern = Pattern.compile("\"name\":\"(.*?)\",");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "";
	}

	@Override
	public String ParseShortDescr(String s) {
		if (s.contains("\"shortDescription\"")) {
			Pattern pattern = Pattern.compile("\"shortDescription\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1);
		} else
			return "";
	}

	@Override
	public String ParseLongDescr(String s) {
		if (s.contains("\"aboutTheCourse\"")) {
			Pattern pattern = Pattern.compile("\"aboutTheCourse\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);

			if (matcher.find()) {

				// Since this is picking up HTML "Snippet" we need to clean up
				// the string of any tags / attributes
				return html2text(matcher.group(1)).trim().replaceAll("/\\\n", "");
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
			if (matcher.group(1) == "")
				return "";
			else
				return "https://www.youtube.com/watch?v=" + matcher.group(1);
		} else
			return "";
	}

	@Override
	public Date ParseStartDate(String s) {
		if (s.contains("\"startDate\"")) {
			Pattern pattern = Pattern
					.compile("\"startDay\":\"(.*?)\",.*\"startMonth\":\"(.*?)\",.*\"startYear\":\"(.*?)\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			Date date = new Date(11);
			date.setDate(Integer.parseInt(matcher.group(1)));
			date.setMonth(Integer.parseInt(matcher.group(2)) - 1);
			date.setYear(Integer.parseInt(matcher.group(3)));
			return date;
		} else
			return null;
	}

	@Override
	public int ParseCourseLength(String s) {
		if (s.contains("\"durationString\"")) {
			Pattern pattern = Pattern.compile("\"durationString\":\"(.*?)weeks\"");
			Matcher matcher = pattern.matcher(s);
			matcher.find();
			return Integer.parseInt(matcher.group(1));
		} else
			return -1;

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
			if(Boolean.parseBoolean(matcher.group(1))==true)
					{
						return "YES";
					}
			else
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
			else if(mat2.find())
				return mat2.group(1);
			else return "";
		} else {

			return "";
		}

	}

	@Override
	public String TimeScraped() {
		
		java.util.Date dt = new java.util.Date();
		
		
		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
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

		if (s.contains("\"id\""))
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
			Pattern pattern = Pattern.compile("\"courseId\":\"(.*?)\"");
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
			return (matcher.group(1));
		} else
			return "";
	}

}
