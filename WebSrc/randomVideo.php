<?php
include("dbconnect.php");

?>



<?php
function returnSixRandomVids()
{
	include("dbconnect.php");

	$yt = "youtube";
	$embed = "embed";
    $query = $conn->query("
      SELECT *
      FROM course_data
     
	  WHERE video_link LIKE '%{$embed}%'
    ");
    
   
    if ( ! $query->num_rows ) {
		
		
      return false;
    }
    
    
    while( $row = $query->fetch_object() ) {
      $rows[] = $row;
    }
    
    
    $search_results = array(
      'count' => $query->num_rows,
      'results' => $rows,
    );
    
    return $search_results;
	}
		
	

?>