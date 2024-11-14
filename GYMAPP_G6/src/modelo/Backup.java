package modelo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Backup {

	public static final String FILE_USERS = "backups/usuario.dat";
	public static final String FILE_WORKOUTS = "backups/workouts.dat";
	public static final String FILE_HISTORICO_XML = "backups/historico.xml";

	private static int maxLevel = 3;

	public static void main(String[] args) throws ParserConfigurationException {
		guardarUsuarios(new Usuario().obtenerMultiplesUsuarios(true));
		guardarWorkouts(new Workout().obtenerWorkouts((long) maxLevel, true));

	}

	public static void guardarUsuarios(ArrayList<Usuario> usuarios) throws ParserConfigurationException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_USERS))) {
			for (Usuario u : usuarios) {
				oos.writeObject(u);
				guardarHistoricoEnXML(u.getId(), u.getUsersCollection());

			}
			System.out.println("Users saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void guardarWorkouts(ArrayList<Workout> workouts) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_WORKOUTS))) {
			for (Workout w : workouts) {
				if (w.getListaEjercicios() == null || w.getListaEjercicios().isEmpty()) {
					w.setListaEjercicios(new Ejercicio().obtenerEjercicios(w.getId(), true));
				}
				oos.writeObject(w);
			}
			System.out.println("Workouts saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void guardarHistoricoEnXML(String idUsuario, String coleccion) throws ParserConfigurationException {
		Historial h = new Historial();
		ArrayList<Historial> historial = h.mObtenerHistorico(coleccion, idUsuario);

		try {
			File archivoXML = new File(FILE_HISTORICO_XML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;

			// Verificar si el archivo XML ya existe
			if (archivoXML.exists() && archivoXML.length() > 0) {
				try {
					doc = dBuilder.parse(archivoXML);
				} catch (SAXException | IOException e) {
					// Si el archivo existe pero da error, crear un nuevo documento
					doc = dBuilder.newDocument();
					Element rootElement = doc.createElement("historico");
					doc.appendChild(rootElement);
				}
			} else {
				// Si el archivo no existe o está vacío, crear un nuevo documento y el elemento
				// raíz
				doc = dBuilder.newDocument();
				Element rootElement = doc.createElement("historico");
				doc.appendChild(rootElement);
			}

			Element elementoRaiz = doc.getDocumentElement();
			Element usuario = null;

			// Verificar si ya existe el usuario en el XML
			NodeList usuarios = doc.getElementsByTagName("usuario");
			for (int i = 0; i < usuarios.getLength(); i++) {
				Element usuarioExistente = (Element) usuarios.item(i);
				if (usuarioExistente.getAttribute("id").equals(idUsuario)) {
					// Eliminar el nodo del usuario del elemento raíz
					usuarioExistente.getParentNode().removeChild(usuarioExistente);
					break; // Salir del bucle una vez eliminado el usuario
				}
			}

			// Si el usuario no existe en el XML, crea uno nuevo
			if (usuario == null) {
				usuario = doc.createElement("usuario");
				usuario.setAttribute("id", idUsuario);
				elementoRaiz.appendChild(usuario);
			}

			// Agregar los registros históricos del usuario al XML
			for (Historial registroHistorial : historial) {
				Workout workout = registroHistorial.getWorkout();

				Element registro = doc.createElement("registro");
				usuario.appendChild(registro);

				Element nombre = doc.createElement("nombre");
				nombre.appendChild(doc.createTextNode(workout.getNombre()));
				registro.appendChild(nombre);

				Element nivel = doc.createElement("nivel");
				nivel.appendChild(doc.createTextNode(String.valueOf(workout.getNivel())));
				registro.appendChild(nivel);

				Element tiempoRealizacion = doc.createElement("tiempoTotal");
				tiempoRealizacion
						.appendChild(doc.createTextNode(String.valueOf(registroHistorial.getTiempoRealizacion())));
				registro.appendChild(tiempoRealizacion);

				Element tiempoPrevisto = doc.createElement("tiempoPrevisto");
				tiempoPrevisto.appendChild(doc.createTextNode(String.valueOf(workout.getTiempoPrevisto())));
				registro.appendChild(tiempoPrevisto);

				Element fecha = doc.createElement("fecha");
				fecha.appendChild(doc.createTextNode(registroHistorial.getFecha().toString()));
				registro.appendChild(fecha);

				Element porcentajeCompletado = doc.createElement("porcentajeCompletado");
				porcentajeCompletado
						.appendChild(doc.createTextNode(String.valueOf(registroHistorial.getPorcentajeCompletado())));
				registro.appendChild(porcentajeCompletado);

			}

			// Guardar el contenido en el archivo XML sin sobrescribir completamente
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(archivoXML);
			transformer.transform(source, result);

			System.out.println("Historico XML saved for user ID: " + idUsuario);

		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
