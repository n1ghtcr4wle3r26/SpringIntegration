package com.egpp.diplomado;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@SpringBootApplication
@ImportResource("http-outbound-gateway.xml")
public class GenerarFacturaApplication {

	@Autowired
	@Qualifier("get_send_channel")
	MessageChannel getSendChannel;

	@Autowired
	@Qualifier("get_receive_channel")
	PollableChannel getReceiveChannel;
	
	String path = "../generar-factura/facturas/";
	
	public static void main(String[] args) {
		SpringApplication.run(GenerarFacturaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		
		return args -> {
			Message<?> message = MessageBuilder.withPayload("").build();						
			getSendChannel.send(message);						
			String obj = getReceiveChannel.receive().getPayload().toString();
			System.out.println("Informacion Servicio: " + obj);										
			String fileName = path + datos_factura() + ".json";			
			try {
				FileWriter file = new FileWriter(fileName);
				file.write(JSONtoTXT.JsonToString(obj, 4));
				file.flush();
				file.close();
				System.out.println("Factura generada: " + fileName);								
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
	
	public static String datos_factura() {
		String res = "";
		int numero_factura = (int)(Math.random()*3000+1);
		String name = new SimpleDateFormat("dd-MM-yyyy_HHmmss").format(new Date());
		res = "factura_"+ String.valueOf(numero_factura) + "_"+name; 
		return res;		
	}
}
