package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByDate;
import bibibig.bigstar.domain.LikesByFood;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.previousOperation;


@RequiredArgsConstructor
@Repository
public class FoodingRepository {

    private final MongoTemplate mongoTemplate;

    public List<Fooding> findAll () {
        return mongoTemplate.findAll(Fooding.class);
    }

    public Fooding findById (Long id) {
        Fooding fooding = mongoTemplate.findById(id, Fooding.class);
        return Optional.ofNullable(fooding).orElseThrow(() -> new RestClientException("Not found fooding"));
    }

    public List<Fooding> findByFoodName (String foodName) {

        Query query = new Query(Criteria.where("food_name").is(foodName));
        return mongoTemplate.find(query, Fooding.class, "foodings");
    }

    @Transactional
    public String insertFooding (Fooding fooding) {
        mongoTemplate.insert(fooding);
        return fooding.getId();
    }

    public List<LikesByDate> getLikesByDate (String foodName) {
        //match
        Criteria criteria = new Criteria().where("foodName").is(foodName);
        MatchOperation matchOperation = Aggregation.match(criteria);

        //group
        GroupOperation groupOperation = Aggregation.group("foodName","date").sum("likes").as("totalLikes");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "date");

        ProjectionOperation projectionOperation = Aggregation.project("foodName","date","totalLikes");

        AggregationResults<LikesByDate> aggregate =
                this.mongoTemplate.aggregate(Aggregation.newAggregation(
                        matchOperation,groupOperation,sortOperation,projectionOperation),
                        Fooding.class, LikesByDate.class);

        return aggregate.getMappedResults();
    }

    // 음식이름 별 좋아요 수
    public List<LikesByFood> getLikesByFood () {
        GroupOperation groupOperation = Aggregation.group("foodName").sum("likes").as("totalLikes");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "totalLikes");

        ProjectionOperation projectionOperation = Aggregation.project("totalLikes").and(previousOperation()).as("foodName");

        AggregationResults<LikesByFood> aggregate =
                this.mongoTemplate.aggregate(newAggregation(groupOperation, sortOperation, projectionOperation),
                        Fooding.class, LikesByFood.class);

        return aggregate.getMappedResults();

    }
}
