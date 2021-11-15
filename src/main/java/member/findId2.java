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

@WebServlet("/findId2")
// 입력한 이메일과 입력한 이름에 해당하는 정보가 데이터베이스에 있는지 확인
public class findId2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		name = name.trim();
		name = name.replace(" ", "");
		email = email.trim();
		email = email.replace(" ", "");
		boolean exist = false;		
		
		Map<String,MemberInfo> memberTable = Signup.memberTable;
		for(String members : memberTable.keySet()) {
			MemberInfo member = memberTable.get(members);
			String nthMemberEmail = member.getEmail();
			String nthMemberName = member.getName();
			
			if(name.equals(nthMemberName) && email.equals(nthMemberEmail)) {
				//이름과 이메일이 일치하는 정보가있다면
				exist = true;
				response.setStatus(HttpServletResponse.SC_OK);
				String memberId = member.getId();
				request.setAttribute("memberId", memberId);
				RequestDispatcher rd = request.getRequestDispatcher("/findId3");
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
