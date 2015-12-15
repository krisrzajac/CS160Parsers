<?php
include("dbconnect.php");



function searchRecommendedCourses($search_term,$conp) {

	$queryUsed = "SELECT * FROM course_data";
	$numberOfAdditionalQueries = 0;

	if($search_term['4Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=4";
		}
		else{
			$queryUsed .= " OR course_length=4";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}

	if($search_term['7Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=7";
		}
		else{
			$queryUsed .= " OR course_length=7";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	if($search_term['8Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=8";
		}
		else{
			$queryUsed .= " OR course_length=8";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	if($search_term['10Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=10";
		}
		else{
			$queryUsed .= " OR course_length=10";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}

	if($search_term['0Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=0";
		}
		else{
			$queryUsed .= " OR course_length=0";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}


	if($search_term['Year'] !=-1 &&$search_term['Month'] !=-1 &&$search_term['Day'] !=-1){
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE start_date='".$search_term['Year']."-".$search_term['Month']."-".$search_term['Day']."'";
		}
		else{
			$queryUsed .= " OR start_date=".$search_term['Year']."-".$search_term['Month']."-".$search_term['Day'];
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}

	if($search_term['English'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE language='English'";
		}
		else{
			$queryUsed .= " OR language='English'";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}

	if($search_term['Spanish'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE language='Spanish'";
		}
		else{
			$queryUsed .= " OR language='Spanish'";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}

	if($search_term['French'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE language='French'";
		}
		else{
			$queryUsed .= " OR language='French'";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	if($search_term['cost'] !=-1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_fee=".$search_term['cost'];
		}
		else{
			$queryUsed .= " OR course_fee=".$search_term['cost'];
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}




	

	$query = $conp->query($queryUsed);

	 
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









  
  function searchRecommendedCourses($search_term,$conp) {
    
    $queryUsed = "SELECT * FROM course_data";
	$numberOfAdditionalQueries = 0;
	
	if($search_term['4Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=4";
		}
		else{
			$queryUsed .= " OR course_length=4";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	if($search_term['7Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=7";
		}
		else{
			$queryUsed .= " OR course_length=7";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	if($search_term['8Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=8";
		}
		else{
			$queryUsed .= " OR course_length=8";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	if($search_term['10Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=10";
		}
		else{
			$queryUsed .= " OR course_length=10";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	if($search_term['0Weeks'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_length=0";
		}
		else{
			$queryUsed .= " OR course_length=0";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	
	if($search_term['Year'] !=-1 &&$search_term['Month'] !=-1 &&$search_term['Day'] !=-1){
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE start_date='".$search_term['Year']."-".$search_term['Month']."-".$search_term['Day']."'";
		}
		else{
			$queryUsed .= " OR start_date=".$search_term['Year']."-".$search_term['Month']."-".$search_term['Day'];
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	if($search_term['English'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE language='English'";
		}
		else{
			$queryUsed .= " OR language='English'";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	if($search_term['Spanish'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE language='Spanish'";
		}
		else{
			$queryUsed .= " OR language='Spanish'";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	if($search_term['French'] ==1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE language='French'";
		}
		else{
			$queryUsed .= " OR language='French'";
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	if($search_term['cost'] !=-1)
	{
		if($numberOfAdditionalQueries ==0)
		{
			$queryUsed .= " WHERE course_fee=".$search_term['cost'];
		}
		else{
			$queryUsed .= " OR course_fee=".$search_term['cost'];
		}
		$numberOfAdditionalQueries = $numberOfAdditionalQueries+1;
	}
	
	
	
	
	print $queryUsed;
	
    $query = $conp->query($queryUsed);
    
   
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
  

  
  
  function searchTheDatabase($search_term,$conp) {
    
    $cleanme = $conp->real_escape_string($search_term);
    
   
	if($search_term != "What are ya buyin?")
	{
    $query = $conp->query("
      SELECT *
      FROM course_data
      WHERE title LIKE '%{$cleanme}%'
      OR category LIKE '%{$cleanme}%'
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	else
	{
		$query = $conp->query("
      SELECT * FROM course_data
      
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
	
  }
  ?>
