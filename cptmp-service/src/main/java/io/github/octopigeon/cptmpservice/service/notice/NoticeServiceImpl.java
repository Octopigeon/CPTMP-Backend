package io.github.octopigeon.cptmpservice.service.notice;

import com.github.pagehelper.PageInfo;
import io.github.octopigeon.cptmpdao.mapper.NoticeMapper;
import io.github.octopigeon.cptmpdao.model.Notice;
import io.github.octopigeon.cptmpservice.dto.notice.NoticeDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通知服务实现类
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/20
 * @last-check-in 李国豪
 * @date 2020/7/20
 */
@Service
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 根据发送者Id获取已发通知
     *
     * @param page     页号
     * @param offset   页容量
     * @param senderId 发送者Id
     * @return 通知分页列表
     */
    @Override
    public PageInfo<NoticeDTO> findBySenderId(int page, int offset, BigInteger senderId) {
        List<Notice> notices = noticeMapper.findNoticeBySenderId(senderId);
        return getNoticeDtoPageInfo(notices);
    }

    /**
     * 根据接收者去获取通知
     * @param page 页号
     * @param offset 页容量
     * @param receiverId 接受者Id
     * @return 通知分页列表
     */
    @Override
    public PageInfo<NoticeDTO> findByReceiverId(int page, int offset, BigInteger receiverId) {
        List<Notice> notices = noticeMapper.findNoticeByReceiverId(receiverId);
        return getNoticeDtoPageInfo(notices);
    }

    /**
     * 根据团队Id获取通知
     * @param page 页号
     * @param offset 页容量
     * @param teamId 团队Id
     * @return 通知分页列表
     */
    @Override
    public PageInfo<NoticeDTO> findByTeamId(int page, int offset, BigInteger teamId) {
        List<Notice> notices = noticeMapper.findNoticeByTeamId(teamId);
        return getNoticeDtoPageInfo(notices);
    }

    /**
     * 根据notice列表构造DTO的分页信息
     * @param notices Notice列表
     * @return 通知分页列表
     */
    @NotNull
    private PageInfo<NoticeDTO> getNoticeDtoPageInfo(List<Notice> notices) {
        List<NoticeDTO> results = new ArrayList<>();
        for (Notice notice: notices) {
            NoticeDTO result = new NoticeDTO();
            BeanUtils.copyProperties(notice, result);
            results.add(result);
        }
        return new PageInfo<>(results);
    }

    /**
     * 添加数据
     *
     * @param dto ：dto实体
     */
    @Override
    public void add(NoticeDTO dto) throws Exception {
        try{
            Notice notice = new Notice();
            BeanUtils.copyProperties(dto, notice);
            notice.setGmtCreate(new Date());
            //默认未读
            notice.setIsRead(false);
            noticeMapper.addNotice(notice);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Add Notice failed!");
        }

    }

    /**
     * 移除数据
     * 此处采用的软删除
     * @param dto ：dto实体
     */
    @Override
    public void remove(NoticeDTO dto) throws Exception {
        if(noticeMapper.findNoticeById(dto.getId()) != null){
            noticeMapper.hideNoticeById(dto.getId(), new Date());
        }else{
            throw new Exception("Notice is not exist!");
        }
    }

    /**
     * 通知的相关信息修改
     * @param dto 通知DTO
     * @return 是否修改成功
     */
    @Override
    public Boolean modify(NoticeDTO dto) throws Exception {
        try{
            Notice notice = noticeMapper.findNoticeById(dto.getId());
            if(notice == null){
                throw new ValueException("Notice is not exist!");
            }
            BeanUtils.copyProperties(dto, notice, Utils.getNullPropertyNames(dto));
            notice.setGmtModified(new Date());
            noticeMapper.updateNoticeById(notice);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 基础查询服务，每个表都需要支持通过id查询
     * @param id 查询Id
     * @return 通知DTO
     */
    @Override
    public NoticeDTO findById(BigInteger id) throws Exception {
        NoticeDTO noticeDTO = new NoticeDTO();
        Notice notice = noticeMapper.findNoticeById(id);
        if(notice == null){
            throw new Exception("Notice is not exist!");
        }
        BeanUtils.copyProperties(notice, noticeDTO);
        return noticeDTO;
    }
}
