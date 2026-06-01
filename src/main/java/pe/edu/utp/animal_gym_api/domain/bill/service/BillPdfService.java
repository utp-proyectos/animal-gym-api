package pe.edu.utp.animal_gym_api.domain.bill.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;

@Service
public class BillPdfService {

	public byte[] generatePdf(BillResponseDTO bill) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(new Rectangle(227, 800), 10, 10, 10, 10);
		PdfWriter.getInstance(document, baos);
		document.open();

		// Encabezado
		document.add(p("ANIMAL GYM", Element.ALIGN_CENTER));
		document.add(p("Santa Clara, Asoc Hijos de Apurímac Mz c la 21", Element.ALIGN_CENTER));
		document.add(p("ATE - LIMA - LIMA", Element.ALIGN_CENTER));
		document.add(p("Teléfono: 954617578", Element.ALIGN_CENTER));
		document.add(p("-".repeat(34)));

		// Boleta
		document.add(p("BOLETA DE VENTA ELECTRONICA", Element.ALIGN_CENTER));
		document.add(p("B-" + String.format("%06d", bill.getId()), Element.ALIGN_CENTER));
		document.add(p("-".repeat(34)));

		// Socio
		document.add(p("Socio: " + (bill.getPartnerFirstName() + " " +
				bill.getPartnerLastName()).toUpperCase()));
		document.add(p("-".repeat(34)));

		// Empleado
		document.add(p("Atendido por: " + bill.getEmployeeFirstName() +
				" " + bill.getEmployeeLastName()));
		document.add(p("-".repeat(34)));

		document.add(p("Fecha: " + bill.getIssueDate()));
		document.add(p("Hora: " + bill.getTime()));
		document.add(p("-".repeat(34)));

		// Tabla conceptos
		PdfPTable table1 = new PdfPTable(3);
		table1.setWidthPercentage(100);
		table1.setWidths(new float[] { 3, 1, 2 });

		table1.addCell(cell("Concepto", Element.ALIGN_LEFT));
		table1.addCell(cell("Cant.", Element.ALIGN_CENTER));
		table1.addCell(cell("Subtotal", Element.ALIGN_RIGHT));

		table1.addCell(cell(bill.getMembershipName(), Element.ALIGN_LEFT));
		table1.addCell(cell("1", Element.ALIGN_CENTER));
		table1.addCell(cell(String.format("S/ %.2f", bill.getSubTotal()), Element.ALIGN_RIGHT));

		document.add(table1);
		document.add(p("-".repeat(34)));

		// Tabla totales
		PdfPTable table2 = new PdfPTable(2);
		table2.setWidthPercentage(100);
		table2.setWidths(new float[] { 2, 1 });

		table2.addCell(cell("GRAVADO:", Element.ALIGN_LEFT));
		table2.addCell(cell(String.format("S/ %.2f", bill.getSubTotal()), Element.ALIGN_RIGHT));

		table2.addCell(cell("I.G.V (18%):", Element.ALIGN_LEFT));
		table2.addCell(cell(String.format("S/ %.2f", bill.getIgv()), Element.ALIGN_RIGHT));

		table2.addCell(cell("IMPORTE TOTAL:", Element.ALIGN_LEFT));
		table2.addCell(cell(String.format("S/ %.2f", bill.getTotalPrice()), Element.ALIGN_RIGHT));

		document.add(table2);
		document.add(p("-".repeat(34)));

		document.add(p("GRACIAS POR SU PREFERENCIA", Element.ALIGN_CENTER));
		document.add(p("-".repeat(34)));

		document.close();
		return baos.toByteArray();
	}

	private Paragraph p(String txt) {
		Font font = new Font(Font.FontFamily.COURIER, 10);
		return new Paragraph(txt, font);
	}

	private Paragraph p(String txt, int alignment) {
		Font font = new Font(Font.FontFamily.COURIER, 10);
		Paragraph paragraph = new Paragraph(txt, font);
		paragraph.setAlignment(alignment);
		return paragraph;
	}

	private PdfPCell cell(String text, int alignment) {
		Font font = new Font(Font.FontFamily.COURIER, 10);
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(alignment);
		return cell;
	}
}
