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
		
		Connection conn = DBMS.getConnection();
		String sql = "SELECT * FROM memberinfo WHERE name=? AND id=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			pstmt.setString(2,id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				if(name.equals(rs.getString("name")) && id.equals(rs.getString("id"))) {
					response.setStatus(HttpServletResponse.SC_OK);
					String memberPw = rs.getString("pw");
					request.setAttribute("memberPw", memberPw);
					RequestDispatcher rd = request.getRequestDispatcher("/findPw3");
					rd.forward(request, response);
					return;
				}else {
					
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
