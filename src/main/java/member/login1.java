package member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login1")
// 파라미터값들 확인(id,pw 조건 확인)
public class login1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력한 파라미터들 변수에 저장
		String id =request.getParameter("id");
		String pw =request.getParameter("pw");
		
		
		
		
		
		// Id와 Pw는 한개라도 입력이 안되있으면 안된다. 
		if(id==null || id.isEmpty()||pw==null||pw.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("브라우저의 뒤로가기 버튼을 눌러 ID또는 PW를 입력하세요.");
			out.close();
			return;
		}	
		
		RequestDispatcher rd = request.getRequestDispatcher("/login2");
		rd.forward(request, response);		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		doGet(request, response);
	}

}
