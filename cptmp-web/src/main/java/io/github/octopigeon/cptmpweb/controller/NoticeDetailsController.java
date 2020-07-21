package io.github.octopigeon.cptmpweb.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpservice.constantclass.CptmpStatusCode;
import io.github.octopigeon.cptmpservice.dto.cptmpuser.BaseUserInfoDTO;
import io.github.octopigeon.cptmpservice.dto.notice.NoticeDTO;
import io.github.octopigeon.cptmpservice.dto.team.TeamInfoDTO;
import io.github.octopigeon.cptmpservice.service.notice.NoticeService;
import io.github.octopigeon.cptmpweb.bean.response.RespBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 陈若琳
 * @version 3.0
 * @date 2020/07/21
 * @last-check-in 陈若琳
 * @date 2020/07/21
 */
public class NoticeDetailsController {

    @Autowired
    private NoticeService noticeService;

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
            return new RespBeanWithNoticeList(CptmpStatusCode.INFO_ACCESS_FAILED,"get notice failed");
        }
    }

    /**
     * 通过id获取通知信息
     * @param id
     * @return
     */
    @GetMapping("api/notice/team/{id}")
    public RespBeanWithNotice findById(@PathVariable("id")BigInteger id)
    {
        try{
            NoticeDTO noticeDTO = noticeService.findById(id);
            return new RespBeanWithNotice(noticeDTO);
        }catch (Exception e)
        {
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
            return new RespBeanWithNoticeList(CptmpStatusCode.INFO_ACCESS_FAILED,"get notice failed");
        }
    }

    public RespBean createNotice()
    {
        return null;
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

