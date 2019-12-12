package Ex1;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Font;
import com.google.gson.*;



public class Functions_GUI implements functions {

    private LinkedList<function> list = new LinkedList<>();
    public static Color[] Colors = {Color.blue, Color.cyan,
            Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};

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
            br.close();
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
        StdDraw.setCanvasSize(width, height);

        // rescale the coordinate system
        StdDraw.setXscale(rx.get_min(), rx.get_max());
        StdDraw.setYscale(ry.get_min(), ry.get_max());

        //////// vertical lines
        StdDraw.setPenColor(Color.LIGHT_GRAY);
        for (int i = (int) Math.floor(rx.get_min()); i <= rx.get_max(); i++) {
            StdDraw.line(i, ry.get_min(), i, ry.get_max());
        }

        //////// horizontal lines
        for (int i = (int) Math.floor(ry.get_min()); i <= ry.get_max(); i++) {
            StdDraw.line(rx.get_min(), i, rx.get_max(), i);
        }

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.black);
        StdDraw.line(rx.get_min(),0,rx.get_max(),0);
        StdDraw.line(0,ry.get_min(),0,ry.get_max());


        //////// x axis
        StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
        StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
        for (double i = rx.get_min(); i <= rx.get_max(); i = i + 1) {
            StdDraw.text(i , -0.5, Double.toString(i));
        }

        //////// y axis
        StdDraw.line(0, ry.get_min(), 0, ry.get_max());
        for (double i = ry.get_min(); i <= ry.get_max(); i = i + 1) {
            StdDraw.text( - 0.5, i , Double.toString(i));
        }

    // plot the approximation to the function
        double eps=(rx.get_max()-rx.get_min())/resolution;
        for(function f:list) {
            int color = ((int)(Math.random()*7));
            StdDraw.setPenColor(Colors[color]);
            StdDraw.setPenRadius(0.005);
             for (double i = rx.get_min(); i < rx.get_max(); i = i + eps) {
                     StdDraw.line(i, f.f(i), i + eps, f.f(i + eps));
                 }
       }
    }

    public void drawFunctions(){
        drawFunctions(800, 800, new Range(-10,10), new Range (-10,10),400 );

    }
    @Override
    public void drawFunctions(String json_file)  {
        // Make JSON!!
        Gson gson = new Gson();
        // Read from file
        try {
            FileReader fr = new FileReader(json_file);
            DF_params parameters = gson.fromJson(fr, DF_params.class);
            Range rx = new Range(parameters.Range_X[0], parameters.Range_X[1]);
            Range ry = new Range(parameters.Range_Y[0], parameters.Range_Y[1]);
            drawFunctions(parameters.Width, parameters.Height, rx, ry, parameters.Resolution);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }



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
    public boolean add(function f) {
        return list.add(f);
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
    public function get(int index){
       return list.get(index);
    }
}
