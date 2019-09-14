import java.util.List;




/**

 * model for foods recipes

 */

public class Recipe {




    private String title; // exp : Chorizo Stuffed Bell Peppers

    private String description; // exp : optional exp : an amazing recipe ...

    private String author; // exp : Chef Yanki naki

    private String yield; // exp : 2 person

    private String photo; // exp : https://www.allrecipe.com/recipe/img/1x.png

    private String tag; // exp : main dishes

    private String nutrition; // exp : 150 cals

    private String preparationTime; // exp : 40 m

    private String cookTime; // exp : 1 h

    private String readyInTime; // exp :1h 40 m

    private List<Ingredient> ingredients; // exp : water, salt, carrot ...

    private List<Instruction> instructions; // exp : step 1, step 2 ..end

    private String source;




    /**

     * Constructor

     */

    public Recipe(){




    }




    public String getTitle() {

        return title;

    }




    public void setTitle(String title) {

        this.title = title;

    }




    public String getDescription() {

        return description;

    }




    public void setDescription(String description) {

        this.description = description;

    }




    public String getAuthor() {

        return author;

    }




    public void setAuthor(String author) {

        this.author = author;

    }




    public String getYield() {

        return yield;

    }




    public void setYield(String yield) {

        this.yield = yield;

    }




    public String getPhoto() {

        return photo;

    }




    public void setPhoto(String photo) {

        this.photo = photo;

    }




    public String getTag() {

        return tag;

    }




    public void setTag(String tag) {

        this.tag = tag;

    }




    public String getNutrition() {

        return nutrition;

    }




    public void setNutrition(String nutrition) {

        this.nutrition = nutrition;

    }




    public String getPreparationTime() {

        return preparationTime;

    }




    public void setPreparationTime(String preparationTime) {

        this.preparationTime = preparationTime;

    }




    public String getCookTime() {

        return cookTime;

    }




    public void setCookTime(String cookTime) {

        this.cookTime = cookTime;

    }




    public String getReadyInTime() {

        return readyInTime;

    }




    public void setReadyInTime(String readyInTime) {

        this.readyInTime = readyInTime;

    }




    public List<Ingredient> getIngredients() {

        return ingredients;

    }




    public void setIngredients(List<Ingredient> ingredients) {

        this.ingredients = ingredients;

    }




    public List<Instruction> getInstructions() {

        return instructions;

    }




    public void setInstructions(List<Instruction> instructions) {

        this.instructions = instructions;

    }




    public String getSource() {

        return source;

    }




    public void setSource(String source) {

        this.source = source;

    }

}
