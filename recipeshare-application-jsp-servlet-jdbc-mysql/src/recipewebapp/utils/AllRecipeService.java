import com.essalah.recipescraper.model.Ingredient;

import com.recipescraper.model.Instruction;

import com.recipescraper.model.Recipe;

import com.recipescraper.model.enums.AllRecipeAttributesEnum;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;




import java.io.IOException;

import java.util.ArrayList;

import java.util.List;




public class AllRecipeService {




    public static Recipe getFromUrl(String url) throws IOException {

        Recipe recipe = new Recipe();

        Document document = Jsoup.connect(url).get();

        // title

        Element titleElement = document.getElementsByClass(AllRecipeAttributesEnum.TITLE.getValue()).first();

        if (titleElement != null)

            recipe.setTitle(titleElement.text());




        // author

        Element authorElement = document.getElementsByClass(AllRecipeAttributesEnum.AUTHOR.getValue()).first();

        if (authorElement != null)

            recipe.setAuthor(authorElement.text());




        // yield

        Element yieldElement = document.getElementById(AllRecipeAttributesEnum.YIELD.getValue());

        if (yieldElement != null) {

            recipe.setYield(yieldElement.attr("content"));

        }

        // image

        Element imageElement = document.getElementsByClass(AllRecipeAttributesEnum.IMAGE.getValue()).first();

        if (imageElement != null)

            recipe.setPhoto(imageElement.attr("src"));




        // nutrition

        Element nutritionTimeElement = document.getElementsByClass(AllRecipeAttributesEnum.NUTRITION.getValue()).first();

        if (nutritionTimeElement != null)

            recipe.setNutrition(nutritionTimeElement.text());







        // preparation time

        Element preparationTimeElement = document.select(AllRecipeAttributesEnum.PREPARATION_TIME.getValue()).first();

        if (preparationTimeElement != null)

            recipe.setPreparationTime(preparationTimeElement.attr("datetime").replace("PT", ""));




        // cook time

        Element cookTimeElement = document.select(AllRecipeAttributesEnum.COOK_TIME.getValue()).first();

        if (cookTimeElement != null)

            recipe.setCookTime(cookTimeElement.attr("datetime").replace("PT", ""));




        // ready in time

        Element readyInTimeElement = document.select(AllRecipeAttributesEnum.READY_IN_TIME.getValue()).first();

        if (readyInTimeElement != null)

            recipe.setReadyInTime(readyInTimeElement.attr("datetime").replace("PT", ""));




        // source

        recipe.setSource(document.baseUri());




        // seek for ingredients

        recipe.setIngredients(getIngredientList(document));

        //seek for instructions

        recipe.setInstructions(getInstructionList(document));




        return recipe;

    }







    private static List<Ingredient> getIngredientList(Document document) {

        List<Ingredient> ingredients = new ArrayList<>();

        Elements ingredientsElements = document.getElementsByClass(AllRecipeAttributesEnum.INGREDIENT_LIST.getValue());

        for (Element ingredientElement :

                ingredientsElements) {




            Ingredient ingredient = new Ingredient();

            ingredient.setIngredient(ingredientElement.text());

            if (!ingredient.getIngredient().isEmpty())

                ingredients.add(ingredient);







        }




        return ingredients;

    }




    private static List<Instruction> getInstructionList(Document document) {

        List<Instruction> instructions = new ArrayList<>();




        Elements instructionElements = document.getElementsByClass(AllRecipeAttributesEnum.DIRECTION_LIST.getValue());

        int i = 1;

        for (Element instructionElement :

                instructionElements) {




            Instruction instruction = new Instruction();

            instruction.setDescription(instructionElement.text());

            instruction.setOrder(i);

            if (!instruction.getDescription().isEmpty()) {

                instructions.add(instruction);

                i++;

            }







        }




        return instructions;

    }

}
