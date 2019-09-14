public enum AllRecipeAttributesEnum {




    TITLE("recipe-summary__h1"),

    AUTHOR("submitter__name"),

    YIELD("metaRecipeServings"),

    IMAGE("rec-photo"),

    NUTRITION("calorie-count"),

    PREPARATION_TIME("time[itemprop=prepTime]"),

    COOK_TIME("time[itemprop=cookTime]"),

    READY_IN_TIME("time[itemprop=totalTime]"),

    INGREDIENT_LIST("recipe-ingred_txt added"),

    DIRECTION_LIST("recipe-directions__list--item");







    private String value;




    AllRecipeAttributesEnum(String s) {

        value = s;

    }




    public String getValue() {

        return value;

    }




    @Override

    public String toString() {

        return value;

    }

}

		 
