package entidades;

import java.io.Serializable;
import java.time.LocalDate;

import grafico.Candle;


public class InfoCandle implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nomeDoPapel;

	private LocalDate data;

	private Double abertura;

	private Double fechamento;
	
	private Double maxima;

	private Double minima;

	private Double volume;

	private Double precoMedia8;

	private Double precoMedia20;

	private Double precoMedia200;

	private Double volumeMedia20;

	public InfoCandle() {

	}
	public InfoCandle(Candle candle) {
		this.nomeDoPapel = candle.getPapel();
		this.abertura = candle.getAbertura();
		this.fechamento = candle.getFechamento();
		this.maxima = candle.getMaxima();
		this.minima = candle.getMinima();
		this.volume = candle.getVolume();
		this.data = candle.getData();
	}

	/*
	 * public void setInfoCandlePK(InfoCandlePK infoCandlePK) { this.infoCandlePK =
	 * infoCandlePK; }
	 * 
	 * public InfoCandlePK getInfoCandlePK() { return infoCandlePK; }
	 */

	public void setPrecoMedia8(Double precoMedia8) {
		this.precoMedia8 = precoMedia8;
	}

	public void setPrecoMedia20(Double precoMedia20) {
		this.precoMedia20 = precoMedia20;
	}

	public void setPrecoMedia200(Double precoMedia200) {
		this.precoMedia200 = precoMedia200;
	}

	public void setVolumeMedia20(Double volumeMedia20) {
		this.volumeMedia20 = volumeMedia20;
	}

	public Double getPrecoMedia8() {
		return precoMedia8;
	}

	public Double getPrecoMedia20() {
		return precoMedia20;
	}

	public Double getPrecoMedia200() {
		return precoMedia200;
	}

	public Double getVolumeMedia20() {
		return volumeMedia20;
	}

	public String getNomeDoPapel() {
		return nomeDoPapel;
	}

	public LocalDate getData() {
		return data;
	}

	public Double getAbertura() {
		return abertura;
	}

	public Double getFechamento() {
		return fechamento;
	}

	public Double getMaxima() {
		return maxima;
	}

	public Double getMinima() {
		return minima;
	}

	public Double getVolume() {
		return volume;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setNomeDoPapel(String nomeDoPapel) {
		this.nomeDoPapel = nomeDoPapel;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public void setAbertura(Double abertura) {
		this.abertura = abertura;
	}
	public void setFechamento(Double fechamento) {
		this.fechamento = fechamento;
	}
	public void setMaxima(Double maxima) {
		this.maxima = maxima;
	}
	public void setMinima(Double minima) {
		this.minima = minima;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}

}
