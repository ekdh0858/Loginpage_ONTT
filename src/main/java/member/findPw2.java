package member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findPw2")
public class findPw2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		
		name=name.trim();
		name=name.replace(" ", "");
		id=id.trim();
		id=id.replace(" ", "");
		boolean exist = false;		
		
		Map<String,MemberInfo> memberTable = Signup.memberTable;
		for(String members : memberTable.keySet()) {
			MemberInfo member = memberTable.get(members);
			String nthMemberName = member.getName();
			String nthMemberId = member.getId();
			System.out.println(nthMemberName+","+nthMemberId+","+name+","+id);
			if(name.equals(nthMemberName) && id.equals(nthMemberId)) {
				//이름과 전화번호가 일치하는 정보가있다면
				exist = true;
				response.setStatus(HttpServletResponse.SC_OK);
				String memberPw = member.getPw1();
				request.setAttribute("memberPw", memberPw);
				RequestDispatcher rd = request.getRequestDispatcher("/findPw3");
				rd.forward(request, response);
				return;
			}// end if
		}// end for
		
		if(!exist) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("일치하는 정보가 없습니다.");
			out.close();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);}	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
