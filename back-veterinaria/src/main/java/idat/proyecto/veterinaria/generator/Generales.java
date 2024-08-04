package idat.proyecto.veterinaria.generator;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class Generales {
	
	public static String formatLocalDateTime(LocalDateTime dateTime, int spacesBeforeFecha, int spacesAfterFecha, int spacesBeforeHora, int spacesAfterHora) {
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    String formattedDate = dateTime.format(dateFormatter);
	    String formattedTime = dateTime.format(timeFormatter);
	    return " ".repeat(spacesBeforeFecha) + "Fecha:" + " ".repeat(spacesAfterFecha)+ formattedDate + " ".repeat(spacesBeforeHora) + "Hora:" + " ".repeat(spacesAfterHora) + formattedTime;
	}

    public static Font formatFont(URL urlFont, Integer size) {
    	try {
			BaseFont baseFont = BaseFont.createFont(urlFont.toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(baseFont, size);
			return font;
		} catch (Exception e) {
			return null;
		}	
    }
    
    public static String formatNumberCodigo(int number, int length) {
        String numberString = Integer.toString(number);
        int paddingLength = length - numberString.length() - 1;
        String padding = "";
        for (int i = 0; i < paddingLength; i++) {
            padding += "0";
        }
        return "N" + padding + numberString;
    }

    public static String formatNumberSoles(Double number) {
        RuleBasedNumberFormat formatter = new RuleBasedNumberFormat(ULocale.forLanguageTag("es"), RuleBasedNumberFormat.SPELLOUT);
        String result = formatter.format(number) + " soles";
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }
}
