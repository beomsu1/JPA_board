package org.bs.jpa.controller.rest;

import java.util.List;

import org.bs.jpa.dto.board.BoardCreateDTO;
import org.bs.jpa.dto.board.BoardDTO;
import org.bs.jpa.dto.board.BoardListDTO;
import org.bs.jpa.dto.board.BoardUpdateDTO;
import org.bs.jpa.service.BoardService;
import org.bs.jpa.util.FileuploadUtil;
import org.bs.jpa.util.Page.PageRequestDTO;
import org.bs.jpa.util.Page.PageResponseDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/board/")
public class BoardRestController {

    private final BoardService boardService;

    private final FileuploadUtil fileuploadUtil;

    // List
    @GetMapping("list")
    public PageResponseDTO<BoardListDTO> getBoardList(PageRequestDTO pageRequestDTO) {

        log.info("GET | Board List Controller");

        return boardService.boardList(pageRequestDTO);

    }

    // Post Create
    @PostMapping("")
    public String postBoardCreate(BoardCreateDTO boardCreateDTO) {

        log.info("POST | Board Create Controller");

        List<String> files = fileuploadUtil.uploadFiles(boardCreateDTO.getMultipartFiles());

        boardCreateDTO.setFiles(files);

        boardService.boardCreate(boardCreateDTO);

        return "게시글 생성 완료";

    }

    // Get Read
    @GetMapping("{bno}")
    public BoardDTO getBoardRead(@PathVariable("bno") Long bno) {

        log.info("GET | Board Read Controller");

        return boardService.boardReadOne(bno);

    }

    // Post Update
    @PostMapping("{bno}")
    public String postBoardUpdate(BoardUpdateDTO boardUpdateDTO) {

        log.info("POST | Board Update Controller");

        // 새로운 업로드 파일
        List<String> uploadFile = fileuploadUtil.uploadFiles(boardUpdateDTO.getMultipartFiles());

        // 기존 파일 이름 목록
        List<String> existingFileNames = boardUpdateDTO.getFiles();

        // 기존 파일 이름을 업로드 파일에 추가
        uploadFile.addAll(existingFileNames);

        boardUpdateDTO.setFiles(uploadFile);

        boardService.boardUpdate(boardUpdateDTO);

        return "게시글 수정 완료";

    }

    // Delete Delete
    @DeleteMapping("{bno}")
    public String postBoardDelete(@PathVariable("bno") Long bno) {

        log.info("POST | Board Delete Controller");

        boardService.boardDelete(bno);

        return "게시글 삭제 완료";

    }

}
