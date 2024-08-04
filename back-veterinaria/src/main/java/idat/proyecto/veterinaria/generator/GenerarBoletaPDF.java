package idat.proyecto.veterinaria.generator;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import idat.proyecto.veterinaria.entity.Boleta;
import idat.proyecto.veterinaria.entity.BoletaProducto;

import static idat.proyecto.veterinaria.generator.Generales.formatLocalDateTime;
import static idat.proyecto.veterinaria.generator.Generales.formatFont;
import static idat.proyecto.veterinaria.generator.Generales.formatNumberCodigo;
import static idat.proyecto.veterinaria.generator.Generales.formatNumberSoles;


public class GenerarBoletaPDF{

    private Boleta boleta;
    
    public GenerarBoletaPDF(Boleta boleta){
    	this.boleta = boleta;
    }
	
	public Resource generatePdfResource() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        designPDF(document);
        byte[] pdfContent = outputStream.toByteArray();
        return new ByteArrayResource(pdfContent);
    }
	
    protected void designPDF(Document document) throws Exception {
    	
    	URL logoURL = getClass().getResource("/imgs/LOGO_VET_RECTANGULAR.png");
    	URL QR_URL = getClass().getResource("/imgs/qr-code.png");
    	URL rucFontURL = getClass().getResource("/fonts/AlfaSlabOne-Regular.ttf");
    	URL subTextFontURL = getClass().getResource("/fonts/FjallaOne-Regular.ttf");
    	URL normalTextFontURL = getClass().getResource("/fonts/BreeSerif-Regular.ttf");
    	URL columnTextFontURL = getClass().getResource("/fonts/Staatliches-Regular.ttf");
    	URL cellTextFontURL = getClass().getResource("/fonts/Vollkorn-Regular.ttf");
    	URL boldTextFontURL = getClass().getResource("/fonts/PassionOne-Regular.ttf");
    	
	    // Configuracion de fuentes

    	Font superBoldTextFont = formatFont(rucFontURL, 12);
    	Font subTextFont = formatFont(subTextFontURL, 12);
    	Font normalTextFont = formatFont(normalTextFontURL, 12);
    	Font columnTextFont = formatFont(columnTextFontURL, 12);
    	Font cellTextFont = formatFont(cellTextFontURL, 12);
    	Font boldTextFont = formatFont(boldTextFontURL, 14);
	    
    	// Agregamos una celda general
    	
	    PdfPCell cell;
	    
	    // Agregamos un separador general
	    
	    LineSeparator separator = new LineSeparator();
	    separator.setLineColor(BaseColor.BLACK);

	    // Agregamos el header
	    
	    PdfPTable header = new PdfPTable(2);
	    header.setWidthPercentage(100);
	    header.setSpacingBefore(10);
	    header.getDefaultCell().setBorder(Rectangle.NO_BORDER); 
	    
	    Image logo = Image.getInstance(logoURL);
	    logo.scaleToFit(50, 50);
	    logo.setAlignment(Element.ALIGN_CENTER);
	    header.addCell(logo);
	    
	    cell = new PdfPCell();
	    cell.setBorder(Rectangle.NO_BORDER);
	    
	    Paragraph ruc = new Paragraph();
	    ruc.setLeading(20, 1);
	    ruc.add(new Phrase("R.U.C. N° 1798285937001", superBoldTextFont));
	    ruc.setSpacingAfter(10);
	    ruc.setAlignment(Element.ALIGN_CENTER);

	    Paragraph ubicacion = new Paragraph();
	    ubicacion.add(new Phrase("V.M.T. Jose Galvez Pdr. 10.5", subTextFont));
	    ubicacion.setAlignment(Element.ALIGN_CENTER);
	    
	    Paragraph telefono = new Paragraph();
	    telefono.add(new Phrase("Teléfono: 01-2345678", subTextFont));
	    telefono.setAlignment(Element.ALIGN_CENTER);  
	    
	    cell.addElement(ruc);
	    cell.addElement(ubicacion);
	    cell.addElement(telefono);
	    header.addCell(cell);
	    
	    // Agregamos la data
	    
	    PdfPTable data = new PdfPTable(2);
	    data.setWidthPercentage(100);
	    data.setSpacingAfter(10);

	    cell = new PdfPCell();
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.addElement(new Paragraph("Cliente:         " + boleta.getCliente().getNombre() + " " + boleta.getCliente().getApellidos(), normalTextFont));
	    cell.addElement(new Paragraph("Domicilio:    " + boleta.getCliente().getDireccion(), normalTextFont));
	    data.addCell(cell);

	    cell = new PdfPCell();
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    cell.addElement(new Paragraph("Numero de Boleta:         " + formatNumberCodigo(boleta.getId(), 8), normalTextFont));
	    cell.addElement(new Paragraph(formatLocalDateTime(boleta.getFecha_creacion(),0,4,6,4), normalTextFont));
	    data.addCell(cell);

	    // Agregamos los detalles Productos
	    
		PdfPTable detallesProductos = new PdfPTable(4);
		detallesProductos.setWidthPercentage(100);
		detallesProductos.setSpacingBefore(10);
		detallesProductos.setSpacingAfter(10);

		String[] titles = {"Producto", "Cantidad", "Precio", "Total"};
		for (String title : titles) {
		    cell = new PdfPCell();
		    cell.setBorder(Rectangle.BOTTOM);
		    cell.addElement(new Paragraph(title, columnTextFont));
		    detallesProductos.addCell(cell);
		}
		
		for (BoletaProducto dp : boleta.getDetalle_producto()) {
		    
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(dp.getProducto().getNombre(), cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(dp.getCantidad() + "  uni.", cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph("S/.  " + dp.getPrecio(), cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
		    cell.addElement(new Paragraph("S/.  " + dp.getTotal(), cellTextFont));
		    detallesProductos.addCell(cell);         
		}
		

		
		// Agregamos voucher
		
		PdfPTable voucher = new PdfPTable(4);
		voucher.setWidthPercentage(100);
		voucher.setSpacingAfter(10);
		header.getDefaultCell().setBorder(Rectangle.BOX); 

	    cell = new PdfPCell();
	    cell.setPaddingLeft(10);
	    cell.addElement(new Paragraph("IMPORTE EN LETRAS", boldTextFont));
 	    cell.addElement(new Paragraph(formatNumberSoles(boleta.getPrecio_final()),normalTextFont));
 	    cell.addElement(new Paragraph("\nTIPO DE PAGO", boldTextFont));
	    cell.addElement(new Paragraph(boleta.getTipo_pago(),normalTextFont));
	    cell.setColspan(3);

	    Image logoQR = Image.getInstance(QR_URL);
	    logoQR.scaleToFit(50, 50);
	    logoQR.setAlignment(Element.ALIGN_CENTER);
	    
	    voucher.addCell(cell);
	    voucher.addCell(logoQR);
	    
	    // Agregamos las observaciones 
		
  		PdfPTable observations = new PdfPTable(1);
  		observations.setWidthPercentage(100);
  		observations.setSpacingAfter(10);
  		
  		String mensaje = boleta.getMensaje() == null ? "No hay ninguna observacion relevante para esta boleta, ha sido emitida correctamente" : boleta.getMensaje();

  	    cell = new PdfPCell();
  	    cell.setBorder(Rectangle.NO_BORDER);
  	    cell.addElement(new Paragraph("OBSERVACIONES", boldTextFont));
  	    cell.addElement(new Paragraph(mensaje, cellTextFont));
  	    observations.addCell(cell);
	    
	    // Agregamos los costos 
		
 		PdfPTable costs = new PdfPTable(2);
 		costs.setWidthPercentage(100);
 		costs.setSpacingAfter(10);

 	    cell = new PdfPCell();
 	    cell.setBorder(Rectangle.NO_BORDER);
 	    cell.addElement(new Paragraph("Sub Total:    S/." + boleta.getSub_total(), normalTextFont));
 	    cell.addElement(new Paragraph("IGV:                 S/." + boleta.getIgv(), normalTextFont));
 	    costs.addCell(cell);

 	    cell = new PdfPCell();
 	    cell.setBorder(Rectangle.NO_BORDER);
 	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	    cell.addElement(new Paragraph("Estado:               " + boleta.getEstado().toUpperCase(), normalTextFont));
 	    cell.addElement(new Paragraph("Precio Final:    S/." + boleta.getPrecio_final(), normalTextFont));
 	    costs.addCell(cell);
	    
		
		// Agregamos todo al documento
	    
	    document.open();
		document.add(header);
		document.add(separator);
		document.add(data);
		document.add(separator);
		document.add(detallesProductos);
		document.add(voucher);
		document.add(separator);
		document.add(observations);
		document.add(separator);
		document.add(costs);
		document.add(separator);
		document.close();
    }
    
}
