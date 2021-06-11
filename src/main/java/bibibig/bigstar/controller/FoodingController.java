package bibibig.bigstar.controller;

import bibibig.bigstar.domain.Fooding;
import bibibig.bigstar.domain.LikesByDate;
import bibibig.bigstar.domain.LikesByFood;
import bibibig.bigstar.domain.MainRankedFood;
import bibibig.bigstar.repository.FoodingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<String> topFoodNames = new ArrayList<>();


        for(int i=0; i<5;i++) {
            List<LikesByDate> likesByDate = foodingRepository.getLikesByDate(likesByFoods.get(i).getFood_name());
            List<String> dates = new ArrayList<>();
            List<Integer> likes = new ArrayList<>();
            for (LikesByDate byDate : likesByDate) {
                likes.add(byDate.getTotalLikes());
                dates.add(byDate.getDate());
            }

            model.addAttribute("likes"+i, likes);
            model.addAttribute("dates"+i, dates);
            model.addAttribute("foodName" + i, likesByDate.get(0).getFood_name());
        }

        List<LikesByDate> first = new ArrayList<>();
        for (String topFoodName : topFoodNames) {
            List<LikesByDate> likesByDate = foodingRepository.getLikesByDate(topFoodName);
        }

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
    public String aboutFood(@RequestParam String foodName, Model model, RedirectAttributes redirectAttributes) {
        List<Fooding> byFoodName = foodingRepository.findByFoodName(foodName);
        if(byFoodName.isEmpty() || byFoodName == null) {
            redirectAttributes.addAttribute("fail", true);
            return "redirect:/";
        }

        List<LikesByDate> likesByDates = foodingRepository.getLikesByDate(foodName);
        List<String> dates = new ArrayList<>();
        List<Integer> likes = new ArrayList<>();

        model.addAttribute("food", byFoodName.get(0));
        model.addAttribute("likesByDates", likesByDates);

        return "about";
    }
}
