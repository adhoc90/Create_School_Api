package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
@RequestMapping("info")
@Tag(name = "API для получении информации о преложении")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/getPort")
    public String getPort() {
        return infoService.getPort();
    }
    @GetMapping("/calculate-stream")
    public ResponseEntity<Void> calculateStream(@RequestParam Integer limit) {
        infoService.getCalculateWithStream(limit);
        return ResponseEntity.ok().build();
    }
}