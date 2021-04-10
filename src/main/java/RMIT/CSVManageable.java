package RMIT;

import java.util.ArrayList;

public interface CSVManageable<T> {
    public boolean saveToCsv(String filename, ArrayList<T> list);
    public boolean addFromCsv(String route);
}
