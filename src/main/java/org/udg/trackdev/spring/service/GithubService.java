package org.udg.trackdev.spring.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.udg.trackdev.spring.dto.response.GithubPullRequestDTO;
import org.udg.trackdev.spring.entity.GithubInfo;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.repository.GithubInfoRepository;
import org.udg.trackdev.spring.utils.GithubApiConstants;

import java.util.List;

/**
 * The type Github service.
 */
@Service
public class GithubService extends BaseServiceUUID<GithubInfo, GithubInfoRepository>{

    private final RestTemplate restTemplate;

    /**
     * Instantiates a new Github service.
     */
    public GithubService(){
        super();
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get github information response entity.
     *
     * @param token the token
     * @return the response entity
     */
    public ResponseEntity<GithubInfo> getGithubInformation(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        try{
            return restTemplate.exchange(
                    GithubApiConstants.GITHUB_API_USER_URL,
                    org.springframework.http.HttpMethod.GET,
                    requestEntity,
                    GithubInfo.class
            );
        }
        catch (HttpClientErrorException e){
            if(e.getStatusCode().value() == 401){
                return ResponseEntity.status(401).build();
            }
            else{
                return ResponseEntity.status(500).build();
            }
        }

    }

    public List<GithubPullRequestDTO> getPullRequest(Project project, User user){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + user.getGithubInfo().getGithub_token());
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        // TODO: Change the hardcoded repository name (trackdev2-spring) to the project name
        String url = String.format(GithubApiConstants.GITHUB_API_PR_URL,project.getCourse().getGithubOrganization(),project.getName());
        try {
            return restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<GithubPullRequestDTO>>() {}
            ).getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }


}
