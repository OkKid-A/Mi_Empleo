package org.cunoc.mi_empleo_api.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormateoDeFechas {

    public FormateoDeFechas() {
    }

    public String formateoStringAString(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(fecha);
        String fechaString = sdf.format(date);
        return fechaString;
    }

    public String  formatioDateAString(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = sdf.format(fecha);
        return fechaString;
    }

    public Date formateDateADate(Date fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = sdf.format(fecha);
        return sdf.parse(fechaString);
    }

    public Date formateStringToDate(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(fecha);
        return date;
    }

    public String  addDias(Date date, int dias){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, dias);
        return sdf.format(c.getTime());
    }


}
