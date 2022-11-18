package com.example.back.controller;

import com.example.back.Domain.Dto.*;
import com.example.back.Domain.Dto.crosswalkk.childCross;
import com.example.back.Domain.Dto.crosswalkk.cross;
import com.example.back.Domain.Dto.crosswalkk.respCross;
import com.example.back.Domain.Dto.gps.childReq;
import com.example.back.Domain.Dto.gps.locInfo;
import com.example.back.Domain.Dto.gps.parReq;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class userController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public jsonResponse execSignup(@Validated @RequestBody signDto signDto) {
            jsonResponse jsonReponse = new jsonResponse();
            jsonReponse.setMsg("It is Check to communicate");
            log.info(signDto.getUserId()+"!!!!!!!!!!!!!!!!!!!!!");
            log.info(signDto.getPassword()+"!!!!!!!!!!!!!!!!!!!!!");
            userService.joinUser(signDto);
            return jsonReponse;
    }


    @PostMapping("/user/login")
    public ResponseDto login(@RequestBody LoginRequest loginRequest) {
        ResponseDto responseDto = userService.loginChk(loginRequest.getUserId(), loginRequest.getPassword());
        return responseDto;
        // 로그인 상태유지와 더불어서 token으로 이제 API를 사용할수가 있습니다.
    }

    @PostMapping("/user/child")
    public childrenDto req(@RequestBody childrenDto childrenDto) {
        childrenDto dto1 = userService.childrenRequest(childrenDto);
        return dto1;
    }

    @GetMapping("/user/login/cross")
    public respCross testLoc3(@RequestBody childCross childCross) {
        log.info("test2"+childCross.isIdx()+"!!!!!!!!!!!!!!!!!!!!!!!");
        if(childCross.isIdx()) throw new RuntimeException("It is allow to child");
        List<cross> crosses = userService.reqForCross();
        respCross respCross = new respCross();
        respCross.setCrosses(crosses);
        return respCross;
        // 넘어온 String형태의 위도 경도를 그대로 반환한다.
    }

    @PostMapping("/user/login/child")
    public void gpsLoc(@RequestBody childReq childReq) {
        log.info("test2"+childReq.getUserId()+"!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("test2"+childReq.getLatitude()+"!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("test2"+childReq.getLongitude()+"!!!!!!!!!!!!!!!!!!!!!!!");
        if(childReq.isIdx()) throw new RuntimeException("It is allow to child");
        userService.putCache(childReq);

    }

    @PostMapping("/user/login/parent")
    public locInfo gpsLoc2(@RequestBody parReq parReq) {
        log.info("test2"+parReq.getUserId()+"!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("test2"+parReq.getUserId()+"!!!!!!!!!!!!!!!!!!!!!!!");
        if(!parReq.isIdx()) throw new RuntimeException("It is allow to parent");
        locInfo locInfo = userService.getCache(parReq);
        return locInfo;
        // 넘어온 String형태의 위도 경도를 그대로 반환한다.
    }

}
