package kr.mem.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.mem.model.MemberDAO;
import kr.mem.model.MemberVO;
import kr.mem.pojo.Controller;
import kr.mem.pojo.MemberDeleteController;
import kr.mem.pojo.MemberInsertFormController;
import kr.mem.pojo.MemberInsertController;
import kr.mem.pojo.MemberListController;


public class MemberFrontController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		//1.� ��û���� �ľ��ϴ� �۾�--> *.do
		String reqUrl=request.getRequestURI();
		//System.out.println(reqUrl);
		String ctxPath=request.getContextPath();
		//System.out.println(ctxPath);
		//Ŭ���̾�Ʈ�� ��û�� ���
		String command=reqUrl.substring(ctxPath.length());
		System.out.println(command);
		//�� ��û�� ���� ó���ϱ�(�б��۾�)
		Controller controller=null;
		//MemberDAO dao= new MemberDAO();
		String nextView=null;
		HandlerMapping mappings=new HandlerMapping();
		controller=mappings.getController(command);
		nextView=controller.requestHandle(request, response);
		
//		//�ڵ鷯����(HandlerMapping)
//		//list.do-->MemberListcontroller
//		//insert.do-->MemberInsertcontroller
//		//insertForm.do-->MemberInsertFormcontroller
//		//delete.do-->MemberDeleteController
//		if(command.equals("/list.do")) {
//			//�������� �Ͻ��Ѽ� ����..
//			controller=new MemberListController();
//			 nextView=controller.requestHandle(request,response);
//			
//		}else if(command.equals("/insert.do")) { 
//			controller=new MemberInsertController();
//			nextView=controller.requestHandle(request,response);
//		}else if(command.equals("/insertForm.do")) {
//			//response.sendRedirect("member/member.html");
//			controller=new MemberInsertFormController();
//			nextView=controller.requestHandle(request, response);
//		}else if(command.equals("/delete.do")) {
//			controller=new MemberDeleteController();
//			nextView=controller.requestHandle(request, response);
//		}
		//view�������� �����ϴ� �κ�
		if(nextView!=null) {
			if(nextView.indexOf("redirect:")!=-1) {
				String[] sp=nextView.split(":");//sp[0]:sp[1]�������� �ڸ���
				response.sendRedirect(sp[1]);//:o
			}else {
				RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/"+nextView);
				rd.forward(request, response);
				
			}
		}
	}

}
