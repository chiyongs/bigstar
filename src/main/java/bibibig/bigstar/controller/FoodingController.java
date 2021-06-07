package bibibig.bigstar.controller;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByDate;
import bibibig.bigstar.domain.LikesByFood;
import bibibig.bigstar.repository.FoodingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FoodingController {

    private final FoodingRepository foodingRepository;

    @GetMapping("/")
    public String fooding (@RequestParam String foodName, Model model) {

        // 날짜에 따른 좋아요 수
        List<LikesByDate> likesByDates = foodingRepository.getLikesByDate(foodName);
        List<Fooding> byFoodName = foodingRepository.findByFoodName(foodName);
        List<LikesByFood> likesByFoods = foodingRepository.getLikesByFood();
        List<Fooding> foods = new ArrayList<>();
        for(int i=0; i<3; i++) {
            foods.add(byFoodName.get(i));
        }

        model.addAttribute("likesByFoods", likesByFoods);
        model.addAttribute("likesByDates",likesByDates);
        // 해당 음식에 대해 좋아요 랭킹 3개
        model.addAttribute("foods", foods);

        return "ok";

    }
}
