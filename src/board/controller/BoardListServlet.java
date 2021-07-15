package board.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.Utils;

/**
 * Servlet implementation class BoardSearchServlet
 */
@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int numPerPage = 12;
		int cPage = 1;
		
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
			
		}
		
		String lowPriceS = request.getParameter("lowPrice");
		String highPriceS = request.getParameter("highPrice");
		String[] categoryArr = request.getParameterValues("category");
		String[] locationArr = request.getParameterValues("location");
		String keywordType = request.getParameter("keywordType");
		String keyword = request.getParameter("keyword");
		int lowPrice = lowPriceS != null && !lowPriceS.isEmpty() ? Integer.parseInt(lowPriceS) : 0;
		int highPrice = highPriceS != null && !highPriceS.isEmpty() ? Integer.parseInt(highPriceS) : 0;
		String classDateS = request.getParameter("classDate");
		Date classDate = classDateS != null && !"".equals(classDateS) ? Date.valueOf(classDateS) : null;
//		System.out.println(Arrays.toString(categoryArr));
//		System.out.println(Arrays.toString(locationArr));
		Map <String, Object> param = new HashMap<>();
		param.put("categoryArr", categoryArr);
		param.put("locationArr", locationArr);
		param.put("keywordType", keywordType);
		param.put("keyword", keyword);
		param.put("lowPrice", lowPrice);
		param.put("highPrice", highPrice);
		param.put("classDate", classDate);
		
		Map<String, Object> map = new BoardService().getBoardList(numPerPage, cPage, param);
		List<Board> list = (List<Board>)map.get("list");
		int totalContents = (Integer)map.get("totalContents");
		System.out.println("list@servlet = " + list);
		System.out.println("totalContent@servlet = " + totalContents);
		
		
		String url = request.getRequestURI() + "?";
		
		if(categoryArr != null && categoryArr.length != 0) {
			for(int i = 0; i < categoryArr.length; i++){
				url += "category=" + categoryArr[i] + "&";
			}
		}
		if(locationArr != null && locationArr.length != 0) {
			for(int i = 0; i < locationArr.length; i++) {
				url += "location=" + locationArr[i] + "&";
			}
		}
		if(keyword != null && !"".equals(keyword)) {
			url += "&" + keywordType + "=" + keyword + "&";
		}
		if(lowPrice != 0) {
			url += "&lowPrice=" + lowPrice;
		}
		if(highPrice != 0) {
			url += "&highPrice=" + highPrice;
		}
		
		if(classDate != null) {
			url += "&classDate=" + classDate;
		}
//		System.out.println(url);
		String pageBar = Utils.getPageBarHTML(cPage, numPerPage, totalContents, url);
		
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
