package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.constantclass.NoticeType;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.notice.NoticeDTO;
import io.github.octopigeon.cptmpservice.service.notice.NoticeService;
import io.github.octopigeon.cptmpservice.service.trainproject.TrainService;
import io.github.octopigeon.cptmpservice.service.userinfo.UserInfoService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 3.0
 * @date 2020/07/21
 * @last-check-in 陈若琳
 * @date 2020/07/21
 */

@RestController
public class NoticeDetailsController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TrainService trainService;

    /**
     * 发送签到信息
     * @param type
     * @param senderId
     * @param trainId
     * @return
     */
    @PostMapping("api/notice/signin")
    public RespBean sendSignInNotice(
            @RequestParam("type")String type,
            @RequestParam("sender_id")BigInteger senderId,
            @RequestParam("train_id")BigInteger  trainId)
    {
        try{
            List<BaseUserInfoDTO>userInfoDTOList = userInfoService.findByTrain(trainId);
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setSenderId(senderId);
            noticeDTO.setNoticeType(NoticeType.MESSAGE_NOTICE.name());
            noticeDTO.setContent(type+"签到:请及时进行"+trainService.findById(trainId).getName()+"实训的签到！");
            for (BaseUserInfoDTO user:userInfoDTOList)
            {
                noticeDTO.setReceiverId(user.getId());
                noticeService.add(noticeDTO);
            }
            return RespBean.ok("send message successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"send message failed");
        }
    }

    /**
     * 通过接收者id获取通知信息
     * @param receiverId
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/notice/receiver/{receiver_id}")
    public RespBeanWithNoticeList findByReceiverId(
            @PathVariable("receiver_id")BigInteger receiverId,
            @RequestParam("offset")int offset,
            @RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<NoticeDTO> pageInfo = noticeService.findByReceiverId(page, offset, receiverId);
            return new RespBeanWithNoticeList(pageInfo.getList(),pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithNoticeList(CptmpStatusCode.INFO_ACCESS_FAILED,"get notice failed");
        }
    }

    /**
     * 通过id获取通知信息
     * @param id
     * @return
     */
    @GetMapping("api/notice/{notice_id}")
    public RespBeanWithNotice findById(@PathVariable("notice_id")BigInteger id)
    {
        try{
            NoticeDTO noticeDTO = noticeService.findById(id);
            return new RespBeanWithNotice(noticeDTO);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithNotice(CptmpStatusCode.INFO_ACCESS_FAILED,"get notice failed");
        }
    }

    /**
     * 通过团队id获取通知信息
     * @param teamId
     * @param offset
     * @param page
     * @return
     */
    @GetMapping("api/notice/team/{team_id}")
    public RespBeanWithNoticeList findByTeamId(
            @PathVariable("team_id")BigInteger teamId,
            @RequestParam("offset")int offset,
            @RequestParam("page")int page)
    {
        try{
            Page pages = PageHelper.startPage(page, offset);
            PageInfo<NoticeDTO> pageInfo = noticeService.findByTeamId(page, offset, teamId);
            return new RespBeanWithNoticeList(pageInfo.getList(),pages.getTotal());
        }catch (Exception e)
        {
            e.printStackTrace();
            return new RespBeanWithNoticeList(CptmpStatusCode.INFO_ACCESS_FAILED,"get notice failed");
        }
    }

    /**
     * 创建消息提醒
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("api/notice")
    public RespBean createNotice(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        NoticeDTO noticeDTO = objectMapper.readValue(json,NoticeDTO.class);
        noticeDTO.setNoticeType(NoticeType.MESSAGE_NOTICE.name());
        try{
            noticeService.add(noticeDTO);
            return RespBean.ok("create notice successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.CREATE_FAILED,"create notice failed");
        }
    }

    /**
     * 根据id删除消息提示
     * @param noticeId
     * @return
     */
    @DeleteMapping("api/notice/{notice_id}")
    public RespBean deleteNotice(@PathVariable("notice_id")BigInteger noticeId)
    {
        try{
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setId(noticeId);
            noticeService.remove(noticeDTO);
            return RespBean.ok("remove notice successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.REMOVE_FAILED,"remove notice failed");
        }
    }

    /**
     * 更新消息提示
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping("api/notice")
    public RespBean updateNotice(@RequestBody String json) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        NoticeDTO noticeDTO = objectMapper.readValue(json,NoticeDTO.class);
        try{
            noticeService.modify(noticeDTO);
            return RespBean.ok("update notice successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            return RespBean.error(CptmpStatusCode.UPDATE_BASIC_INFO_FAILED,"update notice failed");
        }
    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithNotice extends RespBean
{
    public RespBeanWithNotice(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithNotice(NoticeDTO noticeDTO)
    {
        super();
        this.noticeDTO = noticeDTO;
    }

    @JsonProperty("data")
    private NoticeDTO noticeDTO;
}


@Data
@EqualsAndHashCode(callSuper = true)
class RespBeanWithNoticeList extends RespBean
{
    public RespBeanWithNoticeList(Integer status, String msg)
    {
        super(status,msg);
    }

    public RespBeanWithNoticeList(List<NoticeDTO> noticeList,long totalRows)
    {
        super();
        this.totalRows = totalRows;
        this.noticeList = noticeList;
    }

    @JsonProperty("total_rows")
    private long totalRows;
    @JsonProperty("data")
    List<NoticeDTO> noticeList;
}

