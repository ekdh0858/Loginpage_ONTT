package member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findId1")
//정보를 올바르게 다 입력했는가 확인
public class findId1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =request.getParameter("name");
		String email = request.getParameter("email");
		name = name.trim();
		name = name.replace(" ", "");
		email = email.trim();
		email = email.replace(" ", "");
		if(name==null || name.isEmpty()||email==null||email.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("브라우저의 뒤로가기 버튼을 눌러 이름과 이메일주소를 입력하세요.");
			out.close();
			return;
		}	
		if(name.length()>10 || name.length()<6 || email.length()>50) {
			// 이름의 입력 조건에 맞게 적었는가
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/findId2");
		rd.forward(request, response);		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
