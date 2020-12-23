package com.github.devsjh.application.api;

import com.github.devsjh.application.exception.InvalidFileTypeException;
import com.github.devsjh.application.payload.ApiResponse;
import com.github.devsjh.domain.service.StatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Api(value = "Stats Controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;
    private static final String ACCEPT_TYPE = "application/json";

    @ApiOperation(value = "데이터 조회", notes = "날짜(2020-01-01)를 이용한 데이터 조회")
    @GetMapping("/daily")
    public ApiResponse read(@ApiParam(value = "날짜", required = true, example = "2020-01-01") @RequestParam String date) {
        return new ApiResponse(statsService.read(date));
    }

    @ApiOperation(value = "데이터 조회", notes = "날짜(2020-01-01)와 시각(21)을 이용한 데이터 조회")
    @GetMapping("/hourly")
    public ApiResponse read(@ApiParam(value = "날짜", required = true, example = "2020-01-01") @RequestParam String date,
                            @ApiParam(value = "시각", required = true, example = "21") @RequestParam int hour) {
        return new ApiResponse(statsService.read(date, hour));
    }

    @ApiOperation(value = "데이터 등록", notes = "날짜/시각을 기록한 JSON 파일 등록")
    @PostMapping("/")
    public ApiResponse store(@ApiParam(value = "파일", required = true, example = ".json")
                             @RequestParam("file") MultipartFile file) throws IOException {
        if (!ACCEPT_TYPE.equals(file.getContentType())) {
            throw new InvalidFileTypeException();
        }

        statsService.store(new String(file.getBytes()));

        return new ApiResponse("");
    }
}