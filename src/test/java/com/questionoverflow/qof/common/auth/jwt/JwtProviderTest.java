package com.questionoverflow.qof.common.auth.jwt;

import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProviderImpl jwtProvider;

    @Test
    void verifyTokenTest() {
        //given
        JsonWebToken jsonWebToken = jwtProvider.generateToken("mallangyi@naver.com");
        String accessToken = jsonWebToken.getAccessToken();

        //when
        boolean rslt = jwtProvider.verifyToken(accessToken);

        //then
        boolean answer = true;
        Assertions.assertThat(rslt).isEqualTo(answer);
    }

    @Test
    void getEmail() {

        //given
        String email = "mallangyi@naver.com";
        JsonWebToken jsonWebToken = jwtProvider.generateToken(email);
        String accessToken = jsonWebToken.getAccessToken();

        //when
        String rslt = jwtProvider.getEmail(accessToken);

        //then
        Assertions.assertThat(rslt).isEqualTo(email);
    }
}