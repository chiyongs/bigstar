package bibibig.bigstar.controller;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByDate;
import bibibig.bigstar.repository.FoodingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FoodingController {

    private final FoodingRepository foodingRepository;

    @GetMapping("/")
    public String fooding (@RequestParam String foodName, Model model) {

        // 날짜에 따른 좋아요 수
        List<LikesByDate> likesByDate = foodingRepository.getLikesByDate(foodName);

        return "ok";

    }
}
