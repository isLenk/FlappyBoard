package Talban.FlappyBoard;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import Talban.FlappyBoard.JImage;

import java.util.ArrayList;
import java.util.Random;

public class Results extends GameObject {

    // Images for the star ratings on death
    private PImage filledStar;
    // will be an empty star when displayed
    private PImage emptyStar;
    // the width of the star
    private float starWidth;
    // gap in between stars
    private float gap;
    // defining a random variable
    private Random rng;

    // year, month, day for the reviews
    private int year, month, day;
    // displayes day month year
    private String date;
    // different possible month choices
    private String[] months;

    private String chosenName;
    private String chosenReview = "";
    // list of names
    private String[] names = {"Bob", "jim", "larry", "rob", "jasmine", "chloe", "mathew", "sarah"};

    private ArrayList<String[]> reviewDescriptions;

    // Review Descriptions
    // 1 Star Review
    private String[] oneStarReview = {"item was very delayed on shipping time", "item came slightly damaged, hardly worked though", "very poor delivery time, item malfunctioned", "wouldn't recommend, item came in poor condition", "waited 2 months for amazon to respond to were my item what"};
    // 2 Star Review
    private String[] twoStarReview = {"item was pretty delayed, also damaged box", "item didnt fir description fully but works", "item was fine, but delivery was a pain", "ok product, customer serving took forever though", "wouldn't recommend, there are definetly better places to get this item"};
    // 3 Star Review
    private String[] threeStarReview = {"item was good and was just a little late", "would recommend if your willing to wait for it", "item came as description said, just came a little late", "pretty good customer service, helped me get things sorted out", "helpful customer care delayed shipping time however"};
    // 4 Star Review
    private String[] fourStarReview = {"on time, item worked for almost everything i needed", "no complaints, just cam a day to late", "good customer service, would recommend", "no issues with delivery everything works as should", "got what i expected process was smooth and would recommend"};
    // 5 Star Review
    private String[] fiveStarReview = {" loved the item would recommend for anyone thinking about buying", "1 day shipping! crazy how fast i got my item", "another great purchase from amazon, highly recommended", "always a smooth process with amazon, 100% recommended", "no issues, will be buying from amazon in the future!"};

    public Results(PApplet sketch) {
        super(sketch);
        // displaying filled and empty stars
        filledStar = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Misc/FilledStar2.png");
        emptyStar = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Misc/EmptyStar2.png");
        rng = new Random();
        // making an array list for the reviews
        reviewDescriptions = new ArrayList<>();
        // making a list of months
        months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        starWidth = 30;
        gap = 5;
        // generating random dates
        year = 1996 + rng.nextInt(200);
        month = rng.nextInt(12);
        day = rng.nextInt(31);
        date = String.format("%s %s, %s", months[month], day, year);
        // deciding what review to add to what star rating
        reviewDescriptions.add(oneStarReview);
        reviewDescriptions.add(twoStarReview);
        reviewDescriptions.add(threeStarReview);
        reviewDescriptions.add(fourStarReview);
        reviewDescriptions.add(fiveStarReview);
        chosenName = names[rng.nextInt(names.length - 1)];
    }

    public void display() {
        sketch.background(35, 47, 62);

        // Determines the filled versus non filled by getting a ratio from the required points per star
        int starPerSection = C.pipesPassedToWin / 4;

        // Box information
        float halfWidth = sketch.width / 2f;
        float halfHeight = sketch.height / 2f;
        float boxWidth = 600;
        float boxHeight = 300;
        float cornerDiameter = 30;

        sketch.fill(255);
        // Draw Rounded White box
        sketch.rect(halfWidth - boxWidth/2 - cornerDiameter/2, halfHeight - boxHeight/2, boxWidth + cornerDiameter, boxHeight);
        sketch.rect(halfWidth - boxWidth/2, halfHeight - boxHeight/2 - cornerDiameter/2, boxWidth, boxHeight + cornerDiameter);
        sketch.ellipse(halfWidth - boxWidth/2, halfHeight - boxHeight/2, cornerDiameter, cornerDiameter);
        sketch.ellipse(halfWidth + boxWidth/2, halfHeight - boxHeight/2, cornerDiameter, cornerDiameter);
        sketch.ellipse(halfWidth - boxWidth/2, halfHeight + boxHeight/2, cornerDiameter, cornerDiameter);
        sketch.ellipse(halfWidth + boxWidth/2, halfHeight + boxHeight/2, cornerDiameter, cornerDiameter);

        // Placement of the persons name
        sketch.textAlign(sketch.LEFT, sketch.TOP);
        sketch.fill(50);
        sketch.text(chosenName, halfWidth - boxWidth/2 + 20, halfHeight - boxHeight / 2 + 10);

        // Get a review if it does not yet exist
        if (chosenReview.equals("")) {
            chosenReview = reviewDescriptions.get((Player.points / starPerSection))[rng.nextInt(reviewDescriptions.get(Player.points / starPerSection).length - 1)];
        }
        // placment of review text
        sketch.text(chosenReview, halfWidth - boxWidth/2 + 20, halfHeight - boxHeight / 2 + 60);
        sketch.fill(150);

        sketch.text(date, halfWidth - boxWidth/2 + 20, halfHeight - boxHeight / 2 + 35);
        // Draw Stars
        for (int star = 1; star <= 5 ; star++) {
            float x = halfWidth + boxWidth / 2 - ((starWidth * star) + gap * star);
            float y = halfHeight - boxHeight / 2;
            if (Player.points / starPerSection >= 5 - star) {
                sketch.image(filledStar, x, y);
            } else {
                sketch.image(emptyStar, x, y);
            }
        }

        // Information
        sketch.textAlign(sketch.RIGHT, sketch.BOTTOM);
        sketch.fill(19, 26, 34);
        sketch.text("PRESS 'R' TO RESTART!", halfWidth + boxWidth / 2, halfHeight + boxHeight / 2);
    }
}
