package io.github.octopigeon.cptmpweb.controller;

import io.github.octopigeon.cptmpservice.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.FileDTO;
import io.github.octopigeon.cptmpservice.service.FileService;
import io.github.octopigeon.cptmpservice.service.ImageService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import io.github.octopigeon.cptmpweb.bean.response.RespBeanWithObj;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
    private FileService fileService;
    @Autowired
    private ImageService imageService;

    @PostMapping({"api/uploadFile/{userId}/{teamId}", "api/uploadFile/{userId}"})
    public RespBeanWithObj<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable BigInteger userId, @PathVariable(required = false) BigInteger teamId){
        try {
            RespBeanWithObj<FileDTO> fileResp = new RespBeanWithObj<>();
            FileDTO fileDTO = fileService.singleStoreFile(file, userId, teamId);
            fileResp.setObj(fileDTO);
            return fileResp;
        }catch (Exception e){
            RespBeanWithObj<Exception> resp = new RespBeanWithObj<>();
            resp.setObj(e);
            return resp;
        }

    }

    @PostMapping({"api/uploadMultiFiles/{userId}/{teamId}", "api/uploadMultiFiles/{userId}"})
    public List<? extends RespBeanWithObj<?>> uploadMultiFile(@RequestParam("files") MultipartFile[] files, @PathVariable BigInteger userId, @PathVariable(required = false) BigInteger teamId) {
        try {
            List<RespBeanWithObj<?>> fileResps = new ArrayList<>();
            for (MultipartFile file:files) {
                fileResps.add(uploadFile(file,userId,teamId));
            }
            return fileResps;
        }catch (Exception e){
            List<RespBeanWithObj<Exception>> resps= new ArrayList<>();
            RespBeanWithObj<Exception> resp = new RespBeanWithObj<>();
            resp.setObj(e);
            resps.add(resp);
            return resps;
        }
    }

    @GetMapping("storage/{year}/{month}/{day}/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, @PathVariable String year, @PathVariable String month, @PathVariable String day, HttpServletRequest request) {
        try {
            Resource resource = fileService.loadFile(fileName, year, month, day);
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

    //state是avatar或者face
    @PostMapping("api/upload/{state}/{username}")
    public RespBean uploadImg(@RequestParam("file") MultipartFile file, @PathVariable String state, @PathVariable String username){
        try {
            String url = imageService.storeImage(file, username, state);
            return RespBean.ok(url);
        } catch (Exception e) {
            return RespBean.error(CptmpStatusCode.AUTH_FAILED_UNKNOWN_ERROR, e.getMessage());
        }
    }

    @GetMapping("storage/{state}/{filename}")
    public ResponseEntity<?> downloadImg(@PathVariable String state, @PathVariable String filename, HttpServletRequest request) {
        try{
            Resource resource = imageService.uploadImage(filename, state);
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
