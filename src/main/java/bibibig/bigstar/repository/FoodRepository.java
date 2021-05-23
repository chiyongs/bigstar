package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FoodRepository {

    private final MongoTemplate mongoTemplate;

    public void findAll() {
        List<Food> all = mongoTemplate.findAll(Food.class);
        all.get(0).getFoodings().get(0);
        System.out.println("all.get(0).getFoodings().get(0).getFood_name() = " + all.get(0).getFoodings().get(0).getFood_name());

    }
}
