<?php
include("dbconnect.php");


  

  
  
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
