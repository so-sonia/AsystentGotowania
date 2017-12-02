import java.net.*;
import java.util.HashMap;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class Parser {

	public static void main(String[] args) {
		
		Parser parser = new Parser();
		
		HashMap<String, WebsiteData> knownWebsites = new HashMap<String, WebsiteData>();
		knownWebsites.put("kwestiasmaku", new WebsiteData("kwestiasmaku", "group-skladniki field-group-div", 
				"group-przepis field-group-div", "field field-name-field-wskazowki field-type-text-long field-label-above"));
		knownWebsites.put("kotlet", new WebsiteData("kotlet", "ingredients", "steps-ul", "lists"));

		
		//jedna klasa na wszystko: jadlonomia: "center-element relative"
		try {	
			
			String website = "http://www.jadlonomia.com/przepisy/krem-z-pieczonej-dyni-i-pomidorow-z-syropem-klonowym/";
			//Jsoup.connect is used to get the HTML content of the given website
			Document doc = Jsoup.connect(website).get();
			
			//TO DO: get the name of the website and chceck if it is in the database;
			// then check in which class to look for the ingredients and recipe
			String websiteName = parser.getWebsiteName(website);
			
			//If the website exists in our database we get only the content of the specific class in which
			//ingredients are located
			Elements ingredientsDirty = doc.getElementsByClass("center-element relative");

//			Element newsHeadlines = doc.getElementsContainingOwnText("sk³adniki").get(1).parent().parent();
//			String news = newsHeadlines.text();	
//			System.out.println(news.replace("\t", "\\t").replace("\n","\\n").replace("\r", "\\r"));
			
			String ingredientsClean = parser.cleanParser(ingredientsDirty);


			//we parse the recipe in the same way
			Elements recipeDirty = doc.getElementsByClass("font-bold subtitle big-margin-top");
			String recipeClean = parser.cleanParser(recipeDirty);
			
			System.out.println(websiteName);
			System.out.println(ingredientsClean);
			System.out.println(recipeClean);
			
			
		}
		catch (IOException e) {
            e.printStackTrace();
        }
		
		 /*</div><div class="group-przepis field-group-div">  <h3 class="field-label">
    Przygotowanie  </h3>

  <div class="field field-name-field-przygotowanie field-type-text-long field-label-above">*/
		 
		 /* <div class="group-skladniki field-group-div"><h3><span>Sk³adniki</span></h3>*/
		 
		 /*<div class="field field-name-field-wskazowki field-type-text-long field-label-above">*/
		 
		 /*Ró¿ne typy stron:
		  * zaczynaj¹ce siê od s³owa sk³adniki, sk³adniki:
		  * zaczynaj¹ce listê od powtórzenia nazwy przepisu (pogrubionej <p><strong>)
		  * nie ka¿dy blog rozpoczyna specyficzna klasa
		  */
		 
		 /*Ró¿ne typy stron:
		  * Instrukcje, Przygotowania, przepis
		  * tuz pod sk³adnikami, bez ¿adnego innego s³owa
		  * nazwa przepisu - przepis:
		  */
		 
	}
	
	
	/**cleanParser gets rid of the HTML tags preserving the new line characters. It also cleans extra new line characters to
	get pretty output (first making sure all multiple spaces are change to one space, so they are not considered
	as more than one whitespace character)*/
	
	public String cleanParser(Elements ingredientsDirty){
		ingredientsDirty.select("br").append("\\n");
		ingredientsDirty.select("p").prepend("\\n\\n");
	    String ingredients = ingredientsDirty.html().replaceAll("\\\\n", "\n");
		ingredients = Jsoup.clean(ingredients, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
		ingredients = ingredients.replaceAll(" {2,}", " ").replaceAll("(\\s){2,}", "\n").replaceAll("&nbsp;", " ");
		return ingredients;
	}
	
	/**extracts the name of the website from the link*/
	public String getWebsiteName(String website){
		String name = website.replaceAll(".*(www\\.|http://)(.*)\\.(pl|com|tv).*", "$2");
		return name;
	}
	
	//TO DO: parser other powinien sprawdzaæ, czy nie ma tam te¿ sk³adników i/lub instrukcji

}
