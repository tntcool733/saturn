 package saturn.spring.cloud.serviceconsumer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wenziheng
 * @date 2019/07/13
 */
@Slf4j
@RestController
@RequestMapping("/fm")
@RefreshScope
public class FriendsMatchController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/getMatchUser")
    public String getMatchUser() {
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("uid", 123L);
        String result = restTemplate.getForObject("http://service-provider/user/getByUid?uid={uid}", String.class, paramMap);
        log.info("[cmd=getMatchUser result:{}]", result);
        return result;
    }

}
