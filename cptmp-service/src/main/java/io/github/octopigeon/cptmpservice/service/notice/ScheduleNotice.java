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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/21
 * @last-check-in Gh Li
 * @date 2020/7/21
 */
@Component
public class ScheduleNotice {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private ProcessEventMapper processEventMapper;

    @Autowired
    private ProjectTrainMapper projectTrainMapper;

    @Autowired
    private TeamPersonMapper teamPersonMapper;

    @Autowired
    private CptmpUserMapper cptmpUserMapper;

    @Autowired
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
                            //如果没有完成记录，进行notice插入
                            if(recordMapper.findRecordByUserIdAndProcessEventId(user.getId(), processEvent.getId()) == null){
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
    }

    /**
     * 每隔30秒检测一次作业，签到情况
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 30000)
    public void addWarningNotice(){

    }

    /**
     * 每天自动检测notice表，移除已读且超过30天的过期notice
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredNotice(){
        noticeMapper.removeExpiredNotices();
    }

    /**
     * 找到一个trainId下的所有学生用户
     * @param trainId
     * @return
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
     * @return
     */
    private String generateDeadlineNotice(String trainName, String content, Date deadline){
        return trainName +"中，您有一个 "+content+" 待完成，最后期限为："+deadline;
    }

    /**
     * 警告消息提醒内容
     * @param username 用户名
     * @param content 事务内容
     * @return
     */
    private String generateWarningNotice(String username, String content){
        return "用户 "+username+" 未正常完成 "+content;
    }
}
