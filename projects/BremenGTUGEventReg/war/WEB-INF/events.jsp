<%@ page import="org.gtugs.bremen.eventReg.data.daos.EventDao" %>
<%@ page import="java.util.List" %>
<%@ page import="org.gtugs.bremen.eventReg.data.Event" %>

<%
	EventDao eventDao = new EventDao();
	List<Event> events = eventDao.getAllEvents(); %>
	
	<table>
	<% for(int i=0; i<events.size; i++) { %>
		<tr>
			<td><a href="<%= events.get(i).getUrl() %>"><%= events.get(i).getName() %></a></td>
			<td>
				<form action="/event">
					<input type="button" value="anmelden" onclick="document.location='registration?id= <%= events.get(i).getPkId() %> ">
				</form>
			</td>
		</tr>
	</table>
	
		