package data.providers;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.classes.CreateUser;

public class NormalUserData {
    public static CreateUser randomUserData() {

        CreateUser createUser = new CreateUser();
        createUser.setName(RandomStringUtils.randomAlphabetic(9));
        createUser.setEmail(RandomStringUtils.randomAlphabetic(6)
                + System.currentTimeMillis()
                + "@mail.com");
        createUser.setPassword(RandomStringUtils.randomAlphabetic(6)
                + System.currentTimeMillis());
        return createUser;
    }
}
