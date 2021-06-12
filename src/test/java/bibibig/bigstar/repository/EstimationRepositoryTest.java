package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Estimation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EstimationRepositoryTest {

    @Autowired
    EstimationRepository est;

    @Test
    void 단건조회() {
        Estimation estimation = est.findByFoodName("오므라이스");
        Assertions.assertThat(estimation.getFood_name()).isEqualTo("오므라이스");

    }

}