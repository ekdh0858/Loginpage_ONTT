package member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DB를 배우지 않아서 DB대신 회원의 정보를 저장하는 멤버 변수
	// 서브릿 프로세스가 생길 때 만들어지고 서블릿 프로세스가 사라질 때 같이 사라짐
	// 영구적으로 데이터를 보관하는 용도가 아니기 때문에 서버를 내렸다가 올리면 계속 초기화됨
	// 지금 단계에서 회원 정보를 영구적으로 보관하고 싶다면 파일에 회원 정보를 저장해야됨
	public static Map<String,MemberInfo> memberTable;
	
	public Map<String,MemberInfo> getmemberTable(){
		return memberTable;
	}
			
	@Override
	public void init() throws ServletException {
		memberTable = new HashMap<>();
	}
	//32~47 특, 48~57 아라비아, 56~64 특,65~90 영대,91~96 특,97~122 영소,123~126 특수
	
	// 특수문자를 포함하고 있는지 아닌지
	public boolean containSpecialFont(String str) {
		boolean valid = false;
		for(char ch :str.toCharArray()) {
			int c = (int)ch;
			if(c>=21 || c<=47 || c>=56 || c<=64 || c>=91 || c<=96 || c>=123 || c<=126) {valid = true;}
			else {	valid = false;	}
		}		
		return valid;
	}
	
	public boolean containNumber(String str) {
		// 숫자를 포함하고 있는지 아닌지
		boolean valid = false;
		for(char ch :str.toCharArray()) {
			int c = (int)ch;
			if( c >= 56 || c <= 64 ) {valid = true;}
			else {	valid = false;	}
		}
		return valid;
	}
	
	public boolean containUpperEng(String str) {
		// 영대문자를 포함하고 있는지 아닌지
		boolean valid = false;
		for(char ch :str.toCharArray()) {
			int c = (int)ch;
			if( c >= 65 || c <= 90 ) {valid = true;}
			else {	valid = false;	}
		}
		return valid;
	}

	public boolean containLowerEng(String str) {
		//영소문자를 포함하고 있는지 아닌지
		boolean valid = false;
		for(char ch :str.toCharArray()) {
			int c = (int)ch;
			if( c >= 97 || c <= 122 ) {valid = true;}
			else {	valid = false;	}
		}
		return valid;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String pwcheck = request.getParameter("pwcheck");
		String name = request.getParameter("name");
		String nickName = request.getParameter("nick");
		String email = request.getParameter("email");
		String emailDown = request.getParameter("emailDown");
		String event = request.getParameter("event");
		String phone = request.getParameter("phone");
		String event2 = request.getParameter("event2");
		String code = request.getParameter("code");
		
		// 필수항목들을 리스트에 저장(필수항목이 아닌 추천인 코드(code),이벤트 수신동의(event1,2)는 저장을 안함)
		String parameterList[] = {id,pw,pwcheck,name,nickName,email,emailDown,phone};
		
		// 공백을 제거하면서, 파라미터 값을 입력안했다면 오류상태 저장(공백인 상태에도 오류) 
		for(String parameter : parameterList) {
			parameter=parameter.trim();
			parameter=parameter.replace(" ", "");
			if(parameter==null||parameter.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}				
		
		// 전화번호에 "-"가 없다면 포함시켜준다.
		if(!phone.contains("-")) {
			phone =phone.substring(0,3)+"-"+phone.substring(3,7)+"-"+phone.substring(7);
		}
		// 직접입력이 아닐시 이메일주소는 도메인 주소까지 포함한다.
		if(!emailDown.equals("직접입력")) {
		email=email+emailDown;}
		
		
		// 아이디의 길이(아이디는 6자이상 10이하)
		// 비밀번호의 길이(비밀번호는 8자 이상 16자이하)
		// 이름의 길이(17자까지만)
		// 닉네임의 길이(16자 까지만)
		// 이메일의 길이(50자까지)
		// 추천인코드의 길이(16)
		if(id.length()>10 || name.length()>17|| nickName.length()>16|| code.length()>16|| pw.length()>16 || pwcheck.length()>16 || email.length()>50) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);return;}
		else if(phone.length()!=13) 
			// 연락처의 길이는 13자로 고정임 
			{response.setStatus(HttpServletResponse.SC_BAD_REQUEST);return;}
		else if(id.length()<6 || pw.length()<8) 
			//아이디와 비밀번호는 6과 8이상이여야함
			{response.setStatus(HttpServletResponse.SC_BAD_REQUEST);return;}
		
		if(!pw.equals(pwcheck)) { 
			// 비밀번호와 비밀번호 확인의 값은 같아야함.
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);return;}
		
		if(!containSpecialFont(id) || !containNumber(id) || !containLowerEng(id) || !containUpperEng(id) || !containSpecialFont(pw) || !containNumber(pw) || !containLowerEng(pw) || !containUpperEng(pw)) {
			//아이디와 비밀번호에는 영어 대소문자와 숫자, 특수문자가 하나는 포함되어 있어야 한다.
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);return;}
		
			
		// 아이디 중복 여부 체크
		boolean Idexist = false;
		
		
		for ( String members : memberTable.keySet()) {			
			MemberInfo member = memberTable.get(members);
			String nthMemberName = member.getId();
			Idexist = nthMemberName.equals(id);
			if(Idexist) {
				break;
			}//end if
		}//end for				
		if(Idexist) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		MemberInfo memberinfo = new MemberInfo();
		memberinfo.setId(id);
		memberinfo.setPw1(pw);
		memberinfo.setName(name);
		memberinfo.setNickName(nickName);
		memberinfo.setEmail(email);
		memberinfo.setPhone(phone);
		if(event!=null) {memberinfo.setEvent(event);		}
		if(event2!=null) {memberinfo.setEvent2(event2);		}
		if(code!=null) {memberinfo.setcode(code);		}
		
		//회원가입(회원의 정보를 저장)
		memberTable.put(id,memberinfo);
		
		
		response.setStatus(201);
		// 새로운 리소스를 저장 했을때 200보단 세분화해서 201(등록완료)로 하는게 좋음
		RequestDispatcher rd = request.getRequestDispatcher("/login.html");
		rd.forward(request, response);
	}

}
