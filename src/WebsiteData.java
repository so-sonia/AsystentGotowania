public class WebsiteData {
	private String name;
	private String ingredientsClass;
	private String recipeClass;
	private String other;
	
	
	public WebsiteData(String name, String ingredients, String recipe, String other){
		this.name = name;
		this.ingredientsClass = ingredients;
		this.recipeClass = recipe;
		this.other = other;
	}
	
	public WebsiteData(String name, String ingredients, String recipe){
		this(name, ingredients, recipe, "");
	}
	
	public WebsiteData(String name, String ingredients){
		this(name, ingredients, "", "");
	}
	
	public boolean hasIngredientClass(){
		return("".equals(ingredientsClass));
	}
	
	public boolean hasRecipeClass(){
		return("".equals(recipeClass));
	}
	
	public boolean hasOther(){
		return("".equals(other));
	}
	
	public String getIngredientClass(){
		return(ingredientsClass);
	}
	
	public String getRecipeClass(){
		return(recipeClass);
	}
	
	public String getOther(){
		return(other);
	}

	

}
