import java.util.Arrays;
import java.util.List;

public class Menu {
    private final List<String> entries;
    private int currentSelection = 0;

    public Menu() {
        this.entries = Arrays.asList("Start Game","Resume", "Exit");
    }

    public void nextEntry() {
        currentSelection++;
        if (currentSelection > entries.size() - 1)
            currentSelection = 0;
    }

    public void previousEntry() {
        currentSelection--;
        if (currentSelection < 0)
            currentSelection = entries.size() - 1;
    }

    public String getEntry(int i) {
        return entries.get(i);
    }

    public boolean isSelected(int i) {
        return currentSelection == i;
    }

    public boolean isSelectedExit() {
        return isSelected(2);
    }

    public boolean isSelectedResume() {return isSelected(1);}

    public boolean isSelectedStart() {
        return isSelected(0);
    }

    public int getNumberEntries() {
        return entries.size();
    }
}