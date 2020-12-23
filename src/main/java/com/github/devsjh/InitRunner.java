package com.github.devsjh;

import com.github.devsjh.domain.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class InitRunner implements ApplicationRunner {

    private final StatsService statsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource resource = new ClassPathResource("2020-01-01-init.json");
        byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());

        statsService.store(new String(data, StandardCharsets.UTF_8));
    }
}