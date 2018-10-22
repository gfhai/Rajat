package com.iprimed.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.iprimed.model.Customer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfManager {

	//creating customer welcome PDF
	public String registrationWelcomePdf(Customer customer) {
		Document document  = new Document();
		//path where pdf will be created and pdf name will be customer emailId
		String fileName = "D:\\Prodapt PDF\\Registration\\" + customer.getEmail() + ".pdf";
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			document.add(new Paragraph("Welcome "+customer.getFirstName()));
			document.close();
			System.out.println("PDF Created");
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
}
