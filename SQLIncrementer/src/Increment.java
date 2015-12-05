import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Increment {

	public static String IncrementGroupData(String inFilePath, int incrementAmount)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		

		
		BufferedReader br = null;
		

		br = new BufferedReader(new InputStreamReader(new FileInputStream(inFilePath), "utf-8"));
		// br = new BufferedReader(new FileReader("C:\\Users\\Downstairs
		// Better\\eclipse\\WorkSpace\\Group2Fixer\\group2dataINC.sql"));
		String currentLine;
		String wholeFile = "";
		while ((currentLine = br.readLine()) != null) {
			// first half, finds id at beginning of both tables and replaces it
			// with the amount + increment value
			Pattern idPattern = Pattern.compile("([(]\\d*\\, )");
			Matcher matcher = idPattern.matcher(currentLine);
			if (matcher.find()) {
				System.out.println(matcher.group(1));
				int temp = Integer.parseInt(matcher.group(1).replaceAll("[(]", "").replaceAll("\\, ", ""))
						+ incrementAmount;
				System.out.println(temp);
				currentLine = currentLine.replaceAll("\\" + matcher.group(1), "(" + Integer.toString(temp) + ", ");

			}
			// second half, finds id course_id at end of course_details entries

			Pattern courseidPattern = Pattern.compile("(\\, \\d*[)])");
			matcher = courseidPattern.matcher(currentLine);
			if (matcher.find()) {
				System.out.println(matcher.group(1));
				int temp = Integer.parseInt(matcher.group(1).replaceAll("[)]", "").replaceAll("\\, ", ""))
						+ incrementAmount;
				System.out.println(temp);
				currentLine = currentLine.replaceAll(matcher.group(1).replaceAll("[)]", ""),
						", " + Integer.toString(temp));

			}

			currentLine = currentLine + "\n";
			wholeFile = wholeFile + currentLine;
		}

		return wholeFile;
	}
	public static void WriteToFile(String outPath,String data) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outPath), "utf-8"))) {
			writer.write(data);
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		String group2Data = IncrementGroupData("C:\\Users\\Downstairs Better\\Desktop\\DBstuff\\ExportedDatabaseSQL\\group2data.sql", 409);
		String group3Data = IncrementGroupData("C:\\Users\\Downstairs Better\\Desktop\\DBstuff\\ExportedDatabaseSQL\\group3data.sql", 536);
		String group4Data = IncrementGroupData("C:\\Users\\Downstairs Better\\Desktop\\DBstuff\\ExportedDatabaseSQL\\group4data.sql", 1354);
		WriteToFile("C:\\Users\\Downstairs Better\\Desktop\\DBstuff\\ExportedDatabaseSQL\\group2dataIncremented.sql", group2Data);
		WriteToFile("C:\\Users\\Downstairs Better\\Desktop\\DBstuff\\ExportedDatabaseSQL\\group3dataIncremented.sql", group3Data);
		WriteToFile("C:\\Users\\Downstairs Better\\Desktop\\DBstuff\\ExportedDatabaseSQL\\group4dataIncremented.sql", group4Data);
	}
}


