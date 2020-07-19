package io.github.octopigeon.cptmpservice.service.assignment;

import com.alibaba.fastjson.JSONObject;
import io.github.octopigeon.cptmpdao.mapper.AssignmentMapper;
import io.github.octopigeon.cptmpdao.model.Assignment;
import io.github.octopigeon.cptmpservice.config.FileProperties;
import io.github.octopigeon.cptmpservice.dto.assignment.AssignmentDTO;
import io.github.octopigeon.cptmpservice.dto.file.FileDTO;
import io.github.octopigeon.cptmpservice.service.attachmentfile.AttachmentFileService;
import io.github.octopigeon.cptmpservice.service.basefileservice.BaseFileServiceImpl;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/14
 * @last-check-in 李国豪
 * @date 2020/7/14
 */
@Deprecated
@Service
public class AssignmentServiceImpl extends BaseFileServiceImpl implements AssignmentService{

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private AttachmentFileService attachmentFileService;

    @Autowired
    public AssignmentServiceImpl(FileProperties fileProperties) throws Exception {
        super(fileProperties);
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(AssignmentDTO dto) throws Exception {
        Assignment assignment = new Assignment();
        BeanUtils.copyProperties(dto, assignment);
        assignment.setGmtCreate(new Date());
        assignment.setFile(true);
        assignmentMapper.addAssignment(assignment);
    }

    /**
     * 移除数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void remove(AssignmentDTO dto) throws Exception {
        assignmentMapper.hideAssignmentById(dto.getId(), new Date());
    }

    /**
     * 更新的文件实体
     *
     * @param dto
     * @return 是否删除成功
     */
    @Override
    public Boolean modify(AssignmentDTO dto) throws Exception {
        try{
            Assignment assignment = new Assignment();
            BeanUtils.copyProperties(dto, assignment, Utils.getNullPropertyNames(dto));
            assignmentMapper.updateAssignmentById(assignment);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
        return null;
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     *
     * @param id 查询
     * @return dto
     */
    @Override
    public AssignmentDTO findById(BigInteger id) throws Exception {
        Assignment assignment = assignmentMapper.findAssignmentById(id);
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        BeanUtils.copyProperties(assignment, assignmentDTO);
        return assignmentDTO;
    }

    /**
     * 给资源库上传文件
     *
     * @param file         文件
     * @param assignmentId 作业Id
     */
    @Override
    public void uploadDocument(MultipartFile file, BigInteger assignmentId) throws Exception {
        FileDTO fileInfo = storePrivateFile(file);
        Assignment assignment = assignmentMapper.findAssignmentById(assignmentId);
        if(assignment == null){
            throw new ValueException("assignment is not existed");
        }
        FileDTO commited = JSONObject.parseObject(assignment.getDocumentPath(), FileDTO.class);
        if(commited != null){
            attachmentFileService.remove(commited);
            removeFile(commited.getFilePath());
        }

        JSONObject object = new JSONObject();
        object.put("file", fileInfo);
        attachmentFileService.add(fileInfo);
        assignmentMapper.updateAssignmentDocumentById(assignmentId, new Date(), object.toString());
    }

    /**
     * 删除资源库中文件
     *
     * @param assignmentId 作业Id
     * @param file         fileDTO
     */
    @Override
    public void removeDocument(BigInteger assignmentId, FileDTO file) throws Exception {
        Assignment assignment = assignmentMapper.findAssignmentById(assignmentId);
        if(assignment == null){
            throw new ValueException("assignment is not existed");
        }
        FileDTO commited = JSONObject.parseObject(assignment.getDocumentPath(), FileDTO.class);
        attachmentFileService.remove(commited);
        removeFile(commited.getFilePath());

        assignmentMapper.updateAssignmentDocumentById(assignmentId, new Date(), null);
    }
}
