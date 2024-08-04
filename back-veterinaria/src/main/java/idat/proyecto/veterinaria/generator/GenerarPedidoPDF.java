package idat.proyecto.veterinaria.generator;

import static idat.proyecto.veterinaria.generator.Generales.formatFont;
import static idat.proyecto.veterinaria.generator.Generales.formatLocalDateTime;
import static idat.proyecto.veterinaria.generator.Generales.formatNumberCodigo;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import idat.proyecto.veterinaria.entity.Pedido;
import idat.proyecto.veterinaria.entity.PedidoProducto;

public class GenerarPedidoPDF {
	
private Pedido pedido;
    
    public GenerarPedidoPDF(Pedido pedido){
    	this.pedido = pedido;
    }
	
	public Resource generatePdfResource() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);
        designPDF(document);
        byte[] pdfContent = outputStream.toByteArray();
        return new ByteArrayResource(pdfContent);
    }
	
    protected void designPDF(Document document) throws Exception {
    	
    	URL logoURL = getClass().getResource("/imgs/LOGO_VET_RECTANGULAR.png");
    	URL noIMG = getClass().getResource("/imgs/no-img.png");
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
	    PdfPTable header = new PdfPTable(3);
	    header.setWidthPercentage(100);
	    header.setSpacingBefore(10);
	    header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

	    Image logo = Image.getInstance(logoURL);
	    logo.scaleToFit(50, 50);
	    logo.setAlignment(Element.ALIGN_LEFT);
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

	    cell = new PdfPCell();
	    cell.setBorder(Rectangle.NO_BORDER);

	    Paragraph orden = new Paragraph();
	    orden.setLeading(20, 1);
	    orden.add(new Phrase("ORDEN NUMERO    " + formatNumberCodigo(pedido.getId(), 8), superBoldTextFont));
	    orden.setSpacingAfter(10);
	    orden.setAlignment(Element.ALIGN_RIGHT);

	    Paragraph fecha = new Paragraph();
	    fecha.add(new Phrase(formatLocalDateTime(pedido.getFecha_emision(),0,6,6,6), normalTextFont));
	    fecha.setAlignment(Element.ALIGN_RIGHT);

	    cell.addElement(orden);
	    cell.addElement(fecha);
	    header.addCell(cell);

	    
	    // Agregamos la data
	    
	    PdfPTable data = new PdfPTable(5);
	    data.setWidthPercentage(100);
	    data.setSpacingAfter(10);
	    
	    cell = new PdfPCell();
	    cell.setColspan(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    cell.addElement(new Paragraph("DATOS DEL USUARIO", boldTextFont));
	    cell.addElement(new Paragraph("Nombre:    " + pedido.getUsuario().getUsername(), normalTextFont));
	    cell.addElement(new Paragraph("Correo:      " + pedido.getUsuario().getCorreo(), normalTextFont));
	    cell.addElement(new Paragraph("Direccion envio:    Local de Jose Galvez ", normalTextFont));
	    data.addCell(cell);

	    cell = new PdfPCell();
	    cell.setColspan(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.addElement(new Paragraph("DATOS DEL PROVEEDOR", boldTextFont));
	    cell.addElement(new Paragraph("Nombre:      " + pedido.getProveedor().getNombre(), normalTextFont));
	    cell.addElement(new Paragraph("Celular:       " + pedido.getProveedor().getCelular(), normalTextFont));
	    cell.addElement(new Paragraph("Correo:        " + pedido.getProveedor().getCorreo(), normalTextFont));
	    data.addCell(cell);
	    
	    String imageUrl = pedido.getProveedor().getFoto();
	    Image image;
	    try {
	        image = Image.getInstance(imageUrl);
	    } catch (Exception e) {
	        image = Image.getInstance(noIMG);
	    }
	    image.scaleAbsolute(70, 70);

	    cell = new PdfPCell();
	    cell.setBorder(Rectangle.NO_BORDER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setPadding(0);

	    Paragraph space = new Paragraph("\n");
	    space.setLeading(8);
	    cell.addElement(space);

	    cell.addElement(image);
	    data.addCell(cell);

	    // Agregamos los detalles Productos
	    
		PdfPTable detallesProductos = new PdfPTable(7);
		detallesProductos.setWidthPercentage(100);
		detallesProductos.setSpacingBefore(10);
		detallesProductos.setSpacingAfter(10);

		String[] titles = {"Item", "Articulo", "Marca", "Unidad Medida", "Cantidad", "Precio", "Importe"};
		for (String title : titles) {
		    cell = new PdfPCell();
		    cell.setBorder(Rectangle.BOTTOM);
		    cell.addElement(new Paragraph(title, columnTextFont));
		    detallesProductos.addCell(cell);
		}
		
		int item = 1;
		for (PedidoProducto dp : pedido.getDetalle_producto()) {
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(String.valueOf(item), cellTextFont));
			detallesProductos.addCell(cell);
		    
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(dp.getProducto().getNombre(), cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(dp.getProducto().getMarca(), cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(dp.getProducto().getUnidad_medida(), cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph(dp.getCantidad() + "  uni.", cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.addElement(new Paragraph("S/.  " + dp.getPrecio_compra(), cellTextFont));
			detallesProductos.addCell(cell);
			
			cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
		    cell.addElement(new Paragraph("S/.  " + dp.getImporte(), cellTextFont));
		    detallesProductos.addCell(cell);
		    
		    item++;
		}

	    // Agregamos las observaciones 
		
  		PdfPTable observations = new PdfPTable(1);
  		observations.setWidthPercentage(100);
  		observations.setSpacingAfter(10);
  		
  		String mensaje = pedido.getMensaje() == null ? "Estimado proveedor, nos gustaría solicitar la adquisición los productos mencionados en la orden. "
  				+ "Por favor, revise la lista adjunta de productos y cantidades y confirme si puede cumplir con esta solicitud. "
  				+ "Esperamos su respuesta. Atentamente, Santos Cachorros" : pedido.getMensaje();

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
 	    cell.addElement(new Paragraph("Descuento (%):        0 %", normalTextFont));
 	    cell.addElement(new Paragraph("Descuento Neto:     S/.0.00", normalTextFont));
 	    costs.addCell(cell);

 	    cell = new PdfPCell();
 	    cell.setBorder(Rectangle.NO_BORDER);
 	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	    cell.addElement(new Paragraph("Estado:             " + pedido.getEstado().toString().toUpperCase(), normalTextFont));
 	    cell.addElement(new Paragraph("Total:                S/." + pedido.getTotal(), normalTextFont));
 	    costs.addCell(cell);
	    
		
		// Agregamos todo al documento
	    
	    document.open();
		document.add(header);
		document.add(separator);
		document.add(data);
		document.add(separator);
		document.add(detallesProductos);
		document.add(separator);
		document.add(observations);
		document.add(separator);
		document.add(costs);
		document.add(separator);
		document.close();
    }
}
