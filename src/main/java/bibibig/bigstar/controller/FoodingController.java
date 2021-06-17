package bibibig.bigstar.controller;

import bibibig.bigstar.domain.*;
import bibibig.bigstar.repository.EstimationRepository;
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
    private final EstimationRepository estimationRepository;

    @GetMapping("/")
    public String fooding (Model model) {

        List<LikesByFood> likesByFoods = foodingRepository.getLikesByFood();
        List<MainRankedFood> topfoods = new ArrayList<>();

        makeBubbleChartOfAllFoods(model);
        makeLineChartOfTop5Foods(model, likesByFoods);
        getTop3Foods(model, likesByFoods, topfoods);

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

        String tagData = byFoodName.get(0).getTag().toString();


        model.addAttribute("tags", tagData);
        model.addAttribute("grade", grade);
        model.addAttribute("counts", counts);
        model.addAttribute("food", byFoodName.get(0));
        model.addAttribute("likesByDates", dates);
        model.addAttribute("likes", likes);

        Estimation estimation = estimationRepository.findByFoodName(foodName);
        List<EstGrade> positives = estimation.getPositive();
        List<EstGrade> negatives = estimation.getNagative();
        Random random = new Random();



        List<BubbleDataset> estimationBubbleDatasets = new ArrayList<>();


        int r1 = 10;
        int g1 = 0;
        int b1 = 255;
        for (EstGrade positive : positives) {
            BubbleDataset bs1 = new BubbleDataset();

            List<BubbleElementsDTO> lbu = new ArrayList<>();
            BubbleElementsDTO bu1 = new BubbleElementsDTO(random.nextInt(50), random.nextInt(90), (int) (positive.getScore() * 40));
            lbu.add(bu1);

            bs1.setData(lbu);



            bs1.setBorderColor("rgba("+r1+", "+g1+", "+b1+", 0.8)");
            bs1.setBackgroundColor("rgba("+r1+", "+g1+", "+b1+", 0.8)");

            r1 += 25;
            g1 += 25;



            bs1.setLabel(positive.getName());
            estimationBubbleDatasets.add(bs1);


        }

        int r2 = 255;
        int g2 = 0;
        int b2 = 50;

        for (EstGrade negative : negatives) {
            BubbleDataset bs2 = new BubbleDataset();

            List<BubbleElementsDTO> lbu = new ArrayList<>();
            BubbleElementsDTO bu2 = new BubbleElementsDTO(random.nextInt(50) + 50, random.nextInt(90), (int) (Math.abs(negative.getScore()) * 40));
            lbu.add(bu2);

            bs2.setData(lbu);



            bs2.setBorderColor("rgba("+r2+", "+g2+", "+b2+", 0.8)");
            bs2.setBackgroundColor("rgba("+r2+", "+g2+", "+b2+", 0.8)");

            g2 += 25;
            b2 += 25;




            bs2.setLabel(negative.getName());
            estimationBubbleDatasets.add(bs2);




        }


        model.addAttribute("estimationBubbleDataset", estimationBubbleDatasets);

        return "about";
    }
    private void makeLineChartOfTop5Foods(Model model, List<LikesByFood> likesByFoods) {

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
    }

    private void makeBubbleChartOfAllFoods(Model model) {

        List<LikesByFood> allFoods = foodingRepository.getLikesByFoodNoSort();
        List<String> allFoodNames = new ArrayList<>();
        List<Integer> allFoodLikes = new ArrayList<>();

        LikesByFood totalLikes = foodingRepository.getTotalLikes();
        int totalCount = totalLikes.getTotalLikes();

        List<BubbleDataset> bubbleDatasets = new ArrayList<>();

        BubbleElementsDTO one = new BubbleElementsDTO(0,0,0);
        Random random = new Random();

        for (LikesByFood likesByFood : allFoods) {
            BubbleDataset bds = new BubbleDataset();

            List<BubbleElementsDTO> lbe = new ArrayList<>();
            BubbleElementsDTO be = new BubbleElementsDTO(
                    random.nextInt(85)+5, random.nextInt(85)+5, (int)(((double)likesByFood.getTotalLikes()/totalCount) * 1000));

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
    }

    private void getTop3Foods(Model model, List<LikesByFood> likesByFoods, List<MainRankedFood> topfoods) {
        for(int i = 0; i<3; i++) {
            MainRankedFood ma = new MainRankedFood();
            ma.setURL(foodingRepository.findByFoodName(likesByFoods.get(i).getFood_name()).get(i).getURL());
            ma.setLikesByFood(likesByFoods.get(i));
            topfoods.add(ma);
        }

        model.addAttribute("topFoods", topfoods);
    }

}
