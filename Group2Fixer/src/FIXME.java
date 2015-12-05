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

public class FIXME {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		// int incrementValue = Integer.parseInt(args[0]);
		AddNewLineAfterEachRow(null);
		AddSpacesAfterCommas("nada");
		FixSpaces(null);
	}
	public static void AddNewLineAfterEachRow(String inputFilePath) throws IOException
	{
		int incrementValue = 409;

		String readedData, alteredData;
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\ExportedDatabaseSQL\\group2data.sql"), "utf-8"));
		String currentLine;
		String wholeFile = "";
		while ((currentLine = br.readLine()) != null) {
			currentLine = currentLine.replaceAll("\\)\\,\\(", "),\n(");
			
			wholeFile =wholeFile +currentLine+"\n";
		}

		System.out.println(wholeFile);

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("group2dataFixed.sql"), "utf-8"))) {
			writer.write(wholeFile);
		}
	}
	
	
	public static void AddSpacesAfterCommas(String inputFilePath) throws IOException
	{
	
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(new FileInputStream("group2dataFixed.sql"), "utf-8"));
		String currentLine;
		String wholeFile = "";
		while ((currentLine = br.readLine()) != null) {
			currentLine = currentLine.replaceAll("\\,'", "\\, '");
			currentLine = currentLine.replaceAll("'\\,", "'\\, ");
			wholeFile =wholeFile +currentLine+"\n";
		}

		System.out.println(wholeFile);

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("group2dataFixed.sql"), "utf-8"))) {
			writer.write(wholeFile);
		}
	}
	
	
	
	public static void FixSpaces(String inputFilePath) throws IOException
	{
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(new FileInputStream("group2dataFixed.sql"), "utf-8"));
		String currentLine;
		String wholeFile = "";
		while ((currentLine = br.readLine()) != null) {
			currentLine = currentLine.replaceAll("  ", " ");
			
			wholeFile =wholeFile +currentLine+"\n";
		}

		System.out.println(wholeFile);

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("group2dataFixed.sql"), "utf-8"))) {
			writer.write(wholeFile);
		}
	}
}
