package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Estimation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EstimationRepository {

    private final MongoTemplate mongoTemplate;

    public List<Estimation> findAll() {
        return mongoTemplate.findAll(Estimation.class);
    }

    public Estimation findByFoodName (String foodName) {
        Criteria criteria = new Criteria().where("food_name").is(foodName);
        MatchOperation matchOperation = Aggregation.match(criteria);

        AggregationResults<Estimation> aggregate =
                this.mongoTemplate.aggregate(Aggregation.newAggregation(matchOperation),
                        Estimation.class, Estimation.class);
        return Optional.ofNullable(aggregate.getUniqueMappedResult()).orElseThrow(() ->
                new RestClientException("찾으시는 음식이 없습니다."));
    }


}

