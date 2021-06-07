package bibibig.bigstar.controller;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByDate;
import bibibig.bigstar.domain.LikesByFood;
import bibibig.bigstar.domain.MainRankedFood;
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
    public String fooding (Model model) {

        // 탑 랭킹 3위
        List<LikesByFood> likesByFoods = foodingRepository.getLikesByFood();
        List<MainRankedFood> topfoods = new ArrayList<>();

        for(int i = 0; i<3; i++) {
            MainRankedFood ma = new MainRankedFood();
            ma.setURL(foodingRepository.findByFoodName(likesByFoods.get(i).getFood_name()).get(i).getURL());
            ma.setLikesByFood(likesByFoods.get(i));
            topfoods.add(ma);
        }

        model.addAttribute("topFoods", topfoods);

        return "index";

    }

    @GetMapping("/search")
    public String aboutFood(@RequestParam String foodName, Model model) {
        List<Fooding> byFoodName = foodingRepository.findByFoodName(foodName);
        List<Fooding> foods = new ArrayList<>();

        List<LikesByDate> likesByDates = foodingRepository.getLikesByDate(foodName);
        for(int i=0; i<3; i++) {
            foods.add(byFoodName.get(i));
        }

        model.addAttribute("foods", foods);
        model.addAttribute("likesByDates", likesByDates);

        return "about";
    }
}
