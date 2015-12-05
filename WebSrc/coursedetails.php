<?php
include("dbconnect.php");

?>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
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
$sql = "SELECT course_data.id,course_id,profname,profimage,title,course_link FROM coursedetails, course_data WHERE course_id = course_data.id";
$result = mysqli_query($conn, $sql);

if($result)
{
	print 
	"<table class=\"hoverTable\">
		<thead>
			<tr>
				<th bgcolor=\"silver\">ID</th>
				<th bgcolor=\"silver\">COURSEID</th>
				<th bgcolor=\"silver\">COURSETITLE</th>
				<th bgcolor=\"silver\">INSTRUCTOR</th>
				<th bgcolor=\"silver\">INSTRUCTORPIC</th>
				<th bgcolor=\"silver\">Course URL</th>
				
			 </tr>
		</thead>";
		while($row = mysqli_fetch_array($result))
		{
			echo '<tr>
					<td>'.$row["id"].'</td>
					<td>'.$row["course_id"].'</td>
					<td>'.$row["title"].'</td>
					<td>'.$row["profname"].'</td>
					<td><img src ="'.$row["profimage"].'" height="100"></td>			
					<td><a href='.$row["course_link"].'>'.$row["course_link"].'</a></td>
					
				</tr>';
					
				
				
		}
			
}
				

	 			 
			 
			 


				mysqli_free_result($result);
?>			
