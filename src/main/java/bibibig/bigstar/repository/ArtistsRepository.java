package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Artists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ArtistsRepository {

    private final MongoTemplate mongoTemplate;

    public List<Artists> findAll() {
        List<Artists> all = mongoTemplate.findAll(Artists.class);
        for (Artists artists : all) {
            System.out.println("artists.getArtistName() = " + artists.getArtistname());
        }
        return all;
    }

    @Transactional
    public void insert(Artists artists) {
        mongoTemplate.insert(artists);
    }


}
