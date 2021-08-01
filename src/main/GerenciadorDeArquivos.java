package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class GerenciadorDeArquivos {
	
	private String baseFile;
	static final String outputFolder = "/series-sql-popula/";
	final String extensao = ".sql";
	
	static File baseDirectory = new File("");
	static final String outputDirectory = baseDirectory.getAbsolutePath() + outputFolder;
	
	
	

	public void criaArquivo(int ano, String content, int parteArquivo) {
		
		String finalDestination = outputDirectory + ano + "_" + parteArquivo + extensao;
		
		try {
			PrintWriter writer = new PrintWriter(finalDestination, "UTF-8");
			writer.println(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getFullName(int i) {
		File baseDirectory = new File("");
		final String base_start = "COTAHIST_A";
		final String base_end = ".TXT";
		final String seriesFolder = "/series-historicas/";
		System.out.println(baseDirectory.getAbsolutePath());
		

		String directory = baseDirectory.getAbsolutePath() + seriesFolder;
		
		String name;
		String fullName;
		name = base_start + i + base_end;
		fullName = directory + name;
		System.out.println(fullName);
		return fullName;
	}
	
	public static ArrayList<String> leLinhasDeArquivo(String fullName) {
		FileReader arquivo = null;
		try {
			arquivo = new FileReader(fullName);
			
			BufferedReader leitura = new BufferedReader(arquivo);
			
			ArrayList<String> linhas = leTodasLinhas(leitura);
			System.out.println(linhas.size());
			return linhas;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(arquivo != null)
					arquivo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static ArrayList<String> leTodasLinhas(BufferedReader leitura) {
		String linha;
		ArrayList<String> linhas = new ArrayList<>();
		
		try {
			linha = leitura.readLine();
			while (linha != null) {
				linhas.add(linha);
				linha = leitura.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linhas;
	}
	
	
	
}
