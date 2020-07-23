package io.github.octopigeon.cptmpservice.service.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.octopigeon.cptmpdao.mapper.TeamMapper;
import io.github.octopigeon.cptmpdao.model.Team;
import io.github.octopigeon.cptmpservice.dto.github.ContributorDTO;
import io.github.octopigeon.cptmpservice.utils.Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author 魏啸冲
 * @version 1.0
 * @date 2020/7/17
 * 调用GitHub api得到小组成员统级数据
 * @last-check-in 魏啸冲
 * @date 2020/7/17
 */
@Service
public class ContributorStaticsService {

    @Autowired
    private TeamMapper teamMapper;

    /**
     * 获取贡献者的相关信息
     * @param teamId 团队Id
     * @return 贡献者列表
     * @throws IOException
     */
    public List<ContributorDTO> getContributorStatics(BigInteger teamId) throws IOException {
        Team team = teamMapper.findTeamByTeamId(teamId);
        String githubUsername = team.getGithubUsername();
        String githubToken = team.getGithubToken();
        String repoUrl = team.getRepoUrl();
        OkHttpClient client = Utils.getOkhttpClientWithProxy();
        String combineToken = githubUsername + ":" + githubToken;
        String auth = Base64.getEncoder().encodeToString(combineToken.getBytes());
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + repoUrl + "/stats/contributors")
                .method("GET", null)
                .addHeader("Authorization", "Basic " + auth)
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
        String json = Objects.requireNonNull(response.body()).string();
        ObjectMapper objectMapper = new ObjectMapper();
        ContributorDTO[] contributors = objectMapper.readValue(json, ContributorDTO[].class);
        return Arrays.asList(contributors);
    }

}