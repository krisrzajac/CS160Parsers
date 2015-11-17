<?php
include("dbconnect.php");
?>


<style style="text/css">
  	.hoverTable{
		
		border-collapse:collapse; 
	}
	.hoverTable td{ 
		padding:7px; border:#4e95f4 1px solid;
	}
	/* Define the default color for all the table rows */
	.hoverTable tr{
		background: #b8d1f3;
	}
	/* Define the hover highlight color for the table row */
    .hoverTable tr:hover {
          background-color: #ffff99;
    }
</style>

<?php
$sql = "select * from course_data";
$result = mysqli_query($conn, $sql);

if($result)
{
	print 
	"<table class=\"hoverTable\">
		<thead>
			<tr>
				<th bgcolor=\"silver\">ID</th>
				<th bgcolor=\"silver\">IMG</th>
				<th bgcolor=\"silver\">Title</th>
				<th bgcolor=\"silver\">Short Description</th>
				<th bgcolor=\"silver\">Long Description</th>
				<th bgcolor=\"silver\">Course URL</th>
				<th bgcolor=\"silver\">Video Link</th>
				<th bgcolor=\"silver\">Start Date</th>
				<th bgcolor=\"silver\">Course Length</th>
				<th bgcolor=\"silver\">University</th>
				<th bgcolor=\"silver\">Category</th>
				<th bgcolor=\"silver\">Site</th>
				<th bgcolor=\"silver\">Course fee</th>
				<th bgcolor=\"silver\">Language</th>
				<th bgcolor=\"silver\">Certificate</th>
				<th bgcolor=\"silver\">Time Scraped</th>
			 </tr>
		</thead>";
		while($row = mysqli_fetch_array($result))
		{
			echo '<tr>
					<td>'.$row["id"].'</td>
					<td><img src ="'.$row["course_image"].'" height="100"></td>
					<td>'.$row["title"].'</td>
					<td>'.$row["short_desc"].'</td>
					<td>'.$row["long_desc"].'</td>
					<td>'.$row["course_link"].'</td>
					<td>'.$row["video_link"].'</td>
					<td>'.$row["start_date"].'</td>
					<td>'.$row["course_length"].'</td>
					<td>'.$row["university"].'</td>
					<td>'.$row["category"].'</td>
					<td>'.$row["site"].'</td>
					<td>'.$row["course_fee"].'</td>
					<td>'.$row["language"].'</td>
					<td>'.$row["certificate"].'</td>
					<td>'.$row["time_scraped"].'</td>
				</tr>';
					
				
				
		}
			print "/table";
}
				

	 			 
			 
			 


				mysqli_free_result($result);
?>			

<table class="hoverTable">
	<tr>
		<td>Text 1A</td><td>Text 1B</td><td>Text 1C</td>
	</tr>
	<tr>
		<td>Text 2A</td><td>Text 2B</td><td>Text 2C</td>
	</tr>
	<tr>
		<td>Text 3A</td><td>Text 3B</td><td>Text 3C</td>
	</tr>
</table>
