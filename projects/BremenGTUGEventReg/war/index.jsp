<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="org.gtugs.bremen.eventReg.data.Event" %>
<%@ page import="org.gtugs.bremen.eventReg.data.daos.EventDao" %>
<%@ page import="java.util.List" %>


<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Bremen GTUG</title>


	<style type="text/css" media="screen">
		@import url(style.css);
	</style>
<body>


<div id="wrap">




<div id="header" class="round-top">
	
	<a href="http://bremengtugeventreg.appspot.com">
		<img id="logo" src="../images/gtug-bremen-trans_480.png"/>
	</a>
	<img id="maskot" src="../images/maskot.png"/>
	
</div>


<div id="navbar">
	<ul>
		<li><span id="eventsspan">Events</span></li>
		<li><span id="adminspan">Admin</span></li>
	</ul> 	
	
</div>


<div id="main" class="round-bottom">

	<div id="content">
		
		
		<%
	EventDao eventDao = new EventDao();
	List<Event> events = eventDao.getAllEvents();
	events.size(); %>
	
	<table>
	<% for(int i=0; i<events.size(); i++) { %>
		<tr>
			<td><a href="<%= events.get(i).getUrl() %>"><%= events.get(i).getName() %></a></td>
			<td>
				<form action="../event">
					<input type="button" value="anmelden" onclick="document.location='registration?id= <%= events.get(i).getPkId() %> ">
				</form>
			</td>
		</tr>
	<% } %>
	</table>
   	</div>
		




	<div id="sidebar" class="round">
		<div id="twitter">
			<a title="Follow us on Twitter"
				href="http://www.twitter.com/peacei" target="_blank">
				<img src="../images/twitter.png" />
			</a>
		</div>
		<div id="facebook">
			<a title="Like us on Facebook" target="_blank"
				href="http://www.facebook.com/people/Matthias-Friedrich/100000740014232">
				<img src="../images/facebook.png" />
			</a>
			
		</div>

		<div id="gplus">
			<a title="Circle us on Google+" target="_blank"
				href="https://plus.google.com/107701928579044041923">
				<img src="../images/gplus.png"	/>
			</a>
			
		</div>
		
	</div>	

</div>



<div id="footer" class="round">





</div>




</div>

<script type="text/javascript" src="../js/jquery.min.js"></script>
	</head>
	<script type="text/javascript">
		document.getElementById("eventsspan").onclick = function () {
            refreshDiv("#content", "../events.jsp");
        };
        
        document.getElementById("adminspan").onclick = function () {
            refreshDiv("#content", "../admin.jsp");
        };
        
        function refreshDiv(divId, url) {
            $.ajax({
                url: url,
                type: 'GET',
                success: function (htmlData) {
                    $(divId).html(htmlData);
                }
            });
        }
	</script>

</body>
</html>
