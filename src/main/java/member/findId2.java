package member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBMS;

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
		
		
		// DB접속
		// 이름과 이메일이 일치하는 회원을 찾아 아이디를 저장후 전달
		Connection conn = DBMS.getConnection();
		String sql = "SELECT * FROM memberinfo WHERE name=? AND email=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			pstmt.setString(2,email);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(name.equals(rs.getString("name")) && email.equals(rs.getString("email"))) {
					response.setStatus(HttpServletResponse.SC_OK);
					String memberId = rs.getString("id");
					request.setAttribute("memberId", memberId);
					RequestDispatcher rd = request.getRequestDispatcher("/findId3");
					rd.forward(request, response);
					return;
				}else {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("일치하는 정보가 없습니다.");
					out.close();
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);	
				}
			}//end while
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		
				
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
