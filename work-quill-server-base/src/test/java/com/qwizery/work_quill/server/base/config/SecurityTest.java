package com.qwizery.work_quill.server.base.config;

import com.qwizery.work_quill.server.base.util.JwtTool;
import com.qwizery.work_quill.server.base.util.TimeTool;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile({"dev"})
@SpringBootTest
class SecurityTest {

    private static final Logger log = LoggerFactory.getLogger(SecurityTest.class);

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodeTest() {
        var result = passwordEncoder.encode("password");
        log.info(result);

        // The encoded password doesn't have a prefix {bcrypt}
        Assertions.assertFalse(result.startsWith("{bcrypt}"));

        var result2 = passwordEncoder.encode("password");
        Assertions.assertTrue(passwordEncoder.matches("password", result));
        Assertions.assertTrue(passwordEncoder.matches("password", result2));
    }

    @Test
    public void jwtTokenTest() throws InterruptedException {
        String jws = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3b3JrLXF1aWxsIiwiZXhwaXJlVGltZSI6IjE3MTc1NzE1NjIwMjciLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwibmlja25hbWUiOiLnrqHnkIblkZgiLCJ1c2VySWQiOiIxIiwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTcxNzU3MTU2Mn0.KNP7HF-V1WAQ4-TW9hR6zFDL8ntSJ9NoxMRJPlek9qFmblzqG4ETFN-I-2aw0ejmJ7-Zr5moxUHnQzL3cdUUonwlbFDZeC7QkMG4xCuy3Q8tjUqYiLNatiVcvPeuno2_lu8g7cNtIWrqI29o_DmhI2SXuW5dCA6uDrJw_DltFr2ZmXc1_Mawfhhi9SCknIohodt8tvqGRYFfDovE-tKy6paLA9w6m_JpFPrc2wFAdpE19-7my2MyV3jwokpJVjjZXupK_LWVuswGq2GWw7hpjV-pKir1byJ4bcuplpAlrbrj9N2Kx2pEsG54nAUGAl9xsFxHB67XjEdT-IIq-2yAAA";

        JwtTool.setTokenExpireDuration(1000L);
        JwtTool.setTokenRefreshDuration(3000L);
        var jwt = JwtTool.createJws(jws, TimeTool.getNowDate());
        var jwtRefreshToken = JwtTool.createRefreshJws("{ \"data\": \"data1\"}", TimeTool.getNowDate());

        boolean isValid1 = JwtTool.isJwsValid(jwt);
        boolean isValid2 = JwtTool.isJwsValid(jwtRefreshToken);
        Assertions.assertTrue(isValid1);
        Assertions.assertTrue(isValid2);

        Thread.sleep(1100L);

        boolean isValid3 = JwtTool.isJwsValid(jwt);
        boolean isValid4 = JwtTool.isJwsValid(jwtRefreshToken);
        Assertions.assertFalse(isValid3);
        Assertions.assertTrue(isValid4);

        Thread.sleep(2200L);

        boolean isValid5 = JwtTool.isJwsValid(jwt);
        boolean isValid6 = JwtTool.isJwsValid(jwtRefreshToken);
        Assertions.assertFalse(isValid5);
        Assertions.assertFalse(isValid6);
    }

    @Test
    public void jwtTokenValidTest() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3b3JrLXF1aWxsIiwiZXhwaXJlVGltZSI6IjE3MTc3MzAyMjI1MDMiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwibmlja25hbWUiOiLnrqHnkIblkZgiLCJ1c2VySWQiOiIxIiwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTcxNzczMDIyMn0.Pu-YvsQxYmo_fKbGZJTqzey-VAfyAlmQXpD3nDE3CiBPDi_76UZ1u0TEJWdMw5JvfdUyMGn1oPaINXyRkmLVDfgNh_au4V5AG9mC_PzjxeeN-B3amnj5W_TZchLpiVxp0pKWViVfwlhHcpHEUBPTfv37jzv8qiKgUJoUg5BbpiCv1SCCM28Vk0RAaqq5zlnaOFhZQVE5JJMzG6U5BjtAjE2M2-1jHMT1zMdI5mn5OehrgpNl0BMycH5ZYaZzL-XXkV_dqACM6nP0guL8JPU9JbyMJZ_UyZFjDF-oZCgQLoboYgZTY9R7jSIZ0rnZ1CT_6XQHv0HLb9UIBsZwb5DqFA";
        String refreshJwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3b3JrLXF1aWxsIiwiZXhwaXJlVGltZSI6IjE3MTc3MzAyMjI1MDMiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwibmlja25hbWUiOiLnrqHnkIblkZgiLCJ1c2VySWQiOiIxIiwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTcxNzczMjAyMn0.PLUxgvJIf9MkQOaApdlKUxlbXHdpoCjHZrN4tgmUwVA97Ikk9quwqszK_EXX8h08ByuZBpCAUkwJ1sMt3xYUb9Xo2WWMFwkp1njz_1_oQytwmZDw3MxbzUNI7Mc0PbfcWbLS6b2HSXMaHnfPutVB2a_gh6kUfmZrytLd8R-2t7Cy73Y2Mks4R_7x_4tKlvaKY80WzCF6rFL2Tykwt8o7czTA20lGo7g477m1AJzY8DxEk8rTa2iPuIx114FjkjBU889AWBS7F6ezKsouOAXQpDgxo6BL_E9R3HaugtYgrDWyhl-i5kTMW5IE3P9Q49fPG66PG_CYHLfKqx_JLly4Ow";

//        var parser = JwtTool.getJwtParser();
//        log.info("Jwt is Signed? {}", parser.isSigned(jwt));
//        var content = parser.parseSignedClaims(jwt);
//        log.info("Jwt Signed content: {}", content);
//        var expDate = Optional.ofNullable(content.getPayload().getExpiration()).orElseGet(() -> {
//            log.error("No expiration date found!");
//            return new Date();
//        });
//        log.info("Jwt is outdated? {}", expDate.after(TimeTool.getNowDate()));
        var result = JwtTool.isJwsValid(jwt);
        log.info("Jwt is valid? {}", result);
        Assertions.assertTrue(result);
    }
}