package member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findId4")
//정보를 다 입력했는가 확인
public class findId4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name =request.getParameter("name");
		String phone = request.getParameter("phone");
		
		phone=phone.trim();
		phone=phone.replace(" ", "");
		name=name.trim();
		name=name.replace(" ", "");
		
		if(name==null || name.isEmpty()||phone==null||phone.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("브라우저의 뒤로가기 버튼을 눌러 이름과 전화번호를 입력하세요.");
			out.close();
			return;
		}	
		
		RequestDispatcher rd = request.getRequestDispatcher("/findId5");
		rd.forward(request, response);		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
