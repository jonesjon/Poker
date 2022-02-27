package indicador;

import java.util.ArrayList;
import java.util.List;

import entidades.InfoCandle;
import grafico.Candle;

public class Indicador {

	private static double mediaMovel;
	private static double soma;
	private static final int MEDIACURTA = 8;
	private static final int MEDIA = 20;
	private static final int MEDIALONGA = 200;

	public static ArrayList<InfoCandle> calculaMediasMoveis(ArrayList<InfoCandle> candles) {

		ArrayList<InfoCandle> candlesUpdate = new ArrayList<InfoCandle>();
		
		for(int i=0; i<candles.size(); i++) {
			
			InfoCandle infoCandle = candles.get(i);
			
			if(i>=MEDIACURTA-1) {
				ArrayList<InfoCandle> candlesAux = (ArrayList<InfoCandle>) candles.subList(i-(MEDIACURTA-1), i);
				InfoCandle candle = candles.get(i);
				Double mediaCurta = mediaMovel(MEDIACURTA, candlesAux, candle);
				infoCandle.setPrecoMedia8(mediaCurta);
				
			}
			if(i>=MEDIA-1) {
				
				ArrayList<InfoCandle> candlesAux = (ArrayList<InfoCandle>) candles.subList(i-(MEDIA-1), i);
				InfoCandle candle = candles.get(i);
				Double media = mediaMovel(MEDIA, candlesAux, candle);
				Double mediaVolume = mediaMovelVolume(MEDIA, candlesAux, candle);
				infoCandle.setPrecoMedia20(media);
				infoCandle.setVolumeMedia20(mediaVolume);
				
			}
			if(i>=MEDIALONGA-1) {
				ArrayList<InfoCandle> candlesAux = (ArrayList<InfoCandle>) candles.subList(i-(MEDIALONGA-1), i);
				InfoCandle candle = candles.get(i);
				Double mediaLonga = mediaMovel(MEDIALONGA, candlesAux, candle);
				infoCandle.setPrecoMedia200(mediaLonga);
			}
			
			candlesUpdate.add(i, infoCandle);
			
		}
		
		return candlesUpdate;
	}

	public static Double mediaMovel(int parametro, ArrayList<InfoCandle> listaInfoCandle, InfoCandle candle) {

		if (listaInfoCandle.size() >= parametro - 1) {

			mediaMovel = 0;
			soma = 0;

			for (int i = 0; i < parametro - 1; i++) {
				soma += listaInfoCandle.get(i).getFechamento();
			}

			soma += candle.getFechamento();
			mediaMovel = soma / parametro;

			return mediaMovel;
		}

		return null;
	}

	public static Double mediaMovelVolume(int parametro, ArrayList<InfoCandle> grafico, InfoCandle candle) {

		if (grafico.size() >= parametro - 1) {

			mediaMovel = 0;
			soma = 0;

			for (int i = 0; i < parametro - 1; i++) {
				soma += grafico.get(i).getVolume();
			}

			soma += candle.getVolume();
			mediaMovel = soma / parametro;

			return mediaMovel;
		}
		return null;
	}
}
