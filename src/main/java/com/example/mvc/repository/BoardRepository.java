package com.example.mvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.mvc.domain.Board;
import com.example.mvc.parameter.BoardParameter;
import com.example.mvc.parameter.BoardSearchParameter;

@Repository
public interface BoardRepository {

	List<Board> getList(BoardSearchParameter parameter);
	
	Board get(int boardSeq);
	
	void save(BoardParameter board);
	void saveList(Map<String, Object> paramMap);
	
	void update(BoardParameter board);
	
	void delete(int boardSeq);
}
