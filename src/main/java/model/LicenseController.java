package model;

import license.RepositoryLicense;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LicenseController {
    private String repo;
    private String owner;
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public RepositoryLicense reciveLicense(@RequestParam(value = "owner") String owner, @RequestParam(value = "repo") String repo) {
        this.repo = repo;
        this.owner = owner;
        restTemplate = new RestTemplate();
        RepositoryLicense repositoryLicense = restTemplate.getForObject(
                "https://api.github.com/repos/" + owner + "/" + repo + "/license", RepositoryLicense.class);

        return repositoryLicense;
    }



}
