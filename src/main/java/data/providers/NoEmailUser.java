package data.providers;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.classes.CreateUser;

public class NoEmailUser {
    public static CreateUser noEmailUserDataProvider() {

        CreateUser createUser = new CreateUser();
        createUser.setEmail(null);
        createUser.setName(RandomStringUtils.randomAlphabetic(9));
        createUser.setPassword(RandomStringUtils.randomAlphabetic(6)
                + System.currentTimeMillis());
        return createUser;
    }
}
