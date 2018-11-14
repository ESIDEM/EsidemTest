package ng.com.techdepo.esidemtest.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converter {

    @TypeConverter
    public static Date fromTimestamp(Long value){
        return  value==null ? null: new Date(value);
    }

    @TypeConverter
    public static Long dateToTime(Date date){
        return date == null ? null: date.getTime();
    }
}
