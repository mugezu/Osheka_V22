package Logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Hashtable;

public class FillingFile {

    private static Hashtable<Integer, String> poli = new Hashtable<Integer, String>();

    public static void main(String[] args) {
        FillingFile f = new FillingFile();
        f.addTable();
        f.writeFile();
    }


    public void addTable() {
        poli.put(2, "03");
        poli.put(3, "032");
        poli.put(4, "0135");
        poli.put(8, "00013015");
        poli.put(16, "0003000001000053");
        poli.put(32, "00000000030001000000000000000053");
        poli.put(64, "0000000000000000030000000000000000000000000000000000000000100053");
    }

    public void writeFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(poli);
        FileWriter FW = null;
        try {
            FW = new FileWriter(new File("").getAbsolutePath() + "\\GF7.txt", false);
            FW.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (FW != null)
                    FW.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Hashtable<Integer, String> readFile() throws IOException {
        FileReader FR;
        StringBuffer json = new StringBuffer();

        FR = new FileReader(new File("GF7.txt").getAbsolutePath());
        int c;
        while ((c = FR.read()) != -1) {
            json.append((char) c);
        }
        FR.close();
        System.out.println(json);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type type = new TypeToken<Hashtable<Integer, String>>() {
        }.getType();
        poli = null;
        poli = gson.fromJson(json.toString(), type);
        return poli;
    }
}
