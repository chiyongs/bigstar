package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Fooding;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodingRepositoryTest {

    @Autowired FoodingRepository foodingRepository;
    @Autowired FoodRepository foodRepository;

    @Test
    void 전체조회() {
        foodRepository.findAll();
    }

    @Test
    void 이름으로조회() {
        List<Fooding> foodings = foodingRepository.findByFoodName("청국장찌개");
        System.out.println("this = ");
        for (Fooding fooding : foodings) {
            System.out.println("fooding = " + fooding.getFood_name());
            assertThat(fooding.getFood_name()).isEqualTo("청국장찌개");
        }

    }

//    @Test
//    void 전체조회() {
//        foodingRepository.findAll();
//
//    }

    @Test
    void 삽입() {
        Fooding fooding = new Fooding();
        fooding.setFood_name("떡볶이");
        foodingRepository.insertFooding(fooding);
    }

}