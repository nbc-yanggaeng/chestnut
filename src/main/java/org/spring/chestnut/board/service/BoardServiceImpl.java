package org.spring.chestnut.board.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.spring.chestnut.board.dto.request.BoardDto;
import org.spring.chestnut.board.dto.request.BoardRequestDto;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.board.entity.CollaboratorEntity;
import org.spring.chestnut.board.repository.BoardRepository;
import org.spring.chestnut.board.repository.CollaboratorRepository;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final CollaboratorRepository collaboratorRepository;

    @Override
    public void getBoard(Long boardId, UserDetailsImpl userDetails) {

    }

    @Override
    @Transactional
    public void createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        String description = Optional.ofNullable(requestDto.getDescription()).orElse("");
        BoardDto boardDto = new BoardDto(requestDto.getTitle(), requestDto.getBackgroundColor(), description, memberId);
        BoardEntity board = boardRepository.save(boardDto);

        // Collaborator 에 Board 생성자 추가
        CollaboratorEntity collaborator = CollaboratorEntity.of(
            board.getId(),
            memberId
        );
        collaboratorRepository.save(collaborator);
    }

    @Override
    @Transactional
    public void updateBoard(Long boardId, BoardRequestDto requestDto, UserDetailsImpl userDetails) {

    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        BoardEntity board = boardRepository.findBoard(boardId);
        if(!Objects.equals(memberId, board.getCreateMemberId())) {
            throw new IllegalArgumentException("보드 생성 멤버가 아닙니다");
        }

        boardRepository.deleteById(boardId);
    }

    @Override
    public void inviteMember(Long boardId, Long memberId, UserDetailsImpl userDetails) {

    }
}
