<?php
include("dbconnect.php");


include("searchdb.php");
if ( isset( $_GET['s'] ) ) {

  require_once( dirname( __FILE__ ) . '/searchdb.php' );
  

 
  
  $search_term = htmlspecialchars($_GET['s'], ENT_QUOTES);
  
  
  $search_results = searchTheDatabase($search_term,$conn);
}
else
{
	require_once( dirname( __FILE__ ) . '/searchdb.php' );
  
 
	$search_term ="What are ya buyin?";
	$search_results = searchTheDatabase($search_term,$conn);
}
?>
<html>
<head>
	<title>CourseCrazy!</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,400italic,500,700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="css/browse.css">
	

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/singlepagenav.js"></script>
	<script type="text/javascript" src="js/queryloader.js"></script>	
	<script type="text/javascript" src="js/main.js"></script>

	<meta charset="UTF-8">
	<meta name="description" content="CouseCrazy! - Take courses online!">
	<meta name="keywords" content="educational, learning, school, college, CS160, Software Engineering, HTML,CSS,XML,JavaScript">
	<meta name="author" content="CourseCrazy!">
	
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />

	<link rel="icon" type="image/png"  href="img/fav.png" />
	
</head>



<body>
	
	<div class="navbar navbar-inverse navbar-fixed-top top-nav" role="navigation">

      <div class="container">

        <div class="navbar-header">

          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">

            <span class="sr-only">Toggle navigation</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

          </button>

          <a class="navbar-brand" href="#">

          	<img src="img/pm-brand.png" title="CourseCrazy! Logo">

          </a>

        </div>

        <div class="collapse navbar-collapse ">

          <ul class="nav navbar-nav">

            <li><a href="index.html">Home</a></li>

            <li><a href="browse.php">Random</a></li>

            <li><a href="#contact">Recommendations</a></li>
			<li><div class="search-form">
      <form action="" method="get">
        <div class="form-field">
          
          <input type="search" name="s" placeholder="Enter your search term..." results="5" value="<?php echo $search_term; ?>">
          <input type="submit" value="Search">
        </div>
      </form>
    </div></li>

          </ul>
			
        </div><!--/.nav-collapse -->

      </div>

    </div>






				
			
</div>

 
<div class="section section4" id="work">
<?php if ( $search_results ) : 


		print'</div>
			<div class="portfolio clearfix">
				<div id="portfolio-introduction">
					<h3>'.$search_results['count'].' results found!</h3>
					<p>Click on a course for more information</p>
				</div>

				<div id="portfolio-items" class="clearfix">';
					
 foreach($search_results['results'] as $row)
		{
			
				print'<tr><td><div>
					<!-- start of a portfolio item -->
					<div><p><div class="item h3">
						<div class="hover-content">
							<h3>Click for more info</h3>
						</div>
						<div class="hidden-item">
							<img src="'.$row->course_image.'" title="'.$row->title.'" data-axisX="20" data-axisY="10">
						</div>
						<div class="modal fade portfolio-popup" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	                        <div class="modal-dialog modal-lg">
	                            <div class="modal-content">
	                                <div class="row">
	                                    <div class="col-xs-12">
	                                        <div class="portfolio-item-description">
	                                            <h3>'.$row->title.'</h3>
												<p>Class is hosted by '.$row->site.'</p>
	                                            <p><b>Course Duration:    </b>'.$row->course_length.' weeks</p>
												<p><b>Start Date:    </b>'.$row->start_date.' weeks</p>
	                                            <p>Course Fee:  '.$row->course_fee.' <br /> </p>
												<p>Languages offered: '.$row->language.' <br /> </p>
												<p>Certification: '.$row->certificate.' <br /> </p>
												<p><br /><br />'.$row->short_desc.' <br /> </p>
												<p>'.$row->video_link.' <br /> </p>
												<p style ="font-size:30px"><a href="'.$row->course_link.'">Check the course out today <br /></a> </p>
												
												
												
												<p></p>
												<p></p>
	                                            <p>End product is powered with Wordpress.</p>
	                                            <div class="labels">
	                                            	<span class="label label-default">'.$row->language.'</span>
													<span class="label label-primary">'.$row->course_fee.'$</span>
													<span class="label label-success">'.$row->category.'</span>
													
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <div class="col-xs-12">
	                                        <div class=\'popup-image-container\'>
	                                            <p><br /><br />'.$row->long_desc.' <br /> </p>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
					</div>
					</div>
					<p> <br /> </p></td>
					';
					
			
			echo '
					<td"><p style ="font-size:30px"><font color ="black"> <br />'.$row->title.'</p></td>
					<p> <br /> </p>
					<p> <br /> </p>
					<p> <br /> </p>
					
					
					
					
				</tr></p></div>';
					
				
				
		}?>
		
		
		
				</div>
				
			</div>
		</div>



	
  
	
    <?php endif; ?>
	
		
	
</div>
</body>

</html>
<?php
echo "<mm:dwdrfml documentRoot=" . __FILE__ .">";$included_files = get_included_files();foreach ($included_files as $filename) { echo "<mm:IncludeFile path=" . $filename . " />"; } echo "</mm:dwdrfml>";
?>