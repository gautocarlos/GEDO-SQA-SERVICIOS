package sqa.gedo.servicios;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sqa.utils.ProjectCustomPropertiesMatcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class SqaGedoServicios {
	private SoapUITestCaseRunner proyectoSoapUI;
	private ProjectCustomPropertiesMatcher projectCustomPropertiesMatcher;
	/**
	 * TODO Eliminar?
	 */
	String[] properties = null;

	/**
	 * Constructor
	 */
	public SqaGedoServicios() {
		proyectoSoapUI = new SoapUITestCaseRunner();
		projectCustomPropertiesMatcher = new ProjectCustomPropertiesMatcher();
	}

	public ProjectCustomPropertiesMatcher getProjectCustomPropertiesMatcher() {
		return projectCustomPropertiesMatcher;
	}

	public void setProjectCustomPropertiesMatcher(ProjectCustomPropertiesMatcher projectCustomPropertiesMatcher) {
		this.projectCustomPropertiesMatcher = projectCustomPropertiesMatcher;
	}

	@Before
	public void setUpSettings() {
		proyectoSoapUI.setSettingsFile("configuraciones/soapui-settings.xml");
	}

	/**
	 * Parser de archivo de texto plano a array de String compatible con lo que
	 * precisa el SoapUITestCaseRunner
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
	 * Se pasa por parámetro las varibles a considerar para el armado del
	 * request.
	 * 
	 * @param projectFile
	 *            - archivo .xml del proyecto soap a ejecutar
	 * @param customProperties
	 *            - archivo de texto plano de custom properties creadas para la
	 *            ejecución del proyecto
	 */
	public void setUpEscenario(String projectFile, String customProperties) throws Exception {
		proyectoSoapUI.setProjectFile(projectFile);
		proyectoSoapUI.setProjectProperties(parsearArchivoTextoAPropertiesProyectoSoap(customProperties));
	}

	@Given("^A partir de un acrónimo GEDO, un usuario destino y un usuario con permisos de inicio de documento sobre el mismo$")
	public void a_partir_de_un_acronimo_GEDO_y_un_usuario_sin_permisos_de_firma_sobre_el_mismo() throws Throwable {
		// this.setUpEscenario("proyectos_soapui/0000-AUT-004-soapui-project.xml",
		// "properties/Properties_generarDocumentoGEDO.txt");
		String project = getProjectCustomPropertiesMatcher().getProject(1);
		String customProperties = getProjectCustomPropertiesMatcher().getCustomProperties(1);
		setUpEscenario(project, customProperties);

	}

	@When("^Realiza la invocación del servicio generarTarea$")
	public void realiza_la_invocacion_del_servicio_generarTarea() throws Exception {
		proyectoSoapUI.run();
	}

	@Then("^Se genera una tarea de confección de documento al usuario destino$")
	public void no_se_genera_un_numero_de_documento_GDE() throws Throwable {
		/**
		 * TODO Levantar las propiedades del proyecto para obtener el error que
		 * debería arrojar el servicio
		 */
	}

}