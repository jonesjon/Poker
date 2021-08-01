package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import entidades.InfoCandle;
import grafico.Candle;
import indicador.Indicador;;

public class Main {
	
	public static final int NUMERO_MAGICO_UM = 187;
	static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static Scanner myScanner = new Scanner(System.in);
	public static HashMap<String, ArrayList<Candle>> papeisTemp = new HashMap<String, ArrayList<Candle>>();
	public static final int MEDIA_MOVEL_CURTA = 8;
	public static final int MEDIA_MOVEL_MEDIA = 20;
	public static final int MEDIA_MOVEL_LONGA = 200;
	public static final int MEDIA_VOLUME = 20;
	
	

	public static void main(String[] args) {
		System.out.println("iniciando formatador");
		
		BancoService.setup();

		defineStartInicialParaArquivos();
		
		defineStartInicialParaBanco();
		
		leSQLs();
	}
	
	private static void defineStartInicialParaArquivos() {
		System.out.println("Gera arquivos sql para lote? default (n) s/n");
		
		 String ans = myScanner.nextLine();
		    
	    if(ans.length() == 1 && ans.charAt(0) == 's') {
	    	preparaArquivosIniciaisSQLs();
	    }
	}

	private static void defineStartInicialParaBanco() {
		System.out.println("Limpa registros? default (n) s/n:");

	    String ans = myScanner.nextLine();
	    
	    if(ans.length() == 1 && ans.charAt(0) == 's') {
	    	BancoService.dropaDatabase();
	    }
	}

	private static void leSQLs() {
		String[] pathnames;
		File f = new File(GerenciadorDeArquivos.outputDirectory);
		pathnames = f.list();
		for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
            
            String fullName = GerenciadorDeArquivos.outputDirectory + pathname;
            ArrayList<String> linhas =GerenciadorDeArquivos.leLinhasDeArquivo(fullName);
            System.out.println(linhas.get(0).substring(0, 15) );
            
            BancoService.executaQuery(linhas.get(0));
		}
		System.out.println("População terminada");
	}

	private static void preparaArquivosIniciaisSQLs() {
		String fullName;
		for(int ano=2000; ano<=2021; ano++) {
			fullName = GerenciadorDeArquivos.getFullName(ano);

			try {
				escreveLotesSQLFormatados(fullName, ano);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	private static void escreveLotesSQLFormatados(String fullName, int ano) throws FileNotFoundException {
		ArrayList<String> linhas = pegaLinhasDoAno(fullName);
		if(linhas != null) {
			System.out.println("Preparando ano: " + ano);
			preparaArquivosSQL(linhas, ano);
			System.out.println("Fim do ano: " + ano);
		}
	}

	private static ArrayList<String> pegaLinhasDoAno(String fullName) throws FileNotFoundException {

		ArrayList<String> linhas = GerenciadorDeArquivos.leLinhasDeArquivo(fullName);
		return linhas;
		
	}
	
	private static void preparaArquivosSQL(ArrayList<String> linhas, int ano) {
		final int LOTES = 5_000;
		String queryPrimeira = "INSERT INTO INFO_CANDLE" +
				"(dat, abertura, maxima, minima, fechamento, volume, nomeDoPapel, precoMedia8, precoMedia20, precoMedia200, volumeMedia20) values ";
		
		String queryFinal = queryPrimeira;
		String values = "";
		
		int cont = 0;
		int parte = 1;
		
		for(int j = 0 ; j < linhas.size(); j++) {
			String linha = linhas.get(j);
			//System.out.println(linha);
			ArrayList<String> caracteres = new ArrayList<String>();
			if(linha != null) {
				for (int i1 = 0; i1 < linha.length(); i1++) {
					caracteres.add(i1, "" + linha.charAt(i1));
				}
			}
			
		
			String linhaValuesSQL = pegaValuesSQL(caracteres);
			if(linhaValuesSQL != null) {
				values += linhaValuesSQL + ",";
				cont += 1;
				
			}
			
			if(cont == LOTES ) {
				
				values = values.substring(0,values.length() - 1);
				values += ";";
				
				queryFinal += values;
				
				GerenciadorDeArquivos g = new GerenciadorDeArquivos();
				g.criaArquivo(ano, queryFinal, parte);
				
				cont = 0;
				parte += 1;
				values = "";
				queryFinal = queryPrimeira;
			}
				

		}
		
	}

	private static String pegaValuesSQL(ArrayList<String> caracteres) {
		if(caracteres.size() >= NUMERO_MAGICO_UM) {
			fazA(caracteres);
			String linhaValuesSQL = fazB(caracteres);
			return linhaValuesSQL;
		}
		return null;
	}
	
	private static String fazB(ArrayList<String> caracteres) {
		if (Integer.parseInt(caracteres.get(1)) == 1) { // Informa��es referentes aos papeis
			
				
			String papelDaLinha = "";
			String abertura = "";
			String fechamento = "";
			String maxima = "";
			String minima = "";
			String volume = "";
			String dia = "";
			String mes = "";
			String ano = "";
				

			for (int i = 12; i <= 23; i++) { // Verifica o nome do ativo no arquivo
				papelDaLinha += caracteres.get(i);
			} 
			
			if(papelDaLinha.trim().charAt(papelDaLinha.trim().length() -1) == 'T')
				return null;

			for (int i = 8; i <= 9; i++) { // dia de negocia��o
				dia += caracteres.get(i);
			}
			for (int i = 6; i <= 7; i++) { // mes de negocia��o
				mes += caracteres.get(i);
			}
			for (int i = 2; i <= 5; i++) { // ano de negocia��o
				ano += caracteres.get(i);
			}
			for (int i = 56; i <= 68; i++) { // Pre�o de abertura do ativo
				abertura += caracteres.get(i);
			}
			for (int i = 69; i <= 81; i++) { // Pre�o maximo do ativo
				maxima += caracteres.get(i);
			}
			for (int i = 82; i <= 94; i++) { // Pre�o minimo do ativo
				minima += caracteres.get(i);
			}
			for (int i = 108; i <= 120; i++) { // Pre�o de fechamento do ativo
				fechamento += caracteres.get(i);
			}
			for (int i = 170; i <= 187; i++) { // Volume de negocia��o
				volume += caracteres.get(i);
			}

			String sDate = dia + "/" + mes + "/" + ano;
			LocalDate date = LocalDate.parse(sDate, formato);
			
			formataNumero(abertura);
			
			String parte1 = "(#,#,#,#,#,#,#,#,#,#,#)";
			parte1 = parte1.replaceFirst("#", "'" + date.toString() + "'");
			parte1 = parte1.replaceFirst("#", formataNumero(abertura));
			parte1 = parte1.replaceFirst("#", formataNumero(maxima));
			parte1 = parte1.replaceFirst("#", formataNumero(minima));
			parte1 = parte1.replaceFirst("#", formataNumero(fechamento));
			parte1 = parte1.replaceFirst("#", volume);
			parte1 = parte1.replaceFirst("#", "'" + papelDaLinha.trim() + "'");
				
			Double media8 = null;
			Double media20 = null;
			Double media200 = null;
			Double mediaVolume = null;

			Candle candle = new Candle(date, abertura, maxima, minima, fechamento, volume, papelDaLinha.trim());
			ArrayList<Candle> candlesTemp = papeisTemp.get(papelDaLinha.trim());
			
			if(candlesTemp == null) {
				papeisTemp.put(papelDaLinha.trim(), new ArrayList<Candle>(MEDIA_MOVEL_LONGA -1));
				candlesTemp = papeisTemp.get(papelDaLinha.trim());
			} else {
				if(candlesTemp.size() >= MEDIA_MOVEL_CURTA -1) {
					List<Candle> c = candlesTemp.subList(candlesTemp.size() - (MEDIA_MOVEL_CURTA -1) , candlesTemp.size());
					Collections.reverse(Arrays.asList(c));
					ArrayList<Candle> reversed = new ArrayList<Candle>(c);
					media8 = Indicador.mediaMovel(MEDIA_MOVEL_CURTA, reversed, candle);
				}	
				
				if(candlesTemp.size() >= MEDIA_MOVEL_MEDIA -1) {
					List<Candle> c = candlesTemp.subList(candlesTemp.size() - (MEDIA_MOVEL_MEDIA -1) , candlesTemp.size());
					Collections.reverse(Arrays.asList(c));
					ArrayList<Candle> reversed = new ArrayList<Candle>(c);
					media20 = Indicador.mediaMovel(MEDIA_MOVEL_MEDIA, reversed, candle);
				}
				
				if(candlesTemp.size() >= MEDIA_MOVEL_LONGA -1) {
					List<Candle> c = candlesTemp.subList(candlesTemp.size() - (MEDIA_MOVEL_LONGA -1) , candlesTemp.size());
					Collections.reverse(Arrays.asList(c));
					ArrayList<Candle> reversed = new ArrayList<Candle>(c);
					media200 = Indicador.mediaMovel(MEDIA_MOVEL_LONGA, reversed, candle);
					
				}	
				
				if(candlesTemp.size() >= MEDIA_VOLUME -1) {
					List<Candle> c = candlesTemp.subList(candlesTemp.size() - (MEDIA_VOLUME -1) , candlesTemp.size());
					Collections.reverse(Arrays.asList(c));
					ArrayList<Candle> reversed = new ArrayList<Candle>(c);
					mediaVolume = Indicador.mediaMovelVolume(MEDIA_VOLUME, reversed, candle);
				}
			}
			
			papeisTemp.get(papelDaLinha.trim()).add(candle);
			
			if(papeisTemp.get(papelDaLinha.trim()).size() >= MEDIA_MOVEL_LONGA) {
				List<Candle> cs = papeisTemp.get(papelDaLinha.trim()).subList(papeisTemp.get(papelDaLinha.trim()).size() - MEDIA_MOVEL_LONGA, papeisTemp.get(papelDaLinha.trim()).size());
				ArrayList<Candle> cs1 = new ArrayList<Candle>(cs);
				papeisTemp.get(papelDaLinha.trim()).clear();
				papeisTemp.get(papelDaLinha.trim()).addAll(cs1);
			}
			
			parte1 = parte1.replaceFirst("#", media8 != null ? media8.toString() : "null");
			parte1 = parte1.replaceFirst("#", media20 != null ? media20.toString() : "null");
			parte1 = parte1.replaceFirst("#", media200 != null ? media200.toString() : "null");
			parte1 = parte1.replaceFirst("#", mediaVolume != null ? mediaVolume.toString() : "null");

			return parte1;

		}
		return null;
	}

	private static String formataNumero(String dado) {
		Long lo = Long.parseLong(dado);
		Float l = lo/100f;
		return l.toString();
	}
	
	private static void fazA(ArrayList<String> caracteres) {
		
		if (Integer.parseInt(caracteres.get(1)) == 0) { // Header

			LocalDate data;
			
			data = LocalDate.parse(caracteres.get(29) + caracteres.get(30) + "/" + caracteres.get(27)
					+ caracteres.get(28) + "/" + caracteres.get(23) + caracteres.get(24) + caracteres.get(25)
					+ caracteres.get(26), formato); 

			/* System.out.println("Data da geração do Arquivo: " + formato.format(data)); */
		}
	}

}
