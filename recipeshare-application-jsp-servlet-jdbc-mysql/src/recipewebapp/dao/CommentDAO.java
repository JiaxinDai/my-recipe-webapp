package recipewebapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.edumis.admin.comment.vo.CommentVO;
import kr.co.edumis.user.member.vo.MemberVO;

@Repository
public class CommentDAO {
	
	@Autowired
	private SqlSessionTemplate session;
	
	
	//학생...
	public List<MemberVO> selectList() throws Exception {
		List<MemberVO> list = session.selectList("kr.co.edumis.admin.comment.dao.commentList");
		return list;
	}
	//코멘트목록...
	public List<CommentVO> selectcList() throws Exception {
		List<CommentVO> list =session.selectList("kr.co.edumis.admin.comment.dao.evaluationList");
				return list;
	}

	public MemberVO commentSelect(String id)throws Exception {
		System.out.println(id);
		return (MemberVO)session.selectOne("kr.co.edumis.admin.comment.dao.commentSelect", id);
	}

	public void commentInsert(CommentVO comment) throws Exception{
		session.insert("kr.co.edumis.admin.comment.dao.commentInsert",comment);
		session.commit();
	}

/*
 
	public void memberDrop(Map<String, String> drop) throws Exception{
		session.update("kr.co.edumis.admin.comment.dao.memberDrop",drop);
		session.commit();	
	}
	public void commentModify(CommentVO comment) throws Exception{
		session.update("kr.co.edumis.admin.comment.dao.commentModify",comment);
		session.commit();	
	}
 */

	public void deleteCom(String no) {
		session.delete("kr.co.edumis.admin.comment.dao.deleteCom", no);
		session.commit();
	}

	public CommentVO selectLast() {
		return session.selectOne("kr.co.edumis.admin.comment.dao.selectLastComment");
	}
	


	


}