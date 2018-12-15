package figuitosinc.pocketchef.database;

import android.arch.persistence.room.TypeConverter;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {


    @TypeConverter
    public static List<String> stringToStringList(String data) {
        if (data == null) {
            return (null);
        }

        StringReader reader = new StringReader(data);
        JsonReader json = new JsonReader(reader);
        List<String> result = new ArrayList<>();

        try {
            json.beginArray();

            while (json.hasNext()) {
                result.add(json.nextString());
            }

            json.endArray();
        } catch (IOException e) {
        }

        return (result);
    }

    @TypeConverter
    public static String StringListToString(List<String> data) {
        if (data == null)
            return (null);

        StringWriter result = new StringWriter();
        JsonWriter json = new JsonWriter(result);

        try {
            json.beginArray();

            for (String s : data) {
                json.value(s);
            }

            json.endArray();
            json.close();
        } catch (IOException e) {
        }
        return (result.toString());
    }
}