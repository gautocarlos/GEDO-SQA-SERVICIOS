package sqa.utils;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * 
 * */
public class ProjectCustomPropertiesMatcher implements ConstantesServicios {

	private JSONArray propertiesPorProyecto;

	/**
	 * @return the propertiesPorProyecto
	 */
	public JSONArray getPropertiesPorProyecto() {
		return propertiesPorProyecto;
	}

	public void setPropertiesPorProyecto(JSONArray listaProyectos) {
		this.propertiesPorProyecto = listaProyectos;
	}

	public ProjectCustomPropertiesMatcher() {
		JSONParser parser = new JSONParser();
		try {
			Object jsonParseToObject = parser.parse(new FileReader(ARCHIVOCONFIGURACIONESPROYECTOSSOAPUI));
			JSONObject jsonListaProyectos = (JSONObject) jsonParseToObject;
			JSONArray listaProyectos = (JSONArray) jsonListaProyectos.get(PROYECTOSSOAPUI);
			setPropertiesPorProyecto(listaProyectos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se asume que el formato del json de carga de "project:properties" es correcto
	 * */
	public String getProject(int projectIndex) {
		return INPUTPROYECTOSSOAPUI + "/" + splitString((String) getPropertiesPorProyecto().get(projectIndex), 0);
	}

	public String getCustomProperties(int projectIndex) {
		return INPUTPROPERTIES + "/" + splitString((String) getPropertiesPorProyecto().get(projectIndex), 1);
	}

	/**
	 * Divide el contenido del string que recibe por parámero por el caracter ":"
	 * */
	private String splitString(String texto, int index) {
		return texto.split(":")[index];

	}
}
