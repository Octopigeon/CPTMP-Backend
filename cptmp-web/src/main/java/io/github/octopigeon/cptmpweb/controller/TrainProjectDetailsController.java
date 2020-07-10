package io.github.octopigeon.cptmpweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.math.BigInteger;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in anlow
 * @date 2020/7/10
 */
@RestController
public class TrainProjectDetailsController {

    @GetMapping("/train-project/{id}")
    public void doGetTrainProjectDetails(@PathVariable(value = "id") BigInteger trainProjectId) throws NoHandlerFoundException {

    }

}
