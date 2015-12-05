<?php
include("dbconnect.php");

?>



<?php
function returnSixRandomVids()
{
	include("dbconnect.php");

	
	$sql="SELECT  * 
	FROM course_data 
	WHERE video_link LIKE '%{youtube}%'
	";
	
    $result = mysqli_query($conn, $sql);

	
	

	/*
			$result->num_rows-6
			$count =0;
for(%x =$result->num_rows-6; %x<=$result->num_rows-1; $x++)
{
	$rows[$count]=$result->fetch_object();
}*/
	
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