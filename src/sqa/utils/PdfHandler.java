/**
 * 
 */
package sqa.utils;

import java.io.*;
import java.util.ArrayList;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/**
 * @author cargauto
 *
 */
public class PdfHandler {
	/**
	 * @param pdfFile
	 *            - Archivo PDF firmado
	 * @param signatories
	 *            - Cantidad de firmantes
	 * @return boolean
	 * @throws IOException
	 * @throws DocumentException
	 * @throws Exception
	 */
	public static final boolean verifyPdfSignatures(String pdfFile, int signatories)
			throws IOException, DocumentException, Exception {
		PdfReader reader = new PdfReader(pdfFile);
		AcroFields af = reader.getAcroFields();
		int closeSignature = 0;
		int signature = 0;
		// Buscar todos los espacios de firma
		ArrayList names = af.getSignatureNames();

		if ((signatories + 1) == names.size()) {
			for (int k = 0; k < names.size(); ++k) {
				String name = (String) names.get(k);
				System.out.println("Nombre de campo de firma: " + name);
				System.out.println("¿Es firma de cierre?: " + af.signatureCoversWholeDocument(name));
				System.out.println("Revision de documento: " + af.getRevision(name) + " de " + af.getTotalRevisions());
				if (!af.signatureCoversWholeDocument(name)) {
					signature++;
				} else {
					closeSignature++;
				}
			}
			// Si existe más de una firma de cierre o menos firmas qe firmantes
			// esperados se genera una excepción
			if ((closeSignature > 1) || (signature < signatories)) {
				throw new Exception(
						"La cantidad de firmantes no coincide con los espacios de firma y cierre esperados");
			}
		} else {
			throw new Exception("La cantidad de firmantes no coincide con los espacios de firma y cierre adecuados"
					+ "Espacios de firma esperados con el cierre incluído: " + (signatories + 1) + " contenidos en el documento: "
					+ names.size() + ".");
		}
		return true;
	}
}
