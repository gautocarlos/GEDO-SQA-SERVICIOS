Feature: Firma de documentos de tipo libre por servicio

Scenario: Crear un documento GEDO del tipo libre mediante servicio 

	Given A partir de un acrónimo GEDO y un usuario con permisos de firma sobre el mismo 
	When Realiza la invocación del servicio generarDocumento 
	Then Se genera un número de documento GDE 
	
Scenario: Intentar crear un documento GEDO del tipo libre mediante servicio con usuario de repartición sin permiso de firma sobre tal 

	Given A partir de un acrónimo GEDO y un usuario sin permisos de firma sobre el mismo
	When Realiza la invocación del servicio generarDocumento 
	Then No se genera un número de documento GDE