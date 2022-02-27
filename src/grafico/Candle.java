package grafico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Candle {
	
		private LocalDate data;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		private String papel;
		private double abertura; 
		private double fechamento; 
		private double maxima; 
		private double minima; 
		private double volume;
		
		public Candle() {
			
		}
		
		public Candle(LocalDate date, String abertura, String maxima,
					String minima, String fechamento, String volume, String papel) {
			
			this.abertura = Double.parseDouble(abertura)/100;
			this.fechamento = Double.parseDouble(fechamento)/100;
			this.maxima = Double.parseDouble(maxima)/100;
			this.minima = Double.parseDouble(minima)/100;
			this.volume = Double.parseDouble(volume);
			this.data = date;
			this.papel = papel;
			
		}
		
		public LocalDate getDate() {
			return data;
		}

		public String getPapel() {
			return papel;
		}

		public double getMaxima() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public double getFechamento() {
			return fechamento;
		}
		
		public double getVolume() {
			return volume;
		}

		public LocalDate getData() {
			return data;
		}

		public void setData(LocalDate data) {
			this.data = data;
		}

		public DateTimeFormatter getFormato() {
			return formato;
		}

		public void setFormato(DateTimeFormatter formato) {
			this.formato = formato;
		}

		public double getAbertura() {
			return abertura;
		}

		public void setAbertura(double abertura) {
			this.abertura = abertura;
		}

		public double getMinima() {
			return minima;
		}

		public void setMinima(double minima) {
			this.minima = minima;
		}

		public void setPapel(String papel) {
			this.papel = papel;
		}

		public void setFechamento(double fechamento) {
			this.fechamento = fechamento;
		}

		public void setMaxima(double maxima) {
			this.maxima = maxima;
		}

		public void setVolume(double volume) {
			this.volume = volume;
		}


		
}
