package recipewebapp.dao;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.opencsv.CSVReader;
import com.recipeapi.api.Rating;
import com.recipeapi.api.Recipe;

public class RecipeDaoImpl implements RecipeDao {
	
	//using this as DB
	Map<Long, Recipe> recipes;
	private int paginationLimit = 2;
	private long lastUsedId;
	
	public RecipeDaoImpl() throws IOException, ParseException {
		recipes = new HashMap<Long, Recipe>();
		CSVReader reader = new CSVReader(new FileReader("recipe-data.csv"));
	     String [] nextLine;
	     while ((nextLine = reader.readNext()) != null) {
	    	Logger.getLogger(this.getClass()).info("Loaded in recipe id: " + nextLine[0]);
	        Recipe recipe = new Recipe(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5],
	        		nextLine[6], nextLine[7], nextLine[8], nextLine[9], nextLine[10], nextLine[11], nextLine[12],
	        		nextLine[13], nextLine[14], nextLine[15], nextLine[16], nextLine[17], nextLine[18], nextLine[19],
	        		nextLine[20], nextLine[21], nextLine[22], nextLine[23], nextLine[24], nextLine[25]);
	        recipes.put(recipe.getId(), recipe);
	        
	     }
	     reader.close();
	     this.lastUsedId = getMaxId();
	}
	
	private long getMaxId() {
		long max = 0;
		Iterator<Entry<Long, Recipe>> it = recipes.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Long, Recipe> pair = (Map.Entry<Long, Recipe>)it.next();
	        if (max < pair.getValue().getId())
	        	max = pair.getValue().getId();
	    }
	    return max;
	}

	/*
	 * Would use query params here instead of only cuisines
	 */
	public List<Recipe> getAllRecipes(String cuisine, int page) {
		List<Recipe> result = new ArrayList<Recipe>();
		if (cuisine == null || cuisine.isEmpty()) {
			Iterator<Entry<Long, Recipe>> it = recipes.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Long, Recipe> pair = (Map.Entry<Long, Recipe>)it.next();
		        result.add(pair.getValue());
		    }
		}
		else {
			Iterator<Entry<Long, Recipe>> it = recipes.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Long, Recipe> pair = (Map.Entry<Long, Recipe>)it.next();
		        if (pair.getValue().getRecipe_cuisine().equalsIgnoreCase(cuisine))
		        	result.add(pair.getValue());
		    }
		}
		List<List<Recipe>> paginated = paginate(result);
		if (page <= 0 || page > paginated.size())
			page = 1;
		return paginated.get(page-1);
	}
	
	public List<List<Recipe>> paginate(List<Recipe> results) {
		if (results == null)
			return Collections.emptyList();
		List<Recipe> list = new ArrayList<Recipe>(results);
		int pageSize = paginationLimit;
		if (paginationLimit <= 0 || paginationLimit > list.size())
			pageSize = list.size();
		int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
	    List<List<Recipe>> pages = new ArrayList<List<Recipe>>(numPages);
	    for (int pageNum = 0; pageNum < numPages;)
	        pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
	    return pages;
	}

	public Recipe getRecipe(long id) {
		return recipes.get(id);
	}

	public boolean updateRecipe(long id, Recipe recipe) {
		if (recipes.containsKey(id)) {
			recipes.put(id, recipe);
			return true;
		}
		return false;
	}

	public long addRecipe(Recipe recipe) {
		recipe.setId(++lastUsedId);
		recipes.put(recipe.getId(), recipe);
		return lastUsedId;
	}

	public long addRating(long id, Rating rating) {
		if (rating.getRating() >=1 && rating.getRating() <= 5) {
			Recipe recipe = recipes.get(id);
			recipe.getRatings().add(rating);
			recipe.recalcAvgRating(rating.getRating());
			//recipe.save(); to persist changes
			// return new rating id;
			return 1;
		}
		return 0;
	}

	public void deleteRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		
	}
	
	public long getLastUsedId() {
		return this.lastUsedId;
	}
}