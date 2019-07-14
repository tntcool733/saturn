 package saturn.spring.cloud.serviceprovider.controller.view;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author wenziheng
 * @date 2019/07/14
 */
@Data
@Builder
public class UserView {

    private Long uid;
    private String name;
    private Integer sex;
    private Date registTime;

}
