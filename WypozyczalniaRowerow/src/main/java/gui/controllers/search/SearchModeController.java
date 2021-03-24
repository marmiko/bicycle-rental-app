package gui.controllers.search;

public class SearchModeController {
    protected static boolean SearchMode = false;
    public static void enterSearchMode(){
        SearchMode = true;
    }
    public static void exitSearchMode(){
        SearchMode = false;
    }
}
