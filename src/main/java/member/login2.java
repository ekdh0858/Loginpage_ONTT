package member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login2")
//Id와 Pw에 해당하는 정보가 데이터베이스에 있는지 확인
public class login2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		boolean exist = false;
		
		
		
		Map<String,MemberInfo> memberTable = Signup.memberTable;
		for(String memberInfo : memberTable.keySet()) {
			MemberInfo member = memberTable.get(memberInfo);
			String nthMemberId = member.getId();
			String nthMemberPw = member.getPw1();
			
			if(id.equals(nthMemberId) && pw.equals(nthMemberPw)) {
				//로그인 성공
				exist = true;
				response.setStatus(HttpServletResponse.SC_OK);
				RequestDispatcher rd = request.getRequestDispatcher("/login3");
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
