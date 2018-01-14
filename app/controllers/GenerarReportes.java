package controllers;
import play.*;
import play.libs.Json;
import play.mvc.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import views.html.paginaBlank;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
 
import models.*;
import controllers.*;
public class GenerarReportes extends Controller {
	
	public static Result index(Long idPrueba) {
	      return ok((createPdf(idPrueba))).as("application/pdf");
	}
	
	public static final Font[] FONT = new Font[4];
    static {
        FONT[0] = new Font(FontFamily.HELVETICA, 24);
        FONT[1] = new Font(FontFamily.HELVETICA, 18);
        FONT[2] = new Font(FontFamily.HELVETICA, 14);
        FONT[3] = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    }
	 public static File createPdf(Long idPrueba) {
		 File file = new File("itext-test.pdf");
		 String [] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
				 "K", "L", "M","N","O","P","Q","R","S","T","U","V","W", "X","Y","Z" };
		 //List<Alternativa> alternativa=Alternativa.getAlternativaXpregunta(Long idPregunta);
		 try {
				
				FileOutputStream fileout = new FileOutputStream(file);
				Document document = new Document();
				PdfWriter writer =PdfWriter.getInstance(document, fileout);
				
		        writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
		        
		        // step 3
		        document.open();
		        int epoch = -1;
		        int count = 1;
		        Paragraph title = null;
		        Chapter chapter =null;
		        Section section = null;
		        Section subsection = null;
		        List<Pregunta> preguntas=Pregunta.getPreguntasXprueba(idPrueba);
		        for (Pregunta pregunta : preguntas) {
		        	//title = new Paragraph(String.format("The year %d", 1), FONT[0]);
		        	chapter = new Chapter(" "+pregunta.pregunta, count);
		        	count++;
		        	//title = new Paragraph(pregunta.pregunta, FONT[2]);
		        	int idpreg=safeLongToInt(pregunta.idPregunta);
		        	Section section1 = chapter.addSection(title); 
		        	section =section1; 
		        	section.setBookmarkTitle(String.valueOf(1));
		        	
	                section.setIndentation(30);
	                section.setBookmarkOpen(false);
	                List<Alternativa> alternativas=Alternativa.getAlternativaXpregunta(pregunta.idPregunta);
	                int altrn =0;
	                for (Alternativa alternativa : alternativas) {
	                	subsection = chapter.addSection(""); 
	                	subsection.setNumberDepth(0);
	                	subsection.add(new Paragraph(abecedario[altrn]+") "+alternativa.alternativaTxt, FONT[3]));
	                	altrn++;
	                }
	                document.add(chapter);
		        }  
		        
		        
		        
		        document.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		 return file;
	}
	 public static int safeLongToInt(long l) {
		    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
		        throw new IllegalArgumentException
		            (l + " cannot be cast to int without changing its value.");
		    }
		    return (int) l;
		}

}
