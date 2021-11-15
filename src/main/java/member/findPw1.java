package member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findPw1")
public class findPw1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력 받은 파라미터들을 변수에 저장
		String id =request.getParameter("id");
		String name =request.getParameter("name");
		
		id=id.trim();
		id=id.replace(" ", "");
		name=name.trim();
		name=name.replace(" ", "");
		
		
		// Id와 Pw는 한개라도 입력이 안되있으면 안된다. 
		if(id==null || id.isEmpty()||name==null||name.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("브라우저의 뒤로가기 버튼을 눌러 ID또는 이름을 입력하세요.");
			out.close();
			return;
		}	
		
		if(id.length()>10 || id.length()<6 || name.length()>17) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/findPw2");
		rd.forward(request, response);	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
