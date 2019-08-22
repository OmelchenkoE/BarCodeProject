import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {
    private static final int MINCODESONTHEPAGE = 10;

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        JFrame frame = new JFrame("InputDialog");
        String name = JOptionPane.showInputDialog(
                frame,
                "Enter the text",
                "Enter the text",
                JOptionPane.WARNING_MESSAGE
        );
        String number = JOptionPane.showInputDialog(
                frame,
                "Enter the quantity",
                "Enter the quantity",
                JOptionPane.WARNING_MESSAGE
        );
        if (name.length() < 1 || number.length() < 1
                || name.equals("null") || name.equals("0")
                || number.equals("null") || number.equals("0")) {
            JOptionPane.showMessageDialog(null, "incorrect input");
        }
        proxiCreator(name, Integer.parseInt(number));
        System.exit(0);
    }

    private static void creator(String text, int numberCodesOnThePage, String NOfFile) throws FileNotFoundException, DocumentException {
        Document document = new Document(new Rectangle(595, 842));
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("template" + NOfFile + ".pdf"));
        document.open();
        document.add(new Paragraph("Barcode PDF417"));
        BarcodePDF417 pdf417 = new BarcodePDF417();
        pdf417.setErrorLevel(6);
        pdf417.setCodeColumns(2);
        pdf417.setCodeRows(5);
        pdf417.setText(text);
        Image img = pdf417.getImage();
        for (int i = 0; i < numberCodesOnThePage; i++) {
            document.add(img);
            document.add(new Phrase(text));
        }
        document.close();
    }

    private static void proxiCreator(String name, int numberCodesOnThePage) throws FileNotFoundException, DocumentException {
        int nLists = numberCodesOnThePage / MINCODESONTHEPAGE;
        int other = numberCodesOnThePage % MINCODESONTHEPAGE;
        if (numberCodesOnThePage > MINCODESONTHEPAGE) {
            for (int i = 0; i < nLists; i++) {
                creator(name, MINCODESONTHEPAGE, Integer.toString(i));
            }
        }
        creator(name, other, "");
    }
}
