package com.example.mvc.service;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.mvc.domain.Board;
import com.example.mvc.parameter.BoardParameter;
import com.example.mvc.parameter.BoardSearchParameter;
import com.example.mvc.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository repository;
	
	public List<Board> getList(BoardSearchParameter parameter) {
		return repository.getList(parameter);
	}
	
	public Board get(int boardSeq) {
		return repository.get(boardSeq);
	}
	
	public void save(BoardParameter parameter) {
		Board board = repository.get(parameter.getBoardSeq());
		if(board == null) {
			repository.save(parameter);
		}
		else {
			repository.update(parameter);
		}
	}
	
	public void saveList1(List<BoardParameter> list) {
		for(BoardParameter parameter : list) {
			repository.save(parameter);
		}
	}
	
	public void saveList2(List<BoardParameter> boardList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("boardList", boardList);
		repository.saveList(paramMap);
	}
	
	public void delete(int boardSeq) {
		repository.delete(boardSeq);
	}
}
