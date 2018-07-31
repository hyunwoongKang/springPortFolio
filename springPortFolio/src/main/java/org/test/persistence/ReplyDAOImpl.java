package org.test.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.test.domain.PageMaker;
import org.test.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	
	@Inject
	private SqlSession session;
	
	private static String namespace="org.test.mapper.ReplyMapper";
	//모든 댓글 가져오기
	@Override
	public List<ReplyVO> getAllReply(int bno,PageMaker pk) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("bno", bno);
		map.put("pk", pk);		
		
		return session.selectList(namespace+".getAllReply",map);
	}

	//모든 댓글 추가하기
	@Override
	public void addReply(ReplyVO reply) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".addReply",reply);
	}

	//댓글 삭제하기
	@Override
	public void removeReply(int rno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".removeReply",rno);
		
	}
	//댓글 수정하기
	@Override
	public void modifyReply(ReplyVO reply) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".updateReply",reply);
	}
    //해당 게시판 댓글 수 가져오기
	@Override
	public int getRnoCount(int bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".countRno",bno);
	}

}
