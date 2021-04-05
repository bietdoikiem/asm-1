package RMIT;

import java.util.ArrayList;

public interface CSVManageable<T> {
    public void saveToCsv(String filename, ArrayList<T> list);
    public void addFromCsv(String route);
}
