package com.dksd.service.user;

import com.dksd.service.user.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class UserJsonTest {

    @Autowired
    private JacksonTester<User> json;

    @Test
    public void testSerialize() throws Exception {
        User entry = User.EXAMPLE;
        // Assert against a `.json` file in the same package as the test
        //assertThat(this.json.write(entry)).isEqualToJson("expected.json");
        // Or use JSON path based assertions
        JsonContent<User> result = this.json.write(entry);
        assertThat(result).hasJsonPathStringValue("@.email");
        assertThat(result).extractingJsonPathStringValue("@.email")
                .isEqualTo("dylansd@gmail.com");
        assertThat(result).hasJsonPathStringValue("@.firstName");
        assertThat(result).extractingJsonPathStringValue("@.firstName")
                .isEqualTo("Dylan");

        //assertThat(result).hasJsonPathNumberValue("@.origHourEstimate");
        //assertThat(result).extractingJsonPathNumberValue("@.origHourEstimate").isEqualTo("24");

        assertThat(result).hasJsonPathStringValue("@.lastName");
        assertThat(result).extractingJsonPathStringValue("@.lastName")
                .isEqualTo("Scott-Dawkins");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":\"1\",\"parentId\":\"0\",\"title\":\"title\",\"description\":\"description\",\"estWorkRemaining\":\"1d\"}";
        this.json.parse(content);
    }

}
