
<?php

 require_once( dirname( __FILE__ ) . '/random.php' );
 require_once( dirname( __FILE__ ) . '/randomVideo.php' );


$random_videos = returnSixRandomVids();
$random_courses = returnSixRandomCourses();






?>


<!DOCTYPE html>
<html>
<head>
	<title>CourseCrazy!</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,400italic,500,700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" media="all" href="owl/owl.carousel.css">
	<link rel="stylesheet" type="text/css" media="all" href="owl/owl.theme.css">
	<link rel="stylesheet" type="text/css" media="all" href="owl/owl.transitions.css">
	<link rel="stylesheet" type="text/css" href="calendar/view.css" media="all">


	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/singlepagenav.js"></script>
	<script type="text/javascript" src="js/queryloader.js"></script>	
	<script type="text/javascript" src="js/main.js"></script>
	<script src="owl/owl.carousel.min.js"></script>
	<script type="text/javascript" src="calendar/view.js"></script>
	<script type="text/javascript" src="calendar/calendar.js"></script>

	<meta charset="UTF-8">
	<meta name="description" content="CouseCrazy! - Take courses online!">
	<meta name="keywords" content="educational, learning, school, college, CS160, Software Engineering, HTML,CSS,XML,JavaScript">
	<meta name="author" content="CourseCrazy!">

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
            <li><a href="#home">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#services">Videos</a></li>
            <li><a href="#work">Random</a></li>
            <li><a href="#contact">Recommendations</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>


	<div class="content"  id="home">
		<div class="section section1">
			<div class="container">
				<div class="row">
					<div class="col-xs-6 col-sm-4 col-md-2 col-lg-2 col-xs-offset-3 col-sm-offset-4 col-md-offset-5 main-logo"></div>
				</div>
				<div class="row">
					<h2>CourseCrazy!</h2>
				</div>
				<div class="row">
					<p>
						An online school which will enhance your knowledge.
					</p>
					</div>
					<br>
				<div class="row">
				      <form action ="browse.php" method = "get">
				  <p> <input class="form-control-search" type="search" name = "s"> 
				  <input class="btn-search btn-success" type="submit" value="LEARN">
					
				  </p>
				  </form>
			  </div>

			</div>
				</div>
				<br>
				<br>
				 <div class="row">
                	<p>
                    	<a href = "browse.php" ><img src="img/browse.png"></a>
                    </p>
                </div>
			</div>
			</div>
		</div>
		<div class="section section2" id="about">
			<div class="container">
				<div class="row">
					<div class="col-md-6">
						<img class="img-responsive" title="PixelMock Introduction" src="img/big-mac.jpg">
					</div>
					<div class="col-md-6 intro-text">
						<h3 id="aboutus-title">What is CourseCrazy?</h3>
						<p>
							 CourseCrazy! is an education platform that partners with top universities and organizations worldwide, to offer courses online for anyone to take. You can choose from hundreds of courses created by the world's top educational institutions. Courses are open to anyone, and financial aid is available.
						</p>

						<p>
							Watch short video lectures, take interactive quizzes, complete peer graded assessments, and connect with fellow learners and instructors. With CourseCrazy! you can finish your course and receive formal recognition for your accomplishment with an optional Course Certificate.
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="section section3"  id="services">
			<div class="owlcontainer">
			  <div id="slider"> 
			    <div id="owl-demo" class="owl-carousel owl-theme">
				<?php
					$video_to_show = 100;
					$start =rand(0,count($random_videos['results']));
					
					if($start > $video_to_show)
					{
						$start = $start -($video_to_show);
					}
					else{
						$start =0;
					}
					for($x = $start;$x<=$start+$video_to_show;$x++)
					{	
						 
						 echo'<div class="owlitem"><iframe width="700" height="394" src="'.$random_videos['results'][$x]->video_link.'" frameborder="0" allowfullscreen></iframe></div>';
			       
					}
					i
			        ?>

			     </div>
			  </div>
			  <p class="slidertext">Watch videos of a real courses to see what type of learning style is best fit for yourself and if the material is something that will be to your interest.</p>
			</div>
			</div>


		</div>
		<div class="section section4" id="work">
			<div class="portfolio clearfix">
				<div id="portfolio-introduction">
					<h3>Begin Learning</h3>
					<p>Choose a random course or take our recommendations</p>
				</div>

				<div id="portfolio-items" class="clearfix">
					<?php 
					$count = 0;
					foreach($random_courses['results'] as $row)
		{
			
				print'<div>
					<!-- start of a portfolio item -->';
					if($count ==2 || $count ==3)
					{
						print'<div><div class="item">';
					}
					else
					{
					print'<div class="item w3">';
					}
						print'<div class="hover-content">
							<h3>'.$row->title.'</h3>
						</div>
						<div class="hidden-item">
							<img src="'.$row->course_image.'" title="'.$row->title.' ';
							if($count ==2)
							{
								print ' data-axisX="45" data-axisY="50">';
								
							}
							else
							{
								print' data-axisX="50" data-axisY="50">';
							}
							
						print'</div>
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
	                                            <p><b>Course Fee:</b>  '.$row->course_fee.' <br /> </p>
												<p><b>Languages offered:</b> '.$row->language.' <br /> </p>
												<p><b>Certification:</b> '.$row->certificate.' <br /> </p>
												<p><br /><br />'.$row->short_desc.' <br /> </p>
												<p>'.$row->video_link.' <br /> </p>
												<p style ="font-size:30px"><a href="'.$row->course_link.'" target="_blank">Check the course out today <br /></a> </p>
												
												
												
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
					
					';
					
		
				$count = $count + 1;
				if($count==6)
				{
					$count =0;
				}
		}?>

					
				</div>
				
			</div>
		</div>

		<div class="section section5 footer pushfooter" id="contact">
  <div class="container">
    <div class="row">
      <h3 id="contact-us-header">Recommendations</h3>
    </div>
    <div class="row">
      <div class="contact-description">
        <p>We are bunch of open minded people. So, we are really keen to know what you want to learn next.</p>
        <p>Furthermore, if you would like suggestions to what you can learn about next, fill out the following form and we would be happy to give you usefull recommendations. Our systems will search course that would be a good fit to your ability and fit your duration desires.</p>
      </div>
        
          <div id="form_container">
            <form id="contact-form-new"  method="post" action="">
              <article>
              <ul >
                <li id="li_3" >
                  <label class="description" for="element_3">Course Duration </label>
                  <span>
                  <input id="element_3_1" name="element_3_1" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_3_1">4 Weeks</label>
                  <input id="element_3_2" name="element_3_2" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_3_2">7 Weeks</label>
                  <input id="element_3_3" name="element_3_3" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_3_3">8 Weeks</label>
                  <input id="element_3_4" name="element_3_4" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_3_4">10 Weeks</label>
                  <input id="element_3_5" name="element_3_5" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_3_5">Unlimited</label>
                  </span> </li>
                <li id="li_1" >
                  <label class="description" for="element_1">Start Date </label>
                  <span>
                  <input id="element_1_3" name="element_1_3" class="element text" size="6" maxlength="4" value="" type="text">
                  —
                  <label for="element_1_3" id="contact-form-new-small">YYYY</label>
                  </span><span>
                  <input id="element_1_1" name="element_1_1" class="element text" size="4" maxlength="2" value="" type="text">
                  —
                  <label for="element_1_1" id="contact-form-new-small">MM</label>
                  </span> <span>
                  <input id="element_1_2" name="element_1_2" class="element text" size="4" maxlength="2" value="" type="text">
                  
                  <label for="element_1_2" id="contact-form-new-small">DD</label>
                  </span><span id="calendar_1"> <img id="cal_img_1" class="datepicker" src="img/calendar.gif" alt="Pick a date."> </span> 
                  <script type="text/javascript">
      Calendar.setup({
      inputField   : "element_1_3",
      baseField    : "element_1",
      displayArea  : "calendar_1",
      button     : "cal_img_1",
      ifFormat   : "%B %e, %Y",
      onSelect   : selectDate
      });
    </script> 
                </li>
                <li id="li_2" >
                  <label class="description" for="element_2">Course Fee </label>
                  <span class="symbol">$</span> <span>
                  <input id="element_2_1" name="element_2_1" class="element text currency" size="10" value="" type="text" />
                  
                  <label for="element_2_1" id="contact-form-new-small">Dollars</label>
                  </span>  </li>
                <li id="li_4" >
                  <label class="description" for="element_4">Languages </label>
                  <span>
                  <input id="element_4_1" name="element_4_1" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_4_1">English</label>
                  <input id="element_4_2" name="element_4_2" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_4_2">Español</label>
                  <input id="element_4_3" name="element_4_3" class="element checkbox" type="checkbox" value="1" />
                  <label class="choice" for="element_4_3">French</label>
                  </span> </li>
                <li class="buttons">
                  <input type="hidden" name="form_id" value="1079384" />
                  <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit" />
                </li>
              </ul>
            </article>
            </form>      
        
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="owl/script.js"></script>
</body>
</html>
