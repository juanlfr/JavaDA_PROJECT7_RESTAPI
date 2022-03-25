package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;
    @MockBean
    private RatingRepository ratingRepository;

    private Rating rating1;
    private Rating rating2;
    private Rating rating3;
    private List<Rating> ratingList;

    @Before
    public void setUp() {
        rating1 = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating2 = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 20);
        rating2.setId(2);
        rating3 = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 30);
        ratingList = Arrays.asList(rating1, rating2, rating3);
    }

    @Test
    public void findAllTest() {
        when(ratingRepository.findAll()).thenReturn(this.ratingList);
        List<Rating> RatingTest = ratingService.findAll();
        assertThat(RatingTest).isSameAs(ratingList);
        assertThat(RatingTest).hasSize(3);
    }

    @Test
    public void createRatingTest() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);
        Rating RatingTest = ratingService.createRating(this.rating1);
        assertThat(RatingTest).isEqualTo(this.rating1);
    }

    @Test
    public void findByIdTest() {

        when(ratingRepository.findById(2)).thenReturn(Optional.ofNullable(this.rating2));
        Rating RatingFoundById = ratingService.findById(2).get();
        assertThat(RatingFoundById).isEqualTo(this.rating2);

    }

    @Test
    public void deleteTest() {
        ratingService.delete(rating3);
        verify(ratingRepository, Mockito.times(1)).delete(rating3);

    }
}
