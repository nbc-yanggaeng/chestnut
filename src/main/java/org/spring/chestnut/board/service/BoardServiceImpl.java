package org.spring.chestnut.board.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.chestnut.board.dto.request.BoardDto;
import org.spring.chestnut.board.dto.request.BoardRequestDto;
import org.spring.chestnut.board.dto.response.BoardResponse;
import org.spring.chestnut.board.entity.BoardEntity;
import org.spring.chestnut.board.entity.CollaboratorEntity;
import org.spring.chestnut.board.repository.BoardRepository;
import org.spring.chestnut.board.repository.CollaboratorRepository;
import org.spring.chestnut.global.security.UserDetailsImpl;
import org.spring.chestnut.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final MemberRepository memberRepository;

    @Override
    public BoardResponse getBoard(Long boardId, UserDetailsImpl userDetails) {
        // 협력자인지 확인
        if (!collaboratorRepository.existsByMemberIdAndBoardId(userDetails.getMemberId(), boardId)) {
            throw new IllegalArgumentException("협력자가 아닌 멤버입니다");
        }
        return boardRepository.findAllByBoardId(boardId);
    }

    @Override
    @Transactional
    public void createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Long memberId = userDetails.getMemberId();
        String description = Optional.ofNullable(requestDto.getDescription()).orElse("");
        BoardDto boardDto = new BoardDto(requestDto.getTitle(), requestDto.getBackgroundColor(),
            description, memberId);
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
        // 로그인 멤버가 보드 생성 멤버인지 확인
        Long memberId = userDetails.getMemberId();
        validateCreateBoardMember(boardId, memberId);
        BoardDto boardDto = new BoardDto(requestDto.getTitle(), requestDto.getBackgroundColor(),
            requestDto.getDescription(), memberId);

        boardRepository.update(boardDto, boardId);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, UserDetailsImpl userDetails) {
        // 로그인 멤버가 보드 생성 멤버인지 확인
        Long memberId = userDetails.getMemberId();
        validateCreateBoardMember(boardId, memberId);

        boardRepository.deleteById(boardId);
    }

    @Override
    @Transactional
    public void inviteMember(Long boardId, Long memberId, UserDetailsImpl userDetails) {
        // 보드 생성자가 초대하는지 확인
        BoardEntity board = boardRepository.findById(boardId);
        if (!Objects.equals(userDetails.getMemberId(), board.getCreateMemberId())) {
            throw new IllegalArgumentException("보드 생성 멤버가 아닙니다");
        }

        // 존재하는 멤버인지 확인
        memberRepository.findById(memberId).orElseThrow(
            () -> new IllegalArgumentException("초대하려는 유저가 존재하지 않습니다.")
        );

        // 이미 협력자인지 확인
        if (collaboratorRepository.existsByMemberIdAndBoardId(memberId, boardId)) {
            throw new IllegalArgumentException("이미 협력자인 멤버입니다");
        }

        // 콜라보레이터 추가
        CollaboratorEntity collaborator = CollaboratorEntity.of(
            boardId,
            memberId
        );
        collaboratorRepository.save(collaborator);
    }

    private void validateCreateBoardMember(Long boardId, Long memberId) {
        BoardEntity board = boardRepository.findById(boardId);
        if (!Objects.equals(memberId, board.getCreateMemberId())) {
            throw new IllegalArgumentException("보드 생성 멤버가 아닙니다");
        }
    }
}
