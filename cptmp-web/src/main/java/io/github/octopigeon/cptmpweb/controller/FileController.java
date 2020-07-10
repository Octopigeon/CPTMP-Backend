package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.service.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/10
 * @last-check-in Gh Li
 * @date 2020/7/10
 */
@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileTransferService fileTransferService;

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file) throws Exception {
        //String fileName = fileTransferService.singleStoreFile(file);
        return null;
    }
}
