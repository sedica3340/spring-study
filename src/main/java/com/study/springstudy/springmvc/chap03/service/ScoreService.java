package com.study.springstudy.springmvc.chap03.service;

/*
    컨트롤러와 레파지토리사이에 위치하여
    중간 처리를 담당

    - 트랜잭션 처리, 데이터 가공 처리...

    - 의존 관계
    Controller -> Service -> Repository
 */

import com.study.springstudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreListResponseDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreModifyRequestDto;
import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.mapper.ScoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScoreService {

//    private final ScoreRepository repository;
    private final ScoreMapper repository;
    // 목록 조회 중간처리
    // - DB에서 조회한 성적정보 목록은 민감한정보를 모두 포함하고 있는데
    //   이를 컨트롤러에게 직접 넘기면 보안상 불필요한 정보까지 화면으로
    //   넘어갈 수 있기 때문에 숨길건 숨기고 뺄건 빼는 데이터가공을 처리한다.
    public List<ScoreListResponseDto> getList(String sort) {
        List<Score> scoreList = repository.findAll(sort);

        return scoreList.stream()
                .map(ScoreListResponseDto::new)
                .collect(Collectors.toList());
    }

    // 저장 중간처리
    public boolean insert(ScorePostDto dto) {
        return repository.save(new Score(dto));
    }

    // 삭제 중간처리
    public boolean deleteScore(long stuNum) {
        return repository.remove(stuNum);
    }

    // 개별조회 중간처리
    public ScoreDetailResponseDto retrieve(long stuNum) {
        Score score = repository.findOne(stuNum);
        String rank = repository.ranking(score);
        return new ScoreDetailResponseDto(score, rank);
    }

    public boolean update(ScoreModifyRequestDto dto) {
         return repository.updateScore(new Score(dto));
    }
}
