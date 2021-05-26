package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByFood;
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


    @Test
    void 전체조회() {
        foodingRepository.findAll();
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

    @Test
    void 음식에따른좋아요수() {
        List<LikesByFood> likesByFood = foodingRepository.getLikesByFood();
        System.out.println("likesByFood.get(0) = " + likesByFood.get(0));
        String foodName = likesByFood.get(0).getFood_name();
        int totalLikes = likesByFood.get(0).getTotalLikes();

        System.out.println("foodName = " + foodName);
        System.out.println("totalLikes = " + totalLikes);

    }

}