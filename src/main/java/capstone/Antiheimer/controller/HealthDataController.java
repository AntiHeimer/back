package capstone.Antiheimer.controller;

import capstone.Antiheimer.dto.*;
import capstone.Antiheimer.exception.DuplicateHealthDataException;
import capstone.Antiheimer.exception.NotExistException;
import capstone.Antiheimer.service.HealthDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HealthDataController {

    private final HealthDataService healthDataService;

    @PostMapping("/save/active")
    public NormalResDto saveActive(@RequestBody SaveActiveReqDto request) {

        try {
            log.info("Active 데이터 저장 시작");
            healthDataService.insertActive(request);

            return new NormalResDto("200", "활동 데이터 저장 완료");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 활동 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    @PostMapping("/save/move")
    public NormalResDto saveMove(@RequestBody SaveMoveReqDto request) {

        try {
            log.info("Move 데이터 저장 시작");
            healthDataService.insertMove(request);

            return new NormalResDto("200", "움직인 거리 데이터 저장 완료");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 움직인 거리 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    @PostMapping("/save/walk")
    public NormalResDto saveWalk(@RequestBody SaveWalkReqDto request) {

        try {
            log.info("Walk 데이터 저장 시작");
            healthDataService.insertWalk(request);

            return new NormalResDto("200", "걸음수 데이터 저장 완료");
        } catch (DuplicateHealthDataException e) {

            return new NormalResDto("407", "중복된 걸음수 데이터");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

    @PostMapping("/save/weight")
    private NormalResDto saveWeight(@RequestBody SaveWeightReqDto request) {

        try {
            log.info("Weight 저장 시작");
            healthDataService.insertWeight(request);

            return new NormalResDto("200", "몸무게 저장 완료");
        } catch (NotExistException e) {

            return new NormalResDto("408", "존재하지 않는 회원");
        }
    }

//
//    @GetMapping("/find/active/{uuid}/{date}")
//    public FindActiveResDto findActive(@PathVariable("uuid") String uuid,
//                                       @PathVariable("date") LocalDate date) {
//
//        try {
//            log.info("Active 데이터 조회 시작");
//
//            List<Active> activeList = healthDataService.findActiveList(uuid, date);
//
//            return new FindActiveResDto("200", "걸음수 데이터 조회 완료", activeList);
//        } catch (NotExistException e) {
//
//            return new FindActiveResDto("408", "존재하지 않는 회원", null);
//        } catch (NotExistHealthDataException e) {
//
//            return new FindActiveResDto("408", "존재하지 않는 걸음수 데이터", null);
//        }
//    }
//
//    @GetMapping("/find/move/{uuid}/{date}")
//    public FindMoveResDto findMove(@PathVariable("uuid") String uuid,
//                                   @PathVariable("date") LocalDate date) {
//
//        try {
//            log.info("Move 데이터 조회 시작");
//
//            List<Move> moveList = healthDataService.findMoveList(uuid, date);
//
//            return new FindMoveResDto("200", "움직인 거리 데이터 조회 완료", moveList);
//        } catch (NotExistException e) {
//
//            return new FindMoveResDto("408", "존재하지 않는 회원", null);
//        } catch (NotExistHealthDataException e) {
//
//            return new FindMoveResDto("408", "존재하지 않는 움직인 거리 데이터", null);
//        }
//    }
//
//    @GetMapping("/find/walk/{uuid}/{date}")
//    public FindWalkResDto findWalk(@PathVariable("uuid") String uuid,
//                                   @PathVariable("date") LocalDate date) {
//
//        try {
//            log.info("Walk 데이터 조회 시작");
//
//            List<Walk> walkList = healthDataService.findWalkList(uuid, date);
//
//            return new FindWalkResDto("200", "걸음수 데이터 조회 완료", walkList);
//        } catch (NotExistException e) {
//
//            return new FindWalkResDto("408", "존재하지 않는 회원", null);
//        } catch (NotExistHealthDataException e) {
//
//            return new FindWalkResDto("408", "존재하지 않는 걸음수 데이터", null);
//        }
//    }
}
