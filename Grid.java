// Omar Iltaf

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class Grid {

  private Map<Integer, List<Integer>> cellPositions;
  private final int INITAL_SIZE = 6;

  public Grid(int seedGrid) {
    cellPositions = new HashMap<Integer, List<Integer>>();
    switch (seedGrid) {
      case 0: break;
      case 1: seedGrid1(); break;
      case 2: seedGrid2(); break;
      default: seedGrid1(); break;
    }
  }

  private void seedGrid1() {
    addCellPosition(1, 2);
    addCellPosition(2, 2);
    addCellPosition(3, 2);
  }

  private void seedGrid2() {
    for (int i = 0; i < INITAL_SIZE; i++) {
      if (i % 2 == 0) {
        for (int j = 0; j < INITAL_SIZE; j = j + 2) {
          addCellPosition(i, j);
        }
      } else {
        for (int j = 1; j < INITAL_SIZE; j = j + 2) {
          addCellPosition(i, j);
        }
      }
    }
  }

  public void update() {
    Set<Integer> cellPositionsKeys = cellPositions.keySet();
    Iterator<Integer> cellPosIterator = cellPositionsKeys.iterator();
    Map<Integer, Map<Integer, Integer>> cellNeighbours = new HashMap<Integer, Map<Integer, Integer>>();
    while (cellPosIterator.hasNext()) {
      int i = cellPosIterator.next();
      Iterator<Integer> jListIterator = cellPositions.get(i).iterator();
      while (jListIterator.hasNext()) {
        int j = jListIterator.next();
        checkCellNeighbours(cellNeighbours, i, j);
        includeCellPositionInNeighbours(cellNeighbours, i, j);
      }
    }
    // System.out.println(cellNeighbours);

    Set<Integer> cellNeighboursKeys = cellNeighbours.keySet();
    Iterator<Integer> cellNeighKeysIterator = cellNeighboursKeys.iterator();
    while (cellNeighKeysIterator.hasNext()) {
      int i = cellNeighKeysIterator.next();
      Map<Integer, Integer> jToCount = cellNeighbours.get(i);
      Set<Integer> jToCountKeys = jToCount.keySet();
      Iterator<Integer> jToCountKeysIterator = jToCountKeys.iterator();
      while (jToCountKeysIterator.hasNext()) {
        int j = jToCountKeysIterator.next();
        int nearbyCellCount = jToCount.get(j);
        if (containsCell(i, j)) {
          if (nearbyCellCount < 2 || nearbyCellCount > 3) {
            removeCellPosition(i, j);
          }
        } else {
          if (nearbyCellCount == 3) {
            addCellPosition(i, j);
          }
        }
      }
    }
  }

  private void includeCellPositionInNeighbours(Map<Integer, Map<Integer, Integer>> iToMap, int i, int j) {
    if (insideGrid(i, j)) {
      Map<Integer, Integer> jToCount = iToMap.get(i);
      if (jToCount != null) {
        int nearbyCellCount = (int) jToCount.getOrDefault(j, 0);
        jToCount.put(j, nearbyCellCount);
        iToMap.put(i, jToCount);
      } else {
        jToCount = new HashMap<Integer, Integer>();
        jToCount.put(j, 0);
        iToMap.put(i, jToCount);
      }
    }
  }

  private void addCellPosition(int i, int j) {
    if (cellPositions.containsKey(i)) {
        List<Integer> jList = cellPositions.get(i);
        jList.add(j);
        cellPositions.put(i, jList);
    } else {
      List<Integer> jList = new ArrayList<Integer>();
      jList.add(j);
      cellPositions.put(i, jList);
    }
  }

  private void removeCellPosition(int i, int j) {
    List<Integer> jList = cellPositions.get(i);
    jList.remove(new Integer(j));
    cellPositions.put(i, jList);
  }

  private void checkCellNeighbours(Map<Integer, Map<Integer, Integer>> iToMap, int i, int j) {
    int[][] modifiers = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    for (int[] modifier : modifiers) {
      if (insideGrid(i + modifier[0], j + modifier[1])) {
        int i2 = i + modifier[0];
        int j2 = j + modifier[1];
        Map<Integer, Integer> jToCount = iToMap.get(i2);
        if (jToCount != null) {
          int nearbyCellCount = (int) jToCount.getOrDefault(j2, 0);
          nearbyCellCount++;
          jToCount.put(j2, nearbyCellCount);
          iToMap.put(i2, jToCount);
        } else {
          jToCount = new HashMap<Integer, Integer>();
          jToCount.put(j2, 1);
          iToMap.put(i2, jToCount);
        }
      }
    }
  }

  private boolean insideGrid(int i, int j) {
    return i >= 0 && i < INITAL_SIZE && j >= 0 && j < INITAL_SIZE;
  }

  private boolean containsCell(int i, int j) {
    if (insideGrid(i, j)) {
      if (cellPositions.containsKey(i)) {
        List<Integer> jList = cellPositions.get(i);
        if (jList.contains(j)) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public void display() {
    String border = "";
    for (int i = 0; i < INITAL_SIZE; i++) {
      border += "---";
    }
    border+="-";
    System.out.println(border);
    for (int i = 0; i < INITAL_SIZE; i++) {
      for (int j = 0; j < INITAL_SIZE; j++) {
        if (containsCell(i, j)) {
          System.out.printf("|%2c", '#');
        } else {
          System.out.printf("|%2c", ' ');
        }
      }
      System.out.println("|");
    }
    System.out.println(border);
    // System.out.println(cellPositions);
  }


}
