package io.github.octopigeon.cptmpservice.service.notice;

import io.github.octopigeon.cptmpdao.mapper.*;
import io.github.octopigeon.cptmpdao.mapper.relation.ProcessEventMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.ProjectTrainMapper;
import io.github.octopigeon.cptmpdao.mapper.relation.TeamPersonMapper;
import io.github.octopigeon.cptmpdao.model.*;
import io.github.octopigeon.cptmpdao.model.Process;
import io.github.octopigeon.cptmpdao.model.relation.ProcessEvent;
import io.github.octopigeon.cptmpdao.model.relation.ProjectTrain;
import io.github.octopigeon.cptmpdao.model.relation.TeamPerson;
import io.github.octopigeon.cptmpservice.constantclass.NoticeType;
import io.github.octopigeon.cptmpservice.constantclass.RoleEnum;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 定时任务，定时添加通知
 * @author 李国豪
 * @version 1.0
 * @date 2020/7/21
 * @last-check-in 李国豪
 * @date 2020/7/21
 */
@Component
public class ScheduleNotice {

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private TrainMapper trainMapper;

    @Resource
    private ProcessMapper processMapper;

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private ProcessEventMapper processEventMapper;

    @Resource
    private ProjectTrainMapper projectTrainMapper;

    @Resource
    private TeamPersonMapper teamPersonMapper;

    @Resource
    private CptmpUserMapper cptmpUserMapper;

    @Resource
    private RecordMapper recordMapper;

    /**
     * 每天的0点更新deadline提醒
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void addDeadlineNotice(){
        //对每个在时间中的train进行遍历
        List<Train> trains = trainMapper.findValidTrains();
        for (Train train: trains) {
            //查询train下所有的学生用户
            List<CptmpUser> users = findStudentsByTrain(train.getId());
            //查找train下的每个当前时间的process
            List<Process> processes = processMapper.findProcessesByTrainIdAndTime(train.getId());
            for (Process process: processes) {
                List<ProcessEvent> processEvents = processEventMapper.findProcessEventsByProcessId(process.getId());
                //对process中每个当前时间范围的event进行处理
                for (ProcessEvent processEvent: processEvents) {
                    Event event = eventMapper.findEventById(processEvent.getEventId());
                    //判读当前时间是否在此event的时间区间内
                    if(new Date().after(event.getStartTime()) && new Date().before(event.getEndTime())){
                        //对团队中用户进行notice添加处理
                        for ( CptmpUser user: users) {
                            Notice notice = new Notice();
                            notice.setGmtCreate(new Date());
                            notice.setNoticeType(NoticeType.DEADLINE_NOTICE.name());
                            notice.setReceiverId(user.getId());
                            notice.setContent(generateDeadlineNotice(train.getName(), event.getContent(), event.getEndTime()));
                            noticeMapper.addNotice(notice);
                        }
                    }
                }
            }
        }
    }

    /**
     * 每隔1min检测一次作业，签到情况
     * 由于无法检测出event是签到或作业提交，所以采用record表中完全无记录的方法进行检测，可能会存在一定bug，如：用户都没有进行签到或者都没有进行作业提交
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void addWarningNotice(){
        //先查找每个进行中的train
        List<Train> trains = trainMapper.findValidTrains();
        for (Train train: trains) {
            //查询学生用户的teamPerson
            List<TeamPerson> teamStudents = findStudentTeamPeopleByTrain(train.getId());
            //查找train下的每个当前时间的process
            List<Process> processes = processMapper.findProcessesByTrainIdAndTime(train.getId());
            for (Process process: processes) {
                List<ProcessEvent> processEvents = processEventMapper.findProcessEventsByProcessId(process.getId());
                //对process中每个的event进行处理
                for (ProcessEvent processEvent: processEvents) {
                    Event event = eventMapper.findEventById(processEvent.getEventId());
                    //判读当前时间是否在此event的时间区间外两分钟之内 且在record表中出现过
                    Calendar c = Calendar.getInstance();
                    c.setTime(event.getEndTime());
                    c.add(Calendar.MINUTE, 2);
                    if(new Date().after(event.getEndTime()) && new Date().before(c.getTime()) && recordMapper.findRecordByProcessEventId(processEvent.getId()) != null){
                        for (TeamPerson teamPerson: teamStudents) {
                            //如果是团队任务
                            if(!event.getPersonOrTeam()){
                                //如果没有查询到完成记录，即未完成，添加警告记录
                                if(recordMapper.findRecordByTeamIdAndProcessEventId(teamPerson.getTeamId(), processEvent.getId()) == null){
                                    Team team = teamMapper.findTeamByTeamId(teamPerson.getTeamId());
                                    Notice notice = new Notice();
                                    notice.setGmtCreate(new Date());
                                    notice.setNoticeType(NoticeType.WARNING_NOTICE.name());
                                    notice.setReceiverId(teamPerson.getUserId());
                                    notice.setTeamId(teamPerson.getTeamId());
                                    notice.setContent(generateTeamWarningNotice(team.getName(), event.getContent()));
                                    // 如果不存在此项notice就进行添加
                                    if(noticeMapper.findNoticeByNotice(notice) == null){
                                        noticeMapper.addNotice(notice);
                                    }
                                }
                            }else {
                                //如果没有查询到完成记录，即未完成，添加警告记录
                                if(recordMapper.findRecordByUserIdAndProcessEventId(teamPerson.getUserId(), processEvent.getId()) == null){
                                    CptmpUser user = cptmpUserMapper.findUserById(teamPerson.getUserId());
                                    Notice notice = new Notice();
                                    notice.setGmtCreate(new Date());
                                    notice.setNoticeType(NoticeType.WARNING_NOTICE.name());
                                    notice.setReceiverId(teamPerson.getUserId());
                                    notice.setTeamId(teamPerson.getTeamId());
                                    notice.setContent(generateUserWarningNotice(user.getUsername(), event.getContent()));
                                    // 如果不存在此项notice就进行添加
                                    if(noticeMapper.findNoticeByNotice(notice) == null){
                                        noticeMapper.addNotice(notice);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 每天自动检测notice表，移除已读且超过30天的过期notice
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredNotice(){
        noticeMapper.removeExpiredNotices();
    }

    /**
     * 查询学生用户的teamPerson
     * @param trainId 实训Id
     * @return 学生用户的teamPerson列表
     */
    private List<TeamPerson> findStudentTeamPeopleByTrain(BigInteger trainId){
        List<TeamPerson> results = new ArrayList<>();
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(trainId);
        for (ProjectTrain projectTrain: projectTrains) {
            //查询到一个实训项目下所有的队伍
            List<Team> teams =  teamMapper.findTeamsByProjectTrainId(projectTrain.getId());
            for (Team team: teams) {
                List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(team.getId());
                for (TeamPerson teamPerson: teamPeople) {
                    CptmpUser user = cptmpUserMapper.findUserById(teamPerson.getUserId());
                    //如果此人是学生就加入最终学生列表
                    if(RoleEnum.ROLE_STUDENT_MEMBER.name().equals(user.getRoleName())){
                        results.add(teamPerson);
                    }
                }
            }
        }
        return results;
    }

    /**
     * 找到一个trainId下的所有学生用户
     * @param trainId 实训Id
     * @return 学生用户列表
     */
    private List<CptmpUser> findStudentsByTrain(BigInteger trainId){
        List<CptmpUser> users = new ArrayList<>();
        List<ProjectTrain> projectTrains = projectTrainMapper.findProjectTrainsByTrainId(trainId);
        for (ProjectTrain projectTrain: projectTrains) {
            //查询到一个实训项目下所有的队伍
            List<Team> teams =  teamMapper.findTeamsByProjectTrainId(projectTrain.getId());
            for (Team team: teams) {
                List<TeamPerson> teamPeople = teamPersonMapper.findTeamPersonByTeamId(team.getId());
                for (TeamPerson teamPerson: teamPeople) {
                    CptmpUser user = cptmpUserMapper.findUserById(teamPerson.getUserId());
                    //如果此人是学生就加入最终学生列表
                    if(RoleEnum.ROLE_STUDENT_MEMBER.name().equals(user.getRoleName())){
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    /**
     * deadline提醒内容
     * @param content 事务内容
     * @param deadline 最后期限
     * @return deadline消息内容
     */
    private String generateDeadlineNotice(String trainName, String content, Date deadline){
        return String.format("提醒：在 %s 中，您有一个 %s 待完成，最后期限为：%s",trainName, content, deadline.toString());
    }

    /**
     * 警告消息提醒内容
     * @param username 用户名
     * @param content 事务内容
     * @return 个人警告消息内容
     */
    private String generateUserWarningNotice(String username, String content){
        return String.format("警告：用户 %s 未正常完成 %s", username, content);
    }

    /**
     * 警告消息提醒内容
     * @param teamName 用户名
     * @param content 事务内容
     * @return 团队警告消息内容
     */
    private String generateTeamWarningNotice(String teamName, String content){
        return String.format("警告：团队 %s 未正常完成 %s", teamName, content);
    }
}
