package user.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.admin.model.service.AdminService;
import user.vo.User;


@WebServlet("/admin/member/list")
public class AdminMemeberList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminMemeberList() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<User> uList = new AdminService().selectAllUsers();
		
		request.setAttribute("uList", uList);
		request.getRequestDispatcher("/WEB-INF/views/member/admin/userList.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
