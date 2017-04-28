package sqa.gedo.servicios;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sqa.utils.ConstantesServicios;
import sqa.utils.ProjectCustomPropertiesMatcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class SqaGedoServicios implements ConstantesServicios {
	private SoapUITestCaseRunner proyectoSoapUI;
	private ProjectCustomPropertiesMatcher projectCustomPropertiesMatcher;

	/**
	 * Constructor
	 */
	public SqaGedoServicios() {
		setProyectoSoapUI(new SoapUITestCaseRunner());
		setProjectCustomPropertiesMatcher(new ProjectCustomPropertiesMatcher());
		getProyectoSoapUI().setOutputFolder(RUTALOGS);
	}

	/**
	 * @return the proyectoSoapUI
	 */
	public SoapUITestCaseRunner getProyectoSoapUI() {
		return proyectoSoapUI;
	}

	/**
	 * @param proyectoSoapUI
	 *            the proyectoSoapUI to set
	 */
	public void setProyectoSoapUI(SoapUITestCaseRunner proyectoSoapUI) {
		this.proyectoSoapUI = proyectoSoapUI;
	}

	/**
	 * @return the projectCustomPropertiesMatcher
	 */
	public ProjectCustomPropertiesMatcher getProjectCustomPropertiesMatcher() {
		return projectCustomPropertiesMatcher;
	}

	/**
	 * @param projectCustomPropertiesMatcher
	 *            the projectCustomPropertiesMatcher to set
	 */
	public void setProjectCustomPropertiesMatcher(ProjectCustomPropertiesMatcher projectCustomPropertiesMatcher) {
		this.projectCustomPropertiesMatcher = projectCustomPropertiesMatcher;
	}

	/**
	 * Setea las configuraciones básicas que precisa SoapUI
	 */
	@Before
	public void setUpSettings() {
		getProyectoSoapUI().setSettingsFile(ARCHIVOCONFIGURACIONESSOAPUI);
	}

	/**
	 * Parser de archivo de texto plano a array de String compatible con lo que
	 * precisa el SoapUITestCaseRunner TODO Extraer en otra clase
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
		getProyectoSoapUI().setProjectFile(projectFile);
		getProyectoSoapUI().setProjectProperties(parsearArchivoTextoAPropertiesProyectoSoap(customProperties));
	}

	@Given("^A partir de un acrónimo GEDO, un usuario destino y un usuario con permisos de inicio de documento sobre el mismo$")
	public void a_partir_de_un_acronimo_GEDO_y_un_usuario_sin_permisos_de_firma_sobre_el_mismo() throws Throwable {
		String project = getProjectCustomPropertiesMatcher().getProject(0);
		String customProperties = getProjectCustomPropertiesMatcher().getCustomProperties(0);
		setUpEscenario(project, customProperties);
	}

	@When("^Realiza la invocación del servicio generarTarea$")
	public void ejecutarProyectoSoapUI() throws Exception {
		getProyectoSoapUI().run();
	}

	@Then("^Se genera una tarea de confección de documento al usuario destino$")
	public void validarTareasDeConfeccionGeneradas() throws Throwable {
		/**
		 * TODO Levantar las propiedades del proyecto para obtener el error que
		 * debería arrojar el servicio
		 */
	}

	@Given("^A partir de un acrónimo GEDO, un usuario destino y un usuario con permisos de firma de documento sobre el mismo$")
	public void a_partir_de_un_acronimo_GEDO_un_usuario_destino_y_un_usuario_con_permisos_de_firma_de_documento_sobre_el_mismo()
			throws Throwable {
		String project = getProjectCustomPropertiesMatcher().getProject(1);
		String customProperties = getProjectCustomPropertiesMatcher().getCustomProperties(1);
		setUpEscenario(project, customProperties);
	}

	@Then("^Se genera una tarea de firma de documento al usuario destino$")
	public void validarTareasDeFirmaGeneradas() throws Throwable {
		/**
		 * TODO - generar lógica de validación
		 */
	}
}