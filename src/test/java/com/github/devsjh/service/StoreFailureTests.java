package com.github.devsjh.service;

import com.github.devsjh.application.exception.InvalidJsonFormatException;
import com.github.devsjh.domain.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class StoreFailureTests {

    @InjectMocks private StatsService statsService;

    @Test
    @DisplayName(value = "잘못된 형식의 JSON 파일 등록")
    public void invalidJsonFormat() {
        // Given (JSON 파일을 등록했으나 Dto 객체로 변환할 수 없을 경우를 가정한다.)
        final String JSON = "";

        // When
        Throwable exception = assertThrows(InvalidJsonFormatException.class, () -> statsService.store(JSON));

        // Then
        System.out.println(exception.getMessage()); // Invalid JSON Format
        assertEquals("Invalid JSON Format", exception.getMessage());
    }
}