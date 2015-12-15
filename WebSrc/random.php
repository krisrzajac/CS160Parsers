<?php
include("dbconnect.php");

?>



<?php
function returnSixRandomCourses()
{
	include("dbconnect.php");
	$notSixRowsyet = true;
	while($notSixRowsyet)
	{
	$counterSql = "SELECT COUNT(*) FROM course_data";
	$countResult = mysqli_query($conn, $counterSql);
	
	$temp = mysqli_fetch_row($countResult);
	$totalRows = $temp[0];
	$firstRand = rand(3, $totalRows);
	$secondRand = rand(3,$totalRows);
	$thirdRand = rand(3, $totalRows);
	$fourthRand = rand(3, $totalRows);
	$fifthRand = rand(3, $totalRows);
	$sixthRand = rand(3, $totalRows);
	/*
	$firstRand = rand(3, 2100);
	$secondRand = rand(3, 2100);
	$thirdRand = rand(3, 2100);
	$fourthRand = rand(3, 2100);
	$fifthRand = rand(3, 2100);
	$sixthRand = rand(3, 2100);
	*/
	$sql="SELECT  * FROM course_data WHERE id IN (".$firstRand.","
	.$secondRand.","
	.$thirdRand.","
	.$fourthRand.","
	.$fifthRand.","
	.$sixthRand.")";
    $result = mysqli_query($conn, $sql);
			if($result->num_rows ==6)
{
	$notSixRowsyet = false;
}
	}
	
	

	
			

	while( $row = $result->fetch_object() ) {
      $rows[] = $row;
    }
    
    
    $sixCourses = array(
      'count' => $result->num_rows,
      'results' => $rows,
    );
		return $sixCourses;
	}
	
	
		
	

?>