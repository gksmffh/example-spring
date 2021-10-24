package com.example.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.configuration.exception.BaseException;
import com.example.configuration.http.BaseResponse;
import com.example.configuration.http.BaseResponseCode;
import com.example.framework.data.domain.MySQLPageRequest;
import com.example.framework.data.domain.PageRequestParameter;
import com.example.framework.web.bind.annotation.RequestConfig;
import com.example.mvc.domain.Board;
import com.example.mvc.domain.BoardType;
import com.example.mvc.domain.MenuType;
import com.example.mvc.parameter.BoardParameter;
import com.example.mvc.parameter.BoardSearchParameter;
import com.example.mvc.service.BoardService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
public class BoardController { //게시판 컨트롤러
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired //스프링 컨테이너에서 관리하는 빈을 주입
	private BoardService service;
	
	@GetMapping("{menuType}")
	public String list(
			@PathVariable MenuType menuType,
			BoardSearchParameter parameter,
			MySQLPageRequest pageRequest,  Model model) {
		logger.info("pageRequest : {}", pageRequest);
		parameter.setBoardTypes( new BoardType[] { menuType.getboardType() });
		logger.info("parameter : {}", parameter);
		PageRequestParameter<BoardSearchParameter> pageRequestParameter = new PageRequestParameter<BoardSearchParameter>(pageRequest, parameter);
		List<Board> boardList = service.getList(pageRequestParameter);
		model.addAttribute("boardList", boardList);
		model.addAttribute("menuType",menuType);
		return "/board/list";
	}
	
	@GetMapping("/{menuType}/{boardSeq}") //상세페이지
	public String get(@PathVariable MenuType menuType,  @PathVariable int boardSeq, Model model) {
		Board board = service.get(boardSeq);
		if (board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
		}

		model.addAttribute("board",board);
		model.addAttribute("menuType",menuType);
		return "/board/detail";
		}
	
	
	@GetMapping("{menuType}/form") //등록화면
	@RequestConfig(loginCheck = false)
	public String form(@PathVariable MenuType menuType, BoardParameter parameter, Model model) {
		model.addAttribute("parameter", parameter);
		model.addAttribute("menuType",menuType);
		return "/board/form";
	}
	
	@GetMapping("{menuType}/edit/{boardSeq}") //수정화면
	@RequestConfig(loginCheck = false)
	public String edit(@PathVariable MenuType menuType, @PathVariable(required = true) int boardSeq, BoardParameter parameter, Model model) {
		Board board = service.get(boardSeq);
		logger.info("{}",parameter);
		if(board==null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] { "게시물" });
			}
		model.addAttribute("board", board);
		model.addAttribute("menuType",menuType);
		model.addAttribute("parameter", parameter);
		return "/board/form";
	}
	
	@PostMapping("{menuType}/save") //등록,수정처리
	@ResponseBody
	@RequestConfig(loginCheck = false)
	@ApiOperation(value = "등록 / 수정처리", notes = "신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"),
		@ApiImplicitParam(name = "title", value = "제목", example = "spring"),
		@ApiImplicitParam(name = "contents", value = "내용", example = "spring 강좌")
	})
	public BaseResponse<Integer> save(@PathVariable MenuType menuType, BoardParameter parameter) {
		if(StringUtils.isEmpty(parameter.getTitle())) {// 제목 null 체크
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] { "title", "제목" });
		}
		if(StringUtils.isEmpty(parameter.getContents())) {// 제목 null 체크
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED, new String[] { "contents", "내용" });
		}
		parameter.setBoardType(menuType.getboardType());
		service.save(parameter);
		return new BaseResponse<Integer>(parameter.getBoardSeq());
	}
	
	@PutMapping("/saveList1") //대용량 등록
	@ResponseBody
	@ApiOperation(value = "대용량 등록처리1", notes = "대용량 등록처리1")
	public BaseResponse<Boolean> saveList1() {
		int count = 0;
		List<BoardParameter> list = new ArrayList<BoardParameter>();
		while(true) {
			count++;
			String title = RandomStringUtils.randomAlphabetic(10);
			String contents = RandomStringUtils.randomAlphabetic(10);
			list.add(new BoardParameter(title, contents));
			if(count >= 10000) {
				break;
			}	
		}
		long start = System.currentTimeMillis();
		service.saveList1(list);
		long end = System.currentTimeMillis();
		logger.info("실행 시간: {}", (end-start)/ 1000.0);
		return new BaseResponse<Boolean>(true);
		
	}
	
	@PutMapping("/saveList2") //대용량 등록
	@ResponseBody
	@ApiOperation(value = "대용량 등록처리2", notes = "대용량 등록처리2")
	public BaseResponse<Boolean> saveList2() {
		int count = 0;
		List<BoardParameter> list = new ArrayList<BoardParameter>();
		while(true) {
			count++;
			String title = RandomStringUtils.randomAlphabetic(10);
			String contents = RandomStringUtils.randomAlphabetic(10);
			list.add(new BoardParameter(title, contents));
			if(count >= 10000) {
				break;
			}	
		}
		long start = System.currentTimeMillis();
		service.saveList2(list);
		long end = System.currentTimeMillis();
		logger.info("실행 시간: {}", (end-start)/ 1000.0);
		return new BaseResponse<Boolean>(true);
		
	}
	
	@DeleteMapping("/delete/{boardSeq}")
	@ResponseBody
	@ApiOperation(value = "삭제처리", notes = "게시물 번호에 해당하는 정보를 삭제합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "boardSeq", value = "게시물 번호", example = "1"),
	})
	public BaseResponse<Boolean> delete(@PathVariable int boardSeq) {
		Board board = service.get(boardSeq);
		if(board == null) {
			return new BaseResponse<Boolean>(false);
		}
		service.delete(boardSeq);
			return new BaseResponse<Boolean>(true);
	}
}
