package com.ddm.climatempo.api.model;

import java.time.LocalDate;


public class Clima {
	
	private double tempDia;
	
	private double tempMax;
	
	private double tempMin;
	
	private String linkIcone;
	
	private LocalDate dataClima;
	
	private double umidade;

	private Double chuva;
	
	private String clima;
	
	private Double nuvem;
	
	private Double vento;

	
	public Double getNuvem() {
		return nuvem;
	}

	public void setNuvem(Double nuvem) {
		this.nuvem = nuvem;
	}

	public Double getVento() {
		return vento;
	}

	public void setVento(Double vento) {
		this.vento = vento;
	}

	public double getTempDia() {
		return tempDia;
	}

	public void setTempDia(double tempDia) {
		this.tempDia = tempDia;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public String getLinkIcone() {
		return linkIcone;
	}

	public void setLinkIcone(String linkIcone) {
		this.linkIcone = linkIcone;
	}

	public LocalDate getDataClima() {
		return dataClima;
	}

	public void setDataClima(LocalDate dataClima) {
		this.dataClima = dataClima;
	}

	public double getUmidade() {
		return umidade;
	}

	public void setUmidade(double umidade) {
		this.umidade = umidade;
	}

	public Double getChuva() {
		return chuva;
	}

	public void setChuva(Double chuva) {
		this.chuva = chuva;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}
	
	
}

