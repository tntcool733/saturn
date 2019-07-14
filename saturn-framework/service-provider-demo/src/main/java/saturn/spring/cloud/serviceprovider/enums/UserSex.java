 package saturn.spring.cloud.serviceprovider.enums;

/**
 * @author wenziheng
 * @date 2019/07/14
 */
public enum UserSex {

    SECRET(-1), FEMALE(0), MALE(1);
    
    private int sex;

    private UserSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

}
