package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.service.basefileService.BaseFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @Qualifier("baseFileServiceImpl")
    @Autowired
    private BaseFileService baseFileService;

    @GetMapping("storage/{year}/{month}/{day}/{fileName:.+}")
    public ResponseEntity<?> downloadPublicFile(@PathVariable String fileName, @PathVariable String year, @PathVariable String month, @PathVariable String day, HttpServletRequest request) {
        try {
            Resource resource = baseFileService.loadPublicFile(fileName, year, month, day);
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                logger.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        }catch (Exception e){
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
    }

    @GetMapping("api/storage/{year}/{month}/{day}/{fileName:.+}")
    public ResponseEntity<?> downloadPrivateFile(@PathVariable String fileName, @PathVariable String year, @PathVariable String month, @PathVariable String day, HttpServletRequest request) {
        try {
            Resource resource = baseFileService.loadPrivateFile(fileName, year, month, day);
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                logger.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        }catch (Exception e){
            return (ResponseEntity<?>) ResponseEntity.notFound();
        }
    }
}
