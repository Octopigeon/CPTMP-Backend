package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.PigeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PigeonController {

    @Autowired
    private PigeonService pigeonService;

    @GetMapping("/pigeon")
    public void pigeonHandler(@RequestParam(required = true, name = "cmd") String command,
                              @RequestParam(required = false, name = "arg") String arg) {
        pigeonService.managePigeon(command, arg);
    }

}
