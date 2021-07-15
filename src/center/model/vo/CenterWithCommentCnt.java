package center.model.vo;

import java.io.Serializable;
import java.sql.Date;

public class CenterWithCommentCnt extends Center implements Serializable{

	
	private int centerCommentCnt;

	public CenterWithCommentCnt() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CenterWithCommentCnt(int centerCommentCnt) {
		super();
		this.centerCommentCnt = centerCommentCnt;
	}

	public int getCenterCommentCnt() {
		return centerCommentCnt;
	}

	public void setCenterCommentCnt(int centerCommentCnt) {
		this.centerCommentCnt = centerCommentCnt;
	}

	@Override
	public String toString() {
		return "CenterWithCommentCnt [centerCommentCnt=" + centerCommentCnt + "]";
	}


	
	
}
