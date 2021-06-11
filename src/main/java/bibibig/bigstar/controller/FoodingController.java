package bibibig.bigstar.controller;

import bibibig.bigstar.domain.*;
import bibibig.bigstar.repository.FoodingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Controller
@Slf4j
public class FoodingController {

    private final FoodingRepository foodingRepository;

    @GetMapping("/")
    public String fooding (Model model) {

        List<LikesByFood> likesByFoods = foodingRepository.getLikesByFood();
        List<MainRankedFood> topfoods = new ArrayList<>();
        List<String> topFoodNames = new ArrayList<>();

        List<LikesByFood> allFoods = foodingRepository.getLikesByFoodNoSort();
        List<String> allFoodNames = new ArrayList<>();
        List<Integer> allFoodLikes = new ArrayList<>();

        LikesByFood totalLikes = foodingRepository.getTotalLikes();
        int totalCount = totalLikes.getTotalLikes();
        int start = 30;


        List<BubbleDataset> bubbleDatasets = new ArrayList<>();

        Random random = new Random();
        BubbleElementsDTO one = new BubbleElementsDTO(0,0,0);


        for (LikesByFood likesByFood : allFoods) {
            BubbleDataset bds = new BubbleDataset();


            List<BubbleElementsDTO> lbe = new ArrayList<>();
            BubbleElementsDTO be = new BubbleElementsDTO(
                    random.nextInt(85)+5, random.nextInt(85)+5, (int)(((double)likesByFood.getTotalLikes()/totalCount) * 1000));

            start++;
            lbe.add(be);


            bds.setData(lbe);
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);

            bds.setBorderColor("rgba("+r+", "+g+", "+b+", 0.7)");
            bds.setBackgroundColor("rgba("+r+", "+g+", "+b+", 0.7)");

            bds.setLabel(likesByFood.getFood_name());
            bubbleDatasets.add(bds);

            allFoodNames.add(likesByFood.getFood_name());
            allFoodLikes.add(likesByFood.getTotalLikes());
        }



        model.addAttribute("bds",bubbleDatasets);

        model.addAttribute("allFoodNames", allFoodNames);
        model.addAttribute("allFoodLikes", allFoodLikes);


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
        int counts = 0;
        for (Fooding fooding : byFoodName) {
            counts += fooding.getLike();
        }
        if(byFoodName.isEmpty() || byFoodName == null) {
            redirectAttributes.addAttribute("fail", true);
            return "redirect:/";
        }


        List<LikesByDate> likesByDates = foodingRepository.getLikesByDate(foodName);
        List<String> dates = new ArrayList<>();
        List<Integer> likes = new ArrayList<>();
        for (LikesByDate likesByDate : likesByDates) {
            dates.add(likesByDate.getDate());
            likes.add(likesByDate.getTotalLikes());
        }

        // 등수 찾기
        List<LikesByFood> likesByFood = foodingRepository.getLikesByFood();
        int grade = 1;
        for (LikesByFood byFood : likesByFood) {
            if(byFood.getFood_name().equals( foodName)) {
                break;
            }
            grade++;
        }

        model.addAttribute("grade", grade);
        model.addAttribute("counts", counts);
        model.addAttribute("food", byFoodName.get(0));
        model.addAttribute("likesByDates", dates);
        model.addAttribute("likes", likes);

        return "about";
    }
}
