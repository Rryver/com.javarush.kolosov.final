package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.BaseHandler;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.internal.web.UserTestData;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_PROFILE = BaseHandler.REST_URL + "/profile";

    //    @MockBean
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));
    }

    @Test
    @WithUserDetails(value = UserTestData.GUEST_MAIL)
    public void get_whenUserIsGuest() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(GUEST_PROFILE_EMPTY_TO));
    }

    @Test
    public void get_whenUnauthorizedUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    public void update() throws Exception {
        long id = 1L;
        Profile expect = ProfileTestData.getUpdated(id);

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(ProfileTestData.getUpdatedTo(id)));
        perform(post)
                .andExpect(status().isNoContent());

        Profile actual = profileRepository.findById(id).orElseThrow();
        PROFILE_MATCHER.assertMatch(actual, expect);
    }
}