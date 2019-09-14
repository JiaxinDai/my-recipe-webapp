import com.essalah.recipescraper.services.AllRecipeService;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;




import java.io.IOException;




@RestController

public class RecipeApiController {




    @RequestMapping(method = RequestMethod.GET, value = "/fromurl")

    public Object getRecipeFromGivenUrl(@RequestParam("url") String url){

        try {

            return AllRecipeService.getFromUrl(url);

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }

    }

}
