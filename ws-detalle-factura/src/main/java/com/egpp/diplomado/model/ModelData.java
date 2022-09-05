package com.egpp.diplomado.model;

import java.io.Serializable;

public class ModelData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cantidad;
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPrecio_unitario() {
		return precio_unitario;
	}
	public void setPrecio_unitario(int precio_unitario) {
		this.precio_unitario = precio_unitario;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	private String descripcion;
	private int precio_unitario;
	private int total;
}
