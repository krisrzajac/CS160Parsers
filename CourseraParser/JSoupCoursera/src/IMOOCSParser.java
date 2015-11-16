import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public interface IMOOCSParser {
	public Object GetURL(String url) throws IOException;
	public String ParseTitle(String s);
	public String ParseShortDescr(String s);
	public String ParseLongDescr(String s);
	public String ParseCourseLink(String s);
	public String ParseVidLink(String s);
	public Date ParseStartDate(String s);
	public int ParseCourseLength(String s);
	public String ParseCourseImageURL(String s);
	public String ParseCategory(String s);
	public String SetSite(String s);
	public int ParseFee(String s);
	public String ParseLanguage(String s);
	public String ParseCert(String s);
	public String ParseUni(String s);
	public String TimeScraped();
}
