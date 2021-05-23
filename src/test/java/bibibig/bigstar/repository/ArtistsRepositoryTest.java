package bibibig.bigstar.repository;

import bibibig.bigstar.domain.Artists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArtistsRepositoryTest {

    @Autowired ArtistsRepository artistsRepository;

    @Test
    void 아티스트조회() {
        artistsRepository.findAll();
    }

    @Test
    void 아티스트추가() {
        Artists artists = new Artists();
//        artists.setArtistName("abcd");
//        artistsRepository.insert(artists);
    }


}