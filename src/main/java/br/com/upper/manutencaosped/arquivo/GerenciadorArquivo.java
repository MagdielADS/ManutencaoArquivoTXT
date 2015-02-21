/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upper.manutencaosped.arquivo;

import br.com.upper.manutencaosped.excecoes.OutColumnOfFileLimits;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Magdiel Ildefonso
 */
public class GerenciadorArquivo {

    public static int alterarLinhasPorRegistro(String caminho, String destino, String registro, int coluna, String valorAntigo, String valorNovo) throws FileNotFoundException, IOException, OutColumnOfFileLimits {
        List<String> linhas = new ArrayList<String>();
        FileReader arq;
        int qtde = 0;
        String arquivoTmp = "";
        if (destino.equals("") || destino.equals("")) {
            Calendar data = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");
            String dataFormat = df.format(data.getTime());
            arquivoTmp += caminho.substring(0, caminho.indexOf("."));
            arquivoTmp += "-" + dataFormat + "-modif.txt";
        } else {
            Calendar data = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");
            String dataFormat = df.format(data.getTime());

            String aux = caminho.replace('\\', '/');
            String[] vetorAux = aux.split("/");
            arquivoTmp += destino + "\\";
            arquivoTmp += vetorAux[vetorAux.length - 1].substring(0, vetorAux[vetorAux.length - 1].indexOf("."));

            arquivoTmp += "-" + dataFormat + "-modif.txt";
        }

        arq = new FileReader(caminho);
        BufferedReader lerArq = new BufferedReader(arq);
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTmp));

        String linha = lerArq.readLine();

        while (linha != null) {
            if (linha.charAt(0) == '|') {
                String[] vetorInicial = linha.split("\\|", -1);
                String[] valores = new String[vetorInicial.length - 1];
                for (int i = 0; i < vetorInicial.length - 1; i++) {
                    valores[i] = vetorInicial[i];
                }

                if (valores[1].equalsIgnoreCase(registro)) {
                    if (coluna >= valores.length - 1) {
                        System.out.println("Linha: " + linha + "Length: " + valores.length);
                        writer.close();
                        new File(arquivoTmp).delete();
                        throw new OutColumnOfFileLimits("Coluna " + String.valueOf(coluna) + " não existe no registro " + registro);
                    } else {
                        if (valores[coluna].equals(valorAntigo)) {
                            valores[coluna] = valorNovo;
                            qtde++;
                        } else if (valorAntigo.equals("") || valorAntigo.equals(" ")) {
                            valores[coluna] = valorNovo;
                            qtde++;
                        }

                        String novaLinha = "";
                        for (String string : valores) {
                            novaLinha += "|" + string;
                        }
                        novaLinha = novaLinha.substring(1);
                        novaLinha += "|";
                        System.out.println(novaLinha);
                        novaLinha += System.getProperty("line.separator");
                        writer.write(novaLinha);
                    }
                } else {
                    System.out.println(linha);
                    linha += System.getProperty("line.separator");
                    writer.write(linha);
                }

                linha = lerArq.readLine();
            } else {
                linha = lerArq.readLine();
            }
        }

        arq.close();
        lerArq.close();
        writer.close();
        System.out.println("Quantidade: " + qtde);
        if (qtde <= 0) {
            new File(arquivoTmp).delete();
        }

        return qtde;
    }
}
