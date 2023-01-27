package com.harera.hayat;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.harera.hayat.config.FirebaseServiceConfig;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@RunWith(SpringRunner.class)
@Import(FirebaseServiceConfig.class)
public abstract class ApplicationIT {

}
