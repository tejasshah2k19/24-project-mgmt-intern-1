
<%

	if(session.getAttribute("user") == null)
	{
		out.print("<h2>Hi</h2>");
		response.sendRedirect("/login");
	}
	
%>
