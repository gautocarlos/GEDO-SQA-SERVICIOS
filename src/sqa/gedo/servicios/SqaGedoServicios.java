package sqa.gedo.servicios;

//import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class SqaGedoServicios {
	private SoapUITestCaseRunner runner = new SoapUITestCaseRunner();

	/**
	 * 
	 */
	String[] properties = null;

	@Before
	private void setUpSettings() {
		runner.setSettingsFile("config/soapui-settings.xml");
	}

	public void setUpEscenario01() throws Exception {
		runner.setProjectFile("proyectos_soapui/0000-AUT-002-soapui-project.xml");
	}

	/**
	 * * A partir de un archivo plano de properties se setean las mismas al
	 * runner del proyecto SoapUI a ejecutar
	 * 
	 * @throws Exception
	 **/
	public String[] parsearArchivoTextoAPropertiesProyectoSoap(String rutaArchivoProperty) throws Exception {
        ArrayList<String> lista = new ArrayList<String>();
		try {
			FileReader archivo = new FileReader(rutaArchivoProperty);
			BufferedReader buffer = new BufferedReader(archivo);

			String temp = buffer.readLine();
			Byte i;
			for (i = 0; temp != null; temp = buffer.readLine()) {
				lista.add(temp);
				i++;
			}
			buffer.close();
		} catch (Exception e) {
			throw e;
		}
		String[] projectProperties = lista.toArray(new String[lista.size()]);
		return projectProperties;
	}

	/**
	 * Se pasan por parámetro las varibles a considerar para el armado del
	 * request.
	 * 
	 * Parámetros: acronimoTipoDocumento, data, referencia, usuario, numero
	 */
	public void setUpEscenario02() throws Exception {
		runner.setProjectFile("proyectos_soapui/0000-AUT-004-soapui-project.xml");
		runner.setProjectProperties(
				parsearArchivoTextoAPropertiesProyectoSoap("properties/Properties_generarDocumentoGEDO.txt"));
	}

	@Given("^A partir de un acrónimo GEDO y un usuario con permisos de firma sobre el mismo$")
	public void a_partir_de_un_acronimo_GEDO_y_un_usuario_con_permisos_de_firma_sobre_el_mismo() throws Exception {
		this.setUpEscenario01();
		/**
		 * TODO Crear/Levantar y pasar propiedades de ejecución al proyecto
		 * SoapUI mediante un mapa
		 */
	}

	@When("^Realiza la invocación del servicio generarDocumento$")
	public void realiza_la_invocacion_del_servicio_generarDocumento() throws Exception {
		runner.run();
	}

	@Then("^Se genera un número de documento GDE$")
	/**
	 * Ejemplo simple implementando la clase SoapUITestCaseRunner
	 * (com.eviware.soapui.impl.wsdl.WsdlProject.WsdlProject) para procesar y
	 * ejecutar un proyecto SoapUI
	 */
	public void se_genera_un_numero_de_documento_GDE() throws Exception {
		/**
		 * TODO Levantar las propiedades del proyecto para obtener el número
		 * GEDO generado
		 */
	}

	@Given("^A partir de un acrónimo GEDO y un usuario sin permisos de firma sobre el mismo$")
	public void a_partir_de_un_acronimo_GEDO_y_un_usuario_sin_permisos_de_firma_sobre_el_mismo() throws Throwable {
		this.setUpEscenario02();
		/**
		 * TODO Crear/Levantar y pasar propiedades de ejecución al proyecto
		 * SoapUI mediante un mapa
		 */

	}

	@Then("^No se genera un número de documento GDE$")
	public void no_se_genera_un_numero_de_documento_GDE() throws Throwable {
		/**
		 * TODO Levantar las propiedades del proyecto para obtener el error que
		 * debería arrojar el servicio
		 */
	}

}