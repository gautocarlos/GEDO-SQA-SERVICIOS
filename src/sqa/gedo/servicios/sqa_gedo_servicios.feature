Feature: Firma de documentos de tipo libre por servicio

Scenario: GEDO-ENVIAR A PRODUCIR

	Given A partir de un acrónimo GEDO, un usuario destino y un usuario con permisos de inicio de documento sobre el mismo
	When Realiza la invocación del servicio generarTarea
	Then Se genera una tarea de confección de documento al usuario destino
	
