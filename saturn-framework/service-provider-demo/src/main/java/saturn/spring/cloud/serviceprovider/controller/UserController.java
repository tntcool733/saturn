 package saturn.spring.cloud.serviceprovider.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import saturn.spring.cloud.serviceprovider.controller.view.UserView;
import saturn.spring.cloud.serviceprovider.enums.UserSex;

/**
 * @author wenziheng
 * @date 2019/07/13
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Value("${user.uidWhiteList}")
    private String uidWhiteList;
    
    @RequestMapping(path = "/getUidWhiteList")
    public String getUidWhiteList() {
        return uidWhiteList;
    }

    @RequestMapping(path = "/getByUid", params = {"uid"})
    public UserView getByUid(Long uid) {
        log.info("[cmd=getByUid] uid:{}", uid);
        return UserView.builder().uid(uid).name("Saturn").sex(UserSex.MALE.getSex()).registTime(new Date()).build();
    }

}
