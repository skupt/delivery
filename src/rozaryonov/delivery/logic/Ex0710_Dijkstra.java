package rozaryonov.delivery.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 10. Реализуйте алгоритм Дейкстры для поиска кратчайших путей между городами, связанными
 * сетью автомобильных дорог. (Описание этого алгоритма можно найти в популярной литературе
 * по алгоритмам или в соответствующей статье Википедии.) Воспользуйтесь вспомогательным
 * классом Neighbor для хранения названия соседнего города и расстояния до него. Представьте
 * полученный граф в виде преобразования названий городов в множества соседних городов.
 * Воспользуйтесь в данном алгоритме классом PriorityQueue<Neighbor>.
 */
public class Ex0710_Dijkstra {
    public static class Town implements Comparable<Town> {

        private String name;
        private double distance;
        private boolean invited;
        private Map<Town, Double> neighbors;

        public Town(String name) {
            this.name = name;
            this.distance = Double.POSITIVE_INFINITY;
            this.invited = false;
            neighbors = new TreeMap<>();
        }

        public int compareTo(Town other) {
            int result = Double.compare(this.distance, other.distance);
            if (result == 0) result = Boolean.compare(this.invited, other.invited);
            if (result == 0) result = this.name.compareTo(other.name);

            return result;
        }

        public boolean equals (Object other) {
            if (other == this) return true;
            if ((other instanceof Town) == false) return false;
            if (((Town) other).name.compareTo(this.name) == 0) return true;
            return false;
        }

        public String toString() {
            Town t = this;
            StringBuilder sb = new StringBuilder();
            sb.append(t.getClass().getSimpleName()).append(": ");
            //sb.append("#").append(town_id).append("; ");
            sb.append(t.name).append("; ").append(t.distance).append("; invited=");
            sb.append(t.invited).append('\n');
            for (Map.Entry<Town, Double> entry : t.neighbors.entrySet()) {
                sb.append(entry.getKey().name)
                        .append("; ").append(entry.getValue()).append(';').append('\n');
            }
            return sb.toString();
        }


    }

    public static Set<Town> loadTowns (String path) throws FileNotFoundException {
    	
        File file = new File (path);
        Scanner sc = new Scanner(file);
        //sc.useLocale(Locale.ENGLISH);
        Map<String, Town> townMap = new TreeMap<>();
        //Set<String> townNames = new HashSet<>();
        Town town1 = null;
        Town town2 = null;
        String curStrTown;
        while (sc.hasNext()) {

            curStrTown = sc.next();
            town1 = townMap.putIfAbsent(curStrTown, new Town(curStrTown));
            town1 = townMap.get(curStrTown);

            curStrTown = sc.next();
            town2 = townMap.putIfAbsent(curStrTown, new Town(curStrTown));
            town2 = townMap.get(curStrTown);

            Double distance = sc.nextDouble();

            //System.out.println(town1.name + "-" + town2.name + "-" + distance);

            town1.neighbors.put(town2, distance);


            }
        Set<Town> result = new TreeSet<>();
        for (Map.Entry<String, Town> townTown : townMap.entrySet()) result.add(townTown.getValue());
        sc.close();

        return result;
    }

    private static Set<Town> calcShortestDistances (Set<Town> towns, String townStart, String townFinish)
            throws NoSuchElementException{

        Town start = null, finish = null;
        Iterator<Town> iter  = towns.iterator();
        while (iter.hasNext()) {
            Town ct = iter.next();
            if (ct.name.equals(townStart)) {start = ct; start.distance = 0;}
            if (ct.name.equals(townFinish)) finish = ct;
        }

        if (start == null || finish == null) throw new NoSuchElementException
                ("Start or finish towns are absent in towns' queue");

        for (int i=0; i<towns.size(); i++) {
            Town currentTown = Collections.min(towns,
                    (Town a, Town b) -> {
                        int result = Boolean.compare(a.invited, b.invited);
                        if (result == 0) result = Double.compare(a.distance, b.distance);
                        return result;
                    }); // получаем непосещенную вершину с минимальным расстоянием до нее
            for (Map.Entry<Town, Double> townNeighbor : currentTown.neighbors.entrySet()) {//цикл расчета и установки
                // расстояний до соседей
                if (townNeighbor.getKey().invited == true) {
                    continue;//если точка уже посещена, то перейти к следующему соседу
                }
                Double nextNeibhorDist = currentTown.distance + townNeighbor.getValue();//расчет расстояния до соседа
                if (nextNeibhorDist < townNeighbor.getKey().distance) {
                    townNeighbor.getKey().distance = nextNeibhorDist;//если из
                }
                // текущей точки до сосеней общее расстояние меньше чем установленное ранее, то установить меньшее
            }
            currentTown.invited = true;
            if (currentTown == finish) break; //если вершина - это конец пути, то прервать цикл

        }
        return towns;
    }


    public static TreeSet<Town> findShortestPath (String path, String start, String finish) throws FileNotFoundException {

        Set<Town> calcedTowns = calcShortestDistances(loadTowns(path), start, finish);
        Town startT = null, finishT = null;
        Iterator<Town> iter = calcedTowns.iterator();
        while (iter.hasNext()) {
            Town ct = iter.next();
            //ct.invited = false;
            if (ct.name.equals(start)) startT = ct;
            if (ct.name.equals(finish)) finishT = ct;
        }
        //System.out.println("TTT Start " + startT.name + "Finish " + finishT.name);

        TreeSet<Town> pathOverTowns = new TreeSet<>();
        //finishT.invited = true;
        pathOverTowns.add(finishT);

        Town currentTown = finishT;
        for(int i=0; i < calcedTowns.size(); i++) {
            //теперь нужно из соседей крайнего выбрать тех, у которых сумма их расстояния
            // от начала пути до него и расстояния от него до крайнего равны расстоянию крайнего

            ArrayList<Town> distEqualTown = new ArrayList<>();
            Town nextTownInPath = null;
            for (Map.Entry<Town, Double> nb : currentTown.neighbors.entrySet()){
                if ((nb.getKey().distance)+ nb.getValue() == currentTown.distance)
                    {distEqualTown.add(nb.getKey());} //разветвление не реализовано далее
            }
           nextTownInPath = distEqualTown.get(0);// берем 1-й, т.к.разветвление путей одинаковх по длине не реализовано
            pathOverTowns.add(nextTownInPath);// добавим этот "следующий" город в "путь"
            if (nextTownInPath == startT) break;//если "следующий" город это начальный,то закончить цикл
            currentTown = nextTownInPath; //устанавливаем текущим для обработки город следующий
        }

        Iterator<Town> townIterator = pathOverTowns.iterator();
        while(iter.hasNext()) {
            Town el = townIterator.next();
            System.out.println(el.toString());
        }
        return pathOverTowns;
    }

    public static Double minDistance (String filePath, String start, String finish)
            throws FileNotFoundException {
        TreeSet<Town> path = findShortestPath (filePath, start, finish);
        Double result = path.last().distance;
        return result;
    }

    public static String shortestPath (String filePath, String start, String finish)
            throws FileNotFoundException {
        TreeSet<Town> path = findShortestPath (filePath, start, finish);
        StringBuilder sb = new StringBuilder();
        for (Town town : path) sb.append(town.name).append("->");
        sb.append(" (").append(path.last().distance).append(")");
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
            String start = "6";
            String finish = "4";
            String filepath = "task/NetOfTowns.txt";
            Set<Town> loaded = loadTowns(filepath);
            Set<Town> calced = calcShortestDistances(loaded, start, finish);
            TreeSet <Town> pathed = findShortestPath(filepath, start, finish);
            Iterator<Town> iter2 = pathed.iterator();
            while (iter2.hasNext()) {
                Town ct = iter2.next();
                System.out.println(ct.toString());
            }
            System.out.println(shortestPath(filepath, start, finish));
            System.out.println(minDistance(filepath, start, finish));


    }
}
