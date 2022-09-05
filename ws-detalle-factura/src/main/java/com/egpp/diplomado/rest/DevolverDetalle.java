package com.egpp.diplomado.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.egpp.diplomado.funciones.Randomicos;
import com.egpp.diplomado.model.ModelData;

@RestController 
@RequestMapping(path = "/servicio")
public class DevolverDetalle {
	
	static List<ModelData> compras = new ArrayList<ModelData>();	
	
	@GetMapping(path = "/detallefactura", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelData getData(@RequestParam(name = "nombre") String message){
		ModelData response = new ModelData();
		response.setCantidad(15);
		response.setDescripcion("Cuadernos de 100 hojas tamaño carta");
		response.setPrecio_unitario(20);
		response.setTotal(300);
		return response;
	}
	
	@GetMapping(path = "/detallecompras", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ModelData> getCompras(@RequestParam(name = "nombre") String message){
						
		int cantidad;
		int precio_unitario;
		int total;
		int codigo_producto;
		compras.clear();
		
		for (int i = 1; i <= 10; i++) {
			cantidad = (int)(Math.random()*50+1);
			codigo_producto = (int)(Math.random()*3000+1);
			precio_unitario = (int)(Math.random()*300+1);
			total = cantidad * precio_unitario;
			ModelData response = new ModelData();	
			response.setCantidad(cantidad);
			response.setDescripcion("Producto con codigo " + codigo_producto);
			response.setPrecio_unitario(precio_unitario);
			response.setTotal(total);
			compras.add(response);					
		}
		/*
		response.setCantidad(15);
		response.setDescripcion("Cuadernos de 100 hojas tamaño carta");
		response.setPrecio_unitario(20);
		response.setTotal(300);
		compras.add(response);
		response.setCantidad(Randomicos.aleatorio());
		response.setDescripcion("Cuadernos de 100 hojas tamaño oficio");
		response.setPrecio_unitario(15);
		response.setTotal(150);
		compras.add(response);		
		
		
		Random numAleatorio = new Random();
		*/
		
		return compras;
	}
	
	
	
	
}