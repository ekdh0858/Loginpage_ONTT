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

@WebServlet("/findId5")
// 입력한 전화번호와 입력한 이름에 해당하는 정보가 데이터베이스에 있는지 확인
public class findId5 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		phone=phone.trim();
		phone=phone.replace(" ", "");
		name=name.trim();
		name=name.replace(" ", "");
		
		Connection conn = DBMS.getConnection();
		String sql = "SELECT * FROM memberinfo WHERE name=? AND phone=?";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(name.equals(rs.getString("name")) && phone.equals(rs.getString("phone"))) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
			
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
