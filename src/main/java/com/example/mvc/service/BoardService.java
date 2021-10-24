package com.example.mvc.service;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.framework.data.domain.PageRequestParameter;
import com.example.mvc.domain.Board;
import com.example.mvc.parameter.BoardParameter;
import com.example.mvc.parameter.BoardSearchParameter;
import com.example.mvc.repository.BoardRepository;

@Service
public class BoardService { //게시판 service

	@Autowired
	private BoardRepository repository;
	
	public List<Board> getList(PageRequestParameter<BoardSearchParameter> parameter) {
		return repository.getList(parameter);
	}
	
	public Board get(int boardSeq) {
		return repository.get(boardSeq);	
		}
	
	public void save(BoardParameter parameter) {
		//조회하여 리턴된 정보
		Board board = repository.get(parameter.getBoardSeq()); 
		if(board == null) {
			repository.save(parameter);
		} else {
			repository.update(parameter);
		}
	}
	
	public void saveList1(List<BoardParameter> list) {
		for(BoardParameter parameter : list) {
			repository.save(parameter); //커넥션풀은 하나씩 계속 만들어서 오래걸림
		}
		
	}
	
	public void saveList2(List<BoardParameter> boardList) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("boardList", boardList);
		repository.saveList(paraMap);
	}

	public void delete(int boardSeq) {
		repository.delete(boardSeq);
	}
}
