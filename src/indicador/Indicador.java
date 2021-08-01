package indicador;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

import entidades.InfoCandle;
import grafico.Candle;

public class Indicador {

	private static double mediaMovel;
	private static double soma;
	
	public static Double mediaMovel(int parametro, ArrayList<Candle> listaInfoCandle, Candle candle) {
		
		if(listaInfoCandle.size()>=parametro-1) {
			
			mediaMovel = 0;
			soma = 0;
			
			for(int i=0; i<parametro-1; i++) {
				soma += listaInfoCandle.get(i).getFechamento();
			}
			
			soma+= candle.getFechamento();
			mediaMovel = soma/parametro;
			
			return mediaMovel;
		}
		
		return null;
	}
	
	public static Double mediaMovelVolume(int parametro, ArrayList<Candle> grafico, Candle candle) {
			
		if(grafico.size()>=parametro-1) {
		
			mediaMovel = 0;
			soma = 0;
		
			for(int i=0; i<parametro-1; i++) {
				soma += grafico.get(i).getVolume();
			}
			
			soma+= candle.getVolume();
			mediaMovel = soma/parametro;
			
			return mediaMovel;
		}
		return null;
	}
}
