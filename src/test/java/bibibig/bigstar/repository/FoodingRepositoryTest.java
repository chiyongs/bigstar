package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByDate;
import bibibig.bigstar.domain.LikesByFood;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
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
            System.out.println("fooding.getLike() = " + fooding.getLike());
            System.out.println("fooding.getURL() = " + fooding.getURL());
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

//        String foodName = likesByFood.get(2).getFood_name();
//        int totalLikes = likesByFood.get(2).getTotalLikes();
//
//        System.out.println("foodName = " + foodName);
//        System.out.println("totalLikes = " + totalLikes);
        for (LikesByFood byFood : likesByFood) {
            System.out.println("byFood.getFood_name() = " + byFood.getFood_name());
            System.out.println("byFood.getTotalLikes() = " + byFood.getTotalLikes());
        }

    }

    @Test
    void 해당음식에날짜에따른좋아요수() {
        List<LikesByDate> likes = foodingRepository.getLikesByDate("비빔밥");
        for (LikesByDate like : likes) {
            System.out.println("like.getDate() = " + like.getDate());
            System.out.println("like.getTotalLikes() = " + like.getTotalLikes());
        }
    }

    
    @Test
    void 정렬없는음식조회() {
        List<LikesByFood> likesByFoodNoSort = foodingRepository.getLikesByFoodNoSort();
        int totalCount = 0;
        for (LikesByFood likesByFood : likesByFoodNoSort) {
            totalCount += likesByFood.getTotalLikes();
            System.out.println("likesByFood.getTotalLikes() = " + likesByFood.getTotalLikes());
        }
        System.out.println("totalCount = " + totalCount);
    }
    @Test
    void 전체좋아요수() {
        LikesByFood totalLike = foodingRepository.getTotalLikes();


            System.out.println("totalLike.getTotalLikes() = " + totalLike.getTotalLikes());

    }



}