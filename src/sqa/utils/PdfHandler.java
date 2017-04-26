/**
 * 
 */
package sqa.utils;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Calendar;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.AcroFields.Item;

/**
 * @author cargauto
 *
 */
public class PdfHandler {

	/**
	 * 
	 */
	// public PdfHandler() {
	// // TODO Auto-generated constructor stub
	// }

	public static void main(String[] args) {
		try {
			PdfHandler.verifyPdf("pdf/ME-2017-00158123-APN-AFJP.pdf");
			PdfHandler.verifyPdfSignature("pdf/ME-2017-00158123-APN-AFJP.pdf");
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
	}

	public static final boolean verifyPdf(String pdfFile) throws IOException, DocumentException, Exception {
		KeyStore kall = PdfPKCS7.loadCacertsKeyStore();
		// KeyStore kall = PdfPKCS7.class.load;

		PdfReader reader = new PdfReader(pdfFile);
		AcroFields af = reader.getAcroFields();

		// Search of the whole signature
		ArrayList names = af.getSignatureNames();

		// For every signature :
		for (int k = 0; k < names.size(); ++k) {
			String name = (String) names.get(k);
			// Affichage du nom
			System.out.println("Signature name: " + name);
			System.out.println("Signature covers whole document: " + af.signatureCoversWholeDocument(name));
			// Affichage sur les revision - version
			System.out.println("Document revision: " + af.getRevision(name) + " of " + af.getTotalRevisions());
			// Debut de l'extraction de la "revision"
			FileOutputStream out = new FileOutputStream("pdf/" + af.getRevision(name) + ".pdf");
			byte bb[] = new byte[8192];
			InputStream ip = af.extractRevision(name);
			int n = 0;
			while ((n = ip.read(bb)) > 0)
				out.write(bb, 0, n);
			out.close();
			ip.close();
			// Fin extraction revision

//			PdfPKCS7 pk = af.verifySignature(name);
//			Calendar cal = pk.getSignDate();
//			Certificate pkc[] = pk.getCertificates();
			// Information about the certificat, le signataire
//			System.out.println("Subject: " + PdfPKCS7.getSubjectFields(pk.getSigningCertificate()));
			// Le document à t'il ete modifié ?
//			System.out.println("Document modified: " + !pk.verify());

			// Is the certificate avaible ? Be carefull we search the chain of
			// certificat
//			Object fails[] = PdfPKCS7.verifyCertificates(pkc, kall, null, cal);
//			if (fails == null)
//				System.out.println("Certificates verified against the KeyStore");
//			else
//				System.out.println("Certificate failed: " + fails[1]);
		}
		return true;
	}

    public static void verifyPdfSignature(String pdfFile) throws IOException {
        PdfReader reader = new PdfReader(pdfFile);
        AcroFields fields = reader.getAcroFields();
        Item item = fields.getFieldItem("signature_1");
        
        PdfDictionary widget = item.getWidget(0);
        PdfDictionary ap = widget.getAsDict(PdfName.AP);
        PdfStream normal = ap.getAsStream(PdfName.N);
        PdfDictionary resources = normal.getAsDict(PdfName.RESOURCES);
        PdfDictionary xobject = resources.getAsDict(PdfName.XOBJECT);
        PdfStream frm = xobject.getAsStream(PdfName.FRM);
        PdfDictionary res = frm.getAsDict(PdfName.RESOURCES);
        PdfDictionary xobj = res.getAsDict(PdfName.XOBJECT);
        PRStream n2 = (PRStream) xobj.getAsStream(PdfName.N2);
        byte[] stream = PdfReader.getStreamBytes(n2);
        System.out.println(new String(stream));
        System.out.println(item.toString());        
    }
	
}
