package Ex1;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;


public class Functions_GUI implements functions {
    private LinkedList<function> list;
    @Override
    public void initFromFile(String file) throws IOException {
        String line = "";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null)
            {
                String[] stringOfFunction = line.split("\n");
                for(int i=0; i<stringOfFunction.length;i++){
                    ComplexFunction f= new ComplexFunction();
                   list.add(f.initFromString(stringOfFunction[i])) ;

                }

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("could not read file");
        }


    }

    @Override
    public void saveToFile(String file) throws IOException {
        String newFile="";
        Iterator<function> it = list.iterator();
        while(it.hasNext()){
            function f = it.next();
            newFile+=f+"\n";
        }

        try
        {
            PrintWriter pw = new PrintWriter(new File(file));

            pw.write(newFile);
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
// rescale the coordinate system
        StdDraw.setXscale(rx.get_min(), rx.get_max());
        StdDraw.setYscale(ry.get_min(), ry.get_max());


    }


    @Override
    public void drawFunctions(String json_file) {

    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<function> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(function function) {
        return list.add(function);
    }

    @Override
    public boolean remove(Object o) { return list.remove(o); }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends function> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
list.clear();
    }
}
