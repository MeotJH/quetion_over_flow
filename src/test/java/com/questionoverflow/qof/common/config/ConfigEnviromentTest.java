package com.questionoverflow.qof.common.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ConfigEnviromentTest {

    @Value("${spring.config.activate.on-profile}")
    String enviromentValue;

    @Test
    @DisplayName("Local.yml이 잘 넣어졌는지 확인해야 한다.")
    void configEnviromentTest(){

        //then
        String local = "local";
        Assertions.assertThat(enviromentValue).isEqualTo(local);

    }
}
