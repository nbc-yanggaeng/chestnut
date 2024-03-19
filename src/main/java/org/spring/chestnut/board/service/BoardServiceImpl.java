package org.spring.chestnut.board.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.spring.chestnut.board.dto.BoardRequestDto;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.board.repository.BoardRepository;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void getBoard(Long boardId, UserDetailsImpl userDetails) {

    }

    @Override
    public void createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        String description = Optional.ofNullable(requestDto.getDescription()).orElse("");

        BoardEntity board = BoardEntity.of(
            requestDto.getTitle(),
            requestDto.getBackgroundColor(),
            description,
            memberId
        );

        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void updateBoard(Long boardId, BoardRequestDto requestDto, UserDetailsImpl userDetails) {

    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, UserDetailsImpl userDetails) {

    }

    @Override
    public void inviteMember(Long boardId, Long memberId, UserDetailsImpl userDetails) {

    }

    private BoardEntity findBoard(Long boardId) throws InvalidInputException {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new InvalidInputException("해당 보드를 찾을 수 없습니다."));
    }
}
